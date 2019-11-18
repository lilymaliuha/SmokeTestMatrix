package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsuserdetailspage.panels;

import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSPanelOnDetailsPage;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Properties pane on User Details page.
 */
public class PropertiesPane extends BaseCDSPanelOnDetailsPage {

    public PropertiesPane(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public PropertiesPane load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }
}
