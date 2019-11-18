package cdssmoketest.documentcommentstests;

import cdssmoketest.BaseCDSTest;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.PreviewPane;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.commentsblock.CommentsBlock;
import org.testng.annotations.*;

import static com.xyleme.bravais.datacontainers.StringConstants.*;
import static org.testng.Assert.*;

/**
 * Tests which verify functionality of document comments (Checks ##22-26 of TLOR-613)
 */
public class TestCommentAddingEditingDeleting extends BaseCDSTest {
    private CommentsBlock commentsBlock;

    @Override
    @BeforeClass (alwaysRun = true)
    public void setUp() {
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDS();
        documentsPage = cdsHomePage.openDocumentsPage();
        documentsPage.navigateToFolder(BASE_CDS_FOLDER + "|" + PREVIEW);
        documentDetailsPage = documentsPage.getFilteredTableItem(JPEG_FILE, true).goToDocumentDetails();
    }

    @Test (description = "Checks #22, #24 and #26 of TLOR-613")
    public void addEditAndDeleteCommentTest() {
        String comment = "Test Comment";

        PreviewPane previewPane = documentDetailsPage.selectPreviewTabAndGetPane();
        commentsBlock = previewPane.getCommentsBlock();
        int numberOfCommentsBeforeAddingNewComment = commentsBlock.getNumberOfCommentsDisplayedInBlockHeader();
        commentsBlock.addComment(comment);

        assertEquals(commentsBlock.getNumberOfCommentsDisplayedInBlockHeader(),
                numberOfCommentsBeforeAddingNewComment + 1, "Actual number of comments displayed in the " +
                        "Comments block header after adding a new comment differs from the expected one.");
        assertEquals(commentsBlock.getComment(0).getCommentText(), comment,
                "The comment has not been added to the document or the comment's text differs from the expected one.");
        commentsBlock.getComment(0).editComment(comment + " Edited");

        assertEquals(commentsBlock.getComment(0).getCommentText(), comment + " Edited",
                "The comment has not been updated as expected.");
        commentsBlock.getComment(0).deleteComment();

        assertEquals(commentsBlock.getNumberOfCommentsDisplayedInBlockHeader(), numberOfCommentsBeforeAddingNewComment,
                "The comment has not been deleted.");
    }

    @Override
    @AfterMethod (alwaysRun = true)
    public void tearDown() {

        if (commentsBlock.getNumberOfCommentsDisplayedInBlockHeader() > 0) {
            commentsBlock.getComment(0).deleteComment();
        }
    }

    @AfterClass (alwaysRun = true)
    public void classTearDown() {
        super.tearDown();
    }
}