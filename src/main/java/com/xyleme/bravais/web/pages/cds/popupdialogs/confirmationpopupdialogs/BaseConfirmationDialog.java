package com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.LabelText;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSPageHeader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Base Confirmation pop-up dialog.
 */
public abstract class BaseConfirmationDialog extends BaseCDSPageHeader {

    BaseConfirmationDialog(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAvailable() {
        return dialogBody().isAvailable() &&
                dialogTitle().isAvailable() &&
                closeDialogButton().isAvailable() &&
                dialogMessage().isAvailable() &&
                cancelButton().isAvailable();
    }

    Element dialogBody() {
        return new Element(driver, By.xpath("//div[contains(@class, 'confirm-dialog')]/div[@class='modal-dialog']"));
    }

    private LabelText dialogTitle() {
        return new LabelText(driver, By.xpath(dialogBody().getXpathLocatorValue() + "//h5[@class='modal-title']/b"));
    }

    private Button closeDialogButton() {
        return new Button(driver, By.xpath(dialogBody().getXpathLocatorValue() + "//button[@class='close']"));
    }

    private LabelText dialogMessage() {
        return new LabelText(driver, By.xpath(dialogBody().getXpathLocatorValue() +
                "//div[starts-with(@class, 'modal-body')]/small"));
    }

    Button dialogBottomButton(String buttonTitle) {
        return new Button(driver, By.xpath(dialogBody().getXpathLocatorValue() + "//button[text()='" + buttonTitle + "']"));
    }

    private Button cancelButton() {
        return dialogBottomButton("Cancel");
    }

    /**
     * Gets message displayed on the dialog.
     *
     * @return {@code String}
     */
    public String getDialogMessage() {
        return dialogMessage().getText();
    }

    /**
     * Clicks close ('x') button.
     */
    void clickCloseDialogButton() {
        closeDialogButton().click();
    }

    /**
     * Clicks 'Cancel' button.
     */
    void clickCancelButton() {
        cancelButton().click();
    }
}