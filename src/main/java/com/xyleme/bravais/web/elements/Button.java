package com.xyleme.bravais.web.elements;

import com.xyleme.bravais.web.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Button extends WebComponent<Button> {

    public Button(WebDriver driver, By findByMethod) {
        super(driver, findByMethod);
    }

    public Boolean isDisplayed() {
        return getWebElement().isDisplayed();
    }
}
