package com.xyleme.bravais.web;

import com.xyleme.bravais.Configuration;
import com.xyleme.bravais.Environment;
import com.xyleme.bravais.web.elements.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.datatransfer.*;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.xyleme.bravais.BaseTest.staticSleep;
import static com.xyleme.bravais.Configuration.getConfig;

public abstract class WebPage<T extends WebPage<T>> extends Component<T> implements ClipboardOwner {
    private static final Configuration CONFIG = getConfig();
    public static final Environment ENVIRONMENT = CONFIG.getEnvironmentSettings();

    public WebPage(WebDriver driver) {
        super(driver);
    }

    public abstract T load();

    protected T loadAndWaitUntilAvailable() {
        load();
        return waitUntilAvailable();
    }

    protected LabelText footerLabelWithBuildNumber(){
        return new LabelText(driver, By.xpath("//footer//small"));
    }

    protected Element notificationMessage() {
        return new Element(driver, By.xpath("//div[contains(@class, 'cg-notify-message')]"));
    }

    /**
     * Gets text located in a page footer.
     *
     * @return {@code String}
     */
    public String getFooterContent() {
        return footerLabelWithBuildNumber().waitUntilAvailable().getText();
    }

    /**
     * Waits for Angular JS processing.
     *
     * @return {@code boolean}
     */
    protected boolean waitForAngularJSProcessing() {
        staticSleep(0.5);
        int timeOutInSeconds = 20;
        boolean jQcondition = false;

        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            new WebDriverWait(driver, timeOutInSeconds) {}
            .until((ExpectedCondition<Boolean>) driver -> Boolean.valueOf(((JavascriptExecutor) driver).executeScript(
                    "return window.angular!= undefined && angular.element(document.getElementsByTagName('body'))" +
                            ".injector().get('$http').pendingRequests.length === 0").toString()));
            jQcondition = (Boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active == 0");
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            staticSleep(0.5);
            return jQcondition;
        } catch (Exception ignored) {
        }
        return jQcondition;
    }

    /**
     * Refreshes browser page.
     */
    public void refresh() {
        driver.navigate().refresh();
        staticSleep(2);
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents){
    }

    /**
     * Checks if a specified web element is displayed.
     *
     * @param element - Specifies the web element intended to be checked
     * @return {@code boolean}
     */
    protected boolean isElementDisplayed(WebElement element) {

        try {
            return element.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            System.out.println("Element is no longer attached to DOM");
            return false;
        }
    }

