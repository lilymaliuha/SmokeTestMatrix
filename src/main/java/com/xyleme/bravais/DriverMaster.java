package com.xyleme.bravais;

import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static com.xyleme.bravais.BaseTest.ENVIRONMENT;

public class DriverMaster {
    public static String pathToDownloadsFolder;
    public static String pathToScreenshotsFolder;
    public static String pathToFilesForUploadingFolder;
    public static String pathToMMLForUploadingFolder;
    public static String pathToSurefireReportsFolder;
    public static boolean headlessMode;
    public static boolean remoteExecution;
    private static HashMap<Long, WebDriver> map = new HashMap<>();
    private static WebDriver myDriver;

    private DriverMaster() {
    }

    public static WebDriver getDriverInstance() {

        if (myDriver == null) {
            createDriverInstance();
            return myDriver;
        }
        return myDriver;
    }

    public static WebDriver checkIfOldDriverClosedAndCreateNewDriverInstance() {

        if (myDriver == null) {
            createDriverInstance();
        } else {
            stopDriver();
            createDriverInstance();
        }
        return myDriver;
    }

    private static void createDriverInstance() {
//        myDriver = createMyDriver();
        myDriver = createRemoteWebDriver();
//        myDriver = createLocalRemoteWebDriver();
    }

    private static WebDriver createMyDriver() {
        pathToDownloadsFolder = new File("").getAbsolutePath() + "/src/test/resources/downloads/";
        pathToScreenshotsFolder = new File("").getAbsolutePath() + "/surefire-reports/screenshots/";
        pathToFilesForUploadingFolder = new File("").getAbsolutePath() + "/src/test/resources/filesForUploading/";
        pathToMMLForUploadingFolder = new File("").getAbsolutePath() + "/src/test/resources/mmlForUploading/";
        pathToSurefireReportsFolder = new File("").getAbsolutePath() + "/surefire-reports/screenshots";
        headlessMode = false;
        WebDriver driver = null;
        String path = new File("").getAbsolutePath().replace("\\target", "") +
                "\\src\\test\\resources\\BrowserDrivers\\";
        com.xyleme.bravais.BrowserType browser = com.xyleme.bravais.BrowserType.get(ENVIRONMENT.env.get("browser"));

        switch (browser) {
            case FIREFOX:
                FirefoxProfile firefoxProfile = new FirefoxProfile();
                firefoxProfile.setPreference("browser.download.folderList", 2);
                firefoxProfile.setPreference("browser.download.dir", pathToDownloadsFolder);
                firefoxProfile.setPreference("browser.download.manager.alertOnEXEOpen", false);
                firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/msword, " +
                        "application/csv, application/ris, text/csv, image/png, application/pdf, text/html, " +
                        "text/plain, application/zip, application/x-zip, application/x-zip-compressed, " +
                        "application/download, application/octet-stream, application/vnd.ms-excel, application/zip, " +
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, " +
                        "application/x-shockwave-flash, video/mp4");
                firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
                firefoxProfile.setPreference("browser.download.manager.focusWhenStarting", false);
                firefoxProfile.setPreference("browser.download.useDownloadDir", true);
                firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
                firefoxProfile.setPreference("browser.download.manager.alertOnEXEOpen", false);
                firefoxProfile.setPreference("browser.download.manager.closeWhenDone", true);
                firefoxProfile.setPreference("browser.download.manager.showAlertOnComplete", false);
                firefoxProfile.setPreference("browser.download.manager.useWindow", false);
                firefoxProfile.setPreference("services.sync.prefs.sync.browser.download.manager.showWhenStarting", false);
                firefoxProfile.setPreference("pdfjs.disabled", true);

                System.setProperty("webdriver.gecko.driver", path + "geckodriver.exe");

                DesiredCapabilities capabilities = DesiredCapabilities.firefox();
                capabilities.setJavascriptEnabled(true);
                capabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
                capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
                driver = new FirefoxDriver(capabilities);

                map.put(Thread.currentThread().getId(), driver);
                break;
            case CHROME:
                System.setProperty("webdriver.chrome.driver", path + "chromedriver.exe");

                HashMap<String, Object> chromePrefs = new HashMap<>();
                chromePrefs.put("profile.default_content_settings.popups", 1);
                chromePrefs.put("download.default_directory", pathToDownloadsFolder);
                chromePrefs.put("credentials_enable_service", false);
                chromePrefs.put("profile.password_manager_enabled", false);
                ChromeOptions options = new ChromeOptions();
                options.setExperimentalOption("prefs", chromePrefs);
                options.addArguments("disable-infobars");
                DesiredCapabilities cap = DesiredCapabilities.chrome();
                cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                cap.setCapability(ChromeOptions.CAPABILITY, options);
                cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);

