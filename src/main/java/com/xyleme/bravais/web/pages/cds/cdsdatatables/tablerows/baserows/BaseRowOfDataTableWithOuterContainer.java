package com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.baserows;

import com.xyleme.bravais.web.elements.*;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.basetables.BaseCDSDataTableWithOuterContainer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Implementation of a base Table Row which belongs to a Data Table which is located inside of a specified container (not
 * a single table on a page).
 */
public abstract class BaseRowOfDataTableWithOuterContainer extends BaseCDSDataTableWithOuterContainer {
    private Element rowBodyElement;
    private By detailsLinkBy = By.xpath(".//a[text()='Details']");
    private int indexOfColumnWithDetailsLink;

    public BaseRowOfDataTableWithOuterContainer(WebDriver driver, Element tableContainer, Element rowBodyElement) {
        super(driver, tableContainer);
        this.rowBodyElement = rowBodyElement;
    }

    /**
     * Gets specified structure element (<td> structure element) of the row.
     *
     * @param structureElementIndex - Specifies index of the structure element
     * @return {@code Element}
     */
    private Element getRowStructureElement(int structureElementIndex) {
        return new Element(driver, By.xpath(rowBodyElement.getXpathLocatorValue() + "/td[" + (structureElementIndex + 1)
                + "]")).waitUntilAvailable();
    }

    /**
     * Gets specified structure element (<td> structure element) of the row.
     *
     * @param columnTitle - Specifies title of the column structure element of which is intended to be returned
     * @return {@code Element}
     */
    protected Element getRowStructureElement(String columnTitle) {
        int columnIndex = getIndexOfColumn(columnTitle);
        return getRowStructureElement(columnIndex);
    }

    /**
     * Gets last structure element of the row which contains icon clicking on which opens item options drop-down, and
     * row selection checkbox.
     *
     * @return {@code Element}
     */
    private Element getLastStructureElementOfRow() {
        List<WebElement> rowStructureElements = driver.findElements(By.xpath(rowBodyElement.getXpathLocatorValue() + "/td"));
        int indexOfLastStructureElement = rowStructureElements.size() - 1;
        return getRowStructureElement(indexOfLastStructureElement);
    }

    /**
     * Gets item options drop-down icon.
     *
     * @return {@code Element}
     */
    private Element getItemOptionsDropDownIcon() {
        return new Element(driver, By.xpath(getLastStructureElementOfRow().getXpathLocatorValue() +
                "//a[contains(@class, 'dropdown')]"));
    }

    /**
     * Gets column text value of the row.
     *
     * @param columnTitle - Specifies title of the column text value of which is intended to be returned
     * @return {@code String}
     */
    protected String getColumnValue(String columnTitle) {
        return getRowStructureElement(columnTitle).getText();
    }

    /**
     * Gets list of row parameter elements.
     *
     * @return {@code List<WebElement>}
     */
    private List<WebElement> getListOfRowStructureElements() {
        WebElement rowElement = driver.findElement(By.xpath(rowBodyElement.getXpathLocatorValue()));
        return rowElement.findElements(By.xpath("./td"));
    }

    /**
     * Checks if the row contains Details link.
     *
     * @return {@code boolean}
     */
    private boolean isDetailsLinkPresentInRow() {
        boolean result = false;
        List<WebElement> rowStructureElements = getListOfRowStructureElements();
        for (WebElement structureElement : rowStructureElements) {
            if (structureElement.findElements(detailsLinkBy).size() > 0) {
                result = true;
                indexOfColumnWithDetailsLink = rowStructureElements.indexOf(structureElement);
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
            WebElement rowStructureElementWithDetailsLink = driver.findElement(By.xpath(getRowStructureElement(
                    indexOfColumnWithDetailsLink).getXpathLocatorValue()));
            rowStructureElementWithDetailsLink.findElement(detailsLinkBy).click();
        } else {
            new RuntimeException("The table row doesn't contain 'Details' link!");
        }
    }

    /**
     * Clicks row options drop-down icon, waits until the menu is opened and returns element which represents the opened
     * menu.
     *
     * @return {@code Element}
     */
    protected Element openItemOptionsMenuAndReturnOpenedMenuElement() {
        getItemOptionsDropDownIcon().waitUntilAvailable().click();
        return new Element(driver, By.xpath(getLastStructureElementOfRow().getXpathLocatorValue() +
                "//ul[@class='dropdown-menu']")).waitUntilAvailable();
    }

    /**
     * Gets element which represents specific option of opened item options drop-down menu.
     *
     * @param openedMenu - Specifies element which represents opened item options drop-down menu
     * @param option     - Specifies the option element of which is intended to be returned
     * @return {@code Element}
     */
    protected Element getOptionOfOpenedItemOptionsMenu(Element openedMenu, String option) {
        return new Element(driver, By.xpath(openedMenu.getXpathLocatorValue() +
                "//li[not (contains(@class, 'hide'))]/a[text()='" + option + "']")).waitUntilAvailable();
    }

    /**
     * Gets row selection checkbox.
     *
     * @return {@code Checkbox}
     */
    private CheckBox getRowSelectionCheckbox() {
        return new CheckBox(driver, By.xpath(getLastStructureElementOfRow().getXpathLocatorValue() + "//input"));
    }

    /**
     * Checks if row is selected.
     *
     * @return {@code boolean}
     */
    private boolean isRowSelected() {
        return rowBodyElement.getAttribute("class").contains("row-selected");
    }

    /**
     * Selects the table row.
     */
    public void select() {
        if (!isRowSelected()) {
            rowBodyElement.hoverOver();
            new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(
                    By.xpath(getRowSelectionCheckbox().getXpathLocatorValue()))).click();
        } else {
            System.out.println("The row is already selected.");
        }
    }
}