package com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.baserows.documentstablerow;

import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.baserows.BaseCDSDataTableRow;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Implementation of a base Consumer Portal Data Table Row.
 */
public abstract class BaseConsumerPortalTableRow extends BaseCDSDataTableRow implements TableRowData {

    public BaseConsumerPortalTableRow(WebDriver driver, WebElement recordBody) {
        super(driver, recordBody);
    }
}