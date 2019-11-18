package com.xyleme.bravais.web.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.xyleme.bravais.web.WebComponent;

public class Link extends WebComponent<Link> {

    public Link(WebDriver driver, By findByMethod) {
        super(driver, findByMethod);
    }

    public Boolean isDisplayed() {
        return getWebElement().isDisplayed();
    }

    public Boolean isActive(){
        return this.getAttribute("class").equals("active");
    }
}
