package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsuserdetailspage.panels.identitiespane.paneblocks.localidentityblock;

import com.xyleme.bravais.web.WebPage;
import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.TextInput;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsuserdetailspage.CDSUserDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsuserdetailspage.panels.identitiespane.paneblocks.BaseIdentitiesPanelBlock;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Implementation of Local Identity block with already added identity.
 */
public class LocalIdentityBlockWithAddedIdentity extends BaseIdentitiesPanelBlock {

    public LocalIdentityBlockWithAddedIdentity(WebDriver driver, By blockElementLocator) {
        super(driver, blockElementLocator);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                usernameValueLabel().isAvailable() &&
                changePasswordButton().isAvailable();
    }

    @Override
    public LocalIdentityBlockWithAddedIdentity load() {
        return this;
    }

    private Element usernameValueLabel() { // Element which needs to be hovered over and clicked to activate input field.
        return new Element(driver, By.xpath(blockBody().getXpathLocatorValue() +
                "//div[contains(@class, 'identity-user-name')]/div[contains(@class, 'editable')]"));
    }

    private Button changePasswordButton() {
        return new Button(driver, By.xpath(blockBody().getXpathLocatorValue() +
                "//button[normalize-space()='Change Password']"));
    }

    /**
     * Clicks Change Password button and returns Change Password form.
     *
     * @return {@code ChangePasswordForm}
     */
    private ChangePasswordForm clickChangePasswordButton() {
        changePasswordButton().click();
        return new ChangePasswordForm(driver);
    }

    /**
     * Clicks Change Password button, fills out Change Password form and submits the changes.
     *
     * @param newPassword - Specifies the new password intended to be set
     * @return {@code CDSUserDetailsPage}
     */
    public CDSUserDetailsPage changePassword(String newPassword) {
        ChangePasswordForm changePasswordForm = clickChangePasswordButton();
        return changePasswordForm.fillOutAndSubmitForm(newPassword);
    }

    /**
     * Implementation of Change Password form which appears after clicking Change Password button.
     */
    private class ChangePasswordForm extends WebPage<ChangePasswordForm> {

        ChangePasswordForm(WebDriver driver) {
            super(driver);
            this.waitUntilAvailable();
        }

        @Override
        public ChangePasswordForm load() {
            return this;
        }

        @Override
        public boolean isAvailable() {
            return newPasswordInputField().isAvailable() &&
                    reTypeNewPasswordInputField().isAvailable() &&
                    saveButton().isAvailable() &&
                    cancelButton().isAvailable();
        }

        private Element parentFormElement() {
            return new Element(driver, By.xpath("//form[@id='change-identity-password-form']"));
        }

        private TextInput newPasswordInputField() {
            return new TextInput(driver, By.id("identity-new-password"));
        }

        private TextInput reTypeNewPasswordInputField() {
            return new TextInput(driver, By.id("identity-confirm-new-password"));
        }

        private Button saveButton() {
            return new Button(driver, By.xpath(parentFormElement().getXpathLocatorValue() +
                    "//span[text()='Save']/parent::button"));
        }

        private Button cancelButton() {
            return new Button(driver, By.xpath(parentFormElement().getXpathLocatorValue() + "//button[text()='Cancel']"));
        }

        /**
         * Enters new password in to the New Password input field.
         *
         * @param newPassword - Specifies the new password intended to be entered into the input field.
         * @return {@code ChangePasswordForm}
         */
        private ChangePasswordForm enterNewPassword(String newPassword) {
            newPasswordInputField().clear();
            newPasswordInputField().sendKeys(newPassword);
            return this;
        }

        /**
         * Enters new password into the Re-Type New Password input field.
         *
         * @param password - Specifies the password intended to be entered into the input field.
         * @return {@code ChangePasswordForm}
         */
        private ChangePasswordForm confirmNewPassword(String password) {
            reTypeNewPasswordInputField().clear();
            reTypeNewPasswordInputField().sendKeys(password);
            return this;
        }

        /**
         * Clicks Save button.
         *
         * @return {@code CDSUserDetailsPage}
         */
        private CDSUserDetailsPage clickSaveButton() {
            saveButton().click();
            notificationMessage().waitUntilAvailable();
            new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath(notificationMessage().getXpathLocatorValue())));
            return new CDSUserDetailsPage(driver);
        }

        /**
         * Clicks Cancel button.
         *
         * @return {@code CDSUserDetailsPage}
         */
        private CDSUserDetailsPage clickCancelButton() {
            cancelButton().click();
            return new CDSUserDetailsPage(driver);
        }

        /**
         * Fills out the form and clicks Save button.
         *
         * @param newPassword - Specifies the new password intended to be set
         * @return {@code CDSUserDetailsPage}
         */
        CDSUserDetailsPage fillOutAndSubmitForm(String newPassword) {
            enterNewPassword(newPassword);
            confirmNewPassword(newPassword);
            return clickSaveButton();
        }
    }
}