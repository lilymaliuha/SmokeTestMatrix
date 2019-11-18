package com.xyleme.bravais.utils;

import com.xyleme.bravais.DriverMaster;
import org.apache.tools.ant.util.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.xyleme.bravais.DriverMaster.pathToSurefireReportsFolder;

/**
 * Implementation of the Screenshot On Failure mechanism.
 */
public class ScreenShotOnFailure extends TestListenerAdapter {

    private void takeScreenshot(String name, WebDriver driver) {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String date = new SimpleDateFormat("YYYYMMdd_HHmmss").format(Calendar.getInstance().getTime());
        String destDir = pathToSurefireReportsFolder;
        new File(destDir).mkdirs();
        System.out.println("Screenshot Path: " + destDir);
        String destFile = date + ".png";
        System.out.println("Screenshot Name: " + date + ".png");
        try {
            FileUtils.getFileUtils().copyFile(scrFile, new File(destDir + "/" + destFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Reporter.setEscapeHtml(false);
        Reporter.log("Saved <a href=./screenshots/" + destFile + ">" + name + "</a>");
    }

    private void takeScreenshot(String name) {
        WebDriver driver = DriverMaster.getDriverInstance();
        takeScreenshot(name, driver);
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        takeScreenshot(tr.getMethod().getMethodName());
    }
}