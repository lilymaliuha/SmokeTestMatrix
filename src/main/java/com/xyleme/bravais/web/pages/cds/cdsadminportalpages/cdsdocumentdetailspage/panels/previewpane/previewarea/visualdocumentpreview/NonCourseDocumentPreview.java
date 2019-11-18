package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.previewarea.visualdocumentpreview;

import com.xyleme.bravais.datacontainers.PreviewType;
import com.xyleme.bravais.web.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of preview of a document which has preview but which is not a course-type document (Image, PDF).
 */
public class NonCourseDocumentPreview extends BaseVisualDocumentPreview {

    public NonCourseDocumentPreview(WebDriver driver) {
        super(driver);
        waitForInvisibilityOfSpinnerOnPageInIFrame(PreviewType.XPE_OR_PDF_OR_IMAGE_PREVIEW);
    }

    public NonCourseDocumentPreview(WebDriver driver, boolean waitForInvisibilityOfSpinnerInIFrame) {
        super(driver);

        if (waitForInvisibilityOfSpinnerInIFrame) {
            waitForInvisibilityOfSpinnerOnPageInIFrame(PreviewType.XPE_OR_PDF_OR_IMAGE_PREVIEW);
        }
    }

    /**
     * Checks if PDF plugin for document preview is opened.
     *
     * @return {@code boolean}
     */
    public boolean isPDFPluginOpened() {
        return new Element(driver, By.xpath("//embed[@id='plugin']")).isAvailable();
    }

    /**
     * Checks oif image preview is opened.
     *
     * @return {@code boolean}
     */
    public boolean isImagePreviewOpened() {
        return new Element(driver, By.xpath("//body/img")).isAvailable();
    }
}