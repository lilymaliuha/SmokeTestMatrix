package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels;

import com.xyleme.bravais.datacontainers.customattributesdata.CABooleanTypeParameter;
import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.Select;
import com.xyleme.bravais.web.elements.TextInput;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSPanelOnDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.commentsblock.CommentsBlock;
import com.xyleme.bravais.web.pages.cds.functionalmobules.additemform.BaseAddItemForm;
import com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs.ClassificationElementDeletingDialog;
import com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs.CustomAttributeDeletingConfirmationDialog;
import com.xyleme.bravais.web.pages.cds.popupdialogs.nodeselectioninnodestreedialog.classificationselectiondialog.ManageClassificationDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of Properties pane on CDS Document Details page.
 */
public class PropertiesPane extends BaseCDSPanelOnDetailsPage {

    public PropertiesPane(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public PropertiesPane load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return propertiesParentContainer().isAvailable() &&
                addCustomAttributeButton().isAvailable() &&
                manageClassificationsButton().isAvailable();
    }

    private Element propertiesParentContainer() {
        return new Element(driver, By.id("properties-list"));
    }

    private Element customAttributesBlock() {
        return new Element(driver, By.xpath("//div[contains(@class, 'document-custom-attributes')]"));
    }

    private Button addCustomAttributeButton() {
        return new Button(driver, By.xpath(customAttributesBlock().getXpathLocatorValue() +
                "//button[normalize-space()='Add Custom Attribute']"));
    }

    private Element classificationsBlock() {
        return new Element(driver, By.xpath("//div[contains(@class, 'document-classifications')]"));
    }

    private Button manageClassificationsButton() {
        return new Button(driver, By.xpath(classificationsBlock().getXpathLocatorValue() +
                "//button[normalize-space()='Manage Classifications']"));
    }

    private Button documentAttributeDeleteButton(String attribute, String attributeType) {
        return new Button(driver, By.xpath("//table[@class='document-" + attributeType + "-list']//span[text()='" +
                attribute + "']/ancestor::tr//button[contains(@class, 'delete')]"));
    }

    /**
     * Clicks 'Mange Classifications' button and wait for 'Manage Classifications' dialog.
     *
     * @return {@code ManageClassificationDialog}
     */
    private ManageClassificationDialog clickManageClassificationsButton() {
        manageClassificationsButton().click();
        return new ManageClassificationDialog(driver);
    }

    /**
     * Assigns specified classification element to the document.
     *
     * @param pathToClassificationElement - Specifies path to the classification element in the classification tree
     *                                    intended to be selected
     * @return {@code PropertiesPane}
     */
    public PropertiesPane assignClassificationToDocument(String pathToClassificationElement) {
        ManageClassificationDialog manageClassificationDialog = clickManageClassificationsButton();
        return manageClassificationDialog.selectClassificationElement(pathToClassificationElement).save(PropertiesPane.class);
    }

    /**
     * Assigns specified Custom Attribute of boolean type to the document.
     *
     * @param customAttributeName - Specifies boolean type parameter (Yes or No)
     * @return {@code PropertiesPane}
     */
    public PropertiesPane assignCustomAttributeOfBooleanTypeToDocument(String customAttributeName,
                                                                       CABooleanTypeParameter booleanTypeParameter) {
        addCustomAttributeButton().waitUntilAvailable().click();
        return new AddCustomAttributeForm(driver).addCustomAttributeOfBooleanType(customAttributeName,
                booleanTypeParameter);
    }

    /**
     * Gets list of custom attribute elements (container with custom attribute name and delete button).
     *
     * @return {@code List<WebElement>}
     */
    private List<WebElement> getListOfDocumentCustomAttributeElements() {
        return driver.findElements(By.xpath("//table[@class='document-custom-attributes-list']/tbody/tr"));
    }

    /**
     * Gets list of classification elements (container with classification name and delete button).
     *
     * @return {@code List<WebElement>}
     */
    private List<WebElement> getListOfDocumentClassificationElements() {
        return getElementsDynamically(By.xpath("//table[@class='document-classifications-list']/tbody/tr"));
    }

    /**
     * Gets list of custom attributes assigned to the document.
     *
     * @return {@code List<String>}
     */
    public List<String> getListOfDocumentCustomAttributes() {
        return getListOfDocumentAttributes(getListOfDocumentCustomAttributeElements(), "custom attributes");
    }

    /**
     * Gets list of classifications assigned to the document.
     *
     * @return {@code List<String>}
     */
    public List<String> getListOfDocumentClassifications() {
        return getListOfDocumentAttributes(getListOfDocumentClassificationElements(), "classification");
    }

