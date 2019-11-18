package com.xyleme.bravais.web.pages.cds.cdsadminportalpages;

import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.TableOnRecentDocumentsPage;
import org.openqa.selenium.WebDriver;


/**
 * Implementation of CDS Recent Documents page.
 */
public class CDSRecentDocumentsPage extends BaseCDSPageWithDataTable {

    public CDSRecentDocumentsPage(WebDriver driver) {
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
     * @return {@code TableOnRecentDocumentsPage}
     */
    @Override
    public TableOnRecentDocumentsPage getDataTable() {
        return new TableOnRecentDocumentsPage(driver);
    }
}