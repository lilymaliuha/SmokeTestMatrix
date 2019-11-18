package com.xyleme.bravais.web.pages.cds.cdsadminportalpages;

import com.xyleme.bravais.web.elements.*;
import com.xyleme.bravais.web.pages.BasePageHeaderBlock;
import com.xyleme.bravais.web.pages.analytics.AnalyticsHomePage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsbrandingpage.CDSBrandingPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsclassificationspage.CDSClassificationsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdshomepage.CDSHomePage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdspreferencespage.CDSPreferencesPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdssearchpage.CDSSearchPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdscustomattributespage.CDSCustomAttributesPage;
import com.xyleme.bravais.web.pages.cds.cdsconsumerportalpages.homepage.CDSConsumerPortalHomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Base CDS Page Header block.
 */
public abstract class BaseCDSPageHeader extends BasePageHeaderBlock {

    public BaseCDSPageHeader(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                searchInputField().isAvailable() &&
                searchButton().isAvailable() &&
                homeLinkInNavMenu().isAvailable() &&
                documentsLinkInNavMenu().isAvailable() &&
                channelsLinkInNavMenu().isAvailable();
    }

    @Override
    public BaseCDSPageHeader load() {
        return this;
    }

    private TextInput searchInputField() {
        return new TextInput(driver, By.xpath(stringValueOfTopNavigationBarXPATHLocator + "//input[@name='query']"));
    }

    private Button searchButton() {
        return new Button(driver, By.xpath(stringValueOfTopNavigationBarXPATHLocator + "//button[@id='button__search']"));
    }

    private Link linkInNavMenu(String linkTitle) {
        return new Link(driver, By.xpath(stringValueOfNavigationMenuUnderTopNavBarXPATHLocator + "//a[text()='" +
                linkTitle + "']"));
    }

    private Link homeLinkInNavMenu() {
        return linkInNavMenu("Home");
    }

    private Link documentsLinkInNavMenu() {
        return linkInNavMenu("Documents");
    }

    private Link channelsLinkInNavMenu() {
        return linkInNavMenu("Channels");
    }

    private Link usersLinkInNavMenu() {
        return dropDownLinkInNavMenu("Users");
    }

    private Link settingsLinkInNavMenu() {
        return dropDownLinkInNavMenu("Settings");
    }

    /**
     * Opens drop-down menu next to Xyleme logo and selects 'Analytics' menu option.
     *
     * @return {@code AnalyticsHomePage}
     */
    public AnalyticsHomePage goToAnalytics() {
        selectModule("Analytics");
        return new AnalyticsHomePage(driver);
    }

    /**
     * Opens drop-down menu next to Xyleme logo and selects 'Portal' menu option.
     *
     * @return {@code CDSConsumerPortalHomePage}
     */
    public CDSConsumerPortalHomePage goToConsumerPortal() {
        selectModule("Portal");
        return new CDSConsumerPortalHomePage(driver);
    }

    /**
     * Clicks Search button (button with magnifying glass icon).
     *
     * @return {@code CDSSearchPage}
     */
    public CDSSearchPage openSearchPage() {
        searchButton().waitUntilAvailable().click();
        return new CDSSearchPage(driver);
    }

    /**
     * Enters specified query (content) into the Search input field and clicks the Search button (button with magnifying
     * glass icon).
     *
     * @param contentToSearch - Specifies the content intended to be searched for
     * @return {@code CDSSearchPage}
     */
    public CDSSearchPage searchForContent(String contentToSearch) {
        searchInputField().sendKeys(contentToSearch);
        return openSearchPage();
    }

    /**
     * Opens Home page.
     *
     * @return {@code CDSHomePage}
     */
    public CDSHomePage openHomePage() {
        homeLinkInNavMenu().waitUntilAvailable().click();
        return new CDSHomePage(driver);
    }

    /**
     * Opens Documents page.
     *
     * @return {@code CDSDocumentsPage}
     */
    public CDSDocumentsPage openDocumentsPage() {
        documentsLinkInNavMenu().waitUntilAvailable().click();
        return new CDSDocumentsPage(driver);
    }

    /**
     * Opens Channels page.
     *
     * @return {@code CDSChannelsPage}
     */
    public CDSChannelsPage openChannelsPage() {
        channelsLinkInNavMenu().waitUntilAvailable().click();
        return new CDSChannelsPage(driver);
    }

    /**
     * Opens 'Users' page by selecting respective option in 'Users' drop-down.
     *
     * @return {@code CDSUsersPage}
     */
    public CDSUsersPage openUsersPage() {
        return selectOptionOfNavMenuDropDown("Users", "Users", CDSUsersPage.class);
    }

    /**
     * Opens 'Groups' page by selecting respective option in 'Users' drop-down.
     *
     * @return {@code CDSGroupsPage}
     */
    public CDSGroupsPage openGroupsPage() {
        return selectOptionOfNavMenuDropDown("Users", "Groups", CDSGroupsPage.class);
    }

    /**
     * Opens 'Settings' drop-down in navigation menu under top navigation bar, clicks specified drop-down option and
     * returns instance of a respective page.
     *
     * @param dropDownOption - Specified drop-down option intended to be selected
     * @param tClass         - Specifies the page instance of which is expected to be returned after clicking specified
     *                       drop-down option
     * @return {@code T extends BaseCDSPageHeader}
     */
    private <T extends BaseCDSPageHeader> T selectOptionOfSettingsDropDown(String dropDownOption, Class<T> tClass) {
        return selectOptionOfNavMenuDropDown("Settings", dropDownOption, tClass);
    }

    /**
     * Opens 'Preferences' page by selecting respective option in 'Settings' drop-down.
     *
     * @return {@code CDSPreferencesPage}
     */
    public CDSPreferencesPage openPreferencesPage() {
        return selectOptionOfSettingsDropDown("Preferences",  CDSPreferencesPage.class);
    }

    /**
     * Opens 'Classifications' page by selecting respective option in 'Settings' drop-down.
     *
     * @return {@code CDSClassificationsPage}
     */
    public CDSClassificationsPage openClassificationsPage() {
        return selectOptionOfSettingsDropDown("Classifications", CDSClassificationsPage.class);
    }

    /**
     * Opens 'Custom Attributes' page by selecting respective option in 'Settings' drop-down.
     *
     * @return {@code CDSCustomAttributesPage}
     */
    public CDSCustomAttributesPage openCustomAttributesPage() {
        return selectOptionOfSettingsDropDown("Custom Attributes", CDSCustomAttributesPage.class);
    }

    /**
     * Opens 'User Attributes' page by selecting respective option in 'Settings' drop-down.
     *
     * @return {@code CDSUserAttributesPage}
     */
    public CDSUserAttributesPage openUserAttributesPage() {
        return selectOptionOfSettingsDropDown("User Attributes", CDSUserAttributesPage.class);
    }

    /**
     * Opens 'Branding' page by selecting respective option in 'Settings' drop-down.
     *
     * @return {@code CDSBrandingPage}
     */
    public CDSBrandingPage openBrandingPage() {
        return selectOptionOfSettingsDropDown("Branding", CDSBrandingPage.class);
    }

    /**
     * Opens 'Trusted Applications' page by selecting respective option in 'Settings' drop-down.
     *
     * @return {@code CDSTrustedApplicationsPage}
     */
    public CDSTrustedApplicationsPage openTrustedApplicationPage() {
        return selectOptionOfSettingsDropDown("Trusted Applications", CDSTrustedApplicationsPage.class);
    }

    public void openToolsPage() { // ToDo -> should return respective page
//        return selectOptionOfSettingsDropDown("Tools", TBD...);
    }
}