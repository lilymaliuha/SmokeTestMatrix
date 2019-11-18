package com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsgroupdetailspage.CDSGroupDetailsPage;
import org.openqa.selenium.WebDriver;

public class RemovingGroupMemberConfirmationDialog extends BaseConfirmationDialog {

    public RemovingGroupMemberConfirmationDialog(WebDriver driver) {
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
     * @return {@code CDSGroupDetailsPage}
     */
    public CDSGroupDetailsPage confirmAction() {
        removeButton().click();
        return new CDSGroupDetailsPage(driver);
    }

    /**
     * Clicks 'Cancel' button.
     *
     * @return {@code CDSGroupDetailsPage}
     */
    public CDSGroupDetailsPage cancelAction() {
        clickCancelButton();
        return new CDSGroupDetailsPage(driver);
    }

    /**
     * Clicks close ('x') button.
     *
     * @return {@code CDSGroupDetailsPage}
     */
    public CDSGroupDetailsPage closeDialog() {
        clickCloseDialogButton();
        return new CDSGroupDetailsPage(driver);
    }
}
