package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsgroupdetailspage.panels.contentpermissionspane.permissionblocks;

import com.xyleme.bravais.datacontainers.PermissionLevel;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of 'Documents' permissions block available on Content Permissions panel of Group Details page.
 */
public class DocumentsBlock extends BasePermissionsBlock {

    public DocumentsBlock(WebDriver driver) {
        super(driver, "Documents");
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    /**
     * Adds specified level of permissions to a specified document.
     *
     * @param documentName    - Specifies the document the level of permissions is intended to be set
     * @param permissionLevel - Specifies the level of permissions intended to be set for the document
     * @return {@code DocumentsBlock}
     */
    public DocumentsBlock addPermission(String documentName, PermissionLevel permissionLevel) {
        return addPermission(documentName, permissionLevel, DocumentsBlock.class);
    }
}