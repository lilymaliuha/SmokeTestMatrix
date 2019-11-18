package com.xyleme.bravais.web.pages.analytics.recordtables.records;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Implementation of the Course Progress Record object located in the Records table on 'Course Progress Reset' page.
 */
public class CourseProgressRecord extends BaseRecord {

    public CourseProgressRecord(WebDriver driver, WebElement recordBody) {
        super(driver, recordBody);
    }

    /**
     * Gets name of the Course.
     *
     * @return {@code String}
     */
    public String getCourse() {
        return getRecordValueOfColumn("Course");
    }

    /**
     * Gets full name of the user who launched a course.
     *
     * @return {@code String}
     */
    public String getFullName() {
        return getRecordValueOfColumn("Full Name");
    }

    /**
     * Gets email of the user who launched a course.
     *
     * @return {@code String}
     */
    public String getEmail() {
        return getRecordValueOfColumn("Email");
    }

    /**
     * Gets Employee ID.
     *
     * @return {@code String}
     */
    public String getEmployeeId() {
        return getRecordValueOfColumn("Employee ID");
    }

    /**
     * Gets time of the course launching.
     *
     * @return {@code Sting}
     */
    public String getStartedTimeStamp() {
        return getRecordValueOfColumn("Started");
    }

    /**
     * Gets checkbox element used for selecting/unselecting a course progress record.
     *
     * @return {@code WebElement}
     */
    private WebElement getSelectionCheckbox() {
        List<WebElement> recordElements = recordBody.findElements(By.xpath("./td"));
        return recordElements.get(recordElements.size() - 1).findElement(By.xpath("./input"));
    }

    /**
     * Selects the course progress record == checks the checkbox.
     *
     * @return {@code CourseProgressRecord}
     */
    public CourseProgressRecord selectRecord() {
        if (!doesElementContainAttribute(getSelectionCheckbox(), "checked")) {
            getSelectionCheckbox().click();
            waitUntil(doesElementContainAttribute(getSelectionCheckbox(), "checked"));
        } else {
            System.out.println("The record is already selected.");
        }
        return this;
    }
}