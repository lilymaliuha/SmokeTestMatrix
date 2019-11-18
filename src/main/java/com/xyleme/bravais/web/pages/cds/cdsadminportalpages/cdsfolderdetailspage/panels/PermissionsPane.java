package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsfolderdetailspage.panels;

import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSPanelOnDetailsPage;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Permissions panel on Folder Details page.
 */
public class PermissionsPane extends BaseCDSPanelOnDetailsPage {

    public PermissionsPane(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public PermissionsPane load() {
        return this;
    }
}