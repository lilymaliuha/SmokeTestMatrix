package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdssearchpage.searchresultsblock;

import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.LabelText;
import com.xyleme.bravais.web.elements.Link;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.CDSDocumentDetailsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Implementation of a search result item available after searching by a specific query on the Search page.
 */
public class SearchResultItem extends SearchResultsContainer {
    private Element itemBody;

    SearchResultItem(WebDriver driver, int itemIndex) {
        super(driver);
        itemBody = new Element(driver, By.xpath("(" + getXpathLocatorValue(searchResultItemBy) + ")["
                + (itemIndex + 1) + "]")).waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return itemBody.isAvailable() &&
                documentIconElement().isAvailable() &&
                documentNameElement().isAvailable() &&
                breadcrumbElement().isAvailable() &&
                documentResourceTypeElement().isAvailable();
    }

    private Element documentIconElement() {
        return new Element(driver, By.xpath(itemBody.getXpathLocatorValue() + "//td[contains(@class, 'document-icon')]"));
    }

    private LabelText documentNameElement() {
        return new LabelText(driver, By.xpath(itemBody.getXpathLocatorValue() + "//a[contains(@class, 'document-name')]"));
    }

    private Element breadcrumbElement() {
        return new Element(driver, By.xpath(itemBody.getXpathLocatorValue() + "//ol[contains(@class, 'breadcrumb')]"));
    }

    private LabelText documentResourceTypeElement() {
        return new LabelText(driver, By.xpath(itemBody.getXpathLocatorValue() + "//span[contains(@class, 'resource-type')]"));
    }

    private List<WebElement> getListOfDocumentStructureElements() {
        return getElementsDynamically(By.xpath(itemBody.getXpathLocatorValue() +
                "//div[@class='snippets']/following-sibling::div/a[not(contains(@class, 'hide'))]"));
    }

    private Link moreResultsLink() {
        return new Link(driver, By.xpath(itemBody.getXpathLocatorValue() +
                "//a/div[starts-with(normalize-space(), 'More results from')]"));
    }

    /**
     * Gets document name.
     *
     * @return {@code String}
     */
    public String getDocumentName() {
        return documentNameElement().waitUntilAvailable().getText();
    }

    /**
     * Gets location path of the document.
     *
     * @return {@code String}
     */
    public String getDocumentLocationPath() {
        String pathToReturn = "";
        List<WebElement> pathItems = getElementsDynamically(By.xpath(breadcrumbElement().getXpathLocatorValue() + "/li/a"));
        for (WebElement pathItem : pathItems) {
            pathToReturn = pathToReturn + pathItem.getText() + "|";
        }
        return pathToReturn.substring(0, pathToReturn.lastIndexOf("|"));
    }

    /**
     * Clicks on a document name (title of the search result item) and returns instance of Document Details page.
     *
     * @return {@code CDSDocumentDetailsPage}
     */
    public CDSDocumentDetailsPage goToDocument() {
        documentNameElement().waitUntilAvailable().click();
        return new CDSDocumentDetailsPage(driver);
    }

    /**
     * Gets name of a specific document structure element.
     *
     * @param elementIndex - Specifies index of the element in the list of document structure elements
     * @return {@code String}
     */
    public String getNameOfDocumentStructureElement(int elementIndex) {
        return getListOfDocumentStructureElements().get(elementIndex).getText();
    }

    /**
     * Clicks specific document structure element and returns instance of Document Details page.
     *
     * @param elementIndex - Specifies index of the element in the list of document structure elements
     * @return {@code <TBD>} ToDo: Should return DocumentStructureElementDetails page.
     */
    public void goToDocumentStructureElement(int elementIndex) {
        getListOfDocumentStructureElements().get(elementIndex).click();
    }

    /**
     * Expands collapsed list of document structure elements.
     *
     * @return {@code SearchResultItem}
     */
    public SearchResultItem expandListOfDocumentStructureElements() {
        moreResultsLink().waitUntilAvailable().click();
        return this;
    }
}