package com.xyleme.bravais.web.pages.cds.cdsadminportalpages;

import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.TableOnLatestUpdatesPage;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of CDS Latest Updates page.
 */
public class CDSLatestUpdatesPage extends BaseCDSPageWithDataTable {

    public CDSLatestUpdatesPage(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable();
    }

    /**
     * Gets Documents table available on the page.
     *
     * @return {@code TableOnLatestUpdatesPage}
     */
    @Override
    public TableOnLatestUpdatesPage getDataTable() {
        return new TableOnLatestUpdatesPage(driver);
    }
}