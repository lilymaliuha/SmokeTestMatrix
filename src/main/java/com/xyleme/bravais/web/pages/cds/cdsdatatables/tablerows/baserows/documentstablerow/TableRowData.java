package com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.baserows.documentstablerow;

/**
 * Interface for retrieving data from Base Data Table row.
 */
public interface TableRowData {

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
}