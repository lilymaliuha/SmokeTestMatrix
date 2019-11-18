package com.xyleme.bravais.web.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.xyleme.bravais.web.WebComponent;

public class CheckBox extends WebComponent<CheckBox> {

    public CheckBox(WebDriver driver, By findByMethod) {
        super(driver, findByMethod);
    }

    public Boolean isSelected() {
       return getWebElement().isSelected();
    }

    public void select() {
        if (!isSelected()) {
            click();
        } else {
            System.out.println("Checkbox is already selected.");
        }
    }

    public void unselect() {
        if (isSelected()) {
            click();
        } else {
            System.out.println("Checkbox is already unselected.");
        }
    }
}