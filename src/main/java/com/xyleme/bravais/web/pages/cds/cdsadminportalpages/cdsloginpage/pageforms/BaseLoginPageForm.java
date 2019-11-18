package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsloginpage.pageforms;

import com.xyleme.bravais.web.WebPage;
import com.xyleme.bravais.web.elements.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Base Form of CDS Login page (common for 'Sign In', 'Forgot Password', and 'Reset Password' forms).
 */
public abstract class BaseLoginPageForm extends WebPage<BaseLoginPageForm> {
     private String formTitle;

    protected BaseLoginPageForm(WebDriver driver, String formTitle) {
        super(driver);
        this.formTitle = formTitle;
    }

    @Override
    public boolean isAvailable() {
        return formBody().isAvailable();
    }

    @Override
    public BaseLoginPageForm load() {
        return this;
    }

    private Element formBody() {
        return new Element(driver, By.xpath("//h3[text()='" + formTitle + "']/parent::form"));
    }

    protected TextInput formInputField(String inputNameAttributeValue) {
        return new TextInput(driver, By.xpath(formBody().getXpathLocatorValue() + "//input[@name='"
                + inputNameAttributeValue + "']"));
    }

    protected Link formLink(String linkName) {
        return new Link(driver, By.xpath("//a[normalize-space()=\"" + linkName + "\"]"));
    }

    protected Button formButton(String buttonName) {
        return new Button(driver, By.xpath(formBody().getXpathLocatorValue() + "//button[text()='" + buttonName + "']"));
    }

    LabelText instructionMessageLabel() {
        return new LabelText(driver, By.xpath(formBody().getXpathLocatorValue() + "/p"));
    }
}