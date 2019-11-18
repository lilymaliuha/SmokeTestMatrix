package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdspreferencespage.configurationblocks;

import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.TextInput;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Document Download configuration block.
 */
public class DocumentDownloadConfigBlock extends BaseConfigBlock {

    public DocumentDownloadConfigBlock(WebDriver driver, Element blockElement) {
        super(driver, blockElement);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return maximumNumberOfFilesInBulkConfigItemContainerBody().isAvailable() &&
                maximumNumberOfFilesInBulkConfigInput().isAvailable();
    }

    private Element maximumNumberOfFilesInBulkConfigItemContainerBody() {
        return configurationItemContainerBody("Maximum number of Files in Bulk");
    }

    private TextInput maximumNumberOfFilesInBulkConfigInput() {
        return new TextInput(driver, configurationInputLocator("Maximum number of Files in Bulk",
                "up to 10,000"));
    }

    /**
     * Sets specified value into the text input field of "Maximum number of Files in Bulk" configuration.
     *
     * @param number - Specifies the value intended to be set into the text input field
     * @param save   - Specifies the decision whether to save the changes or not (clicks 'Save' button in case of 'true')
     * @return {@code DocumentDownloadConfigBlock}
     */
    public DocumentDownloadConfigBlock setMaximumNumberOfFilesInBulk(int number, boolean save) {
        maximumNumberOfFilesInBulkConfigInput().clear();
        maximumNumberOfFilesInBulkConfigInput().sendKeys(String.valueOf(number));
        if (save) {
            clickSaveButton();
        }
        return this;
    }
}