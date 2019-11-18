package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdshomepage.leftsidepanels;

import com.xyleme.bravais.web.WebPage;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.Link;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.CDSDocumentDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdshomepage.CDSHomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Implementation of Base Left Side Panel available on CDS Home page (the panels which inherit from the current base
 * panel: 'Latest Updates', 'Recent Documents', 'Favorites').
 */
public abstract class BaseLeftSidePanelOnHomePage extends WebPage<BaseLeftSidePanelOnHomePage> {
    private Element panelBodyElement;

    BaseLeftSidePanelOnHomePage(WebDriver driver, By panelBodyLocator) {
        super(driver);
        panelBodyElement = new Element(driver, panelBodyLocator);
    }

    @Override
    public boolean isAvailable() {
        return panelBodyElement.isAvailable();
    }

    @Override
    public BaseLeftSidePanelOnHomePage load() {
        return this;
    }

    private Link panelHeaderLink() {
        return new Link(driver, By.xpath(panelBodyElement.getXpathLocatorValue() +
                "//li[contains(@class, 'panel-header')]//a[contains(@class, 'text-left')]"));
    }

    private Link hideShowLink() {
        return new Link(driver, By.xpath(panelBodyElement.getXpathLocatorValue() + "//a[@class='text-right']"));
    }

    /**
     * Gets list of web elements which represent panel list items.
     *
     * @return {@code List<WebElement>}
     */
    private List<WebElement> getListOfPanelListItemElements() {
        return driver.findElements(
                By.xpath(panelBodyElement.getXpathLocatorValue() + "//li[contains(@class, 'panel-item')]/a"));
    }

    /**
     * Gets web element which represents specified panel list item.
     *
     * @param itemIndex - Specifies index of the item in the panel list
     * @return {@code WebElement}
     */
    private WebElement getPanelListItemElement(int itemIndex) {
        return getListOfPanelListItemElements().get(itemIndex);
    }

    /**
     * Gets web element which represents name element (document name) of a specific panel list item.
     *
     * @param itemIndex - Specifies index of the panel list item
     * @return {@code WebElement}
     */
    private WebElement getPanelListItemNameElement(int itemIndex) {
        return getPanelListItemElement(itemIndex).findElement(By.xpath("./span[not (contains(@class, 'small-text'))]"));
    }

    /**
     * Gets web element which represents time stamp element of specific list item.
     *
     * @param itemIndex - Specifies index of the panel list item
     * @return {@code WebElement}
     */
    private WebElement getPanelListItemTimeStampElement(int itemIndex) {
        return getPanelListItemElement(itemIndex).findElement(By.xpath("./span[contains(@class, 'small-text')]"));
    }

    /**
     * Checks if panel header link is available.
     *
     * @return {@code boolean}
     */
    public boolean isPanelHeaderLinkAvailable() {
        return panelHeaderLink().isAvailable();
    }

    /**
     * Checks if 'hide'/'show' is available.
     *
     * @return {@code boolean}
     */
    public boolean isCollapseExpandLinkAvailable() {
        return hideShowLink().isAvailable();
    }

    /**
     * Gets current name of 'hide'/'show' link of the panel.
     *
     * @return {@code String}
     */
    public String getNameOfHideShowLink() {
        return hideShowLink().waitUntilAvailable().getText();
    }

    /**
     * Checks whether the panel is collapsed.
     *
     * @return {@code boolean}
     */
    public boolean isPanelCollapsed() {
        return getNameOfHideShowLink().equals("show") && getListOfPanelListItemElements().size() == 0;
    }

    /**
     * Checks whether a panel is expanded, of so - collapses it.
     *
     * @return {@code CDSHomePage}
     */
    public CDSHomePage collapsePanel() {
        if (!isPanelCollapsed()) {
            hideShowLink().waitUntilAvailable().click();
            waitUntil(isPanelCollapsed());
        }
        return new CDSHomePage(driver);
    }

    /**
     * Checks whether a panel is collapsed, of so - expands it.
     *
     * @param tClass - Specifies the panel class instance of which is intended to be returned after expanding the panel
     * @return {@code T extends BaseLeftSidePanelOnHomePage}
     */
    <T extends BaseLeftSidePanelOnHomePage> T expandPanel(Class<T> tClass) {
        if (isPanelCollapsed()) {
            hideShowLink().waitUntilAvailable().click();
            waitUntil(!isPanelCollapsed());
        }
        return constructInstanceOfPanelClass(tClass, By.xpath(panelBodyElement.getXpathLocatorValue()));
    }

    /**
     * Gets number of panel's list items.
     *
     * @return {@code int}
     */
    public int getNumberOfPanelListItems() {
        return getListOfPanelListItemElements().size();
    }

    /**
     * Gets title of specified panel list item
     *
     * @param itemIndex - Specifies index of the list item
     * @return {@code String}
     */
    public String getPanelListItemTitle(int itemIndex) {
        return getPanelListItemNameElement(itemIndex).getText();
    }

    /**
     * Gets time stamp of specified list item.
     *
     * @param itemIndex - Specifies index of the list item
     * @return {@code String}
     */
    public String getPanelListItemTimeStamp(int itemIndex) {
        return getPanelListItemTimeStampElement(itemIndex).getText();
    }

    /**
     * Clicks on specified panel list item.
     *
     * @param itemIndex - Specifies index of the list item
     * @return {@code CDSDocumentDetailsPage}
     */
    public CDSDocumentDetailsPage clickOnPanelListItem(int itemIndex) {
        getPanelListItemElement(itemIndex).click();
        return new CDSDocumentDetailsPage(driver);
    }

    /**
     * Clicks on the panel header link.
     */
    void clickOnPanelHeaderLink() {
        panelHeaderLink().click();
    }

    /**
     * Constructs instance of a specified left side panel on Home page class.
     *
     * @param tClass - Specifies panel class instance of which is expected to be constructed
     * @return {@code T extends BaseLeftSidePanelOnHomePage}
     */
    private <T extends BaseLeftSidePanelOnHomePage> T constructInstanceOfPanelClass(Class<T> tClass, By panelBodyLocator) {
        try {
            return tClass.getDeclaredConstructor(WebDriver.class, By.class).newInstance(driver, panelBodyLocator);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot construct " + tClass.getName()
                    + "\n <<<!>>> Check if all requirements of isAvailable() method of respective class are met! <<<!>>>");
        }
    }
}