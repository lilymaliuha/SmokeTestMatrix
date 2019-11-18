package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsuserdetailspage.panels.identitiespane.paneblocks;

import com.xyleme.bravais.web.WebPage;
import com.xyleme.bravais.web.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of a base Block object of Identities panel.
 */
public abstract class BaseIdentitiesPanelBlock extends WebPage<BaseIdentitiesPanelBlock> {
    private Element blockElement;

    public BaseIdentitiesPanelBlock(WebDriver driver, By blockElementLocator) {
        super(driver);
        blockElement = new Element(driver, blockElementLocator);
    }

    @Override
    public boolean isAvailable() {
        return blockHeader().isAvailable() &&
                blockBody().isAvailable();
    }

    private Element blockHeader() {
        return new Element(driver, By.xpath(blockElement.getXpathLocatorValue() + "/div[@class='panel-heading']"));
    }

    protected Element blockBody() {
        return new Element(driver, By.xpath(blockElement.getXpathLocatorValue() + "/div[@class='panel-body']"));
    }
}