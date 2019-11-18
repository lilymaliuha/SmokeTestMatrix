package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane;

import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSPanelOnDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.commentsblock.CommentsBlock;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.previewarea.BaseDocumentPreviewAreaInCDS;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.previewarea.NonVisualPreviewType;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.previewarea.VisualPreviewType;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.previewarea.visualdocumentpreview.BaseVisualDocumentPreview;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.previewarea.visualdocumentpreview.CoursePreview;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.previewarea.visualdocumentpreview.NonCourseDocumentPreview;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Preview pane on CDS Document Details page.
 */
public class PreviewPane extends BaseCDSPanelOnDetailsPage {

    public PreviewPane(WebDriver driver) {
        super(driver);
    }

    @Override
    public PreviewPane load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return previewContainer().isAvailable() &&
                documentPreviewArea().isAvailable();
    }

    private Element previewContainer() {
        return new Element(driver, By.xpath("//div[@id='preview-container']"));
    }

    private Element documentPreviewArea() {
        return new Element(driver, By.xpath(previewContainer().getXpathLocatorValue() + "/div[@class='row']"));
    }

    /**
     * Gets Preview area of a specified preview type.
     *
     * @param previewType - Specifies Preview type expected to be returned
     * @return {@code T extends BaseDocumentPreviewAreaInCDS}
     */
    private <T extends BaseDocumentPreviewAreaInCDS> T getPreviewArea(Class<T> previewType) {
        return constructClassInstance(previewType);
    }

    /**
     * Gets Preview area of a document which doesn't have visual preview.
     *
     * @return {@code NonVisualPreviewType}
     */
    public NonVisualPreviewType getPreviewAreaOfNonVisualPreviewType() {
        return getPreviewArea(NonVisualPreviewType.class);
    }

    /**
     * Gets visual document preview of a specified type.
     *
     * @param visualPreviewType - Specifies type of a visual preview
     * @return {@code T extends BaseVisualDocumentPreview}
     */
    private <T extends BaseVisualDocumentPreview> T getPreviewOfVisualPreviewType(Class<T> visualPreviewType) {
        return getPreviewArea(VisualPreviewType.class).getPreview(visualPreviewType);
    }

    /**
     * Gets visual preview of a Course-type document.
     *
     * @return {@code CoursePreview}
     */
    public CoursePreview getCoursePreview() {
        return getPreviewOfVisualPreviewType(CoursePreview.class);
    }

    /**
     * Gets visual preview of a non course-type document (Image, PDF).
     *
     * @return {@code NonCourseDocumentPreview}
     */
    public NonCourseDocumentPreview getVisualPreviewOfNonCourseDocument() {
        return getPreviewOfVisualPreviewType(NonCourseDocumentPreview.class);
    }

    /**
     * Checks if Comments block is available.
     *
     * @return {@code boolean}
     */
    public boolean isCommentsBlockAvailable() {
        return new CommentsBlock(driver, false).isAvailable();
    }

    /**
     * Gets Comments block available under the document preview area.
     *
     * @return {@code CommentsBlock}
     */
    public CommentsBlock getCommentsBlock() {
        return new CommentsBlock(driver);
    }
}