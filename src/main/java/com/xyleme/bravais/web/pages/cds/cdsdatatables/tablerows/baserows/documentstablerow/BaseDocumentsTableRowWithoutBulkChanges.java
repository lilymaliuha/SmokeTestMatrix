package com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.baserows.documentstablerow;

import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.baserows.BaseCDSDataTableRow;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.CDSDocumentDetailsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Implementation of Base Row element of Base Documents table in CDS.
 */
public abstract class BaseDocumentsTableRowWithoutBulkChanges extends BaseCDSDataTableRow implements DocumentsTableRowData {

    protected BaseDocumentsTableRowWithoutBulkChanges(WebDriver driver, WebElement recordBody) {
        super(driver, recordBody);
    }

    /**
     * Gets item (document or folder) name.
     *
     * @return {@code String}
     */
    @Override
    public String getItemName() {
        return getColumnValue("Name");
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