package com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs;

import org.openqa.selenium.WebDriver;

/**
 * Implementation of confirmation dialog which appears on a group deleting attempt.
 */
public class GroupDeletingConfirmationDialog extends BaseDeletingConfirmationDialog {

    public GroupDeletingConfirmationDialog(WebDriver driver) {
        super(driver);
    }
}