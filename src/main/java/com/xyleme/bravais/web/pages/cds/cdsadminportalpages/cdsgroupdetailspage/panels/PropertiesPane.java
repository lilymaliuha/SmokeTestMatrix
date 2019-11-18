package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsgroupdetailspage.panels;

import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSPanelOnDetailsPage;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Properties pane on Group Details page.
 */
public class PropertiesPane extends BaseCDSPanelOnDetailsPage {

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