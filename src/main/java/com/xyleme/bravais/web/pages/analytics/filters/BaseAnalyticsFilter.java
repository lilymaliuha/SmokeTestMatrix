package com.xyleme.bravais.web.pages.analytics.filters;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.TextInput;
import com.xyleme.bravais.web.pages.analytics.BaseAnalyticsPageHeader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Implementation of Base Analytics filter.
 */
abstract class BaseAnalyticsFilter extends TimeFilterBlock {

    BaseAnalyticsFilter(WebDriver driver, String heading) {
        super(driver, heading);
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() && applyFilterButton().isAvailable();
    }

    private Button applyFilterButton() {
        return new Button(driver, By.xpath("//button[starts-with(text(), 'Apply')]"));
    }

    TextInput filterInputField(String inputTitle) {
        return new TextInput(driver, By.xpath("//label[normalize-space()='" + inputTitle + "']/parent::div//input"));
    }

    /**
     * Checks if specified input field contains any selected matches, if so - deletes them == clears the input field.
     *
     * @param inputTitle - Specifies title of input field
     */
    private void checkIfInputFieldContainsSelectedMatchesIfSoClearInputField(String inputTitle) {
        List<WebElement> selectedMatches = driver.findElements(By.xpath("//label[text()='" + inputTitle
                + "']/parent::div//span[contains(@class, 'match-item')]"));
        if (selectedMatches.size() > 0) {
            for (WebElement selectedMatch : selectedMatches) {
                selectedMatch.findElement(By.xpath("./span[contains(@class, 'close')]")).click();
            }
        } else {
            System.out.println("Filter input field '" + inputTitle + "' is empty therefore it can be filled out.");
        }
    }

    /**
     * Gets list of suggested matches displayed after entering actor name into the Actor text field in new Analytics.
     *
     * @param actor - Specifies name of the actor
     * @return {@code List<WebElement>}
     */
    List<WebElement> getSuggestedMatchesOfActiveInputField(String actor) {
        return getElementsAvoidingStaleReferenceException(
                By.xpath("//div[normalize-space()='" + actor + "']/ancestor::span[contains(@class, 'select-choices')]"));
    }

    /**
     * Sets specified value into specified filter input field and returns list of suggested matches.
     *
     * @param inputTitle - Specifies title of filter input field
     * @param value      - Specifies value intended to be entered into the specified input field
     * @return {@code List<WebElement>}
     */
    List<WebElement> setValueIntoFilterInputFieldAndReturnListOfSuggesterMatches(String inputTitle, String value) {
        checkIfInputFieldContainsSelectedMatchesIfSoClearInputField(inputTitle);
        filterInputField(inputTitle).waitUntilAvailable().sendKeys(value);
        return getSuggestedMatchesOfActiveInputField(value);
    }

    /**
     * Clicks highlighted suggested match.
     *
     * @param value - Specifies value entered into the input field
     */
    void clickHighlightedSuggestedMatch(String value) {
        clickWebElementAvoidingElementStaleness(By.xpath("//span[contains(@class, 'select-highlight') and contains(text(), '"
                + value + "')]/ancestor::span[contains(@class, 'select-choices-row')]"));
    }

    /**
     * Clicks 'Apply Filter/s' button and returns instance of a specific Analytics page.
     *
     * @param tClass - Specifies Analytics page expected to be returned after clicking the button
     * @return {@code <T extends BaseAnalyticsPageHeader>}
     */
    public <T extends BaseAnalyticsPageHeader> T clickApplyFilterButton(Class<T> tClass) {
        applyFilterButton().waitUntilAvailable().click();
        return constructClassInstance(tClass);
    }
}