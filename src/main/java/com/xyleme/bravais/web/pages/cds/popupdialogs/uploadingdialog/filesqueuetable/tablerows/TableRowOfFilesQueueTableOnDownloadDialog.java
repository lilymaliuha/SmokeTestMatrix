package com.xyleme.bravais.web.pages.cds.popupdialogs.uploadingdialog.filesqueuetable.tablerows;

import com.xyleme.bravais.web.pages.cds.popupdialogs.uploadingdialog.FileUploadingDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Implementation of a row object of a file queue table available on Uploading dialog.
 */
public class TableRowOfFilesQueueTableOnDownloadDialog extends BaseRowOfFilesQueueTable {

    public TableRowOfFilesQueueTableOnDownloadDialog(WebDriver driver, WebElement rowBodyElement) {
        super(driver, rowBodyElement);
    }

    @Override
    public TableRowOfFilesQueueTableOnDownloadDialog load() {
        return this;
    }

    private WebElement deleteButtonElement() {
        return rowBodyElement.findElement(By.xpath(".//i[contains(@class, 'trash')]"));
    }

    /**
    * Deletes the file.
     *
     * @return {@code FileUploadingDialog}
     */
    public FileUploadingDialog deleteFile() {
        deleteButtonElement().click();
        return new FileUploadingDialog(driver);
    }
}