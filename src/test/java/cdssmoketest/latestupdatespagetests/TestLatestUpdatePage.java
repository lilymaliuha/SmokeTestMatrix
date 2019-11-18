package cdssmoketest.latestupdatespagetests;

import cdssmoketest.BaseCDSTest;
import com.xyleme.bravais.utils.RandomizationUtil;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSLatestUpdatesPage;
import org.testng.annotations.*;

import java.util.List;

import static com.xyleme.bravais.datacontainers.StringConstants.*;
import static org.testng.Assert.*;

/**
 * Tests which verify functionality of Latest Updates panel on Home page and Latest Updates page (Checks #3, #4 of TLOR-613).
 */
public class TestLatestUpdatePage extends BaseCDSTest {
    private CDSLatestUpdatesPage latestUpdatesPage;

    @Override
    @BeforeClass (alwaysRun = true)
    public void setUp() {
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDS();
    }

    @Test (description = "Steps 2-5 of TLOR-9")
    public void latestUpdatesPanelAvailabilityOnHomePageTest() {
        assertEquals(cdsHomePage.getIndexOfPanelWithTitle("Latest Updates"), 0,
                "'Latest Updates' panel is not the first in the list of panels displayed at the right side of the page.");
        assertFalse(cdsHomePage.getLatestUpdatesPanel().isPanelCollapsed(),
                "'Latest Updates' panel is not expanded by default.");
        assertTrue(cdsHomePage.getLatestUpdatesPanel().isPanelHeaderLinkAvailable(),
                "Header link of the 'Latest Updates' panel is not available on the page.");
        assertTrue(cdsHomePage.getFavoritesPanel().isCollapseExpandLinkAvailable(),
                "'show'/'hide' link of the 'Latest Updates' panel is not available on the page.");
        assertEquals(cdsHomePage.getLatestUpdatesPanel().getNumberOfPanelListItems(), 5, "" +
                "'Latest Updates' panel on the CDS Home page doesn't contain expected number of documents in its list.");
    }

    @Test (description = "Steps 6, 8 of TLOR-9")
    public void collapseExpandLatestUpdatesPanelOnHomePageTest() {
        assertEquals(cdsHomePage.getLatestUpdatesPanel().getNameOfHideShowLink(),"hide",
                "Name of the 'hide'/'show' link of the 'Latest Updates' panel displayed on the CDS Home page " +
                        "does not state 'hide' by default or the panel is not expanded by default.");
        cdsHomePage.getLatestUpdatesPanel().collapsePanel();

        assertEquals(cdsHomePage.getLatestUpdatesPanel().getNameOfHideShowLink(), "show",
                "Name of the 'hide'/'show' link of the 'Latest Updates' panel displayed on the CDS Home page did " +
                        "not change from 'hide' to 'show' after collapsing the panel.");
        assertTrue(cdsHomePage.getLatestUpdatesPanel().isPanelCollapsed(),
                "'Latest Updates' panel was not collapsed after clicking the 'hide' link.");
        cdsHomePage.getLatestUpdatesPanel().expandPanel();

        assertEquals(cdsHomePage.getLatestUpdatesPanel().getNameOfHideShowLink(), "hide",
                "Name of the 'hide'/'show' link of the 'Latest Updates' panel displayed on the CDS Home page did " +
                        "not change from 'show' to 'hide' after expanding the panel.");
        assertFalse(cdsHomePage.getLatestUpdatesPanel().isPanelCollapsed(),
                "'Latest Updates' panel was not expanded after clicking the 'show' link.");
        assertEquals(cdsHomePage.getLatestUpdatesPanel().getNumberOfPanelListItems(), 5,
                "Actual number of documents listed in the 'Latest Updates' panel on the CDS Home page after " +
                        "collapsing and expanding the panel differs from the expected one.");
    }

    @Test (description = "Step 9 of TLOR-9")
    public void goToLatestUpdatesPageByClickingHeaderLinkOfLatestUpdatesPanelOnHomePageTest() {
        latestUpdatesPage = cdsHomePage.getLatestUpdatesPanel().goToLatestUpdatesPage();

        assertTrue(latestUpdatesPage.isAvailable(), "'Latest Updates' page was not opened after clicking the " +
                "header link of the 'Latest Update' panel on the CDS Home page.");
    }

    @Test (description = "Step 10 of TLOR-9")
    public void goToDocumentDetailsPageByClickingOnDocumentListedInLatestUpdatesPanelOnHomePageTest() {
        documentDetailsPage = cdsHomePage.getLatestUpdatesPanel().clickOnPanelListItem(1);

        assertTrue(documentDetailsPage.isAvailable(), "Document Details page was not opened after clicking on a " +
                "document (2nd in the list) listed in the 'Latest Updates' panel on the CDS Home page.");
    }

    @Test (description = "Steps 11-13 of TLOR-9") // Was failed by LOR-6741, has been fixed in v7.8 / Failed by LOR-6969 (found in 7.11.1)
    public void updateDocumentAndVerifyThatItAppearedInLatestUpdatesPanelOnHomePageTest() { // ToDo: Keep an eye on this one. It used to be unstable.
        documentsPage = cdsHomePage.openDocumentsPage();
        documentsPage.navigateToFolder(BASE_CDS_FOLDER + "|" + LATEST_UPDATES);
        documentDetailsPage = documentsPage.getFilteredTableItem("Doc", false).goToDocumentDetails();
                String newDocumentName = "Doc " + RandomizationUtil.getRandomNumber(100, 999);
        documentDetailsPage.editDocumentName(newDocumentName);
        cdsHomePage = documentDetailsPage.openHomePage();

        assertEquals(cdsHomePage.getLatestUpdatesPanel().getPanelListItemTitle(0), newDocumentName,"Name of " +
                "the document which has been updated by the test hasn't changed in the Latest Updates panel on Home page.");
    }

    @Test (description = "Check #4 of TLOR-613")
    public void filterDocumentsOnLatestUpdatesPageTest() {
        String matchingWord = "SSP";
        latestUpdatesPage = cdsHomePage.getLatestUpdatesPanel().goToLatestUpdatesPage();
        List<String> listOfDocumentsNameOfWhichContainWordBeforeFiltering =
                latestUpdatesPage.getDataTable().getTitlesOfDocumentsWhichContain(matchingWord);

        latestUpdatesPage.enterQueryIntoFilterInputField(matchingWord);
        List<String> listOfDocumentsNameOfWhichContainWordAfterFiltering =
                latestUpdatesPage.getDataTable().getTitlesOfDocumentsWhichContain(matchingWord);

        assertTrue(listOfDocumentsNameOfWhichContainWordAfterFiltering.size() >
                listOfDocumentsNameOfWhichContainWordBeforeFiltering.size(),"A number of documents, titles of which " +
                "contain word '" + matchingWord + "' before filtering is not less than the number of matched documents " +
                "returned after filtering == documents might be not sorted out of all documents existed in CDS!");
    }

    @Override
    @AfterMethod (alwaysRun = true)
    public void tearDown() {
        if (!cdsHomePage.isAvailable()) {
            driver.get(ENVIRONMENT.env.get("CDS_URL"));
            cdsHomePage.waitUntilAvailable();
        }
    }

    @AfterClass (alwaysRun = true)
    public void classTearDown() {
        super.tearDown();
    }
}