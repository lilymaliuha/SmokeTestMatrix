package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsclassificationspage;

import com.xyleme.bravais.datacontainers.ClassificationType;
import com.xyleme.bravais.web.elements.*;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSPageHeader;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsclassificationspage.classificationeditor.ClassificationEditorBlock;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsclassificationspage.pageforms.AddChildClassificationElementForm;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsclassificationspage.pageforms.AddRootClassificationElementForm;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsclassificationspage.pageforms.AddSiblingClassificationElementForm;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsclassificationspage.pageforms.EditClassificationElementForm;
import com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs.ClassificationElementDeletingDialog;
import com.xyleme.bravais.web.pages.cds.popupdialogs.uploadingdialog.ClassificationImportDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.xyleme.bravais.DriverMaster.pathToMMLForUploadingFolder;

/**
 * Implementation of CDS Classifications page.
 */
public class CDSClassificationsPage extends BaseCDSPageHeader {

    public CDSClassificationsPage(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                pageHeading().isAvailable() &&
                importButton().isAvailable() &&
                deleteTaxonsNotPresentInImportedFileCheckbox().isAvailable();
    }

    private LabelText pageHeading() {
        return new LabelText(driver, By.xpath("//div[@id='page-content']//h1"));
    }

    private Button importButton() {
        return new Button(driver, By.xpath("//span[normalize-space()='Import']"));
    }

    private Element importButtonInput() {
        return new Element(driver, By.xpath(importButton().getXpathLocatorValue() + "//input"));
    }

    private Element deleteTaxonsNotPresentInImportedFileCheckbox() {
        return new Element(driver, By.xpath("//div[normalize-space()='Delete taxons not present in the imported file']" +
                "//input"));
    }

    /**
     * Imports specified classification (MML file).
     *
     * @param mml - Specifies the MML file (== classification) intended to be imported
     * @return {@code CDSClassificationsPage}
     */
    public CDSClassificationsPage importClassification(String mml) {
        importButtonInput().sendKeys(pathToMMLForUploadingFolder + mml);
        return new ClassificationImportDialog(driver).completeImport();
    }

    /**
     * Gets Add Root Classification form (if available on the page).
     *
     * @return {@code AddRootClassificationElementForm}
     */
    private AddRootClassificationElementForm getAddRootClassificationElementForm() {
        if (new AddRootClassificationElementForm(driver, false).isAvailable()) {
            return new AddRootClassificationElementForm(driver);
        } else {
            throw new RuntimeException("'Add Root Classification' form is not available on the Classifications page!");
        }
    }

    /**
     * Adds root classification element with specified parameters.
     *
     * @param vocabID            - Specifies element Vocab ID
     * @param name               - Specifies element Name
     * @param classificationType - Specifies classification type
     * @return {@code CDSClassificationsPage}
     */
    public CDSClassificationsPage addRootClassificationElement(String vocabID,
                                                               String name, ClassificationType classificationType) {
        return getAddRootClassificationElementForm().addRootClassificationElement(vocabID, name, classificationType);
    }

    /**
     * Gets Classification Editor Block (if available on the page).
     *
     * @return {@code ClassificationEditorBlock}
     */
    public ClassificationEditorBlock getClassificationEditorBlock() {
        if (new ClassificationEditorBlock(driver, false).isAvailable()) {
            return new ClassificationEditorBlock(driver);
        } else {
            throw new RuntimeException("Classification Editor Block is not available on the Classifications page!");
        }
    }

    /**
     * Adds child element with specified parameters to a specified classification node.
     *
     * @param pathToParentElement - Specifies path to the classification element the child element is intended to be
     *                            added to (path format: nodeA|nodeAA|nodeAAA)
     * @param vocabID             - Specifies Vocab ID of the child element intended to be added
     * @param name                - Specifies Name of the child element intended to be added
     * @return {@code CDSClassificationsPage}
     */
    public CDSClassificationsPage addChildElementToClassification(String pathToParentElement, String vocabID,
                                                                  String name) {
       AddChildClassificationElementForm addChildClassificationElementForm = getClassificationEditorBlock()
               .selectClassificationNode(pathToParentElement).clickAddChildButton();
       return addChildClassificationElementForm.addChildClassificationElement(vocabID, name);
    }

    /**
     * Adds sibling element with specified parameters to a specified classification node.
     *
     * @param pathToParentElement - Specifies path to the classification element the sibling element is intended to be
     *                            added to (path format: nodeA|nodeAA|nodeAAA)
     * @param vocabID             - Specifies Vocab ID of the sibling element intended to be added
     * @param name                - Specifies Name of the sibling element intended to be added
     * @return {@code CDSClassificationsPage}
     */
    public CDSClassificationsPage addSiblingElementToClassification(String pathToParentElement, String vocabID,
                                                                    String name) {
        AddSiblingClassificationElementForm addSiblingClassificationElementForm = getClassificationEditorBlock()
                .selectClassificationNode(pathToParentElement).clickAddSiblingButton();
        return addSiblingClassificationElementForm.addSiblingClassificationElement(vocabID, name);
    }

    /**
     * Edits specified classification element with specified parameters.
     *
     * @param pathToElement          - Specifies path to the classification element intended to be edited (path format:
     *                               nodeA|nodeAA|nodeAAA)
     * @param newVocabID             - Specifies new Vocab ID intended to be set for the element
     * @param newName                - Specifies new Name intended to be set for the element
     * @param newClassificationType  - Specifies new classification type intended to be set for the element
     * @return {@code CDSClassificationsPage}
     */
    public CDSClassificationsPage editClassificationElement(String pathToElement, String newVocabID,
                                                            String newName, ClassificationType newClassificationType) {
        EditClassificationElementForm editClassificationElementForm = getClassificationEditorBlock()
                .selectClassificationNode(pathToElement).clickEditButton();
        return editClassificationElementForm.editClassificationElement(newVocabID, newName, newClassificationType);
    }

    /**
     * Removes specified classification element.
     *
     * @param pathToElement - Specifies path to the classification element intended to be removed (path format:
     *                      nodeA|nodeAA|nodeAAA).
     * @return {@code CDSClassificationsPage}
     */
    public CDSClassificationsPage removeClassificationElement(String pathToElement) {
        ClassificationElementDeletingDialog classificationElementDeletingDialog = getClassificationEditorBlock()
                .selectClassificationNode(pathToElement).clickRemoveButton();
        return classificationElementDeletingDialog.confirmAction(CDSClassificationsPage.class);
    }
}