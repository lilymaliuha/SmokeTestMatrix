package cdssmoketest.customattributestests;

import cdssmoketest.BaseCDSTest;
import com.xyleme.bravais.datacontainers.customattributesdata.CABooleanTypeParameter;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnCustomAttributesPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdssearchpage.CDSSearchPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdssearchpage.searchresultsblock.SearchResultItem;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdschanneldetailspage.CDSChannelDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSChannelsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.PropertiesPane;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdscustomattributespage.CDSCustomAttributesPage;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.*;

import java.util.List;

import static com.xyleme.bravais.datacontainers.StringConstants.*;
import static org.testng.Assert.*;

/**
 * Tests which verify Custom Attributes functionality and (partly) Channels functionality (Checks ##47 - 52 of TLOR-613).
 */
public class TestCustomAttributeFunctionality extends BaseCDSTest {
    private CDSCustomAttributesPage customAttributesPage;
    private CDSChannelsPage channelsPage;
    private CDSChannelDetailsPage cdsChannelDetailsPage;
    private CDSSearchPage searchPage;
    private SearchResultItem resultItem;
    private String customAttribute = "custom attribute " + getRandomNumber(100001, 900000);
    private String originalChannelName = "AAA test channel";
    private String updatedChannelName = originalChannelName + " 1";

    @BeforeClass (alwaysRun = true)
    public void setUp() {
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDS();
        customAttributesPage = cdsHomePage.openCustomAttributesPage();
    }

    @Test (description = "Check #47 of TLOR-613")
    public void newCustomAttributeCreationTest() {
        customAttributesPage.addCustomAttributeOfBooleanType(customAttribute, true);
        customAttributesPage.enterQueryIntoFilterInputField(customAttribute);
        RowOfTableOnCustomAttributesPage row = customAttributesPage.getDataTable().getRow(0);

        assertEquals(row.getAttributeName(), customAttribute,
                "Actual name of the created custom attribute differs from the expected one.");
        assertTrue(row.getSearchCheckbox().isSelected(),
                "Search parameter of the created custom output differs from the expected one.");
        assertEquals(row.getAttributeType(), "boolean",
                "Actual type of the created custom attribute differs from the expected one.");
        assertEquals(row.getAttributeValues(), "Yes, No",
                "Actual Values parameter of the created custom output differs from the expected one.");
    }

    @Test (description = "Check #48 of TLOR-613", dependsOnMethods = "newCustomAttributeCreationTest", retryAnalyzer =
            IRetryAnalyzer.class)
    public void customAttributeAssignmentToDocumentTest() {
        documentsPage = customAttributesPage.openDocumentsPage();
        documentsPage.navigateToFolder(BASE_CDS_FOLDER + "|" + PREVIEW);
        documentDetailsPage = documentsPage.getFilteredTableItem(JPEG_FILE, true).goToDocumentDetails();
        PropertiesPane propertiesPane = documentDetailsPage.selectPropertiesTabAndGetPane();
        propertiesPane.assignCustomAttributeOfBooleanTypeToDocument(customAttribute, CABooleanTypeParameter.YES);
        List<String> documentCustomAttributes = propertiesPane.getListOfDocumentCustomAttributes();

        assertTrue(documentCustomAttributes.contains(customAttribute + ":"),
                "List of custom attributes assigned to the document doesn't contain expected custom attribute." +
                        "\nActual list of document custom attributes: " + documentCustomAttributes);
    }

    @Test (description = "Checks #49 and #50 of TLOR-613", dependsOnMethods = "customAttributeAssignmentToDocumentTest",
            retryAnalyzer = IRetryAnalyzer.class)
    public void newChannelCreatingAndEditingTest() {
        channelsPage = customAttributesPage.openChannelsPage();
        cdsChannelDetailsPage = channelsPage.createNewChannel(originalChannelName, "", "");

        assertTrue(cdsChannelDetailsPage.isAvailable(),"The channel Details page has not been opened after " +
                "creating a new channel (the channel might not be created).");
        assertEquals(cdsChannelDetailsPage.getChannelName(), originalChannelName,"Actual name displayed on the " +
                "channel Details page of the newly created channel differs from the expected one.");
        cdsChannelDetailsPage.editChannelName(updatedChannelName);

        assertEquals(cdsChannelDetailsPage.getChannelName(), updatedChannelName,
                "Actual name of the channel after editing attempt differs from the expected one.");
    }

