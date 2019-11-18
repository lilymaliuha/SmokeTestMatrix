package com.xyleme.bravais.web.pages.cds.cdsconsumerportalpages.homepage;

import com.xyleme.bravais.web.WebPage;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.LabelText;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of the Channels block available on CDS Consumer Portal Home page.
 */
public class ChannelsBlockOnCDSConsumerPortalHomePage extends WebPage<ChannelsBlockOnCDSConsumerPortalHomePage> {

    ChannelsBlockOnCDSConsumerPortalHomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAvailable() {
        return channelsBlockBody().isAvailable();// &&
//                paginationInfo().isAvailable() &&  // Note: Removed in 8.2 due to UI changes.
//                paginationNavigationBlock().isAvailable();
    }

    @Override
    public ChannelsBlockOnCDSConsumerPortalHomePage load() {
        return this;
    }

    private Element channelsBlockBody() {
        return new Element(driver, By.xpath("//div[@id='channels']"));
    }

    private Element channelTile(String channelName) {
        return new Element(driver, By.xpath(channelsBlockBody().getXpathLocatorValue() +
                "//small[text()='" + channelName + "']//ancestor::a[@class='channel-thumbnail']"));
    }

//    private LabelText paginationInfo() {
//        return new LabelText(driver, By.xpath(channelsBlockBody().getXpathLocatorValue() +
//                "//div[@class='pagination-info']"));
//    }

//    private Element paginationNavigationBlock() {
//        return new Element(driver, By.xpath(channelsBlockBody().getXpathLocatorValue()
//                + "//ul[starts-with(@class, 'pagination')]"));
//    }
}