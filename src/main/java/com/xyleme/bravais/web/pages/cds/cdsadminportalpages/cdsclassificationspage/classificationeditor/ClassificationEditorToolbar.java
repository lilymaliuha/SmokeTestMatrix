package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsclassificationspage.classificationeditor;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsclassificationspage.CDSClassificationsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsclassificationspage.pageforms.AddChildClassificationElementForm;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsclassificationspage.pageforms.AddSiblingClassificationElementForm;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsclassificationspage.pageforms.EditClassificationElementForm;
import com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs.ClassificationElementDeletingDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Classification Editor Toolbar available on Classifications page when at least one classification is
 * available on the page.
 */
public class ClassificationEditorToolbar extends CDSClassificationsPage {

    ClassificationEditorToolbar(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAvailable() {
        return addChildButton().isAvailable() &&
                addSiblingButton().isAvailable() &&
                editButton().isAvailable() &&
                removeButton().isAvailable();
    }

    private Button toolBarButton(String buttonName) {
        return new Button(driver, By.xpath("//div[@class='btn-toolbar']//button[normalize-space()='" + buttonName + "']"));
    }

    private Button addChildButton() {
        return toolBarButton("Add Child");
    }

    private Button addSiblingButton() {
        return toolBarButton("Add Sibling");
    }

    private Button editButton() {
        return toolBarButton("Edit");
    }

    private Button removeButton() {
        return toolBarButton("Remove");
    }

    /**
     * Clicks Add Child button.
     *
     * @return {@code AddChildClassificationElementForm}
     */
    public AddChildClassificationElementForm clickAddChildButton() {
        addChildButton().click();
        return new AddChildClassificationElementForm(driver);
    }

    /**
     * Clicks Add Sibling button and returns Add Sibling Classification Form.
     *
     * @return {@code AddSiblingClassificationElementForm}
     */
    public AddSiblingClassificationElementForm clickAddSiblingButton() {
        addSiblingButton().click();
        return new AddSiblingClassificationElementForm(driver);
    }

    /**
     * Clicks Edit button and returns Classification Edit Form.
     *
     * @return {@code EditClassificationElementForm}
     */
    public EditClassificationElementForm clickEditButton() {
        editButton().click();
        return new EditClassificationElementForm(driver);
    }

    /**
     * Clicks Remove button and returns Classification Deleting Dialog.
     *
     * @return {@code ClassificationElementDeletingDialog}
     */
    public ClassificationElementDeletingDialog clickRemoveButton() {
        removeButton().click();
        return new ClassificationElementDeletingDialog(driver);
    }
}