package com.xyleme.bravais.web.pages.cds.cdsdatatables.tables;

import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnDocumentsPage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.basetables.documentstable.BaseDocumentsTableDataRetriever;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of a data table available on Documents page.
 */
public class TableOnDocumentsPage extends BaseDocumentsTableDataRetriever {

    public TableOnDocumentsPage(WebDriver driver) {
        super(driver);
    }

    private Element selectAllCheckbox() {
        return new Element(driver, By.xpath(tableHeaderElement().getXpathLocatorValue() + "//input[@title='Select All']"));
    }

    /**
     * Checks if Select All checkbox is checked.
     *
     * @return {@code boolean}
     */
    private boolean isSelectAllCheckboxChecked() {
        return selectAllCheckbox().getAttribute("checked") != null;
    }

    /**
     * Checks if Select All checkbox is checked, if not - checks it.
     *
     * @return {@code TableOnDocumentsPage}
     */
    public TableOnDocumentsPage checkSelectAllCheckbox() {

        if (!isSelectAllCheckboxChecked()) {
            selectAllCheckbox().waitUntilAvailable().click();
            waitUntil(isSelectAllCheckboxChecked());
        } else {
            System.out.println("Select All checkbox is checked.");
        }
        return this;
    }

    /**
     * Gets specified table row.
     *
     * @param rowIndex - Specifies index of the row intended to be returned
     * @return {@code RowOfTableOnDocumentsPage}
     */
    @Override
    public RowOfTableOnDocumentsPage getRow(int rowIndex) {

        if (!isTableEmpty()) {
            return new RowOfTableOnDocumentsPage(driver, getTableRowElement(rowIndex));
        } else {
            throw new RuntimeException("The table is empty, it doesn't contain any rows!");
        }
    }
}