package com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs;

import com.xyleme.bravais.web.WebPage;
import com.xyleme.bravais.web.elements.Button;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of a base deleting confirmation dialog (common for ChannelDeletingConfirmationDialog,
 * ClassificationElementDeletingDialog, CustomAttributeDeletingConfirmationDialog classes).
 */
public abstract class BaseDeletingConfirmationDialog extends BaseConfirmationDialog {

    BaseDeletingConfirmationDialog(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                deleteButton().isAvailable();
    }

    private Button deleteButton() {
        return dialogBottomButton("Delete");
    }

    /**
     * Clicks 'Delete' button and returns specified page.
     *
     * @param pageToReturn - Specifies page intended to be returned after closing the dialog
     * @return {@code <T extends WebPage>}
     */
    public <T extends WebPage> T confirmAction(Class<T> pageToReturn) {
        deleteButton().click();
        return constructClassInstance(pageToReturn);
    }

    /**
     * Clicks 'Cancel' button.
     *
     * @param pageToReturn - Specifies page intended to be returned after closing the dialog
     * @return {@code <T extends WebPage>}
     */
    public <T extends WebPage> T cancelAction(Class<T> pageToReturn) {
        clickCancelButton();
        return constructClassInstance(pageToReturn);
    }

    /**
     * Clicks close ('x') button.
     *
     * @param pageToReturn - Specifies page intended to be returned after closing the dialog
     * @return {@code <T extends WebPage>}
     */
    public <T extends WebPage> T closeDialog(Class<T> pageToReturn) {
        clickCloseDialogButton();
        return constructClassInstance(pageToReturn);
    }
}