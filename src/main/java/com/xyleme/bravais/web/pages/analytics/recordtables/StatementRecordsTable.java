package com.xyleme.bravais.web.pages.analytics.recordtables;

import com.xyleme.bravais.datacontainers.AnalyticsVerb;
import com.xyleme.bravais.web.pages.analytics.statementdetails.AnalyticsStatementDetailsPage;
import com.xyleme.bravais.web.pages.analytics.recordtables.records.StatementRecord;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of Statement Records table available on Detailed Report page.
 */
public class StatementRecordsTable extends BaseRecordsTable {

    public StatementRecordsTable(WebDriver driver, boolean waitForTable) {
        super(driver);
        if (waitForTable) {
            this.waitUntilAvailable();
        }
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                getTableColumnHeaderElement("Actor").isDisplayed() &&
                getTableColumnHeaderElement("Business unit").isDisplayed() &&
                getTableColumnHeaderElement("Location").isDisplayed() &&
                getTableColumnHeaderElement("Verb").isDisplayed() &&
                getTableColumnHeaderElement("Object").isDisplayed() &&
                getTableColumnHeaderElement("Type").isDisplayed() &&
                getTableColumnHeaderElement("Timestamp").isDisplayed() &&
                getTableColumnHeaderElement("Details").isDisplayed();
    }

    /**
     * Gets specified Statement record object.
     *
     * @param recordIndex - Specifies index of the statement record in the table
     * @return {@code StatementRecord}
     */
    public StatementRecord getStatementRecord(int recordIndex) {
        return new StatementRecord(driver, getTableRows().get(recordIndex));
    }

    /**
     * Gets web element of statement record with specified time stamp.
     *
     * @param timeStamp - Specifies the time stamp
     * @return {@code WebElement}
     */
    public WebElement getStatementRecordWebElementWithTimeStamp(String timeStamp) {
        WebElement rowToReturn = null;

        for (WebElement row : getTableRows()) {
            if (new StatementRecord(driver, row).getTimeStamp().equals(timeStamp)) {
                rowToReturn = row;
                break;
            }
        }
        if (rowToReturn != null) {
            return rowToReturn;
        } else {
            throw new RuntimeException("Statements table doesn't contain statement with time stamp '" + timeStamp +"'!");
        }
    }

    /**
     * Gets Statement Record object with specified time stamp.
     *
     * @param timeStamp - Specifies the time stamp
     * @return {@code StatementRecord}
     */
    public StatementRecord getStatementRecordWithTimeStamp(String timeStamp) {
        return new StatementRecord(driver, getStatementRecordWebElementWithTimeStamp(timeStamp));
    }

    /**
     * Gets specified number of statement record objects (starting from the first record).
     *
     * @param numberOfRecords - Specifies number of statement records expected to be returned
     * @return {@code List<StatementRecord>}
     */
    public List<StatementRecord> getListOfStatementRecords(int numberOfRecords) {
        List<StatementRecord> recordsList = new ArrayList<>();

        for (int i = 0; i < numberOfRecords; i++) {
            recordsList.add(new StatementRecord(driver, getTableRows().get(i)));
        }
        return recordsList;
    }

    /**
     * Gets verbs of list of statement records.
     *
     * @param numberOfRecords - Specifies number of records verbs of which are expected to be returned.
     * @return {@code List<String>}
     */
    public List<String> getVerbsOfListOfStatementRecords(int numberOfRecords) {
        List<String> verbs = new ArrayList<>();
        List<StatementRecord> records = getListOfStatementRecords(numberOfRecords);

        for (StatementRecord record : records) {
            verbs.add(record.getVerb());
        }

        if (verbs.size() == numberOfRecords) {
            return verbs;
        } else {
            throw new RuntimeException("Number of returned verbs doesn't correspond to the expected number of statement " +
                    "records!");
        }
    }

    /**
     * Gets list of names of objects retrieved from the specified number of records.
     *
     * @param numberOfRecords - Specifies number of statement records Object name of which are expected to be returned
     * @return {@code List<String>}
     */
    public List<String> getObjectNamesOfListOfRecords(int numberOfRecords) {
        List<String> documentNames = new ArrayList<>();
        List<StatementRecord> records = getListOfStatementRecords(numberOfRecords);
        for (StatementRecord record : records) {
            documentNames.add(record.getObject());
        }
        return documentNames;
    }

