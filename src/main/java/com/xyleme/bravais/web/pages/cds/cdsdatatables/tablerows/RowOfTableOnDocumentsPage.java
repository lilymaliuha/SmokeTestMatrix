package com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows;

import com.xyleme.bravais.api.CDSApiHelper;
import com.xyleme.bravais.datacontainers.FileType;
import com.xyleme.bravais.utils.FileDownloader;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSDocumentsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsfolderdetailspage.CDSFolderDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.baserows.documentstablerow.BaseDocumentsTableRowWithBulkChanges;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.CDSDocumentDetailsPage;
import com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs.ArchivingConfirmationDialog;
import com.xyleme.bravais.web.pages.cds.popupdialogs.nodeselectioninnodestreedialog.documentorfoldermovingdialog.MoveToDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

import static com.xyleme.bravais.BaseTest.staticSleep;
import static com.xyleme.bravais.DriverMaster.remoteExecution;
import static com.xyleme.bravais.web.WebPage.ENVIRONMENT;

/**
 * Implementation of a Row element of the table available on Documents page.
 */
public class RowOfTableOnDocumentsPage extends BaseDocumentsTableRowWithBulkChanges {
    private WebElement starIcon = getLastOptionsBlockOfRow().findElement(
            By.xpath(".//a[@class='favorite-button']//parent::span[not (contains(@class, 'hide'))]//i"));

    public RowOfTableOnDocumentsPage(WebDriver driver, WebElement recordBody) {
        super(driver, recordBody);
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
     * Clicks Details link of the row if it represents a document.
     *
     * @return {@code CDSDocumentDetailsPage}
     */
    @Override
    public CDSDocumentDetailsPage goToDocumentDetails() {

        if (!doesRowRepresentFolder()) {
            clickDetailsLink();
            return new CDSDocumentDetailsPage(driver);
        } else {
            throw new RuntimeException("The row doesn't represent a document, it represents a folder.");
        }
    }

    /**
     * Clicks Details link of the row if it represents a folder.
     *
     * @return {@code CDSFolderDetailsPage}
     */
    public CDSFolderDetailsPage goToFolderDetails() {

        if (doesRowRepresentFolder()) {
            clickDetailsLink();
            return new CDSFolderDetailsPage(driver);
        } else {
            throw new RuntimeException("The row doesn't represent a folder, it represents a document.");
        }
    }

    /**
     * Clicks link of the 'Name' column value of the row if it represents a folder.
     *
     * @return {@code CDSDocumentsPage}
     */
    public CDSDocumentsPage openFolder() {

        if (doesRowRepresentFolder()) {
            getColumnParameterElement("Name").findElement(By.xpath("./a")).click();
            return new CDSDocumentsPage(driver);
        } else {
            throw new RuntimeException("The row doesn't represent a folder, it represents a document.");
        }
    }

    /**
     * Selects the row (checks the selection checkbox).
     *
     * @return {@code CDSDocumentsPage}
     */
    public CDSDocumentsPage select() {
        checkOrUncheckRow(true);
        return new CDSDocumentsPage(driver);
    }

    /**
     * Clicks the Star button on the row and waits first for the notification message appearing and then for its disappearing.
     *
     * @return {@code CDSDocumentsPage}
     */
    private CDSDocumentsPage clickStarIcon() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By notificationMessage = By.xpath("//div[contains(@class, 'notify-message')]");
        starIcon.click();

        if (remoteExecution) {
            staticSleep(5); // Timeout for dynamic notification appearing/disappearing.
        } else {
            wait.until(ExpectedConditions.visibilityOfElementLocated(notificationMessage));

            if (new Element(driver, notificationMessage).isAvailable()) {
                wait.until(ExpectedConditions.invisibilityOfElementLocated(notificationMessage));
            }
        }

        return new CDSDocumentsPage(driver);
    }

    /**
     * Marks or unmarks the row as Favorite.
     *
     * @param mark - Specifies the decision (true - mark, false - unmark)
     * @return {@code CDSDocumentsPage}
     */
    public CDSDocumentsPage markOrUnmarkAsFavorite(boolean mark) {
        boolean isMarkedAsFavorite = starIcon.getAttribute("class").contains("active");

        if (mark && !isMarkedAsFavorite) {
            clickStarIcon();
        } else if (!mark && isMarkedAsFavorite) {
            clickStarIcon();
        } else {
            System.out.println("'markOrUnmarkAsFavorite' method: No action has been taken since the intended action " +
                    "doesn't apply to the current state of the Star icon.\n>> Intended action: mark? - "
                    + mark + "\n>> Current state of the Star icon: is selected? - " + isMarkedAsFavorite);
        }
        return new CDSDocumentsPage(driver);
    }

    /**
     * Moves the table item (folder or document) to a specified target folder.
     *
     * @param targetFolderPath - Specifies path to the target folder the item is intended to be moved to
     * @return {@code CDSDocumentsPage}
     */
    public CDSDocumentsPage moveItemTo(String targetFolderPath) {
        MoveToDialog moveToDialog = openItemOptionsDropDownAndSelectMenuOption("Move", MoveToDialog.class);
        moveToDialog.selectTargetFolder(targetFolderPath);
        return moveToDialog.move();
    }

    /**
     * Archives the table item (folder or document).
     *
     * @return {@code CDSDocumentsPage}
     */
    public CDSDocumentsPage archiveTableItem() {
        int initialNumberOfTableRow = getNumberOfTableRows();
        ArchivingConfirmationDialog confirmationDialog = openItemOptionsDropDownAndSelectMenuOption("Archive",
                ArchivingConfirmationDialog.class);
        confirmationDialog.confirmAction();
        waitUntil(getNumberOfTableRows() == initialNumberOfTableRow - 1);
        return new CDSDocumentsPage(driver);
    }

    /**
     * Downloads the table item (document or folder) using Download option of More menu of the item.
     *
     * @param fileType - Specifies the file type (type of the final downloaded file)
     * @return {@code String}
     */
    public String downloadItemUsingDownloadOptionOfMoreMenu(FileType fileType) {
        String pathToDownloadedFile = null;
        String downloadingURL;
        openItemOptionsDropDownAndSelectMenuOption("Download", CDSDocumentsPage.class);

        if (doesRowRepresentFolder()) {
            downloadingURL = ENVIRONMENT.env.get("CDS_CORE_URL") + "/api/dynamic/folders/" + getRowItemId();
        } else {
            downloadingURL = CDSApiHelper.getDownloadingURLOfDocument(getRowItemId());
        }

        try {
            pathToDownloadedFile = new FileDownloader(driver, downloadingURL).downloadFile(fileType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert pathToDownloadedFile != null;
        return pathToDownloadedFile;
    }
}