package com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs;

import org.openqa.selenium.WebDriver;

/**
 * Implementation of a confirmation dialog which appears on a user deleting attempt.
 */
public class UserDeletingConfirmationDialog extends BaseDeletingConfirmationDialog {

    public UserDeletingConfirmationDialog(WebDriver driver) {
        super(driver);
    }
}