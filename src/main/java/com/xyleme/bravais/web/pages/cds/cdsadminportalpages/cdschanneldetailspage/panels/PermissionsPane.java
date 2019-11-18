package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdschanneldetailspage.panels;

import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSPanelOnDetailsPage;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Permissions pane on CDS Channel Details page.
 */
public class PermissionsPane extends BaseCDSPanelOnDetailsPage { // ToDo: Work on the pane if needed.

    public PermissionsPane(WebDriver driver) {
        super(driver);
    }

    @Override
    public PermissionsPane load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }
}