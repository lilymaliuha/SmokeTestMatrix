package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdssearchpage.leftsidepanels;

import com.xyleme.bravais.web.WebPage;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.Link;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdssearchpage.CDSSearchPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.xyleme.bravais.BaseTest.staticSleep;

/**
 * Implementation of Base Left Side Panel available on CDS Search page (the panels which inherit from the current base
 * panel: 'Resource Type', 'Format', 'Language', 'Custom Attributes').
 */
public abstract class BaseLeftSidePanelOnSearchPage extends WebPage<BaseLeftSidePanelOnSearchPage> {
    Element panelBodyElement;

    BaseLeftSidePanelOnSearchPage(WebDriver driver, By panelBodyLocator) {
        super(driver);
        panelBodyElement = new Element(driver, panelBodyLocator);
    }

    @Override
    public boolean isAvailable() {
        return panelBodyElement.isAvailable();
    }

    @Override
    public BaseLeftSidePanelOnSearchPage load() {
        return this;
    }

    private Link panelHeader() {
        return new Link(driver, By.xpath(panelBodyElement.getXpathLocatorValue() +
                "//li[contains(@class, 'panel-header')]//td[contains(@class, 'facet-name')]"));
    }

    private Element collapseExpandElement() {
        return new Element(driver, By.xpath(panelBodyElement.getXpathLocatorValue() +
                "//td[contains(@class, 'text-right')]"));
    }

