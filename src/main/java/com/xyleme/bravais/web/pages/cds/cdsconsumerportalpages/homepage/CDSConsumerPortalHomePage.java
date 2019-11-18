package com.xyleme.bravais.web.pages.cds.cdsconsumerportalpages.homepage;

import com.xyleme.bravais.web.elements.Link;
import com.xyleme.bravais.web.pages.cds.BaseCDSConsumerPortalPageHeader;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdshomepage.CDSHomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Consumer Portal Home page.
 */
public class CDSConsumerPortalHomePage extends BaseCDSConsumerPortalPageHeader {

    public CDSConsumerPortalHomePage(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public CDSConsumerPortalHomePage load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                getChannelsBlock().isAvailable();
    }

    private Link adminPortalLinkInNavigationMenu() {
        return new Link(driver, By.xpath(stringValueOfNavigationMenuUnderTopNavBarXPATHLocator +
                "//a[text()='Admin Portal']"));
    }

    /**
     * Gets Channels block on Home Page.
     *
     * @return {@code ChannelsBlockOnCDSConsumerPortalHomePage}
     */
    private ChannelsBlockOnCDSConsumerPortalHomePage getChannelsBlock() {
        return new ChannelsBlockOnCDSConsumerPortalHomePage(driver);
    }

    /**
     * Goes to Admin Portal by clicking respective button in a navigation menu in the page header.
     *
     * @return {@code CDSHomePage}
     */
    public CDSHomePage goToAdminPortal() {
        adminPortalLinkInNavigationMenu().waitUntilAvailable().click();
        return new CDSHomePage(driver);
    }
}