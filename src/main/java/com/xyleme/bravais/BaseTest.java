package com.xyleme.bravais;

import org.testng.annotations.*;

import static com.xyleme.bravais.Configuration.setGlobalEnvironment;

public abstract class BaseTest {
    protected static final Environment ENVIRONMENT = com.xyleme.bravais.web.WebPage.ENVIRONMENT;
    private static final String ESCAPE_PROPERTY = "org.uncommons.reportng.escape-output";

    @BeforeMethod (alwaysRun = true)
    public void setUp() {
        DriverMaster.checkIfOldDriverClosedAndCreateNewDriverInstance();
        setGlobalEnvironment();
        System.setProperty(ESCAPE_PROPERTY, "false");
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown() {
        DriverMaster.stopDriver();
    }

    @AfterTest (alwaysRun = true)
    public void testSetTearDown() {
        DriverMaster.stopDriver();
    }

    protected static int getRandomNumber(int min, int max) {
        return (min + (int) (Math.random() * ((max - min) + 1)));
    }

    public static void staticSleep(double sleepInSeconds) {
        try {
            Thread.sleep((int) (sleepInSeconds * 1000));
        } catch (InterruptedException ignored) {}
    }
}