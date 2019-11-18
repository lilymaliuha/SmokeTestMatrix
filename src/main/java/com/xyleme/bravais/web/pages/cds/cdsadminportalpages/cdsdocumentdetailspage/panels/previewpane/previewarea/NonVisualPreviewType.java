package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.previewarea;

import com.xyleme.bravais.api.CDSApiHelper;
import com.xyleme.bravais.datacontainers.FileType;
import com.xyleme.bravais.utils.FileDownloader;
import com.xyleme.bravais.web.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

/**
 * Implementation of a non-visual preview type (a visual document preview is not available, instead - the Download button
 * is available in the center of the preview area).
 */
public class NonVisualPreviewType extends BaseDocumentPreviewAreaInCDS {

    public NonVisualPreviewType(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public NonVisualPreviewType load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return downloadButtonInDocumentPreviewArea().isAvailable();
    }

    private Button downloadButtonInDocumentPreviewArea() {
        return new Button(driver, By.xpath("//div[@id='preview-container']//button[normalize-space()='Download']"));
    }

    /**
     * Checks if button 'Download' is available in a document preview area.
     *
     * @return {@code boolean}
     */
    public boolean isDownloadButtonInPreviewAreaAvailable() {
        return downloadButtonInDocumentPreviewArea().isAvailable();
    }

    /**
     * Gets id of the opened document from the URL.
     *
     * @return {@code String}
     */
    private String getDocumentId() {
        String url = driver.getCurrentUrl();
        String documentId = url.substring(url.lastIndexOf("/") + 1);
        System.out.println("Document id: " + documentId);
        return documentId;
    }

    /**
     * Downloads a document using the Download button in the middle of the preview area (using a request triggered by
     * the button) and returns path to the downloaded file.
     *
     * @param fileType - Specifies file type of the document
     * @return {@code String}
     */
    public String downloadDocument(FileType fileType) {
        String pathToDownloadedFile = null;

        if (downloadButtonInDocumentPreviewArea().isAvailable()) {
            downloadButtonInDocumentPreviewArea().click(); // NOTE: Have to click the button otherwise the Downloaded statement is not sent to Analytics.
            String downloadingURL = CDSApiHelper.getDownloadingURLOfDocument(getDocumentId());

            try {
                pathToDownloadedFile = new FileDownloader(driver, downloadingURL).downloadFile(fileType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("'Download' button is not available in a document preview area on the Document " +
                    "Details page. The document cannot be downloaded.");
        }
        assert pathToDownloadedFile != null;
        return pathToDownloadedFile;
    }
}