package com.xyleme.bravais.web.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;

import com.xyleme.bravais.web.WebComponent;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TextInput extends WebComponent<TextInput> {

    public TextInput(WebDriver driver, By findByMethod) {
        super(driver, findByMethod);
    }

    public TextInput inputText(String text) {
        getWebElement().sendKeys(text);
        return this;
    }

    public String readInputText() {
        return getWebElement().getText();
    }

    public String getValue(){
        return getWebElement().getAttribute("value");
    }

    public TextInput clear() {
        new WebDriverWait(driver, 30).ignoring(InvalidElementStateException.class)
                .until(ExpectedConditions.visibilityOf(getWebElement())).clear();
        return this;
    }

    public Boolean isDisplayed() {
        return getWebElement().isDisplayed();
    }
}