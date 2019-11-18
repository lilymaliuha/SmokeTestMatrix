package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsloginpage.pageforms;

import com.xyleme.bravais.web.WebPage;
import com.xyleme.bravais.web.elements.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of 'Forgot your Password?' form which appears on CDS Login page after clicking
 * 'Can't get access to your account?' link on CDS Login page.
 */
public class ForgotPasswordForm extends BaseLoginPageForm {

    ForgotPasswordForm(WebDriver driver) {
        super(driver, "Forgot your password?");
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                instructionMessageLabel().isAvailable() &&
                userNameInputField().isAvailable() &&
                cancelLink().isAvailable() &&
                submitButton().isAvailable();
    }

    private TextInput userNameInputField() {
        return formInputField("username");
    }

    private Link cancelLink() {
        return formLink("Cancel");
    }

    private Button submitButton() {
        return formButton("Submit");
    }

    /**
     * Gets instruction message displayed on the form.
     *
     * @return {@code String}
     */
    public String getInstructionMessage() {
        return instructionMessageLabel().getText();
    }

    /**
     * Enters specific username into the Username text input field.
     *
     * @param username - Specifies the username intended to be entered
     * @return {@code ForgotPasswordForm}
     */
    private ForgotPasswordForm enterUsername(String username) {
        userNameInputField().clear();
        userNameInputField().sendKeys(username);
        return this;
    }

    /**
     * Clicks Submit button and returns password reset request conformation form.
     *
     * @return {@code PasswordResetRequestConfirmationForm}
     */
    private PasswordResetRequestConfirmationForm clickSubmitButton() {
        submitButton().click();
        return new PasswordResetRequestConfirmationForm(driver);
    }

    /**
     * Fills out the Username field with specified username, clicks Submit button, and returns password reset request
     * conformation form.
     *
     * @param username - Specifies the username intended to be entered
     * @return {@code PasswordResetRequestConfirmationForm}
     */
    public PasswordResetRequestConfirmationForm fillOutAndSubmitForm(String username) {
        enterUsername(username);
        return clickSubmitButton();
    }

    /**
     * Implementation password reset request conformation form.
     */
    private class PasswordResetRequestConfirmationForm extends WebPage<PasswordResetRequestConfirmationForm> {

        PasswordResetRequestConfirmationForm(WebDriver driver) {
            super(driver);
            this.waitUntilAvailable();
        }

        @Override
        public boolean isAvailable() {
            return confirmationMessage().isAvailable() &&
                    confirmationMessage().getText().equals("Your password reset request has been processed. " +
                            "Check your mailbox please.");
        }

        @Override
        public PasswordResetRequestConfirmationForm load() {
            return this;
        }

        private Element formBody() {
            return new Element(driver, By.xpath("//h3[text()='Forgot your password?']/parent::div[@class='form-general']"));
        }

        private LabelText confirmationMessage() {
            return new LabelText(driver, By.xpath(formBody().getXpathLocatorValue() + "/p"));
        }
    }
}