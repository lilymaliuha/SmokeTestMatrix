package com.xyleme.bravais.web.pages.cds.cdsadminportalpages;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.Select;
import com.xyleme.bravais.web.elements.TextInput;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnChannelsPage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.TableOnChannelsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdschanneldetailspage.CDSChannelDetailsPage;
import com.xyleme.bravais.web.pages.cds.functionalmobules.additemform.BaseAddItemForm;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of CDS Channels page.
 */
public class CDSChannelsPage extends BaseCDSPageWithDataTableAndBulkChangesDropDown {

    public CDSChannelsPage(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                allChannelsDropDown().isAvailable() &&
                addChannelButton().isAvailable();
    }

    private Select allChannelsDropDown() {
        return new Select(driver, By.id("filter-displayed-channels"));
    }

    private Button addChannelButton() {
        return new Button(driver, By.id("add-channel-button"));
    }

    /**
     * Filters out channels available in the Channels table by channel name and returns the first table row object in
     * case the channel the row belongs to matches the expected channel.
     *
     * @param channelName - Specifies the name of the channel expected to be returned (filtering criteria)
     * @param fullMatch   - Specifies filtering type (true - full match / false - part match)
     * @return {@code RowOfTableOnChannelsPage}
     */
    public RowOfTableOnChannelsPage getFilteredChannel(String channelName, boolean fullMatch) {
        enterQueryIntoFilterInputField(channelName);
        RowOfTableOnChannelsPage firstTableRowAfterFiltering = getDataTable().getRow(0);
        String filteredChannelName = firstTableRowAfterFiltering.getChannelName();
        boolean condition = fullMatch ? filteredChannelName.equals(channelName) : filteredChannelName.startsWith(channelName);
        if (condition) {
            return firstTableRowAfterFiltering;
        } else {
            throw new RuntimeException("Table on Channels page doesn't contain channel with name '" + channelName + "'!");
        }
    }

    /**
     * Creates a new channel with specified parameters.
     *
     * @param channelName        - Specifies name of the channel intended to be created
     * @param channelDescription - Specifies description of the channel intended to be created
     * @param channelQuery       - Specifies query of the channel intended to be created
     * @return {@code CDSChannelDetailsPage}
     */
    public CDSChannelDetailsPage createNewChannel(String channelName, String channelDescription, String channelQuery) {
        addChannelButton().click();
        return new AddChannelForm(driver).fillOutAndSubmitForm(channelName, channelDescription, channelQuery);
    }

    /**
     * Gets Channels table available on the page.
     *
     * @return {@code TableOnChannelsPage}
     */
    @Override
    public TableOnChannelsPage getDataTable() {
        return new TableOnChannelsPage(driver);
    }

    /**
     * Implementation of Add Channel form which appears after clicking +Add Channel button.
     */
    private class AddChannelForm extends BaseAddItemForm {

        AddChannelForm(WebDriver driver) {
            super(driver);
            this.waitUntilAvailable();
        }

        @Override
        public boolean isAvailable() {
            return super.isAvailable() &&
                    nameInputField().isAvailable() &&
                    descriptionInputField().isAvailable() &&
                    queryInputField().isAvailable();
        }

        @Override
        protected Element parentFormElement() {
            return new Element(driver, By.xpath("//form[@name='createChannelForm']"));
        }

        private TextInput nameInputField() {
            return new TextInput(driver, By.id("new-channel-name"));
        }

        private TextInput descriptionInputField() {
            return new TextInput(driver, By.id("new-channel-description"));
        }

        private TextInput queryInputField() {
            return new TextInput(driver, By.id("new-channel-query"));
        }

        /**
         * Fills out the form with specified data and clicks Add button.
         *
         * @param channelName        - Specifies name of the channel intended to be created
         * @param channelDescription - Specifies description of the channel intended to be created
         * @param channelQuery       - Specifies query of the channel intended to be created
         * @return {@code CDSChannelDetailsPage}
         */
        private CDSChannelDetailsPage fillOutAndSubmitForm(String channelName, String channelDescription, String channelQuery) {
            fillOutInputField(nameInputField(), channelName);
            fillOutInputField(descriptionInputField(), channelDescription);
            fillOutInputField(queryInputField(), channelQuery);
            return clickAddButton(CDSChannelDetailsPage.class);
        }
    }
}