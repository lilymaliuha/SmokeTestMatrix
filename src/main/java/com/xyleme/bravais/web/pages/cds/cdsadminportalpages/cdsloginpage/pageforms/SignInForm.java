package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsloginpage.pageforms;

import com.xyleme.bravais.web.elements.*;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdshomepage.CDSHomePage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsloginpage.adfsloginpage.ADFSLoginPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsloginpage.adfsloginpage.ADFSLoginPageInNonProdEnv;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsloginpage.adfsloginpage.ADFSLoginPageInProdEnv;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of 'Sign In' form available on CDS Login page.
 */
public class SignInForm extends BaseLoginPageForm {

    public SignInForm(WebDriver driver) {
        super(driver, "Sign In");
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                usernameInputField().isAvailable() &&
                passwordInputField().isAvailable() &&
                cantGetAccessToYourAccountLink().isAvailable() &&
                signInWithCASLink().isAvailable() &&
                signInButton().isAvailable();
    }

    private TextInput usernameInputField() {
        return formInputField("username");
    }

    private TextInput passwordInputField() {
        return formInputField(("password"));
    }

    private Link cantGetAccessToYourAccountLink() {
        return formLink("Can't get access to your account?");
    }

    private Link signInWithCASLink() {
        return formLink("Sign In with CAS");
    }

    private Button signInButton() {
        return formButton("Sign In");
    }

    private LabelText loginErrorMessageElement() {
        return new LabelText(driver, By.xpath("//form//div[contains(@class, 'alert-danger')]"));
    }

    /**
     * Checks if Username input field contains any input.
     *
     * @return {@code boolean}
     */
    public boolean isUsernameInputFieldEmpty() {
        return getInputFieldValue("username").isEmpty();
    }

    /**
     * Checks if Password input field contains any input.
     *
     * @return {@code boolean}
     */
    public boolean isPasswordInputFieldEmpty() {
        return getInputFieldValue("password").isEmpty();
    }

    /**
     * Checks whether the Password input field is of type 'password' (if so, it means that the entered password should
     * be masked).
     *
     * @return {@code boolean}
     */
    public boolean isPasswordInputFieldOfTypePassword() {
        return passwordInputField().getAttribute("type").equals("password");
    }

    /**
     * Clears 'Username' input field.
     *
     * @return {@code SignInForm}
     */
    public SignInForm clearUsernameInputField() {

        if (!isUsernameInputFieldEmpty()) {
            usernameInputField().clear();
        }
        return this;
    }

    /**
     * Clears 'Password' input field.
     *
     * @return {@code SignInForm}
     */
    private SignInForm clearPasswordInputField() {

        if (!isPasswordInputFieldEmpty()) {
            passwordInputField().clear();
        }
        return this;
    }

    /**
     * Clears 'Username' input field and enters specified value into it.
     *
     * @param username - Specifies username value intended to be entered
     * @return {@code SignInForm}
     */
    public SignInForm enterUsername(String username) {
        clearUsernameInputField();
        usernameInputField().sendKeys(username);
        return this;
    }

    /**
     * Clears 'Password' input field and enters specified value into it.
     *
     * @param password - Specifies password value intended to be entered
     * @return {@code SignInForm}
     */
    public SignInForm enterPassword(String password) {
        clearPasswordInputField();
        passwordInputField().sendKeys(password);
        return this;
    }

    /**
     * Enters specified username and password into respective input fields.
     *
     * @param username - Specifies username
     * @param password - Specifies password
     * @return {@code SignInForm}
     */
    public SignInForm enterUsernameAndPassword(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        return this;
    }

    /**
     * Clicks 'Sign In' button for submitting valid credentials.
     *
     * @return {@code CDSHomePage}
     */
    public CDSHomePage clickSignInButtonForSubmittingValidCredentials() {
        signInButton().click();
        return new CDSHomePage(driver);
    }

    /**
     * Tries to log in to CDS with the specified user credentials.
     *
     * @param username - Specifies username
     * @param password - Specifies password
     */
    public void enterUserCredentialsAndClickSignInButton(String username, String password) {
        enterUsernameAndPassword(username, password);
        signInButton().click();
    }

    /**
     * Logs in to CDS with a specified user.
     *
     * @param username - Specifies username of the user intended to be logged in.
     * @param password - Specifies password of the user intended to be logged in.
     * @return {@code CDSHomePage}
     */
    public CDSHomePage logInToCDSWithUser(String username, String password) {
        enterUserCredentialsAndClickSignInButton(username, password);
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
        enterUserCredentialsAndClickSignInButton(username, password);

        if (this.isAvailable()) {
            return this;
        } else {
            throw new RuntimeException("Sign In form on CDS Login page is not available after invalid login attempt!");
        }
    }

    /**
     * Gets text of login error message.
     *
     * @return {@code String}
     */
    public String getLoginErrorMessageText() {

        if (loginErrorMessageElement().isAvailable()) {
            return loginErrorMessageElement().getText();
        } else {
            throw new RuntimeException("Login error message has not been displayed on the Login page, " +
                    "message text cannot be retrieved.");
        }
    }

    /**
     * Checks whether 'Sign In with CAS' link is clickable.
     *
     * @return {@code boolean}
     */
    public boolean isLoginWithCASLinkClickable() {
        return !driver.findElement(By.xpath(signInWithCASLink().getXpathLocatorValue() +
                "/parent::div[contains(@class, 'link')]")).getAttribute("class").endsWith("disabled");
    }

    /**
     * Clicks 'Sign In with CAS' link.
     *
     * @return {@code ADFSLoginPage}
     */
    public ADFSLoginPage clickSignInWithCASLink() {

        if (signInWithCASLink().isAvailable()) {
            signInWithCASLink().click();

            if (ENVIRONMENT.env.get("prodLikeCASPage").equals("no")) {
                return new ADFSLoginPageInNonProdEnv(driver);
            } else {
                return new ADFSLoginPageInProdEnv(driver);
            }
        } else {
            throw new RuntimeException("'Sign In with CAS' link is not available on CDS Login page!");
        }
    }

    /**
     * Clicks 'Can't get access to your account?' link and returns Forgot Password form.
     *
     * @return {@code ForgotPasswordForm}
     */
    public ForgotPasswordForm clickCantGetAccessToYourAccountLink() {
        cantGetAccessToYourAccountLink().click();
        return new ForgotPasswordForm(driver);
    }
}