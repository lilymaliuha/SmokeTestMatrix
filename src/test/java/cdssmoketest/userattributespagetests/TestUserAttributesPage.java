package cdssmoketest.userattributespagetests;

import cdssmoketest.BaseCDSTest;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSUserAttributesPage;
import org.testng.annotations.*;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Test which verifies User Attributes page availability and compliance (Check #55 of TLOR-613).
 */
public class TestUserAttributesPage extends BaseCDSTest {

    @Override
    @BeforeClass (alwaysRun = true)
    public void setUp() {
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDS();
    }

    @Test (description = "Check #55 of TLOR-613")
    public void userAttributesPageContentTest() {
        CDSUserAttributesPage userAttributesPage = cdsHomePage.openUserAttributesPage();

        assertTrue(userAttributesPage.isAvailable(),"User Attributes page hasn't been opened " +
                "(or not all elements of the page appeared) after navigating to the page.");
        assertEquals(userAttributesPage.getPageTitle(), "User Attributes",
                "Actual page title of User Attributes page differs from the expected one.");
        List<String> namesOfTableRows = userAttributesPage.getTable().getNamesOfRows();

        assertTrue(namesOfTableRows.containsAll(Arrays.asList("Business Unit", "Department", "Employee ID", "Location",
                "Role", "Supervisor")), "Page table doesn't contain all expected rows. Actual table row names: " +
                namesOfTableRows);
    }

    @Override
    @AfterClass (alwaysRun = true)
    public void tearDown() {
        super.tearDown();
    }
}