package com.xyleme.bravais.web.pages.cds.cdsdatatables.tables;

import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnFavoritesPage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.basetables.documentstable.BaseDocumentsTableDataRetriever;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of a data table available on Favorites page.
 */
public class TableOnFavoritesPage extends BaseDocumentsTableDataRetriever {

    public TableOnFavoritesPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Gets specified table row.
     *
     * @param rowIndex - Specifies index of the row intended to be returned
     * @return {@code RowOfTableOnFavoritesPage}
     */
    @Override
    public RowOfTableOnFavoritesPage getRow(int rowIndex) {
        return new RowOfTableOnFavoritesPage(driver, getTableRowElement(rowIndex));
    }
}