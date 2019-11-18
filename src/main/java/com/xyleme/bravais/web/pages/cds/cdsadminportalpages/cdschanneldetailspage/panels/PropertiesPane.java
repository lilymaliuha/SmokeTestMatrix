package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdschanneldetailspage.panels;

import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSPanelOnDetailsPage;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Properties pane on CDS Channel Details page.
 */
public class PropertiesPane extends BaseCDSPanelOnDetailsPage { // ToDo: Work on the pane if needed.

    public PropertiesPane(WebDriver driver) {
        super(driver);
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