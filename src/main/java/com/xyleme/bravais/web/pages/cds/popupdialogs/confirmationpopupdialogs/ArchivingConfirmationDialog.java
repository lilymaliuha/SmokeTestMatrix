package com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSDocumentsPage;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of a popup dialog which appears after attempting to archive an item (document or folder).
 */
public class ArchivingConfirmationDialog extends BaseConfirmationDialog {

    public ArchivingConfirmationDialog(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                archiveButton().isAvailable();
    }

    private Button archiveButton() {
        return dialogBottomButton("Archive");
    }

    /**
     * Clicks 'Archive' button.
     *
     * @return {@code CDSDocumentsPage}
     */
    public CDSDocumentsPage confirmAction() {
        archiveButton().click();
        return new CDSDocumentsPage(driver);
    }

    /**
     * Clicks 'Cancel' button.
     *
     * @return {@code CDSDocumentsPage}
     */
    public CDSDocumentsPage cancelAction() {
        clickCancelButton();
        return new CDSDocumentsPage(driver);
    }

    /**
     * Clicks close ('x') button.
     *
     * @return {@code CDSDocumentsPage}
     */
    public CDSDocumentsPage closeDialog() {
        clickCloseDialogButton();
        return new CDSDocumentsPage(driver);
    }
}