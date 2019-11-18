package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdscustomattributespage.attributecreationforms;

import com.xyleme.bravais.web.elements.*;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdscustomattributespage.CDSCustomAttributesPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdscustomattributespage.attributecreationforms.attributetypes.BaseAttributeType;
import com.xyleme.bravais.web.pages.cds.functionalmobules.additemform.BaseAddItemForm;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of a base attribute creation form.
 */
public abstract class BaseAttributeCreationForm extends BaseAddItemForm {

    public BaseAttributeCreationForm(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                nameInputField().isAvailable() &&
                searchCheckbox().isAvailable() &&
                attributeTypeDropDown().isAvailable();
    }

    @Override
    protected Element parentFormElement() {
        return new Element(driver, By.xpath("//form[@name='addAttributeForm']"));
    }

    private TextInput nameInputField() {
        return new TextInput(driver, By.id("new-attribute-name"));
    }

    private CheckBox searchCheckbox() {
        return new CheckBox(driver, By.id("new-attribute-search"));
    }

    private Select attributeTypeDropDown() {
        return new Select(driver, By.id("new-attribute-type"));
    }

    /**
     * Enters specified attribute name into the respective input field.
     *
     * @param attributeName - Specifies attribute name
     */
    private void enterAttributeName(String attributeName) {
        fillOutInputField(nameInputField(), attributeName);
    }

    /**
     * Checks Search checkbox.
     */
    private void checkSearchCheckbox() {
        searchCheckbox().waitUntilAvailable().select();
    }

    /**
     * Enters specified attribute name and checks (if search parameter is true) Search checkbox.
     *
     * @param attributeName - Specifies attribute name
     * @param search        - Specifies the decision whether to check Search checkbox or not
     */
    public void enterAttributeNameAndSetSearchParameter(String attributeName, boolean search) {
        enterAttributeName(attributeName);
        if (search) {
            checkSearchCheckbox();
        }
    }

    /**
     * Selects specified attribute type.
     *
     * @param attributeType - Specifies attribute type
     * @return {@code <T extends BaseAttributeType>}
     */
    public <T extends BaseAttributeType> T selectAttributeType(Class<T> attributeType) {
        String type = "";
        switch (attributeType.getSimpleName()) {
            case "BooleanAttributeType":
                type = "boolean";
                break;
            case "ChoiceAttributeType":
                type = "choice";
                break;
            case "StringAttributeType":
                type = "string";
                break;
        }
        attributeTypeDropDown().waitUntilAvailable().selectItemByValue(type);
        return constructClassInstance(attributeType);
    }

    /**
     * Clicks Add button and handles item creation notification message.
     *
     * @return {@code CDSCustomAttributesPage}
     */
    public CDSCustomAttributesPage clickAddButton() {
        return clickAddButtonWithHandlingItemCreationNotificationMessage(CDSCustomAttributesPage.class);
    }
}