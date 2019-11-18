package com.xyleme.bravais.web.pages.cds.cdsdatatables.tables;

import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnCustomAttributesPage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.basetables.BaseTableDataRetriever;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of a data table available on Custom Attributes page.
 */
public class TableOnCustomAttributesPage extends BaseTableDataRetriever {

    public TableOnCustomAttributesPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Gets specified table row.
     *
     * @param rowIndex - Specifies index of the row intended to be returned
     * @return {@code RowOfTableOnCustomAttributesPage}
     */
    @Override
    public RowOfTableOnCustomAttributesPage getRow(int rowIndex) {

        if (!isTableEmpty()) {
            return new RowOfTableOnCustomAttributesPage(driver, getTableRowElement(rowIndex));
        } else {
            throw new RuntimeException("The table is empty, it doesn't contain any rows!");
        }
    }
}