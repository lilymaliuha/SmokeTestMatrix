package cdssmoketest.logintests;

import cdssmoketest.BaseCDSTest;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Test which verifies Forgot Password feature (Check #80 of TLOR-613).
 */
public class TestForgotPasswordFeature extends BaseCDSTest {
    private String username = ENVIRONMENT.env.get("usernameOfUserForEmailsProcessing");
    private String originalPassword = ENVIRONMENT.env.get("passwordOfUserForEmailsProcessing");
    private String newPassword = originalPassword + "45";

    @Override
    @BeforeClass (alwaysRun = true)
    public void setUp() {
        super.setUp();
    }

    @Test (description = "Check #80 of TLOR-613")
    public void forgotPasswordFeatureTest() {
        cdsLoginPage.resetPasswordForUser(username, newPassword);
        cdsHomePage = cdsLoginPage.loginToCDSWithUserAfterPasswordReset(username, newPassword);

        Assert.assertTrue(cdsHomePage.isAvailable(), "User did not get access to CDS Home page (probably did " +
                "not get access to the account) after resetting password via 'Forgot your Password?' form.");
    }

    @Override
    @AfterClass (alwaysRun = true)
    public void tearDown() {
        super.tearDown();
        super.setUp();
        cdsLoginPage.resetPasswordForUser(username, originalPassword);
        cdsHomePage = cdsLoginPage.loginToCDSWithUserAfterPasswordReset(username, originalPassword);
        cdsHomePage.signOut();
        super.tearDown();
    }
}