package com.xyleme.bravais.web.pages.cds.cdsdatatables.tables;

import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnChannelsPage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.basetables.BaseTableDataRetriever;
import org.openqa.selenium.WebDriver;

import java.util.List;

/**
 * Implementation of a data table available on Channels page.
 */
public class TableOnChannelsPage extends BaseTableDataRetriever {

    public TableOnChannelsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Gets specified table row.
     *
     * @param rowIndex - Specifies index of the row
     * @return {@code RowOfTableOnChannelsPage}
     */
    @Override
    public RowOfTableOnChannelsPage getRow(int rowIndex) {
        if (!isTableEmpty()) {
            return new RowOfTableOnChannelsPage(driver, getTableRowElement(rowIndex));
        } else {
            throw new RuntimeException("The table is empty, it doesn't contain any rows!");
        }
    }

    /**
     * Gets list of all available table rows.
     *
     * @return {@code List<RowOfTableOnChannelsPage>}
     */
    public List<RowOfTableOnChannelsPage> getAllTableRows() {
        return getAllRows();
    }
}