package com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows;

import com.xyleme.bravais.datacontainers.PermissionLevel;
import com.xyleme.bravais.web.elements.*;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.baserows.BaseRowOfDataTableWithOuterContainer;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of a Row element of a table available on Content Permissions panel of Group Details page.
 */
public class RowOfTableOnContentPermissionsPanelOfGroupDetailsPage extends BaseRowOfDataTableWithOuterContainer {

    public RowOfTableOnContentPermissionsPanelOfGroupDetailsPage(WebDriver driver, Element tableContainer,
                                                                 Element rowBodyElement) {
        super(driver, tableContainer, rowBodyElement);
    }

    /**
     * Gets item name link.
     *
     * @return {@code Link}
     */
    private Link getItemLink() {
        return new Link(driver, By.xpath(getRowStructureElement("Name").getXpathLocatorValue() + "/a"));
    }

    /**
     * Gets label element of Access Level parameter.
     *
     * @return {@code LabelText}
     */
    private LabelText getAccessLevelLabel() {
        return new LabelText(driver, By.xpath(getRowStructureElement("Access Level").getXpathLocatorValue() +
                "/span[contains(@class, 'field-truncate-value')]"));
    }

    /**
     * Gets Permissions Level drop-down which appears after clicking on access level parameter label.
     *
     * @return {@code Select}
     */
    private Select permissionsLevelDropDown() {
        return new Select(driver, By.xpath(getAccessLevelLabel().getXpathLocatorValue() +
                "/form/select[@class='form-control']"));
    }

    /**
     * Gets item name.
     *
     * @return {@code String}
     */
    public String getName() {
        return getColumnValue("Name");
    }

    /**
     * Gets item access level.
     *
     * @return {@code String}
     */
    public String getAccessLevel() {
        return getColumnValue("Access Level");
    }

    /**
     * Selects specified level of permissions in the Permissions Level drop-down.
     *
     * @param levelOfPermissions - Specifies the level of permissions intended to be set
     * @return {@code RowOfTableOnContentPermissionsPanelOfGroupDetailsPage}
     */
    public RowOfTableOnContentPermissionsPanelOfGroupDetailsPage setLevelOfPermissions(PermissionLevel levelOfPermissions) {
        getAccessLevelLabel().waitUntilAvailable().click();
        permissionsLevelDropDown().waitUntilAvailable().selectItemByValue(levelOfPermissions.getValue());
        permissionsLevelDropDown().sendKeys(Keys.ENTER);
        return this;
    }

    /**
     * Removes the table item.
     */
    public void remove() {
        Element optionsMenuElement = openItemOptionsMenuAndReturnOpenedMenuElement();
        getOptionOfOpenedItemOptionsMenu(optionsMenuElement, "Remove").click();
    }
}