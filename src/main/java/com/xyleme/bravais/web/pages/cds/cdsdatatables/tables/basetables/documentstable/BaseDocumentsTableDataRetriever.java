package com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.basetables.documentstable;

import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.baserows.documentstablerow.DocumentsTableRowData;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.basetables.BaseTableDataRetriever;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a Base Documents Table data (row) retriever.
 */
public abstract class BaseDocumentsTableDataRetriever extends BaseTableDataRetriever {

    public BaseDocumentsTableDataRetriever(WebDriver driver) {
        super(driver);
    }

    /**
     * Gets list of document titles which contain specified word or letters sequence.
     *
     * @param matchingWord - Specifies the matching word or sequence of letters
     * @return {List<String>}
     */
    public List<String> getTitlesOfDocumentsWhichContain(String matchingWord) {
        List<String> listOfAllDocumentTitles = new ArrayList<>();
        List<String> listOfMatchedTitles = new ArrayList<>();
        List<DocumentsTableRowData> listOfTableRows = getAllRows();

        for (DocumentsTableRowData row : listOfTableRows) {
            listOfAllDocumentTitles.add(row.getItemName());
        }

        for (String documentTitle : listOfAllDocumentTitles) {
            if (StringUtils.containsIgnoreCase(documentTitle, matchingWord)) {
                listOfMatchedTitles.add(documentTitle);
            }
        }
        if (listOfMatchedTitles.size() == 0) {
            System.out.println("Table doesn't contain documents title of which contain '" + matchingWord + "'!");
        }
        return listOfMatchedTitles;
    }

    /**
     * Checks if a table contains a document with the specified name.
     *
     * @param documentName - Specifies name of the  document
     * @return {@code boolean}
     */
    public boolean doesTableContainDocumentWithName(String documentName) {
        boolean result = false;
        List<DocumentsTableRowData> allTableRows = getAllRows();

        for (DocumentsTableRowData row : allTableRows) {
            if (row.getItemName().equals(documentName)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Gets list of names of all table items.
     *
     * @return {@code ArrayList<String>}
     */
    public ArrayList<String> getNamesOfAllTableItems() {
        ArrayList<String> names = new ArrayList<>();
        List<DocumentsTableRowData> allTableRows = getAllRows();
        for (DocumentsTableRowData row : allTableRows) {
            names.add(row.getItemName());
        }
        return names;
    }
}