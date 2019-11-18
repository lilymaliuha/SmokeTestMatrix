package cdssmoketest.logintests;

import cdssmoketest.BaseCDSTest;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsloginpage.CDSLoginPage;
import org.testng.annotations.*;

import static org.testng.Assert.*;

/**
 * Tests which verify the system behavior in response to attempting to log in using invalid credentials (TLOR-2).
 */
public class TestUnsuccessfulLogin extends BaseCDSTest {

    private static String lowerCasedPassword = password.toLowerCase();

    @DataProvider
    private static Object[][] invalidCredentials() {
        return new Object[][] {
                {username, password + 5678910},
                {"john.travolta", password},
                {"", ""},
                {username, lowerCasedPassword}
        };
    }

    @Test (description = "TLOR-2", dataProvider = "invalidCredentials")
    public void unsuccessfulLoginAttemptTest(String username, String password) {
        assertTrue(cdsLoginPage.isAvailable(), "CDS login page didn't appear after navigating to CDS URL.");
        cdsLoginPage.performInvalidLoginAttempt(username, password);

        assertTrue(cdsLoginPage.isAvailable(), "CDS Login page is not available after invalid login attempt.");
        assertTrue(cdsLoginPage.isUsernameInputFieldEmpty(),
                "Username input field did not get cleared after attempting to log in using invalid credentials.");
        assertTrue(cdsLoginPage.isPasswordInputFieldEmpty(),
                "Password input field did not get cleared after attempting to log in using invalid credentials.");
        assertEquals(cdsLoginPage.getLoginErrorMessageText(), "The username or password is incorrect.",
                "Actual error message appeared after invalid login attempt differs from the expected one.");
    }

    @Override
    @AfterMethod (alwaysRun = true)
    public void tearDown() {
        driver.get(ENVIRONMENT.env.get("CDS_URL"));
        cdsLoginPage = new CDSLoginPage(driver);
    }

    @AfterClass (alwaysRun = true)
    public void classTearDown() {
        super.tearDown();
    }
}