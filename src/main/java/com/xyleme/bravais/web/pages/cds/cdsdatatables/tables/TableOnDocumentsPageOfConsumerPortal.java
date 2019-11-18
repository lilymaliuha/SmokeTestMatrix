package com.xyleme.bravais.web.pages.cds.cdsdatatables.tables;

import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnConsumerPortalDocumentsPage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.basetables.documentstable.BaseDocumentsTableDataRetriever;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of a data table available on the 'Documents' page on CDS Consumer Portal.
 */
public class TableOnDocumentsPageOfConsumerPortal extends BaseDocumentsTableDataRetriever {

    public TableOnDocumentsPageOfConsumerPortal(WebDriver driver) {
        super(driver);
    }

    /**
     * Gets specified table row.
     *
     * @param rowIndex - Specifies index of the row intended to be returned
     * @return {@code RowOfTableOnConsumerPortalDocumentsPage}
     */
    @Override
    public RowOfTableOnConsumerPortalDocumentsPage getRow(int rowIndex) {
        return new RowOfTableOnConsumerPortalDocumentsPage(driver, getTableRowElement(rowIndex));
    }
}