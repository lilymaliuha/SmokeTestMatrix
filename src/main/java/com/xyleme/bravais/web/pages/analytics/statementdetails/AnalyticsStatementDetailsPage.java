package com.xyleme.bravais.web.pages.analytics.statementdetails;

import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.LabelText;
import com.xyleme.bravais.web.elements.Link;
import com.xyleme.bravais.web.pages.analytics.BaseAnalyticsPageHeader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Implementation of Analytics Statement Details page.
 */
public class AnalyticsStatementDetailsPage extends BaseAnalyticsPageHeader {

    public AnalyticsStatementDetailsPage(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                statementDetailsBlockBody().isAvailable() &&
                statementsLinkInBreadcrumbBlock().isAvailable() &&
                statementGuidInBreadCrumbBlock().isAvailable() &&
                statementGuidInStatementDetailsBlock().isAvailable() &&
                statementTimingAndAggregationStatusTable().isAvailable() &&
                actorTableHeader().isAvailable() &&
                verbTableHeader().isAvailable() &&
                objectTableHeader().isAvailable() &&
                contextTableHeader().isAvailable() &&
                authorityTableHeader().isAvailable();
    }

    private Element statementDetailsBlockBody() {
        return new Element(driver, By.id("statement-details"));
    }

    private Link statementsLinkInBreadcrumbBlock() {
        return new Link(driver, By.xpath("//ol[@class='breadcrumb']//a[text()='Statements']"));
    }

    private LabelText statementGuidInBreadCrumbBlock() {
        return new LabelText(driver, By.xpath("//ol[@class='breadcrumb']//li[starts-with(@class, 'active')]"));
    }

    private LabelText statementGuidInStatementDetailsBlock() {
        return new LabelText(driver, By.xpath("//div[@id='statement-details']/h1"));
    }

    private Element statementTimingAndAggregationStatusTable() {
        return new Element(driver, By.xpath("//div[@id='statement-details']/table"));
    }

    private By locatorOfDataTableHeaderElement(String headerTitle) {
        return By.xpath("//h2[normalize-space()='" + headerTitle + "']");
    }

    private By locatorOfTableDataBlockWithTitle(String tableTitle) {
        return By.xpath(getXpathLocatorValue(locatorOfDataTableHeaderElement(tableTitle)) + "/following-sibling::div[1]");
    }

    private WebElement dataTableElement(String tableTitle) {
        return getElementDynamically(By.xpath(getXpathLocatorValue(locatorOfTableDataBlockWithTitle(tableTitle)) + "/table"));
    }

    private WebElement dataSubTableElement(String parentTableTitle, String subTableTitle) {
        return getElementDynamically(By.xpath(getXpathLocatorValue(locatorOfTableDataBlockWithTitle(parentTableTitle))
                + "//h4[text()='" + subTableTitle + "']/following-sibling::table"));
    }

    private LabelText actorTableHeader() {
        return new LabelText(driver, locatorOfDataTableHeaderElement("Actor"));
    }

    private LabelText verbTableHeader() {
        return new LabelText(driver, locatorOfDataTableHeaderElement("Verb"));
    }

    private LabelText objectTableHeader() {
        return new LabelText(driver, locatorOfDataTableHeaderElement("Object"));
    }

    private LabelText contextTableHeader() {
        return new LabelText(driver, locatorOfDataTableHeaderElement("Context"));
    }

    private LabelText authorityTableHeader() {
        return new LabelText(driver, locatorOfDataTableHeaderElement("Authority"));
    }

    /**
     * Gets Actor data table.
     *
     * @return {@code ActorDataTable}
     */
    public ActorDataTable getActorDataTable() {
        return new ActorDataTable(dataTableElement("Actor"));
    }

    /**
     * Gets Actor > Account data table.
     *
     * @return {@code ActorAccountDataTable}
     */
    public ActorAccountDataTable getActorAccountDataTable() {
        return new ActorAccountDataTable(dataSubTableElement("Actor", "Account"));
    }

    /**
     * Gets Verb data table.
     *
     * @return {@code VerbDataTable}
     */
    public VerbDataTable getVerbDataTable() {
        return new VerbDataTable(dataTableElement("Verb"));
    }

    /**
     * Gets Object data table.
     *
     * @return {@code ObjectDataTable}
     */
    public ObjectDataTable getObjectDataTable() {
        By tableLocator = By.xpath((getXpathLocatorValue(locatorOfTableDataBlockWithTitle("Object"))
                + "/div[not(contains(@class, 'hide'))]/table"));
        return new ObjectDataTable(getElementDynamically(tableLocator));
    }

