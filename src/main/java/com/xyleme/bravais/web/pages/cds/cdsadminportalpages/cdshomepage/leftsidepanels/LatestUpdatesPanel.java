package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdshomepage.leftsidepanels;

import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSLatestUpdatesPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of the 'Latest Updates' panel available on the Home page.
 */
public class LatestUpdatesPanel extends BaseLeftSidePanelOnHomePage {

    public LatestUpdatesPanel(WebDriver driver, By panelBodyLocator) {
        super(driver, panelBodyLocator);
    }

    /**
     * Clicks panel header link and returns Latest Updates page.
     *
     * @return {@code CDSLatestUpdatesPage}
     */
    public CDSLatestUpdatesPage goToLatestUpdatesPage() {
        clickOnPanelHeaderLink();
        return new CDSLatestUpdatesPage(driver);
    }

    /**
     * Expands the panel.
     *
     * @return {@code LatestUpdatesPanel}
     */
    public LatestUpdatesPanel expandPanel() {
        return super.expandPanel(LatestUpdatesPanel.class);
    }
}