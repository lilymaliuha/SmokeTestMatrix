package com.xyleme.bravais.web.pages.analytics.recordtables.records;

import com.xyleme.bravais.web.pages.analytics.recordtables.BaseRecordsTable;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Implementation of Base Record of Base Analytics Records Table.
 */
abstract class BaseRecord extends BaseRecordsTable {
    WebElement recordBody;

    BaseRecord(WebDriver driver, WebElement recordBody) {
        super(driver);
        this.recordBody = recordBody;
    }

    /**
     * Gets table record value of specified column.
     *
     * @param columnTitle - Specifies column title
     * @return {@code String}
     */
    String getRecordValueOfColumn(String columnTitle) {
        return getElementAvoidingStaleReferenceException(recordBody.findElements(By.xpath("./td"))
                .get(getColumnIndex(columnTitle))).getText();
    }

    /**
     * Gets number of the statement record in the table.
     *
     * @return {@code int}
     */
    public int getSequenceNumber() {
        return Integer.parseInt(getRecordValueOfColumn("#").replaceAll("\\D+", ""));
    }
}