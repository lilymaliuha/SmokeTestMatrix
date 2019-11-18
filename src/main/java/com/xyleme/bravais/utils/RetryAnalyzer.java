package com.xyleme.bravais.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Implementation of IRetryAnalyzer interface.
 */
public class RetryAnalyzer implements IRetryAnalyzer {
    private int counter = 0;

    @Override
    public boolean retry(ITestResult result) {
        int retryLimit = 2;
        if (counter < retryLimit) {
            System.out.println("Retrying test '" + result.getName() + "' with status '"
                    + getResultStatusName(result.getStatus()) + "' for the " + (counter + 1) + " time(s).");
            counter++;
            return true;
        }
        return false;
    }

    /**
     * Gets result status of a test method.
     *
     * @param status - Specifies index of the status
     * @return {@code String}
     */
    private String getResultStatusName(int status) {
        String resultName = null;
        switch (status) {
            case 1:
                resultName = "PASSED";
                break;
            case 2:
                resultName = "FAILED";
                break;
            case 3:
                resultName = "SKIPPED";
                break;
        }
        assert resultName != null;
        return resultName;
    }
}