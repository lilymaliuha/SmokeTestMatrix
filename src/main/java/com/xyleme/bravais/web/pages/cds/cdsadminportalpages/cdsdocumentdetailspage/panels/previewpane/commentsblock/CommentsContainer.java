package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.commentsblock;

import com.xyleme.bravais.web.elements.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of comments container (container of existing comments).
 */
class CommentsContainer extends CommentsBlock {

    CommentsContainer(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
         return commentsContainerHeader().isAvailable();
    }

    private Element commentsContainerHeader() {
        return new Element(driver, By.xpath(
                "//div[starts-with(@class, 'comments-container-heading') and not(contains(@class, 'hide'))]"));
    }

    /**
     * Gets specified document comment.
     *
     * @param commentIndex - Specifies index of the comment intended to be returned
     * @return {@code Comment}
     */
    public Comment getComment(int commentIndex) {
        return new Comment(driver, commentIndex);
    }
}