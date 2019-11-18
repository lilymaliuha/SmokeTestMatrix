package cdssmoketest.previewtests;

import cdssmoketest.BaseCDSTest;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.PreviewPane;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.previewarea.visualdocumentpreview.NonCourseDocumentPreview;
import org.testng.annotations.*;

import static com.xyleme.bravais.DriverMaster.closeCurrentTabAndSwitchToOriginalTab;
import static com.xyleme.bravais.datacontainers.StringConstants.*;
import static org.testng.Assert.*;

/**
 * Tests which verify functionality of a document preview (checked document types: Excel, Image) (Checks #16 - #19 of
 * TLOR-613).
 */
public class TestExcelAndImagePreview extends BaseCDSTest {
    private PreviewPane previewPane;
    private String previewFolderURL;

    @BeforeClass (alwaysRun = true)
    public void classSetUp() {
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDS();
        documentsPage = cdsHomePage.openDocumentsPage();
        documentsPage.navigateToFolder(BASE_CDS_FOLDER + "|" + PREVIEW);
        previewFolderURL = driver.getCurrentUrl();
        documentsPage.archiveAllItemsInFolder();
        documentsPage.uploadMultipleFilesFromFilesForUploadingFolder(new String[]{JPEG_FILE, XLTX_FILE});
    }

    @Override
    @BeforeMethod (alwaysRun = true)
    public void setUp() {
        if (!documentsPage.isAvailable()) {
            driver.get(previewFolderURL);
            documentsPage.waitUntilAvailable();
        }
    }

    @Test (description = "Checks #16 - #18 of TLOR-613 for Excel document")
    public void previewExcelDocumentTest() {
        documentDetailsPage = documentsPage.getFilteredTableItem(XLTX_FILE, true).goToDocumentDetails();
        previewPane = documentDetailsPage.selectPreviewTabAndGetPane();

        assertTrue(previewPane.getPreviewAreaOfNonVisualPreviewType().isDownloadButtonInPreviewAreaAvailable(),
                "Document Preview area doesn't contain Download button");
    }

    @Test (description = "Checks #16 - #18 of TLOR-613 for Image")
    public void previewImageTest() {
        documentDetailsPage = documentsPage.getFilteredTableItem(JPEG_FILE, true).goToDocumentDetails();
        previewPane = documentDetailsPage.selectPreviewTabAndGetPane();

        assertTrue(previewPane.getVisualPreviewOfNonCourseDocument().isImagePreviewOpened(),
                "Image preview hasn't been opened in Preview pane on Document Details page.");
    }

    @Test (description = "Check #19 of TLOR-613", groups = "fullScreenVisualPreview")
    public void imageFullPreviewTest() {
        documentDetailsPage = documentsPage.getFilteredTableItem(JPEG_FILE, true).goToDocumentDetails();

        assertTrue(documentDetailsPage.openFullScreenPreviewOfDocument(NonCourseDocumentPreview.class).isImagePreviewOpened(),
                "Full Screen preview the image has not been opened.");
    }

    @AfterGroups (value = "fullScreenVisualPreview", alwaysRun = true)
    public void closeFullScreenPreviewTab() {
        closeCurrentTabAndSwitchToOriginalTab();
        documentDetailsPage.waitUntilAvailable();
    }

    @Override
    @AfterClass (alwaysRun = true)
    public void tearDown() {
        super.tearDown();
    }
}