    /**
     * Gets list of attributes (classifications or custom attributes) assigned to the document.
     *
     * @param listOfElements - Specifies list of elements text values of which are expected to be returned
     * @param attributeType  - Specifies attribute type ('classification' or 'custom attribute')
     * @return {@code List<String>}
     */
    private List<String> getListOfDocumentAttributes(List<WebElement> listOfElements, String attributeType) {
        List<String> documentClassifications = new ArrayList<>();
        if (listOfElements.size() > 0) {
            for (WebElement element : listOfElements) {
                documentClassifications.add(getElementDynamically(element.findElement(By.xpath("./td/span"))).getText());
            }
        } else {
            System.out.println("Document doesn't have any assigned " + attributeType + "!");
        }
        return documentClassifications;
    }

    /**
     * Deletes specified custom attribute which was assigned to the document.
     *
     * @param customAttribute - Specifies the custom attribute intended to be deleted.
     * @return {@code PropertiesPane}
     */
    public PropertiesPane deleteDocumentCustomAttribute(String customAttribute) {
        documentAttributeDeleteButton(customAttribute, "custom-attributes").waitUntilAvailable().click();
        return new CustomAttributeDeletingConfirmationDialog(driver).confirmAction(PropertiesPane.class);
    }

    /**
     * Deletes specified classification which was assigned to the document.
     *
     * @param classification - Specifies the classification intended to be deleted.
     * @return {@code PropertiesPane}
     */
    public PropertiesPane deleteDocumentClassification(String classification) {
        documentAttributeDeleteButton(classification, "classifications").waitUntilAvailable().click();
        return new ClassificationElementDeletingDialog(driver).confirmAction(PropertiesPane.class);
    }

    /**
     * Checks if Comments block is available.
     *
     * @return {@code boolean}
     */
    public boolean isCommentsBlockAvailable() {
        return new CommentsBlock(driver, false).isAvailable();
    }

    /**
     * Implementation of Add Custom Attribute form which appears after clicking '+ Add Custom Attribute' button.
     */
    private class AddCustomAttributeForm extends BaseAddItemForm {

        AddCustomAttributeForm(WebDriver driver) {
            super(driver);
            this.waitUntilAvailable();
        }

        @Override
        public boolean isAvailable() {
            return super.isAvailable() &&
                    nameInputField().isAvailable();
        }

        @Override
        protected Element parentFormElement() {
            return new Element(driver, By.xpath("//form[@name='addDocumentCustomAttributeForm']"));
        }

        private TextInput nameInputField() {
            return new TextInput(driver, By.xpath(parentFormElement().getXpathLocatorValue() +
                    "//input[@id='document-custom-attribute-name']"));
        }

        private Select customAttributeValueDropDown() {
            return new Select(driver, By.xpath(parentFormElement().getXpathLocatorValue() +
                    "//select[@id='document-custom-attribute-value']"));
        }

        /**
         * Enters specified custom attribute name into the respective text input field and selects suggested match.
         *
         * @param customAttributeName - Specifies name of the custom attribute intended to be entered and selected
         * @return {@code AddCustomAttributeForm}
         */
        private AddCustomAttributeForm enterAndSelectCustomAttributeName(String customAttributeName) {
            String truncatedCustomAttributeName = customAttributeName.substring(0, customAttributeName.length() - 3); // ToDo: Remove this line after the issue is fixed.
            fillOutInputField(nameInputField(), truncatedCustomAttributeName); // ToDo: Workaround for issue #<TBD>. Use full value (customAttributeName) after the issue is fixed.
            selectSuggestedInputFieldMatch(customAttributeName);
            customAttributeValueDropDown().waitUntilAvailable();
            return this;
        }

        /**
         * Selects specified custom attribute value in the respective drop-down.
         *
         * @param parameter - Specifies the custom attribute value intended to be selected
         * @return {@code AddCustomAttributeForm}
         */
        private AddCustomAttributeForm selectCustomAttributeValue(CABooleanTypeParameter parameter) {
            if (customAttributeValueDropDown().isAvailable()) {
                customAttributeValueDropDown().selectItemByValue(parameter.getValue());
            } else {
                throw new RuntimeException("Custom Attribute value drop-down is not available in the form. Value " +
                        parameter.getValue() + " cannot be selected.");
            }
            return this;
        }

        /**
         * Adds specified custom attribute (of boolean type) with specified value to the document.
         *
         * @param customAttributeName  - Specifies custom attribute name
         * @param customAttributeValue - Specifies custom attribute value
         * @return {@code PropertiesPane}
         */
        private PropertiesPane addCustomAttributeOfBooleanType(String customAttributeName,
                                                              CABooleanTypeParameter customAttributeValue) {
            enterAndSelectCustomAttributeName(customAttributeName);
            selectCustomAttributeValue(customAttributeValue);
            return clickAddButtonWithHandlingItemCreationNotificationMessage(PropertiesPane.class);
        }
    }
}