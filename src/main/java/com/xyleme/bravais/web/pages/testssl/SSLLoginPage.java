package com.xyleme.bravais.web.pages.testssl;

import com.xyleme.bravais.web.WebPage;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.TextInput;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SSLLoginPage extends WebPage<SSLLoginPage> {

    public SSLLoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAvailable() {
        return inputField().isAvailable();
    }

    @Override
    public SSLLoginPage load() {
        return this;
    }

    private TextInput inputField() {
        return new TextInput(driver, By.xpath("//input[@id='LoginFormModel_password']"));
    }

    private Element loginForm() {
        return new Element(driver, By.xpath("//form[@name='frmLogin']"));
    }

    public void inputId() {
        loginForm().isAvailable();
        staticSleep(10);
        inputField().sendKeys("111111");
    }

    public static void staticSleep(double sleepInSeconds) {
        try {
            Thread.sleep((int) (sleepInSeconds * 1000));
        } catch (InterruptedException ignored) {}
    }
}
