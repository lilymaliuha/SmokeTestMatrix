package com.xyleme.bravais.web.pages.cds.cdsadminportalpages;

import com.xyleme.bravais.web.elements.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import static com.xyleme.bravais.BaseTest.staticSleep;

/**
 * Implementation of a base CDS item Details page.
 */
public abstract class BaseCDSItemDetailsPage extends BaseCDSPageHeader {

    public BaseCDSItemDetailsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                breadcrumbBlock().isAvailable() &&
                itemName().isAvailable() &&
                itemDescription().isAvailable() &&
                tabsPanel().isAvailable();
    }

    private Element breadcrumbBlock() {
        return new Element(driver, By.xpath("//ol[contains(@class, 'breadcrumb')]"));
    }

    private Link pathLinkInBreadcrumbBlock(String folderInPath) {
        return new Link(driver, By.xpath("//ol[contains(@class, 'breadcrumb')]/li/a[normalize-space()='" + folderInPath +
                "']"));
    }

    protected Button buttonUnderBreadcrumbsBlock(String buttonName) {
        return new Button(driver, By.xpath(
                "//div[contains(@class, 'details-header')]//button[normalize-space()='" + buttonName + "']"));
    }

    private LabelText itemName() {
        return new LabelText(driver, By.xpath("//h2[contains(@class, 'name')]"));
    }

    protected LabelText itemDescription() {
        return new LabelText(driver, By.xpath("//h5[contains(@class, 'description')]"));
    }

    private Element tabsPanel() {
        return new Element(driver, By.xpath("//ul[contains(@class, 'tabs-panel')]"));
    }

    private Element tab(String tabName) {
        return new Element(driver, By.xpath(tabsPanel().getXpathLocatorValue() +
                "//li[not (contains(@class, 'hide'))]/a[text()='" + tabName + "']"));
    }

    /**
     * Edits name of an item Details page of which is opened.
     *
     * @param newName - Specifies the new name
     */
    protected void editName(String newName) {
        editName(itemName(), newName);
    }

    /**
     * Edits name of specified item Details page of which is opened.
     *
     * @param itemNameLabel - Specifies label element of the item name of which is intended to be edited
     * @param newName       - Specifies the new name
     */
    protected void editName(LabelText itemNameLabel, String newName) {
        itemNameLabel.hoverOverAndClick();
        waitUntil(itemNameLabel.getAttribute("class").contains("editing"));
        TextInput nameInput = new TextInput(driver, By.xpath(itemNameLabel.getXpathLocatorValue() + "//form/input"))
                .waitUntilAvailable();
        nameInput.clear();
        waitForAngularJSProcessing();
        nameInput.senKeysCharByChar(newName);
        nameInput.sendKeys(Keys.ENTER);
        staticSleep(2); // Needed for applying the update.
    }

    /**
     * Gets name of the item the details page is opened for.
     *
     * @return {@code String}
     */
    protected String getName() {
        return itemName().waitUntilAvailable().getText();
    }

    /**
     * Checks if the specified tab is available on the page.
     *
     * @param tabName - Specifies name of the tab intended to be checked
     * @return {@code boolean}
     */
    public boolean isTabAvailable(String tabName) {
        return tab(tabName).isAvailable();
    }

    /**
     * Checks if specified tab is active.
     *
     * @param tabName - Specifies name of the tab intended to be checked
     * @return {@code boolean}
     */
    private boolean isTabActive(String tabName) {
        return new Element(driver, By.xpath(tab(tabName).getXpathLocatorValue() + "/parent::li")).getAttribute(
                "class").contains("active");
    }

    /**
     * Checks if specified tab is active, if not - selects it, waits until it becomes active and returns respective pane.
     *
     * @param tabName       - Specifies name of the tab intended to be selected
     * @param panelToReturn - Specifies class of a respective pane expected to be returned after selecting specified tab
     * @return {@code T extends BaseCDSPanelOnDetailsPage}
     */
    protected <T extends BaseCDSPanelOnDetailsPage> T selectTabAndGetPane(String tabName, Class<T> panelToReturn) {

        if (isTabAvailable(tabName)) {

            if (!isTabActive(tabName)) {
                System.out.println("lilia");
                staticSleep(10);
                tab(tabName).waitUntilAvailable().click();
                waitUntil(isTabActive(tabName));
            } else {
                System.out.println("'" + tabName + "' tab is already selected.");
            }
        } else {
            throw new RuntimeException("Tab '" + tabName + "' is not available on the page.");
        }
        return constructClassInstance(panelToReturn);
    }

    /**
     * Clicks the specified destination link in the breadcrumbs block and returns instance of the expected page.
     *
     * @param destinationLink  - Specifies destination link in the breadcrumbs block intended to be clicked
     * @param pageToBeReturned - Specifies the page expected to be returned after the navigation
     * @return {@code <T extends BaseCDSPageHeader>}
     */
    public <T extends BaseCDSPageHeader> T navigateFromPageUsingLinkInBreadcrumbsBlock(String destinationLink,
                                                                                       Class<T> pageToBeReturned) {
        pathLinkInBreadcrumbBlock(destinationLink).waitUntilAvailable().click();
        return constructClassInstance(pageToBeReturned);
    }
}