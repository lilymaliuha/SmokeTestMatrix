package com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.baserows.documentstablerow;

import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.CDSDocumentDetailsPage;

/**
 * Interface for retrieving data from Base Documents page.
 */
public interface DocumentsTableRowData {

    /**
     * Gets item (document or folder) name.
     *
     * @return {@code String}
     */
    String getItemName();

    /**
     * Gets resource type.
     *
     * @return {@code String}
     */
    String getResourceType();

    /**
     * Gets the 'Updated' value.
     *
     * @return {@code String}
     */
    String getTimeStampOfLastDocumentUpdate();

    /**
     * Gets the 'Created' value.
     *
     * @return {@code String}
     */
    String getTimeStampOfDocumentCreation();

    /**
     * Clicks Details link of the row.
     *
     * @return {@code CDSDocumentDetailsPage}
     */
    CDSDocumentDetailsPage goToDocumentDetails();

    /**
     * Gets item's (document or folder) id.
     *
     * @return {@code String}
     */
    String getId();
}