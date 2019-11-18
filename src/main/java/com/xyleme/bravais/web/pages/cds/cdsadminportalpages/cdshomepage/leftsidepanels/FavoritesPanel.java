package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdshomepage.leftsidepanels;

import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSFavoritesPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of the 'Favorites' panel available on the Home page.
 */
public class FavoritesPanel extends BaseLeftSidePanelOnHomePage {

    public FavoritesPanel(WebDriver driver, By panelBodyLocator) {
        super(driver, panelBodyLocator);
    }

    /**
     * Clicks panel header link and returns Favorites page.
     *
     * @return {@code CDSFavoritesPage}
     */
    public CDSFavoritesPage goToFavoritesPage() {
        clickOnPanelHeaderLink();
        return new CDSFavoritesPage(driver);
    }

    /**
     * Expands the panel.
     *
     * @return {@code FavoritesPanel}
     */
    public FavoritesPanel expandPanel() {
        return super.expandPanel(FavoritesPanel.class);
    }
}