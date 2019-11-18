package com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSFavoritesPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Implementation of a popup dialog which appears after attempting to remove a document from Favorites list.
 */
public class RemovingFromFavoritesConfirmationDialog extends BaseConfirmationDialog {

    public RemovingFromFavoritesConfirmationDialog(WebDriver driver) {
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
     * @return {@code CDSFavoritesPage}
     */
    public CDSFavoritesPage confirmAction() {
        removeButton().click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
                dialogBody().getXpathLocatorValue())));
        return new CDSFavoritesPage(driver);
    }

    /**
     * Clicks 'Cancel' button.
     *
     * @return {@code CDSFavoritesPage}
     */
    public CDSFavoritesPage cancelAction() {
        clickCancelButton();
        return new CDSFavoritesPage(driver);
    }

    /**
     * Clicks close ('x') button.
     *
     * @return {@code CDSFavoritesPage}
     */
    public CDSFavoritesPage closeDialog() {
        clickCloseDialogButton();
        return new CDSFavoritesPage(driver);
    }
}