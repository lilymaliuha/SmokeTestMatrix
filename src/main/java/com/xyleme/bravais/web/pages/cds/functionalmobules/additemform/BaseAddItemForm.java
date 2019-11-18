package com.xyleme.bravais.web.pages.cds.functionalmobules.additemform;

import com.xyleme.bravais.web.WebPage;
import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.TextInput;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.xyleme.bravais.BaseTest.staticSleep;
import static com.xyleme.bravais.DriverMaster.remoteExecution;

/**
 * Implementation of a base Add Item Form.
 */
public abstract class BaseAddItemForm extends WebPage<BaseAddItemForm> {

    public BaseAddItemForm(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAvailable() {
        return parentFormElement().isAvailable() &&
                addButton().isAvailable() &&
                cancelButton().isAvailable();
    }

    @Override
    public BaseAddItemForm load() {
        return this;
    }

    protected abstract Element parentFormElement();

    private Button addButton() {
        return new Button(driver, By.xpath(parentFormElement().getXpathLocatorValue() +
                "//span[text()='Add']/parent::button"));
    }

    private Button cancelButton() {
        return new Button(driver, By.xpath(parentFormElement().getXpathLocatorValue() + "//button[text()='Cancel']"));
    }

    /**
     * Fills out specified input field with specified value.
     *
     * @param inputField - Specifies the input field intended to be filled out
     * @param inputValue - Specifies the value intended to be entered into the input field
     */
    protected void fillOutInputField(TextInput inputField, String inputValue) {
        inputField.waitUntilAvailable().clear();
        inputField.sendKeys(inputValue);
    }

    /**
     * Clicks Add button and returns instance of specified page/form.
     *
     * @param objectToReturn - Specifies class of a page or form expected to be returned after clicking the button
     * @return {@code <T extends BaseCDSPageHeader>}
     */
    protected <T extends WebPage> T clickAddButton(Class<T> objectToReturn) {
        addButton().click();
        return constructClassInstance(objectToReturn);
    }

    /**
     * Clicks Add button, handles item creation notification message, and returns instance of specified page/form.
     *
     * @param objectToReturn - Specifies class of a page or form expected to be returned after clicking the button
     * @return {@code <T extends WebPage>}
     */
    protected <T extends WebPage> T clickAddButtonWithHandlingItemCreationNotificationMessage(
            Class<T> objectToReturn) {
        addButton().click();
        handleItemCreationNotificationMessage();
        return constructClassInstance(objectToReturn);
    }

    /**
     * Clicks Cancel button and returns instance of specified page/form.
     *
     * @param objectToReturn - Specifies class of a page or form expected to be returned after clicking the button
     * @return {@code <T extends WebPage>}
     */
    protected <T extends WebPage> T clickCancelButton(Class<T> objectToReturn) {
        cancelButton().click();
        return constructClassInstance(objectToReturn);
    }

    /**
     * Selects suggested input field match which conforms to the expected specified value.
     *
     * @param valueToMatch - Specifies the value expected to be among the suggested options (expected to be selected)
     */
    protected void selectSuggestedInputFieldMatch(String valueToMatch) {
        Element expectedMatch = new Element(driver, By.xpath(parentFormElement().getXpathLocatorValue() +
                "//span[text()='" + valueToMatch + "']/ancestor::li[@role='option']"));
        expectedMatch.waitUntilAvailable().click();
    }

    /**
     * Waits until notification popup message disappears.
     */
    private void waitUntilNotificationPopupMessageDisappears() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath(notificationMessage().getXpathLocatorValue())));
    }

    /**
     * Waits until pop-up message which informs user about the result of an item creation action appears and then disappears.
     */
    private void handleItemCreationNotificationMessage() {
        if (remoteExecution) {
            staticSleep(1);

            if (notificationMessage().isAvailable()) {
                waitUntilNotificationPopupMessageDisappears();
            }
        } else {
            notificationMessage().waitUntilAvailable();
            waitUntilNotificationPopupMessageDisappears();
        }
    }
}