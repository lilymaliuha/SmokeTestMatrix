package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsloginpage;

import com.xyleme.bravais.utils.Emails;
import com.xyleme.bravais.web.WebPage;
import com.xyleme.bravais.web.elements.LabelText;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdshomepage.CDSHomePage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsloginpage.adfsloginpage.ADFSLoginPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsloginpage.pageforms.ForgotPasswordForm;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsloginpage.pageforms.ResetPasswordForm;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsloginpage.pageforms.SignInForm;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import javax.mail.Message;

import java.util.Arrays;
import java.util.List;

import static com.xyleme.bravais.BaseTest.staticSleep;

/**
 * Implementation of CDS Login page.
 */
public class CDSLoginPage extends WebPage<CDSLoginPage> {

    public CDSLoginPage(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return getSignInForm().isAvailable() &&
                footerLabelWithBuildNumber().isAvailable();
    }

    @Override
    public CDSLoginPage load() {
        driver.get(ENVIRONMENT.env.get("CDS_URL"));
        getSignInForm().waitUntilAvailable();
        return this;
    }

    /**
     * Gets 'Sign In' form.
     *
     * @return {@code SignInForm}
     */
    private SignInForm getSignInForm() {
        return new SignInForm(driver);
    }

    /**
     * Checks if Username input field contains any input.

     * @return {@code boolean}
     */
    public boolean isUsernameInputFieldEmpty() {
        return getSignInForm().isUsernameInputFieldEmpty();
    }

    /**
     * Checks if Password input field contains any input.
     *
     * @return {@code boolean}
     */
    public boolean isPasswordInputFieldEmpty() {
        return getSignInForm().isPasswordInputFieldEmpty();
    }

    /**
     * Checks whether the Password input field is of type 'password' (if so, it means that the entered password should
     * be masked).
     *
     * @return {@code boolean}
     */
    public boolean isPasswordInputFieldOfTypePassword() {
        return getSignInForm().isPasswordInputFieldOfTypePassword();
    }

    /**
     * Clears 'Username' input field.
     *
     * @return {@code SignInForm}
     */
    public SignInForm clearUsernameInputField() {
        return getSignInForm().clearUsernameInputField();
    }

    /**
     * Clears 'Username' input field and enters specified value into it.
     *
     * @param username - Specifies username value intended to be entered
     * @return {@code SignInForm}
     */
    public SignInForm enterUsername(String username) {
        return getSignInForm().enterUsername(username);
    }

    /**
     * Clears 'Password' input field and enters specified value into it.
     *
     * @param password - Specifies password value intended to be entered
     * @return {@code SignInForm}
     */
    public SignInForm enterPassword(String password) {
        return getSignInForm().enterPassword(password);
    }

    /**
     * Enters specified username and password into respective input fields.
     *
     * @param username - Specifies username
     * @param password - Specifies password
     * @return {@code SignInForm}
     */
    public SignInForm enterUsernameAndPassword(String username, String password) {
        return getSignInForm().enterUsernameAndPassword(username, password);
    }

    /**
     * Clicks 'Sign In' button for submitting valid credentials.
     *
     * @return {@code CDSHomePage}
     */
    public CDSHomePage clickSignInButtonForSubmittingValidCredentials() {
        return getSignInForm().clickSignInButtonForSubmittingValidCredentials();
    }

    /**
     * Logs in to CDS with a specified user.
     *
     * @param username - Specifies username of the user intended to be logged in.
     * @param password - Specifies password of the user intended to be logged in.
     * @return {@code CDSHomePage}
     */
    public CDSHomePage logInToCDSWithUser(String username, String password) {
        return getSignInForm().logInToCDSWithUser(username, password);
    }

    /**
     * Logs in to CDS with a default CDS user created for CDS Smoke testing.
     *
     * @return {@code CDSHomePage}
     */
    public CDSHomePage logInToCDS() {
        return logInToCDSWithUser(ENVIRONMENT.env.get("username"), ENVIRONMENT.env.get("password"));
    }

