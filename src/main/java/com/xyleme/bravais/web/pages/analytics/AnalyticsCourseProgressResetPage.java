package com.xyleme.bravais.web.pages.analytics;

import com.xyleme.bravais.web.pages.analytics.filters.CourseProgressResetFilter;
import com.xyleme.bravais.web.pages.analytics.recordtables.CourseProgressRecordsTable;
import org.openqa.selenium.WebDriver;

public class AnalyticsCourseProgressResetPage extends BaseAnalyticsPageHeader {

    public AnalyticsCourseProgressResetPage(WebDriver driver) {
        super(driver);
        this.loadAndWaitUntilAvailable();
    }

    public AnalyticsCourseProgressResetPage(WebDriver driver, boolean waitForPageToLoad) {
        super(driver);
        if (waitForPageToLoad) {
            this.loadAndWaitUntilAvailable();
        }
    }

    @Override
    public AnalyticsCourseProgressResetPage load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() && getCourseProgressResetFilter().isAvailable();
    }

    /**
     * Gets Course Progress Reset filter block with all its functionality.
     *
     * @return {@code CourseProgressResetFilter}
     */
    private CourseProgressResetFilter getCourseProgressResetFilter() {
        return new CourseProgressResetFilter(driver);
    }

    /**
     * Filters Course Progress records by Course parameter.
     *
     * @param course - Specifies the course
     * @return {@code AnalyticsCourseProgressResetPage}
     */
    private AnalyticsCourseProgressResetPage filterByCourse(String course) {
        return getCourseProgressResetFilter().filterByCourse(course);
    }

    /**
     * Filters Course Progress records by Learner parameter.
     *
     * @param learner - Specifies the learner
     * @return {@code AnalyticsCourseProgressResetPage}
     */
    private AnalyticsCourseProgressResetPage filterByLearner(String learner) {
        return getCourseProgressResetFilter().filterByLearner(learner);
    }

    /**
     * Resets progress for specified Course Progress record.
     *
     * @param recordIndex - Specifies Course Progress record index in the table
     * @return {@code AnalyticsCourseProgressResetPage}
     */
    private AnalyticsCourseProgressResetPage resetCourseProgress(int recordIndex) {
        int initialNumberOfRecords = getCourseProgressRecordsTable().getNumberOfTableRecords();
        getCourseProgressRecordsTable().getCourseProgressRecord(recordIndex).selectRecord();
        getCourseProgressResetFilter().clickResetSelectedAttemptsButtonAndConfirmProgressReset();
        waitUntil(getCourseProgressRecordsTable().getNumberOfTableRecords() == initialNumberOfRecords - 1);
        return this;
    }

    /**
     * Resets progress for all Course Progress records listed in the table.
     *
     * @return {@code AnalyticsCourseProgressResetPage}
     */
    private AnalyticsCourseProgressResetPage resetProgressOfAllCourses() {
        getCourseProgressResetFilter().clickSelectAllProgressRecordsLink();
        getCourseProgressResetFilter().clickResetSelectedAttemptsButtonAndConfirmProgressReset();
        waitUntil(getCourseProgressRecordsTable().getNumberOfTableRecords() == 0);
        return this;
    }

    /**
     * Resets progress of most recent course (first in the list) launched by specified learner.
     *
     * @param learner - Specifies the learner
     * @return {@code AnalyticsCourseProgressResetPage}
     */
    public AnalyticsCourseProgressResetPage resetProgressOfMostRecentCourseLaunchedByLearner(String learner) {
        filterByLearner(learner);
        return resetCourseProgress(0);
    }

    /**
     * Resets progress of all courses launched by specified learner.
     *
     * @param learner - Specifies the learner
     * @return {@code AnalyticsCourseProgressResetPage}
     */
    public AnalyticsCourseProgressResetPage resetProgressOfAllCoursesLaunchedByLearner(String learner) {
        filterByLearner(learner);
        return resetProgressOfAllCourses();
    }

    /**
     * Gets table with Course Progress records displayed on the page.
     *
     * @return {@code CourseProgressRecordsTable}
     */
    public CourseProgressRecordsTable getCourseProgressRecordsTable() {
        return new CourseProgressRecordsTable(driver, true);
    }
}