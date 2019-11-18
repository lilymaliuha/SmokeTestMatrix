package cdssmoketest;

import com.xyleme.bravais.datacontainers.CDSFeatures;
import org.testng.annotations.*;

/**
 * Checks if the 'Admin Portal' feature is checked in the Feature Access panel of 'All users' group.
 */
public class SuiteSetUp extends BaseCDSTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
    }

    @Test
    public void checkAdminPortalFeatureInFeatureAccessPanelOfAllUsersGroup() {
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDS();
        groupsPage = cdsHomePage.openGroupsPage();
        groupDetailsPage = groupsPage.getFilteredGroup("All users", true).goToGroupDetailsPage();
        groupDetailsPage.selectFeatureAccessTabAndGetPane().selectMultipleFeatures(CDSFeatures.ADMIN_PORTAL);
        super.tearDown();
    }

    @Override
    @AfterMethod (alwaysRun = true)
    public void tearDown() {

    }
}