    @Test (description = "Check #51 of TLOR-613", dependsOnMethods = "newChannelCreatingAndEditingTest", retryAnalyzer =
            IRetryAnalyzer.class)
    public void settingCustomAttributeAsCriterionForChannelTest() {
        cdsChannelDetailsPage.selectQueryTabAndGetPane().selectFacetOption(customAttribute, CABooleanTypeParameter.YES
                .getValue());
        searchPage = cdsChannelDetailsPage.clickSearchButton();
        resultItem = searchPage.getSearchResultsContainer().getSearchResultItem(0);

        assertEquals(resultItem.getDocumentName(), JPEG_FILE,
                "Actual document returned after searching for documents with assigned custom attribute '" +
                        customAttribute + "' using respective channel differs from the expected one.");
        assertEquals(resultItem.getDocumentLocationPath(), ROOT_FOLDER + "|" + BASE_CDS_FOLDER + "|" + PREVIEW,
                "Actual path of the document returned after searching for documents with assigned custom attribute "
                        + customAttribute + "' using respective channel differs from the expected one.");
    }

    @Test (description = "Check #52 of TLOR-613", dependsOnMethods = "settingCustomAttributeAsCriterionForChannelTest",
            retryAnalyzer = IRetryAnalyzer.class)
    public void searchingByChannelWithMultipleSearchCriteriaTest() {
        channelsPage = searchPage.openChannelsPage();
        cdsChannelDetailsPage = channelsPage.getFilteredChannel(updatedChannelName, true).goToChannelDetailsPage();
        cdsChannelDetailsPage.selectQueryTabAndGetPane().unselectFacetOption(customAttribute, CABooleanTypeParameter.YES
                .getValue());
        cdsChannelDetailsPage.selectQueryTabAndGetPane().selectFacetOption("Resource Type", "Media");
        cdsChannelDetailsPage.selectQueryTabAndGetPane().selectFacetOption("Format", "Video");
        cdsChannelDetailsPage.selectQueryTabAndGetPane().selectFacetOption("Language", "Indonesian");
        cdsHomePage = cdsChannelDetailsPage.openHomePage();
        searchPage = cdsHomePage.getChannelsBlock().goToChannel(updatedChannelName);

        resultItem = searchPage.getSearchResultsContainer().getSearchResultItem(0);

        assertEquals(resultItem.getDocumentName(), M4V_FILE,"Actual document returned after searching for " +
                "documents using parameterized channel '" + updatedChannelName + "' differs from the expected one.");
        assertEquals(resultItem.getDocumentLocationPath(), ROOT_FOLDER + "|" + BASE_CDS_FOLDER + "|" +
                DOCUMENTS_FOR_SEARCH_TESTING, "Actual path of the document returned after searching for " +
                "documents using parameterized channel '" + updatedChannelName + "' differs from the expected one.");
    }

    @AfterClass (alwaysRun = true)
    public void tearDown() {
        driver.get(ENVIRONMENT.env.get("CDS_URL"));
        cdsHomePage.waitUntilAvailable();
        // Channel deleting:
        channelsPage = cdsHomePage.openChannelsPage();
        channelsPage.enterQueryIntoFilterInputField(updatedChannelName);
        channelsPage.getDataTable().getRow(0).deleteChannel();
        // Custom attribute deleting:
        customAttributesPage = channelsPage.openCustomAttributesPage();
        customAttributesPage.enterQueryIntoFilterInputField(customAttribute);
        customAttributesPage.getDataTable().getRow(0).deleteAttribute();
        super.tearDown();
    }
}