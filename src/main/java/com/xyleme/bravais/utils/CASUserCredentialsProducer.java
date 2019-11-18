package com.xyleme.bravais.utils;

import static com.xyleme.bravais.web.WebPage.ENVIRONMENT;

/**
 * Implementation of CAS User credentials producer.
 */
public class CASUserCredentialsProducer {
    public static String USERNAME;
    public static String PASSWORD;
    public static String FULL_NAME;

    static {
        setCASUserCredentials();
    }

    /**
     * Sets corresponding username, password and full name of CAS user basing on the environment the tests are intended
     * to be executed in.
     */
    private static void setCASUserCredentials() {
        String environmentURL = ENVIRONMENT.env.get("CDS_URL");

        if (environmentURL.contains("portal3.qa.bravais") || environmentURL.contains("portal1.dev.bravais.com")) {
            USERNAME = "ad1\\cds.tester-qa";
            PASSWORD = "Xyleme@2018";
            FULL_NAME = "CDS Tester QA";
        } else if (environmentURL.contains("qa1-beta.beta.bravais.com")) {
            USERNAME = "ad1\\cds.tester-beta";
            PASSWORD = "Xyleme@2018";
            FULL_NAME = "CDS Tester BETA";
        } else if (environmentURL.contains("qa1-stg.stg.bravais")) {
            USERNAME = "qa+qa1-stg@xyleme.com";
            PASSWORD = "w6khD}W6xweETR94GT";
            FULL_NAME = "QA qa1-stg";
        } else if (environmentURL.contains("qa1.bravais")) {
            USERNAME = "qa@xyleme.com";
            PASSWORD = "H(c*S:zEf/5A%g}";
            FULL_NAME = "Xyleme QA";
        } else if (environmentURL.contains("qa1-eu.bravais")) {
            USERNAME = "qa@xyleme.com";
            PASSWORD = "c4QkVai@dZ3nW3RE8V";
            FULL_NAME = "Xyleme QA";
        }
    }
}