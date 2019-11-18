package com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsbrandingpage.CDSBrandingPage;
import org.openqa.selenium.WebDriver;

public class DefaultsRestoringConfirmationDialog extends BaseConfirmationDialog {

    public DefaultsRestoringConfirmationDialog(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                restoreButton().isAvailable();
    }

    private Button restoreButton() {
        return dialogBottomButton("Restore");
    }

    /**
     * Clicks 'Restore' button.
     *
     * @return {@code CDSBrandingPage}
     */
    public CDSBrandingPage confirmAction() {
        restoreButton().click();
        return new CDSBrandingPage(driver);
    }

    /**
     * Clicks 'Cancel' button.
     *
     * @return {@code CDSBrandingPage}
     */
    public CDSBrandingPage cancelAction() {
        clickCancelButton();
        return new CDSBrandingPage(driver);
    }

    /**
     * Clicks close ('x') button.
     *
     * @return {@code CDSBrandingPage}
     */
    public CDSBrandingPage closeDialog() {
        clickCloseDialogButton();
        return new CDSBrandingPage(driver);
    }
}
