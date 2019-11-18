package com.xyleme.bravais;

import com.xyleme.bravais.web.WebPage;
import com.xyleme.bravais.web.elements.Element;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implementation of Elements Availability Checker.
 */
public class ElementsAvailabilityChecker { // Experimental approach.
    protected WebDriver driver;
    private List<Integer> indexesOfUnavailableTableElements;

    public ElementsAvailabilityChecker(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Returns list of indexes of all false conditions of the specified list of conditions.
     *
     * @param listOfConditionsToBeChecked - Specifies list of conditions intended to be checked
     * @return {@code List<Integer>}
     */
    private List<Integer> indexOfAllFalseConditions(List<Boolean> listOfConditionsToBeChecked) {
        List<Integer> listToReturn = new ArrayList<>();
        for (int i = 0; i < listOfConditionsToBeChecked.size(); i++) {
            if (!listOfConditionsToBeChecked.get(i)) {
                listToReturn.add(i);
            }
        }
        return listToReturn;
    }

    /**
     * Checks if each element of the specified list of elements is available.
     *
     * @param listOfElementsToBeChecked - Specifies list of element intended to be checked
     * @return {@code boolean}
     */
    private boolean areElementsAvailable(List<Element> listOfElementsToBeChecked) {
        List<Boolean> listOfConditions = new ArrayList<>();
        for (Element elementToBeChecked : listOfElementsToBeChecked) {
            listOfConditions.add(elementToBeChecked.isAvailable());
        }
        if (listOfConditions.contains(false)) {
            indexesOfUnavailableTableElements = indexOfAllFalseConditions(listOfConditions);
        }
        return !listOfConditions.contains(false);
    }

    /**
     * Waits until each element of the specified list of elements is available.
     *
     * @param elementsToBeChecked - Specifies list of the elements
     */
    protected void waitUntilElementsAreAvailable(Element... elementsToBeChecked) {
        assert elementsToBeChecked.length != 0;
        List<Element> listOfElementsToBeChecked = new ArrayList<>(Arrays.asList(elementsToBeChecked));
        ExpectedCondition<Boolean> expectation = (webDriver) -> areElementsAvailable(listOfElementsToBeChecked);
        try {
            new WebDriverWait(driver, 30).until(expectation);
        } catch (Throwable e) {
            List<String> listOfUnAvailableElements = new ArrayList<>();
            for (int index : indexesOfUnavailableTableElements) {
                listOfUnAvailableElements.add(listOfElementsToBeChecked.get(index).getXpathLocatorValue());
            }
            throw new RuntimeException("Timed out after 30 seconds waiting for availability of specified elements." +
                    "\n>> Indexes of unavailable elements: " + indexesOfUnavailableTableElements +
                    "\n>> Locators of unavailable elements: " + listOfUnAvailableElements);
        }
    }

    /**
     * Constructs instance of a specified class.
     *
     * @param nClass - Specifies class instance of which is expected to be constructed
     * @return {@code <T extends WebPage>}
     */
    protected <T extends WebPage> T constructClassInstance(Class<T> nClass) {
        try {
            return nClass.getDeclaredConstructor(WebDriver.class).newInstance(driver);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot construct " + nClass.getName()
                    + "\n <<<!>>> Check if all requirements of isAvailable() method of respective class are met! <<<!>>>");
        }
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
}