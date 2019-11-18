package com.xyleme.bravais.web.pages.cds.cdsadminportalpages;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.TextInput;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnGroupsPage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.TableOnGroupsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsgroupdetailspage.CDSGroupDetailsPage;
import com.xyleme.bravais.web.pages.cds.functionalmobules.additemform.BaseAddItemForm;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of CDS Groups page.
 */
public class CDSGroupsPage extends BaseCDSPageWithDataTableAndBulkChangesDropDown {

    public CDSGroupsPage(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                addGroupButton().isAvailable();
    }

    private Button addGroupButton() {
        return new Button(driver, By.id("add-group-button"));
    }

    /**
     * Filters out groups available in the Groups table by name and returns the first table row object in case the group
     * the row belongs to matches the expected group.
     *
     * @param groupName - Specifies the name of the group expected to be returned (filtering criteria)
     * @param fullMatch - Specifies filtering type (true - full match / false - part match)
     * @return {@code RowOfTableOnGroupsPage}
     */
    public RowOfTableOnGroupsPage getFilteredGroup(String groupName, boolean fullMatch) {
        enterQueryIntoFilterInputField(groupName);
        RowOfTableOnGroupsPage firstTableRowAfterFiltering = getDataTable().getRow(0);
        String filteredGroupName = firstTableRowAfterFiltering.getGroupName();
        boolean condition = fullMatch ? filteredGroupName.equals(groupName) : filteredGroupName.startsWith(groupName);
        if (condition) {
            return firstTableRowAfterFiltering;
        } else {
            throw new RuntimeException("Table on Groups page doesn't contain group with name '" + groupName + "'! " +
                    "Name of the first group in the list after filtering: " + filteredGroupName);
        }
    }

    /**
     * Deletes the group, name of which was attempted to be updated - checks if the data table on the Groups page
     * contains the group with the updated name, if so - deletes the group. If the table doesn't contain the group with
     * the updated name, the method searches for the group with the original name and deletes it.
     *
     * @param originalGroupName - Specifies original name of the group
     * @param updatedGroupName  - Specified updated name of the group
     * @return {@code CDSGroupsPage}
     */
    public CDSGroupsPage deleteGroupNameOfWhichWasAttemptedToBeUpdated(String originalGroupName, String updatedGroupName) {
        RowOfTableOnGroupsPage group;
        try {
            group = getFilteredGroup(updatedGroupName, true);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("The table is empty")) {
                group = getFilteredGroup(originalGroupName, true);
            } else {
                throw e;
            }
        }
        group.deleteGroup();
        return this;
    }

    /**
     * Clicks +Add Group button and returns Add Group form.
     *
     * @return {@code AddGroupForm}
     */
    private AddGroupForm clickAddGroupButton() {
        addGroupButton().click();
        return new AddGroupForm(driver);
    }

    /**
     * Clicks +Add Group button, fills out Add Group form with specified data and clicks Add button of the form.
     *
     * @param groupName        - Specifies name of the group intended to be created
     * @param groupDescription - Specifies description of the group intended to bre created
     * @return {@code CDSGroupDetailsPage}
     */
    public CDSGroupDetailsPage createNewGroup(String groupName, String groupDescription) {
        AddGroupForm addGroupForm = clickAddGroupButton();
        return addGroupForm.fillOutAndSubmitForm(groupName, groupDescription);
    }

    /**
     * Gets the data table available on the page.
     *
     * @return {@code TableOnGroupsPage}
     */
    @Override
    public TableOnGroupsPage getDataTable() {
        return new TableOnGroupsPage(driver);
    }

    /**
     * Implementation of Add Group form which appears after clicking +Add Group button.
     */
    private class AddGroupForm extends BaseAddItemForm {

        AddGroupForm(WebDriver driver) {
            super(driver);
            this.waitUntilAvailable();
        }

        @Override
        public boolean isAvailable() {
            return super.isAvailable() &&
                    nameInputField().isAvailable() &&
                    descriptionInputField().isAvailable();
        }

        @Override
        protected Element parentFormElement() {
            return new Element(driver, By.xpath("//form[@name='createGroupForm']"));
        }

        private TextInput nameInputField() {
            return new TextInput(driver, By.id("new-group-name"));
        }

        private TextInput descriptionInputField() {
            return new TextInput(driver, By.id("new-group-description"));
        }

        /**
         * Enters group name into the respective input field.
         *
         * @param groupName - Specifies name of the group intended to be created
         */
        private void enterGroupName(String groupName) {
            fillOutInputField(nameInputField(), groupName);
        }

        /**
         * Enters group description into the respective input field.
         *
         * @param groupDescription - Specifies description of the group intended to be created
         */
        private void enterGroupDescription(String groupDescription) {
            fillOutInputField(descriptionInputField(), groupDescription);
        }

        /**
         * Fills out the form with specified data and clicks Add button.
         *
         * @param groupName        - Specifies name of the group intended tp be created
         * @param groupDescription - Specifies description of the group intended to be created
         * @return {@code CDSGroupDetailsPage}
         */
        CDSGroupDetailsPage fillOutAndSubmitForm(String groupName, String groupDescription) {
            enterGroupName(groupName);
            enterGroupDescription(groupDescription);
            return clickAddButton(CDSGroupDetailsPage.class);
        }
    }
}