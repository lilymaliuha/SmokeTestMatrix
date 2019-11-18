package com.xyleme.bravais.web.pages.cds.cdsadminportalpages;

import com.xyleme.bravais.web.elements.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Base Details page (common for Folder Details and Document Details pages).
 */
public abstract class BaseCDSDocumentsPageItemDetailsPage extends BaseCDSItemDetailsPage {

    public BaseCDSDocumentsPageItemDetailsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                favoritesStar().isAvailable() &&
                moreButton().isAvailable();
    }

    private Element favoritesStar() {
        return new Element(driver, By.xpath("//i[starts-with(@class, 'icon-favorite') and not (contains(@class, 'hide'))]"));
    }

    private Button moreButton() {
       return buttonUnderBreadcrumbsBlock("More");
    }

    /**
     * Checks if a document/folder is marked as favorite (checks status of 'Favorites' star next to a document/folder name).
     *
     * @return {@code boolean}
     */
    public boolean isItemMarkedAsFavorite() {
        return favoritesStar().getAttribute("class").contains("active");
    }

    /**
     * Clicks 'More' button, waits until drop-down menu opens and clicks on a specific menu option.
     *
     * @param option - Specifies drop-down menu option intended to be clicked
     */
    protected void selectMoreDropDownMenuOption(String option) {
        moreButton().waitUntilAvailable().click();
        Element openedDropDownMenu = new Element(driver, By.xpath(moreButton().getXpathLocatorValue() +
                "/following-sibling::ul[contains(@class, 'dropdown-menu')]"));
        Link optionLinkInDropDownMenu = new Link(driver, By.xpath(openedDropDownMenu.getXpathLocatorValue() +
                "/li[not(contains(@class, 'hide'))]/a[normalize-space()='" + option + "']")).waitUntilAvailable();
        optionLinkInDropDownMenu.click();
    }
}