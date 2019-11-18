package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsclassificationspage.pageforms;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsclassificationspage.CDSClassificationsPage;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Base Classification Element Form with Cancellation option.
 */
public abstract class BaseClassificationElementFormWithCancellationOption extends BaseClassificationElementForm {

    BaseClassificationElementFormWithCancellationOption(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                cancelButton().isAvailable();
    }

    private Button cancelButton() {
        return formButton("Cancel");
    }

    /**
     * Clicks 'Cancel' button and returns Classification page.
     *
     * @return {@code CDSClassificationsPage}
     */
    public CDSClassificationsPage clickCancelButton() {
        cancelButton().click();
        return new CDSClassificationsPage(driver);
    }
}