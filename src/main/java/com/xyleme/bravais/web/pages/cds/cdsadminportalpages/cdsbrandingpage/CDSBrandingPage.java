package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsbrandingpage;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.LabelText;
import com.xyleme.bravais.web.elements.Link;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSPageHeader;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsbrandingpage.panels.*;
import com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs.DefaultsRestoringConfirmationDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.xyleme.bravais.BaseTest.staticSleep;

/**
 * Implementation of CDS Branding page.
 */
public class CDSBrandingPage extends BaseCDSPageHeader {

    public CDSBrandingPage(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                pageHeader().isAvailable() &&
                logoPanelElement().isAvailable() &&
                footerPanelElement().isAvailable() &&
                colorSystemPanelElement().isAvailable() &&
                typographyPanelElement().isAvailable() &&
                customStylesPanelElement().isAvailable() &&
                previewPanelElement().isAvailable() &&
                restoreDefaultsLink().isAvailable() &&
                cancelButton().isAvailable() &&
                saveButton().isAvailable();
    }

    private LabelText pageHeader() {
        return new LabelText(driver, By.xpath("//h1[contains(@class, 'page-heading')]"));
    }

    private Element pagePanelElement(String panelName) {
        return new Element(driver, By.xpath("//h3[text()='" + panelName +
                "']/ancestor::div[contains(@class, 'panel-default')]"));
    }

    private Element logoPanelElement() {
        return pagePanelElement("Logo");
    }

    private Element footerPanelElement() {
        return pagePanelElement("Footer");
    }

    private Element colorSystemPanelElement() {
        return pagePanelElement("Color System");
    }

    private Element typographyPanelElement() {
        return pagePanelElement("Typography");
    }

    private Element customStylesPanelElement() {
        return pagePanelElement("Custom Styles");
    }

    private Element previewPanelElement() {
        return pagePanelElement("Preview");
    }

    private Link restoreDefaultsLink() {
        return new Link(driver, By.xpath("//a[normalize-space()='Restore Defaults']"));
    }

    private Button footerButton(String buttonName) {
        return new Button(driver, By.xpath("//div[contains(@class, 'buttons-panel')]//button[normalize-space()='" +
                buttonName + "']"));
    }

    private Button cancelButton() {
        return footerButton("Cancel");
    }

    private Button saveButton() {
        return footerButton("Save");
    }

    /**
     * Gets Logo panel
     *
     * @return {@code LogoPanel}
     */
    public LogoPanel getLogoPanel() {
        return new LogoPanel(driver, logoPanelElement());
    }

    /**
     * Gets Footer panel.
     *
     * @return {@code FooterPanel}
     */
    public FooterPanel getFooterPanel() {
        return new FooterPanel(driver, footerPanelElement());
    }

    /**
     * Gets Color System panel.
     *
     * @return {@code ColorSystemPanel}
     */
    public ColorSystemPanel getColorSystemPanel() {
        return new ColorSystemPanel(driver, colorSystemPanelElement());
    }

    /**
     * Gets Typography panel.
     *
     * @return {@code TypographyPanel}
     */
    public TypographyPanel getTypographyPanel() {
        return new TypographyPanel(driver, typographyPanelElement());
    }

    /**
     * Gets Custom Styles panel.
     *
     * @return {@code CustomStylesPanel}
     */
    public CustomStylesPanel getCustomStylesPanel() {
        return new CustomStylesPanel(driver, customStylesPanelElement());
    }

    /**
     * Gets Preview panel.
     *
     * @return {@code PreviewPanel}
     */
    public PreviewPanel getPreviewPanel() {
        return new PreviewPanel(driver, previewPanelElement());
    }

    /**
     * Clicks 'Restore Defaults' link and confirms the restoring action on the respective pop-up dialog.
     *
     * @return {@code CDSBrandingPage}
     */
    public CDSBrandingPage restoreDefaults() {
        restoreDefaultsLink().click();
        new DefaultsRestoringConfirmationDialog(driver).confirmAction();
        staticSleep(1); // Time needed for footer update.
        return this;
    }

    /**
     * Clicks 'Cancel' button.
     *
     * @return {@code CDSBrandingPage}
     */
    public CDSBrandingPage cancelChanges() {
        cancelButton().click();
        return this;
    }

    /**
     * Clicks 'Save' button and waits until the changes are applied (waits until spinner on the button disappears).
     *
     * @return {@code CDSBrandingPage}
     */
    public CDSBrandingPage saveChanges() {
        saveButton().click();
        new WebDriverWait(driver, 5).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
                saveButton().getXpathLocatorValue() +  "/i[contains(@class, 'spin')]")));
        staticSleep(1); // Time needed for footer update.
        return this;
    }
}