package com.xyleme.bravais.web.pages.analytics.statementdetails;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Implementation of data table (data block) on Statement Details page (Statement data is represented in table elements).
 */
abstract class BaseStatementDetailsPageTable {
    private WebElement tableWebElement;

    BaseStatementDetailsPageTable(WebElement tableWebElement) {
        this.tableWebElement = tableWebElement;
    }

    private WebElement getTableParameterElement(String tableParameterTitle) {
        return tableWebElement.findElement(By.xpath("./tbody/tr/th[starts-with(text(), '" + tableParameterTitle + ":')]"));
    }

    /**
     * Gets value of specified table parameter.
     *
     * @param tableParameterTitle - Specifies table parameter title
     * @return {@code String}
     */
    String getValueOfTableParameter(String tableParameterTitle) {
        return getTableParameterElement(tableParameterTitle).findElement(By.xpath("./following-sibling::td")).getText();
    }
}