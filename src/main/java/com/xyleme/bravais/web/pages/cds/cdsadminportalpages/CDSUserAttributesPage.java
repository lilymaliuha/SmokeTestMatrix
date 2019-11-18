package com.xyleme.bravais.web.pages.cds.cdsadminportalpages;

import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.LabelText;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of CDS User Attributes page.
 */
public class CDSUserAttributesPage extends BaseCDSPageHeader {

    public CDSUserAttributesPage(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                pageTitle().isAvailable() &&
                getTable().isAvailable();
    }

    private LabelText pageTitle() {
        return new LabelText(driver, By.xpath("//h1[contains(@class, 'title')]"));
    }

    private Element dataTableParentElement() {
        return new Element(driver, By.xpath("//div[@id='user-attributes-list']/table"));
    }

    /**
     * Gets page title.
     *
     * @return {@code String}
     */
    public String getPageTitle() {
        return pageTitle().getText();
    }

    /**
     * Gets Data Table available on the page.
     *
     * @return {@code DataTable}
     */
    public DataTable getTable() {
        return new DataTable(driver);
    }

    /**
     * Implementation of data table available on the page.
     */
    public class DataTable extends CDSUserAttributesPage {

        public DataTable(WebDriver driver) {
            super(driver);
            this.waitUntilAvailable();
        }

        @Override
        public boolean isAvailable() {
            return tableHeader().isAvailable() &&
                    tableBody().isAvailable();
        }

        private Element tableHeader() {
            return new Element(driver, By.xpath(dataTableParentElement().getXpathLocatorValue() + "/thead"));
        }

        private Element tableBody() {
            return new Element(driver, By.xpath(dataTableParentElement().getXpathLocatorValue() + "/tbody"));
        }

        private By tableRowElementBy = By.xpath(tableBody().getXpathLocatorValue() + "/tr");

        /**
         * Gets list of web elements which represent table rows.
         *
         * @return {@code List<WebElement>}
         */
        private List<WebElement> getTableRowElements() {
            return getElementsDynamically(tableRowElementBy);
        }

        /**
         * Gets list of web elements which represent table column headers.
         *
         * @return {@code List<WebElement>}
         */
        private List<WebElement> getColumnHeaderElements() {
            return getElementsDynamically(By.xpath(tableHeader().getXpathLocatorValue() +
                    "//th[@class and (not(contains(@class, 'hide')))]"));
        }

        /**
         * Gets index of column with specified title.
         *
         * @param columnTitle - Specifies title of the column index of which is intended to be returned
         * @return {@code int}
         */
        private int getIndexOfColumn(String columnTitle) {
            List<WebElement> columnHeaders = getColumnHeaderElements();
            int indexToReturn = - 1;
            for (WebElement columnHeader : columnHeaders) {
                if (columnHeader.getText().equals(columnTitle)) {
                    indexToReturn = columnHeaders.indexOf(columnHeader);
                    break;
                }
            }
            if (indexToReturn != -1) {
                return indexToReturn;
            } else {
                throw new RuntimeException("The table doesn't contain column with title ' " + columnTitle + "'.");
            }
        }

        /**
         * Gets XPATH locator of row element with specific name.
         *
         * @param name - Specifies the name parameter of the row intended to be returned
         * @return {@code WebElement}
         */
        private By getXPATHLocatorOfRowElementWithSpecificName(String name) {
            return By.xpath(getXpathLocatorValue(tableRowElementBy) + "/td[@class and not(contains(@class, 'hide')) and " +
                    "normalize-space()='" + name + "']" +
                    "[" + getIndexOfColumn("Name") + 1 + "]/parent::tr");
        }

        /**
         * Gets web element which represents specific column value of specific row
         *
         * @param rowName     - Specifies Name parameter of the row value element of which is intended to be returned
         * @param columnTitle - Specifies column title row value of which is intended to be returned
         * @return {@code WebElement}
         */
        private WebElement getRowValueElement(String rowName, String columnTitle) {
            int columnIndex = getIndexOfColumn(columnTitle);
            String rowXPATHLocatorValue = getXpathLocatorValue(getXPATHLocatorOfRowElementWithSpecificName(rowName));
            return getElementsDynamically(By.xpath(rowXPATHLocatorValue + "/td[@class and not (contains(@class, 'hide'))]"))
                    .get(columnIndex);
        }

        /**
         * Gets value of specific column which belongs to a specific row.
         *
         * @param rowName     - Specifies name of the row column value of which is intended to be returned
         * @param columnTitle - Specifies title of the column row value which is intended to be returned
         * @return {@code Sring}
         */
        public String getColumnValueOfRow(String rowName, String columnTitle) {
            return getRowValueElement(rowName, columnTitle).getText();
        }

        /**
         * Gets number of table rows.
         *
         * @return {@code int}
         */
        private int getNumberOfTableRows() {
            return getTableRowElements().size();
        }

        /**
         * Gets list of names of table rows.
         *
         * @return {@code List<String>}
         */
        public List<String> getNamesOfRows() {
            List<String> rowNames = new ArrayList<>();
            int numberOfRows = getNumberOfTableRows();
            int nameColumnIndex = getIndexOfColumn("Name");
            for (int i = 0; i < numberOfRows; i++) {
                String tableRowLocator = "(" + getXpathLocatorValue(tableRowElementBy) + ")[" + (i + 1) + "]";
                LabelText rowNameLabel = new LabelText(driver, By.xpath(tableRowLocator +
                        "/td[@class and not(contains(@class, 'hide'))][" + (nameColumnIndex + 1) +"]"));
                rowNames.add(rowNameLabel.waitUntilAvailable().getText());
            }
            if (rowNames.size() == numberOfRows) {
                return rowNames;
            } else {
                throw new RuntimeException("Not all row names have been retrieved!");
            }
        }
    }
}