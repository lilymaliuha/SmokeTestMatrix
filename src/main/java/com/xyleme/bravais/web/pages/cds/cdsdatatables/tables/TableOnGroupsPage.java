package com.xyleme.bravais.web.pages.cds.cdsdatatables.tables;

import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnGroupsPage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.basetables.BaseTableDataRetriever;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of a data table available on Groups page.
 */
public class TableOnGroupsPage extends BaseTableDataRetriever {

    public TableOnGroupsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Gets specified table row.
     *
     * @param rowIndex - Specifies index of the row intended to be returned
     * @return {@code RowOfTableOnGroupsPage}
     */
    @Override
    public RowOfTableOnGroupsPage getRow(int rowIndex) {

        if (!isTableEmpty()) {
            return new RowOfTableOnGroupsPage(driver, getTableRowElement(rowIndex));
        } else {
            throw new RuntimeException("The table is empty, it doesn't contain any rows!");
        }
    }
}