package cdssmoketest.trustedapplicationspagetests;

import cdssmoketest.BaseCDSTest;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnTrustedApplicationPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSTrustedApplicationsPage;
import org.testng.annotations.*;

import static org.testng.Assert.assertEquals;

/**
 * Test which verifies creation and adding of a new trusted application on Trusted Applications page (Check #59 of TLOR-613).
 */
public class TestTrustedApplicationsPage extends BaseCDSTest {
    private CDSTrustedApplicationsPage trustedApplicationsPage;
    private RowOfTableOnTrustedApplicationPage applicationRow;
    private String applicationName = "AT Trusted App " + getRandomNumber(100001, 900000);

    @Override
    @BeforeClass (alwaysRun = true)
    public void setUp() {
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDS();
        trustedApplicationsPage = cdsHomePage.openTrustedApplicationPage();
    }

    @Test (description = "Check #59 of TLOR-613")
    public void newTrustedApplicationAddingTest() {
        int validityTerm = 999;

        trustedApplicationsPage.createNewApplication(applicationName, fullUserName, validityTerm);
        applicationRow = trustedApplicationsPage.getFilteredApplication(applicationName,true);

        assertEquals(applicationRow.getApplicationName(), applicationName,
                "Actual name of the newly created application differs from the expected one.");
        assertEquals(applicationRow.getDefaultUser(), fullUserName,
                "Actual default user of the newly created application differs from the expected one.");
        assertEquals(applicationRow.getValidityTerm(), validityTerm + " (999 days left)",
                "Actual validity term of the newly created application differs from the expected one.");
        assertEquals(applicationRow.getStatus(),"enabled",
                "Actual status of the newly created application differs from the expected one.");
    }

    @Override
    @AfterClass (alwaysRun = true)
    public void tearDown() {
        applicationRow = trustedApplicationsPage.getFilteredApplication(applicationName,true);
        applicationRow.removeApplication();
        super.tearDown();
    }
}