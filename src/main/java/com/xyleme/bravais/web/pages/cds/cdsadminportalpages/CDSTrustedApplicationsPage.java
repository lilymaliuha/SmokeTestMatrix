package com.xyleme.bravais.web.pages.cds.cdsadminportalpages;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.Select;
import com.xyleme.bravais.web.elements.TextInput;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnTrustedApplicationPage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.TableOnTrustedApplicationPage;
import com.xyleme.bravais.web.pages.cds.functionalmobules.additemform.BaseAddItemForm;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of CDS Trusted Applications page.
 */
public class CDSTrustedApplicationsPage extends BaseCDSPageWithDataTableAndBulkChangesDropDown {

    public CDSTrustedApplicationsPage(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                allApplicationsDropDown().isAvailable() &&
                addApplicationButton().isAvailable();
    }

    private Select allApplicationsDropDown() {
        return new Select(driver, By.xpath("//option[text()='All applications']/parent::select"));
    }

    private Button addApplicationButton() {
        return new Button(driver, By.xpath("//button[normalize-space()='Add Application']"));
    }

    /**
     * Filters out applications available in the Trusted Applications table by name and returns the first table row
     * object in case the application the row belongs to matches the expected application.
     *
     * @param applicationName - Specifies the name of the application expected to be returned (the table is filtered out
     *                        by)
     * @param fullMatch       - Specifies type of the filtering (true - full match / false - part match)
     * @return {@code RowOfTableOnTrustedApplicationPage}
     */
    public RowOfTableOnTrustedApplicationPage getFilteredApplication(String applicationName, boolean fullMatch) {
        enterQueryIntoFilterInputField(applicationName);
        RowOfTableOnTrustedApplicationPage firstTableRowAfterFiltering = getDataTable().getRow(0);
        String appName = firstTableRowAfterFiltering.getApplicationName();
        boolean condition = fullMatch ? appName.equals(applicationName) : appName.startsWith(applicationName);
        if (condition) {
            return firstTableRowAfterFiltering;
        } else {
            throw new RuntimeException("Table on Trusted Applications page doesn't contain application with name '" +
                    applicationName + "'!");
        }
    }

    /**
     * Clicks +Add Application button and returns Add Application form.
     *
     * @return {@code AddApplicationForm}
     */
    private AddApplicationForm clickAddApplicationButton() {
        addApplicationButton().waitUntilAvailable().click();
        return new AddApplicationForm(driver);
    }

    /**
     * Creates new Application - clicks Add Application button, fills out Add Application form and clicks Add button.
     *
     * @param applicationName - Specifies application name
     * @param defaultUser     - Specifies default user
     * @param validityTerm    - Specifies application validity term
     * @return {@code CDSTrustedApplicationsPage}
     */
    public CDSTrustedApplicationsPage createNewApplication(String applicationName,  String defaultUser, int validityTerm) {
        AddApplicationForm addApplicationForm = clickAddApplicationButton();
        return addApplicationForm.fillOutAndSubmitForm(applicationName, defaultUser, validityTerm);
    }

    /**
     * Gets the data table available on the page.
     *
     * @return {@code TableOnTrustedApplicationPage}
     */
    @Override
    public TableOnTrustedApplicationPage getDataTable() {
        return new TableOnTrustedApplicationPage(driver);
    }

    /**
     * Implementation of Add Application form which appears after clicking +Add Application button.
     */
    private class AddApplicationForm extends BaseAddItemForm {

        AddApplicationForm(WebDriver driver) {
            super(driver);
            this.waitUntilAvailable();
        }

        @Override
        public boolean isAvailable() {
            return super.isAvailable() &&
                    nameInputField().isAvailable() &&
                    defaultUserInputField().isAvailable() &&
                    validityInputField().isAvailable();
        }

        @Override
        protected Element parentFormElement() {
            return new Element(driver, By.xpath("//form[@name='addApplicationForm']"));
        }

        private TextInput nameInputField() {
            return new TextInput(driver, By.id("new-application-name"));
        }

        private TextInput defaultUserInputField() {
            return new TextInput(driver, By.id("new-application-default-user"));
        }

        private TextInput validityInputField() {
            return new TextInput(driver, By.id("new-application-valid"));
        }

        /**
         * Enters specified text value into Name input field.
         *
         * @param applicationName - Specifies name of the application (text value) intended to be entered into the input
         *                        field
         */
        private void enterApplicationName(String applicationName) {
            fillOutInputField(nameInputField(), applicationName);
        }

        /**
         * Enters specified text value into Default User input field.
         *
         * @param fullUserName - Specifies full name of the user (text value) intended to be entered into the input field
         */
        private void enterDefaultUser(String fullUserName) {
            String truncatedUserName = fullUserName.substring(0, fullUserName.length() - 3); // ToDo: Remove this line after the issue is fixed.
            fillOutInputField(defaultUserInputField(), truncatedUserName); // ToDo: Workaround for issue #<TBD>. Use full value (fullUserName) after the issue is fixed.
            selectSuggestedInputFieldMatch(fullUserName);
        }

        /**
         * Enters specified int value into Valid input field.
         *
         * @param validityTerm - Specifies validity term (int value) intended to be entered into the input filed.
         */
        private void enterValidityTerm(int validityTerm) {
            fillOutInputField(validityInputField(), String.valueOf(validityTerm));
        }

        /**
         * Fills out the form and clicks Add button.
         *
         * @param applicationName - Specifies name of the application intended to be created
         * @param defaultUser     - Specifies the default application user
         * @param validityTerm    - Specifies application validity term (default value is 365)
         * @return {@code CDSTrustedApplicationsPage}
         */
        CDSTrustedApplicationsPage fillOutAndSubmitForm(String applicationName, String defaultUser, int validityTerm) {
            enterApplicationName(applicationName);
            enterDefaultUser(defaultUser);
            enterValidityTerm(validityTerm);
            return clickAddButton(CDSTrustedApplicationsPage.class);
        }
    }
}