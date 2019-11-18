package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdshomepage.leftsidepanels;

import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSRecentDocumentsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of the 'Recent Documents' panel available on the Home page.
 */
public class RecentDocumentsPanel extends BaseLeftSidePanelOnHomePage {

    public RecentDocumentsPanel(WebDriver driver, By panelBodyLocator) {
        super(driver, panelBodyLocator);
    }

    /**
     * Clicks panel header link and returns Recent Documents page.
     *
     * @return {@code CDSRecentDocumentsPage}
     */
    public CDSRecentDocumentsPage goToRecentDocumentsPage() {
        clickOnPanelHeaderLink();
        return new CDSRecentDocumentsPage(driver);
    }

    /**
     * Expands the panel.
     *
     * @return {@code RecentDocumentsPanel}
     */
    public RecentDocumentsPanel expandPanel() {
        return super.expandPanel(RecentDocumentsPanel.class);
    }
}