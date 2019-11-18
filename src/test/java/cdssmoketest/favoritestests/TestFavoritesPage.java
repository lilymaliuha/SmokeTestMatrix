package cdssmoketest.favoritestests;

import cdssmoketest.BaseCDSTest;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSFavoritesPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.CDSDocumentDetailsPage;
import org.testng.annotations.*;

import java.util.List;

import static com.xyleme.bravais.datacontainers.StringConstants.*;
import static org.testng.Assert.*;

/**
 * Tests which verify functionality of 'Favorites' page (Checks #7, #8, #9, #10 of TLOR-613).
 */
public class TestFavoritesPage extends BaseCDSTest {
    private CDSFavoritesPage favoritesPage;
    private boolean resetFilter;
    private String documentA = "image.png";
    private static String documentB = "presentation.pptx";
    private String documentC = "text.doc";

    @DataProvider (name = "filteringData")
    private static Object[][] filteringData() {
        return new Object[][] {
                {"Fol", FOLDER_A},
                {"pptx", documentB}
        };
    }

    @Override
    @BeforeClass (alwaysRun = true)
    public void setUp() {
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDS();
        documentsPage = cdsHomePage.openDocumentsPage();
        documentsPage.navigateToFolder(BASE_CDS_FOLDER + "|" + FAVORITES);
        documentsPage.getFilteredTableItem(FOLDER_A, true).markOrUnmarkAsFavorite(true);
        documentsPage.navigateToFolder(DOCUMENTS);
        documentsPage.getFilteredTableItem(documentA, true).markOrUnmarkAsFavorite(true);
        documentsPage.getFilteredTableItem(documentB, true).markOrUnmarkAsFavorite(true);
        documentsPage.getFilteredTableItem(documentC, true).markOrUnmarkAsFavorite(true);
        cdsHomePage = documentsPage.openHomePage();
        favoritesPage = cdsHomePage.getFavoritesPanel().goToFavoritesPage();
    }

    @Test (description = "Check #7 of TLOR-613")
    public void favoriteDocumentsDisplayOnFavoritesPageTest() {
        resetFilter = false;
        assertTrue(favoritesPage.getDataTable().doesTableContainDocumentWithName(FOLDER_A),
                "Documents table on Favorites page doesn't contain expected folder - '" + FOLDER_A + "'.");
        assertTrue(favoritesPage.getDataTable().doesTableContainDocumentWithName(documentA),
                "Documents table on Favorites page doesn't contain expected document - '" + documentA + "'.");
        assertTrue(favoritesPage.getDataTable().doesTableContainDocumentWithName(documentB),
                "Documents table on Favorites page doesn't contain expected document - '" + documentB + "'.");
        assertTrue(favoritesPage.getDataTable().doesTableContainDocumentWithName(documentC),
                "Documents table on Favorites page doesn't contain expected document - '" + documentC + "'.");
    }

    @Test (description = "Check #8 of TLOR-613", dataProvider = "filteringData")
    public void filterFavoritesTest(String matchingWord, String filterQuery) {
        resetFilter = true;
        List<String> matchesBeforeFiltering =
                favoritesPage.getDataTable().getTitlesOfDocumentsWhichContain(matchingWord);
        favoritesPage.enterQueryIntoFilterInputField(filterQuery);
        List<String> matchesAfterFiltering =
                favoritesPage.getDataTable().getTitlesOfDocumentsWhichContain(matchingWord);

        assertEquals(matchesBeforeFiltering, matchesAfterFiltering,
                "List of titles of table items which contain word '" + matchingWord + "' before filtering differs " +
                        "from the list of titles returned after filtering by word '" + filterQuery +"'." +
                "\n >> Filter might retrieve documents/folders which are not marked as Favorite.");
    }

    @Test (description = "Checks #9 and #10 of TLOR-613", priority = 1)
    public void favoritesBadgePresenceOnDocumentDetailsPageTest() {
        resetFilter = false;
        CDSDocumentDetailsPage documentDetailsPage = favoritesPage.getFilteredTableItem(documentA).goToDocumentDetails();

        assertTrue(documentDetailsPage.isItemMarkedAsFavorite(),
                "Document '" + documentA + "' which is actually marked as favorite (it belongs to a list of " +
                        "documents/folders on Favorites page) is not marked as favorite on the Document Details page.");
        cdsHomePage = documentDetailsPage.openHomePage();
        favoritesPage = cdsHomePage.getFavoritesPanel().goToFavoritesPage();
        favoritesPage.getFilteredTableItem(documentA).removeFromFavorites();
        documentsPage = favoritesPage.openDocumentsPage();
        documentsPage.navigateToFolder(BASE_CDS_FOLDER + "|" + FAVORITES + "|" + DOCUMENTS);
        documentDetailsPage = documentsPage.getFilteredTableItem(documentA, true).goToDocumentDetails();

        assertFalse(documentDetailsPage.isItemMarkedAsFavorite(),"Document '" + documentA + "' which was removed " +
                "from Favorites list on the Favorites page remains marked as favorite on the Document Details page.");
    }

    @Override
    @AfterMethod (alwaysRun = true)
    public void tearDown() {
        if (resetFilter) {
            favoritesPage.resetFilter();
        }
    }

    @AfterClass (alwaysRun = true)
    public void classTearDown() {

        if (!favoritesPage.isAvailable()) {
            driver.get(ENVIRONMENT.env.get("CDS_URL"));
            cdsHomePage.waitUntilAvailable();
            favoritesPage = cdsHomePage.getFavoritesPanel().goToFavoritesPage();
        }
        favoritesPage.getFilteredTableItem(FOLDER_A).removeFromFavorites();

        if (favoritesPage.getDataTable().doesTableContainDocumentWithName(documentA)) {
            favoritesPage.getFilteredTableItem(documentA).removeFromFavorites();
        }
        favoritesPage.getFilteredTableItem(documentB).removeFromFavorites();
        favoritesPage.getFilteredTableItem(documentC).removeFromFavorites();
        super.tearDown();
    }
}