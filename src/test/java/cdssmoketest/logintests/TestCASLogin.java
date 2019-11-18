package cdssmoketest.logintests;

import cdssmoketest.BaseCDSTest;
import com.xyleme.bravais.utils.CASUserCredentialsProducer;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdshomepage.CDSHomePage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsloginpage.adfsloginpage.ADFSLoginPage;
import com.xyleme.bravais.web.pages.cds.cdsconsumerportalpages.CDSConsumerPortalDocumentsPage;
import org.testng.annotations.*;

import static org.testng.Assert.*;

/**
 * Test which verifies CAS login (Check #81 of TLOR-613).
 */
public class TestCASLogin extends BaseCDSTest {
    private boolean consumerPortal;
    private CDSConsumerPortalDocumentsPage consumerPortalDocumentsPage;

    @Test (description = "Check #81 of TLOR-613")
    public void signInWithCasTest() {
        ADFSLoginPage adfsLoginPage = cdsLoginPage.clickSignInWithCASLink();
        adfsLoginPage.fillOutAndSubmitLoginFormWithValidData(CASUserCredentialsProducer.USERNAME, CASUserCredentialsProducer.PASSWORD);

        if (driver.getCurrentUrl().contains("consumer")) {
            consumerPortal = true;
            consumerPortalDocumentsPage = new CDSConsumerPortalDocumentsPage(driver);
            System.out.println(" > Consumer Portal has been opened after logging in with CAS User.");

            assertTrue(consumerPortalDocumentsPage.isAvailable(),"CAS user did not get access to Consumer Portal " +
                    "Documents page after attempting to log in via CAS login page.");
            assertEquals(consumerPortalDocumentsPage.getNameOfUserInPageHeader(), CASUserCredentialsProducer.FULL_NAME,
                    "Actual full name of the user displayed in the page header differs from the expected one.");
        } else {
            cdsHomePage = new CDSHomePage(driver);
            System.out.println(" > Admin Portal has been opened after logging in with CAS User.");

            assertTrue(cdsHomePage.isAvailable(),
                    "CAS user did not get access to CDS Home page after attempting to log in via CAS login page.");
            assertEquals(cdsHomePage.getNameOfUserInPageHeader(), CASUserCredentialsProducer.FULL_NAME,
                    "Actual full name of the user displayed in the page header differs from the expected one.");
        }
    }

    @Override
    @AfterMethod (alwaysRun = true)
    public void tearDown() {

        if (consumerPortal) {
            consumerPortalDocumentsPage.signOut();
        } else {
            cdsHomePage.signOut();
        }
        super.tearDown();
    }
}