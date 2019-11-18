package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsuserdetailspage.panels.identitiespane.paneblocks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of CAS Identity block available on Identities panel of User Details page.
 */
public class CASIdentityBlock extends BaseIdentitiesPanelBlock {

    public CASIdentityBlock(WebDriver driver, By blockElementLocator) {
        super(driver, blockElementLocator);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable();
    }

    @Override
    public CASIdentityBlock load() {
        return this;
    }
}