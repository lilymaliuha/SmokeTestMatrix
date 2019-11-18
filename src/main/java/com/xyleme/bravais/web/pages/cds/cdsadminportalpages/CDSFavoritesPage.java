package com.xyleme.bravais.web.pages.cds.cdsadminportalpages;

import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnFavoritesPage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.TableOnFavoritesPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of CDS Favorites page.
 */
public class CDSFavoritesPage extends BaseCDSPageWithDataTableAndBulkChangesDropDown {

    public CDSFavoritesPage(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                staticAlertBox().isAvailable();
    }

    private Element staticAlertBox() {
        return new Element(driver, By.xpath("//div[@id='add-favorite-form']//p[contains(@class, 'alert')]"));
    }

    /**
     * Gets Documents table available on the page.
     *
     * @return {@code TableOnFavoritesPage}
     */
    @Override
    public TableOnFavoritesPage getDataTable() {
        return new TableOnFavoritesPage(driver);
    }

    /**
     * Enters specified text item into the 'Filter Favorites' input field and returns the first table row available
     * after the filtering.
     *
     * @param filteringItem - Specifies the item intended to be entered into the filter field
     * @return {@code RowOfTableOnFavoritesPage}
     */
    public RowOfTableOnFavoritesPage getFilteredTableItem(String filteringItem) {
        enterQueryIntoFilterInputField(filteringItem);
        RowOfTableOnFavoritesPage row = getDataTable().getRow(0);

        if (row.getItemName().equals(filteringItem)) {
            return row;
        } else {
            throw new RuntimeException("Destination folder doesn't contain item with name '" + filteringItem + "'.");
        }
    }
}