package com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows;

import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.baserows.BaseCDSTableRowWithBulkChanges;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSTrustedApplicationsPage;
import com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs.TrustedAppRemovingConfirmationDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Implementation of a Row element of the table available on Trusted Applications page.
 */
public class RowOfTableOnTrustedApplicationPage extends BaseCDSTableRowWithBulkChanges {

    public RowOfTableOnTrustedApplicationPage(WebDriver driver, WebElement recordBody) {
        super(driver, recordBody);
    }

    /**
     * Gets application name.
     *
     * @return {@code String}
     */
    public String getApplicationName() {
        return getColumnValue("Name");
    }

    /**
     * Gets application's access key.
     *
     * @return {@code String}
     */
    public String getAccessKey() {
        return getColumnValue("Access Key");
    }

    /**
     * Gets application's default user.
     *
     * @return {@code String}
     */
    public String getDefaultUser() {
        return getColumnValue("Default User");
    }

    /**
     * Gets application's validity term.
     *
     * @return {@code String}
     */
    public String getValidityTerm() {
        return getColumnValue("Valid");
    }

    /**
     * Gets application status.
     *
     * @return {@code Syting}
     */
    public String getStatus() {
        return getColumnValue("Status");
    }

    /**
     * Gets secret key link of the row.
     *
     * @param linkName - Specifies name of the link intended to be returned (either 'Show secret key' or 'Generate new key')
     * @return {@code WebElement}
     */
    private WebElement secretKeyLink(String linkName) {
        return getColumnParameterElement("Secret Key").findElement(By.xpath(".//a[normalize-space()='" +
                linkName + "']"));
    }

    /**
     * Gets 'Show secret key' link of the row.
     *
     * @return {@code WebElement}
     */
    public WebElement getShowSecretKeyLink() {
        return secretKeyLink("Show secret key");
    }

    /**
     * Gets 'Generate new key' link of the row.
     *
     * @return {@code WebElement}
     */
    public WebElement getGenerateNewKeyLink() {
        return secretKeyLink("Generate new key");
    }

    /**
     * Removes the application.
     *
     * @return {@code CDSTrustedApplicationsPage}
     */
    public CDSTrustedApplicationsPage removeApplication() {
        TrustedAppRemovingConfirmationDialog confirmationDialog = openItemOptionsDropDownAndSelectMenuOption(
                "Remove", TrustedAppRemovingConfirmationDialog.class);
        return confirmationDialog.confirmAction();
    }
}