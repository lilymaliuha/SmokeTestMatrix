package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdssearchpage.leftsidepanels;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Format panel on Search page.
 */
public class FormatPanel extends BaseLeftSidePanelOnSearchPage {

    public FormatPanel(WebDriver driver, By panelBodyLocator) {
        super(driver, panelBodyLocator);
    }

    /**
     * Expands the panel.
     *
     * @return {@code FormatPanel}
     */
    public FormatPanel expandPanel() {
        return expandPanel(FormatPanel.class);
    }
}