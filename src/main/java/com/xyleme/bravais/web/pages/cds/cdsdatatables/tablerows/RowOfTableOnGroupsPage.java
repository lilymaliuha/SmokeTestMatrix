package com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows;

import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.baserows.BaseCDSTableRowWithBulkChanges;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSGroupsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsgroupdetailspage.CDSGroupDetailsPage;
import com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs.GroupDeletingConfirmationDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Implementation of a Row element of the table available on Groups page.
 */
public class RowOfTableOnGroupsPage extends BaseCDSTableRowWithBulkChanges {

    public RowOfTableOnGroupsPage(WebDriver driver, WebElement recordBody) {
        super(driver, recordBody);
    }

    /**
     * Gets group name.
     *
     * @return {@code String}
     */
    public String getGroupName() {
        return getColumnValue("Name");
    }

    /**
     * Gets number of group members.
     *
     * @return {@code int}
     */
    public int getNumberOfGroupMembers() {
        return Integer.parseInt(getColumnValue("Members"));
    }

    /**
     * Gets group type.
     *
     * @return {@code String}
     */
    public String getGroupType() {
        return getColumnParameterElement("Type").findElement(By.xpath(".//span")).getText();
    }

    /**
     * Gets group creation timestamp.
     *
     * @return {@code String}
     */
    public String getGroupCreationTimeStamp() {
        return getColumnValue("Created");
    }

    /**
     * Clicks the Details link of the group and returns instance of Group Details page.
     *
     * @return {@code CDSGroupDetailsPage}
     */
    public CDSGroupDetailsPage goToGroupDetailsPage() {
        clickDetailsLink();
        return new CDSGroupDetailsPage(driver);
    }

    /**
     * Selects the table row.
     *
     * @return {@code CDSGroupsPage}
     */
    public CDSGroupsPage select() {
        checkOrUncheckRow(true);
        return new CDSGroupsPage(driver);
    }

    /**
     * Deletes the group.
     *
     * @return {@code CDSGroupsPage}
     */
    public CDSGroupsPage deleteGroup() {
        GroupDeletingConfirmationDialog confirmationDialog = openItemOptionsDropDownAndSelectMenuOption(
                "Delete", GroupDeletingConfirmationDialog.class);
        return confirmationDialog.confirmAction(CDSGroupsPage.class);
    }
}