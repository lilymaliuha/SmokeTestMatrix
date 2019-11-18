package com.xyleme.bravais.web.pages.analytics.filters;

import com.xyleme.bravais.web.elements.TextInput;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Content Overview Filter block.
 */
public class ContentOverviewFilterBlock extends BaseFilterBlock {

    public ContentOverviewFilterBlock(WebDriver driver) {
        super(driver,"Content Overview");
    }

    @Override
    public ContentOverviewFilterBlock load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                learnerInputField().isAvailable() &&
                supervisorInputField().isAvailable() &&
                businessUnitInputField().isAvailable() &&
                departmentInputField().isAvailable() &&
                locationInputField().isAvailable() &&
                roleInputField().isAvailable();
    }

    private TextInput learnerInputField() {
        return filterInputField("Learner");
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
}