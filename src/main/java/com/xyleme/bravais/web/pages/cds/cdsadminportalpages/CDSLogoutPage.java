package com.xyleme.bravais.web.pages.cds.cdsadminportalpages;

import com.xyleme.bravais.web.WebPage;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.LabelText;
import com.xyleme.bravais.web.elements.Link;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsloginpage.CDSLoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of CDS Logout page - the page which appears after successful logout.
 */
public class CDSLogoutPage extends WebPage<CDSLogoutPage> {

    public CDSLogoutPage(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return signOutForm().isAvailable() &&
                messageInSignOutForm().isAvailable() &&
                signInLink().isAvailable();
    }

    @Override
    public CDSLogoutPage load() {
        return this;
    }

    private Element signOutForm() {
        return new Element(driver, By.xpath("//div[@class='form-general']"));
    }

    private LabelText messageInSignOutForm() {
        return new LabelText(driver, By.xpath(signOutForm().getXpathLocatorValue() + "/p"));
    }

    private Link signInLink() {
        return new Link(driver, By.xpath(signOutForm().getXpathLocatorValue() + "//a[text()='Sign In']"));
    }

    /**
     * Gets message displayed in the Sign Out form.
     *
     * @return {@code String}
     */
    public String getMessageInSignOutForm() {
        return messageInSignOutForm().readLabelText();
    }

    /**
     * Clicks 'Sign In' link in the Sign Out form.
     *
     * @return {@code CDSLoginPage}
     */
    public CDSLoginPage clickSignInLink() {
        signInLink().waitUntilAvailable().click();
        return new CDSLoginPage(driver);
    }
}