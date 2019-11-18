package cdssmoketest.documentspagetests;

import cdssmoketest.BaseCDSTest;
import com.xyleme.bravais.utils.RandomizationUtil;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnDocumentsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsfolderdetailspage.CDSFolderDetailsPage;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.*;

import static org.testng.Assert.assertEquals;

/**
 * Tests which verify functionality of 'Documents' page (Checks #11 and #12).
 */
public class TestDocumentsPage extends BaseCDSTest {
    private String newFolderName = "Folder created by Auto Smoke test";
    private String updatedFolderName = newFolderName + " " + RandomizationUtil.getRandomNumber(100, 999);
    private RowOfTableOnDocumentsPage matchingFolder;
    private CDSFolderDetailsPage folderDetailsPage;

    @Override
    @BeforeClass (alwaysRun = true)
    public void setUp() {
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDS();
    }

    @Test (description = "Check #11 of TLOR-613")
    public void createFolderTest() {
        documentsPage = cdsHomePage.openDocumentsPage();
        documentsPage.createNewFolder(newFolderName, "Test Description");
        documentsPage.openDocumentsPage();
        matchingFolder = documentsPage.getFilteredTableItem(newFolderName, true);

        assertEquals(matchingFolder.getItemName(), newFolderName,
                "A folder named '" + newFolderName + "' has not been created.");
    }

    @Test (dependsOnMethods = "createFolderTest", description = "Check #12 of TLOR-613", retryAnalyzer =
            IRetryAnalyzer.class)
    public void editFolderNameTest() {
        folderDetailsPage = matchingFolder.goToFolderDetails();
        folderDetailsPage.editFolderName(updatedFolderName);
        documentsPage = folderDetailsPage.openDocumentsPage();
        matchingFolder = documentsPage.getFilteredTableItem(updatedFolderName, true);

        assertEquals(matchingFolder.getItemName(), updatedFolderName,"Name of the folder which was " +
                "originally named '" + newFolderName + "' has not been edited to '" + updatedFolderName + "'.");
    }

    @Override
    @AfterClass (alwaysRun = true)
    public void tearDown() {
        if (!documentsPage.isAvailable()) {
            driver.get(ENVIRONMENT.env.get("CDS_URL"));
            cdsHomePage.waitUntilAvailable();
            documentsPage = cdsHomePage.openDocumentsPage();
        }
        folderDetailsPage = documentsPage.getFilteredTableItem(newFolderName, false).goToFolderDetails();
        folderDetailsPage.archiveFolder();
        super.tearDown();
    }
}