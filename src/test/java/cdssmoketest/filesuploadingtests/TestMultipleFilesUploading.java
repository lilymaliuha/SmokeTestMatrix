package cdssmoketest.filesuploadingtests;

import cdssmoketest.BaseCDSTest;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.xyleme.bravais.datacontainers.StringConstants.*;
import static org.testng.Assert.*;

/**
 * Tests which verify functionality of multiple files uploading using Add Document button on Documents page
 * (Check #15 of TLOR-613).
 */
public class TestMultipleFilesUploading extends BaseCDSTest {
    private String[] filesToUpload = new String[]{DOCX_FILE, JPEG_FILE, M4V_FILE, PDF_FILE, PPTX_FILE, TXT_FILE,
            XLTX_FILE, XML_FILE, ZIP_FILE, HTML_FILE};
    private List<String> expectedNamesOfFiles = new ArrayList<>(Arrays.asList(DOCX_FILE, JPEG_FILE, M4V_FILE, PDF_FILE,
            PRESENTATION_FILE, TXT_FILE, XLTX_FILE, XML_FILE, ARCHIVE_FILE, HTML_FILE));

    @Override
    @BeforeClass (alwaysRun = true)
    public void setUp() {
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDS();
        documentsPage = cdsHomePage.openDocumentsPage();
        documentsPage.navigateToFolder(BASE_CDS_FOLDER + "|" + UPLOADED_FILES);
        documentsPage.archiveAllItemsInFolder();
    }

    @Test (description = "Check #15 of TLOR-613, files uploading part")
    public void uploadTenFilesInOneAttemptTest() {
        documentsPage.uploadMultipleFilesFromFilesForUploadingFolder(filesToUpload);

        assertEquals(documentsPage.getNumberOfItemsDisplayedOnItemsCounter(), 10,
                "Actual number of documents in a target folder after uploading files differs from the expected one.");
        List<String> namesOfAllTableItems = documentsPage.getDataTable().getNamesOfAllTableItems();

        assertTrue(namesOfAllTableItems.containsAll(expectedNamesOfFiles),"Actual list of folder items after " +
                "uploading test files doesn't contain all expected files.\nActual list of folder items: " +
                namesOfAllTableItems + "\nExpected list of folder items: " + expectedNamesOfFiles);
    }

    @Override
    @AfterClass (alwaysRun = true)
    public void tearDown() {
        super.tearDown();
    }
}