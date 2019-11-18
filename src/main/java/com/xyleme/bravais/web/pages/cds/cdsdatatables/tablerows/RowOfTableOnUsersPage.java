package com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows;

import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.baserows.BaseCDSTableRowWithBulkChanges;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSUsersPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsuserdetailspage.CDSUserDetailsPage;
import com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs.UserDeletingConfirmationDialog;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Implementation of a Row element of the table available on Users page.
 */
public class RowOfTableOnUsersPage extends BaseCDSTableRowWithBulkChanges {

    public RowOfTableOnUsersPage(WebDriver driver, WebElement recordBody) {
        super(driver, recordBody);
    }

    /**
     * Gets First Name of the user.
     *
     * @return {@code String}
     */
    public String getFirstName() {
        return getColumnValue("First Name");
    }

    /**
     * Gets Last Name of the user.
     *
     * @return {@code String}
     */
    public String getLastUserName() {
        return getColumnValue("Last Name");
    }

    /**
     * Gets Email of the user.
     *
     * @return {@code String}
     */
    public String getEmail() {
        return getColumnValue("Email");
    }

    /**
     * Gets timestamp of Last Sign-in of the user.
     *
     * @return {@code String}
     */
    public String getLastSignInTimestamp() {
        return getColumnValue("Last Sign-in");
    }

    /**
     * Gets status of the user.
     *
     * @return {@code String}
     */
    public String getStatus() {
        return getColumnValue("Status");
    }

    /**
     * Clicks the Details link of the user and returns instance of User Details page.
     *
     * @return {@code CDSUserDetailsPage}
     */
    public CDSUserDetailsPage goToUserDetailsPage() {
        clickDetailsLink();
        return new CDSUserDetailsPage(driver);
    }

    /**
     * Selects the table row.
     *
     * @return {@code CDSUsersPage}
     */
    public CDSUsersPage select() {
        checkOrUncheckRow(true);
        return new CDSUsersPage(driver);
    }

    /**
     * Enables the user.
     *
     * @return {@code CDSUsersPage}
     */
    public CDSUsersPage enableUser() { // ToDo: Needs to be tested!
        return openItemOptionsDropDownAndSelectMenuOption("Enable", CDSUsersPage.class);
    }

    /**
     * Disables the user.
     *
     * @return {@code CDSUsersPage}
     */
    public CDSUsersPage disableUser() { // ToDo: Needs to be tested!
        return openItemOptionsDropDownAndSelectMenuOption("Disable", CDSUsersPage.class);
    }

    /**
     * Deletes the user.
     *
     * @return {@code CDSUsersPage}
     */
    public CDSUsersPage deleteUser() {
        UserDeletingConfirmationDialog confirmationDialog = openItemOptionsDropDownAndSelectMenuOption(
                "Delete", UserDeletingConfirmationDialog.class);
        return confirmationDialog.confirmAction(CDSUsersPage.class);
    }
}