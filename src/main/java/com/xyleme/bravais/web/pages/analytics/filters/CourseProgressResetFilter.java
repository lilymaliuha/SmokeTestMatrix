package com.xyleme.bravais.web.pages.analytics.filters;

import com.xyleme.bravais.web.elements.*;
import com.xyleme.bravais.web.pages.analytics.AnalyticsCourseProgressResetPage;
import com.xyleme.bravais.web.pages.analytics.AnalyticsStatementsPage;
import com.xyleme.bravais.web.pages.analytics.recordtables.StatementRecordsTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Implementation of Course Progress Reset filter.
 */
public class CourseProgressResetFilter extends BaseAnalyticsFilter {

    public CourseProgressResetFilter(WebDriver driver) {
        super(driver, "Course Progress Reset");
        this.waitUntilAvailable();
    }

    @Override
    public CourseProgressResetFilter load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                courseInputField().isAvailable() &&
                learnerInputField().isAvailable() &&
                resetSelectedAttemptsButton().isAvailable();
    }

    private TextInput courseInputField() {
        return filterInputField("Course");
    }

    private TextInput learnerInputField() {
        return filterInputField("Learner");
    }

    private Button resetSelectedAttemptsButton() {
        return new Button(driver, By.xpath("//button[starts-with(text(), 'Reset Selected Attempts')]"));
    }

    private Link selectAllAttemptsLink() {
        return new Link(driver, By.xpath("//a[starts-with(text(), 'Select All')]"));
    }

    /**
     * Filters Course Progress records by Course parameter.
     *
     * @param course - Specifies the course
     * @return {@code AnalyticsCourseProgressResetPage}
     */
    public AnalyticsCourseProgressResetPage filterByCourse(String course) {
        setValueIntoFilterInputFieldAndReturnListOfSuggesterMatches("Course", course);
        clickHighlightedSuggestedMatch(course);
        return clickApplyFilterButton(AnalyticsCourseProgressResetPage.class);
    }

    /**
     * Filters Course Progress records by Learner parameter.
     *
     * @param learner - Specifies the learner
     * @return {@code AnalyticsCourseProgressResetPage}
     */
    public AnalyticsCourseProgressResetPage filterByLearner(String learner) {
        List<WebElement> suggestedMatches = setValueIntoFilterInputFieldAndReturnListOfSuggesterMatches("Learner",
                learner);
        if (suggestedMatches.size() > 1) {  // --> Workaround for LOR-6426/AC-940.
            System.out.println("More than one learner has been displayed in the list of suggested matches! " +
                    "Number of identical actors in the list: " + suggestedMatches.size());
            for (int i = 0; i < suggestedMatches.size(); i++) {
                clickWebElementAvoidingElementStaleness(getSuggestedMatchesOfActiveInputField(learner).get(i));
                clickApplyFilterButton(AnalyticsStatementsPage.class);
                if (!new StatementRecordsTable(driver, false).isAvailable()) {
                    setValueIntoFilterInputFieldAndReturnListOfSuggesterMatches("Learner", learner);
                } else {
                    break;
                }
            }
        } else {
            clickHighlightedSuggestedMatch(learner);
            return clickApplyFilterButton(AnalyticsCourseProgressResetPage.class);
        }
        return new AnalyticsCourseProgressResetPage(driver, true);
    }

    /**
     * Checks if Reset All Selected Attempts button is enabled.
     *
     * @return {@code boolean}
     */
    private boolean isResetSelectedAttemptsButtonEnabled() {
        return resetSelectedAttemptsButton().isActive();
    }

    /**
     * Clicks on Select All Attempts link and waits until Reset Selected Attempts button gets enabled.
     *
     * @return {@code AnalyticsCourseProgressResetPage}
     */
    public AnalyticsCourseProgressResetPage clickSelectAllProgressRecordsLink() {
        selectAllAttemptsLink().waitUntilAvailable().click();
        waitUntil(isResetSelectedAttemptsButtonEnabled());
        return new AnalyticsCourseProgressResetPage(driver);
    }

    /**
     * Clicks Reset Selected Attempts button and confirms progress reset.
     *
     * @return {@code AnalyticsCourseProgressResetPage}
     */
    public AnalyticsCourseProgressResetPage clickResetSelectedAttemptsButtonAndConfirmProgressReset() {
        if (isResetSelectedAttemptsButtonEnabled()) {
            resetSelectedAttemptsButton().click();
        } else {
            throw new RuntimeException("Button 'Reset Selected Attempts' cannot be clicked because it is in disabled state.");
        }
        return getProgressResetConfirmationDialog().confirmProgressResetting();
    }

    /**
     * Gets Progress Reset confirmation dialog.
     *
     * @return {@code ProgressResetConfirmationDialog}
     */
    private ProgressResetConfirmationDialog getProgressResetConfirmationDialog() {
        return new ProgressResetConfirmationDialog(driver);
    }

    /**
     * Implementation of Progress Reset confirmation dialog.
     */
    private class ProgressResetConfirmationDialog extends CourseProgressResetFilter {

        ProgressResetConfirmationDialog(WebDriver driver) {
            super(driver);
            this.waitUntilAvailable();
        }

        @Override
        public boolean isAvailable() {
            return dialogBody().isAvailable() &&
                    dialogHeader().isAvailable() &&
                    closeDialogButton().isAvailable() &&
                    dialogMessage().isAvailable() &&
                    cancelButton().isAvailable() &&
                    yesResetProgressButton().isAvailable();
        }

        private Element dialogBody() {
            return new Element(driver, By.xpath("//div[@class='modal-content']"));
        }

        private LabelText dialogHeader() {
            return new LabelText(driver,
                    By.xpath("//b[text()=' Confirmation required']/ancestor::div[contains(@class, 'modal-header')]"));
        }

        private Button closeDialogButton() {
            return new Button(driver, By.xpath("//div[contains(@class, 'modal-header')]/button[@class='close']"));
        }

        private LabelText dialogMessage() {
            return new LabelText(driver, By.xpath("//div[contains(@class, 'modal-body')]"));
        }

        private Button footerButton(String buttonTitle) {
            return new Button(driver,
                    By.xpath("//div[contains(@class, 'modal-footer')]//button[text()='" + buttonTitle + "']"));
        }

        private Button cancelButton() {
            return footerButton("Cancel");
        }

        private Button yesResetProgressButton() {
            return footerButton("Yes, Reset Progress");
        }

        /**
         * Clicks 'Yes, Reset Progress' button and waits until the dialog disappears.
         *
         * @return {@code AnalyticsCourseProgressResetPage}
         */
        AnalyticsCourseProgressResetPage confirmProgressResetting() {
            yesResetProgressButton().click();
            waitUntil(!this.isAvailable());
            return new AnalyticsCourseProgressResetPage(driver);
        }

        /**
         * Clicks 'Cancel' button and waits until the dialog disappears.
         *
         * @return {@code AnalyticsCourseProgressResetPage}
         */
        public AnalyticsCourseProgressResetPage cancelProgressResetting() {
            cancelButton().click();
            waitUntil(!this.isAvailable());
            return new AnalyticsCourseProgressResetPage(driver);
        }

        /**
         * Clicks 'X' button in the top right corner of the dialog and waits until the dialog disappears.
         *
         * @return {@code AnalyticsCourseProgressResetPage}
         */
        public AnalyticsCourseProgressResetPage closeDialog() {
            closeDialogButton().click();
            waitUntil(!this.isAvailable());
            return new AnalyticsCourseProgressResetPage(driver);
        }
    }
}