    /**
     * Gets list of web elements which represent panel list items.
     *
     * @return {@code List<WebElement>}
     */
    private List<WebElement> getListOfPanelListItemElements() {
        return driver.findElements(
                By.xpath(panelBodyElement.getXpathLocatorValue() + "//li[contains(@class, 'panel-item')]"));
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
     * Gets web element which represents specified panel list item.
     *
     * @param itemName - Specifies name of the list item (word next to a respective checkbox)
     * @return {@code WebElement}
     */
    WebElement getPanelListItemElement(String itemName) {
        WebElement elementToReturn = null;
        for (WebElement listItem : getListOfPanelListItemElements()) {
            if (listItem.getText().equals(itemName)) {
                elementToReturn = listItem;
                break;
            }
        }
        if (elementToReturn != null) {
            return elementToReturn;
        } else {
            throw new RuntimeException("Panel doesn't contain item '" + itemName + "'");
        }
    }

    /**
     * Gets web element which represents name element (word next to a checkbox) of a specific panel list item.
     *
     * @param itemIndex - Specifies index of the panel list item
     * @return {@code WebElement}
     */
    private WebElement getPanelListItemNameElement(int itemIndex) {
        return getPanelListItemElement(itemIndex).findElement(By.xpath("./span[contains(@class, 'facet-value-name')]"));
    }

    /**
     * Gets web element which represents checkbox of a specified panel list item.
     *
     * @param itemIndex - Specifies index of the panel list item
     * @return {@code WebElement}
     */
    private WebElement getPanelListItemCheckboxElement(int itemIndex) {
        return getPanelListItemElement(itemIndex).findElement(By.xpath("./input"));
    }

    /**
     * Gets web element which represents checkbox of a specified panel list item.
     *
     * @param itemName - Specifies name (word next to the checkbox) of the panel list item
     * @return {@code WebElement}
     */
    private WebElement getPanelListItemCheckboxElement(String itemName) {
        return getPanelListItemElement(itemName).findElement(By.xpath("./input"));
    }

    /**
     * Checks if the panel is collapsed.
     *
     * @return {@code boolean}
     */
    private boolean isPanelCollapsed() {
        return driver.findElement(By.xpath(collapseExpandElement().getXpathLocatorValue() +
                "//ancestor::div/ul[contains(@class, 'collapsible')][2]")).getAttribute("class").contains("hide");
    }

    /**
     * Checks whether a panel is expanded, of so - collapses it.
     *
     * @return {@code CDSSearchPage}
     */
    public CDSSearchPage collapsePanel() {
        if (!isPanelCollapsed()) {
            collapseExpandElement().waitUntilAvailable().click();
            waitUntil(isPanelCollapsed());
        }
        return new CDSSearchPage(driver);
    }

    /**
     * Checks whether a panel is collapsed, of so - expands it.
     *
     * @param tClass - Specifies the panel class instance of which is intended to be returned after expanding the panel
     * @return {@code T extends BaseLeftSidePanelOnSearchPage}
     */
    <T extends BaseLeftSidePanelOnSearchPage> T expandPanel(Class<T> tClass) {
        if (isPanelCollapsed()) {
            collapseExpandElement().waitUntilAvailable().click();
            waitUntil(!isPanelCollapsed());
        }
        return constructInstanceOfPanelClass(tClass, By.xpath(panelBodyElement.getXpathLocatorValue()));
    }

    /**
     * Checks if the specified checkbox is checked.
     *
     * @param itemIndex - Specifies index of the panel item checkbox of which is intended to be verified
     * @return {@code boolean}
     */
    private boolean isPanelListCheckboxIsChecked(int itemIndex) {
        return getPanelListItemCheckboxElement(itemIndex).isSelected();
    }

    /**
     * Checks if the specified checkbox is checked.
     *
     * @param itemName - Specifies name of the panel item checkbox next to which is intended to be verified
     * @return {@code boolean}
     */
    private boolean isPanelListCheckboxIsChecked(String itemName) {
        return getPanelListItemCheckboxElement(itemName).isSelected();
    }

    /**
     * Checks checkbox of the specified panel list item.
     *
     * @param itemIndex - Specifies index of the panel item checkbox of which is intended to be selected
     * @return {@code CDSSearchPage}
     */
    public CDSSearchPage selectPanelItemCheckbox(int itemIndex) {
        if (!isPanelListCheckboxIsChecked(itemIndex)) {
            getPanelListItemCheckboxElement(itemIndex).click();
            waitUntil(isPanelListCheckboxIsChecked(itemIndex));
        } else {
            System.out.println("Checkbox of the panel item with index '" + itemIndex + "' is already checked.");
        }
        return new CDSSearchPage(driver);
    }

    /**
     * Checks checkbox of the specified panel list item.
     *
     * @param itemName - Specifies name of the panel item checkbox next to which is intended to be selected
     * @return {@code CDSSearchPage}
     */
    public CDSSearchPage selectPanelItemCheckbox(String itemName) {
        if (!isPanelListCheckboxIsChecked(itemName)) {
            getPanelListItemCheckboxElement(itemName).click();
            waitUntil(isPanelListCheckboxIsChecked(itemName));
            staticSleep(2); // Time for left-side panels re-rendering after selecting the item (search criteria)
        } else {
            System.out.println("Checkbox of the panel item with index '" + itemName + "' is already checked.");
        }
        return new CDSSearchPage(driver);
    }

    /**
     * Unchecks checkbox of the specified panel list item.
     *
     * @param itemName - Specifies name of the panel item checkbox next to which is intended to be unselected
     * @return {@code CDSSearchPage}
     */
    public CDSSearchPage unselectPanelItemCheckbox(String itemName) {
        if (isPanelListCheckboxIsChecked(itemName)) {
            getPanelListItemCheckboxElement(itemName).click();
            waitUntil(!isPanelListCheckboxIsChecked(itemName));
        } else {
            System.out.println("Checkbox of the panel item with index '" + itemName + "' is already unchecked.");
        }
        return new CDSSearchPage(driver);
    }

    /**
     * Constructs instance of a specified left side panel on Search page class.
     *
     * @param tClass - Specifies panel class instance of which is expected to be constructed
     * @return {@code T extends BaseLeftSidePanelOnSearchPage}
     */
    private <T extends BaseLeftSidePanelOnSearchPage> T constructInstanceOfPanelClass(Class<T> tClass, By panelBodyLocator) {
        try {
            return tClass.getDeclaredConstructor(WebDriver.class, By.class).newInstance(driver, panelBodyLocator);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot construct " + tClass.getName()
                    + "\n <<<!>>> Check if all requirements of isAvailable() method of respective class are met! <<<!>>>");
        }
    }
}