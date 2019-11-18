package com.xyleme.bravais.web.elements;

import com.xyleme.bravais.web.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Image extends WebComponent<Image> {
    public Image(WebDriver driver, By findByMethod) {
        super(driver, findByMethod);
    }

    public int pictureWidth(){
        return getWebElement().getSize().getWidth();
    }

    public int pictureHeight(){
        return getWebElement().getSize().getHeight();
    }
}
