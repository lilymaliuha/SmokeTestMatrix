package com.xyleme.bravais.web.pages.cds.cdsadminportalpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a page which contains panels at the left side of a page.
 */
public abstract class BaseCDSPageWithLeftSidePanels extends BaseCDSPageHeader {
    private String panelHeaderTag;
    private By rightSidePanelBodyBy = By.xpath("//ul[contains(@class, 'nav-stacked') and not (contains(@class, 'hide'))]");

    public BaseCDSPageWithLeftSidePanels(WebDriver driver, boolean headerOfLeftSidePanelIsLink) {
        super(driver);
        definePanelHeaderTag(headerOfLeftSidePanelIsLink);
    }

    /**
     * Defines tag of a panel header.
     *
     * @param headerOfLeftSidePanelIsLink - defines whether the header is represented by a link
     */
    private void definePanelHeaderTag(boolean headerOfLeftSidePanelIsLink) {
        panelHeaderTag =  headerOfLeftSidePanelIsLink ? "//a" : "//td";
    }

    protected By leftSidePanelBy(String panelTitle) {
        return By.xpath(panelHeaderTag + "[text()='" + panelTitle + "']/ancestor::" + getXpathLocatorValue(rightSidePanelBodyBy)
                .replaceAll("//", ""));
    }

    /**
     * Gets list of titles of the panels located at the right side of the page.
     *
     * @return {@code List<String>}
     */
    private List<String> getTitlesOfRightSidePanels() {
        List<String> titles = new ArrayList<>();
        List<WebElement> titleElements = getElementsDynamically(By.xpath(getXpathLocatorValue(rightSidePanelBodyBy) +
                panelHeaderTag + "[contains(@class, 'text-left')]"));
        for (WebElement titleElement : titleElements) {
            titles.add(titleElement.getText());
        }
        return titles;
    }

    /**
     * Gets index of right side panel with specified title.
     *
     * @param panelTitle - Specifies panel title
     * @return {@code int}
     */
    public int getIndexOfPanelWithTitle(String panelTitle) {
        return getTitlesOfRightSidePanels().indexOf(panelTitle);
    }
}