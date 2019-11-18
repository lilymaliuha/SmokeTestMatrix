package cdssmoketest.logintests;

import cdssmoketest.BaseCDSTest;
import org.testng.annotations.*;

import static org.testng.Assert.*;

/**
 * Tests which verify the system behavior in response to attempting to log in using valid credentials (TLOR-1).
 */
public class TestSuccessfulLogin extends BaseCDSTest {

    @Test (description = "Steps 1-4 of TLOR-1")
    public void successfulLoginTest() {
        cdsLoginPage.enterUsernameAndPassword(username, password);

        assertTrue(cdsLoginPage.isPasswordInputFieldOfTypePassword(),
                "Password input field is not of type 'password', the entered password can be not masked.");
        cdsHomePage = cdsLoginPage.clickSignInButtonForSubmittingValidCredentials();

        assertTrue(cdsHomePage.isAvailable(),
                "User didn't access CDS Home page after attempting to log in using valid credentials.");
    }

    @Test (description = "Step 5 of TLOR-1")
    public void bravaisContextCookieCreationAfterSuccessfulLoginTest() {
        cdsHomePage = cdsLoginPage.logInToCDS();

        assertTrue(cdsHomePage.isBravaisContextCookieCreated(),
                "'Bravais-Context' cookie wasn't generated after successful login.");
    }

    @Test (description = "Step 6 of TLOR-1")
    public void deleteBravaisContextCookieAfterSuccessfulLoginTest() {
        cdsHomePage = cdsLoginPage.logInToCDS();
        driver.manage().deleteCookie(cdsHomePage.getBravaisContextCookie());
        cdsHomePage.refresh();

        assertTrue(cdsLoginPage.isAvailable(),"User has not been logged out after deleting 'Bravais-Context' cookie.");
    }

    @Test (description = "Step 7 of TLOR-1")
    public void loginWithUsernameWithChangedCharactersCaseTest() {
        cdsLoginPage.enterUsernameAndPassword(username.toUpperCase(), password);
        cdsHomePage = cdsLoginPage.clickSignInButtonForSubmittingValidCredentials();

        assertTrue(cdsHomePage.isAvailable(), "User hasn't got access to CDS after attempting to log in using " +
                "valid username characters of which were changed to upper case and a valid (not changed) password.");
    }

    @Test (description = "Steps 8-10 of TLOR-1")
    public void signInWithCASLinkClickabilityTest() {
        cdsLoginPage.enterUsername(username);

        assertTrue(cdsLoginPage.isPasswordInputFieldEmpty(),"'Password' input field is not empty after entering username.");
        assertTrue(cdsLoginPage.isLoginWithCASLinkClickable(),
                "'Sign In with CAS' link is not clickable after entering username and leaving password input field empty.");
        cdsLoginPage.clearUsernameInputField();
        cdsLoginPage.enterPassword(password);

        assertTrue(cdsLoginPage.isUsernameInputFieldEmpty(),"'Username' input field is not empty after entering password.");
        assertTrue(cdsLoginPage.isLoginWithCASLinkClickable(),
                "'Sign In with CAS' link is not clickable after entering password and leaving username input field empty.");
        cdsLoginPage.enterUsernameAndPassword(username, password);

        assertFalse(cdsLoginPage.isLoginWithCASLinkClickable(),
                "'Sign In with CAS' link is clickable after entering password and username.");
    }

    @Override
    @AfterMethod (alwaysRun = true)
    public void tearDown() {

        if (!cdsLoginPage.isAvailable()) {
            cdsLogoutPage = cdsHomePage.signOut();
            cdsLoginPage = cdsLogoutPage.clickSignInLink();
        }
    }

    @AfterClass (alwaysRun = true)
    public void classTearDown() {
        super.tearDown();
    }
}