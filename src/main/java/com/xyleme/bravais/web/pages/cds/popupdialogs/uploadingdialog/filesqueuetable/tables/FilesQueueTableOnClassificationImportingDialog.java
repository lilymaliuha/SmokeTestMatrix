package com.xyleme.bravais.web.pages.cds.popupdialogs.uploadingdialog.filesqueuetable.tables;

import com.xyleme.bravais.web.pages.cds.popupdialogs.uploadingdialog.filesqueuetable.tablerows.TableRowOfFilesQueueTableOnImportDialog;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of a table with file/s intended/being imported available on the File/s importing dialog.
 */
public class FilesQueueTableOnClassificationImportingDialog extends BaseFilesQueueTable {

    public FilesQueueTableOnClassificationImportingDialog(WebDriver driver) {
        super(driver);
    }

    /**
     * Gets specified table row.
     *
     * @param rowIndex - Specifies table row index
     * @return {@code TableRowOfFilesQueueTableOnImportDialog}
     */
    public TableRowOfFilesQueueTableOnImportDialog getTableRow(int rowIndex) {
        return constructRow(TableRowOfFilesQueueTableOnImportDialog.class, getTableRowElements().get(rowIndex));
    }

    /**
     * Gets specified table row.
     *
     * @param fileName - Specifies file name of the table row intended to be returned
     * @return {@code TableRow}
     */
    public TableRowOfFilesQueueTableOnImportDialog getTableRow(String fileName) {
        return constructRow(TableRowOfFilesQueueTableOnImportDialog.class, getTableRowElementOfFile(fileName));
    }
}