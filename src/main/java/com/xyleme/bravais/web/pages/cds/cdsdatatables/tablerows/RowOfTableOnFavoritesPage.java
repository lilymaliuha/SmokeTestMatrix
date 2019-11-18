package com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows;

import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSFavoritesPage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.baserows.documentstablerow.BaseDocumentsTableRowWithBulkChanges;
import com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs.RemovingFromFavoritesConfirmationDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Implementation of a Row element of the table available on Favorites page.
 */
public class RowOfTableOnFavoritesPage extends BaseDocumentsTableRowWithBulkChanges {

    public RowOfTableOnFavoritesPage(WebDriver driver, WebElement recordBody) {
        super(driver, recordBody);
    }

    private WebElement rowMovingArrowIcon() {
        return getLastOptionsBlockOfRow().findElement(
                By.xpath(".//a[@class='favorite-move-button' and contains(@style, 'visible')]"));
    }

    /**
     * Defines suggested moving direction of the row by checking if the moving arrow points up.
     *
     * @return {@code boolean}
     */
    private boolean isMovingArrowPointsUp() {
        return rowMovingArrowIcon().findElement(By.xpath("./i")).getAttribute("class").contains("arrow-up");
    }

    /**
     * Moves the row up.
     *
     * @return {@code CDSFavoritesPage}
     */
    public CDSFavoritesPage moveUp() {
        if (isMovingArrowPointsUp()) {
            rowMovingArrowIcon().click();
        }
        return new CDSFavoritesPage(driver);
    }

    /**
     * Moves the row down.
     *
     * @return {@code CDSFavoritesPage}
     */
    public CDSFavoritesPage moveDown() {
        if (!isMovingArrowPointsUp()) {
            rowMovingArrowIcon().click();
        }
        return new CDSFavoritesPage(driver);
    }

    /**
     * Removes the row form Favorites list.
     *
     * @return {@code CDSFavoritesPage}
     */
    public CDSFavoritesPage removeFromFavorites() {
        int initialNumberOfTableRows = getNumberOfTableRows();
        RemovingFromFavoritesConfirmationDialog confirmationDialog = openItemOptionsDropDownAndSelectMenuOption(
                "Remove from Favorites", RemovingFromFavoritesConfirmationDialog.class);
        confirmationDialog.confirmAction();
//        waitUntil(getNumberOfTableRows() == initialNumberOfTableRows - 1); // ToDo: This piece of code is unstable. Work on it.
        System.out.println("Initial number of table rows: " + initialNumberOfTableRows);
        System.out.println("Number of table rows after removing item from Favorites list: " + getNumberOfTableRows());
        return new CDSFavoritesPage(driver);
    }
}