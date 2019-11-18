package cdssmoketest.brandingpagetests;

import cdssmoketest.BaseCDSTest;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsbrandingpage.CDSBrandingPage;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.*;

import static org.testng.Assert.*;

/**
 * Tests which verify functionality of Branding page (Checks ##56 - 58 of TLOR-613).
 */
public class TestBrandingPage extends BaseCDSTest {
    private CDSBrandingPage brandingPage;
    private String footerContentBeforeChanging;
    private String productVersion = System.getProperty("productVersion");

    @Override
    @BeforeClass (alwaysRun = true)
    public void setUp() {
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDS();
        brandingPage = cdsHomePage.openBrandingPage();
    }

    @Test (description = "Check #57 of TLOR-613")
    public void customFooterSettingTest() {
        footerContentBeforeChanging = brandingPage.getFooterContent();
        brandingPage.getFooterPanel().setCustomFooterContent(productVersion).saveChanges();
        String footerContentAfterChanging = brandingPage.getFooterContent();

        assertNotEquals(footerContentBeforeChanging, footerContentAfterChanging, "Footer content has not been updated.");
        assertEquals(footerContentAfterChanging, productVersion,
                "Footer content has not been updated to the expected value.");
    }

    @Test (description = "Check #58 of TLOR-613", dependsOnMethods = "customFooterSettingTest", retryAnalyzer = IRetryAnalyzer.class)
    public void defaultsRestoringTest() {
        brandingPage.restoreDefaults();

        assertNotEquals(brandingPage.getFooterContent(), productVersion,
                "Custom footer content has not been restored to the default one after clicking Restore Defaults link.");
    }

    @Override
    @AfterClass (alwaysRun = true)
    public void tearDown() {
        if (!brandingPage.getFooterContent().equals(footerContentBeforeChanging)) {
            brandingPage.restoreDefaults();
        }
        super.tearDown();
    }
}