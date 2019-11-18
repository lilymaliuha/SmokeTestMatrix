package com.xyleme.bravais.web.pages.cds.popupdialogs.uploadingdialog;

import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsclassificationspage.CDSClassificationsPage;
import com.xyleme.bravais.web.pages.cds.popupdialogs.infodialogs.ClassificationUploadingInfoDialog;
import com.xyleme.bravais.web.pages.cds.popupdialogs.uploadingdialog.filesqueuetable.tables.FilesQueueTableOnClassificationImportingDialog;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of a dialog which is used for classifications importing.
 */
public class ClassificationImportDialog extends BaseUploadingDialog {

    public ClassificationImportDialog(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable();
    }

    @Override
    public ClassificationImportDialog load() {
        return this;
    }

    /**
     * Gets files queue table available on the dialog.
     *
     * @return {@code FilesQueueTableOnClassificationImportingDialog}
     */
    @Override
    public FilesQueueTableOnClassificationImportingDialog getFilesQueueTable() {
        return new FilesQueueTableOnClassificationImportingDialog(driver);
    }

    /**
     * Waits until the import process is completed and closes the dialog.
     *
     * @return {@code CDSClassificationsPage}
     */
    public CDSClassificationsPage completeImport() {
        waitUntilAllFilesAreUploaded();
        closeDialog(CDSClassificationsPage.class);
        new ClassificationUploadingInfoDialog(driver).closeDialog();
        driver.navigate().refresh();
        return new CDSClassificationsPage(driver);
    }
}