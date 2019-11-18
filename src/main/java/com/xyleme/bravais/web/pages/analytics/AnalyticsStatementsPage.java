package com.xyleme.bravais.web.pages.analytics;

import com.xyleme.bravais.datacontainers.AnalyticsVerb;
import com.xyleme.bravais.web.pages.analytics.filters.DetailedReportFilterBlock;
import com.xyleme.bravais.web.pages.analytics.recordtables.records.StatementRecord;
import com.xyleme.bravais.web.pages.analytics.recordtables.StatementRecordsTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.xyleme.bravais.BaseTest.staticSleep;

public class AnalyticsStatementsPage extends BaseAnalyticsPageHeader {
    private By locatorOfStatementRecordsListedAboveSpecifiedRecord =
            By.xpath("./preceding-sibling::tr[not(contains(@class, 'hide'))]");

    public AnalyticsStatementsPage(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    public AnalyticsStatementsPage(WebDriver driver, boolean waitForPageToLoad) {
        super(driver);
        if (waitForPageToLoad) {
            this.waitUntilAvailable();
        }
    }

    @Override
    public AnalyticsStatementsPage load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() && getDetailedReportFilterBlock().isAvailable();
    }

    /**
     * Gets Detailed Report filter block with all its functionality.
     *
     * @return {@code DetailedReportFilterBlock}
     */
    private DetailedReportFilterBlock getDetailedReportFilterBlock() {
        return new DetailedReportFilterBlock(driver);
    }

    /**
     * Filters statements by Actor parameter.
     *
     * @param actor - Specifies the actor
     * @return {@code AnalyticsStatementsPage}
     */
    public AnalyticsStatementsPage filterByActor(String actor) {
        waitForAngularJSProcessing();
        return getDetailedReportFilterBlock().filterByActor(actor);
    }

    /**
     * Filters statements by Verb parameter.
     *
     * @param verb - Specifies the verb
     * @return {@code AnalyticsStatementsPage}
     */
    public AnalyticsStatementsPage filterByVerb(AnalyticsVerb verb) {
        return getDetailedReportFilterBlock().filterByVerb(verb);
    }

    /**
     * Gets table with statements displayed on the page.
     *
     * @return {@code StatementRecordsTable}
     */
    public StatementRecordsTable getStatementsTable() {
        return new StatementRecordsTable(driver,true);
    }

    /**
     * Gets time stamp of the most recent statement record.
     *
     * @return {@code String}
     */
    public String getTimeStampOfMostRecentStatement() {
        return getStatementsTable().getStatementRecord(0).getTimeStamp();
    }

