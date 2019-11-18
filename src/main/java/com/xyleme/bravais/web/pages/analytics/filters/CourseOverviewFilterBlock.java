package com.xyleme.bravais.web.pages.analytics.filters;

import org.openqa.selenium.WebDriver;

/**
 * Implementation of Course Overview Filter block.
 */
public class CourseOverviewFilterBlock extends BaseFilterBlock {

    public CourseOverviewFilterBlock(WebDriver driver) {
        super(driver, "Course Overview");
        this.waitUntilAvailable();
    }

    @Override
    public CourseOverviewFilterBlock load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable(); // && // ToDo: Needs to be extended!
    }
}