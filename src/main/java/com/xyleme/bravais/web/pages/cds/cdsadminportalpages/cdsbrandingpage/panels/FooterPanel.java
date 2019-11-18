package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsbrandingpage.panels;

import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.TextInput;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsbrandingpage.CDSBrandingPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Footer panel available on Branding page.
 */
public class FooterPanel extends BasePanelOnBrandingPage {

    public FooterPanel(WebDriver driver, Element panelBodyElement) {
        super(driver, panelBodyElement);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                footerContentInputField().isAvailable();
    }

    private TextInput footerContentInputField() {
        return new TextInput(driver, By.xpath(panelBody().getXpathLocatorValue() +
                "//span[text()='Footer Content']/ancestor::tr/following-sibling::tr//textarea"));
    }

    /**
     * Enters specified text into Footer Content text field.
     *
     * @param footerContentToBeSet - Specifies the text (footer content) intended to be entered into the field
     * @return {@code CDSBrandingPage}
     */
    public CDSBrandingPage setCustomFooterContent(String footerContentToBeSet) {
        footerContentInputField().clear();
        footerContentInputField().sendKeys(footerContentToBeSet);
        return new CDSBrandingPage(driver);
    }
}