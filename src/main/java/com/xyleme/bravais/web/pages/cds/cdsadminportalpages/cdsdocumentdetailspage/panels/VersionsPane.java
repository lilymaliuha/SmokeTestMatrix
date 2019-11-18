package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels;

import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSPanelOnDetailsPage;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Versions pane on CDS Document Details page.
 */
public class VersionsPane extends BaseCDSPanelOnDetailsPage {

    public VersionsPane(WebDriver driver) {
        super(driver);
    }

    @Override
    public VersionsPane load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return false; // ToDo!
    }
}