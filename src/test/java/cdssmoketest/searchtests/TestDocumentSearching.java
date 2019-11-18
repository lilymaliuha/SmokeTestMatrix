package cdssmoketest.searchtests;

import cdssmoketest.BaseCDSTest;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdssearchpage.CDSSearchPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdssearchpage.searchresultsblock.SearchResultItem;
import org.openqa.selenium.logging.LogEntries;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

import static com.xyleme.bravais.datacontainers.StringConstants.*;
import static org.testng.Assert.*;

/**
 * Tests which verify documents searching functionality (Search page) (Checks ##53 - 54 of TLOR-613).
 */
public class TestDocumentSearching extends BaseCDSTest {
    private CDSSearchPage searchPage;

    @Override
    @BeforeClass(alwaysRun = true)
    public void setUp() {
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDS();
        searchPage = cdsHomePage.openSearchPage();
    }

    @Test (description = "Checks #53 and #54 of TLOR-613")
    public void documentSearchUsingSearchCriteriaOnSearchPageTest() {
        searchPage.getResourceTypePanel().selectPanelItemCheckbox("Media");
        searchPage.getFormatPanel().selectPanelItemCheckbox("Video");
        searchPage.getLanguagePanel().selectPanelItemCheckbox("Indonesian");
        SearchResultItem resultItem = searchPage.getSearchResultsContainer().getSearchResultItemWithDocumentName(M4V_FILE);

        assertNotNull(resultItem, "Expected document is not found after searching using specified search criteria.");
        assertEquals(resultItem.getDocumentLocationPath(),ROOT_FOLDER + "|" + BASE_CDS_FOLDER + "|" +
                DOCUMENTS_FOR_SEARCH_TESTING, "Location path of the returned document differs from the expected " +
                "one == expected document is not returned.");
        verifyConsoleLogs();
    }

    @Override
    @AfterClass (alwaysRun = true)
    public void tearDown() {
        super.tearDown();
    }

    /**
     * Verifies if browser console log doesn't contain any errors or exceptions.
     */
    private void verifyConsoleLogs() {
        List<Boolean> resultsForErrorKeywordCheck = new ArrayList<>();
        List<Boolean> resultsForExceptionKeywordCheck = new ArrayList<>();
        StringBuilder fullLog = new StringBuilder();
        LogEntries consoleLogs = driver.manage().logs().get("browser");
        consoleLogs.forEach(logEntry -> fullLog.append("- ").append(logEntry.getMessage()).append("\n"));
        consoleLogs.forEach(logEntry -> resultsForErrorKeywordCheck.add(logEntry.getMessage().toLowerCase().contains(
                "error")));
        consoleLogs.forEach(logEntry -> resultsForExceptionKeywordCheck.add(logEntry.getMessage().toLowerCase().contains(
                "exception")));

        assertFalse(resultsForErrorKeywordCheck.contains(true),"Console logs contain an error.\n> Logs:\n" + fullLog);
        assertFalse(resultsForExceptionKeywordCheck.contains(true),"Console logs contain an exception.\n> Logs:\n" +
                fullLog);
    }
}