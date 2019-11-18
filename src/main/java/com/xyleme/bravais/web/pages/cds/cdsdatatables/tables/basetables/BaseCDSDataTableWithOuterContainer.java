package com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.basetables;

import com.xyleme.bravais.ElementsAvailabilityChecker;
import com.xyleme.bravais.web.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Implementation of a Base Data Table which is located inside of a specified container (not a single table on a page).
 */
public abstract class BaseCDSDataTableWithOuterContainer extends ElementsAvailabilityChecker {
    protected Element tableContainer;

    public BaseCDSDataTableWithOuterContainer(WebDriver driver, Element tableContainer) {
        super(driver);
        this.tableContainer = tableContainer;
        waitUntilElementsAreAvailable(table(), tableHeader(), tableBody());
    }

    private Element table() {
        return new Element(driver, By.xpath(tableContainer.getXpathLocatorValue() +
                "//table[contains(@class, 'list-table')]"));
    }

    private Element tableHeader() {
        return new Element(driver, By.xpath(table().getXpathLocatorValue() + "/thead"));
    }

    private Element tableBody() {
        return new Element(driver, By.xpath(table().getXpathLocatorValue() + "/tbody"));
    }

    private Element columnHeaderElement() {
        return new Element(driver, By.xpath(tableHeader().getXpathLocatorValue() + "/tr/th"));
    }

    private Element tableRowElement() {
        return new Element(driver, By.xpath(tableBody().getXpathLocatorValue() + "/tr"));
    }

    private List<WebElement> getListOfTableRowElements() {
        return driver.findElements(By.xpath(tableRowElement().getXpathLocatorValue()));
    }

    private List<WebElement> getListOfColumnHeaderElements() {
        return driver.findElements(By.xpath(columnHeaderElement().getXpathLocatorValue()));
    }

    /**
     * Checks if a table is empty (if it contains any row elements)
     *
     * @return {@code boolean}
     */
    protected boolean isTableEmpty() {
        return getListOfTableRowElements().size() == 0;
    }

    /**
     * Gets row element.
     *
     * @param rowIndex - Specifies index of the row
     * @return {@code Element}
     */
    protected Element getRowElementOfItem(int rowIndex) {
        return new Element(driver, By.xpath(tableRowElement().getXpathLocatorValue() + "[" + (rowIndex + 1) + "]"));
    }

    /**
     * Gets row element of specific item.
     *
     * @param itemName - Specifies the item name the row element of which is expected to be returned
     * @return {@code Element}
     */
    protected Element getRowElementOfItem(String itemName) {
        return new Element(driver, By.xpath(tableRowElement().getXpathLocatorValue() + "/td[normalize-space()='" +
                itemName + "']/parent::tr"));
    }

    /**
     * Gets index of the specified column.
     *
     * @param columnHeader - Specifies header of the column index of which is intended to be returned
     * @return {@code int}
     */
    protected int getIndexOfColumn(String columnHeader) {
        int indexToReturn = -1;
        List<WebElement> columnHeaderElements = getListOfColumnHeaderElements();
        for (WebElement columnHeaderElement : columnHeaderElements) {
            if (columnHeaderElement.getText().equals(columnHeader)) {
                indexToReturn = columnHeaderElements.indexOf(columnHeaderElement);
                break;
            }
        }
        if (indexToReturn != -1) {
            return indexToReturn;
        } else {
            throw new RuntimeException("Table doesn't contain column with name '" + indexToReturn + "'.");
        }
    }
}