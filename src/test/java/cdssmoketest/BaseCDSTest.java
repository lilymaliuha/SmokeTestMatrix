package cdssmoketest;

import com.xyleme.bravais.BaseTest;
import com.xyleme.bravais.DriverMaster;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSDocumentsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSGroupsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.CDSDocumentDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsgroupdetailspage.CDSGroupDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdshomepage.CDSHomePage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsloginpage.CDSLoginPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSLogoutPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.util.Date;

public class BaseCDSTest extends BaseTest {
    protected WebDriver driver;
    protected CDSLoginPage cdsLoginPage;
    protected CDSLogoutPage cdsLogoutPage;
    protected CDSHomePage cdsHomePage;
    protected CDSDocumentsPage documentsPage;
    protected CDSDocumentDetailsPage documentDetailsPage;
    protected CDSGroupsPage groupsPage;
    protected CDSGroupDetailsPage groupDetailsPage;
    protected static String username = ENVIRONMENT.env.get("username");
    protected static String password = ENVIRONMENT.env.get("password");
    protected static String fullUserName = ENVIRONMENT.env.get("fullUsername");
    private static boolean handleStatementsDelay =
            System.getProperty("testEnvironment").equalsIgnoreCase("stg") ||
            System.getProperty("testEnvironment").equalsIgnoreCase("beta");

    @Override
    @BeforeMethod (alwaysRun = true)
    public void setUp() {
        driver = DriverMaster.checkIfOldDriverClosedAndCreateNewDriverInstance();
        driver.manage().window().maximize();
        driver.get(ENVIRONMENT.env.get("CDS_URL"));
        cdsLoginPage = new CDSLoginPage(driver);
    }

    /**
     * Checks if the test environment is either Beta or STG and if so, - records date and time of the test start.
     */
    protected void recordTestStartDateAndTimeInCaseOfTestingOnBetaOrSTGEnvironment() {

        if (handleStatementsDelay) {
            ENVIRONMENT.env.put("testStartDate", String.valueOf(new Date()));
            System.out.println("\n>> Test start date and time: " + ENVIRONMENT.env.get("testStartDate") + '\n');
        }
    }
}