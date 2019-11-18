package com.xyleme.bravais.web.pages.cds.cdsdatatables.tables;

import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnContentPermissionsPanelOfGroupDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.basetables.BaseCDSDataTableWithOuterContainer;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of a data table available on Content Permissions panel of Group Details page (all three data tables
 * available on the panel are structurally the same therefore represented by one class).
 */
public class TableOnContentPermissionsPanelOfGroupDetailsPage extends BaseCDSDataTableWithOuterContainer {

    public TableOnContentPermissionsPanelOfGroupDetailsPage(WebDriver driver, Element tableContainer) {
        super(driver, tableContainer);
    }

    /**
     * Gets specified table row.
     *
     * @param rowIndex - Specifies index of the row intended to be returned
     * @return {@code RowOfTableOnContentPermissionsPanelOfGroupDetailsPage}
     */
    public RowOfTableOnContentPermissionsPanelOfGroupDetailsPage getRow(int rowIndex) {
        if (!isTableEmpty()) {
            return new RowOfTableOnContentPermissionsPanelOfGroupDetailsPage(driver, tableContainer, getRowElementOfItem(
                    rowIndex));
        } else {
            throw new RuntimeException("The table is empty, it doesn't contain any rows!");
        }
    }

    /**
     * Gets specified table row.
     *
     * @param itemName - Specifies name of the item the row expected to be returned represents
     * @return {@code RowOfTableOnContentPermissionsPanelOfGroupDetailsPage}
     */
    public RowOfTableOnContentPermissionsPanelOfGroupDetailsPage getRow(String itemName) {
        if (!isTableEmpty()) {
            return new RowOfTableOnContentPermissionsPanelOfGroupDetailsPage(driver, tableContainer, getRowElementOfItem(
                    itemName));
        } else {
            throw new RuntimeException("The table is empty, it doesn't contain any rows!");
        }
    }
}