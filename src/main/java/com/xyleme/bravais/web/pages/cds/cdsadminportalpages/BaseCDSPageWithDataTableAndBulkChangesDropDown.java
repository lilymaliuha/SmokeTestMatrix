package com.xyleme.bravais.web.pages.cds.cdsadminportalpages;

import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.LabelText;
import com.xyleme.bravais.web.pages.cds.functionalmobules.filterform.FilterFormWithBulkChanges;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Base CDS Page which contains data Table and Filter for filtering Table elements and 'Bulk Changes'
 * drop-down.
 */
public abstract class BaseCDSPageWithDataTableAndBulkChangesDropDown extends BaseCDSPageWithDataTable {

    public BaseCDSPageWithDataTableAndBulkChangesDropDown(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                getFilterFormWithBulkChanges().isAvailable() &&
                paginationInfoLabel().isAvailable() &&
                paginationNavigationBlock().isAvailable();
    }


    private LabelText paginationInfoLabel() {
        return new LabelText(driver, By.xpath("//div[@class='pagination-info']"));
    }

    private Element paginationNavigationBlock() {
        return new Element(driver, By.xpath("//ul[starts-with(@class, 'pagination')]"));
    }

    /**
     * Gets Filter form with Bulk Changes drop-down.
     *
     * @return {@code FilterFormWithBulkChanges}
     */
    private FilterFormWithBulkChanges getFilterFormWithBulkChanges() {
        return new FilterFormWithBulkChanges(driver, parentFilterFormElementLocator);
    }

    /**
     * Selects specified option of 'Bulk Changes' drop-down.
     *
     * @param option - Specifies the option of the drop-down list intended to be selected
     * @return {@code BaseCDSPageWithDataTableAndBulkChangesDropDown}
     */
    public BaseCDSPageWithDataTableAndBulkChangesDropDown selectOptionOfBulkChangesDropDown(String option) {
        getFilterFormWithBulkChanges().selectOptionOfBulkChangesDropDown(option);
        return this;
    }

    /**
     * Checks if the specified option is present in the Bulk Changes drop-down menu.
     *
     * @param option - Specifies the option intended to be checked
     * @return {@code boolean}
     */
    boolean isOptionPresentInBulkChangesDropDown(String option) {
        return getFilterFormWithBulkChanges().isOptionPresentInBulkChangesDropDown(option);
    }
}