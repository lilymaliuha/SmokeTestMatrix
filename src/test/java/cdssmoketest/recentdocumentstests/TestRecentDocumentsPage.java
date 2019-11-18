package cdssmoketest.recentdocumentstests;

import cdssmoketest.BaseCDSTest;
import com.xyleme.bravais.utils.RandomizationUtil;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSRecentDocumentsPage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnRecentDocumentsPage;
import org.testng.annotations.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.xyleme.bravais.datacontainers.StringConstants.*;
import static org.testng.Assert.*;

/**
 * Tests which verify functionality of Recent Documents page (Checks ##5, 6, and 73 of TLOR-613).
 */
public class TestRecentDocumentsPage extends BaseCDSTest {
    private CDSRecentDocumentsPage recentDocumentsPage;
    private String originalDocumentName = "documentForRecentUpdatesTests";
    private String updatedDocumentName = originalDocumentName + RandomizationUtil.getRandomNumber(100, 999) + ".jpg";

    @BeforeGroups (value = "documentUpdateTest", alwaysRun = true)
    public void setUpForDocumentUpdateTest() {
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDSWithUser("msis.tester", "Mypass@123");
        documentsPage = cdsHomePage.openDocumentsPage();
        documentsPage.navigateToFolder(BASE_CDS_FOLDER + "|" + RECENT_DOCUMENTS);
        documentDetailsPage = documentsPage.getFilteredTableItem(originalDocumentName, false).goToDocumentDetails();
        documentDetailsPage.editDocumentName(updatedDocumentName);
        documentDetailsPage.signOut();
        super.tearDown();
    }

    @Override
    @BeforeMethod (alwaysRun = true)
    public void setUp() {
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDS();
    }

    @Test (description = "Check #5 of TLOR-613", groups = "documentUpdateTest")
    public void recentDocumentsTest() {
        recentDocumentsPage = cdsHomePage.getRecentDocumentsPanel().goToRecentDocumentsPage();

        assertFalse(recentDocumentsPage.getDataTable().doesTableContainDocumentWithName(updatedDocumentName),
                "User who hasn't updated document '" + updatedDocumentName + "' has it in his Recent Documents list.");
    }

    @Test (description = "Check #6 of TLOR-613")
    public void documentsFilteringOnRecentDocumentsPageTest() {
        String matchingWord = "SSP";
        recentDocumentsPage = cdsHomePage.getRecentDocumentsPanel().goToRecentDocumentsPage();
        List<String> listOfDocumentsNameOfWhichContainWordBeforeFiltering = recentDocumentsPage.getDataTable()
                .getTitlesOfDocumentsWhichContain(matchingWord);
        recentDocumentsPage.enterQueryIntoFilterInputField(matchingWord);
        List<String> listOfDocumentsNameOfWhichContainsWordAfterFiltering =
                recentDocumentsPage.getDataTable().getTitlesOfDocumentsWhichContain(matchingWord);

        assertTrue(listOfDocumentsNameOfWhichContainsWordAfterFiltering.size() >=
                listOfDocumentsNameOfWhichContainWordBeforeFiltering.size(),"A number of documents, titles of which " +
                "contain word '" + matchingWord + "' before filtering is not less than or equals to the number of matched " +
                "documents returned after filtering == documents might be not sorted out of all documents existed in CDS!");
    }

    @Test (description = "Check #73 of TLOR-613")
    public void documentAppearingOnRecentDocumentsPageAfterNameUpdateTest() {
        String newNameForTextFile = "text_file" + + RandomizationUtil.getRandomNumber(100, 999) + ".txt";

        documentsPage = cdsHomePage.openDocumentsPage();
        documentsPage.navigateToFolder(BASE_CDS_FOLDER + "|" + RECENT_DOCUMENTS);
        documentDetailsPage = documentsPage.getFilteredTableItem("text_file", false).goToDocumentDetails();
        documentDetailsPage.editDocumentName(newNameForTextFile);
        recentDocumentsPage = documentDetailsPage.openHomePage().getRecentDocumentsPanel().goToRecentDocumentsPage();
        RowOfTableOnRecentDocumentsPage firstTableRow = recentDocumentsPage.getDataTable().getRow(0);
        String firstDocumentInTable = firstTableRow.getItemName();
        String updateDateOfFirstDocumentInTable = firstTableRow.getTimeStampOfLastDocumentUpdate();
        String currentDate = new SimpleDateFormat("MMM d, yyyy").format(new Date());

        assertEquals(firstDocumentInTable, newNameForTextFile,
                "The actual first document in the table on the Recent Documents page is not the one uploaded by the test.");
        assertEquals(updateDateOfFirstDocumentInTable, currentDate, "The actual update date of the first " +
                "document in the table on the Recent Documents page does not correspond to the current date.");
    }

    @Override
    @AfterMethod (alwaysRun = true)
    public void tearDown() {
        super.tearDown();
    }
}