    /**
     * Gets Object > Definition data table.
     *
     * @return {@code ObjectDefinitionDataTable}
     */
    public ObjectDefinitionDataTable getObjectDefinitionDataTable() {
        return new ObjectDefinitionDataTable(dataSubTableElement("Object", "Definition"));
    }

    /**
     * Gets Result data table.
     *
     * @return {@code ResultDataTable}
     */
    public ResultDataTable getResultDataTable() {
        return new ResultDataTable(dataTableElement("Result"));
    }

    /**
     * Gets Result > Score data table.
     *
     * @return {@code ResultDataTable}
     */
    public ResultScoreDataTable getResultScoreDataTable() {
        return new ResultScoreDataTable(dataSubTableElement("Result", "Score:"));
    }

    /**
     * Gets Context data table.
     *
     * @return {@code ContextDataTable}
     */
    public ContextDataTable getContextDataTable() {
        return new ContextDataTable(dataTableElement("Context"));
    }

    /**
     * Gets Context > Extensions data table.
     *
     * @return {@code ContextExtensionsDataTable}
     */
    public ContextExtensionsDataTable getContextExtensionsDataTable() {
        return new ContextExtensionsDataTable(dataSubTableElement("Context", "Extensions"));
    }

    /**
     * Gets Authority data table.
     *
     * @return {@code AuthorityDataTable}
     */
    public AuthorityDataTable getAuthorityDataTable() {
        return new AuthorityDataTable(dataTableElement("Authority"));
    }

    /**
     * Gets Authority > Account data table.
     *
     * @return {@code AuthorityAccountDataTable}
     */
    public AuthorityAccountDataTable getAuthorityAccountDataTable() {
        return new AuthorityAccountDataTable(dataSubTableElement("Authority", "Account"));
    }

    //================================================== Data Tables ===================================================

    /**
     * Implementation of Actor data table.
     */
    public class ActorDataTable extends BaseStatementDetailsPageTable {

        ActorDataTable(WebElement tableElement) {
            super(tableElement);
        }

        /**
         * Gets value of 'objectType' parameter.
         *
         * @return {@code String}
         */
        public String getOjectType() {
            return getValueOfTableParameter("objectType");
        }

        /**
         * Gets value of 'name' parameter.
         *
         * @return {@code String}
         */
        public String getName() {
            return getValueOfTableParameter("name");
        }

        /**
         * Gets value of 'mbox' parameter.
         *
         * @return {@code String}
         */
        public String getMbox() {
            return getValueOfTableParameter("mbox").replace("mailto:", "");
        }
    }

    /**
     * Implementation of Actor > Account data table.
     */
    public class ActorAccountDataTable extends BaseStatementDetailsPageTable {

        ActorAccountDataTable(WebElement tableWebElement) {
            super(tableWebElement);
        }

        /**
         * Gets value of 'name' parameter.
         *
         * @return {@code String}
         */
        public String getName() {
            return getValueOfTableParameter("name");
        }

        /**
         * Gets value of 'homePage' parameter.
         *
         * @return {@code String}
         */
        public String getHomePage() {
            return getValueOfTableParameter("homePage");
        }
    }

    /**
     * Implementation of Verb data table.
     */
    public class VerbDataTable extends BaseStatementDetailsPageTable {

        VerbDataTable(WebElement tableWebElement) {
            super(tableWebElement);
        }

        /**
         * Gets value of 'display' parameter.
         *
         * @return {@code String}
         */
        public String getId() {
            return getValueOfTableParameter("id");
        }

        /**
         * Gets value of 'display' parameter.
         *
         * @return {@code String}
         */
        public String getDisplay() {
            return getValueOfTableParameter("display");
        }
    }

    /**
     * Implementation of Object data table.
     */
    public class ObjectDataTable extends BaseStatementDetailsPageTable {

        ObjectDataTable(WebElement tableWebElement) {
            super(tableWebElement);
        }

        /**
         * Gets value of 'display' parameter.
         *
         * @return {@code String}
         */
        public String getId() {
            return getValueOfTableParameter("id");
        }

        /**
         * Gets value of 'objectType' parameter.
         *
         * @return {@code String}
         */
        public String getOjectType() {
            return getValueOfTableParameter("objectType");
        }
    }

    /**
     * Implementation of Object > Definition data table.
     */
    public class ObjectDefinitionDataTable extends BaseStatementDetailsPageTable {

