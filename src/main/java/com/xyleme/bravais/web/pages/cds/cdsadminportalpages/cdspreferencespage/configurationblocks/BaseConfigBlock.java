package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdspreferencespage.configurationblocks;

import com.xyleme.bravais.web.elements.CheckBox;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdspreferencespage.CDSPreferencesPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of a Base Configuration Block.
 */
public abstract class BaseConfigBlock extends CDSPreferencesPage {
    private Element blockElement;

    BaseConfigBlock(WebDriver driver, Element blockElement) {
        super(driver, false);
        this.blockElement = blockElement;
        this.waitUntilAvailable();
    }

    @Override
    public BaseConfigBlock load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return blockHeader().isAvailable() &&
                blockBody().isAvailable();
    }

    private Element blockHeader() {
        return new Element(driver, By.xpath(blockElement.getXpathLocatorValue() + "/div[@class='panel-heading']"));
    }

    private Element blockBody() {
        String blockBodyLocator;
        if (blockElement.getXpathLocatorValue().contains("Sharing Content")) {
            blockBodyLocator = "/div[@class='row preferences-item']";
        } else {
            blockBodyLocator = "/div[@class='panel-body']";
        }
        return new Element(driver, By.xpath(blockElement.getXpathLocatorValue() + blockBodyLocator));
    }

    Element configurationItemContainerBody(String configurationHeader) {
        return new Element(driver, By.xpath(blockBody().getXpathLocatorValue() + "//label[normalize-space()='" +
                configurationHeader + "']//ancestor::div[contains(@class, 'preferences-item')]"));
    }

    By configurationInputLocator(String configuration, String inputName) {
        return By.xpath(configurationItemContainerBody(configuration).getXpathLocatorValue() + "//label[normalize-space()='" +
                inputName + "']/input");
    }

    CheckBox configurationItemCheckbox(String configuration, String checkboxName) {
        return new CheckBox(driver, configurationInputLocator(configuration, checkboxName));
    }
}