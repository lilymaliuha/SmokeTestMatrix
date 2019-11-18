package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.commentsblock;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.LabelText;
import com.xyleme.bravais.web.elements.TextInput;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.PreviewPane;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Comments block available on Document Details page.
 */
public class CommentsBlock extends PreviewPane {

    public CommentsBlock(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    public CommentsBlock(WebDriver driver, boolean waitForBlockToBeAvailable) {
        super(driver);

        if (waitForBlockToBeAvailable) {
            this.waitUntilAvailable();
        }
    }

    @Override
    public boolean isAvailable() {
        return commentsBlockBody().isAvailable() &&
                blockHeader().isAvailable() &&
                commentInputField().isAvailable() &&
                postCommentButton().isAvailable();
    }

    private Element commentsBlockBody() {
        return new Element(driver, By.xpath("//div[starts-with(@class, 'document-comments ')]"));
    }

    private LabelText blockHeader() {
        return new LabelText(driver, By.xpath(commentsBlockBody().getXpathLocatorValue() +
                "//div[@class='document-comments-header']//span"));
    }

    private TextInput commentInputField() {
        return new TextInput(driver, By.xpath(commentsBlockBody().getXpathLocatorValue() +
                "//form[@name='createCommentForm']//textarea"));
    }

    private Button postCommentButton() {
        return new Button(driver, By.xpath(commentsBlockBody().getXpathLocatorValue() +
                "//span[text()='Post Comment']/parent::button"));
    }

    /**
     * Gets number of comments displayed in the Comments Block header.
     *
     * @return {@code int}
     */
    public int getNumberOfCommentsDisplayedInBlockHeader() {
        if (blockHeader().getText().equals("No Comments")) {
            return 0;
        } else {
            return Integer.parseInt(blockHeader().getText().replaceAll("\\D+", ""));
        }
    }

    /**
     * Enters specified comment into the Comment input field.
     *
     * @param comment - Specifies text of the comment intended to be entered
     * @return {@code CommentsBlock}
     */
    private CommentsBlock enterComment(String comment) {
        commentInputField().clear();
        commentInputField().sendKeys(comment);
        return this;
    }

    /**
     * Checks if the Post Comment button is enabled.
     *
     * @return {@code boolean}
     */
    private boolean isPostCommentButtonEnabled() {
        return postCommentButton().getAttribute("disabled") == null;
    }

    /**
     * Adds specified comment to a document.
     *
     * @param comment - Specifies text of the comment intended to be added
     * @return {@code CommentsBlock}
     */
    public CommentsBlock addComment(String comment) {
        enterComment(comment);

        if (isPostCommentButtonEnabled()) {
            postCommentButton().click();
            new CommentsContainer(driver).waitUntilAvailable();
        }
        return this;
    }

    /**
     * Gets comments container (the container is available only if at least one comment is added to a document).
     *
     * @return {@code CommentsContainer}
     */
    private CommentsContainer getCommentsContainer() {
        return new CommentsContainer(driver);
    }

    /**
     * Gets specific comment from a comments container.
     *
     * @param commentIndex - Specifies index of the comment
     * @return {@code Comment}
     */
    public Comment getComment(int commentIndex) {
        return getCommentsContainer().getComment(commentIndex);
    }
}