package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdscustomattributespage.attributecreationforms;

import org.openqa.selenium.WebDriver;

/**
 * Implementation of Add Attribute form which appears after clicking +Add Attribute button.
 */
public class AddAttributeForm extends BaseAttributeCreationForm {

    public AddAttributeForm(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable();
    }

    @Override
    public AddAttributeForm load() {
        return this;
    }
}