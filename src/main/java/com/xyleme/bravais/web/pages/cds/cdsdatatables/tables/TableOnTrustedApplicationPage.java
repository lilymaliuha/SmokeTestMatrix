package com.xyleme.bravais.web.pages.cds.cdsdatatables.tables;

import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnTrustedApplicationPage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.basetables.BaseTableDataRetriever;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of a data table available on Trusted Applications page.
 */
public class TableOnTrustedApplicationPage extends BaseTableDataRetriever {

    public TableOnTrustedApplicationPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Gets specified table row.
     *
     * @param rowIndex - Specifies index of the row intended to be returned
     * @return {@code RowOfTableOnTrustedApplicationPage}
     */
    @Override
    public RowOfTableOnTrustedApplicationPage getRow(int rowIndex) {

        if (!isTableEmpty()) {
            return new RowOfTableOnTrustedApplicationPage(driver, getTableRowElement(rowIndex));
        } else {
            throw new RuntimeException("The table is empty, it doesn't contain any rows!");
        }
    }
}