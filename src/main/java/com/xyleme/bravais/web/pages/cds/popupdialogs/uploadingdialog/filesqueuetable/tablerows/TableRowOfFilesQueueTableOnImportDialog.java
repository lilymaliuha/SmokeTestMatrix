package com.xyleme.bravais.web.pages.cds.popupdialogs.uploadingdialog.filesqueuetable.tablerows;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Implementation of a row object of a file queue table available on Importing dialog.
 */
public class TableRowOfFilesQueueTableOnImportDialog extends BaseRowOfFilesQueueTable {

    public TableRowOfFilesQueueTableOnImportDialog(WebDriver driver, WebElement rowBodyElement) {
        super(driver, rowBodyElement);
    }

    @Override
    public TableRowOfFilesQueueTableOnImportDialog load() {
        return this;
    }
}