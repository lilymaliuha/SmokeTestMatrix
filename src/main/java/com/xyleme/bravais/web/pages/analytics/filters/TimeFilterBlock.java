package com.xyleme.bravais.web.pages.analytics.filters;

import com.xyleme.bravais.web.elements.*;
import com.xyleme.bravais.web.pages.analytics.BaseAnalyticsPageHeader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Time Filter block.
 */
public class TimeFilterBlock extends BaseAnalyticsPageHeader {
    private String heading;

    TimeFilterBlock(WebDriver driver, String heading) {
        super(driver);
        this.heading = heading;
    }

    @Override
    public TimeFilterBlock load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return blockBody().isAvailable() &&
                blockHeading().isAvailable() &&
                periodSelectionDropDownInDateFilterBlock().isAvailable() &&
                fromDateInputInDateFilterBlock().isAvailable() &&
                toDateInputInDateFilterBlock().isAvailable() &&
                calendarButtonOfFromDateInputInDateFilterBlock().isAvailable() &&
                calendarButtonOfToDateInputInDateFilterBlock().isAvailable();
    }

    private Element blockBody() {
        return new Element(driver, By.xpath("//div[@id='time-filter']"));
    }

    private LabelText blockHeading() {
        return new LabelText(driver, By.xpath("//h1[text()='" + heading + "']"));
    }

    private Select periodSelectionDropDownInDateFilterBlock() {
        return new Select(driver, By.id("period-select"));
    }

    private TextInput fromDateInputInDateFilterBlock() {
        return new TextInput(driver, By.id("from"));
    }

    private TextInput toDateInputInDateFilterBlock() {
        return new TextInput(driver, By.id("to"));
    }

    private Button calendarButtonOfInputInDateFilterBlock(String inputId) {
        return new Button(driver, By.xpath("//input[@id='" + inputId + "']/parent::p/span/button"));
    }

    private Button calendarButtonOfFromDateInputInDateFilterBlock() {
        return calendarButtonOfInputInDateFilterBlock("from");
    }

    private Button calendarButtonOfToDateInputInDateFilterBlock() {
        return calendarButtonOfInputInDateFilterBlock("to");
    }
}