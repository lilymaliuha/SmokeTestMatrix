package cdssmoketest.documentsdownloadingtests;

import cdssmoketest.BaseCDSTest;
import com.xyleme.bravais.datacontainers.FileType;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.PreviewPane;
import org.testng.annotations.*;

import java.io.File;
import java.util.List;

import static com.xyleme.bravais.datacontainers.StringConstants.*;
import static com.xyleme.bravais.utils.Utils.getFilesInDownloadsFolder;
import static org.testng.Assert.*;

/**
 * Tests which verify functionality of a single document downloading (checked document types: Excel, Image)
 * (Checks #20, 21 of TLOR-613).
 */
public class TestSingleDocumentDownloading extends BaseCDSTest {

    @Override
    @BeforeClass (alwaysRun = true)
    public void setUp() {
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDS();
        documentsPage = cdsHomePage.openDocumentsPage();
        documentsPage.navigateToFolder(BASE_CDS_FOLDER + "|" + PREVIEW);
    }

    @Test (description = "Checks #20 and #21 of TLOR-613 for Excel document")
    public void downloadExcelDocumentTest() {
        documentDetailsPage = documentsPage.getFilteredTableItem(XLTX_FILE, true).goToDocumentDetails();
        PreviewPane previewPane = documentDetailsPage.selectPreviewTabAndGetPane();
        String pathToDownloadedDocument = previewPane.getPreviewAreaOfNonVisualPreviewType().downloadDocument(FileType.EXEL);
        List<String> filesOfDownloadsFolder = getFilesInDownloadsFolder();

        assertTrue(filesOfDownloadsFolder.contains("downloaded.xltx"),
                "Expected document has not been downloaded. Documents of 'downloads' folder: " + filesOfDownloadsFolder);
        assertEquals((new File(pathToDownloadedDocument).length() / 1024), 19,
                "Actual size of the downloaded file differs from the expected one.");
    }

    @Test (description = "Checks #20 and #21 of TLOR-613 for Image")
    public void downloadImageTest() {
        documentDetailsPage = documentsPage.getFilteredTableItem(JPEG_FILE, true).goToDocumentDetails();
        String pathToDownloadedDocument = documentDetailsPage.downloadDocument(FileType.JPEG);
        List<String> filesOfDownloadsFolder = getFilesInDownloadsFolder();

        assertTrue(filesOfDownloadsFolder.contains("downloaded.jpeg"),
                "Expected document has not been downloaded. Documents of 'downloads' folder: " + filesOfDownloadsFolder);
        assertEquals((new File(pathToDownloadedDocument).length() / 1024), 89,
                "Actual size of the downloaded file differs from the expected one.");
    }

    @Override
    @AfterMethod (alwaysRun = true)
    public void tearDown() {
        if (!documentsPage.getNameOfCurrentFolder().equals(PREVIEW)) {
            documentsPage.goToOuterFolderViaLinkInBreadCrumbsBlock(PREVIEW);
        }
    }

    @AfterClass (alwaysRun = true)
    public void classTearDown() {
        super.tearDown();
    }
}