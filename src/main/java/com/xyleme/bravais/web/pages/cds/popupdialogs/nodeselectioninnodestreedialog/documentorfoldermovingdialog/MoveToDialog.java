package com.xyleme.bravais.web.pages.cds.popupdialogs.nodeselectioninnodestreedialog.documentorfoldermovingdialog;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSDocumentsPage;
import com.xyleme.bravais.web.pages.cds.popupdialogs.nodeselectioninnodestreedialog.BaseNodeSelectionDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of the Move Document To... dialog (dialog which opens on attempting to move an item of Documents
 * table (document or folder).
 */
public class MoveToDialog extends BaseNodeSelectionDialog {

    public MoveToDialog(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public MoveToDialog load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                moveButton().isAvailable();
    }

    @Override
    protected Element dialogParentElement() {
        return new Element(driver, By.xpath("//div[@id='move-dialog']//div[@class='modal-dialog']"));
    }

    private Button moveButton() {
        return dialogFooterButton("Move");
    }

    /**
     * Selects specified target folder.
     *
     * @param targetFolderPath - Specifies path to the target folder
     * @return {@code MoveToDialog}
     */
    public MoveToDialog selectTargetFolder(String targetFolderPath) {
        return selectTargetNode(targetFolderPath, MoveToDialog.class);
    }

    /**
     * Checks if 'Move' button is enabled, if so - clicks the button and waits until the moving process completes.
     *
     * @return {@code CDSDocumentsPage}
     */
    public CDSDocumentsPage move() {
        if (!moveButton().isDisabled()) {
            moveButton().click();
            return new MovingProgressStatusDialog(driver).waitUntilItemIsMoved();
        } else {
            throw new RuntimeException("'Move' button is disabled, the item cannot be moved!");
        }
    }
}