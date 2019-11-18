package com.xyleme.bravais.web.pages.cds.popupdialogs.uploadingdialog.filesqueuetable.tables;

import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.pages.cds.popupdialogs.uploadingdialog.FileUploadingDialog;
import com.xyleme.bravais.web.pages.cds.popupdialogs.uploadingdialog.filesqueuetable.tablerows.BaseRowOfFilesQueueTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Implementation of a base table with file/s intended/being uploaded/imported available on the File/s uploading/importing
 * dialog.
 */
public abstract class BaseFilesQueueTable extends FileUploadingDialog {
    private By tableRowElementBy = By.xpath(tableBody().getXpathLocatorValue() + "/table//tr[not(contains(@class, 'hide'))]");

    BaseFilesQueueTable(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return tableBody().isAvailable();
    }

    private Element tableBody() {
        return new Element(driver, By.xpath(dialogBodyElement().getXpathLocatorValue() +
                "//div[@class='uploader-queue']"));
    }

    WebElement getTableRowElementOfFile(String fileName) {
        return driver.findElement(By.xpath("//td[@class='uploader-queue-file-name']//small[text()='" + fileName
                + "' and(not(contains(@class, 'hide')))]//ancestor::tr"));
    }

    /**
     * Gets list of web elements which represent table rows.
     *
     * @return {@code List<WebElement>}
     */
    List<WebElement> getTableRowElements() {
        return driver.findElements(tableRowElementBy);
    }

    /**
     * Gets number of table rows.
     *
     * @return {@code int}
     */
    int getNumberOfTableRows() {
        return getTableRowElements().size();
    }

    /**
     * Constructs specifies row object.
     *
     * @param rowType - Specifies type of the row object expected to be constructed
     * @return {@code <T extends BaseRowOfFilesQueueTable>}
     */
    <T extends BaseRowOfFilesQueueTable> T constructRow(Class<T> rowType, WebElement rowBodyElement) {
        try {
            return rowType.getDeclaredConstructor(WebDriver.class, WebElement.class).newInstance(driver,
                    rowBodyElement);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot construct row object of type " + rowType.getName()
                    + "\n<<<!>>> Check if expected row is available in the table! <<<!>>>");
        }
    }

    /**
     * Gets specified table row.
     *
     * @param rowIndex - Specifies index of the row
     * @return {@code BaseRowOfFilesQueueTable}
     */
    public abstract BaseRowOfFilesQueueTable getTableRow(int rowIndex);

    /**
     * Gets specified table row.
     *
     * @param fileName - Specifies name of the file the row represents
     * @return {@code BaseRowOfFilesQueueTable}
     */
    public abstract BaseRowOfFilesQueueTable getTableRow(String fileName);
}