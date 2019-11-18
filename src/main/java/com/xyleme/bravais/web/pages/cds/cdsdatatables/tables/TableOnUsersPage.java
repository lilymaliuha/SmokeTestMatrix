package com.xyleme.bravais.web.pages.cds.cdsdatatables.tables;

import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnUsersPage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.basetables.BaseTableDataRetriever;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of a data table available on Users page.
 */
public class TableOnUsersPage extends BaseTableDataRetriever {

    public TableOnUsersPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Gets specified table row.
     *
     * @param rowIndex - Specifies index of the row intended to be returned
     * @return {@code RowOfTableOnUsersPage}
     */
    @Override
    public RowOfTableOnUsersPage getRow(int rowIndex) {
        if (!isTableEmpty()) {
            return new RowOfTableOnUsersPage(driver, getTableRowElement(rowIndex));
        } else {
            throw new RuntimeException("The table is empty, it doesn't contain any rows!");
        }
    }
}