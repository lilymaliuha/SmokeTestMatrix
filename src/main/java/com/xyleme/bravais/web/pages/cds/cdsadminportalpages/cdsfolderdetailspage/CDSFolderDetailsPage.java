package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsfolderdetailspage;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSDocumentsPageItemDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSDocumentsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsfolderdetailspage.panels.PermissionsPane;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsfolderdetailspage.panels.PropertiesPane;
import com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs.ArchivingConfirmationDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of CDS Folder Details page.
 */
public class CDSFolderDetailsPage extends BaseCDSDocumentsPageItemDetailsPage {

    public CDSFolderDetailsPage(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable()  {
        return super.isAvailable() &&
                archiveButton().isAvailable();
    }

    private Button archiveButton() {
        return new Button(driver, By.xpath("//button[normalize-space()='Archive']"));
    }

    /**
     * Edits name of a folder.
     *
     * @param newName - Specifies the name the folder in intended to be renamed to
     * @return {@code CDSFolderDetailsPage}
     */
    public CDSFolderDetailsPage editFolderName(String newName) {
        editName(newName);
        return this;
    }

    /**
     * Archives a folder.
     *
     * @return {@code CDSDocumentsPage}
     */
    public CDSDocumentsPage archiveFolder() {
        archiveButton().click();
        return new ArchivingConfirmationDialog(driver).confirmAction();
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
     * Checks if Permissions tab is selected, if not - selects it and returns instance of the respective pane.
     *
     * @return {@code PermissionsPane}
     */
    public PermissionsPane selectPermissionsTabAndGetPane() {
        return selectTabAndGetPane("Permissions", PermissionsPane.class);
    }
}