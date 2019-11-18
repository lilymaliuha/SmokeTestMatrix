package com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows;

import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.baserows.documentstablerow.BaseDocumentsTableRowWithoutBulkChanges;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Implementation of a Row element of the table available on Latest Updates page.
 */
public class RowOfTableOnLatestUpdatesPage extends BaseDocumentsTableRowWithoutBulkChanges {

    public RowOfTableOnLatestUpdatesPage(WebDriver driver, WebElement recordBody) {
        super(driver, recordBody);
    }
}