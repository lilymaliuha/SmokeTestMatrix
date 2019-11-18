package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsbrandingpage.panels;

import com.xyleme.bravais.web.elements.Element;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Custom Styles panel available on Branding page.
 */
public class CustomStylesPanel extends BasePanelOnBrandingPage {

    public CustomStylesPanel(WebDriver driver, Element panelBodyElement) {
        super(driver, panelBodyElement);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable();
    }

    // ToDo: Work on the panel if needed.
}