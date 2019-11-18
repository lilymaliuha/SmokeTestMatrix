package com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows;

import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.baserows.BaseCDSTableRowWithBulkChanges;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdschanneldetailspage.CDSChannelDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSChannelsPage;
import com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs.ChannelDeletingConfirmationDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Implementation of a Row element of the table available on Channels page.
 */
public class RowOfTableOnChannelsPage extends BaseCDSTableRowWithBulkChanges {

    public RowOfTableOnChannelsPage(WebDriver driver, WebElement recordBody) {
        super(driver, recordBody);
    }

    /**
     * Gets name of the channel.
     *
     * @return {@code String}
     */
    public String getChannelName() {
        return getColumnValue("Name");
    }

    /**
     * Gets 'Displayed' checkbox element of the channel.
     *
     * @return {@code WebElement}
     */
    public WebElement getDisplayedCheckbox() {
        return getColumnParameterElement("Displayed").findElement(By.xpath("./input"));
    }

    /**
     * Gets order of the channel.
     *
     * @return {@code String}
     */
    public String getChannelOrder() {
        return getColumnValue("Order");
    }

    /**
     * Gets creation time stamp of the channel.
     *
     * @return {@code String}
     */
    public String getChannelCreationTimeStamp() {
        return getColumnValue("Created");
    }

    /**
     * Clicks the Details link of the channel and returns instance of Channel Details page.
     *
     * @return {@code CDSChannelDetailsPage}
     */
    public CDSChannelDetailsPage goToChannelDetailsPage() {
        clickDetailsLink();
        return new CDSChannelDetailsPage(driver);
    }

    /**
     * Selects the table row.
     *
     * @return {@code CDSChannelsPage}
     */
    public CDSChannelsPage select() {
        checkOrUncheckRow(true);
        return new CDSChannelsPage(driver);
    }

    /**
     * Deletes the channel.
     *
     * @return {@code CDSChannelsPage}
     */
    public CDSChannelsPage deleteChannel() {
        ChannelDeletingConfirmationDialog confirmationDialog = openItemOptionsDropDownAndSelectMenuOption(
                "Delete", ChannelDeletingConfirmationDialog.class);
        return confirmationDialog.confirmAction(CDSChannelsPage.class);
    }
}