package com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs;

import org.openqa.selenium.WebDriver;

/**
 * Implementation of a confirmation dialog which appears on a channel deleting attempt.
 */
public class ChannelDeletingConfirmationDialog extends BaseDeletingConfirmationDialog {

    public ChannelDeletingConfirmationDialog(WebDriver driver) {
        super(driver);
    }
}