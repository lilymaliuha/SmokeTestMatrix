package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsloginpage.adfsloginpage;

import com.xyleme.bravais.web.WebPage;
import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.LabelText;
import com.xyleme.bravais.web.elements.TextInput;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of ADFS Login page which appears after clicking "Sign In with CAS" link on CDS Login page in non
 * Production environment.
 */
public class ADFSLoginPageInNonProdEnv extends WebPage<ADFSLoginPageInNonProdEnv> implements ADFSLoginPage {

    public ADFSLoginPageInNonProdEnv(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public ADFSLoginPageInNonProdEnv load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return pageRightSideContentContainer().isAvailable() &&
                pageHeader().isAvailable() &&
                loginForm().isAvailable() &&
                loginInstructionLabel().isAvailable() &&
                usernameInputField().isAvailable() &&
                passwordInputField().isAvailable() &&
                signInButton().isAvailable();
    }

    private Element pageRightSideContentContainer() {
        return new Element(driver, By.id("content"));
    }

    private LabelText pageHeader() {
        return new LabelText(driver, By.id("header"));
    }

    private Element loginForm() {
        return new Element(driver, By.id("loginForm"));
    }

    private LabelText loginInstructionLabel() {
        return new LabelText(driver, By.id("loginMessage"));
    }

    private TextInput usernameInputField() {
        return new TextInput(driver, By.id("userNameInput"));
    }

    private TextInput passwordInputField() {
        return new TextInput(driver, By.id("passwordInput"));
    }

    private Button signInButton() {
        return new Button(driver, By.id("submitButton"));
    }

    /**
     * Enters specified username into respective input field.
     *
     * @param username - Specifies the username intended to be entered
     * @return {@code ADFSLoginPageInNonProdEnv}
     */
    private ADFSLoginPageInNonProdEnv enterUsername(String username) {
        usernameInputField().clear();
        usernameInputField().sendKeys(username);
        return this;
    }

    /**
     * Enters specified password into respective input field.
     *
     * @param password - Specifies the password intended to be entered
     * @return {@code ADFSLoginPageInNonProdEnv}
     */
    private ADFSLoginPageInNonProdEnv enterPassword(String password) {
        passwordInputField().clear();
        passwordInputField().sendKeys(password);
        return this;
    }

    /**
     * Fills out Login form with valid data (username and password) and clicks 'Sign in' button.
     *
     * @param username - Specifies the username intended to be entered
     * @param password - Specifies the password intended to be entered
     */
    @Override
    public void fillOutAndSubmitLoginFormWithValidData(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        signInButton().click();
    }
}