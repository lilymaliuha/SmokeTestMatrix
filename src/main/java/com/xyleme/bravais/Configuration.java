package com.xyleme.bravais;

import java.io.*;
import java.util.Properties;

public class Configuration {
    private static final Environment ENVIRONMENT;
    private static Environment GLOBAL_ENVIRONMENT;
    private static Configuration config;
    private static Properties prop;

    static {
        ENVIRONMENT = getPropValues();

        System.out.println("\n======================================================================================\n\n");
        System.out.println("    >>    TEST ENVIRONMENT: " + System.getProperty("testEnvironment"));
        System.out.println("    >>    ENVIRONMENT CONFIGURATION FILE USED: " + getEnvironmentConfigurationFile());
        System.out.println("    >>    PRODUCT VERSION: " + System.getProperty("productVersion"));
        System.out.println("\n\n======================================================================================\n");
    }

    private Configuration() {
    }

    public static Configuration getConfig() {

        if (config == null) {
            config = new Configuration();
            setGlobalEnvironment();
            return config;
        }
        return config;
    }

    static void setGlobalEnvironment() {
        GLOBAL_ENVIRONMENT = ENVIRONMENT;
    }

    public Environment getEnvironmentSettings() {
        return GLOBAL_ENVIRONMENT;
    }

    /**
     * Gets reference to the respective configuration file according to the test environment passed in the testEnvironment
     * property.
     *
     * @return {@code String}
     */
    private static String getEnvironmentConfigurationFile() {
        String configurationFileToReturn = null;
        String testEnvironment = System.getProperty("testEnvironment").toLowerCase();

        switch (testEnvironment) {
            case "dev":
                configurationFileToReturn = "/dev.properties";
                break;
            case "qa":
                configurationFileToReturn = "/qa.properties";
                break;
            case "beta":
                configurationFileToReturn = "/beta.properties";
                break;
            case "stg":
                configurationFileToReturn = "/stg.properties";
                break;
            case "prodeu":
                configurationFileToReturn = "/prodeu.properties";
                break;
            case "produs":
                configurationFileToReturn = "/produs.properties";
                break;
            case "perf":
                configurationFileToReturn = "/perf.properties";
                break;
            case "portal6":
                configurationFileToReturn = "/portal6.properties";
                break;
            case "it1":
                configurationFileToReturn = "/it1.properties";
                break;
        }
        assert configurationFileToReturn != null;
        return "/environmentConfigurationFiles" + configurationFileToReturn;
    }

    private static Environment getPropValues() {
        String propFileName = getEnvironmentConfigurationFile();

        try {
            prop = new Properties();
            prop.load(ClassLoader.class.getResourceAsStream(propFileName));

            Properties usersAndBrowserProperties = new Properties();
            usersAndBrowserProperties.load(ClassLoader.class.getResourceAsStream("/usersandbrowser.properties"));

            prop.putAll(usersAndBrowserProperties);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Environment environment = new Environment();

        for (String key : prop.stringPropertyNames()) {
            environment.env.put(key.replace("com.xyleme.data.", ""), prop.getProperty(key));
        }
        return environment;
    }
}