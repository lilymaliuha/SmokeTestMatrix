package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdscustomattributespage.attributecreationforms.attributetypes;

import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdscustomattributespage.attributecreationforms.BaseAttributeCreationForm;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of a base attribute type form (common class for 'boolean', 'choice', and 'string' types).
 */
public abstract class BaseAttributeType extends BaseAttributeCreationForm {

    BaseAttributeType(WebDriver driver) {
        super(driver);
    }

    @Override
    public BaseAttributeCreationForm load() {
        return this;
    }
}