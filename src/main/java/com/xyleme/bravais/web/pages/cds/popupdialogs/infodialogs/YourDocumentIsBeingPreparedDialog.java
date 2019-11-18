package com.xyleme.bravais.web.pages.cds.popupdialogs.infodialogs;

import com.xyleme.bravais.web.WebPage;
import com.xyleme.bravais.web.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Implementation of the dialog which appears after selecting a document fo preview in Consumer Portal.
 */
public class YourDocumentIsBeingPreparedDialog extends WebPage<YourDocumentIsBeingPreparedDialog> {
    private By dialogBody = By.xpath("//*[normalize-space()='Your document is being prepared']//parent::div[@class='modal-dialog']");

    public YourDocumentIsBeingPreparedDialog(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public YourDocumentIsBeingPreparedDialog load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return dialogBody().isAvailable();
    }

    private Element dialogBody() {
        return new Element(driver, dialogBody);
    }

    /**
     * Wits until the dialog disappears.
     */
    public void waitUntilDisappears() {
        new WebDriverWait(driver, 30).until(ExpectedConditions.invisibilityOfElementLocated(dialogBody));
    }
}