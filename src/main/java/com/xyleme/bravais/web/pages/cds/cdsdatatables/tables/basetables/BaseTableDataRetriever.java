package com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.basetables;

import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.baserows.BaseCDSDataTableRow;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a Base Table data (row) retriever.
 */
public abstract class BaseTableDataRetriever extends BaseCDSDataTable {

    public BaseTableDataRetriever(WebDriver driver) {
        super(driver);
    }

    /**
     * Gets table row with the specified index.
     *
     * @param rowIndex - Specified index of the row
     * @return {@code BaseCDSDataTableRow}
     */
    public abstract BaseCDSDataTableRow getRow(int rowIndex);

    /**
     * Gets list of all rows present in the table.
     *
     * @return {@code List<BaseCDSDataTableRow>}
     */
    protected List getAllRows() {
        int numberOfTableRows = getNumberOfTableRows();
        List<BaseCDSDataTableRow> listOfTableRows = new ArrayList<>();

        for (int i = 0; i < numberOfTableRows; i++) {
            listOfTableRows.add(getRow(i));
        }
        return listOfTableRows;
    }
}