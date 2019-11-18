package com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.baserows;

import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.basetables.BaseCDSDataTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Implementation of Base Row element of CDS Data Table.
 */
public abstract class BaseCDSDataTableRow extends BaseCDSDataTable {
    WebElement recordBody;
    private By detailsLinkBy = By.xpath(".//a[text()='Details']");
    private int indexOfColumnWithDetailsLink;

    public BaseCDSDataTableRow(WebDriver driver, WebElement recordBody) {
        super(driver);
        this.recordBody = recordBody;
    }

    /**
     * Gets list of row parameter elements.
     *
     * @return {@code List<WebElement>}
     */
    private List<WebElement> getListOfRowParameterElements() {
        return recordBody.findElements(By.xpath("./td"));
    }

    /**
     * Gets specified parameter of the row.
     *
     * @param columnTitle - Specifies title of the column
     * @return {@code WebElement}
     */
    protected WebElement getColumnParameterElement(String columnTitle) {
        return getListOfRowParameterElements().get(getColumnIndex(columnTitle));
    }

    /**
     * Gets specified parameter of the row.
     *
     * @param columnIndex - Specifies index of the column
     * @return {@code WebElement}
     */
    protected WebElement getColumnParameterElement(int columnIndex) {
        return getListOfRowParameterElements().get(columnIndex);
    }

    /**
     * Gets last block of elements which belongs to the last td tag on the row (options included in the tag - Details link,
     * options drop-down, Favorites marker, selection checkbox)
     *
     * @return {@code WebElement}
     */
    protected WebElement getLastOptionsBlockOfRow() {
        return getColumnParameterElement(getListOfRowParameterElements().size() - 1);
    }

    /**
     * Gets column text value of the row.
     *
     * @param columnTitle - Specifies title of the column parameter of which is intended to be returned
     * @return {@code String}
     */
    protected String getColumnValue(String columnTitle) {
        return getColumnParameterElement(columnTitle).getText();
    }

    /**
     * Gets id of the row item (document or folder).
     *
     * @return {@code String}
     */
    protected String getRowItemId() {
        return recordBody.getAttribute("data-item-id");
    }

    /**
     * Checks if the row contains Details link.
     *
     * @return {@code boolean}
     */
    private boolean isDetailsLinkPresentInRow() {
        boolean result = false;
        List<WebElement> rowParameters = getListOfRowParameterElements();

        for (WebElement rowParameter : rowParameters) {

            if (rowParameter.findElements(detailsLinkBy).size() > 0) {
                result = true;
                indexOfColumnWithDetailsLink = rowParameters.indexOf(rowParameter);
                break;
            }
        }
        return result;
    }

    /**
     * Checks if Details link is present, if so - clicks it.
     */
    protected void clickDetailsLink() {

        if (isDetailsLinkPresentInRow()) {
            getColumnParameterElement(indexOfColumnWithDetailsLink).findElement(detailsLinkBy).click();
        } else {
            new RuntimeException("The table row doesn't contain 'Details' link!");
        }
    }
}