package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage;

import com.xyleme.bravais.api.CDSApiHelper;
import com.xyleme.bravais.datacontainers.FileType;
import com.xyleme.bravais.utils.FileDownloader;
import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.LabelText;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSDocumentsPageItemDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.LearningObjectsPane;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.PermissionsPane;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.PreviewPane;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.VersionsPane;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.previewarea.visualdocumentpreview.BaseVisualDocumentPreview;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.PropertiesPane;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

import static com.xyleme.bravais.DriverMaster.switchToNewTab;

/**
 * Implementation of CDS Document Details page.
 */
public class CDSDocumentDetailsPage extends BaseCDSDocumentsPageItemDetailsPage {

    public CDSDocumentDetailsPage(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public CDSDocumentDetailsPage load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                documentVersion().isAvailable() &&
                downloadButtonNextToMoreDropDownButton().isAvailable();
    }

    private LabelText documentVersion() {
        return new LabelText(driver, By.xpath(
                "//div[contains(@class, 'details-header')]//h5//b[contains(normalize-space(), 'V')]"));
    }

    private Button downloadButtonNextToMoreDropDownButton() {
        return new Button(driver, By.xpath("//div[contains(@class, 'document-buttons-container')]" +
                "//button[starts-with(normalize-space(), 'Download')]"));
    }

    private Button uploadNewVersionButton() {
        return new Button(driver, By.xpath("//div[contains(@class, 'document-buttons-container')]" +
                "//span[normalize-space()='Upload New Version']"));
    }

    /**
     * Gets version of a document/course opened in cds.
     *
     * @return {@code int}
     */
    public int getDocumentVersion() {
        return Integer.parseInt(documentVersion().getText().replaceAll("\\D+", ""));
    }

    /**
     * Edits name of a document.
     *
     * @param newName - Specifies the name the document in intended to be renamed to
     * @return {@code CDSDocumentDetailsPage}
     */
    public CDSDocumentDetailsPage editDocumentName(String newName) {
        editName(newName);
        return this;
    }

    /**
     * Downloads a document using Download button next to 'Upload New Version' button (using a request triggered by the
     * button) and returns path to the downloaded file.
     *
     * @param fileType - Specifies file type of the document
     * @return {@code String}
     */
    public String downloadDocument(FileType fileType) {
        String pathToDownloadedFile = null;
        String url = driver.getCurrentUrl();

        if (downloadButtonNextToMoreDropDownButton().isAvailable()) {
            downloadButtonNextToMoreDropDownButton().click(); // NOTE: Have to click the button otherwise the Downloaded statement won't be sent to Analytics.
            String downloadingURL = CDSApiHelper.getDownloadingURLOfDocument(url.substring(url.lastIndexOf("/") + 1));

            try {
                pathToDownloadedFile = new FileDownloader(driver, downloadingURL).downloadFile(fileType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("'Download' button is not available on Document Details page (the button next to " +
                    "'Upload New Version' button). The document cannot be downloaded.");
        }
        assert pathToDownloadedFile != null;
        return pathToDownloadedFile;
    }

    /**
     * Opens a full screen preview of a document.
     *
     * @param visualPreviewType - Specifies type of a visual preview basing on a document type intended to be previewed
     * @return {@code T extends BaseVisualDocumentPreview}
     */
    public <T extends BaseVisualDocumentPreview> T openFullScreenPreviewOfDocument(Class<T> visualPreviewType) {
        selectMoreDropDownMenuOption("Full Screen Preview");
        switchToNewTab();
        waitUntil(driver.getCurrentUrl().endsWith("preview"));
        return constructClassInstance(visualPreviewType);
    }

    /**
     * Checks if Preview tab is selected, if not - selects it and returns instance of the respective pane.
     *
     * @return {@code PreviewPane}
     */
    public PreviewPane selectPreviewTabAndGetPane() {
        return selectTabAndGetPane("Preview", PreviewPane.class);
    }

    /**
     * Checks if Permissions tab is selected, if not - selects it and returns instance of the respective pane.
     *
     * @return {@code PropertiesPane}
     */
    public PropertiesPane selectPropertiesTabAndGetPane() {
        return selectTabAndGetPane("Properties", PropertiesPane.class);
    }

    /**
     * Checks if Versions tab is selected, if not - selects it and returns instance of the respective pane.
     *
     * @return {@code VersionsPane}
     */
    public VersionsPane selectVersionsTabAndGetPane() {
        return selectTabAndGetPane("Versions", VersionsPane.class);
    }

    /**
     * Checks if Learning Objects tab is selected, if not - selects it and returns instance of the respective pane.
     *
     * @return {@code LearningObjectsPane}
     */
    public LearningObjectsPane selectLearningObjectsTabAndGetPane() {
        return selectTabAndGetPane("Learning Objects", LearningObjectsPane.class);
    }

    /**
     * Checks if Permissions tab is selected, if not - selects it and returns instance of the respective pane.
     *
     * @return {@code PermissionsPane}
     */
    public PermissionsPane selectPermissionsTabAndGetPane() {
        return selectTabAndGetPane("Permissions", PermissionsPane.class);
    }
}