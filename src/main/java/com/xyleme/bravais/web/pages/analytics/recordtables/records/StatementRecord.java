package com.xyleme.bravais.web.pages.analytics.recordtables.records;

import com.xyleme.bravais.web.pages.analytics.statementdetails.AnalyticsStatementDetailsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Implementation of the Statement Record object located in the Statements table on 'Detailed Report' page.
 */
public class StatementRecord extends BaseRecord {

    public StatementRecord(WebDriver driver, WebElement statementBody) {
        super(driver, statementBody);
    }

    /**
     * Gets name of the statement Actor.
     *
     * @return {@code String}
     */
    public String getActor() {
        return getRecordValueOfColumn("Actor");
    }

    /**
     * Gets Business Unit value of the statement record.
     *
     * @return {@code String}
     */
    public String getBusinessUnit() {
        return getRecordValueOfColumn("Business unit");
    }

    /**
     * Gets Location value of the statement record.
     *
     * @return {@code String}
     */
    public String getLocation() {
        return getRecordValueOfColumn("Location");
    }

    /**
     * Gets Verb value of the sattement record.
     *
     * @return {@code String}
     */
    public String getVerb() {
        return getRecordValueOfColumn("Verb");
    }

    /**
     * Gets Object value of the statement record.
     *
     * @return {@code String}
     */
    public String getObject() {
        return getRecordValueOfColumn("Object");
    }

    /**
     * Gets Type value of the statement record.
     *
     * @return {@code String}
     */
    public String getType() {
        return getRecordValueOfColumn("Type");
    }

    /**
     * Gets Time Stamp value of the statement record.
     *
     * @return {@code String}
     */
    public String getTimeStamp() {
        return getRecordValueOfColumn("Timestamp");
    }

    /**
     * Gets Details link of the statement record.
     *
     * @return {@code WebElement}
     */
    public WebElement getDetailsLink() {
        return recordBody.findElements(By.xpath("./td")).get(getColumnIndex("Details"))
                .findElement(By.xpath("./a"));
    }

    /**
     * Opens Statement Details page for the statement record.
     *
     * @return {@code AnalyticsStatementDetailsPage}
     */
    public AnalyticsStatementDetailsPage openDetailsPage() {
        getDetailsLink().click();
        return new AnalyticsStatementDetailsPage(driver);
    }
}