package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.previewarea;

import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.previewarea.visualdocumentpreview.BaseVisualDocumentPreview;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of a visual preview type (a visual document preview is available in the Preview area).
 */
public class VisualPreviewType extends BaseDocumentPreviewAreaInCDS {

    public VisualPreviewType(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public VisualPreviewType load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return previewFrame().isAvailable();
    }

    private Element previewFrame() {
        return new Element(driver, By.id("preview-frame"));
    }

    /**
     * Gets visual preview of a document of a specified type.
     *
     * @param visualPreviewType - Specifies the type of a visual preview
     * @return {@code T extends BaseVisualDocumentPreview}
     */
    public <T extends BaseVisualDocumentPreview> T getPreview(Class<T> visualPreviewType) {
        return constructClassInstance(visualPreviewType);
    }
}