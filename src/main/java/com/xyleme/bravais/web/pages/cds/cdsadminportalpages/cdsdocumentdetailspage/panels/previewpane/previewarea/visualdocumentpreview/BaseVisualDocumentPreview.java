package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.previewarea.visualdocumentpreview;

import com.xyleme.bravais.datacontainers.PreviewType;
import com.xyleme.bravais.web.WebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static com.xyleme.bravais.BaseTest.staticSleep;

public abstract class BaseVisualDocumentPreview extends WebPage<BaseVisualDocumentPreview> {

    BaseVisualDocumentPreview(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        waitForInvisibilityOfSpinner();
        return true;
    }

    @Override
    public BaseVisualDocumentPreview load() {
        return this;
    }

    /**
     * Waits for invisibility of a spinner.
     */
    private void waitForInvisibilityOfSpinner() {
        int timeOutInSeconds = 30;
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".Spinner")));
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Waits until spinner which appears on a course page once it's previewed disappears.
     *
     * @param previewType - Specifies preview type
     */
    void waitForInvisibilityOfSpinnerOnPageInIFrame(PreviewType previewType) {
        waitForFrameToBeAvailable(previewType);
        new WebDriverWait(driver, 60).until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector("#PageBody .Spinner")));
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    /**
     * Waits for specific frame to be available.
     *
     * @param key - Specifies preview type
     */
    private void waitForFrameToBeAvailable(PreviewType key) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        driver.switchTo().defaultContent();
        switch (key) {
            case SHARING:
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("#shared-document-preview iframe")));
                staticSleep(0.5);
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("skin"));
                break;
            case BCP:
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("skin"));
                break;
            case SHARING_DOC:
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("#shared-document-preview iframe")));
                staticSleep(2);
                break;
            case FULL_PREVIEW:
                // NOTE: have to ignore WebDriverException because StaleElementReference exception sometimes is thrown not explicitly
                // but as 'unknown' runtime exception of WebDriverException class in case where a browser page refreshing is involved.
                wait.ignoring(WebDriverException.class).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("preview-frame")));
                staticSleep(0.5);
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.tagName("iframe")));
                break;
            case XPE_OR_PDF_OR_IMAGE_PREVIEW:
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("preview-frame")));
                staticSleep(1);
                break;
            case HTML:
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("#shared-document-preview iframe")));
                staticSleep(0.5);
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.tagName("iframe")));
                break;
        }
    }
}