package cdssmoketest.documentsdownloadingtests;

import cdssmoketest.BaseCDSTest;
import com.xyleme.bravais.datacontainers.FileType;
import com.xyleme.bravais.utils.Utils;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnDocumentsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdspreferencespage.CDSPreferencesPage;
import org.testng.annotations.*;

import java.io.File;
import java.util.*;

import static com.xyleme.bravais.DriverMaster.pathToDownloadsFolder;
import static com.xyleme.bravais.datacontainers.StringConstants.*;
import static com.xyleme.bravais.utils.FileDownloader.unzipFile;
import static org.testng.Assert.*;

/**
 * Tests which verify functionality of multiple documents downloading (the downloading process itself and the related
 * configurations) (Checks ##32 - 38 of TLOR-613)
 */
public class TestMultipleDocumentsDownloading extends BaseCDSTest {
    private CDSPreferencesPage preferencesPage;

    @BeforeClass (alwaysRun = true)
    public void classSetUp() {
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDS();
        preferencesPage = cdsHomePage.openPreferencesPage();
        preferencesPage.getDocumentDownloadConfigurationBlock().setMaximumNumberOfFilesInBulk(2, true);
        documentsPage = preferencesPage.openDocumentsPage();
        documentsPage.navigateToFolder(BASE_CDS_FOLDER);
    }

    @Override
    @BeforeMethod (alwaysRun = true)
    public void setUp() {

        if (!documentsPage.getNameOfCurrentFolder().equals(BASE_CDS_FOLDER)) {
            documentsPage.goToOuterFolderViaLinkInBreadCrumbsBlock(BASE_CDS_FOLDER);
        }
    }

    @Test (description = "Checks #33 and #34 of TLOR-613")
    public void attemptToDownloadAllowedNumberOfDocumentsSelectedSeparatelyTest() {
        documentsPage.navigateToFolder(PREVIEW);
        RowOfTableOnDocumentsPage documentA = documentsPage.getDataTable().getRow(0);
        RowOfTableOnDocumentsPage documentB = documentsPage.getDataTable().getRow(1);
        List<RowOfTableOnDocumentsPage> itemsToDownload = new ArrayList<>(Arrays.asList(documentA, documentB));
        documentA.select();
        documentB.select();
        String pathToDownloadedZipFile = documentsPage.downloadSelectedItemsViaBulkChangesMenu(itemsToDownload);

        assertTrue(Utils.getFilesInDownloadsFolder().contains("downloaded.zip"),"Expected zip file is not present " +
                "in the downloads folder.\nFiles available in the downloads folder: " + Utils.getFilesInDownloadsFolder()
                + "\n");
        assertEquals(new File(pathToDownloadedZipFile).length() / 1024, 105,
                "Actual size of the downloaded zip file differs from the expected one.");
        unzipFile(pathToDownloadedZipFile, pathToDownloadsFolder);
        new File(pathToDownloadedZipFile).delete();

        assertTrue(Utils.getFilesInDownloadsFolder().contains(XLTX_FILE),"Expected excel document has not been " +
                "extracted from the downloaded zip file to the downloads folder.\nFiles available in the downloads folder: "
                + Utils.getFilesInDownloadsFolder() + "\n");
        assertTrue(Utils.getFilesInDownloadsFolder().contains(JPEG_FILE),"Expected image has not been extracted " +
                "from the downloaded zip file to the downloads folder.\nFiles available in the downloads folder: "
                + Utils.getFilesInDownloadsFolder() + "\n");
        assertEquals(new File(pathToDownloadsFolder + Utils.getFileInDownloadsFolder(FileType.JPEG)).length()
                / 1024, 89, "Actual size of the downloaded image differs from the expected one.");
        assertEquals(new File(pathToDownloadsFolder + Utils.getFileInDownloadsFolder(FileType.EXEL)).length()
                / 1024, 19, "Actual size of the downloaded excel file differs from the expected one.");
    }

    @Test (description = "Check #35 of TLOR-613")
    public void attemptToDownloadMoreThanAllowedNumberOfDocumentsSelectedSeparatelyTest() {
        documentsPage.navigateToFolder(UPLOADED_FILES);
        documentsPage.getDataTable().getRow(0).select();
        documentsPage.getDataTable().getRow(1).select();
        documentsPage.getDataTable().getRow(2).select();
        documentsPage.selectOptionOfBulkChangesDropDown("Download");

        assertEquals(documentsPage.getPoppedUpErrorMessageAndClosePopUp(),"Cannot download more than 2 documents at once.",
                "Actual error message differs from the expected one.");
    }

    @Test (description = "Checks #36 and #37 of TLOR-613")
    public void attemptToDownloadAllowedNumberOfDocumentsBySelectingParentFolderTest() {
        RowOfTableOnDocumentsPage folder = documentsPage.getFilteredTableItem(PREVIEW, true);
        String pathToDownloadedZipFile = folder.downloadItemUsingDownloadOptionOfMoreMenu(FileType.ZIP_FOR_DOCUMENT_DOWNLOADING);

        assertTrue(Utils.getFilesInDownloadsFolder().contains("downloaded.zip"),"Expected zip file is not present " +
                "in the downloads folder.\nFiles available in the downloads folder: " + Utils.getFilesInDownloadsFolder()
                + "\n");
        assertEquals(new File(pathToDownloadedZipFile).length() / 1024, 105,
                "Actual size of the downloaded zip file differs from the expected one.");
        unzipFile(pathToDownloadedZipFile, pathToDownloadsFolder);
        new File(pathToDownloadedZipFile).delete();

        assertTrue(Utils.getFilesInDownloadsFolder().contains(PREVIEW + File.separator + XLTX_FILE),"Expected excel document " +
                "hasn't been extracted as an item of Preview folder from the downloaded zip file to the downloads folder." +
                "\nFiles available in the downloads folder: " + Utils.getFilesInDownloadsFolder() + "\n");
        assertTrue(Utils.getFilesInDownloadsFolder().contains(PREVIEW + File.separator + JPEG_FILE),"Expected image hasn't been " +
                "extracted as an item of Preview folder from the downloaded zip file to the downloads folder.\nFiles " +
                "available in the downloads folder: " + Utils.getFilesInDownloadsFolder() + "\n");
        assertEquals(new File(pathToDownloadsFolder + Utils.getFileInDownloadsFolder(FileType.JPEG)).length()
                / 1024, 89, "Actual size of the downloaded image differs from the expected one.");
        assertEquals(new File(pathToDownloadsFolder + Utils.getFileInDownloadsFolder(FileType.EXEL)).length()
                / 1024, 19, "Actual size of the downloaded excel file differs from the expected one.");
    }

    @Test (description = "Check #38 of TLOR-613")
    public void attemptToDownloadMoreThanAllowedNumberOfDocumentsBySelectingParentFolderTest() {
        RowOfTableOnDocumentsPage folder = documentsPage.getFilteredTableItem(UPLOADED_FILES,true);
        folder.clickOptionOfItemOptionsMenu("Download");

        assertEquals(documentsPage.getPoppedUpErrorMessageAndClosePopUp(),"Cannot download more than 2 documents at once.",
                "Actual error message differs from the expected one.");
    }

    @Override
    @AfterClass (alwaysRun = true)
    public void tearDown() {
        driver.get(ENVIRONMENT.env.get("CDS_URL"));
        cdsHomePage.waitUntilAvailable();
        preferencesPage = cdsHomePage.openPreferencesPage();
        preferencesPage.getDocumentDownloadConfigurationBlock().setMaximumNumberOfFilesInBulk(2, true);
        super.tearDown();
    }
}