package com.xyleme.bravais.web.pages.cds.popupdialogs.uploadingdialog;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.LabelText;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSPageHeader;
import com.xyleme.bravais.web.pages.cds.popupdialogs.uploadingdialog.filesqueuetable.tables.FilesQueueTableOnDocumentsUploadingDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of a dialog which is used for files uploading.
 */
public class FileUploadingDialog extends BaseUploadingDialog {

    public FileUploadingDialog(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                continueButton().isAvailable() &&
                titleOfExpirationDatePickerBlock().isAvailable() &&
                datePickerInputField().isAvailable() &&
                calendarButtonNextToDatePickerInputField().isAvailable() &&
                expirationDateBlockNote().isAvailable();
    }

    @Override
    public FileUploadingDialog load() {
        return this;
    }

    private Button continueButton() {
        return controlButtonOnDialog("Continue");
    }

    private Element expirationDateBlockBody() {
        return new Element(driver, By.xpath(dialogBodyElement().getXpathLocatorValue() +
                "//div[contains(@class, 'expiration-block')]"));
    }

    private LabelText titleOfExpirationDatePickerBlock() {
        return new LabelText(driver, By.xpath(expirationDateBlockBody().getXpathLocatorValue() +
                "/p[starts-with(@class, 'title')]"));
    }

    private Element datePickerInputField() {
        return new Element(driver, By.xpath(expirationDateBlockBody().getXpathLocatorValue() +
                "//div[starts-with(@class, 'form-control')]"));
    }

    private Button calendarButtonNextToDatePickerInputField() {
        return new Button(driver, By.xpath(expirationDateBlockBody().getXpathLocatorValue() +
                "//i[contains(@class, 'calendar')]/parent::button"));
    }

    private LabelText expirationDateBlockNote() {
        return new LabelText(driver, By.xpath(expirationDateBlockBody().getXpathLocatorValue() +
                "//p[starts-with(@class, 'note')]"));
    }

    /**
     * Clicks 'Continue' button.
     *
     * @return {@code FileUploadingDialog}
     */
    private FileUploadingDialog clickContinueButton() {
        continueButton().click();
        return this;
    }

    /**
     * Gets files queue table available on the dialog.
     *
     * @return {@code FilesQueueTableOnDocumentsUploadingDialog}
     */
    @Override
    public FilesQueueTableOnDocumentsUploadingDialog getFilesQueueTable() {
        return new FilesQueueTableOnDocumentsUploadingDialog(driver);
    }

    /**
     * Deletes specified file from the files queue table.
     *
     * @param fileName - Specifies name of the file intended to be deleted
     * @return {@code FileUploadingDialog}
     */
    public FileUploadingDialog deleteFile(String fileName) {
        return getFilesQueueTable().getTableRow(fileName).deleteFile();
    }

    /**
     * Clicks Continue button, waits until file/s is/are uploaded and closes the dialog.
     *
     * @param tClass - Specifies the class (page) instance of which is intended to be returned after closing the dialog
     * @return {@code T extends BaseCDSPageHeader}
     */
    public <T extends BaseCDSPageHeader> T completeUploading(Class<T> tClass) {
        clickContinueButton();
        waitUntilAllFilesAreUploaded();
        return closeDialog(tClass);
    }
}