package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsuserdetailspage.panels.identitiespane.paneblocks.localidentityblock;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.TextInput;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsuserdetailspage.CDSUserDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsuserdetailspage.panels.identitiespane.IdentitiesPane;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsuserdetailspage.panels.identitiespane.paneblocks.BaseIdentitiesPanelBlock;
import com.xyleme.bravais.web.pages.cds.functionalmobules.additemform.BaseAddItemForm;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.xyleme.bravais.BaseTest.staticSleep;

/**
 * Implementation of Local Identity block without added local identity.
 */
public class LocalIdentityBlockWithNoIdentityAdded extends BaseIdentitiesPanelBlock {

    public LocalIdentityBlockWithNoIdentityAdded(WebDriver driver, By blockElementLocator) {
        super(driver, blockElementLocator);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                addLocalIdentityButton().isAvailable();
    }

    @Override
    public LocalIdentityBlockWithNoIdentityAdded load() {
        return this;
    }

    private Button addLocalIdentityButton() {
        return new Button(driver, By.xpath(blockBody().getXpathLocatorValue() +
                "//button[normalize-space()='Add Local Identity']"));
    }

    /**
     * Clicks +Add Local Identity button and returns Add Local Identity form.
     *
     * @return {@code AddLocalIdentityForm}
     */
    private AddLocalIdentityForm clickAddLocalIdentityButton() {
        addLocalIdentityButton().click();
        return new AddLocalIdentityForm(driver);
    }

    /**
     * Adds local identity to the user.
     *
     * @param username - Specifies username intended to be assigned to the user.
     * @param password - Specifies password intended to be assigned to the user.
     * @return {@code CDSUserDetailsPage}
     */
    public CDSUserDetailsPage addLocalIdentity(String username, String password) {

        if (System.getProperty("testEnvironment").startsWith("prod")) { // Workaround for caching related issue on Prod environments.
            staticSleep(5);
        }
        AddLocalIdentityForm addLocalIdentityForm = clickAddLocalIdentityButton();
        addLocalIdentityForm.fillOutAndSubmitForm(username, password);
        new IdentitiesPane(driver).getLocalIdentityBlockWithAddedIdentity().waitUntilAvailable();
        return new CDSUserDetailsPage(driver);
    }

    /**
     * Implementation of Add Local Identity form which appears after clicking +Add Local Identity button in the block.
     */
    private class AddLocalIdentityForm extends BaseAddItemForm {

        AddLocalIdentityForm(WebDriver driver) {
            super(driver);
            this.waitUntilAvailable();
        }

        @Override
        public boolean isAvailable() {
            return super.isAvailable() &&
                    usernameInputField().isAvailable() &&
                    passwordInputField().isAvailable() &&
                    reTypePasswordInputField().isAvailable();
        }

        @Override
        protected Element parentFormElement() {
            return new Element(driver, By.xpath("//form[@name='createIdentityForm']"));
        }

        private TextInput usernameInputField() {
            return new TextInput(driver, By.id("identity-name"));
        }

        private TextInput passwordInputField() {
            return new TextInput(driver, By.id("identity-password"));
        }

        private TextInput reTypePasswordInputField() {
            return new TextInput(driver, By.id("identity-confirm-password"));
        }

        /**
         * Enters specified username into the respective input field.
         *
         * @param username - Specifies the username intended to be entered
         * @return {@code AddLocalIdentityForm}
         */
        private AddLocalIdentityForm enterUsername(String username) {
            fillOutInputField(usernameInputField(), username);
            return this;
        }

        /**
         * Enters specified password into the respective input field.
         *
         * @param password - Specifies password intended to be entered
         * @return {@code AddLocalIdentityForm}
         */
        private AddLocalIdentityForm enterPassword(String password) {
            fillOutInputField(passwordInputField(), password);
            return this;
        }

        /**
         * Enters specified password into the password confirmation input field.
         *
         * @param password - Specifies the password intended to be entered
         * @return {@code AddLocalIdentityForm}
         */
        private AddLocalIdentityForm confirmPassword(String password) {
            fillOutInputField(reTypePasswordInputField(), password);
            return this;
        }

        /**
         * Fills out the form with specified data and clicks Add button.
         *
         * @param username - Specifies username intended to be entered into the respective form input field
         * @param password - Specifies password intended to be entered into the respective form input fields
         * @return {@code CDSUserDetailsPage}
         */
        CDSUserDetailsPage fillOutAndSubmitForm(String username, String password) {
            enterUsername(username);
            enterPassword(password);
            confirmPassword(password);
            return clickAddButton(CDSUserDetailsPage.class);
        }
    }
}