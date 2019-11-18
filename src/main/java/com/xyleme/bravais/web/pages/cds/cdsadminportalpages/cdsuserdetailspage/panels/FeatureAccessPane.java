package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsuserdetailspage.panels;

import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSPanelOnDetailsPage;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Feature Access pane on User Details page.
 */
public class FeatureAccessPane extends BaseCDSPanelOnDetailsPage {

    public FeatureAccessPane(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public FeatureAccessPane load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }
}