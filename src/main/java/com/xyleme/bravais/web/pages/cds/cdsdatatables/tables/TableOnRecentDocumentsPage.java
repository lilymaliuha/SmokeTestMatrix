package com.xyleme.bravais.web.pages.cds.cdsdatatables.tables;

import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnRecentDocumentsPage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.basetables.documentstable.BaseDocumentsTableDataRetriever;
import org.openqa.selenium.WebDriver;

/**
 * Implementation a data table available on Recent Documents page.
 */
public class TableOnRecentDocumentsPage extends BaseDocumentsTableDataRetriever {

    public TableOnRecentDocumentsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Gets specified table row.
     *
     * @param rowIndex - Specifies index of the row intended to be returned
     * @return {@code RowOfTableOnRecentDocumentsPage}
     */
    @Override
    public RowOfTableOnRecentDocumentsPage getRow(int rowIndex) {
        return new RowOfTableOnRecentDocumentsPage(driver, getTableRowElement(rowIndex));
    }
}