    /**
     * Gets date and time of test start.
     *
     * @return {@code Date}
     */
    private Date getTestStartDate() {
        String testStartDate = ENVIRONMENT.env.get("testStartDate");

        SimpleDateFormat conversionFormat = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        Date parsedDate = null;
        try {
            parsedDate = conversionFormat.parse(testStartDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat statementDateFormat = new SimpleDateFormat("MMM d, yyyy h:mm a");
        return new Date(statementDateFormat.format(parsedDate));
    }

    /**
     * Applies filer with or without querying a specific actor (don't need to query an actor in case the filter is
     * pre-set up).
     *
     * @param actor      - Specifies the user the statement/s belong/s to
     * @param queryActor - Specifies the decision whether to query actor in the filter or to click 'Apply Filter'
     *                   button without querying the actor
     */
    private void applyFilter(String actor, boolean queryActor) {
        if (queryActor) {
            filterByActor(actor);
        } else {
            getDetailedReportFilterBlock().clickApplyFilterButton(AnalyticsStatementsPage.class);
        }
    }

    /**
     * Checks if a specific number of new* statements which belong to a specific user (actor) are present in the Statements
     * table (new* - the statements with a time stamp indicating time later than the start of the respective test).
     *
     * @param actor           - Specifies the user the statement/s belong/s to
     * @param statementsCount - Specifies the number of statements intended to be checked
     * @param queryActor      - Specifies the decision whether to query actor in the filter or to click 'Apply Filter'
     *                        button without querying the actor (for cases when the filter query is sect up)
     * @return {@code boolean}
     */
    private boolean checkIfNewStatementsArePresent(String actor, int statementsCount, boolean queryActor) {
        List<Boolean> dateComparisonResults = new ArrayList<>();
        Date testStartDate = getTestStartDate();
        applyFilter(actor, queryActor);
        List<StatementRecord> statements = getStatementsTable().getListOfStatementRecords(statementsCount);

//        System.out.println("------------------------------------------------------------------------------------------");
        for (StatementRecord statement : statements) {
            Date statementDateWithSeconds = new Date(statement.getTimeStamp());
            String formattedStatementTimeStamp = new SimpleDateFormat("MMM d, yyyy h:mm a").format(statementDateWithSeconds);
            Date statementDate = new Date(formattedStatementTimeStamp);
            boolean statementDateComparisonResult = (statementDate.after(testStartDate) || statementDate.equals(testStartDate));
//            System.out.println("Test start time: " + testStartDate + " --> Statement time stamp: " + statementDate + // ToDo: Uncomment if you want to have this info in the console.
//                    " Comparison result --> " + statementDateComparisonResult);
            dateComparisonResults.add(statementDateComparisonResult);
        }
//        System.out.println("------------------------------------------------------------------------------------------");

        return !dateComparisonResults.contains(false);
    }


    /**
     * Waits for specific number of new* statements which belong to a specific user (new* - the statements with a time
     * stamp indicating time later than the start of the respective test).
     *
     * @param actor           - Specifies the user the statement/s belong/s to
     * @param statementsCount - Specifies the number of statements intended to be waited for
     */
    public void waitForStatementsOnBetaOrSTGEnvironment(String actor, int statementsCount) {
        int initialTimeOut = 45; // 45 * 20 sec = 900 sec = 15 min.
        int processedTime = initialTimeOut;
        boolean areNewStatementsPresent = checkIfNewStatementsArePresent(actor, statementsCount,true);

        if (!areNewStatementsPresent) {
            System.out.println("\n>> Waiting for statements...");
        }

        while (!areNewStatementsPresent && processedTime > 0) {
            staticSleep(20);
            areNewStatementsPresent = checkIfNewStatementsArePresent(actor, statementsCount,false);
            processedTime--;
        }

        if (processedTime == initialTimeOut) {
            System.out.println("\n>> Statements appeared without delay!\n");
        } else if (processedTime < initialTimeOut && processedTime > 0) {
            double waitedFor = (initialTimeOut - processedTime) * 20;
            System.out.println(">> Waited for statements for " + waitedFor + " seconds (" +
                    new DecimalFormat("##.##").format(waitedFor / 60) + " minute/s).\n");
        } else if (processedTime <= 0) {
            throw new RuntimeException("Timed out waiting for statements! (time out = 15 minutes)");
        }
    }

    /**
     * Gets specified number of statements (in case when timeStampOfLastStatement parameter is not provided) or a list of
     * statements which are listed in the table above the statement with specified time stamp.
     *
     * @param actor                    - Specifies a name of the actor
     * @param statementsCount          - Specifies a number of statements expected to be returned (starting from the most
     *                                 recent statement)
     * @param timeStampOfLastStatement - Specifies a time stamp of the statement, the statements received after which are
     *                                 expected to be returned
     * @return {@code List <String>}
     */
    public List<String> waitForStatements(String actor, int statementsCount, String timeStampOfLastStatement) {
        int i = 0;
        ArrayList<String> listOfStatementsToReturn = new ArrayList<>();

        if (!timeStampOfLastStatement.equals("")) {
            while (getStatementsTable().getStatementRecordWebElementWithTimeStamp(timeStampOfLastStatement).findElements(
                    locatorOfStatementRecordsListedAboveSpecifiedRecord).size() != statementsCount && i < 5) {
                refresh();
                filterByActor(actor);
                i++;
            }
            List<WebElement> listOfSelectedStatementElements = getStatementsTable()
                    .getStatementRecordWebElementWithTimeStamp(timeStampOfLastStatement)
                    .findElements(locatorOfStatementRecordsListedAboveSpecifiedRecord);

            for (WebElement statementElement : listOfSelectedStatementElements) {
                StatementRecord statement = new StatementRecord(driver, statementElement);
                listOfStatementsToReturn.add(statement.getActor() + "|" + statement.getVerb() + "|" + statement.getObject() + "|");
            }
            if (listOfStatementsToReturn.size() != statementsCount)
                System.out.println("[Warning!] Number of statements retrieved by the method isn't equal to the requested " +
                        "number of statements." +
                        "\n> Actual number of statements retrieved by the method: " + listOfStatementsToReturn.size() +
//                        ". Statements retrieved: " + listOfStatementsToReturn + // Uncomment this line if this info is needed.
                        "\n> Anticipated number of statements: " + statementsCount);
        } else {
            while (getStatementsTable().getNumberOfTableRecords() < statementsCount && i < 5) {
                refresh();
                filterByActor(actor);
                i++;
            }
            List<StatementRecord> listOfSelectedStatements = getStatementsTable().getListOfStatementRecords(statementsCount);

            for (StatementRecord statement : listOfSelectedStatements) {
                listOfStatementsToReturn.add(statement.getActor() + "|" + statement.getVerb() + "|" + statement.getObject() + "|");
            }
        }
        return listOfStatementsToReturn;
    }
}