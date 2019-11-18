package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdssearchpage.leftsidepanels;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Language panel on Search page.
 */
public class LanguagePanel extends BaseLeftSidePanelOnSearchPage {

    public LanguagePanel(WebDriver driver, By panelBodyLocator) {
        super(driver, panelBodyLocator);
    }

    /**
     * Expands the panel.
     *
     * @return {@code LanguagePanel}
     */
    public LanguagePanel expandPanel() {
        return expandPanel(LanguagePanel.class);
    }
}