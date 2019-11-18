package com.xyleme.bravais.web.pages.analytics;

import com.xyleme.bravais.web.elements.Link;
import com.xyleme.bravais.web.pages.BasePageHeaderBlock;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdshomepage.CDSHomePage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSLogoutPage;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of a header section common for all Analytics pages.
 */
public abstract class BaseAnalyticsPageHeader extends BasePageHeaderBlock {

    protected BaseAnalyticsPageHeader(WebDriver driver) {
        super(driver);
    }

    @Override
    public BaseAnalyticsPageHeader load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                dashboardsDropDownLinkInNavigationMenu().isAvailable() &&
                reportsDropDownLinkInNavigationMenu().isAvailable() &&
                administrationDropDownLinkInNavigationMenu().isAvailable();
    }

    private Link dashboardsDropDownLinkInNavigationMenu() {
        return dropDownLinkInNavMenu("Dashboards");
    }

    private Link reportsDropDownLinkInNavigationMenu() {
        return dropDownLinkInNavMenu("Reports");
    }

    private Link administrationDropDownLinkInNavigationMenu() {
        return dropDownLinkInNavMenu("Administration");
    }

    /**
     * Opens drop-down menu next to Xyleme logo and selects 'Admin Portal' menu option.
     *
     * @return {@code CDSHomePage}
     */
    public CDSHomePage goToAdminPortal() {
        selectModule("Admin Portal");
        return new CDSHomePage(driver);
    }

    /**
     * Opens page which contains statements (Detailed Report page).
     *
     * @return {@code AnalyticsStatementsPage}
     */
    public AnalyticsStatementsPage openStatementsPage() {
        return selectOptionOfNavMenuDropDown("Reports", "Detailed Report", AnalyticsStatementsPage.class);
    }

    /**
     * Opens Course Progress Reset page.
     *
     * @return {@code AnalyticsCourseProgressResetPage}
     */
    public AnalyticsCourseProgressResetPage openCourseProgressResetPage() {
        return selectOptionOfNavMenuDropDown("Administration", "Course Progress Reset", AnalyticsCourseProgressResetPage.class);
    }

    /**
     * Clicks 'Sign Out' option in User Profile drop-down.
     *
     * @return {@code CDSLogoutPage}
     */
    public CDSLogoutPage signOut() {
        selectOptionOfUserProfileDropDown("Logout");
        return new CDSLogoutPage(driver);
    }
}