package com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSTrustedApplicationsPage;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of a popup dialog which appears on attempting to remove an application on Trusted Applications page.
 */
public class TrustedAppRemovingConfirmationDialog extends BaseConfirmationDialog {

    public TrustedAppRemovingConfirmationDialog(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                removeButton().isAvailable();
    }

    private Button removeButton() {
        return dialogBottomButton("Remove");
    }

    /**
     * Clicks 'Remove' button.
     *
     * @return {@code CDSTrustedApplicationsPage}
     */
    public CDSTrustedApplicationsPage confirmAction() {
        removeButton().click();
        return new CDSTrustedApplicationsPage(driver);
    }

    /**
     * Clicks 'Cancel' button.
     *
     * @return {@code CDSTrustedApplicationsPage}
     */
    public CDSTrustedApplicationsPage cancelAction() {
        clickCancelButton();
        return new CDSTrustedApplicationsPage(driver);
    }

    /**
     * Clicks close ('x') button.
     *
     * @return {@code CDSTrustedApplicationsPage}
     */
    public CDSTrustedApplicationsPage closeDialog() {
        clickCloseDialogButton();
        return new CDSTrustedApplicationsPage(driver);
    }
}