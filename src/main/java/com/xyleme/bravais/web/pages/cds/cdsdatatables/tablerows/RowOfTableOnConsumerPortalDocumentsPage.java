package com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows;

import com.xyleme.bravais.api.CDSApiHelper;
import com.xyleme.bravais.datacontainers.FileType;
import com.xyleme.bravais.utils.FileDownloader;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.previewarea.visualdocumentpreview.BaseVisualDocumentPreview;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.previewarea.visualdocumentpreview.CoursePreview;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.previewarea.visualdocumentpreview.NonCourseDocumentPreview;
import com.xyleme.bravais.web.pages.cds.cdsconsumerportalpages.CDSConsumerPortalDocumentsPage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.baserows.documentstablerow.BaseConsumerPortalTableRow;
import com.xyleme.bravais.web.pages.cds.popupdialogs.infodialogs.YourDocumentIsBeingPreparedDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;

import static com.xyleme.bravais.DriverMaster.switchToNewTab;

/**
 * Implementation of a row element of the table available on the 'Documents' page on CDS Consumer Portal.
 */
public class RowOfTableOnConsumerPortalDocumentsPage extends BaseConsumerPortalTableRow {
    private WebElement starIcon = getLastOptionsBlockOfRow().findElement(
            By.xpath(".//a[@class='favorite-button']//parent::span[not (contains(@class, 'hide'))]//i"));

    public RowOfTableOnConsumerPortalDocumentsPage(WebDriver driver, WebElement recordBody) {
        super(driver, recordBody);
    }

    private WebElement itemNameLink() {
        return getColumnParameterElement("Name").findElement(By.xpath("./a"));
    }

    /**
     * Gets row value of the 'Name' column.
     *
     * @return {@code String}
     */
    public String getItemName() {
        return getColumnValue("Name");
    }

    /**
     * Gets row value of the 'Resource Type' column.
     *
     * @return {@code String}
     */
    public String getResourceType() {
        return getColumnValue("Resource Type");
    }

    /**
     * Gets row value of the 'Updated' column.
     *
     * @return {@code String}
     */
    public String getTimeStampOfLastDocumentUpdate() {
        return getColumnValue("Updated");
    }

    /**
     * Checks if the table row represents a folder.
     *
     * @return {@code boolean}
     */
    private boolean doesRowRepresentFolder() {
        return getColumnParameterElement(0).findElement(By.xpath("./i")).getAttribute("class")
                .contains("folder");
    }

    /**
     * Clicks Name link of the item (folder or document).
     */
    public void clickItemNameLink() {
        itemNameLink().click();
    }

    /**
     * Clicks link of the 'Name' column value of the row if it represents a folder.
     *
     * @return {@code CDSConsumerPortalDocumentsPage}
     */
    public CDSConsumerPortalDocumentsPage openFolder() {
        if (doesRowRepresentFolder()) {
            getColumnParameterElement("Name").findElement(By.xpath("./a")).click();
            return new CDSConsumerPortalDocumentsPage(driver);
        } else {
            throw new RuntimeException("The row doesn't represent a folder, it represents a document.");
        }
    }

    /**
     * Checks if a name link of the table item is available.
     *
     * @return {@code boolean}
     */
    private boolean isItemNameLinkAvailable() {
        return getColumnParameterElement("Name").findElements(By.xpath("./a")).size() > 0;
    }

    /**
     * Checks whether the table item represents a document and the document name link is available and gets id of the
     * document.
     *
     * @return {@code String}
     */
    private String getDocumentId() {
        if (!doesRowRepresentFolder() && isItemNameLinkAvailable()) {
            return itemNameLink().getAttribute("id").replaceAll("\\D+", "");
        } else {
            throw new RuntimeException("The item is either of a folder type or a document name link is not available.");
        }
    }

    /**
     * Opens a visual document preview.
     *
     * @param previewType - Specifies type of the preview expected to be opened
     * @return {@code T extends BaseVisualDocumentPreview}
     */
    private <T extends BaseVisualDocumentPreview> T openVisualDocumentPreview(Class<T> previewType) {
        if (!doesRowRepresentFolder() && isItemNameLinkAvailable()) {
            clickItemNameLink();
            if (previewType.getSimpleName().equals(NonCourseDocumentPreview.class.getSimpleName())) {
                new YourDocumentIsBeingPreparedDialog(driver).waitUntilDisappears();
            }
            switchToNewTab();
            return constructClassInstance(previewType);
        } else {
            throw new RuntimeException("The table item is of a folder type or the name link of the item is not available.");
        }
    }

    /**
     * Opens visual preview of a course document.
     *
     * @return {@code CoursePreview}
     */
    public CoursePreview openCoursePreview() {
        return openVisualDocumentPreview(CoursePreview.class);
    }

    /**
     * Opens visual preview of a non-course document (e.g. Image, PDF).
     *
     * @return {@code NonCourseDocumentPreview}
     */
    public NonCourseDocumentPreview openNonCourseDocumentVisualPreview() {
        return openVisualDocumentPreview(NonCourseDocumentPreview.class);
    }

    /**
     * Downloads a not previwable document (Word, Excel, PowerPoint, ZIP).
     *
     * @param fileType - Specifies type of the file intended to be downloaded
     * @return {@code String}
     */
    public String downloadNotPreviewableDocument(FileType fileType) {
        String pathToDownloadedFile = null;
        String downloadingURL = CDSApiHelper.getDownloadingURLOfDocument(getDocumentId());
        try {
            pathToDownloadedFile = new FileDownloader(driver, downloadingURL).downloadFile(fileType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert pathToDownloadedFile != null;
        return pathToDownloadedFile;
    }

    /**
     * Clicks the Star button of the row.
     *
     * @return {@code CDSConsumerPortalDocumentsPage}
     */
    private CDSConsumerPortalDocumentsPage clickStarIcon() {
        starIcon.click();
        return new CDSConsumerPortalDocumentsPage(driver);
    }

    /**
     * Checks if the item is marked as favorite.
     *
     * @return {@code boolean}
     */
    private boolean isMarkedAsFavorite() {
        return starIcon.getAttribute("class").contains("active");
    }

    /**
     * Marks or unmarks the row as Favorite.
     *
     * @param mark - Specifies the decision (true - mark, false - unmark)
     * @return {@code CDSConsumerPortalDocumentsPage}
     */
    public CDSConsumerPortalDocumentsPage markOrUnmarkAsFavorite(boolean mark) {
        if (mark && !isMarkedAsFavorite()) {
            clickStarIcon();
        } else if (!mark && isMarkedAsFavorite()) {
            clickStarIcon();
        } else {
            System.out.println("'markOrUnmarkAsFavorite' method: No action has been taken since the intended action " +
                    "doesn't apply to the current state of the Star icon.\n>> Intended action: mark? - "
                    + mark + "\n>> Current state of the Star icon: is selected? - " + isMarkedAsFavorite());
        }
        return new CDSConsumerPortalDocumentsPage(driver, false);
    }
}