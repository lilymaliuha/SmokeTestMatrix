package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdshomepage.channelsblock;

import com.xyleme.bravais.web.WebPage;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.LabelText;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdssearchpage.CDSSearchPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Channels block on Home Page.
 */
public class ChannelsBlock extends WebPage<ChannelsBlock> {

    public ChannelsBlock(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return channelsBlockBody().isAvailable() &&
                paginationInfo().isAvailable() &&
                paginationNavigationBlock().isAvailable();
    }

    @Override
    public ChannelsBlock load() {
        return this;
    }

    private Element channelsBlockBody() {
        return new Element(driver, By.xpath("//div[@id='channels']"));
    }

    private Element channelTile(String channelName) {
        return new Element(driver, By.xpath(channelsBlockBody().getXpathLocatorValue() +
                "//small[text()='" + channelName + "']//ancestor::a[@class='channel-thumbnail']"));
    }

    private LabelText paginationInfo() {
        return new LabelText(driver, By.xpath(channelsBlockBody().getXpathLocatorValue() +
                "//div[@class='pagination-info']"));
    }

    private Element paginationNavigationBlock() {
        return new Element(driver, By.xpath(channelsBlockBody().getXpathLocatorValue()
                + "//ul[starts-with(@class, 'pagination')]"));
    }

    /**
     * Opens specified channel.
     *
     * @param channelName - Specifies name of the channel intended to be opened
     * @return {@code CDSSearchPage}
     */
    public CDSSearchPage goToChannel(String channelName) {
        channelTile(channelName).waitUntilAvailable().click();
        return new CDSSearchPage(driver);
    }
}
