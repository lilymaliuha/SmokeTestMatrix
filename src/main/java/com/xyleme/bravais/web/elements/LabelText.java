package com.xyleme.bravais.web.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.xyleme.bravais.web.WebComponent;

public class LabelText extends WebComponent<LabelText> {

    public LabelText(WebDriver driver, By findByMethod) {
        super(driver, findByMethod);
    }

    public String readLabelText() {
        return getWebElement().getText();
    }

    public int size() {
        return readLabelText().length();
    }

    public Boolean isDisplayed() {
        return getWebElement().isDisplayed();
    }

    public LabelText clear() {
        getWebElement().clear();
        return this;
    }
}
