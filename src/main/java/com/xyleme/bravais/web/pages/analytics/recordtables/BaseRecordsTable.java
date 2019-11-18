package com.xyleme.bravais.web.pages.analytics.recordtables;

import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.pages.analytics.BaseAnalyticsPageHeader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Implementation of Base Records Table.
 */
public abstract class BaseRecordsTable extends BaseAnalyticsPageHeader {
    private String tableBodyLocator = "//table[@class='table table-striped']";
    private By tableHeaderBy = By.xpath(tableBodyLocator + "/thead/tr");
    private By tableBodyBy = By.xpath(tableBodyLocator + "/tbody");

    protected BaseRecordsTable(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAvailable() {
        return table().isAvailable() &&
                tableHeader().isAvailable() &&
                tableBody().isAvailable() &&
                getTableColumnHeaderElement("#").isDisplayed();
    }

    private Element table() {
        return new Element(driver, By.xpath(tableBodyLocator));
    }

    private Element tableHeader() {
        return new Element(driver, tableHeaderBy);
    }

    private Element tableBody() {
        return new Element(driver, tableBodyBy);
    }

    /**
     * Gets table column header elements.
     *
     * @return {@code List<WebElement>}
     */
    private List<WebElement> getTableColumnHeaderElements() {
        return driver.findElements(By.xpath("//table[@class='table table-striped']//thead//th"));
    }

    /**
     * Gets table column header element with specified title.
     *
     * @param columnHeaderTitle - Specifies column header title
     * @return {@code WebElement}
     */
    WebElement getTableColumnHeaderElement(String columnHeaderTitle) {
        WebElement columnHeaderElement = null;
        for (WebElement headerElement : getTableColumnHeaderElements()) {
            if (headerElement.getText().equals(columnHeaderTitle)) {
                columnHeaderElement = headerElement;
                break;
            }
        }
        if (columnHeaderElement != null) {
            return columnHeaderElement;
        } else {
            throw new RuntimeException("Table doesn't contain column header with title '" + columnHeaderTitle + "'!");
        }
    }

    /**
     * Gets index of specified column.
     *
     * @param columnTitle - Specifies column title
     * @return {@code int}
     */
    protected int getColumnIndex(String columnTitle) {
        int index = -1;
        List<WebElement> tableColumnTitles = getTableColumnHeaderElements();
        for (WebElement titleElement : tableColumnTitles) {
            if (titleElement.getText().equals(columnTitle)) {
                index = tableColumnTitles.indexOf(titleElement);
                break;
            }
        }
        if (index == -1) {
            throw new RuntimeException("Table doesn't contain column with title '" + columnTitle + "'.");
        }
        return index;
    }

    /**
     * Gets list of table row elements.
     *
     * @return {@code List<WebElement>}
     */
    List<WebElement> getTableRows() {
        return driver.findElement(tableBodyBy).findElements(By.xpath(".//td[contains(@class, 'cell')]/parent::tr"));
    }

    /**
     * Gets number of table records (table rows) available in the table.
     *
     * @return {@code int}
     */
    public int getNumberOfTableRecords() {
        return getTableRows().size();
    }
}