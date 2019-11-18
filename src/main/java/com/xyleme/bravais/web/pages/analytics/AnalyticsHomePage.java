package com.xyleme.bravais.web.pages.analytics;

import com.xyleme.bravais.web.elements.*;
import com.xyleme.bravais.web.pages.analytics.filters.ContentOverviewFilterBlock;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AnalyticsHomePage extends BaseAnalyticsPageHeader {

    public AnalyticsHomePage(WebDriver driver) {
        super(driver);
        this.loadAndWaitUntilAvailable();
    }

    @Override
    public AnalyticsHomePage load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                getContentOverviewFilterBlock().isAvailable() &&
                activityBlockHeading().isAvailable() &&
                activityLineChart().isAvailable() &&
                activityStatisticsSummaryBlock().isAvailable() &&
                learningActionsBlockHeading().isAvailable() &&
                verbsBarChart().isAvailable() &&
                applicationsBlockHeading().isAvailable() &&
                applicationsPieChart().isAvailable() &&
                mostPopularContentBlockHeading().isAvailable() &&
                mostPopularContentTable().isAvailable() &&
                mostActiveLearnersBlockHeading().isAvailable() &&
                mostActiveLearnersTable().isAvailable();
    }

    private LabelText activityBlockHeading() {
        return new LabelText(driver, By.xpath("//h2[text()='Activity']"));
    }

    private Element activityLineChart() {
        return new Element(driver, By.id("activity-line-chart"));
    }

    private Element activityStatisticsSummaryBlock() {
        return new Element(driver, By.id("summary"));
    }

    private LabelText learningActionsBlockHeading() {
        return new LabelText(driver, By.xpath("//h3[text()='Learning actions']"));
    }

    private Element verbsBarChart() {
        return new Element(driver, By.id("verbs-bar-chart"));
    }

    private LabelText applicationsBlockHeading() {
        return new LabelText(driver, By.xpath("//h3[text()='Applications']"));
    }

    private Element applicationsPieChart() {
        return new Element(driver, By.id("app-pie-chart"));
    }

    private LabelText mostPopularContentBlockHeading() {
        return new LabelText(driver, By.xpath("//h2[text()='Most Popular Content']"));
    }

    private Element mostPopularContentTable() {
        return new Element(driver, By.xpath("//h2[text()='Most Popular Content']/parent::div//table"));
    }

    private LabelText mostActiveLearnersBlockHeading() {
        return new LabelText(driver, By.xpath("//h2[text()='Most Active Learners']"));
    }

    private Element mostActiveLearnersTable() {
        return new Element(driver, By.xpath("//h2[text()='Most Active Learners']/parent::div//table"));
    }

    /**
     * Gets Content Overview filter block with all its functionality.
     *
     * @return {@code ContentOverviewFilterBlock}
     */
    private ContentOverviewFilterBlock getContentOverviewFilterBlock() {
        return new ContentOverviewFilterBlock(driver);
    }
}