package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdschanneldetailspage.panels;

import com.xyleme.bravais.datacontainers.channelconfigurationdata.SortingOption;
import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.CheckBox;
import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.Link;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSPanelOnDetailsPage;
import com.xyleme.bravais.web.pages.cds.popupdialogs.nodeselectioninnodestreedialog.classificationselectiondialog.ManageClassificationDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Query pane on CDS Channel Details page.
 */
public class QueryPane extends BaseCDSPanelOnDetailsPage {

    public QueryPane(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public QueryPane load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return queryBlockElement().isAvailable() &&
                facetsBlockElement().isAvailable() &&
                facetContainer("Resource Type").isAvailable() &&
                facetContainer("Format").isAvailable() &&
                facetContainer("Language").isAvailable() &&
                classificationsFacetContainer().isAvailable() &&
                setFilterButtonOfClassificationsFacet().isAvailable() &&
                sortOptionsFacetContainer().isAvailable();
    }

    private Element paneBlockElement(String blockName) {
        return new Element(driver, By.xpath("//div[contains(@class, 'panel-heading') and text()='" + blockName +
                "']/parent::div"));
    }

    private Element queryBlockElement() {
        return paneBlockElement("Query");
    }

    private Element facetsBlockElement() {
        return paneBlockElement("Facets");
    }

    private Element facetContainer(String facetHeader) {
        return new Element(driver, By.xpath("//b[text()='" + facetHeader +
                "']/ancestor::div[starts-with(@class, 'channel-facet ')]"));
    }

    private Link clearLinkOfFacet(String facetHeader) {
        return new Link(driver, By.xpath(facetContainer(facetHeader).getXpathLocatorValue() +
                "//small[text()='Clear']/parent::a"));
    }

    private CheckBox facetCheckbox(String facetHeader, String checkboxName) {
        return new CheckBox(driver, By.xpath(facetContainer(facetHeader).getXpathLocatorValue() + "//span[text()='" +
                checkboxName + "']/preceding-sibling::input"));
    }

    private Element classificationsFacetContainer() {
        return new Element(driver, By.id("classifications-filter"));
    }

    private Button setFilterButtonOfClassificationsFacet() {
        return new Button(driver, By.xpath("//button[normalize-space()='Set Filter']"));
    }

    private Element sortOptionsFacetContainer() {
        return new Element(driver, By.xpath("//div[@id='sort-filter']"));
    }

    private CheckBox radioButtonOfSortingOption(SortingOption option) {
        return new CheckBox(driver, By.xpath(sortOptionsFacetContainer().getXpathLocatorValue() + "//span[text()='" +
                option.getValue() + "']/parent::td/preceding-sibling::td[contains(@class, 'checkbox')]/input"));
    }

    /**
     * Selects specified option/s (checkbox) of a specified facet in Facets form.
     *
     * @param facetName    - Specifies name of the facet
     * @param facetOptions - Specifies fact option (checkbox) intended to be selected
     * @return {@code QueryPane}
     */
    public QueryPane selectFacetOption(String facetName, String... facetOptions) {
        if (facetOptions.length > 1) {
            for (String facetOption : facetOptions) {
                facetCheckbox(facetName, facetOption).waitUntilAvailable().select();
            }
        } else {
            facetCheckbox(facetName, facetOptions[0]).waitUntilAvailable().select();
        }
        return this;
    }

    /**
     * Unslects specified option/s (checkbox) of a specified facet in Facets form.
     *
     * @param facetName    - Specifies name of the facet
     * @param facetOptions - Specifies fact option (checkbox) intended to be unselected
     * @return {@code QueryPane}
     */
    public QueryPane unselectFacetOption(String facetName, String... facetOptions) {
        if (facetOptions.length > 1) {
            for (String facetOption : facetOptions) {
                facetCheckbox(facetName, facetOption).waitUntilAvailable().unselect();
            }
        } else {
            facetCheckbox(facetName, facetOptions[0]).waitUntilAvailable().unselect();
        }
        return this;
    }

    /**
     * Clicks Clear link of the specified facet.
     *
     * @param facetName - Specifies name of the faced Clear link of which is intended to be clicked
     * @return {@code QueryPane}
     */
    public QueryPane clearFacetSelection(String facetName) {
        clearLinkOfFacet(facetName).waitUntilAvailable().click();
        return this;
    }

    /**
     * Selects specified classification element in Facets form.
     *
     * @param pathToClassificationElement - Specifies path to the classification element in the classification nodes tree
     * @return {@code QueryPane}
     */
    public QueryPane selectClassificationElement(String pathToClassificationElement) {
        setFilterButtonOfClassificationsFacet().click();
        return new ManageClassificationDialog(driver).selectClassificationElement(pathToClassificationElement).save(
                QueryPane.class);
    }

    /**
     * Selects specified sorting option in Facets form.
     *
     * @param sortingOption - Specifies sorting option intended to be selected
     * @return {@code QueryPane}
     */
    public QueryPane selectSortingOption(SortingOption sortingOption) {
        radioButtonOfSortingOption(sortingOption).waitUntilAvailable().click();
        return this;
    }
}