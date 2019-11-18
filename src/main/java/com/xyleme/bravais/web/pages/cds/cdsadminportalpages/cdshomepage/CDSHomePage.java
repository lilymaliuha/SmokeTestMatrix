package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdshomepage;

import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSPageWithLeftSidePanels;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdshomepage.channelsblock.ChannelsBlock;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdshomepage.leftsidepanels.FavoritesPanel;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdshomepage.leftsidepanels.LatestUpdatesPanel;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdshomepage.leftsidepanels.RecentDocumentsPanel;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of CDS Home page.
 */
public class CDSHomePage extends BaseCDSPageWithLeftSidePanels {

    public CDSHomePage(WebDriver driver) {
        super(driver, true);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                getLatestUpdatesPanel().isAvailable() &&
                getRecentDocumentsPanel().isAvailable() &&
                getFavoritesPanel().isAvailable() &&
                getChannelsBlock().isAvailable() &&
                footerLabelWithBuildNumber().isAvailable();
    }

    @Override
    public CDSHomePage load() {
        return this;
    }

    /**
     * Gets 'Latest Updates' panel.
     *
     * @return {@code LatestUpdatesPanel}
     */
    public LatestUpdatesPanel getLatestUpdatesPanel() {
        return new LatestUpdatesPanel(driver, leftSidePanelBy("Latest Updates"));
    }

    /**
     * Gets 'Recent Documents' panel.
     *
     * @return {@code RecentDocumentsPanel}
     */
    public RecentDocumentsPanel getRecentDocumentsPanel() {
        return new RecentDocumentsPanel(driver, leftSidePanelBy("Recent Documents"));
    }

    /**
     * Gets 'Favorites' panel.
     *
     * @return {@code FavoritesPanel}
     */
    public FavoritesPanel getFavoritesPanel() {
        return new FavoritesPanel(driver, leftSidePanelBy("Favorites"));
    }

    /**
     * Gets Channels block on Home Page.
     *
     * @return {@code ChannelsBlock}
     */
    public ChannelsBlock getChannelsBlock() {
        return new ChannelsBlock(driver);
    }
}