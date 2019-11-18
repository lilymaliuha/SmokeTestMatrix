package com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows;

import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.baserows.BaseCDSTableRowWithBulkChanges;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdscustomattributespage.CDSCustomAttributesPage;
import com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs.CustomAttributeDeletingConfirmationDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Implementation of a Row element of the table available on Custom Attributes page.
 */
public class RowOfTableOnCustomAttributesPage extends BaseCDSTableRowWithBulkChanges {

    public RowOfTableOnCustomAttributesPage(WebDriver driver, WebElement recordBody) {
        super(driver, recordBody);
    }

    /**
     * Gets name of the attribute.
     *
     * @return {@code String}
     */
    public String getAttributeName() {
        return getColumnValue("Name");
    }

    /**
     * Gets 'Search' checkbox element of the attribute.
     *
     * @return {@code WebElement}
     */
    public WebElement getSearchCheckbox() {
        return getColumnParameterElement("Search").findElement(By.xpath("./input"));
    }

    /**
     * Gets type of the attribute.
     *
     * @return {@code String}
     */
    public String getAttributeType() {
        return getColumnValue("Type");
    }

    /**
     * Gets attribute's values.
     *
     * @return {@code String}
     */
    public String getAttributeValues() {
        return getColumnValue("Values");
    }

    /**
     * Selects the table row.
     *
     * @return {@code CDSCustomAttributesPage}
     */
    public CDSCustomAttributesPage select() {
        checkOrUncheckRow(true);
        return new CDSCustomAttributesPage(driver);
    }

    /**
     * Deletes the attribute.
     *
     * @return {@code CDSCustomAttributesPage}
     */
    public CDSCustomAttributesPage deleteAttribute() {
        CustomAttributeDeletingConfirmationDialog confirmationDialog = openItemOptionsDropDownAndSelectMenuOption(
                "Delete", CustomAttributeDeletingConfirmationDialog.class);
        return confirmationDialog.confirmAction(CDSCustomAttributesPage.class);
    }
}