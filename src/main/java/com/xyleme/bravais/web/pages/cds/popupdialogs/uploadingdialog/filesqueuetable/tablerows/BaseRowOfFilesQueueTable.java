package com.xyleme.bravais.web.pages.cds.popupdialogs.uploadingdialog.filesqueuetable.tablerows;

import com.xyleme.bravais.web.WebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Implementation of a base row object of a file queue table available on Uploading and Importing dialogs.
 */
public abstract class BaseRowOfFilesQueueTable extends WebPage<BaseRowOfFilesQueueTable> {
    WebElement rowBodyElement;

    BaseRowOfFilesQueueTable(WebDriver driver, WebElement rowBodyElement) {
        super(driver);
        this.rowBodyElement = rowBodyElement;
    }

    @Override
    public boolean isAvailable() {
        return isElementDisplayed(rowBodyElement);
    }

    private WebElement documentNameElement() {
        return rowBodyElement.findElement(
                By.xpath("./td[contains(@class, 'file-name')]//small[not(contains(@class, 'hide'))]"));
    }

    private WebElement documentProgressBarElement(int progressCompleteness) {
        // NOTE: Need to look for the element this way because 'findElements' method doesn't throw NoSuchElement
        // exception and this is important because otherwise the element cannot be used in 'isFileUploaded' method.
        return rowBodyElement.findElements(By.xpath(".//div[starts-with(@class, 'progress-bar') and contains(@style, '"
                + progressCompleteness + "%')]")).get(0);
    }

    /**
     * Gets file name of the table row.
     *
     * @return {@code String}
     */
    public String getFileName() {
        return documentNameElement().getText();
    }

    /**
     * Checks if file uploading process is completed.
     *
     * @return {@code boolean}
     */
    public boolean isFileUploaded() {
        return isElementDisplayed(documentProgressBarElement(100));
    }

    /**
     * Waits until file uploading process is completed.
     */
    public void waitUntilFileIsUploaded() {
        waitUntil(isFileUploaded());
    }
}