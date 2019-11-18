package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsgroupdetailspage.panels.contentpermissionspane.permissionblocks;

import com.xyleme.bravais.datacontainers.PermissionLevel;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of 'Channels' permissions block available on Content Permissions panel of Group Details page.
 */
public class ChannelsBlock extends BasePermissionsBlock {

    public ChannelsBlock(WebDriver driver) {
        super(driver, "Channels");
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    /**
     * Adds specified level of permissions to a specified channel.
     *
     * @param channelName     - Specifies the channel the level of permissions is intended to be set
     * @param permissionLevel - Specifies the level of permissions intended to be set for the channel
     * @return {@code ChannelsBlock}
     */
    public ChannelsBlock addPermission(String channelName, PermissionLevel permissionLevel) {
        return addPermission(channelName, permissionLevel, ChannelsBlock.class);
    }
}