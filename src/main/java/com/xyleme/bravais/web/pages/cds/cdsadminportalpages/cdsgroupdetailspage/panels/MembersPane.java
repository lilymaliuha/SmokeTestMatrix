package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsgroupdetailspage.panels;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.TextInput;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnMembersPanelOfGroupDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.TableOnMembersPanelOfGroupDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSPanelOnDetailsPage;
import com.xyleme.bravais.web.pages.cds.functionalmobules.additemform.BaseAddItemForm;
import com.xyleme.bravais.web.pages.cds.functionalmobules.filterform.FilterFormWithBulkChanges;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Members pane on Group Details page.
 */
public class MembersPane extends BaseCDSPanelOnDetailsPage {

    public MembersPane(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public MembersPane load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return getFilterFormWithBulkChanges().isAvailable() &&
                addMemberButton().isAvailable();
    }

    private Element panelContainer() {
        return new Element(driver, By.xpath("//div[@id='members-list']"));
    }

    private Button addMemberButton() {
        return new Button(driver, By.id("add-member-button"));
    }

    /**
     * Clicks +Add Member button and returns Add Member form.
     *
     * @return {@code AddMemberForm}
     */
    private AddMemberForm clickAddMemberButton() {
        addMemberButton().click();
        return new AddMemberForm(driver);
    }

    /**
     * Adds specified member to the group.
     *
     * @param memberFullName - Specifies full name of the member intended to be added
     * @return {@code MembersPane}
     */
    public MembersPane addMember(String memberFullName) {
        AddMemberForm addMemberForm = clickAddMemberButton();
        return addMemberForm.fillOutAndSubmitForm(memberFullName);
    }

    /**
     * Gets filter form with Bulk Changes drop-down available on the panel.
     *
     * @return {@code FilterFormWithBulkChanges}
     */
    private FilterFormWithBulkChanges getFilterFormWithBulkChanges() {
        return new FilterFormWithBulkChanges(driver, By.xpath("//form[@id='list-filter']"));
    }

    /**
     * Enters specified full name of the user into the 'Filter Members' input field and returns the first table row
     * available after filtering (if it belongs to the expected user).
     *
     * @param memberFullName - Specifies full name of the Group Member intended to be filtered out
     * @param fullMatch      - Specifies type of the filtering (true - full match / false - part match)
     * @return {@code RowOfTableOnMembersPanelOfGroupDetailsPage}
     */
    public RowOfTableOnMembersPanelOfGroupDetailsPage getFilteredGroupMember(String memberFullName, boolean fullMatch) {
        getFilterFormWithBulkChanges().enterQueryIntoFilterInputField(memberFullName);
        RowOfTableOnMembersPanelOfGroupDetailsPage firstFilteredRow = getDataTable().getRow(0);
        String firstName = firstFilteredRow.getFirstName();
        String lastName = firstFilteredRow.getLastName();
        String name = firstName + " " + lastName;
        boolean condition = fullMatch ? name.equals(memberFullName) : name.startsWith(memberFullName);
        if (condition) {
            return firstFilteredRow;
        } else {
            throw new RuntimeException("Group Members table doesn't contain member with name '" + memberFullName + "'.");
        }
    }

    /**
     * Gets Members table available on the panel.
     *
     * @return {@code TableOnMembersPanelOfGroupDetailsPage}
     */
    public TableOnMembersPanelOfGroupDetailsPage getDataTable() {
        return new TableOnMembersPanelOfGroupDetailsPage(driver, panelContainer());
    }

    /**
     * Implementation of Add Member form which appears after clicking +Add Member button.
     */
    private class AddMemberForm extends BaseAddItemForm {

        AddMemberForm(WebDriver driver) {
            super(driver);
            this.waitUntilAvailable();
        }

        @Override
        public boolean isAvailable() {
            return super.isAvailable() &&
                    nameInputField().isAvailable();
        }

        @Override
        protected Element parentFormElement() {
            return new Element(driver, By.xpath("//div[@id='add-member-form']/form"));
        }

        private TextInput nameInputField() {
            return new TextInput(driver, By.id("member-name"));
        }

        /**
         * Enter specified member name into the respective input field.
         *
         * @param fullUserName - Specifies name of the member intended to be entered
         */
        private void enterMemberName(String fullUserName) {
            String truncatedUserName = fullUserName.substring(0, fullUserName.length() - 3); // ToDo: Remove this line after the issue is fixed.
            fillOutInputField(nameInputField(), truncatedUserName); // ToDo: Workaround for issue #<TBD>. Use full value (fullUserName) after the issue is fixed.
            selectSuggestedInputFieldMatch(fullUserName);
        }

        /**
         * Clicks Add button.
         *
         * @return {@code MembersPane}
         */
        MembersPane clickAddButton() {
            return clickAddButtonWithHandlingItemCreationNotificationMessage(MembersPane.class);
        }

        /**
         * Fills out the form with specified data and clicks Add button.
         *
         * @param memberFullName - Specifies full name of the member intended to be added.
         * @return {@code MembersPane}
         */
        MembersPane fillOutAndSubmitForm(String memberFullName) {
            enterMemberName(memberFullName);
            return clickAddButton();
        }
    }
}