package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsgroupdetailspage.panels;

import com.xyleme.bravais.datacontainers.CDSFeatures;
import com.xyleme.bravais.web.elements.CheckBox;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.LabelText;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSPanelOnDetailsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;

import static com.xyleme.bravais.datacontainers.CDSFeatures.*;

/**
 * Implementation of Feature Access pane on Group Details page.
 */
public class FeatureAccessPane extends BaseCDSPanelOnDetailsPage {

    public FeatureAccessPane(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public FeatureAccessPane load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return tableHeader().isAvailable() &&
                tableBody().isAvailable();
    }

    private Element tableParentElement() {
        return new Element(driver, By.xpath("//div[@id='features']//table"));
    }

    private Element tableHeader() {
        return new Element(driver, By.xpath(tableParentElement().getXpathLocatorValue() + "/thead"));
    }

    private Element tableBody() {
        return new Element(driver, By.xpath(tableParentElement().getXpathLocatorValue() + "/tbody"));
    }

    private Element columnHeaderElement() {
        return new Element(driver, By.xpath(tableHeader().getXpathLocatorValue() + "/tr/th"));
    }

    private Element tableRowElement() {
        return new Element(driver, By.xpath(tableBody().getXpathLocatorValue() + "/tr"));
    }

    /**
     * Gets index of specified column.
     *
     * @param columnName - Specifies name of the column index of which is intended to be returned.
     * @return {@code int}
     */
    private int getIndexOfColumn(String columnName) {
        int columnIndex = -1;
        List<WebElement> columnHeaderElements = getElementsDynamically(By.xpath(columnHeaderElement().getXpathLocatorValue()));

        for (WebElement columnHeaderElement : columnHeaderElements) {

            if (columnHeaderElement.getText().equals(columnName)) {
                columnIndex = columnHeaderElements.indexOf(columnHeaderElement);
                break;
            }
        }

        if (columnIndex != -1) {
            return columnIndex;
        } else {
            throw new RuntimeException("Table doesn't contain column with name '" + columnName + "'.");
        }
    }

    /**
     * Gets row element of specific feature.
     *
     * @param featureName - Specifies the feature name the row element of which is expected to be returned
     * @return {@code Element}
     */
    private Element getRowElementOfFeature(String featureName) {
        return new Element(driver, By.xpath(tableRowElement().getXpathLocatorValue() + "/td[text()='" + featureName +
                "']/parent::tr"));
    }

    /**
     * Gets description of specified feature.
     *
     * @param featureName - Specifies the feature description of which is expected to be returned.
     * @return {@code String}
     */
    public String getFeatureDescription(String featureName) {
        int indexOfDescriptionColumn = getIndexOfColumn("Description");
        return new LabelText(driver, By.xpath(getRowElementOfFeature(featureName).getXpathLocatorValue() + "/td[" +
                (indexOfDescriptionColumn + 1) + "]")).waitUntilAvailable().getText();
    }

    /**
     * Gets selection checkbox element of specified feature.
     *
     * @param feature - Specifies the feature the selection checkbox element of which is intended to be returned
     * @return {@code Element}
     */
    private CheckBox getSelectionCheckBoxOfFeature(CDSFeatures feature) {
        return new CheckBox(driver, By.xpath(getRowElementOfFeature(feature.getValue()).getXpathLocatorValue() +
                "/td/input[@type='checkbox']")).waitUntilAvailable();
    }

    /**
     * Checks if feature selection checkbox is selected.
     *
     * @param feature - Specifies the feature checkbox of which is intended to be checked
     * @return {@code boolean}
     */
    public boolean isFeatureSelected(CDSFeatures feature) {
        return getSelectionCheckBoxOfFeature(feature).isSelected();
    }

    /**
     * Selects specified feature.
     *
     * @param featureName - Specifies the feature intended to be selected
     * @return {@code FeatureAccessPane}
     */
    private FeatureAccessPane selectFeature(CDSFeatures featureName) {

        if (!isFeatureSelected(featureName)) {
            getSelectionCheckBoxOfFeature(featureName).select();
            new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeSelected(
                    By.xpath(getSelectionCheckBoxOfFeature(featureName).getXpathLocatorValue())));
        } else {
            System.out.println("Checkbox of '" + featureName + "' feature is already selected.");
        }
        return this;
    }

    /**
     * Selects specified features.
     *
     * @param features - Specifies the features intended to be selected
     * @return {@code FeatureAccessPane}
     */
    public FeatureAccessPane selectMultipleFeatures(CDSFeatures... features) {
        List<CDSFeatures> featuresToSelect = Arrays.asList(features);
        featuresToSelect.forEach(this::selectFeature);
        return this;
    }

    /**
     * Selects all features available on the panel.
     *
     * @return {@code FeatureAccessPane}
     */
    public FeatureAccessPane selectAllFeatures() {
        List<CDSFeatures> features = Arrays.asList(ADMIN_PORTAL, ANALYTICS, BRANDING, CONTENT_ATTRIBUTES, FEATURES, PORTAL,
                SYSTEM_SETTINGS, USER_MANAGEMENT, USER_PROFILES);
        features.forEach(this::selectFeature);
        return this;
    }
}