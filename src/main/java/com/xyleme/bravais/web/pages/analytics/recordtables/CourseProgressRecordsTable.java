package com.xyleme.bravais.web.pages.analytics.recordtables;

import com.xyleme.bravais.web.pages.analytics.recordtables.records.CourseProgressRecord;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Course Progress Records table available on Course Progress Reset page.
 */
public class CourseProgressRecordsTable extends BaseRecordsTable {

    public CourseProgressRecordsTable(WebDriver driver, boolean waitForTable) {
        super(driver);
        if (waitForTable) {
            this.waitUntilAvailable();
        }
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                getTableColumnHeaderElement("Course").isDisplayed() &&
                getTableColumnHeaderElement("Full Name").isDisplayed() &&
                getTableColumnHeaderElement("Email").isDisplayed() &&
                getTableColumnHeaderElement("Employee ID").isDisplayed() &&
                getTableColumnHeaderElement("Started").isDisplayed();
    }

    /**
     * Gets specified Course Progress record object.
     *
     * @param recordIndex - Specifies index of the statement record in the table
     * @return {@code StatementRecord}
     */
    public CourseProgressRecord getCourseProgressRecord(int recordIndex) {
        return new CourseProgressRecord(driver, getTableRows().get(recordIndex));
    }
}