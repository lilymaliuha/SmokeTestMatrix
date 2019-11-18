package com.xyleme.bravais.web.pages.cds.cdsadminportalpages;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.TextInput;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnUsersPage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.TableOnUsersPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsuserdetailspage.CDSUserDetailsPage;
import com.xyleme.bravais.web.pages.cds.functionalmobules.additemform.BaseAddItemForm;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of CDS Users page.
 */
public class CDSUsersPage extends BaseCDSPageWithDataTableAndBulkChangesDropDown {

    public CDSUsersPage(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                addUserButton().isAvailable();
    }

    private Button addUserButton() {
        return new Button(driver, By.id("add-user-button"));
    }

    /**
     * Filters out users available in the Users table by full name of the user and returns the first table row object in
     * case it matches the expected user.
     *
     * @param fullUserName - Specifies full name of the user expected to be returned (filtering criteria)
     * @param fullMatch    - Specifies filtering type (true - full match / false - part match)
     * @return {@code RowOfTableOnUsersPage}
     */
    public RowOfTableOnUsersPage getFilteredUser(String fullUserName, boolean fullMatch) {
        enterQueryIntoFilterInputField(fullUserName);
        RowOfTableOnUsersPage firstTableRowAfterFiltering = getDataTable().getRow(0);
        String firstName = firstTableRowAfterFiltering.getFirstName();
        String lastName = firstTableRowAfterFiltering.getLastUserName();
        String fullNameOfFilteredUser = firstName + " " + lastName;
        boolean condition = fullMatch ? fullNameOfFilteredUser.equals(fullUserName) :
                fullNameOfFilteredUser.startsWith(fullUserName);

        if (condition) {
            return firstTableRowAfterFiltering;
        } else {
            throw new RuntimeException("Table on Users page doesn't contain user with name '" + fullUserName + "'!");
        }
    }

    /**
     * Checks if specified user is available in the Users table.
     *
     * @param fullUserName - Specifies full name of the user expected to be returned (filtering criteria)
     * @param fullMatch    - Specifies filtering type (true - full match / false - part match)
     * @return {@code boolean}
     */
    public boolean isUserInTable(String fullUserName, boolean fullMatch) {
        int resultIndex = -1;

        try {
            getFilteredUser(fullUserName, fullMatch);

            if (getDataTable().getNumberOfTableRows() > 0) {
                resultIndex = 1;
            }
        } catch (RuntimeException e) {

            if (e.getMessage().startsWith("Table on Users page doesn't contain user with name")) {
                resultIndex = 0;
            }
        }

        return resultIndex > 0;
    }

    /**
     * Deletes the user, name of which was attempted to be updated - checks if the data table on the Users page
     * contains the user with the updated name, if so - deletes the user. If the table doesn't contain the user with
     * the updated name, the method searches for the user with the original name and deletes him/her.
     *
     * @param originalFullUserName - Specifies original full name of the user
     * @param updatedFullUserName  - Specified updated full name of the user
     * @return {@code CDSUsersPage}
     */
    public CDSUsersPage deleteUserNameOfWhichWasAttemptedToBeUpdated(String originalFullUserName, String updatedFullUserName) {
        RowOfTableOnUsersPage user;

        try {
            user = getFilteredUser(updatedFullUserName, true);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("The table is empty")) {
                user = getFilteredUser(originalFullUserName, true);
            } else {
                throw e;
            }
        }
        user.deleteUser();
        return this;
    }

    /**
     * Clicks +Add User button and returns Add User form.
     *
     * @return {@code AddUserForm}
     */
    private AddUserForm clickAddUserButton() {
        addUserButton().click();
        return new AddUserForm(driver);
    }

    /**
     * Creates a new user with specified parameters.
     *
     * @param firstName - Specifies first name of the user intended to be created
     * @param lastName  - Specifies last name of the user intended to be created
     * @param email     - Specifies email of the user intended to be created
     * @return {@code CDSUserDetailsPage}
     */
    public CDSUserDetailsPage createNewUser(String firstName, String lastName, String email) {
        AddUserForm addUserForm = clickAddUserButton();
        return addUserForm.fillOutAndSubmitForm(firstName, lastName, email);
    }

    /**
     * Gets the data table available on the page.
     *
     * @return {@code TableOnUsersPage}
     */
    @Override
    public TableOnUsersPage getDataTable() {
        return new TableOnUsersPage(driver);
    }

    /**
     * Implementation of Add User form which appears after clicking +Add User button.
     */
    private class AddUserForm extends BaseAddItemForm {

        AddUserForm(WebDriver driver) {
            super(driver);
            this.waitUntilAvailable();
        }

        @Override
        public boolean isAvailable() {
            return super.isAvailable() &&
                    firstNameInputField().isAvailable() &&
                    lastNameInputField().isAvailable() &&
                    emailInputField().isAvailable();
        }

        @Override
        protected Element parentFormElement() {
            return new Element(driver, By.xpath("//form[@name='createUserForm']"));
        }

        private TextInput firstNameInputField() {
            return new TextInput(driver, By.id("new-user-first-name"));
        }

        private TextInput lastNameInputField() {
            return new TextInput(driver, By.id("new-user-last-name"));
        }

        private TextInput emailInputField() {
            return new TextInput(driver, By.id("new-user-email"));
        }

        /**
         * Enters first name of the user into the respective input field.
         *
         * @param firstName - Specifies first name of the user intended to be created
         * @return {@code AddUserForm}
         */
        private AddUserForm enterFirstName(String firstName) {
            fillOutInputField(firstNameInputField(), firstName);
            return this;
        }

        /**
         * Enters last name of the user into the respective input field.
         *
         * @param lastName - Specifies last name of the user intended to be created
         * @return {@code AddUserForm}
         */
        private AddUserForm enterLastName(String lastName) {
            fillOutInputField(lastNameInputField(), lastName);
            return this;
        }

        /**
         * Enters email of the user into the respective input field.
         *
         * @param email - Specifies email of the user intended to be created
         * @return {@code AddUserForm}
         */
        private AddUserForm enterEmail(String email) {
            fillOutInputField(emailInputField(), email);
            return this;
        }

        /**
         * Fills out the form with specified data and clicks Add button.
         *
         * @param firstName - Specifies first name of the user intended to be created
         * @param lastName  - Specifies last name of the user intended to be created
         * @param email     - Specifies email of the user intended to be created
         * @return {@code CDSUserDetailsPage}
         */
        CDSUserDetailsPage fillOutAndSubmitForm(String firstName, String lastName, String email) {
            enterFirstName(firstName);
            enterLastName(lastName);
            enterEmail(email);
            return clickAddButton(CDSUserDetailsPage.class);
        }
    }
}