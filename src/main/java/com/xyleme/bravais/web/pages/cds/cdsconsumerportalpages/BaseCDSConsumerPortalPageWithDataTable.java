package com.xyleme.bravais.web.pages.cds.cdsconsumerportalpages;

import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.pages.cds.BaseCDSConsumerPortalPageHeader;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.basetables.BaseCDSDataTable;
import com.xyleme.bravais.web.pages.cds.functionalmobules.filterform.FilterForm;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BaseCDSConsumerPortalPageWithDataTable extends BaseCDSConsumerPortalPageHeader {
    private By parentFilterFormElementLocator = By.xpath("//*[@id='list-filter']");
    private By dynamicNotificationMessageBy = By.xpath("//div[contains(@class, 'notify-message')]");

    BaseCDSConsumerPortalPageWithDataTable(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                getFilterForm().isAvailable();
    }

    private Element dynamicNotificationMessage() {
        return new Element(driver, dynamicNotificationMessageBy);
    }

    /**
     * Gets filter form.
     *
     * @return {@code FilterForm}
     */
    FilterForm getFilterForm() {
        return new FilterForm(driver, parentFilterFormElementLocator);
    }

    /**
     * Gets Documents table on a page.
     *
     * @return {@code BaseCDSDataTable}
     */
    public abstract BaseCDSDataTable getDataTable();

    /**
     * Waits for a dynamic notification appearing, gets the message text and waits for the notification disappearing.
     *
     * @return {@code String}
     */
    public String handleDynamicNotificationAndGetMessageText() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String messageText = wait.until(ExpectedConditions.visibilityOfElementLocated(dynamicNotificationMessageBy)).getText();
//        String messageText = dynamicNotificationMessage().getText();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(dynamicNotificationMessageBy));
        return messageText;
    }
}
