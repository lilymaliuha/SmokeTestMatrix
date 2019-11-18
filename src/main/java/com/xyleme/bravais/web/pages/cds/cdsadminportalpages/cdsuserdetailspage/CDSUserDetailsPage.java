package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsuserdetailspage;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.LabelText;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSItemDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSUsersPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsuserdetailspage.panels.*;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsuserdetailspage.panels.identitiespane.IdentitiesPane;
import com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs.UserDeletingConfirmationDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of User Details page.
 */
public class CDSUserDetailsPage extends BaseCDSItemDetailsPage {

    public CDSUserDetailsPage(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                deleteButton().isAvailable();
    }

    private Button deleteButton() {
        return buttonUnderBreadcrumbsBlock("Delete");
    }

    private LabelText firstNameLabel() {
        return new LabelText(driver, By.xpath("//h2[contains(@class, 'first-name')]"));
    }

    private LabelText lastNameLabel() {
        return new LabelText(driver, By.xpath("//h2[contains(@class, 'last-name')]"));
    }

    @Override
    protected LabelText itemDescription() {
        return new LabelText(driver, By.xpath("//h5[contains(@class, 'user-email')]"));
    }

    /**
     * Gets full name of the user displayed on the User Details page.
     *
     * @return {@code String}
     */
    public String getFirstNameOfUser() {
        String firstName = firstNameLabel().waitUntilAvailable().getText();
        String lastName = lastNameLabel().waitUntilAvailable().getText();
        return firstName + " " + lastName;
    }

    /**
     * Deletes the user.
     *
     * @return {@code CDSUsersPage}
     */
    public CDSUsersPage deleteUser() {
        deleteButton().click();
        return new UserDeletingConfirmationDialog(driver).confirmAction(CDSUsersPage.class);
    }

    /**
     * Edits first name of the user.
     *
     * @param newFirstName - Specifies new first name of the user
     * @return {@code CDSUserDetailsPage}
     */
    public CDSUserDetailsPage editFirstName(String newFirstName) {
        editName(firstNameLabel(), newFirstName);
        return this;
    }

    /**
     * Edits last name of the user.
     *
     * @param newLastName - Specifies new last name of the user
     * @return {@code CDSUserDetailsPage}
     */
    public CDSUserDetailsPage editLastName(String newLastName) {
        editName(lastNameLabel(), newLastName);
        return this;
    }

    /**
     * Checks if Identities tab is selected, if not - selects it and returns instance of the respective pane.
     *
     * @return {@code IdentitiesPane}
     */
    public IdentitiesPane selectIdentitiesTabAndGetPane() {
        return selectTabAndGetPane("Identities", IdentitiesPane.class);
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
     * Checks if Feature Access tab is selected, if not - selects it and returns instance of the respective pane.
     *
     * @return {@code FeatureAccessPane}
     */
    public FeatureAccessPane selectFeatureAccessTabAndGetPane() {
        return selectTabAndGetPane("Feature Access", FeatureAccessPane.class);
    }

    /**
     * Checks if Group Membership tab is selected, if not - selects it and returns instance of the respective pane.
     *
     * @return {@code GroupMembershipPane}
     */
    public GroupMembershipPane selectGroupMembershipTabAndGetPane() {
        return selectTabAndGetPane("Group Membership", GroupMembershipPane.class);
    }

    /**
     * Checks if Content Permissions tab is selected, if not - selects it and returns instance of the respective pane.
     *
     * @return {@code ContentPermissionsPane}
     */
    public ContentPermissionsPane selectContentPermissionsTabAndGetPane() {
        return selectTabAndGetPane("Content Permissions", ContentPermissionsPane.class);
    }
}