    /**
     * Waits for the element explicitly and returns the element once it's found.
     *
     * @param locator - Specifies locator of the element
     * @return {@code WebElement}
     */
    protected WebElement getElementDynamically(By locator) {
        return new WebDriverWait(driver, 30)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits for the element explicitly and returns the element once it's found.
     *
     * @param element - Specifies the web element
     * @return {@code WebElement}
     */
    protected WebElement getElementDynamically(WebElement element) {
        return new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits for all elements located by the specified locator and returns a list of the elements.
     *
     * @param locator - Specifies the locator
     * @return {@code List<WebElement>}
     */
    protected List<WebElement> getElementsDynamically(By locator) {
        return new WebDriverWait(driver, 30)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    /**
     * Waits for the element explicitly avoiding Stale Element Reference Exception and returns the element once it's found.
     *
     * @param element - Specifies the web element
     * @return {@code WebElement}
     */
    protected WebElement getElementAvoidingStaleReferenceException(WebElement element) {
        return new WebDriverWait(driver, 30).ignoring(StaleElementReferenceException.class).until(
                ExpectedConditions.visibilityOf(element));
    }

    /**
     * Gets list of specified elements dynamically ignoring Stale Element Reference Exception.
     *
     * @param locator - Specifies locator of the element
     * @return {@code WebElement}
     */
    protected List<WebElement> getElementsAvoidingStaleReferenceException(By locator) {
        return new WebDriverWait(driver, 30).ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    /**
     * Waits until expected condition is true.
     *
     * @param condition specifies expected condition
     */
    protected void waitUntil(Boolean condition) {
        ExpectedCondition<Boolean> expectation = (webDriver) -> condition;
        WebDriverWait wait = new WebDriverWait(driver, 30);

        try {
            wait.until(expectation);
        } catch (Throwable e) {
            throw new RuntimeException("Timed out after 30 seconds waiting for condition to be true.");
        }
    }

    /**
     * Constructs instance of a specified class.
     *
     * @param nClass - Specifies class instance of which is expected to be constructed
     * @return {@code <N extends WebPage>}
     */
    protected <N extends WebPage> N constructClassInstance(Class<N> nClass) {

        try {
            return nClass.getDeclaredConstructor(WebDriver.class).newInstance(driver);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot construct " + nClass.getName()
                    + "\n <<<!>>> Check if all requirements of isAvailable() method of respective class are met! <<<!>>>");
        }
    }

    /**
     * Checks if specified document name contains course type, if so cuts off the course type part and returns the
     * document name.
     *
     * @param initialDocumentName - Specifies initial document name
     * @return {@code String}
     */
    protected static String formDocumentName(String initialDocumentName) {

        if (initialDocumentName.contains("(Web Course)") || initialDocumentName.contains("(Single Source Project)")
                || initialDocumentName.contains("(ISD Lesson Template)")) {
            return initialDocumentName.replace(" (Single Source Project)", "").replace(" (Web Course)", "")
                    .replace(" (ISD Lesson Template)", "");
        } else {
            return initialDocumentName;
        }
    }

    /**
     * Scrolls to a specified element using JavaScript.
     *
     * @param element - Specifies the element
     */
    private void scrollToThisElementUsingJS(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Tries to click the specified element, in case the element is not visible, scrolls to it and then repeats the click
     * attempt again.
     *
     * @param element - Specifies the element intended to be clicked
     */
    protected void clickWebElementWithVisibilityCheck(WebElement element) {

        try {
            element.click();
        } catch (WebDriverException e) {

            if (e.toString().contains("is not clickable at point") || e.toString().contains("element not visible")) {
                scrollToThisElementUsingJS(element);
                element.click();
            } else {
                throw e;
            }
        }
    }

    /**
     * Tries to click the specified element, in case the element is not visible, scrolls to it and then repeats the click
     * attempt again.
     *
     * @param element - Specifies the element intended to be clicked
     */
    protected void clickWebElementAvoidingElementStaleness(WebElement element) {

        try {
            element.click();
        } catch (StaleElementReferenceException e) {
            getElementAvoidingStaleReferenceException(element).click();
        }
    }

    /**
     * Tries to click the specified element, in case the element is not visible, scrolls to it and then repeats the click
     * attempt again.
     *
     * @param elementLocator - Specifies locator of the element intended to be clicked
     */
    protected void clickWebElementAvoidingElementStaleness(By elementLocator) {

        try {
            driver.findElement(elementLocator).click();
        } catch (StaleElementReferenceException e) {
            getElementsAvoidingStaleReferenceException(elementLocator);
        }
    }

    /**
     * Gets value of specified input field.
     *
     * @param inputNameAttributeValue - Specifies value of 'name' attribute of the input field
     * @return {@code String}
     */
    protected String getInputFieldValue(String inputNameAttributeValue) {
        return (String) ((JavascriptExecutor) driver).executeScript(
                "return window.document.querySelector(\"input[name='" + inputNameAttributeValue + "']\").value");
    }

    /**
     * Retrieves value of specified XPATH locator.
     *
     * @param xpathLocator - Specifies the XPATH locator
     * @return {@code String}
     */
    protected String getXpathLocatorValue(By xpathLocator) {
        String stringValueOfLocator = xpathLocator.toString();

        if (stringValueOfLocator.contains("By.xpath")) {
            return stringValueOfLocator.replace("By.xpath: ","");
        } else {
            throw new RuntimeException("Provided locator is not of XPATH type. The method doesn't accept other types of locators!");
        }
    }

    /**
     * Checks if specified web element contains specified attribute.
     *
     * @param element   - Specifies the web element
     * @param attribute - Specifies the attribute
     * @return {@code boolean}
     */
    protected boolean doesElementContainAttribute(WebElement element, String attribute) {
        boolean result = false;

        try {
            String value = element.getAttribute(attribute);
            result = value != null;
        } catch (Exception exception) {
            System.out.println("Web element doesn't contain the specified attribute.\nException message: " + exception.getMessage()); // ToDo: Check which exception is thrown when an attribute is missed and catch respective exception!
        }
        return result;
    }

    /**
     * Checks if the set of cookies retrieved from the current session contains Bravais-Context cookie.
     *
     * @return {@code boolean}
     */
    public boolean isBravaisContextCookieCreated() {
        boolean result = false;
        Set<Cookie> cookies = driver.manage().getCookies();

        for (Cookie cookie : cookies) {
            String cookieName = cookie.getName();

            if (cookieName.contains("Bravais") && cookieName.contains("Context")) {
                result = true;
                break;
            }
        }

        if (!result) {
            System.out.println("Set of cookies of the current session doesn't contain 'Bravais-Context' cookie." +
                    "\nThe following cookies are generated for the current session: " + cookies);
        }
        return result;
    }

    /**
     * Gets full name of Bravais-Context cookie if it is available in the set of cookies retrieved from the current session.
     *
     * @return {@code String}
     */
    private String getFullNameOfBravaisContextCookie() {
        String fullName = null;

        if (isBravaisContextCookieCreated()) {
            Set<Cookie> cookies = driver.manage().getCookies();

            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();

                if (cookieName.contains("Bravais") && cookieName.contains("Context")) {
                    fullName =  cookieName;
                    break;
                }
            }
        } else {
            throw new RuntimeException("Bravais-Context cookie is not available in the set of cookies retrieved from " +
                    "the current session!");
        }
        assert fullName != null;
        return fullName;
    }

    /**
     * Gets Bravais-Context cookie.
     *
     * @return {@code Cookie}
     */
    public Cookie getBravaisContextCookie() {
        return driver.manage().getCookieNamed(getFullNameOfBravaisContextCookie());
    }

    /**
     * Encodes specified string to UTF-8.
     *
     * @param stringToEncode - Specifies the string intended to be encoded
     * @return {@code String}
     */
    protected String encodeStringToUTF_8(String stringToEncode) {
        byte[] bytes = new byte[0];

        try {
            bytes = stringToEncode.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new String(bytes);
    }
}