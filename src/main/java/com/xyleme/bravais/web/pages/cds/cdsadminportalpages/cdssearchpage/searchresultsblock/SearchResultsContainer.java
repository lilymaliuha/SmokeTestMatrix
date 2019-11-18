package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdssearchpage.searchresultsblock;

import com.xyleme.bravais.web.WebPage;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.LabelText;
import com.xyleme.bravais.web.elements.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of Search Results container on CDS Search page.
 */
public class SearchResultsContainer extends WebPage<SearchResultsContainer> {
    By searchResultItemBy = By.xpath("//td[contains(@class, 'result-details')]/parent::tr");

    public SearchResultsContainer(WebDriver driver) {
        super(driver);
    }

    @Override
    public SearchResultsContainer load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return searchResultsBlockBody().isAvailable() &&
                blockHeader().isAvailable() &&
                sortByDropDown().isAvailable() &&
                resultsTableElement().isAvailable();
    }

    private Element searchResultsBlockBody() {
        return new Element(driver, By.xpath(
                "//div[contains(@class, 'results-header')]/parent::div[contains(@class, 'container')]"));
    }

    private LabelText blockHeader() {
        return new LabelText(driver, By.xpath("//span[contains(@class, 'search-results-title')]"));
    }

    private Select sortByDropDown() {
        return new Select(driver, By.id("sort-by-options"));
    }

    private Element resultsTableElement() {
        return new Element(driver, By.xpath("//table[@class='search-results']"));
    }

    /**
     * Gets number of search result items available in the search results block.
     *
     * @return {@code int}
     */
    private int getNumberOfSearchResultItems() {
        return driver.findElements(searchResultItemBy).size();
    }

    /**
     * Gets specific search result item.
     *
     * @param itemIndex - Specifies index of the search result item in the search results list
     * @return {@code SearchResultItem}
     */
    public SearchResultItem getSearchResultItem(int itemIndex) {
        return new SearchResultItem(driver, itemIndex);
    }

    /**
     * Gets list of all search result items available in the search results block.
     *
     * @return {@code List<SearchResultItem>}
     */
    public List<SearchResultItem> getListOfSearchResultItems() {
        List<SearchResultItem> listToReturn = new ArrayList<>();
        int numberOfResultItems = getNumberOfSearchResultItems();
        for (int i = 0; i < numberOfResultItems; i++) {
            listToReturn.add(new SearchResultItem(driver, i));
        }
        return listToReturn;
    }

    /**
     * Gets search result item with specified document name.
     *
     * @param documentName - Specifies the document name
     * @return {@code SearchResultItem}
     */
    public SearchResultItem getSearchResultItemWithDocumentName(String documentName) {
        SearchResultItem resultItemToReturn = null;
        List<SearchResultItem> listOfSearchResultItems = getListOfSearchResultItems();

        for (SearchResultItem resultItem : listOfSearchResultItems) {
            if (resultItem.getDocumentName().equals(documentName)) {
                resultItemToReturn = resultItem;
            }
        }

        if (resultItemToReturn == null) {
            System.out.println("Results container doesn't contain '" + documentName + "' document!");
        }
        return resultItemToReturn;
    }
}