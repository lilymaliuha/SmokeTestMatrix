package com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.basetables;

import com.xyleme.bravais.ElementsAvailabilityChecker;
import com.xyleme.bravais.web.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Implementation of a Base CDS Data Table.
 */
public abstract class BaseCDSDataTable extends ElementsAvailabilityChecker {

    public BaseCDSDataTable(WebDriver driver) {
        super(driver);
        waitUntilElementsAreAvailable(table(), tableHeaderElement(), tableBodyElement());
    }

    private Element table() {
        return new Element(driver, By.xpath("//table[contains(@class, 'list-table')]"));
    }

    protected Element tableHeaderElement() {
        return new Element(driver, By.xpath(table().getXpathLocatorValue() + "/thead"));
    }

    private Element tableBodyElement() {
        return new Element(driver, By.xpath(table().getXpathLocatorValue() + "/tbody"));
    }

    /**
     * Gets table column header elements.
     *
     * @return {@code List<WebElement>}
     */
    private List<WebElement> getTableColumnHeaderElements() {
        return driver.findElements(By.xpath(tableHeaderElement().getXpathLocatorValue() + "//th"));
    }

    /**
     * Gets index of the table column with specified title.
     *
     * @param columnTitle - Specifies column title
     * @return {@code int}
     */
    protected int getColumnIndex(String columnTitle) {
        int indexToReturn = -1;
        List<WebElement> columnHeaders = getTableColumnHeaderElements();
        for (WebElement titleElement : columnHeaders) {
            if (titleElement.getText().equals(columnTitle)) {
                indexToReturn = columnHeaders.indexOf(titleElement);
                break;
            }
        }
        if (indexToReturn == -1) {
            throw new RuntimeException("Table doesn't contain column with title '" + columnTitle + "'.");
        }
        return indexToReturn;
    }

    /**
     * Gets list of table row elements.
     *
     * @return {@code List<WebElement>}
     */
    private List<WebElement> getTableRowElements() {
        return driver.findElements(By.xpath(tableBodyElement().getXpathLocatorValue() + "//tr"));
    }

    /**
     * Checks if a table is empty (if it contains any row elements)
     *
     * @return {@code boolean}
     */
    protected boolean isTableEmpty() {
        return getTableRowElements().size() == 0;
    }

    /**
     * Gets specified table row element.
     *
     * @param rowIndex - Specifies index of the table row
     * @return {@code WebElement}
     */
    protected WebElement getTableRowElement(int rowIndex) {
        return getTableRowElements().get(rowIndex);
    }

    /**
     * Gets number of table rows.
     *
     * @return {@code int}
     */
    public int getNumberOfTableRows() {
        return getTableRowElements().size();
    }
}