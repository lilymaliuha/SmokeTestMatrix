package cdssmoketest.classificationstests;

import cdssmoketest.BaseCDSTest;
import com.xyleme.bravais.datacontainers.ClassificationType;
import com.xyleme.bravais.utils.Emails;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsclassificationspage.CDSClassificationsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.PropertiesPane;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdssearchpage.CDSSearchPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdssearchpage.searchresultsblock.SearchResultItem;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.*;
import javax.mail.Message;

import java.util.Arrays;
import java.util.List;

import static com.xyleme.bravais.datacontainers.StringConstants.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Tests which verify functionality of a new classification import and modification (Checks ##41 - 46).
 */
public class TestClassificationImportAndModification extends BaseCDSTest {
    private CDSClassificationsPage classificationsPage;
    private String classificationName = "Solar System Taxonomy";
    private String classificationNameWithVocabID = "12345678 " + classificationName;
    private String newNameWithVocabID = "123 " + classificationName + " updated";
    private String childSibling = "testsibling1";

    @Override
    @BeforeClass (alwaysRun = true)
    public void setUp() {
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDSWithUser(ENVIRONMENT.env.get("usernameOfUserForEmailsProcessing"),
                ENVIRONMENT.env.get("passwordOfUserForEmailsProcessing"));
        classificationsPage = cdsHomePage.openClassificationsPage();
    }

    @Test (description = "Check #41 of TLOR-613")
    public void importClassificationTest() {
        Emails emails = new Emails();
        emails.deleteMessages();
        classificationsPage.importClassification(MML_FOR_UPLOADING);
        staticSleep(10); // Time for email sending.

        assertTrue(classificationsPage.getClassificationEditorBlock().getAvailableClassificationElements(0)
                .contains(classificationNameWithVocabID), "Expected classification has not been imported.");
        Message message = emails.getMostRecentEmail();
        String[] emailBody = emails.getEmailBody(message);
//        System.out.println("===================================== Email message: ====================================");
//        Arrays.asList(emailBody).forEach(option -> System.out.println(">> " + option));
//        System.out.println("=========================================================================================");

        assertTrue(emailBody[0].contains("Classifications were successfully imported"),
                "The most recent email received by the user doesn't contain expected statement." +
                        "\nNOTE: Uncomment the commented line above the verification to check the full email message text.");
    }

    @Test (description = "Checks ##42 - 44 of TLOR-613", dependsOnMethods = "importClassificationTest", retryAnalyzer =
            IRetryAnalyzer.class)
    public void editClassificationNodesTest() {
        classificationsPage.editClassificationElement(classificationNameWithVocabID, "123",
                classificationName + " updated", ClassificationType.COMPETENCY);

        driver.navigate().refresh();
        classificationsPage.waitUntilAvailable(); // ToDo: Workaround for LOR-6873.

        assertTrue(classificationsPage.getClassificationEditorBlock().getAvailableClassificationElements(0)
                .contains(newNameWithVocabID), "Classification's root node has not been updated.");
        String childNode = "testchild1";
        classificationsPage.addChildElementToClassification(newNameWithVocabID, "", childNode);

        assertTrue(classificationsPage.getClassificationEditorBlock().getAvailableClassificationElements(1)
                .contains(childNode), "Expected child node has not been added to the classification.");
        classificationsPage.addSiblingElementToClassification(newNameWithVocabID + "|" + childNode,
                "", childSibling);

        assertTrue(classificationsPage.getClassificationEditorBlock().getAvailableClassificationElements(1)
                .contains(childSibling), "Expected sibling node (sibling to the child node added in the previous step)" +
                " has not been added to the classification.");
    }

    @Test (description = "Check #45 of TLOR-613", dependsOnMethods = "editClassificationNodesTest", retryAnalyzer =
            IRetryAnalyzer.class)
    public void classificationAssignmentToDocumentTest() {
        documentsPage = classificationsPage.openDocumentsPage();
        documentsPage.navigateToFolder(BASE_CDS_FOLDER + "|" + PREVIEW);
        documentDetailsPage = documentsPage.getFilteredTableItem(XLTX_FILE, true).goToDocumentDetails();
        PropertiesPane propertiesPane = documentDetailsPage.selectPropertiesTabAndGetPane();
        propertiesPane.assignClassificationToDocument(newNameWithVocabID + "|" + childSibling);
        List<String> documentClassifications = propertiesPane.getListOfDocumentClassifications();

        assertTrue(documentClassifications.contains(newNameWithVocabID + " > " + childSibling),
                "List of classifications assigned to the document doesn't contain expected classification." +
                        "\nActual list of document classifications: " + documentClassifications);
    }

    @Test (description = "Check #46 of TLOR-613", dependsOnMethods = "classificationAssignmentToDocumentTest",
            retryAnalyzer = IRetryAnalyzer.class)
    public void searchByAssignedClassificationTest() {
        CDSSearchPage searchPage = classificationsPage.openSearchPage();
        searchPage.getClassificationsPanel().setUpClassificationFilter(newNameWithVocabID + "|" + childSibling);
        SearchResultItem resultItem = searchPage.getSearchResultsContainer().getSearchResultItem(0);

        assertEquals(resultItem.getDocumentName(), XLTX_FILE, "Expected document which was assigned to the " +
                "classification element set in the filter is not displayed in the search results (or is not the " +
                "first in the results list)");
    }

    @Override
    @AfterClass (alwaysRun = true)
    public void tearDown() {
        driver.get(ENVIRONMENT.env.get("CDS_URL"));
        cdsHomePage.waitUntilAvailable();
        classificationsPage = cdsHomePage.openClassificationsPage();
        classificationsPage.removeClassificationElement(newNameWithVocabID);
        super.tearDown();
    }
}