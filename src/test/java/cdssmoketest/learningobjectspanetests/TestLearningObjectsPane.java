package cdssmoketest.learningobjectspanetests;

import cdssmoketest.BaseCDSTest;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSDocumentsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.LearningObjectsPane;
import org.testng.annotations.*;

import static com.xyleme.bravais.datacontainers.StringConstants.*;
import static org.testng.Assert.*;

/**
 * Tests which verify functionality of Learning Objects pane (Checks #22 and 23 of TLOR-613).
 */
public class TestLearningObjectsPane extends BaseCDSTest {
    private String URLOfPreviewFolder;

    @Override
    @BeforeClass (alwaysRun = true)
    public void setUp() {
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDS();
        documentsPage = cdsHomePage.openDocumentsPage();
        documentsPage.navigateToFolder(BASE_CDS_FOLDER + "|" + PREVIEW);
        URLOfPreviewFolder = driver.getCurrentUrl();

        if (!documentsPage.isItemPresentInFolder(PRESENTATION_FILE)) {
            documentDetailsPage = documentsPage.uploadFileFromFilesForUploadingFolder(PPTX_FILE);
            documentsPage = documentDetailsPage.navigateFromPageUsingLinkInBreadcrumbsBlock(PREVIEW, CDSDocumentsPage.class);
        }
    }

    @Test (description = "Check #22 of TLOR-613")
    public void learningObjectsPaneUnavailabilityForImageTest() {
        documentDetailsPage = documentsPage.getFilteredTableItem(JPEG_FILE, true).goToDocumentDetails();

        assertFalse(documentDetailsPage.isTabAvailable("Learning Objects"),
                "'Learning Objects' tab is available on a details page of the Image '" + JPEG_FILE + "'.");
    }

    @Test (description = "Check #23 of TLOR-613", groups = "pptFileCheck")
    public void learningObjectsPaneAvailabilityForPPTDocumentTest() {
        String learningObjectParentNodeName = PRESENTATION_FILE + " - Other";
        String learningObjectChildNodeAName = "Sample pptx - Other";
        String learningObjectChildNodeBName = "Slide 2 - Other";
        String learningObjectChildNodeCName = "Slide 3 - Other";


        documentDetailsPage = documentsPage.getFilteredTableItem(PRESENTATION_FILE, true).goToDocumentDetails();

        assertTrue(documentDetailsPage.isTabAvailable("Learning Objects"), "'Learning Objects' tab is not " +
                "available on a details page of the PowerPoint document '" + PRESENTATION_FILE + "'.");
        LearningObjectsPane learningObjectsPane = documentDetailsPage.selectLearningObjectsTabAndGetPane();

        assertTrue(learningObjectsPane.isParentNodeAvailable(learningObjectParentNodeName),
                "Expected learning object's node (parent node) is not available.");
        assertEquals(learningObjectsPane.getNumberOfChildNodesOfParentNode(learningObjectParentNodeName), 3,"Actual " +
                "number of child nodes of the specified parent node of the learning object differs from the expected one.");
        assertEquals(learningObjectsPane.getNameOfChildNode(learningObjectParentNodeName, 0),
                learningObjectChildNodeAName, "Actual name of the first child node of the specified parent node " +
                        "differs from the expected one.");
        assertEquals(learningObjectsPane.getNameOfChildNode(learningObjectParentNodeName, 1),
                learningObjectChildNodeBName, "Actual name of the second child node of the specified parent node " +
                        "differs from the expected one.");
        assertEquals(learningObjectsPane.getNameOfChildNode(learningObjectParentNodeName, 2),
                learningObjectChildNodeCName, "Actual name of the third child node of the specified parent node " +
                        "differs from the expected one.");
    }

    @Override
    @AfterMethod (alwaysRun =  true)
    public void tearDown() {
        documentsPage = documentDetailsPage.navigateFromPageUsingLinkInBreadcrumbsBlock(PREVIEW, CDSDocumentsPage.class);
    }

    @AfterGroups (value = "pptFileCheck", alwaysRun = true)
    public void removePPTFileAfterTest() {
        if (!documentsPage.isAvailable()) {
            driver.get(URLOfPreviewFolder);
            documentsPage.waitUntilAvailable();
        }
        documentsPage.getFilteredTableItem(PRESENTATION_FILE, true).archiveTableItem();
    }

    @AfterClass (alwaysRun = true)
    public void classTearDown() {
        super.tearDown();
    }
}