package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsgroupdetailspage.panels.contentpermissionspane.permissionblocks;

import com.xyleme.bravais.datacontainers.PermissionLevel;
import com.xyleme.bravais.web.WebPage;
import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.Select;
import com.xyleme.bravais.web.elements.TextInput;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnContentPermissionsPanelOfGroupDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.TableOnContentPermissionsPanelOfGroupDetailsPage;
import com.xyleme.bravais.web.pages.cds.functionalmobules.additemform.BaseAddItemForm;
import com.xyleme.bravais.web.pages.cds.functionalmobules.filterform.FilterFormWithBulkChanges;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Base Permissions Block.
 */
public abstract class BasePermissionsBlock extends WebPage<BasePermissionsBlock> {
    private String blockName;

    BasePermissionsBlock(WebDriver driver, String blockName) {
        super(driver);
        this.blockName = blockName;
    }

    @Override
    public boolean isAvailable() {
        return blockContainerElement().isAvailable() &&
                addPermissionsButton().isAvailable();
    }

    @Override
    public BasePermissionsBlock load() {
        return this;
    }

    private Element blockContainerElement() {
        return new Element(driver, By.xpath("//h2[text()='" + blockName +
                "']//ancestor::div[contains(@class, 'container permission')]"));
    }

    private Button addPermissionsButton() {
        return new Button(driver, By.xpath(blockContainerElement().getXpathLocatorValue() +
                "//button[normalize-space()='Add Permission']"));
    }

    /**
     * Gets Filter form with Bulk Changes drop-down.
     *
     * @return {@code FilterFormWithBulkChanges}
     */
    private FilterFormWithBulkChanges getFilterFormWithBulkChanges() {
        return new FilterFormWithBulkChanges(driver, By.xpath(blockContainerElement().getXpathLocatorValue() +
                "//form[contains(@class, 'permissions-filter')]"));
    }

    /**
     * Gets filtered table item.
     *
     * @param itemName  - Specifies name of the item
     * @param fullMatch - Specifies filtering type (true - full match / false - part match)
     * @return {@code RowOfTableOnContentPermissionsPanelOfGroupDetailsPage}
     */
    public RowOfTableOnContentPermissionsPanelOfGroupDetailsPage getFilteredTableItem(String itemName, boolean fullMatch) {
        getFilterFormWithBulkChanges().enterQueryIntoFilterInputField(itemName);
        RowOfTableOnContentPermissionsPanelOfGroupDetailsPage firstTableRowAfterFiltering = getDataTable().getRow(
                0);
        String filteredGroupName = firstTableRowAfterFiltering.getName();
        boolean condition = fullMatch ? filteredGroupName.equals(itemName) : filteredGroupName.startsWith(itemName);
        if (condition) {
            return firstTableRowAfterFiltering;
        } else {
            throw new RuntimeException("Permissions Table doesn't contain item with name '" + itemName + "'!");
        }
    }

    /**
     * Clicks Add Permission button and returns Add Permission from.
     *
     * @return {@code AddPermissionForm}
     */
    private AddPermissionForm clickAddPermissionButton() {
        addPermissionsButton().click();
        return new AddPermissionForm(driver);
    }

    /**
     * Clicks Add Permission button, fills out Add Permission form with specified data, clicks Add button, and returns
     * specified permissions block.
     *
     * @param itemName        - Specifies item name the permission intended to be set for
     * @param permissionLevel - Specifies the permission level intended to be set for the item
     * @param blockToReturn   - Specifies the block expected to be returned
     * @return {@code <T extends BasePermissionsBlock>}
     */
    <T extends BasePermissionsBlock> T addPermission(String itemName, PermissionLevel permissionLevel, Class<T> blockToReturn) {
        AddPermissionForm addPermissionForm = clickAddPermissionButton();
        return addPermissionForm.fillOutAndSubmitForm(itemName, permissionLevel.getValue(), blockToReturn);
    }

    /**
     * Gets Data Table available in the block.
     *
     * @return {@code TableOnContentPermissionsPanelOfGroupDetailsPage}
     */
    public TableOnContentPermissionsPanelOfGroupDetailsPage getDataTable() {
        return new TableOnContentPermissionsPanelOfGroupDetailsPage(driver, blockContainerElement());
    }

    /**
     * Implementation of Add Permission form which appears after clicking Add Permission button in a respective block.
     */
    private class AddPermissionForm extends BaseAddItemForm {

        AddPermissionForm(WebDriver driver) {
            super(driver);
        }

        @Override
        public boolean isAvailable() {
            return super.isAvailable() &&
                    nameInputField().isAvailable() &&
                    permissionLevelDropDown().isAvailable();
        }

        @Override
        protected Element parentFormElement() {
            return new Element(driver, By.xpath(blockContainerElement().getXpathLocatorValue() +
                    "//div[@id='add-permission-form']//form"));
        }

        private TextInput nameInputField() {
            return new TextInput(driver, By.xpath(parentFormElement().getXpathLocatorValue() +
                    "//input[contains(@id, 'name')]"));
        }

        private Select permissionLevelDropDown() {
            return new Select(driver, By.xpath(parentFormElement().getXpathLocatorValue() +
                    "//select[contains(@id, 'new-permission-level')]"));
        }

        /**
         * Enters specified name into the Name input field.
         *
         * @param name - Specifies the name intended to be entered into the respective input field
         * @return {@code AddPermissionForm}
         */
        private AddPermissionForm enterName(String name) {
            String truncatedUserName = name.substring(0, name.length() - 3); // ToDo: Remove this line after the issue is fixed.
            fillOutInputField(nameInputField(), truncatedUserName); // ToDo: Workaround for issue #<TBD>. Use full value (fullUserName) after the issue is fixed.
            if (blockName.equals("Channels")) {
                Element expectedMatch = new Element(driver, By.xpath(parentFormElement().getXpathLocatorValue() +
                        "//a[text()='" + name + "']/ancestor::li[@role='option']"));
                expectedMatch.waitUntilAvailable().click();
            } else {
                selectSuggestedInputFieldMatch(name);
            }
            return this;
        }

        /**
         * Selects specified permission level in the respective drop-down.
         *
         * @param permissionLevel - Specifies the permission level intended to be selected
         * @return {@code AddPermissionForm}
         */
        private AddPermissionForm selectPermissionLevel(String permissionLevel) {
            permissionLevelDropDown().waitUntilAvailable().selectItemByValue(permissionLevel);
            return this;
        }

        /**
         * Fills out the form with specified data and clicks Add button.
         *
         * @param name            - Specifies item name the permission intended to be set for
         * @param permissionLevel - Specifies the permission level intended to be set for the item
         * @param blockToReturn   - Specifies the block expected to be returned
         * @return {@code <T extends BasePermissionsBlock>}
         */
        <T extends BasePermissionsBlock> T fillOutAndSubmitForm(String name, String permissionLevel, Class<T> blockToReturn) {
            enterName(name);
            selectPermissionLevel(permissionLevel);
            return clickAddButtonWithHandlingItemCreationNotificationMessage(blockToReturn);
        }
    }
}