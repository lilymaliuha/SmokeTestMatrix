package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdssearchpage;

import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSPageWithLeftSidePanels;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdssearchpage.leftsidepanels.*;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdssearchpage.searchresultsblock.SearchResultsContainer;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of CDS Search page.
 */
public class CDSSearchPage extends BaseCDSPageWithLeftSidePanels {

    public CDSSearchPage(WebDriver driver) {
        super(driver, false);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                getResourceTypePanel().isAvailable() &&
                getFormatPanel().isAvailable() &&
                getLanguagePanel().isAvailable() &&
                footerLabelWithBuildNumber().isAvailable();
    }

    /**
     * Gets Resource Type left side panel.
     *
     * @return {@code ResourceTypePanel}
     */
    public ResourceTypePanel getResourceTypePanel() {
        return new ResourceTypePanel(driver, leftSidePanelBy("Resource Type"));
    }

    /**
     * Gets Format left side panel.
     *
     * @return {@code FormatPanel}
     */
    public FormatPanel getFormatPanel() {
        return new FormatPanel(driver, leftSidePanelBy("Format"));
    }

    /**
     * Gets Language left side panel.
     *
     * @return {@code LanguagePanel}
     */
    public LanguagePanel getLanguagePanel() {
        return new LanguagePanel(driver, leftSidePanelBy("Language"));
    }

    public ClassificationsPanel getClassificationsPanel() {
        return new ClassificationsPanel(driver, leftSidePanelBy("Classifications"));
    }

    /**
     * Gets custom attributes panel with specified name.
     *
     * @param panelName - Specifies the name of the panel
     * @return {@code CustomAttributesPanel}
     */
    public CustomAttributesPanel getCustomLeftSidePanel(String panelName) {
        return new CustomAttributesPanel(driver, leftSidePanelBy(panelName));
    }

    /**
     * Gets Search Results container.
     *
     * @return {@code SearchResultsContainer}
     */
    public SearchResultsContainer getSearchResultsContainer() {
        return new SearchResultsContainer(driver);
    }
}