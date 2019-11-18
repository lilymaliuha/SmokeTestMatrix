package com.xyleme.bravais.web.pages.cds.popupdialogs.nodeselectioninnodestreedialog.classificationselectiondialog;

import com.xyleme.bravais.web.WebPage;
import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.pages.cds.popupdialogs.nodeselectioninnodestreedialog.BaseNodeSelectionDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.xyleme.bravais.BaseTest.staticSleep;

/**
 * Implementation of Manage Classifications dialog (the dialog which opens after clicking 'Manage Classifications' button
 * on the Properties pane of a document (document detains page)).
 */
public class ManageClassificationDialog extends BaseNodeSelectionDialog {

    public ManageClassificationDialog(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public ManageClassificationDialog load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                saveButton().isAvailable();
    }

    @Override
    protected Element dialogParentElement() {
        return new Element(driver, By.xpath("//div[@id='classifications-dialog']//div[@class='modal-dialog']"));
    }

    private Button saveButton() {
        return dialogFooterButton("Save");
    }

    /**
     * Selects specified classification element
     *
     * @param pathToTargetClassificationElement - Specifies path to the classification element intended to be selected
     * @return {@code ManageClassificationDialog}
     */
    public ManageClassificationDialog selectClassificationElement(String pathToTargetClassificationElement) {
        return selectTargetNode(pathToTargetClassificationElement, ManageClassificationDialog.class);
    }

    /**
     * Checks if 'Save' button is enabled, if so - clicks the button.
     *
     * @param pageToReturn - Specifies class of the page instance of which is intended to be returned after clicking the
     *                    button
     * @return {@code PropertiesPane}
     */
    public <T extends WebPage> T save(Class<T> pageToReturn) {
        if (!saveButton().isDisabled()) {
            saveButton().click();
            new WebDriverWait(driver, 5).until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath(dialogParentElement().getXpathLocatorValue())));
            staticSleep(1);
            return constructClassInstance(pageToReturn);
        } else {
            throw new RuntimeException("'Move' button is disabled, the item cannot be moved!");
        }
    }
}