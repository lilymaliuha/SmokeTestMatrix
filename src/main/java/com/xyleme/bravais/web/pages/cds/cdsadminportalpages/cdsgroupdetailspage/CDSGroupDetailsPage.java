package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsgroupdetailspage;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSItemDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSGroupsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsgroupdetailspage.panels.contentpermissionspane.ContentPermissionsPane;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsgroupdetailspage.panels.FeatureAccessPane;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsgroupdetailspage.panels.MembersPane;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsgroupdetailspage.panels.PropertiesPane;
import com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs.GroupDeletingConfirmationDialog;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Group Details page.
 */
public class CDSGroupDetailsPage extends BaseCDSItemDetailsPage {

    public CDSGroupDetailsPage(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable();
    }

    private Button deleteButton() {
        return buttonUnderBreadcrumbsBlock("Delete");
    }

    /**
     * Deletes the group.
     *
     * @return {@code CDSGroupsPage}
     */
    public CDSGroupsPage deleteGroup() {
        deleteButton().waitUntilAvailable().click();
        GroupDeletingConfirmationDialog confirmationDialog = new GroupDeletingConfirmationDialog(driver);
        return confirmationDialog.confirmAction(CDSGroupsPage.class);
    }

    /**
     * Gets name of the group displayed on the page.
     *
     * @return {@code String}
     */
    public String getGroupName() {
        return getName();
    }

    /**
     * Edits name of the group.
     *
     * @param newName - Specifies the name the group is intended to be renamed to
     * @return {@code CDSGroupDetailsPage}
     */
    public CDSGroupDetailsPage editGroupName(String newName) {
        editName(newName);
        return this;
    }

    /**
     * Checks if Members tab is selected, if not - selects it and returns instance of the respective pane.
     *
     * @return {@code MembersPane}
     */
    public MembersPane selectMembersTabAndGetPane() {
        return selectTabAndGetPane("Members", MembersPane.class);
    }

    /**
     * Checks if Feature Access tab is selected, if not - selects it and returns instance of the respective pane.
     *
     * @return {@code FeatureAccessPane}
     */
    public FeatureAccessPane selectFeatureAccessTabAndGetPane() {
        return selectTabAndGetPane("Feature Access", FeatureAccessPane.class);
    }

    /**
     * Checks if Properties tab is selected, if not - selects it and returns instance of the respective pane.
     *
     * @return {@code PropertiesPane}
     */
    public PropertiesPane selectPropertiesTabAndGetPane() {
        return selectTabAndGetPane("Properties", PropertiesPane.class);
    }

    /**
     * Checks Content Permissions tab is selected, if not - selects it and returns instance of the respective pane.
     *
     * @return {@code ContentPermissionsPane}
     */
    public ContentPermissionsPane selectContentPermissionsTabAndGetPane() {
        return selectTabAndGetPane("Content Permissions", ContentPermissionsPane.class);
    }
}