    /**
     * Gets statement with specified parameters from list of 5 last (most recent) statements.
     *
     * @param actor  - Specifies the Actor parameter
     * @param verb   - Specifies the Verb parameter
     * @param object - Specifies the Verb parameter
     * @return {@code StatementRecord}
     */
    public StatementRecord getStatementWithFollowingParametersFromSetOfLastFiveStatements(String actor, String verb,
                                                                                          String object) {
        StatementRecord recordToReturn = null;
        List<StatementRecord> statementRecords = getListOfStatementRecords(5);

        for (StatementRecord record : statementRecords) {
            if (record.getActor().equals(actor) && record.getVerb().equals(verb) && record.getObject().equals(object)) {
                recordToReturn = record;
                break;
            }
        }

        if (recordToReturn != null) {
            return recordToReturn;
        } else {
            throw new RuntimeException("List of last 5 statements doesn't contain statement with the specified parameters.");
        }
    }

    /**
     * Checks if statement with specified parameters is present in the list of 5 last (most recent) statements.
     *
     * @param actor  - Specifies the Actor parameter
     * @param verb   - Specifies the Verb parameter
     * @param object - Specifies the Verb parameter
     * @return {@code boolean}
     */
    public boolean isStatementWithFollowingParametersPresentInSetOfLastFiveStatements(String actor, AnalyticsVerb verb,
                                                                                      String object) {
        boolean match = false;
        List<StatementRecord> statementRecords = getListOfStatementRecords(5);
        String verbValue = verb.getValue();

        for (StatementRecord record : statementRecords) {
            if (record.getActor().equals(actor) && record.getVerb().equals(verbValue) && record.getObject().equals(object)) {
                match = true;
                break;
            }
        }
        return match;
    }

    /**
     * Checks if statement with specified parameters is present in the specified list of last (most recent) statements.
     *
     * @param numberOfStatements - Specifies the amount of statements intended to be checked (statements list size)
     * @param actor              - Specifies the Actor parameter
     * @param verb               - Specifies the Verb parameter
     * @param object             - Specifies the Verb parameter
     * @return {@code boolean}
     */
    public boolean isStatementWithFollowingParametersPresentInSetOfLastStatements(int numberOfStatements, String actor, AnalyticsVerb verb,
                                                                                      String object) {
        boolean match = false;
        List<StatementRecord> statementRecords = getListOfStatementRecords(numberOfStatements);
        String verbValue = verb.getValue();

        for (StatementRecord record : statementRecords) {
            if (record.getActor().equals(actor) && record.getVerb().equals(verbValue) && record.getObject().equals(object)) {
                match = true;
                break;
            }
        }
        return match;
    }

    /**
     * Gets list of statements which match specified parameters from list of 5 last (most recent) statements.
     *
     * @param actor  - Specifies the Actor parameter
     * @param verb   - Specifies the Verb parameter
     * @param object - Specifies the Verb parameter
     * @return {@code List<StatementRecord>}
     */
    public List<StatementRecord> getListOfStatementsWithFollowingParametersFromSetOfLastFiveStatements(String actor,
                                                                                                       AnalyticsVerb verb,
                                                                                                       String object) {
        List<StatementRecord> listToReturn = new ArrayList<>();
        List<StatementRecord> statementRecords = getListOfStatementRecords(5);
        String verbValue = verb.getValue();

        for (StatementRecord record : statementRecords) {
            if (record.getActor().equals(actor) && record.getVerb().equals(verbValue) && record.getObject().equals(object)) {
                listToReturn.add(record);
            }
        }
        return listToReturn;
    }

    /**
     * Opens Statement Details page for specified statement record.
     *
     * @param recordIndex - Specifies index of the statement record Details page of which is intended to be opened
     * @return {@code AnalyticsStatementDetailsPage}
     */
    public AnalyticsStatementDetailsPage openDetailsPageForStatementRecord(int recordIndex) {
        getStatementRecord(recordIndex).getDetailsLink().click();
        return new AnalyticsStatementDetailsPage(driver);
    }
}