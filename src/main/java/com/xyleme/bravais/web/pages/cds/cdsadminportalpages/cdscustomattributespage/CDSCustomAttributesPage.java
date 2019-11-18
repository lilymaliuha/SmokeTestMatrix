package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdscustomattributespage;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.Select;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.TableOnCustomAttributesPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSPageWithDataTableAndBulkChangesDropDown;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdscustomattributespage.attributecreationforms.AddAttributeForm;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdscustomattributespage.attributecreationforms.attributetypes.BooleanAttributeType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of CDS Custom Attributes page.
 */
public class CDSCustomAttributesPage extends BaseCDSPageWithDataTableAndBulkChangesDropDown {

    public CDSCustomAttributesPage(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                addAttributeButton().isAvailable() &&
                allAttributesDropDown().isAvailable();
    }

    private Select allAttributesDropDown() {
        return new Select(driver, By.id("filter-displayed-attributes"));
    }

    private Button addAttributeButton() {
        return new Button(driver, By.id("add-channel-button"));
    }

    /**
     * Clicks Add Attribute button and returns Add Attribute form.
     *
     * @return {@code AddAttributeForm}
     */
    private AddAttributeForm clickAddAttributeButton() {
        addAttributeButton().click();
        return new AddAttributeForm(driver);
    }

    /**
     * Adds custom attribute of Boolean type.
     *
     * @param attributeName       - Specifies name of the attribute intended to be created
     * @param checkSearchCheckbox - Specifies the decision whether to check Search checkbox or not
     * @return {@code CDSCustomAttributesPage}
     */
    public CDSCustomAttributesPage addCustomAttributeOfBooleanType(String attributeName, boolean checkSearchCheckbox) {
        AddAttributeForm addAttributeForm = clickAddAttributeButton();
        addAttributeForm.enterAttributeNameAndSetSearchParameter(attributeName, checkSearchCheckbox);
        addAttributeForm.selectAttributeType(BooleanAttributeType.class);
        return addAttributeForm.clickAddButton();
    }

    /**
     * Gets Custom Attributes table available on the page.
     *
     * @return {@code TableOnCustomAttributesPage}
     */
    @Override
    public TableOnCustomAttributesPage getDataTable() {
        return new TableOnCustomAttributesPage(driver);
    }
}