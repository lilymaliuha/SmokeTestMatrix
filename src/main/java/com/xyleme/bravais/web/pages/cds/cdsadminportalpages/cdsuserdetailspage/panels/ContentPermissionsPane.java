package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsuserdetailspage.panels;

import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSPanelOnDetailsPage;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Content Permissions pane on User Details page.
 */
public class ContentPermissionsPane extends BaseCDSPanelOnDetailsPage {

    public ContentPermissionsPane(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public ContentPermissionsPane load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }
}