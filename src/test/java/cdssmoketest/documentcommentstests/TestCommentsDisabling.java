package cdssmoketest.documentcommentstests;

import cdssmoketest.BaseCDSTest;
import com.xyleme.bravais.datacontainers.configparametersofpreferencespage.YesNoParameter;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdspreferencespage.CDSPreferencesPage;
import org.testng.annotations.*;

import static com.xyleme.bravais.datacontainers.StringConstants.*;
import static org.testng.Assert.*;

/**
 * Test which verifies disabling of a commenting function (Comments Block disabling) (Checks #30 (the procedure is
 * executed in the setUp method) and #31 of TLOR-613).
 */
public class TestCommentsDisabling extends BaseCDSTest {
    private CDSPreferencesPage preferencesPage;

    @Override
    @BeforeClass (alwaysRun = true)
    public void setUp() {
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDS();
        preferencesPage = cdsHomePage.openPreferencesPage();
        preferencesPage.getUserCommentsConfigurationBlock().enableUserComments(YesNoParameter.NO, true);
        documentsPage = preferencesPage.openDocumentsPage();
        documentsPage.navigateToFolder(BASE_CDS_FOLDER + "|" + PREVIEW);
    }

    @Test (description = "Check #31 of TLOR-613")
    public void commentingOptionDisablingTest() {
        documentDetailsPage = documentsPage.getFilteredTableItem(XLTX_FILE, true).goToDocumentDetails();

        assertFalse(documentDetailsPage.selectPreviewTabAndGetPane().isCommentsBlockAvailable(), "Comments block " +
                "is available on Preview pane after disabling commenting option on Preferences page.");
        assertFalse(documentDetailsPage.selectPropertiesTabAndGetPane().isCommentsBlockAvailable(), "Comments block " +
                "is available on Properties pane after disabling commenting option on Preferences page.");
    }

    @Override
    @AfterMethod (alwaysRun = true)
    public void tearDown() {
        driver.get(ENVIRONMENT.env.get("CDS_URL"));
        cdsHomePage.waitUntilAvailable();
        preferencesPage = cdsHomePage.openPreferencesPage();
        preferencesPage.getUserCommentsConfigurationBlock().enableUserComments(YesNoParameter.YES, true);
    }

    @AfterClass (alwaysRun = true)
    public void classTearDown() {
        super.tearDown();
    }
}