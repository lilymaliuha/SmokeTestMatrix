package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdschanneldetailspage;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSItemDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdssearchpage.CDSSearchPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdschanneldetailspage.panels.PermissionsPane;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdschanneldetailspage.panels.PropertiesPane;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdschanneldetailspage.panels.QueryPane;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Channel Details page.
 */
public class CDSChannelDetailsPage extends BaseCDSItemDetailsPage {

    public CDSChannelDetailsPage(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                channelImageElement().isAvailable() &&
                searchButton().isAvailable() &&
                deleteButton().isAvailable();
    }

    private Element channelImageElement() {
        return new Element(driver, By.xpath("//div[@class='btn-file']"));
    }

    private Button searchButton() {
        return buttonUnderBreadcrumbsBlock("Search");
    }

    private Button deleteButton() {
        return buttonUnderBreadcrumbsBlock("Delete");
    }

    /**
     * Edits original name of the channel to the specified one.
     *
     * @param newName - Specifies the new channel name
     * @return {@code CDSChannelDetailsPage}
     */
    public CDSChannelDetailsPage editChannelName(String newName) {
        editName(newName);
        return this;
    }

    /**
     * Gets name of the channel the details page is opened for.
     *
     * @return {@code String}
     */
    public String getChannelName() {
        return getName();
    }

    /**
     * Clicks Search button and returns instance of Search page.
     *
     * @return {@code CDSSearchPage}
     */
    public CDSSearchPage clickSearchButton() {
        searchButton().clickUsingJS();
        waitForAngularJSProcessing();
        return new CDSSearchPage(driver);
    }

    /**
     * Checks if Query tab is selected, if not - selects it and returns instance of the respective pane.
     *
     * @return {@code QueryPane}
     */
    public QueryPane selectQueryTabAndGetPane() {
        return selectTabAndGetPane("Query", QueryPane.class);
    }

    /**
     * Checks if Properties tab is selected, if not - selects it and returns instance of the respective pane.
     *
     * @return {@code PropertiesPane}
     */
    public PropertiesPane selectPropertiesTabAndGetPane() {
        return selectTabAndGetPane("Properties", PropertiesPane.class);
    }

    /**
     * Checks if Permissions tab is selected, if not - selects it and returns instance of the respective pane.
     *
     * @return {@code PermissionsPane}
     */
    public PermissionsPane selectPreviewTabAndGetPane() {
        return selectTabAndGetPane("Permissions", PermissionsPane.class);
    }
}