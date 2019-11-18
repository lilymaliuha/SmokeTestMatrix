package com.xyleme.bravais.web.pages.cds.cdsadminportalpages;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.LabelText;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.basetables.BaseCDSDataTable;
import com.xyleme.bravais.web.pages.cds.functionalmobules.filterform.FilterForm;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Implementation of Base CDS Page which contains data Table and Filter for filtering Table elements (without
 * 'Bulk Changes' drop-down!).
 */
public abstract class BaseCDSPageWithDataTable extends BaseCDSPageHeader {
    By parentFilterFormElementLocator = By.xpath("//*[@id='list-filter']");

    BaseCDSPageWithDataTable(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                pageTitle().isAvailable() &&
                getFilterForm().isAvailable() &&
                footerLabelWithBuildNumber().isAvailable();
    }

    private LabelText pageTitle() {
        return new LabelText(driver, By.xpath("//h1[contains(@class, 'title')]"));
    }

    private Element errorMessagePopUp() {
        return new Element(driver, By.xpath("//div[contains(@class, 'notification-error')]"));
    }

    /**
     * Gets filter form.
     *
     * @return {@code FilterForm}
     */
    private FilterForm getFilterForm() {
        return new FilterForm(driver, parentFilterFormElementLocator);
    }

    /**
     * Gets number of items (documents/folders/custom attributes) displayed on the counter next to the Filter input field.
     *
     * @return {@code int}
     */
    public int getNumberOfItemsDisplayedOnItemsCounter() {
        return getFilterForm().getNumberOfItemsDisplayedOnItemsCounter();
    }

    /**
     * Enters specified query into Filter input field.
     *
     * @param query - Specifies the query intended to be entered into the filter
     * @return {@code BaseCDSPageWithDataTable}
     */
    public BaseCDSPageWithDataTable enterQueryIntoFilterInputField(String query) {
        getFilterForm().enterQueryIntoFilterInputField(query);
        return this;
    }

    /**
     * Clicks Reset button next to Filter input field.
     *
     * @return {@code BaseCDSPageWithDataTable}
     */
    public BaseCDSPageWithDataTable resetFilter() {
        getFilterForm().resetFilter();
        return this;
    }

    /**
     * Gets error message displayed on a warning popup and closes the popup.
     *
     * @return {@code String}
     */
    public String getPoppedUpErrorMessageAndClosePopUp() {
        String popUpXPathLocatorValue = errorMessagePopUp().waitUntilAvailable().getXpathLocatorValue();
        String message = new LabelText(driver, By.xpath(popUpXPathLocatorValue + "/div")).getText();
        Button closeButton = new Button(driver, By.xpath(errorMessagePopUp().getXpathLocatorValue() +
                "/button[contains(@class, 'close')]"));
        closeButton.waitUntilAvailable().clickUsingJS();
        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
                popUpXPathLocatorValue)));
        return message;
    }

    /**
     * Gets Documents table on a page.
     *
     * @return {@code BaseCDSDataTable}
     */
    public abstract BaseCDSDataTable getDataTable();
}