                try {
                    driver = new ChromeDriver(cap);
                } catch (Exception e) {
                    driver = new ChromeDriver(cap);
                }
                map.put(Thread.currentThread().getId(), driver);
                break;
            case IE:
                System.setProperty("webdriver.ie.driver", path + "IEDriverServer.exe");
                capabilities = new org.openqa.selenium.remote.DesiredCapabilities();
                driver = new InternetExplorerDriver(capabilities);
                map.put(Thread.currentThread().getId(), driver);
                break;
            case IOS:
                break;
            default:
                firefoxProfile = new FirefoxProfile();
                capabilities = DesiredCapabilities.firefox();
                capabilities.setJavascriptEnabled(true);
                capabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
                driver = new FirefoxDriver(capabilities);
                map.put(Thread.currentThread().getId(), driver);
        }
        return driver;
    }

    /**
     * Creates remote web driver.
     *
     * @return {@code RemoteWebDriver}
     */
    private static RemoteWebDriver createRemoteWebDriver() {
        RemoteWebDriver driver = null;
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--headless");
        options.addArguments("--window-size=1800,1020");
        options.setExperimentalOption("w3c", false);
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        try {
            driver = new RemoteWebDriver(new URL("http://192.168.99.100:4444/wd/hub"), capabilities);
            //"http://selenium-hub.xyleme.com:4444/wd/hub"
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assert driver != null;
        driver.setFileDetector(new LocalFileDetector());
        pathToDownloadsFolder = new File("").getAbsolutePath() + "/target/test-classes/downloads/";
        pathToScreenshotsFolder = new File("").getAbsolutePath() + "/target/surefire-reports/screenshots/";
        pathToFilesForUploadingFolder = new File("").getAbsolutePath() + "/target/test-classes/filesForUploading/";
        pathToMMLForUploadingFolder = new File("").getAbsolutePath() + "/target/test-classes/mmlForUploading/";
        pathToSurefireReportsFolder = new File("").getAbsolutePath() + "/target/surefire-reports/screenshots";
        headlessMode = true;
        remoteExecution = true;
        return driver;
    }

    /**
     * Creates remote web driver instance for running tests on a local grid.
     *
     * @return {@code RemoteWebDriver}
     */
    private static RemoteWebDriver createLocalRemoteWebDriver() {
        RemoteWebDriver driver = null;
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--headless");
        options.addArguments("--window-size=1800,1020");
        options.setExperimentalOption("w3c", false);
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        try {
            driver = new RemoteWebDriver(new URL("http://192.168.99.100:4444/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assert driver != null;
        driver.setFileDetector(new LocalFileDetector());
        pathToDownloadsFolder = new File("").getAbsolutePath() + "/target/test-classes/downloads/";
        pathToScreenshotsFolder = new File("").getAbsolutePath() + "/target/surefire-reports/screenshots/";
        pathToFilesForUploadingFolder = new File("").getAbsolutePath() + "/target/test-classes/filesForUploading/";
        pathToMMLForUploadingFolder = new File("").getAbsolutePath() + "/target/test-classes/mmlForUploading/";
        pathToSurefireReportsFolder = new File("").getAbsolutePath() + "/target/surefire-reports/screenshots";
        headlessMode = true;
        return driver;
    }

    static void stopDriver() {

        if (!(myDriver == null || myDriver.toString().contains("null"))) {
            myDriver.quit();
            myDriver = null;
        }
    }

    /**
     * Gets number of tabs opened in browser.
     *
     * @return {@code int}
     */
    private static int getNumberOfOpenedTabs() {
        return myDriver.getWindowHandles().size();
    }

    /**
     * Switches to a new already opened tab.
     */
    public static void switchToNewTab() {
        new WebDriverWait(myDriver, 10).until(ExpectedConditions.numberOfWindowsToBe(
                2));
        Set<String> handles = myDriver.getWindowHandles();
        String current = myDriver.getWindowHandle();
        handles.remove(current);
        String newTab = handles.iterator().next();
        myDriver.switchTo().window(newTab);
    }

    /**
     * Closes an active tab and switches to the original tab.
     */
    public static void closeCurrentTabAndSwitchToOriginalTab() {
        Set<String> handles = myDriver.getWindowHandles();
        myDriver.close();
        new WebDriverWait(myDriver, 10).until(ExpectedConditions.numberOfWindowsToBe(
                1));
        myDriver.switchTo().window(handles.iterator().next());
    }

    /**
     * Opens a new browser tab and switches focus to it.
     */
    public static void openNewTabAndSwitchToIt() {
        ((JavascriptExecutor) myDriver).executeScript(("window.open('','_blank');"));
        String current = myDriver.getWindowHandle();
        Set<String> handles = myDriver.getWindowHandles();
        handles.remove(current);
        String newTab = handles.iterator().next();
        myDriver.switchTo().window(newTab);
    }

    /**
     * Opens a new clean browser tab, switches to it and closes the previous tab.
     */
    public static void openNewTabSwitchToItAndClosePreviousTab() {
        ((JavascriptExecutor) myDriver).executeScript(("window.open('','_blank');"));
        System.out.println("Number of tabs after opening a new tab: " + getNumberOfOpenedTabs());
        closeFirstTabAndSwitchToSecondTab();
    }

    /**
     * Waits until second tab is opened and switches to it.
     */
    public static void waitUntilSecondTabIsOpenedAndSwitchToIt() {
        new WebDriverWait(myDriver, 20).until(ExpectedConditions.numberOfWindowsToBe(
                2));
        switchToNewTab();
    }

    /**
     * Condition: Two browser tabs are opened.
     * Closes the first tab and switches focus to the second tab.
     */
    private static void closeFirstTabAndSwitchToSecondTab() {
        String current = myDriver.getWindowHandle();
        Set<String> handles = myDriver.getWindowHandles();
        myDriver.close();
        handles.remove(current);
        String newTab = handles.iterator().next();
        myDriver.switchTo().window(newTab);
    }
}