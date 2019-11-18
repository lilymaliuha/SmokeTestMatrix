package com.xyleme.bravais.web.pages.cds.popupdialogs.uploadingdialog;

import com.xyleme.bravais.web.WebPage;
import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.LabelText;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSPageHeader;
import com.xyleme.bravais.web.pages.cds.popupdialogs.uploadingdialog.filesqueuetable.tables.BaseFilesQueueTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Base Uploading Dialog (common for Documents Uploading and Classifications Import).
 */
public abstract class BaseUploadingDialog extends WebPage<BaseUploadingDialog> {
    private By modalParentElementBy = By.xpath("//div[@id='upload-dialog' and @aria-hidden='false']");

    BaseUploadingDialog(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAvailable() {
        return dialogTitle().isAvailable() &&
                minimizeDialogButton().isAvailable() &&
                closeDialogButton().isAvailable() &&
                uploadingProgressLabel().isAvailable() &&
                mainProgressBar().isAvailable() &&
                closeThisWindowWhenCompleteCheckbox().isAvailable() &&
                addFilesButton().isAvailable() &&
                cancelAllButton().isAvailable() &&
                getFilesQueueTable().isAvailable();
    }

    private Element dialogHeaderElement() {
        return new Element(driver, By.xpath(getXpathLocatorValue(modalParentElementBy) + "//div[@class='modal-header']"));
    }

    protected Element dialogBodyElement() {
        return new Element(driver, By.xpath(getXpathLocatorValue(modalParentElementBy) + "//div[@class='modal-body']"));
    }

    private LabelText dialogTitle() {
        return new LabelText(driver, By.xpath(dialogHeaderElement().getXpathLocatorValue() + "//h5[@class='modal-title']/b"));
    }

    private Button minimizeDialogButton() {
        return new Button(driver, By.xpath(dialogHeaderElement().getXpathLocatorValue() +
                "//i[contains(@class, 'minus-circle')]"));
    }

    private Button closeDialogButton() {
        return new Button(driver, By.xpath(dialogHeaderElement().getXpathLocatorValue() +
                "//i[contains(@class, 'times-circle') and not(contains(@class, 'hide'))]"));
    }

    private LabelText uploadingProgressLabel() {
        return new LabelText(driver, By.xpath(dialogBodyElement().getXpathLocatorValue() +
                "//span[starts-with(text(), 'Uploaded')]"));
    }

    private Element mainProgressBar() {
        return new Element(driver, By.xpath(dialogBodyElement().getXpathLocatorValue() +
                "//div[starts-with(@class, 'progress-bar')]/parent::div[contains(@class, 'overall-progress')]"));
    }

    private Element mainProgressBar(int progressCompleteness) {
        return new Element(driver, By.xpath(dialogBodyElement().getXpathLocatorValue() +
                "//div[starts-with(@class, 'progress-bar') and contains(@style, '" + progressCompleteness +
                "%')]/parent::div[contains(@class, 'overall-progress')]"));
    }

    private Element closeThisWindowWhenCompleteCheckbox() {
        return new Element(driver, By.xpath(dialogBodyElement().getXpathLocatorValue() +
                "//small[starts-with(text(), 'Close this window')]/preceding-sibling::input"));
    }

    Button controlButtonOnDialog(String buttonName) {
        return new Button(driver, By.xpath(dialogBodyElement().getXpathLocatorValue() +
                "//button[normalize-space()='" + buttonName + "']"));
    }

    private Button addFilesButton() {
        return new Button(driver, By.xpath(dialogBodyElement().getXpathLocatorValue() +
                "//span[normalize-space()='Add Files']"));
    }

    private Button cancelAllButton() {
        return controlButtonOnDialog("Cancel all");
    }

    /**
     * Gets files queue table available on the dialog.
     *
     * @return {@code BaseFilesQueueTable}
     */
    public abstract BaseFilesQueueTable getFilesQueueTable();

    /**
     * Checks if uploading process of a specified file is completed.
     *
     * @param fileIndex - Specifies file index in the files queue table
     * @return {@code boolean}
     */
    public boolean isFileUploaded(int fileIndex) {
        return getFilesQueueTable().getTableRow(fileIndex).isFileUploaded();
    }

    /**
     * Checks if uploading process of a specified file is completed.
     *
     * @param fileName - Specifies file name
     * @return {@code boolean}
     */
    public boolean isFileUploaded(String fileName) {
        return getFilesQueueTable().getTableRow(fileName).isFileUploaded();
    }

    /**
     * Waits until specified file is uploaded.
     *
     * @param fileIndex  - Specifies index of the document in the files queue table
     */
    public void waitUntilFileIsUploaded(int fileIndex) {
        getFilesQueueTable().getTableRow(fileIndex).waitUntilFileIsUploaded();
    }

    /**
     * Waits until specified file is uploaded.
     *
     * @param fileName - Specifies name of the file
     */
    public void waitUntilFileIsUploaded(String fileName) {
        getFilesQueueTable().getTableRow(fileName).waitUntilFileIsUploaded();
    }

    /**
     * Waits until all files are uploaded.
     */
    void waitUntilAllFilesAreUploaded() {
        mainProgressBar(100).waitUntilAvailableFor(180);
    }

    /**
     * Closes the dialog and returns instance of a specified CDS page.
     *
     * @param tClass - Specifies class of a CDS page instance of which is intended to be returned after closing the dialog
     * @return {@code T extends BaseCDSPageHeader}
     */
    <T extends BaseCDSPageHeader> T closeDialog(Class<T> tClass) {
        closeDialogButton().waitUntilAvailable().click();
        waitUntil(!this.isAvailable());
        return constructClassInstance(tClass);
    }
}