        ObjectDefinitionDataTable(WebElement tableWebElement) {
            super(tableWebElement);
        }

        /**
         * Gets value of 'type' parameter.
         *
         * @return {@code String}
         */
        public String getType() {
            return getValueOfTableParameter("type");
        }

        /**
         * Gets value of 'name' parameter.
         *
         * @return {@code String}
         */
        public String getName() {
            return getValueOfTableParameter("name");
        }

        /**
         * Gets value of 'extensions' parameter.
         *
         * @return {@code String}
         */
        public String getExtensions() {
            return getValueOfTableParameter("extensions");
        }
    }

    /**
     * Implementation of Result data table.
     */
    public class ResultDataTable extends BaseStatementDetailsPageTable {

        ResultDataTable(WebElement tableWebElement) {
            super(tableWebElement);
        }

        /**
         * Gets boolean value of 'success' parameter.
         *
         * @return {@code boolean}
         */
        public boolean getSuccess() {
            return getValueOfTableParameter("success").equals("true");
        }

        /**
         * Gets boolean value of 'completion' parameter.
         *
         * @return {@code boolean}
         */
        public boolean getCompletion() {
            return getValueOfTableParameter("completion").equals("true");
        }

        /**
         * Gets value of 'duration' parameter.
         *
         * @return {@code String}
         */
        public String getDuration() {
            return getValueOfTableParameter("duration");
        }
    }

    /**
     * Implementation of Result > Score data table.
     */
    public class ResultScoreDataTable extends BaseStatementDetailsPageTable {

        ResultScoreDataTable(WebElement tableWebElement) {
            super(tableWebElement);
        }

        /**
         * Gets value of 'max' parameter.
         *
         * @return {@code String}
         */
        public String getMax() {
            return getValueOfTableParameter("max");
        }
    }

    /**
     * Implementation of Context data table.
     */
    public class ContextDataTable extends BaseStatementDetailsPageTable {

        ContextDataTable(WebElement tableWebElement) {
            super(tableWebElement);
        }

        /**
         * Gets value of 'platform' parameter.
         *
         * @return {@code String}
         */
        public String getPlatform() {
            return getValueOfTableParameter("platform");
        }
    }

    /**
     * Implementation of Context > Extensions data table.
     */
    public class ContextExtensionsDataTable extends BaseStatementDetailsPageTable {

        ContextExtensionsDataTable(WebElement tableWebElement) {
            super(tableWebElement);
        }

        /**
         * Gets value of 'platformVersion' parameter.
         *
         * @return {@code String}
         */
        public String getPlatformVersion() {
            return getValueOfTableParameter("platformVersion");
        }

        /**
         * Gets value of 'exitMethod' parameter.
         *
         * @return {@code String}
         */
        public String getExitMethod() {
            return getValueOfTableParameter("exitMethod");
        }

        /**
         * Gets value of 'customParameters' parameter.
         *
         * @return {@code String}
         */
        public String getCustomParameters() {
            return getValueOfTableParameter("customParameters");
        }

        /**
         * Gets value of 'courseAttemptId' parameter.
         *
         * @return {@code String}
         */
        public String getCourseAttemptId() {
            return getValueOfTableParameter("courseAttemptId");
        }
    }

    /**
     * Implementation of Authority data table.
     */
    public class AuthorityDataTable extends BaseStatementDetailsPageTable {

        AuthorityDataTable(WebElement tableWebElement) {
            super(tableWebElement);
        }

        /**
         * Gets value of 'objectType' parameter.
         *
         * @return {@code String}
         */
        public String getObjectType() {
            return getValueOfTableParameter("objectType");
        }

        /**
         * Gets value of 'name' parameter.
         *
         * @return {@code String}
         */
        public String getName() {
            return getValueOfTableParameter("name");
        }

        /**
         * Gets value of 'mbox' parameter.
         *
         * @return {@code String}
         */
        public String getMbox() {
            return getValueOfTableParameter("mbox").replace("mailto:", "");
        }
    }

    /**
     * Implementation of Authority > Account data table.
     */
    public class AuthorityAccountDataTable extends BaseStatementDetailsPageTable {

        AuthorityAccountDataTable(WebElement tableWebElement) {
            super(tableWebElement);
        }

        /**
         * Gets value of 'name' parameter.
         *
         * @return {@code String}
         */
        public String getName() {
            return getValueOfTableParameter("name");
        }

        /**
         * Gets value of 'homePage' parameter.
         *
         * @return {@code String}
         */
        public String getHomePage() {
            return getValueOfTableParameter("homePage");
        }
    }
}