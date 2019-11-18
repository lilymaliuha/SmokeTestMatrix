package com.xyleme.bravais.web.pages.cds;

import com.xyleme.bravais.web.elements.Link;
import com.xyleme.bravais.web.pages.BasePageHeaderBlock;
import com.xyleme.bravais.web.pages.cds.cdsconsumerportalpages.CDSConsumerPortalDocumentsPage;
import com.xyleme.bravais.web.pages.cds.cdsconsumerportalpages.homepage.CDSConsumerPortalHomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class BaseCDSConsumerPortalPageHeader extends BasePageHeaderBlock {
    private By navigationMenuUnderTopNavBarBy = By.xpath("//div[@id='top-navbar-menu']");

    public BaseCDSConsumerPortalPageHeader(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable();
    }

    @Override
    public BaseCDSConsumerPortalPageHeader load() {
        return this;
    }

    private Link linkInNavMenu(String linkTitle) {
        return new Link(driver, By.xpath(getXpathLocatorValue(navigationMenuUnderTopNavBarBy) + "//a[text()='" +
                linkTitle + "']"));
    }

    private Link homeLinkInNavMenu() {
        return linkInNavMenu("Home");
    }

    private Link documentsLinkInNavMenu() {
        return linkInNavMenu("Documents");
    }

    /**
     * Opens Home page.
     *
     * @return {@code CDSConsumerPortalHomePage}
     */
    public CDSConsumerPortalHomePage openHomePage() {
        homeLinkInNavMenu().waitUntilAvailable().click();
        return new CDSConsumerPortalHomePage(driver);
    }

    /**
     * Opens Documents page.
     *
     * @return {@code CDSConsumerPortalDocumentsPage}
     */
    public CDSConsumerPortalDocumentsPage openDocumentsPage() {
        documentsLinkInNavMenu().waitUntilAvailable().click();
        return new CDSConsumerPortalDocumentsPage(driver);
    }
}