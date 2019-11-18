package com.xyleme.bravais.web.pages.cds.popupdialogs.infodialogs;

import com.xyleme.bravais.web.WebPage;
import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.LabelText;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsclassificationspage.CDSClassificationsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Implementation of a pop-up dialog which appears after importing a classification (Info dialog).
 */
public class ClassificationUploadingInfoDialog extends WebPage<ClassificationUploadingInfoDialog> {

    public ClassificationUploadingInfoDialog(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return dialogHeader().isAvailable() &&
                closeButtonInDialogHeader().isAvailable() &&
                dialogBody().isAvailable() &&
                dialogFooter().isAvailable() &&
                closeButtonInDialogFooter().isAvailable();
    }

    @Override
    public ClassificationUploadingInfoDialog load() {
        return this;
    }

    private Element dialogParentElement() {
        return new Element(driver, By.xpath("//div[contains(@class, 'info-dialog')]/div[@class='modal-dialog']"));
    }

    private Element dialogStructureElement(String structureElement) {
        return new Element(driver, By.xpath(dialogParentElement().getXpathLocatorValue() +
                "//div[contains(@class, 'modal-" + structureElement + "')]"));
    }

    private Element dialogHeader() {
        return dialogStructureElement("header");
    }

    private Element dialogBody() {
        return dialogStructureElement("body");
    }

    private Element dialogFooter() {
        return dialogStructureElement("footer");
    }

    private Button closeButtonInDialogHeader() {
        return new Button(driver, By.xpath(dialogHeader().getXpathLocatorValue() + "//button[@class='close']"));
    }

    private Button closeButtonInDialogFooter() {
        return new Button(driver, By.xpath(dialogFooter().getXpathLocatorValue() + "//button[text()='Close']"));
    }

    /**
     * Gets text of dialog message.
     *
     * @return {@code String}
     */
    public String getDialogMessage() {
        return new LabelText(driver, By.xpath(dialogBody().getXpathLocatorValue() + "/small")).waitUntilAvailable().getText();
    }

    /**
     * Clicks Close button in the dialog footer and waits until the dialog disappears.
     *
     * @return {@code CDSClassificationsPage}
     */
    public CDSClassificationsPage closeDialog() {
        closeButtonInDialogFooter().click();
        new WebDriverWait(driver, 5).until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath(dialogParentElement().getXpathLocatorValue())));
        return new CDSClassificationsPage(driver);
    }
}
