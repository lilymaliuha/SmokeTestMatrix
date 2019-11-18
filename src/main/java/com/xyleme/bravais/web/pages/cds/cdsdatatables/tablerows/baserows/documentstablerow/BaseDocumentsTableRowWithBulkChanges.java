package com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.baserows.documentstablerow;

import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.baserows.BaseCDSTableRowWithBulkChanges;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.CDSDocumentDetailsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Implementation of Base Row element with Bulk Changes options of Base Documents table in CDS.
 */
public abstract class BaseDocumentsTableRowWithBulkChanges extends BaseCDSTableRowWithBulkChanges implements DocumentsTableRowData {

    public BaseDocumentsTableRowWithBulkChanges(WebDriver driver, WebElement recordBody) {
        super(driver, recordBody);
    }

    /**
     * Gets item (document or folder) name.
     *
     * @return {@code String}
     */
    @Override
    public String getItemName() {
//        getColumnParameterElement("Name").click();
//        return getColumnParameterElement("Name").findElement(By.xpath("./a")).getText();
        return getColumnParameterElement("Name").getText();
    }

    /**
     * Gets resource type.
     *
     * @return {@code String}
     */
    @Override
    public String getResourceType() {
        return getColumnValue("Resource Type");
    }

    /**
     * Gets the 'Updated' value.
     *
     * @return {@code String}
     */
    @Override
    public String getTimeStampOfLastDocumentUpdate() {
        return getColumnValue("Updated");
    }

    /**
     * Gets the 'Created' value.
     *
     * @return {@code String}
     */
    @Override
    public String getTimeStampOfDocumentCreation() {
        return getColumnValue("Created");
    }

    /**
     * Clicks Details link of the row.
     *
     * @return {@code CDSDocumentDetailsPage}
     */
    @Override
    public CDSDocumentDetailsPage goToDocumentDetails() {
        clickDetailsLink();
        return new CDSDocumentDetailsPage(driver);
    }

    /**
     * Gets item's (document or folder) id.
     *
     * @return {@code String}
     */
    @Override
    public String getId() {
        return getRowItemId();
    }
}