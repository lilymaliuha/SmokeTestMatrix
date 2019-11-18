package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsloginpage.adfsloginpage;

import com.xyleme.bravais.web.WebPage;
import com.xyleme.bravais.web.elements.*;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsloginpage.pageforms.BaseLoginPageForm;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of ADFS Login page which appears after clicking "Sign In with CAS" link on CDS Login page in Production
 * environment.
 */
public class ADFSLoginPageInProdEnv extends WebPage<ADFSLoginPageInProdEnv> implements ADFSLoginPage {

    public ADFSLoginPageInProdEnv(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public ADFSLoginPageInProdEnv load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return marketingImageContainer().isAvailable() &&
                informationBlock().isAvailable() &&
                getSignInForm().isAvailable();

    }

    private Element pageContentContainer() {
        return new Element(driver, By.xpath("//div[contains(@class, 'container page')]"));
    }

    private Element marketingImageContainer() {
        return new Element(driver, By.xpath(pageContentContainer().getXpathLocatorValue() +
                "/div[contains(@class, 'marketing-image')]"));
    }

    private Element informationBlock() {
        return new Element(driver, By.xpath(pageContentContainer().getXpathLocatorValue() +
                "//div[@class='marketing-copy']"));
    }

    /**
     * Gets 'Sign In' form available on the page.
     *
     * @return {@code SignInForm}
     */
    private SignInForm getSignInForm() {
        return new SignInForm(driver);
    }

    /**
     * Fills out Sign In form with valid data (username and password) and clicks 'Sign in' button.
     *
     * @param username - Specifies the username intended to be entered
     * @param password - Specifies the password intended to be entered
     */
    @Override
    public void fillOutAndSubmitLoginFormWithValidData(String username, String password) {
        getSignInForm().fillOutAndSubmitLoginFormWithValidData(username, password);
    }

    /**
     * Implementation of 'Sign In' form available on the page.
     */
    private class SignInForm extends BaseLoginPageForm {

        SignInForm(WebDriver driver) {
            super(driver, "Sign In");
        }

        @Override
        public boolean isAvailable() {
            return super.isAvailable() &&
                    usernameInputField().isAvailable() &&
                    passwordInputField().isAvailable() &&
                    cantGetAccessToYourAccountLink().isAvailable() &&
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

        private Button signInButton() {
            return formButton("Sign In");
        }

        /**
         * Enters specified username of CAS user into the respective input field.
         *
         * @param usernameOfCASUser - Specifies the username intended to be entered
         * @return {@code SignInForm}
         */
        private SignInForm enterUsername(String usernameOfCASUser) {
            usernameInputField().clear();
            usernameInputField().sendKeys(usernameOfCASUser);
            return this;
        }

        /**
         * Enters specified password of CAS user into the respective input field.
         *
         * @param passwordOfCASUser - Specifies the password intended to be entered
         * @return {@code SignInForm}
         */
        private SignInForm enterPassword(String passwordOfCASUser) {
            passwordInputField().clear();
            passwordInputField().sendKeys(passwordOfCASUser);
            return this;
        }

        /**
         * Fills out Sign In form with valid data (username and password) and clicks 'Sign in' button.
         *
         * @param username - Specifies the username intended to be entered
         * @param password - Specifies the password intended to be entered
         */
        void fillOutAndSubmitLoginFormWithValidData(String username, String password) {
            enterUsername(username);
            enterPassword(password);
            signInButton().click();
        }
    }
}