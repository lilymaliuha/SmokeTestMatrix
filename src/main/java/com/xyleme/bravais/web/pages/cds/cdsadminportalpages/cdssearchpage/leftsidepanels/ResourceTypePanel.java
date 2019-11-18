package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdssearchpage.leftsidepanels;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Resource Type panel on Search page.
 */
public class ResourceTypePanel extends BaseLeftSidePanelOnSearchPage {

    public ResourceTypePanel(WebDriver driver, By panelBodyLocator) {
        super(driver, panelBodyLocator);
    }

    /**
     * Expands the panel.
     *
     * @return {@code ResourceTypePanel}
     */
    public ResourceTypePanel expandPanel() {
        return expandPanel(ResourceTypePanel.class);
    }
}