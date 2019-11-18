package com.xyleme.bravais.web;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.Arrays;

import static com.xyleme.bravais.BaseTest.staticSleep;

public abstract class WebComponent<T extends WebComponent<T>> extends Component<T> {
    protected final By findByMethod;

    public WebComponent(WebDriver driver, By findByMethod) {
        super(driver);
        this.findByMethod = findByMethod;
    }

    @Override
    public boolean isAvailable() {

        try {
            return getWebElement().isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            System.out.println("Element is no longer attached to the DOM");
            return false;
        }
    }

    public Boolean isActive(){
        return getWebElement().getAttribute("disabled") == null;
    }

    public void click()  {
        getWebElement().click();
    }

    /**
     * Clicks element using JavaScript.
     */
    private void jsClick() {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].click();", getWebElement());
    }

    /**
     * Clicks element using JavaScript with checking if it's visible. If the element is not visible the method scrolls
     * to the element and then clicks it.
     */
    public void clickUsingJS() {

        try {
            jsClick();
        } catch (WebDriverException e) {

            if (e.toString().contains("is not clickable at point")) {
                scrollToThisElementUsingJS();
                jsClick();
            } else {
                throw e;
            }
        }
    }

    public String getText() {
        return getWebElement().getText();
    }

    public Boolean isDisabled() {
        if ((getWebElement().getAttribute("disabled") != null))
            return (getWebElement().getAttribute("disabled") != null);
        else return getWebElement().getAttribute("class").contains("Disabled");
    }

    public void sendKeys(CharSequence... arg0) {
        getWebElement().sendKeys(arg0);
    }

    public void senKeysCharByChar(CharSequence... arg0) {
        String value = Arrays.toString(arg0).replaceAll("\\[","").replaceAll("]","");

        for (int i = 0; i < value.length(); i++) {
            getWebElement().sendKeys(String.valueOf(value.charAt(i)));
            staticSleep(0.3);
        }
    }

    public String getAttribute(String atr) {
        return getWebElement().getAttribute(atr);
    }

    protected WebElement getWebElement() {
        return driver.findElement(findByMethod);
    }

    public void scrollToThisElement() {
        Actions actions = new Actions(driver);
        actions.moveToElement(getWebElement());
        actions.perform();
    }

    /**
     * Hovers over element.
     */
    public void hoverOver() {
        new Actions(driver).moveToElement(getWebElement()).build().perform();
    }

    /**
     * Hovers over element and clicks it.
     */
    public void hoverOverAndClick() {
        new Actions(driver).moveToElement(getWebElement()).click().build().perform();
    }

    /**
     * Scrolls to a specific element using JavaScript.
     */
    private void scrollToThisElementUsingJS() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", getWebElement());
    }

    /**
     * Retrieves value of specified XPATH locator.
     *
     * @return {@code String}
     */
    public String getXpathLocatorValue() {
        String stringValueOfLocator = findByMethod.toString();

        if (stringValueOfLocator.contains("By.xpath")) {
            return stringValueOfLocator.replace("By.xpath: ","");
        } else {
            throw new RuntimeException("Provided locator is not of XPATH type. The method doesn't accept other types of locators!");
        }
    }
}