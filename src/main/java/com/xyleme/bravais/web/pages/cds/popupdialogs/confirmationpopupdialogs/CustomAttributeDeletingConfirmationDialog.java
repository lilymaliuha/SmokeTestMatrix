package com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs;

import org.openqa.selenium.WebDriver;

/**
 * Implementation of a confirmation dialog which appears on a custom attribute deleting attempt.
 */
public class CustomAttributeDeletingConfirmationDialog extends BaseDeletingConfirmationDialog {

    public CustomAttributeDeletingConfirmationDialog(WebDriver driver) {
        super(driver);
    }
}