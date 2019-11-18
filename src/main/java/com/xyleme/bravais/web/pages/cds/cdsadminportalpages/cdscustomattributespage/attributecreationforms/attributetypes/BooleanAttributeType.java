package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdscustomattributespage.attributecreationforms.attributetypes;

import com.xyleme.bravais.web.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of an attribute creation form of a Boolean type.
 */
public class BooleanAttributeType extends BaseAttributeType {

    public BooleanAttributeType(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                disabledYesNoField().isAvailable();
    }

    private Element disabledYesNoField() {
        return new Element(driver, By.xpath(parentFormElement().getXpathLocatorValue() +
                "//span[contains(@class, 'form-control boolean')]"));
    }
}