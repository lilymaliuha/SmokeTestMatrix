package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsuserdetailspage.panels.identitiespane;

import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSPanelOnDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsuserdetailspage.panels.identitiespane.paneblocks.CASIdentityBlock;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsuserdetailspage.panels.identitiespane.paneblocks.localidentityblock.LocalIdentityBlockWithAddedIdentity;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsuserdetailspage.panels.identitiespane.paneblocks.localidentityblock.LocalIdentityBlockWithNoIdentityAdded;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Identities pane on User Details page.
 */
public class IdentitiesPane extends BaseCDSPanelOnDetailsPage {

    public IdentitiesPane(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public IdentitiesPane load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return localIdentityBlockElement().isAvailable() &&
                casIdentityBlockElement().isAvailable();
    }

    private By locatorOfPanelBlock(String blockName) {
        return By.xpath("//h3[text()='" + blockName +  "']/ancestor::div[contains(@class, 'panel-default')]");
    }

    private Element localIdentityBlockElement() {
        return new Element(driver, locatorOfPanelBlock("Local Identity"));
    }

    private Element casIdentityBlockElement() {
        return new Element(driver, locatorOfPanelBlock("CAS Identity"));
    }

    /**
     * Gets Local Identity block with no identity added.
     *
     * @return {@code LocalIdentityBlockWithNoIdentityAdded}
     */
    public LocalIdentityBlockWithNoIdentityAdded getLocalIdentityBlockWithNoIdentityAdded() {
        return new LocalIdentityBlockWithNoIdentityAdded(driver, locatorOfPanelBlock("Local Identity"));
    }

    /**
     * Gets Local Identity block with added identity.
     *
     * @return {@code LocalIdentityBlockWithAddedIdentity}
     */
    public LocalIdentityBlockWithAddedIdentity getLocalIdentityBlockWithAddedIdentity() {
        return new LocalIdentityBlockWithAddedIdentity(driver, locatorOfPanelBlock("Local Identity"));
    }

    /**
     * Gets CAS Identity block.
     *
     * @return {@code CASIdentityBlock}
     */
    public CASIdentityBlock getCASIdentityBlock() {
        return new CASIdentityBlock(driver, locatorOfPanelBlock("CAS Identity"));
    }
}