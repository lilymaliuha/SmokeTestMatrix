package com.xyleme.bravais.web.pages.analytics.filters;

import com.xyleme.bravais.datacontainers.AnalyticsVerb;
import com.xyleme.bravais.web.elements.TextInput;
import com.xyleme.bravais.web.pages.analytics.AnalyticsStatementsPage;
import com.xyleme.bravais.web.pages.analytics.recordtables.StatementRecordsTable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Implementation of Detailed Filter block.
 */
public class DetailedReportFilterBlock extends BaseFilterBlock {

    public DetailedReportFilterBlock(WebDriver driver) {
        super(driver, "Detailed Report");
        this.waitUntilAvailable();
    }

    @Override
    public DetailedReportFilterBlock load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                actorInputField().isAvailable() &&
                documentInputField().isAvailable() &&
                verbInputField().isAvailable() &&
                supervisorInputField().isAvailable() &&
                businessUnitInputField().isAvailable() &&
                departmentInputField().isAvailable() &&
                locationInputField().isAvailable() &&
                roleInputField().isAvailable();
    }

    private TextInput actorInputField() {
        return filterInputField("Actor");
    }

    private TextInput documentInputField() {
        return filterInputField("Document");
    }

    private TextInput verbInputField() {
        return filterInputField("Verb");
    }

    private TextInput supervisorInputField() {
        return filterInputField("Supervisor");
    }

    private TextInput businessUnitInputField() {
        return filterInputField("Business Unit");
    }

    private TextInput departmentInputField() {
        return filterInputField("Department");
    }

    private TextInput locationInputField() {
        return filterInputField("Location");
    }

    private TextInput roleInputField() {
        return filterInputField("Role");
    }

    /**
     * Filters statements by Actor parameter.
     *
     * @param actor - Specifies the actor
     * @return {@code AnalyticsStatementsPage}
     */
    public AnalyticsStatementsPage filterByActor(String actor) {
        List<WebElement> suggestedMatches = setValueIntoFilterInputFieldAndReturnListOfSuggesterMatches("Actor", actor);
        if (suggestedMatches.size() > 1) {  // --> Workaround for LOR-6426/AC-940.
            System.out.println("More than one actor has been displayed in the list of suggested matches! " +
                    "Number of identical actors in the list: " + suggestedMatches.size());
            for (int i = 0; i < suggestedMatches.size(); i++) {
                clickWebElementAvoidingElementStaleness(getSuggestedMatchesOfActiveInputField(actor).get(i));
                clickApplyFilterButton(AnalyticsStatementsPage.class);
                if (!new StatementRecordsTable(driver, false).isAvailable()) {
                    setValueIntoFilterInputFieldAndReturnListOfSuggesterMatches("Actor", actor);
                } else {
                    break;
                }
            }
        } else {
            clickHighlightedSuggestedMatch(actor);
            return clickApplyFilterButton(AnalyticsStatementsPage.class);
        }
        return new AnalyticsStatementsPage(driver, true);
    }

    /**
     * Filters statements by Verb parameter.
     *
     * @param verb - Specifies the verb
     * @return {@code AnalyticsStatementsPage}
     */
    public AnalyticsStatementsPage filterByVerb(AnalyticsVerb verb) {
        String verbValue = verb.getValue();
        setValueIntoFilterInputFieldAndReturnListOfSuggesterMatches("Verb", verbValue);
        clickHighlightedSuggestedMatch(verbValue);
        return clickApplyFilterButton(AnalyticsStatementsPage.class);
    }
}