package cdssmoketest.documentmovingtests;

import cdssmoketest.BaseCDSTest;
import org.testng.annotations.*;

import static com.xyleme.bravais.datacontainers.StringConstants.*;
import static org.testng.Assert.*;

/**
 * Tests which verify a document moving functionality (Checks #39 and #40).
 */
public class TestDocumentMoving extends BaseCDSTest {

    @Override
    @BeforeClass (alwaysRun = true)
    public void setUp() {
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDS();
        documentsPage = cdsHomePage.openDocumentsPage();
        documentsPage.navigateToFolder(BASE_CDS_FOLDER + "|" + DOCUMENTS_MOVING + "|" + TARGET_FOLDER);
    }

    @Test (description = "Check #39 of TLOR-613")
    public void documentMovingTest() {
        assertFalse(documentsPage.isItemPresentInFolder(PDF_FILE),"Document intended to be moved from one folder " +
                "to another is present in the target folder before the moving attempt.");
        documentsPage.goToOuterFolderViaLinkInBreadCrumbsBlock(DOCUMENTS_MOVING);
        documentsPage.navigateToFolder(ORIGINAL_FOLDER);

        assertTrue(documentsPage.isItemPresentInFolder(PDF_FILE),"Document intended to be moved from one folder " +
                "to another is not present in the original folder (the folder from which the document is intended to be " +
                "moved) before the moving attempt.");
        documentsPage.getFilteredTableItem(PDF_FILE, true).moveItemTo(BASE_CDS_FOLDER + "|" +
                DOCUMENTS_MOVING + "|" + TARGET_FOLDER);

        assertFalse(documentsPage.isItemPresentInFolder(PDF_FILE),"Document intended to be moved from the original " +
                "folder remains in the folder after the moving attempt.");
        documentsPage.goToOuterFolderViaLinkInBreadCrumbsBlock(DOCUMENTS_MOVING);
        documentsPage.navigateToFolder(TARGET_FOLDER);

        assertTrue(documentsPage.isItemPresentInFolder(PDF_FILE),"Document intended to be moved from one folder to " +
                "another is not present in the target the folder after the moving attempt.");
    }

    @Override
    @AfterMethod (alwaysRun = true)
    public void tearDown() {
        if (documentsPage.getNameOfCurrentFolder().equals(TARGET_FOLDER)) {
            if (documentsPage.isItemPresentInFolder(PDF_FILE)) {
                documentsPage.getFilteredTableItem(PDF_FILE, true).moveItemTo(BASE_CDS_FOLDER +
                        "|" + DOCUMENTS_MOVING + "|" + ORIGINAL_FOLDER);
            }
        }
    }

    @AfterClass (alwaysRun = true)
    public void classTearDown() {
        super.tearDown();
    }
}