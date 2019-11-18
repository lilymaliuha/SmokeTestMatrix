package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.commentsblock;

import com.xyleme.bravais.web.elements.*;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.PreviewPane;
import com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs.CommentDeletingConfirmationDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of document Comment object.
 */
public class Comment extends CommentsContainer {
    private int commentIndex;

    Comment(WebDriver driver, int commentIndex) {
        super(driver);
        this.commentIndex = commentIndex;
        this.waitUntilAvailable();
    }

    private Comment(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAvailable() {
        return commenterImage().isAvailable() &&
                commenterName().isAvailable() &&
                commentText().isAvailable() &&
                editLink().isAvailable() &&
                deleteLink().isAvailable() &&
                commentTimeStamp().isAvailable();
    }

    private Element commentElement() {
        return new Element(driver, By.xpath("(//div[@class='comment'])[" + (commentIndex + 1) + "]"));
    }

    private Element commenterImage() {
        return new Element(driver, By.xpath(commentElement().getXpathLocatorValue() + "//td[@class='commenter-image']"));
    }

    private Element commentBody() {
        return new Element(driver, By.xpath(commentElement().getXpathLocatorValue() + "//td[@class='comment-body']"));
    }

    private LabelText commenterName() {
        return new LabelText(driver, By.xpath(commentBody().getXpathLocatorValue() +
                "/h3[contains(@class, 'commenter-name')]"));
    }

    private Element commentContentBody() {
        return new Element(driver, By.xpath(commentBody().getXpathLocatorValue() + "/div[@class='comment-content']"));
    }

    private LabelText commentText() {
        return new LabelText(driver, By.xpath(commentContentBody().getXpathLocatorValue() +
                "/span[starts-with(@class, 'comment-text')]"));
    }

    private Element commentContentControls() {
        return new Element(driver, By.xpath(commentContentBody().getXpathLocatorValue() +
                "/div[@class='comment-content-controls']"));
    }

    private Link editLink() {
        return new Link(driver, By.xpath(commentContentControls().getXpathLocatorValue() + "//span[text()='Edit']"));
    }

    private Link deleteLink() {
        return new Link(driver, By.xpath(commentContentControls().getXpathLocatorValue() +
                "//span[contains(@class, 'button') and normalize-space()='Delete']"));
    }

    private LabelText commentTimeStamp() {
        return new LabelText(driver, By.xpath(commentContentControls().getXpathLocatorValue() +
                "/span[starts-with(@class, 'comment-controls-date')]"));
    }

    /**
     * Gets commenter's name.
     *
     * @return {@code String}
     */
    public String getCommenterName() {
        return commenterName().readLabelText();
    }

    /**
     * Gets comment text.
     *
     * @return {@code String}
     */
    public String getCommentText() {
        return commentText().waitUntilAvailable().readLabelText();
    }

    /**
     * Gets comment's time stamp.
     *
     * @return {@code String}
     */
    public String getCommentTimeStamp() {
        return commentTimeStamp().readLabelText();
    }

    /**
     * Edits original comment.
     *
     * @param newCommentText - Specifies new comment the original one expected to be updated to
     * @return {@code Comment}
     */
    public Comment editComment(String newCommentText) {
        editLink().click();
        return new CommentEditingMode(driver).updateComment(newCommentText);
    }

    /**
     * Deletes the comment.
     *
     * @return {@code PreviewPane}
     */
    public PreviewPane deleteComment() {
        deleteLink().click();
        return new CommentDeletingConfirmationDialog(driver).confirmAction();
    }

    /**
     * Implementation of Comment Editing Mode.
     */
    class CommentEditingMode extends Comment {

        CommentEditingMode(WebDriver driver) {
            super(driver);
            this.waitUntilAvailable();
        }

        @Override
        public boolean isAvailable() {
            return editingTextInput().isAvailable() &&
                    cancelLink().isAvailable() &&
                    saveLink().isAvailable() &&
                    commentTimeStamp().isAvailable();
        }

        private TextInput editingTextInput() {
            return new TextInput(driver, By.xpath(commentBody().getXpathLocatorValue() +
                    "//textarea[starts-with(@class, 'comment-textarea')]"));
        }

        private Element editingModeBlock() {
            return new Element(driver, By.xpath(editingTextInput().getXpathLocatorValue() +
                    "/parent::div[not (contains(@class, 'hide'))]"));
        }

        private Element editingControls() {
            return new Element(driver, By.xpath(editingModeBlock().getXpathLocatorValue() +
                    "/div[@class='comment-content-controls']"));
        }

        private Link cancelLink() {
            return new Link(driver, By.xpath(editingControls().getXpathLocatorValue() + "/span[text()='Cancel']"));
        }

        private Link saveLink() {
            return new Link(driver, By.xpath(editingControls().getXpathLocatorValue() + "/span[normalize-space()='Save']"));
        }

        private LabelText commentTimeStamp() {
            return new LabelText(driver, By.xpath(editingControls().getXpathLocatorValue() +
                    "/span[starts-with(@class, 'comment-controls-date')]"));
        }

        /**
         * Removes original comment in active editing mode, enters new comment and click Save button.
         *
         * @param newComment - Specifies the new comment
         * @return {@code Comment}
         */
        Comment updateComment(String newComment) {
            editingTextInput().clear();
            editingTextInput().sendKeys(newComment);
            saveLink().click();
            return this;
        }

        /**
         * Clicks Cancel link under the editing input field.
         *
         * @return {@code Comment}
         */
        public Comment canelEditing() {
            cancelLink().click();
            return this;
        }

        /**
         * Gets comment's time stamp.
         *
         * @return {@code String}
         */
        public String getCommentTimeStamp() {
            return commentTimeStamp().readLabelText();
        }
    }
}