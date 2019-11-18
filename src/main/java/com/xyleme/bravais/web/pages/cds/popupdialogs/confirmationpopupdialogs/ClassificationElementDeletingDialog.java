package com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs;

import org.openqa.selenium.WebDriver;

/**
 * Implementation of a confirmation dialog which appears on a classification element deleting attempt.
 */
public class ClassificationElementDeletingDialog extends BaseDeletingConfirmationDialog {

    public ClassificationElementDeletingDialog(WebDriver driver) {
        super(driver);
    }
}