    /**
     * Logs in to CDS with a specified user after resetting user password.
     *
     * @param username - Specifies username of the user intended to be logged in.
     * @param password - Specifies password of the user intended to be logged in.
     * @return {@code CDSHomePage}
     */
    public CDSHomePage loginToCDSWithUserAfterPasswordReset(String username, String password) {
        getSignInForm().enterUserCredentialsAndClickSignInButton(username, password);
        new LabelText(driver, By.xpath("//div[@class='form-general']/p[text()='Sign-in successful']")).waitUntilAvailable();
        driver.get(ENVIRONMENT.env.get("CDS_URL"));
        return new CDSHomePage(driver);
    }

    /**
     * Performs invalid login attempt (using invalid user credentials).
     *
     * @param username - Specifies username
     * @param password - Specifies password
     * @return {@code SignInForm}
     */
    public SignInForm performInvalidLoginAttempt(String username, String password) {
        return getSignInForm().performInvalidLoginAttempt(username, password);
    }

    /**
     * Gets text of login error message.
     *
     * @return {@code String}
     */
    public String getLoginErrorMessageText() {
        return getSignInForm().getLoginErrorMessageText();
    }

    /**
     * Checks whether 'Sign In with CAS' link is clickable.
     *
     * @return {@code boolean}
     */
    public boolean isLoginWithCASLinkClickable() {
        return getSignInForm().isLoginWithCASLinkClickable();
    }

    /**
     * Clicks 'Sign In with CAS' link and returns ADFS login page.
     *
     * @return {@code ADFSLoginPage}
     */
    public ADFSLoginPage clickSignInWithCASLink() {
        return getSignInForm().clickSignInWithCASLink();
    }

    /**
     * Clicks 'Can't get access to your account?' link and returns Forgot Password form.
     *
     * @return {@code ForgotPasswordForm}
     */
    private ForgotPasswordForm clickCantGetAccessToYourAccountLink() {
        return getSignInForm().clickCantGetAccessToYourAccountLink();
    }

    /**
     * Navigates to specified URL for password resetting and returns 'Reset Password' form.
     *
     * @param urlForPasswordResetting - Specifies the password resetting URL
     * @return {@code ResetPasswordForm}
     */
    private ResetPasswordForm getResetPasswordForm(String urlForPasswordResetting) {
        driver.get(urlForPasswordResetting);
        return new ResetPasswordForm(driver);
    }

    /**
     * Retrieves URL for password resetting from specified message.
     *
     * @param message - Specifies the message the URL is expected to be retrieved from
     * @return {@code String}
     */
    private String retrievePasswordResettingURLFromMessage(Message message) {
        Emails emails = new Emails();
        String urlToReturn = null;
        List<String> emailBody = Arrays.asList(emails.getEmailBody(message));

        if (emailBody.contains("To reset your password please follow this link:")) {

            for (String emailBodyString : emailBody) {
                if (emailBodyString.startsWith(ENVIRONMENT.env.get("CDS_CORE_URL") + "/login/")) {
//                if (emailBodyString.startsWith("https://qa1-eu.bravais.com" + "/login/")) {
                    urlToReturn = encodeStringToUTF_8(emailBodyString);
                    break;
                }
            }

            if (urlToReturn != null) {
                return urlToReturn;
            } else {
                throw new RuntimeException("Specified email message doesn't contain URL for password resetting!");
            }
        } else {
            throw new RuntimeException("Specified message does not correspond to the one which is expected to " +
                    "contain URL for password resetting.\n --> Message body: " + emailBody);
        }
    }

    /**
     * Resets user password via 'Forgot your Password?' form.
     *
     * @param username    - Specifies username of the user the password is intended to be reset for
     * @param newPassword - Specifies the new password intended to be set for the user
     * @return {@code CDSLoginPage}
     */
    public CDSLoginPage resetPasswordForUser(String username, String newPassword) {
        Emails emails = new Emails();
        emails.deleteMessages();
        ForgotPasswordForm forgotPasswordForm = clickCantGetAccessToYourAccountLink();
        forgotPasswordForm.fillOutAndSubmitForm(username);
        staticSleep(5); // Time for email sending.
        Message message = emails.getMostRecentEmail();
        String urlForPasswordResetting = retrievePasswordResettingURLFromMessage(message);
        driver.get(urlForPasswordResetting);
        ResetPasswordForm resetPasswordForm = getResetPasswordForm(urlForPasswordResetting);
        return resetPasswordForm.fillOutAndSubmitForm(newPassword, newPassword);
    }
}