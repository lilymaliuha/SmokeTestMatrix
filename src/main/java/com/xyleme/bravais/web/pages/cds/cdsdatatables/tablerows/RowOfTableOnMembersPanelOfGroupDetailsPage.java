package com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows;

import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.baserows.BaseRowOfDataTableWithOuterContainer;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsgroupdetailspage.CDSGroupDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsuserdetailspage.CDSUserDetailsPage;
import com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs.RemovingGroupMemberConfirmationDialog;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of a Row element of the table available on Members panel of Group Details page.
 */
public class RowOfTableOnMembersPanelOfGroupDetailsPage extends BaseRowOfDataTableWithOuterContainer {

    public RowOfTableOnMembersPanelOfGroupDetailsPage(WebDriver driver, Element tableContainer, Element rowBodyElement) {
        super(driver, tableContainer, rowBodyElement);
    }

    /**
     * Gets First Name of the member.
     *
     * @return {@code String}
     */
    public String getFirstName() {
        return getColumnValue("First Name");
    }

    /**
     * Gets Last Name of the member.
     *
     * @return {@code String}
     */
    public String getLastName() {
        return getColumnValue("Last Name");
    }

    /**
     * Gets Email of the member.
     *
     * @return {@code String}
     */
    public String getEmail() {
        return getColumnValue("Email");
    }

    /**
     * Gets Status of the member.
     *
     * @return {@code String}
     */
    public String getStatus() {
        return getColumnValue("Status");
    }

    /**
     * Clicks the Details link of the member and returns instance of User Details page.
     *
     * @return {@code CDSUserDetailsPage}
     */
    public CDSUserDetailsPage goToUserDetailsPage() {
        clickDetailsLink();
        return new CDSUserDetailsPage(driver);
    }

    /**
     * Removes the member from the members list.
     *
     * @return {@code CDSGroupDetailsPage}
     */
    public CDSGroupDetailsPage removeGroupMember() {
        Element optionsMenuElement = openItemOptionsMenuAndReturnOpenedMenuElement();
        getOptionOfOpenedItemOptionsMenu(optionsMenuElement, "Remove").click();
        RemovingGroupMemberConfirmationDialog confirmationDialog = new RemovingGroupMemberConfirmationDialog(driver);
        return confirmationDialog.confirmAction();
    }
}