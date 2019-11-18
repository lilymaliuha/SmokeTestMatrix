package com.xyleme.bravais.web.pages.cds.cdsconsumerportalpages;

import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.LabelText;
import com.xyleme.bravais.web.elements.Link;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnConsumerPortalDocumentsPage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.TableOnDocumentsPageOfConsumerPortal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Consumer Portal Documents page.
 */
public class CDSConsumerPortalDocumentsPage extends BaseCDSConsumerPortalPageWithDataTable {

    public CDSConsumerPortalDocumentsPage(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    public CDSConsumerPortalDocumentsPage(WebDriver driver, boolean waitForPageToLoad) {
        super(driver);

        if (waitForPageToLoad) {
            this.waitUntilAvailable();
        }
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                getFilterForm().isAvailable() &&
                breadCrumbElement().isAvailable() &&
                footerLabelWithBuildNumber().isAvailable();
    }

    private Element breadCrumbElement() {
        return new Element(driver, By.xpath("//div[contains(@class, 'breadcrumb')]"));
    }

    private Link linkToFolderOnBreadCrumbElement(String folderName) {
        return new Link(driver, By.xpath(breadCrumbElement().getXpathLocatorValue() + "//a[normalize-space()='" +
                folderName + "']"));
    }

    private LabelText currentFolderLabelOnBreadCrumbElement() {
        return new LabelText(driver, By.xpath(breadCrumbElement().getXpathLocatorValue() +
                "//li[starts-with(@class, 'active')]/span"));
    }

    /**
     * Gets name of the opened folder (the folder the user is located in).
     *
     * @return {@code String}
     */
    public String getNameOfCurrentFolder() {
        return currentFolderLabelOnBreadCrumbElement().getText();
    }

    /**
     * Clicks link in the bread crumbs block which leads to the specified outer folder.
     *
     * @param folderName - Specifies the outer folder intended to be navigated to
     * @return {@code CDSConsumerPortalDocumentsPage}
     */
    public CDSConsumerPortalDocumentsPage goToOuterFolderViaLinkInBreadCrumbsBlock(String folderName) {
        linkToFolderOnBreadCrumbElement(folderName).waitUntilAvailable().clickUsingJS();
        return this;
    }

    /**
     * Enters specified text item into the 'Filter Documents' input field and returns the first table row available
     * after the filtering.
     *
     * @param filteringItem - Specifies the item intended to be entered into the filter field
     * @param fullMatch     - Specifies type of the filtering (true - full match / false - part match)
     * @return {@code RowOfTableOnConsumerPortalDocumentsPage}
     */
    public RowOfTableOnConsumerPortalDocumentsPage getFilteredTableItem(String filteringItem, boolean fullMatch) {
        getFilterForm().enterQueryIntoFilterInputField(filteringItem);
        RowOfTableOnConsumerPortalDocumentsPage firstTableRow = getDataTable().getRow(0);
        String name = firstTableRow.getItemName();
        boolean condition = fullMatch ? name.equals(filteringItem) : name.startsWith(filteringItem);

        if (condition) {
            return firstTableRow;
        } else {
            throw new RuntimeException("Destination folder doesn't contain item with name '" + filteringItem + "'.");
        }
    }

    /**
     * Navigates to the specified location.
     *
     * @param pathToDestinationFolder - Specifies the path to the location intended to accessed
     * @return {@code CDSConsumerPortalDocumentsPage}
     */
    public CDSConsumerPortalDocumentsPage navigateTo(String pathToDestinationFolder) {
        String[] pathElements = pathToDestinationFolder.split("\\|");

        for (String pathElement : pathElements) {
            getFilteredTableItem(pathElement, true).openFolder();
            waitForAngularJSProcessing();
        }
        return this;
    }

    /**
     * Gets Documents table.
     *
     * @return {@code TableOnDocumentsPageOfConsumerPortal}
     */
    @Override
    public TableOnDocumentsPageOfConsumerPortal getDataTable() {
        return new TableOnDocumentsPageOfConsumerPortal(driver);
    }
}