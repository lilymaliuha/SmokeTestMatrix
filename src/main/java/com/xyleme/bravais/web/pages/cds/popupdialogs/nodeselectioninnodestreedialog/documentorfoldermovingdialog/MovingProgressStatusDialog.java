package com.xyleme.bravais.web.pages.cds.popupdialogs.nodeselectioninnodestreedialog.documentorfoldermovingdialog;

import com.xyleme.bravais.web.WebPage;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSDocumentsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Implementation of the dialog which appears after selecting target folder and clicking 'Move' button on the Move To
 * dialog == dialog which indicates status of moving process progress.
 */
public class MovingProgressStatusDialog extends WebPage<MovingProgressStatusDialog> {

    MovingProgressStatusDialog(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public MovingProgressStatusDialog load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return parentDialogElement().isAvailable();
    }

    private Element parentDialogElement() {
        return new Element(driver, By.xpath("//div[@id='move-dialog']/div[@class='modal-dialog']"));
    }

    private Element dialogProgressBar(int progressCompleteness) {
        return new Element(driver, By.xpath(parentDialogElement().getXpathLocatorValue() +
                "//div[contains(@class, 'progress-bar') and contains(@style, '" + progressCompleteness +
                "%')]/parent::div[contains(@class, 'progress-striped')]"));
    }

    /**
     * Waits until process of item moving is completed.
     *
     * @return {@code CDSDocumentsPage}
     */
    CDSDocumentsPage waitUntilItemIsMoved() {
        dialogProgressBar(100).waitUntilAvailableFor(60);
        if (this.isAvailable()) {
            new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath(parentDialogElement().getXpathLocatorValue())));
        }
        return new CDSDocumentsPage(driver);
    }
}