package com.xyleme.bravais.web.pages.cds.popupdialogs.uploadingdialog.filesqueuetable.tables;

import com.xyleme.bravais.web.pages.cds.popupdialogs.uploadingdialog.filesqueuetable.tablerows.TableRowOfFilesQueueTableOnDownloadDialog;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of a table with file/s intended/being uploaded available on the File/s uploading dialog.
 */
public class FilesQueueTableOnDocumentsUploadingDialog extends BaseFilesQueueTable {

    public FilesQueueTableOnDocumentsUploadingDialog(WebDriver driver) {
        super(driver);
    }

    /**
     * Gets specified table row.
     *
     * @param rowIndex - Specifies table row index
     * @return {@code TableRowOfFilesQueueTableOnDownloadDialog}
     */
    public TableRowOfFilesQueueTableOnDownloadDialog getTableRow(int rowIndex) {
        return constructRow(TableRowOfFilesQueueTableOnDownloadDialog.class, getTableRowElements().get(rowIndex));
    }

    /**
     * Gets specified table row.
     *
     * @param fileName - Specifies file name of the table row intended to be returned
     * @return {@code TableRowOfFilesQueueTableOnDownloadDialog}
     */
    public TableRowOfFilesQueueTableOnDownloadDialog getTableRow(String fileName) {
        return constructRow(TableRowOfFilesQueueTableOnDownloadDialog.class, getTableRowElementOfFile(fileName));
    }
}