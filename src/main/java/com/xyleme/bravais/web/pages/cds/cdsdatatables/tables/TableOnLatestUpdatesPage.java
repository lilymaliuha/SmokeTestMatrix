package com.xyleme.bravais.web.pages.cds.cdsdatatables.tables;

import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnLatestUpdatesPage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.basetables.documentstable.BaseDocumentsTableDataRetriever;
import org.openqa.selenium.WebDriver;

/**
 * Implementation a data table available on Latest Updates page.
 */
public class TableOnLatestUpdatesPage extends BaseDocumentsTableDataRetriever {

    public TableOnLatestUpdatesPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Gets specified table row.
     *
     * @param rowIndex - Specifies index of the row intended to be returned
     * @return {@code RowOfTableOnLatestUpdatesPage}
     */
    @Override
    public RowOfTableOnLatestUpdatesPage getRow(int rowIndex) {
        return new RowOfTableOnLatestUpdatesPage(driver, getTableRowElement(rowIndex));
    }
}