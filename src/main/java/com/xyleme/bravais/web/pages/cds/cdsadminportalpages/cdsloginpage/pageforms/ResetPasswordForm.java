package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsloginpage.pageforms;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.TextInput;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsloginpage.CDSLoginPage;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of 'Reset Password' form which appears after submitting username in 'Forgot your Password?' form.
 */
public class ResetPasswordForm extends BaseLoginPageForm {

    public ResetPasswordForm(WebDriver driver) {
        super(driver, "Reset Password");
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                instructionMessageLabel().isAvailable() &&
                passwordInputField().isAvailable() &&
                confirmPasswordInputField().isAvailable() &&
                updatePasswordButton().isAvailable();
    }

    private TextInput passwordInputField() {
        return formInputField("password");
    }

    private TextInput confirmPasswordInputField() {
        return formInputField("confirm_password");
    }

    private Button updatePasswordButton() {
        return formButton("Update Password");
    }

    /**
     * Enters specified password into the Password input field.
     *
     * @param password - Specifies the password intended to be entered
     * @return {@code ResetPasswordForm}
     */
    private ResetPasswordForm enterPassword(String password) {
        passwordInputField().clear();
        passwordInputField().sendKeys(password);
        return this;
    }

    /**
     * Enters specified password in the Confirm Password input field.
     *
     * @param password - Specifies the password intended to be entered
     * @return {@code ResetPasswordForm}
     */
    private ResetPasswordForm confirmPassword(String password) {
        confirmPasswordInputField().clear();
        confirmPasswordInputField().sendKeys(password);
        return this;
    }

    /**
     * Clicks the Update Password button.
     *
     * @return {@code CDSLoginPage}
     */
    private CDSLoginPage clickUpdatePasswordButton() {
        updatePasswordButton().click();
        return new CDSLoginPage(driver);
    }

    /**
     * Fills out the form with specified passwords, clicks 'Update Password' button, and returns default CDS Login page
     * (with 'Sign In' form).
     *
     * @param password                - Specifies the password intended to be entered into the Password input field
     * @param passwordForConfirmation - Specifies the password intended to be entered into the Confirm Password input field
     * @return {@code CDSLoginPage}
     */
    public CDSLoginPage fillOutAndSubmitForm(String password, String passwordForConfirmation) {
        enterPassword(password);
        confirmPassword(passwordForConfirmation);
        return clickUpdatePasswordButton();
    }
}