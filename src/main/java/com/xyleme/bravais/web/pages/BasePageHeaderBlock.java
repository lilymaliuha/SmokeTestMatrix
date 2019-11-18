package com.xyleme.bravais.web.pages;

import com.xyleme.bravais.web.WebPage;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.Image;
import com.xyleme.bravais.web.elements.LabelText;
import com.xyleme.bravais.web.elements.Link;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSLogoutPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implementation of Base Page Header Block (used in CDS Portal and in Analytics).
 */
public abstract class BasePageHeaderBlock extends WebPage<BasePageHeaderBlock> {
    private boolean consumerPortal = false;
    private By topNavigationBarBy = By.xpath("//div[@id='top-navbar']");
    private By navigationMenuUnderTopNavBarBy = By.xpath("//div[@id='top-navbar-menu']");
    protected String stringValueOfTopNavigationBarXPATHLocator = getXpathLocatorValue(topNavigationBarBy);
    protected String stringValueOfNavigationMenuUnderTopNavBarXPATHLocator = getXpathLocatorValue(navigationMenuUnderTopNavBarBy);

    public BasePageHeaderBlock(WebDriver driver) {
        super(driver);
    }

    public BasePageHeaderBlock(WebDriver driver, boolean consumerPortal) {
        super(driver);
        this.consumerPortal = consumerPortal;
    }

    @Override
    public boolean isAvailable() {
        List<Boolean> availabilityResult = Arrays.asList(
                topNavigationBarElement().isAvailable(),
                xylemeLogoInTopNavBar().isAvailable(),
                userProfileLink().isAvailable(),
                navigationMenuUnderTopNavBar().isAvailable());

        if (!consumerPortal) {
            new ArrayList<>(availabilityResult).add(moduleSelectorDropDown().isAvailable());
        }
        return !availabilityResult.contains(false);
    }

    private Element topNavigationBarElement() {
        return new Element(driver, topNavigationBarBy);
    }

    private Image xylemeLogoInTopNavBar() {
        return new Image(driver, By.xpath(stringValueOfTopNavigationBarXPATHLocator + "//img[@id='navbar-logo']"));
    }

    private Element moduleSelectorDropDown() {
        return new Element(driver, By.xpath(stringValueOfTopNavigationBarXPATHLocator +
                "//a[@class='navbar-brand']/parent::div//a[contains(@class, 'dropdown')]"));
    }

    private Link userProfileLink() {
        return new Link(driver, By.xpath(stringValueOfTopNavigationBarXPATHLocator +
                "//ul[contains(@class, 'navbar-right')]//a[contains(@class, 'dropdown')]"));
    }

    private LabelText fullUserNameLabelInPageHeader() {
        return new LabelText(driver, By.xpath(userProfileLink().getXpathLocatorValue() +
                "/div[contains(@class, 'user-name')]"));
    }

    private Element navigationMenuUnderTopNavBar() {
        return new Element(driver, navigationMenuUnderTopNavBarBy);
    }

    protected Link dropDownLinkInNavMenu(String linkTitle) {
        return new Link(driver, By.xpath(stringValueOfNavigationMenuUnderTopNavBarXPATHLocator +
                "//a[contains(@class, 'dropdown-toggle') and contains(text(), '" + linkTitle + "')]"));
    }

    /**
     * Gets full name of the logged user displayed in the page header.
     *
     * @return {@code String}
     */
    public String getNameOfUserInPageHeader() {
        return fullUserNameLabelInPageHeader().waitUntilAvailable().getText();
    }

    /**
     * Clicks module selector link in the page header (next to Xyleme logo) which opens drop-down menu and selects
     * specified menu option.
     *
     * @param moduleName - Specifies module option ('Portal', 'Analytics' or 'Test Builder' (if available))
     */
    protected void selectModule(String moduleName) {

        if (!moduleSelectorDropDown().isAvailable()) {
            driver.navigate().refresh();
        }
        moduleSelectorDropDown().waitUntilAvailable().click();
        Element openedDropDownMenu = new Element(driver, By.xpath(moduleSelectorDropDown().getXpathLocatorValue() +
                "/parent::div//ul[@class='dropdown-menu']")).waitUntilAvailable();
        Link linkToClickInOpenedMenu = new Link(driver, By.xpath(openedDropDownMenu.getXpathLocatorValue() +
                "/li[not (contains(@class, 'hide'))]/a[text()='" + moduleName + "']"));
        linkToClickInOpenedMenu.waitUntilAvailable().click();
    }

    /**
     * Opens specified drop-down and selects specified drop-down option.
     *
     * @param dropDownLink   - Specifies link which opens menu of the drop-down
     * @param dropDownOption - Specifies a drop-down option intended to be selected
     */
    private void openDropDownAndSelectDropDownMenuOption(Link dropDownLink, String dropDownOption) {
        dropDownLink.waitUntilAvailable().click();
        By openedDropDownMenuBy = By.xpath(dropDownLink.getXpathLocatorValue() + "//following-sibling::ul[@class='dropdown-menu']");
        Element openedDropDownMenu = new Element(driver, openedDropDownMenuBy).waitUntilAvailable();
        List<WebElement> dropDownOptions = getElementDynamically(openedDropDownMenuBy).findElements(
                By.xpath(".//li[not (contains(@class, 'hide'))]/a"));

        if (dropDownOptions.size() > 0) {

            for (WebElement option : dropDownOptions) {

                if (option.getText().equals(dropDownOption)) {
                    option.click();
                    waitUntil(!openedDropDownMenu.isAvailable());
                    break;
                }
            }
        } else {
            throw new RuntimeException("Drop-down menu doesn't contain any options! Option '" + dropDownOption +
                    "' cannot be selected.");
        }
    }

    /**
     * Opens specified drop-down in navigation menu under top navigation bar, clicks specified drop-down option and
     * returns instance of a respective page.
     *
     * @param dropDownName   - Specifies name of the drop-down intended to be expanded
     * @param dropDownOption - Specified drop-down option intended to be selected
     * @param tClass         - Specifies the page instance of which is expected to be returned after clicking specified
     *                       drop-down option
     * @return {@code T extends BasePageHeaderBlock}
     */
    protected <T extends BasePageHeaderBlock> T selectOptionOfNavMenuDropDown(String dropDownName, String dropDownOption,
                                                                              Class<T> tClass) {
        openDropDownAndSelectDropDownMenuOption(dropDownLinkInNavMenu(dropDownName), dropDownOption);
        return constructClassInstance(tClass);
    }

    /**
     * Selects specified option of User Profile drop-down.
     *
     * @param dropDownOption - Specifies the drop-down option intended to be selected
     */
    protected void selectOptionOfUserProfileDropDown(String dropDownOption) {
        openDropDownAndSelectDropDownMenuOption(userProfileLink(), dropDownOption);
    }

    /**
     * Clicks 'Sign Out' option in User Profile drop-down.
     */
    public CDSLogoutPage signOut() {
        selectOptionOfUserProfileDropDown("Sign Out");
        return new CDSLogoutPage(driver);
    }
}