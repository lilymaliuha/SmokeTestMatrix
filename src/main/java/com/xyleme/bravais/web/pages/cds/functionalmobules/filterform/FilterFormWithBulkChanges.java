package com.xyleme.bravais.web.pages.cds.functionalmobules.filterform;

import com.xyleme.bravais.web.elements.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class FilterFormWithBulkChanges extends FilterForm {

    public FilterFormWithBulkChanges(WebDriver driver, By parentFormElementXPATHLocator) {
        super(driver, parentFormElementXPATHLocator);
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                bulkChangesDropDown().isAvailable();
    }

    private Select bulkChangesDropDown() {
        return new Select(driver, By.xpath(parentFormElement.getXpathLocatorValue() +
                "//select[contains(@class, 'bulk-action-select')]"));
    }

    /**
     * Selects specified option of 'Bulk Changes' drop-down.
     *
     * @param option - Specifies the option of the drop-down list intended to be selected
     */
    public void selectOptionOfBulkChangesDropDown(String option) {

        if (isOptionPresentInBulkChangesDropDown(option)) {
            bulkChangesDropDown().waitUntilAvailable().selectItemByValue(option);
        } else {
            throw new RuntimeException("Option '" + option + "' is not available in the Bulk Changes drop-down menu!");
        }
    }

    /**
     * Checks if the specified option is present in the Bulk Changes drop-down menu.
     *
     * @param option - Specifies the option intended to be checked
     * @return {@code boolean}
     */
    public boolean isOptionPresentInBulkChangesDropDown(String option) {
        List<WebElement> dropDownOptions = getElementsDynamically(By.xpath(bulkChangesDropDown().getXpathLocatorValue() +
                "/option[not(@value='')]"));
        List<String> availableOptions = new ArrayList<>();
        dropDownOptions.forEach(availableOption -> availableOptions.add(availableOption.getText().replaceAll("\\s",
                "")));

        if (availableOptions.contains(option)) {
            return true;
        } else {
            System.out.println(" >>> Bulk Changes drop-down menu doesn't contain '" + option +
                    "' option. Options available in the drop-down menu: " + availableOptions);
            return false;
        }
    }
}