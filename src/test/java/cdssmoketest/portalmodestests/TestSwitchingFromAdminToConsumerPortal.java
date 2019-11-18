package cdssmoketest.portalmodestests;

import cdssmoketest.BaseCDSTest;
import com.xyleme.bravais.web.pages.cds.cdsconsumerportalpages.homepage.CDSConsumerPortalHomePage;
import org.testng.annotations.*;

import static org.testng.Assert.assertTrue;

/**
 * Test which verifies switching between Admin and Consumer Portal modes.
 */
public class TestSwitchingFromAdminToConsumerPortal extends BaseCDSTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDS();
    }

    @Test
    public void switchBetweenAdminAndConsumerPortalModesTest() {
        CDSConsumerPortalHomePage consumerPortalHomePage = cdsHomePage.goToConsumerPortal();

        assertTrue(consumerPortalHomePage.isAvailable(), "User has not been redirected to Consumer Portal after " +
                "selecting respective option in the module switching menu.");
        cdsHomePage = consumerPortalHomePage.goToAdminPortal();

        assertTrue(cdsHomePage.isAvailable(), "User has not been redirected to Admin Portal after clicking 'Admin " +
                "Portal' link/button in the navigation menu in the page header.");
    }
}