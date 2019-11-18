package com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.PreviewPane;
import org.openqa.selenium.WebDriver;

import static com.xyleme.bravais.BaseTest.staticSleep;

/**
 * Implementation of confirmation dialog which appears on a document comment deleting attempt.
 */
public class CommentDeletingConfirmationDialog extends BaseConfirmationDialog {

    public CommentDeletingConfirmationDialog(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                yesDeleteItButton().isAvailable();
    }

    private Button yesDeleteItButton() {
        return dialogBottomButton("Yes, delete it");
    }

    /**
     * Confirms comment deleting.
     *
     * @return {@code PreviewPane}
     */
    public PreviewPane confirmAction() {
        yesDeleteItButton().click();
        staticSleep(0.5);
        return new PreviewPane(driver);
    }

    /**
     * Clicks 'Cancel' button.
     *
     * @return {@code PreviewPane}
     */
    public PreviewPane cancelAction() {
        clickCancelButton();
        return new PreviewPane(driver);
    }

    /**
     * Clicks close ('x') button.
     *
     * @return {@code PreviewPane}
     */
    public PreviewPane closeDialog() {
        clickCloseDialogButton();
        return new PreviewPane(driver);
    }
}