package com.xyleme.bravais.web.pages.cds.cdsdatatables.tables;

import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnMembersPanelOfGroupDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.basetables.BaseCDSDataTableWithOuterContainer;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of a data table available on Members panel of Group Details page.
 */
public class TableOnMembersPanelOfGroupDetailsPage extends BaseCDSDataTableWithOuterContainer {

    public TableOnMembersPanelOfGroupDetailsPage(WebDriver driver, Element tableContainer) {
        super(driver, tableContainer);
    }

    /**
     * Gets specified table row.
     *
     * @param rowIndex - Specifies index of the row intended to be returned
     * @return {@code RowOfTableOnMembersPanelOfGroupDetailsPage}
     */
    public RowOfTableOnMembersPanelOfGroupDetailsPage getRow(int rowIndex) {

        if (!isTableEmpty()) {
            return new RowOfTableOnMembersPanelOfGroupDetailsPage(driver, tableContainer, getRowElementOfItem(rowIndex));
        } else {
            throw new RuntimeException("The table is empty, it doesn't contain any rows!");
        }
    }

    /**
     * Gets specified table row.
     *
     * @param itemName - Specifies name of the item the row represents
     * @return {@code RowOfTableOnMembersPanelOfGroupDetailsPage}
     */
    public RowOfTableOnMembersPanelOfGroupDetailsPage getRow(String itemName) {

        if (!isTableEmpty()) {
            return new RowOfTableOnMembersPanelOfGroupDetailsPage(driver, tableContainer, getRowElementOfItem(itemName));
        } else {
            throw new RuntimeException("The table is empty, it doesn't contain any rows!");
        }
    }
}