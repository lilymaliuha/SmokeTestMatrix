package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdssearchpage.leftsidepanels;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.Link;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdssearchpage.CDSSearchPage;
import com.xyleme.bravais.web.pages.cds.popupdialogs.nodeselectioninnodestreedialog.classificationselectiondialog.ManageClassificationDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Implementation of Classifications panel on Search page.
 */
public class ClassificationsPanel extends BaseLeftSidePanelOnSearchPage {

    public ClassificationsPanel(WebDriver driver, By panelBodyLocator) {
        super(driver, panelBodyLocator);
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                panelHeader().isAvailable() &&
                setFilterButton().isAvailable();
    }

    private Button setFilterButton() {
        return new Button(driver, By.xpath(panelBodyElement.getXpathLocatorValue() +
                "//button[normalize-space()='Set Filter']"));
    }

    private Link panelHeader() {
        return new Link(driver, By.xpath(panelBodyElement.getXpathLocatorValue() +
                "//li[contains(@class, 'panel-header')]//td[contains(@class, 'classifications-panel')]"));
    }

    /**
     * Expands the panel.
     *
     * @return {@code ClassificationsPanel}
     */
    public ClassificationsPanel expandPanel() {
        return expandPanel(ClassificationsPanel.class);
    }

    /**
     * Clicks Set Filter button.
     *
     * @return {@code ManageClassificationDialog}
     */
    private ManageClassificationDialog clickSetFilterButton() {
        setFilterButton().waitUntilAvailable().click();
        return new ManageClassificationDialog(driver);
    }

    /**
     * Gets web element which represents checkbox of a specified classification.
     *
     * @param classificationName - Specifies name of the classification
     * @return {@code WebElement}
     */
    private WebElement getPanelListItemCheckboxElement(String classificationName) {
        return getElementDynamically(By.xpath(panelBodyElement.getXpathLocatorValue() + "//span[text()='"
                + classificationName + "']//ancestor::tr//input"));
    }

    /**
     * Checks if the specified checkbox is checked.
     *
     * @param classificationName - Specifies name of the classification
     * @return {@code boolean}
     */
    private boolean isPanelListCheckboxIsChecked(String classificationName) {
        return getPanelListItemCheckboxElement(classificationName).isSelected();
    }

    /**
     * Checks checkbox of the specified classification.
     *
     * @param classificationName - Specifies classification name
     * @return {@code CDSSearchPage}
     */
    @Override
    public CDSSearchPage selectPanelItemCheckbox(String classificationName) {
        if (!isPanelListCheckboxIsChecked(classificationName)) {
            getPanelListItemCheckboxElement(classificationName).click();
            waitUntil(isPanelListCheckboxIsChecked(classificationName));
            waitForAngularJSProcessing();
        } else {
            System.out.println("Checkbox of classification '" + classificationName + "' is already checked.");
        }
        return new CDSSearchPage(driver);
    }

    /**
     * Unchecks checkbox of the specified classification.
     *
     * @param classificationName - Specifies classification name
     * @return {@code CDSSearchPage}
     */
    @Override
    public CDSSearchPage unselectPanelItemCheckbox(String classificationName) {
        if (isPanelListCheckboxIsChecked(classificationName)) {
            getPanelListItemCheckboxElement(classificationName).click();
            waitUntil(!isPanelListCheckboxIsChecked(classificationName));
            waitForAngularJSProcessing();
        } else {
            System.out.println("Checkbox of classification '" + classificationName + "' is already unchecked.");
        }
        return new CDSSearchPage(driver);
    }

    /**
     * Sets up classification filter with the specified classification element.
     *
     * @param pathToClassificationNode - Specifies path to the classification element the documents are intended to be
     *                                 filtered by
     * @return {@code CDSSearchPage}
     */
    public CDSSearchPage setUpClassificationFilter(String pathToClassificationNode) {
        ManageClassificationDialog manageClassificationDialog = clickSetFilterButton();
        return manageClassificationDialog.selectClassificationElement(pathToClassificationNode).save(CDSSearchPage.class);
    }
}