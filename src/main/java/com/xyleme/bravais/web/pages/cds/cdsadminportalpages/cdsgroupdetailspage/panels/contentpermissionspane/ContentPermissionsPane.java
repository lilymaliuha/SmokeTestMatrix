package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsgroupdetailspage.panels.contentpermissionspane;

import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSPanelOnDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsgroupdetailspage.panels.contentpermissionspane.permissionblocks.ChannelsBlock;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsgroupdetailspage.panels.contentpermissionspane.permissionblocks.DocumentsBlock;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsgroupdetailspage.panels.contentpermissionspane.permissionblocks.FoldersBlock;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Content Permissions pane on Group Details page.
 */
public class ContentPermissionsPane extends BaseCDSPanelOnDetailsPage {

    public ContentPermissionsPane(WebDriver driver) {
        super(driver);
    }

    @Override
    public ContentPermissionsPane load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    /**
     * Gets 'Folders' permissions block.
     *
     * @return {@code FoldersBlock}
     */
    public FoldersBlock getFolderPermissionsBlock() {
        return new FoldersBlock(driver);
    }

    /**
     * Gets 'Documents' permissions block.
     *
     * @return {@code DocumentsBlock}
     */
    public DocumentsBlock getDocumentPermissionsBlock() {
        return new DocumentsBlock(driver);
    }

    /**
     * Gets 'Channels' permissions block.
     *
     * @return {@code ChannelsBlock}
     */
    public ChannelsBlock getChannelPermissionsBlock() {
        return new ChannelsBlock(driver);
    }
}