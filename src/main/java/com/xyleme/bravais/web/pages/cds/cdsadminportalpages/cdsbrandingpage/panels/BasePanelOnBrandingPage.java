package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsbrandingpage.panels;

import com.xyleme.bravais.web.WebPage;
import com.xyleme.bravais.web.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of a base panel available on Branding page.
 */
public abstract class BasePanelOnBrandingPage extends WebPage<BasePanelOnBrandingPage> {
    private Element panelBodyElement;

    BasePanelOnBrandingPage(WebDriver driver, Element panelBodyElement) {
        super(driver);
        this.panelBodyElement = panelBodyElement;
    }

    @Override
    public BasePanelOnBrandingPage load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return panelHeader().isAvailable() &&
                panelBody().isAvailable();
    }

    private Element panelHeader() {
        return new Element(driver, By.xpath(panelBodyElement.getXpathLocatorValue() + "/div[@class='panel-heading']"));
    }

    Element panelBody() {
        return new Element(driver, By.xpath(panelBodyElement.getXpathLocatorValue() +
                "/div[starts-with(@class, 'panel-body')]"));
    }

    /**
     * Gets text of panel header.
     *
     * @return {@code String}
     */
    public String getPanleHeader() {
        return panelHeader().getText();
    }
}