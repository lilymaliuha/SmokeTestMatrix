package com.xyleme.bravais.web.pages.analytics.filters;

import com.xyleme.bravais.web.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Base Filter Block.
 */
public abstract class BaseFilterBlock extends BaseAnalyticsFilter {

    BaseFilterBlock(WebDriver driver, String blockHeading) {
        super(driver, blockHeading);
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                filterBlock().isAvailable();
    }

    private Element filterBlock() {
        return new Element(driver, By.id("filter-block"));
    }
}