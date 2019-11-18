package com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.baserows;

import com.xyleme.bravais.web.WebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Implementation of Base Row element with Bulk Changes of CDS Data Table.
 */
public abstract class BaseCDSTableRowWithBulkChanges extends BaseCDSDataTableRow {
    private WebElement selectionCheckbox = getLastOptionsBlockOfRow().findElement(By.xpath(".//input"));

    public BaseCDSTableRowWithBulkChanges(WebDriver driver, WebElement recordBody) {
        super(driver, recordBody);
    }

    private By optionInOpenedItemOptionsDropDownMenuBy(String option) {
        return By.xpath(".//li[not (contains(@class, 'hide'))]/a[text()='" + option + "']");
    }

    /**
     * Clicks drop-down icon next to the Details link to open the drop-down menu, waits until the menu is is opened and
     * returns the element which represents the opened menu.
     *
     * @return {@code WebElement}
     */
    private WebElement openItemOptionsMenuAndReturnOpenedMenuElement() {
        getLastOptionsBlockOfRow().findElement(By.xpath(".//a[contains(@class, 'dropdown')]")).click();
        return new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(
                getLastOptionsBlockOfRow().findElement(By.xpath(".//ul[@class='dropdown-menu']"))));
    }

    /**
     * Opens item options menu and clicks the specified option.
     *
     * @param option - Specifies the option intended to be clicked.
     */
    public void clickOptionOfItemOptionsMenu(String option) {
        WebElement openedOptionsMenu = openItemOptionsMenuAndReturnOpenedMenuElement();
        openedOptionsMenu.findElement(optionInOpenedItemOptionsDropDownMenuBy(option)).click();
    }

    /**
     * Clicks drop-down icon next to the Details link to open the drop-down menu, checks if the expected option is
     * available in the menu and if so - clicks it.
     *
     * @param option - Specifies the drop-down menu option intended to be selected
     * @param tClass - Specifies the class instance of which is expected to be returned
     * @return {@code T extends WebPage}
     */
    protected <T extends WebPage> T openItemOptionsDropDownAndSelectMenuOption(String option, Class<T> tClass) {
        WebElement openedOptionsMenu = openItemOptionsMenuAndReturnOpenedMenuElement();

        if (openedOptionsMenu.findElements(optionInOpenedItemOptionsDropDownMenuBy(option)).size() > 0) {

            if (!option.equals("Download")) {
                openedOptionsMenu.findElement(optionInOpenedItemOptionsDropDownMenuBy(option)).click();
            } else {
                System.out.println("'Download' option is available, but the downloading procedure needs to be handled " +
                        "separately - using respective API call.");
            }
        } else {
            throw new RuntimeException("Option '" + option + "' is not available in the item options ('More') menu!");
        }
        return constructClassInstance(tClass);
    }

    /**
     * Checks whether the row selection checkbox is checked.
     *
     * @return {@code boolean}
     */
    private boolean isRowCheckboxChecked() {
        return recordBody.getAttribute("class").contains("row-selected");
    }

    /**
     * Hovers over the row to change the state of the selection checkbox from disabled to enabled and and clicks on the
     * checkbox once it becomes clickable.
     */
    private void hoverOverRowAndClickSelectionCheckbox() {
        new Actions(driver).moveToElement(recordBody).build().perform();
        new WebDriverWait(driver, 5).until(
                ExpectedConditions.elementToBeClickable(selectionCheckbox)).click();
    }

    /**
     * Checks or unchecks the row selection checkbox.
     *
     * @param check - Specifies the decision (true - check, false - uncheck)
     */
    protected void checkOrUncheckRow(boolean check) {
        boolean isRowChecked = isRowCheckboxChecked();

        if (check && !isRowChecked) {
            hoverOverRowAndClickSelectionCheckbox();
            new WebDriverWait(driver, 5).until(ExpectedConditions.attributeContains(recordBody,
                    "class", "row-selected"));
        } else if (!check && isRowChecked) {
            hoverOverRowAndClickSelectionCheckbox();
            new WebDriverWait(driver, 5).until(ExpectedConditions.attributeToBe(recordBody,
                    "class","item-row ng-scope"));
        } else {
            System.out.println("'checkOrUncheckRow' method: No action has been taken since the intended action " +
                    "doesn't apply to the current state of the selection checkbox.\n>> Intended action: check? - "
                    + check + "\n>> Current state of the checkbox: is checked? - " + isRowChecked);
        }
    }
}