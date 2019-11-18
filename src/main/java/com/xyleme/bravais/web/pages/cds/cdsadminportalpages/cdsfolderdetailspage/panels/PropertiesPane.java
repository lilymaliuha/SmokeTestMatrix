package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsfolderdetailspage.panels;

import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSPanelOnDetailsPage;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Properties panel on Folder Details page.
 */
public class PropertiesPane extends BaseCDSPanelOnDetailsPage {

    public PropertiesPane(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAvailable() {
        return false; // ToDo.
    }

    @Override
    public PropertiesPane load() {
        return this;
    }
}