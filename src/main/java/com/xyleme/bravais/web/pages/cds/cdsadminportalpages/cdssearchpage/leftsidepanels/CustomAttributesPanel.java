package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdssearchpage.leftsidepanels;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Custom Attributes panel on Search page.
 */
public class CustomAttributesPanel extends BaseLeftSidePanelOnSearchPage {

    public CustomAttributesPanel(WebDriver driver, By panelBodyLocator) {
        super(driver, panelBodyLocator);
    }

    /**
     * Expands the panel.
     *
     * @return {@code CustomAttributesPanel}
     */
    public CustomAttributesPanel expandPanel() {
        return expandPanel(CustomAttributesPanel.class);
    }
}