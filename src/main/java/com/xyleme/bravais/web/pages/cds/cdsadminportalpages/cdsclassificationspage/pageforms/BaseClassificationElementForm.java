package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsclassificationspage.pageforms;

import com.xyleme.bravais.datacontainers.ClassificationType;
import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.Select;
import com.xyleme.bravais.web.elements.TextInput;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsclassificationspage.CDSClassificationsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.xyleme.bravais.BaseTest.staticSleep;

/**
 * Implementation of Base Classification Element Form.
 */
public abstract class BaseClassificationElementForm extends CDSClassificationsPage {

    BaseClassificationElementForm(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAvailable() {
        return vocabIdInputField().isAvailable() &&
                nameInputField().isAvailable() &&
                classificationTypeDropDown().isAvailable() &&
                submissionButton().isAvailable();
    }

    private Element formBody() {
        return new Element(driver, By.xpath(
                "//form[contains(@class, 'classification-form') and not(contains(@class, 'hide'))]"));
    }

    private TextInput formInputField(String inputFieldPlaceholder) {
        return new TextInput(driver, By.xpath(formBody().getXpathLocatorValue() + "//input[@placeholder='" +
                inputFieldPlaceholder +"']"));
    }

    private TextInput vocabIdInputField() {
        return formInputField("Vocab ID");
    }

    private TextInput nameInputField() {
        return formInputField("Name");
    }

    Button formButton(String buttonName) {
        return new Button(driver, By.xpath(formBody().getXpathLocatorValue() + "//button[normalize-space()='" +
                buttonName + "']"));
    }

    private Select classificationTypeDropDown() {
        return new Select(driver, By.xpath(formBody().getXpathLocatorValue() + "//select[contains(@id, 'type')]"));
    }

    abstract Button submissionButton();

    /**
     * Enters specified Vocab ID into the respective input field.
     *
     * @param vocabID - Specifies the Vocab ID intended to be entered
     */
    private void enterVocabID(String vocabID) {
        vocabIdInputField().clear();
        staticSleep(0.5);
        vocabIdInputField().sendKeys(vocabID);
    }

    /**
     * Enters specified Name into the respective input field.
     *
     * @param name - Specifies the Name intended to be entered
     */
    private void enterName(String name) {
        nameInputField().clear();
        staticSleep(0.5);
        nameInputField().sendKeys(name);
    }

    /**
     * Clicks a submission button.
     */
    private void clickSubmissionButton() {
        submissionButton().waitUntilAvailable().click();
        notificationMessage().waitUntilAvailable();
        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath(notificationMessage().getXpathLocatorValue())));
    }

    /**
     * Selects specified classification type in the Classification Type drop-down.
     *
     * @param type - Specifies the type intended to be selected
     */
    private void selectClassificationType(ClassificationType type) {
        classificationTypeDropDown().selectItemByValue(type.getValue());
    }

    /**
     * Fills out a form (with not editable Classification type drop-down field) with the specified parameters and click
     * submission button.
     *
     * @param vocabId            - Specifies classification Vocab ID
     * @param name               - Specifies classification Name
     * @return {@code CDSClassificationsPage}
     */
    CDSClassificationsPage fillOutFormWithNotEditableClassificationTypeAndClickSubmissionButton(String vocabId,
                                                                                                String name) {
        enterVocabID(vocabId);
        enterName(name);
        clickSubmissionButton();
        return new CDSClassificationsPage(driver);
    }

    /**
     * Fills out a form with the specified parameters and click submission button.
     *
     * @param vocabId            - Specifies classification Vocab ID
     * @param name               - Specifies classification Name
     * @param classificationType - Specifies classification type
     * @return {@code CDSClassificationsPage}
     */
    CDSClassificationsPage fillOutFormAndClickSubmissionButton(String vocabId,
                                                                  String name, ClassificationType classificationType) {
        enterVocabID(vocabId);
        enterName(name);
        selectClassificationType(classificationType);
        clickSubmissionButton();
        return new CDSClassificationsPage(driver);
    }
}