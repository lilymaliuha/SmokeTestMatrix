package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsuserdetailspage.panels;

import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSPanelOnDetailsPage;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Group Membership pane on User Details page.
 */
public class GroupMembershipPane extends BaseCDSPanelOnDetailsPage {

    public GroupMembershipPane(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public GroupMembershipPane load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }
}