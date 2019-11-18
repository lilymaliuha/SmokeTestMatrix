package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdspreferencespage;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.LabelText;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSPageHeader;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdspreferencespage.configurationblocks.DocumentDownloadConfigBlock;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdspreferencespage.configurationblocks.SharingContentConfigBlock;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdspreferencespage.configurationblocks.TimeZoneConfigBlock;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdspreferencespage.configurationblocks.UserCommentsConfigBlock;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of CDS Preferences page.
 */
public class CDSPreferencesPage extends BaseCDSPageHeader {

    public CDSPreferencesPage(WebDriver driver) { // Don't delete this constructor, it's used implicitly by a generic method 'constructClassInstance'.
        super(driver);
        this.waitUntilAvailable();
    }

    public CDSPreferencesPage(WebDriver driver, boolean waitForPageToLoad) {
        super(driver);
        if (waitForPageToLoad) {
            this.waitUntilAvailable();
        }
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                pageHeading().isAvailable() &&
                userCommentsConfigurationBlock().isAvailable() &&
                sharingContentConfigurationBlock().isAvailable() &&
                documentDownloadConfigurationBlock().isAvailable() &&
                timeZoneConfigurationBlock().isAvailable() &&
                saveButton().isAvailable();
    }

    private LabelText pageHeading() {
        return new LabelText(driver, By.xpath("//h1[starts-with(@class, 'page-heading')]"));
    }

    private Element configurationBlock(String blockName) {
        return new Element(driver, By.xpath("//h3[starts-with(@class, 'panel-title') and normalize-space()='" + blockName
                + "']/ancestor::div[contains(@class, 'panel-default')]"));
    }

    private Element userCommentsConfigurationBlock() {
        return configurationBlock("User Comments");
    }

    private Element sharingContentConfigurationBlock() {
        return configurationBlock("Sharing Content");
    }

    private Element documentDownloadConfigurationBlock() {
        return configurationBlock("Document Download");
    }

    private Element timeZoneConfigurationBlock() {
        return configurationBlock("Time Zone");
    }

    private Button saveButton() {
        return new Button(driver, By.xpath("//button[normalize-space()='Save']"));
    }

    /**
     * Gets 'User Comments' configuration block.
     *
     * @return {@code UserCommentsConfigBlock}
     */
    public UserCommentsConfigBlock getUserCommentsConfigurationBlock() {
        return new UserCommentsConfigBlock(driver, userCommentsConfigurationBlock());
    }


    /**
     * Gets 'Sharing Content' configuration block.
     *
     * @return {@code SharingContentConfigBlock}
     */
    public SharingContentConfigBlock getSharingContentConfigurationBlock() { // ToDo: Work on the config block!
        return new SharingContentConfigBlock(driver, sharingContentConfigurationBlock());
    }

    /**
     * Gets 'Document Download' configuration block.
     *
     * @return {@code DocumentDownloadConfigBlock}
     */
    public DocumentDownloadConfigBlock getDocumentDownloadConfigurationBlock() { // ToDo: Work on the config block!
        return new DocumentDownloadConfigBlock(driver, documentDownloadConfigurationBlock());
    }

    /**
     * Gets 'Time Zone' configuration block.
     *
     * @return {@code TimeZoneConfigBlock}
     */
    public TimeZoneConfigBlock getTimeZoneConfigurationBlock() { // ToDo: Work on the config block!
        return new TimeZoneConfigBlock(driver, timeZoneConfigurationBlock());
    }

    /**
     * Clicks 'Save' button.
     *
     * @return {@code CDSPreferencesPage}
     */
    protected CDSPreferencesPage clickSaveButton() {
        saveButton().click();
        return new CDSPreferencesPage(driver, true);
    }
}