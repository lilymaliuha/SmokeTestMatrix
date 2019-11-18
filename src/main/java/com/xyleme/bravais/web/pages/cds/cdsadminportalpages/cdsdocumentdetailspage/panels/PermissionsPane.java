package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels;

import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSPanelOnDetailsPage;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Permissions pane on CDS Document Details page.
 */
public class PermissionsPane extends BaseCDSPanelOnDetailsPage {

    public PermissionsPane(WebDriver driver) {
        super(driver);
    }

    @Override
    public PermissionsPane load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return false;  // ToDo!
    }
}