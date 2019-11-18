package com.xyleme.bravais.web.pages.cds.cdsadminportalpages;

import com.xyleme.bravais.web.WebPage;
import org.openqa.selenium.WebDriver;

/**
 * Class for a base CDS Panel on a base Details page (used as a unification type for all panels on all details pages
 * which is used in generic method selectTabAndGetPane in BaseCDSItemDetailsPage class).
 */
public abstract class BaseCDSPanelOnDetailsPage extends WebPage<BaseCDSPanelOnDetailsPage> {

    public BaseCDSPanelOnDetailsPage(WebDriver driver) {
        super(driver);
    }
}