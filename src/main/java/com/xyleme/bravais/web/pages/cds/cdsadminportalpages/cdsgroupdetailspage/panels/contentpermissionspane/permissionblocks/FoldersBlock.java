package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsgroupdetailspage.panels.contentpermissionspane.permissionblocks;

import com.xyleme.bravais.datacontainers.PermissionLevel;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of 'Folders' permissions block available on Content Permissions panel of Group Details page.
 */
public class FoldersBlock extends BasePermissionsBlock {

    public FoldersBlock(WebDriver driver) {
        super(driver, "Folders");
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    /**
     * Adds specified level of permissions to a specified folder.
     *
     * @param folderName      - Specifies the folder the level of permissions is intended to be set
     * @param permissionLevel - Specifies the level of permissions intended to be set for the folder
     * @return {@code FoldersBlock}
     */
    public FoldersBlock addPermission(String folderName, PermissionLevel permissionLevel) {
        return addPermission(folderName, permissionLevel, FoldersBlock.class);
    }
}