package com.xyleme.bravais.web.pages.cds.functionalmobules.filterform;

import com.xyleme.bravais.web.WebPage;
import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.LabelText;
import com.xyleme.bravais.web.elements.TextInput;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.xyleme.bravais.BaseTest.staticSleep;

/**
 * Implementation of Filter Form available on all pages which contain data tables (form without Bulk Changes drop-down).
 */
public class FilterForm extends WebPage<FilterForm> {
    Element parentFormElement;

    public FilterForm(WebDriver driver, By parentFormElementLocator) {
        super(driver);
        parentFormElement = new Element(driver, parentFormElementLocator);
    }

    @Override
    public FilterForm load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return counterNextToFilterInputField().isAvailable() &&
                filterInputField().isAvailable() &&
                resetFilterButton().isAvailable();
    }

    private LabelText counterNextToFilterInputField() {
        return new LabelText(driver, By.xpath(parentFormElement.getXpathLocatorValue() + "//div[@id='count-total-count']"));
    }

    private TextInput filterInputField() {
        return new TextInput(driver, By.xpath(parentFormElement.getXpathLocatorValue() + "//input[contains(@id, 'filter')]"));
    }

    private Button resetFilterButton() {
        return new Button(driver, By.xpath(parentFormElement.getXpathLocatorValue() + "//button[text()='Reset']"));
    }

    /**
     * Gets number of items (documents/folders/custom attributes) displayed on the counter next to the Filter input field.
     *
     * @return {@code int}
     */
    public int getNumberOfItemsDisplayedOnItemsCounter() {
        String countedItems = counterNextToFilterInputField().getText();
        return Integer.parseInt(countedItems.substring(countedItems.indexOf("/ ") + 2));
    }

    /**
     * Enters specified query into Filter input field.
     *
     * @param query - Specifies the query intended to be entered into the filter
     */
    public void enterQueryIntoFilterInputField(String query) {
        filterInputField().waitUntilAvailable().clear();
        filterInputField().waitUntilAvailable().sendKeys(query);
        waitForAngularJSProcessing();
    }

    /**
     * Clicks Reset button next to Filter input field.
     */
    public void resetFilter() {
        resetFilterButton().click();
        staticSleep(0.5);
    }
}