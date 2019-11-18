package cdssmoketest.groupsanduserspagestests;

import cdssmoketest.BaseCDSTest;
import com.xyleme.bravais.datacontainers.CDSFeatures;
import com.xyleme.bravais.web.pages.analytics.AnalyticsHomePage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnGroupsPage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnMembersPanelOfGroupDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnUsersPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSGroupsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSUsersPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsbrandingpage.CDSBrandingPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsclassificationspage.CDSClassificationsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsgroupdetailspage.panels.FeatureAccessPane;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsgroupdetailspage.panels.MembersPane;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdspreferencespage.CDSPreferencesPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsuserdetailspage.CDSUserDetailsPage;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.*;

import static org.testng.Assert.*;

/**
 * Tests which verify functionality of Groups and Users pages (Checks ##60 - 66 of TLOR-613).
 */
public class TestGroupsAndUsersPages extends BaseCDSTest {
    private CDSUsersPage usersPage;
    private CDSUserDetailsPage userDetailsPage;
    private RowOfTableOnUsersPage user;
    private RowOfTableOnGroupsPage group;
    private String firstNameOfNewUser = "Automated";
    private String lastNameOfNewUser = "Tester";
    private String fullNameOfOriginalUser = firstNameOfNewUser + " " + lastNameOfNewUser;
    private String usernameOfNewUser = "automated.tester";
    private String fullNameOfUpdatedUser = firstNameOfNewUser + "1 " + lastNameOfNewUser + "1";
    private String emailOfNewUser = usernameOfNewUser + getRandomNumber(100001, 900000) + "@xyleme.com";
    private String updatedPassword = "Qwerty@456";
    private String originalGroupName = "Test Group "+ getRandomNumber(100001, 900000);
    private String updatedGroupName = originalGroupName + " Updated";

    @Override
    @BeforeClass (alwaysRun = true)
    public void setUp() {
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDS();
    }

    @Test (description = "Check #60 of TLOR-613")
    public void newUserCreationTest() {
        usersPage = cdsHomePage.openUsersPage();
        userDetailsPage = usersPage.createNewUser(firstNameOfNewUser, lastNameOfNewUser, emailOfNewUser);

        assertEquals(userDetailsPage.getFirstNameOfUser(), firstNameOfNewUser + " " + lastNameOfNewUser,"Actual " +
                "full name of the newly created user displayed on User Details page differs from the expected one.");
        String originalPassword = "Qwerty@123";
        userDetailsPage.selectIdentitiesTabAndGetPane().getLocalIdentityBlockWithNoIdentityAdded().addLocalIdentity(
                usernameOfNewUser, originalPassword);
        usersPage = userDetailsPage.navigateFromPageUsingLinkInBreadcrumbsBlock("Users", CDSUsersPage.class);
        user = usersPage.getFilteredUser(firstNameOfNewUser + " " + lastNameOfNewUser, true);

        assertEquals(user.getEmail(), emailOfNewUser,
                "Actual email address of the newly created user in Users table differs from the expected one.");
        assertEquals(user.getStatus(), "enabled",
                "Actual status of the newly created user in Users table differs from the expected one.");
    }

    @Test (description = "Check #61 of TLOR-613", dependsOnMethods = "newUserCreationTest", retryAnalyzer = IRetryAnalyzer.class)
    public void userNameAndPasswordUpdatingTest() {
        user = usersPage.getFilteredUser(firstNameOfNewUser + " " + lastNameOfNewUser, true);
        userDetailsPage = user.goToUserDetailsPage();
        userDetailsPage.editFirstName(firstNameOfNewUser + "1");
        userDetailsPage.editLastName(lastNameOfNewUser + "1");
        userDetailsPage.selectIdentitiesTabAndGetPane().getLocalIdentityBlockWithAddedIdentity().changePassword(
                updatedPassword);

        assertEquals(userDetailsPage.getFirstNameOfUser(), fullNameOfUpdatedUser,
                "Full name of the user displayed on User Details page has not been updated as expected.");
    }

    @Test (description = "Check #62 of TLOR-613", dependsOnMethods = "userNameAndPasswordUpdatingTest", retryAnalyzer =
            IRetryAnalyzer.class)
    public void newGroupCreationTest() {
        groupsPage = userDetailsPage.openGroupsPage();
        groupDetailsPage = groupsPage.createNewGroup(originalGroupName, "");

        assertEquals(groupDetailsPage.getGroupName(), originalGroupName, "Actual name of the newly created group " +
                "displayed on Group Details page differs from the expected one.");
        groupsPage = groupDetailsPage.navigateFromPageUsingLinkInBreadcrumbsBlock("Groups", CDSGroupsPage.class);
        group = groupsPage.getFilteredGroup(originalGroupName, true);

        assertEquals(group.getNumberOfGroupMembers(),0,
                "Actual number of members of the newly created group differs from the expected one.");
        assertEquals(group.getGroupType(), "local",
                "Actual type of the newly created group differs from the expected one.");
    }

    @Test (description = "Check #63 of TLOR-613", dependsOnMethods = "newGroupCreationTest", retryAnalyzer =
            IRetryAnalyzer.class)
    public void groupNameUpdatingTest() {
        group = groupsPage.getFilteredGroup(originalGroupName, true);
        groupDetailsPage = group.goToGroupDetailsPage();
        groupDetailsPage.editGroupName(updatedGroupName);

        assertEquals(groupDetailsPage.getGroupName(), updatedGroupName,
                "Group name has not been updated as expected.");
    }

    @Test (description = "Check #64 of TLOR-613", dependsOnMethods = "groupNameUpdatingTest", retryAnalyzer =
            IRetryAnalyzer.class)
    public void featureAccessSettingTest() {
        FeatureAccessPane featureAccessPane = groupDetailsPage.selectFeatureAccessTabAndGetPane();
        featureAccessPane.selectAllFeatures();

        assertTrue(featureAccessPane.isFeatureSelected(CDSFeatures.ANALYTICS),
                "Expected feature is not selected after selection attempt.");
        assertTrue(featureAccessPane.isFeatureSelected(CDSFeatures.BRANDING),
                "Expected feature is not selected after selection attempt.");
        assertTrue(featureAccessPane.isFeatureSelected(CDSFeatures.CONTENT_ATTRIBUTES),
                "Expected feature is not selected after selection attempt.");
        assertTrue(featureAccessPane.isFeatureSelected(CDSFeatures.FEATURES),
                "Expected feature is not selected after selection attempt.");
        assertTrue(featureAccessPane.isFeatureSelected(CDSFeatures.PORTAL),
                "Expected feature is not selected after selection attempt.");
        assertTrue(featureAccessPane.isFeatureSelected(CDSFeatures.SYSTEM_SETTINGS),
                "Expected feature is not selected after selection attempt.");
        assertTrue(featureAccessPane.isFeatureSelected(CDSFeatures.USER_MANAGEMENT),
                "Expected feature is not selected after selection attempt.");
        assertTrue(featureAccessPane.isFeatureSelected(CDSFeatures.USER_PROFILES),
                "Expected feature is not selected after selection attempt.");
    }

    @Test (description = "Check #65 of TLOR-613", dependsOnMethods = "featureAccessSettingTest", retryAnalyzer =
            IRetryAnalyzer.class)
    public void groupMemberAddingTest() {
        MembersPane membersPane = groupDetailsPage.selectMembersTabAndGetPane();
        membersPane.addMember(fullNameOfUpdatedUser);

        RowOfTableOnMembersPanelOfGroupDetailsPage member = membersPane.getFilteredGroupMember(fullNameOfUpdatedUser,
                true);

        assertEquals(member.getEmail(), emailOfNewUser,
                "Actual email address of the newly added group member differs from the expected one.");
        assertEquals(member.getStatus(), "enabled",
                "Actual status of the newly added group member differs from the expected.");
    }

    @Test (description = "Check #66 of TLOR-613", dependsOnMethods = "groupMemberAddingTest", retryAnalyzer =
            IRetryAnalyzer.class)
    public void userAccessPermissionsGrantedByGroupConformityTest() {
        groupDetailsPage.signOut();
        super.tearDown();
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDSWithUser(usernameOfNewUser, updatedPassword);
        AnalyticsHomePage analyticsHome = cdsHomePage.goToAnalytics();

        assertTrue(analyticsHome.isAvailable(),"Newly created user added to the newly created group with all " +
                "features enabled doesn't have access to Analytics.");
        cdsHomePage = analyticsHome.goToAdminPortal();
        CDSBrandingPage brandingPage = cdsHomePage.openBrandingPage();

        assertTrue(brandingPage.isAvailable(), "Newly created user added to the newly created group with all " +
                "features enabled doesn't have access to Branding page.");
        CDSClassificationsPage classificationsPage = brandingPage.openClassificationsPage();

        assertTrue(classificationsPage.isAvailable(),"Newly created user added to the newly created group with " +
                "all features enabled doesn't have access to Classifications page.");
        groupsPage = classificationsPage.openGroupsPage();

        assertTrue(groupsPage.isAvailable(),"Newly created user added to the newly created group with all " +
                "features enabled doesn't have access to Groups page.");
        groupDetailsPage = groupsPage.getFilteredGroup(updatedGroupName, true).goToGroupDetailsPage();

        assertTrue(groupDetailsPage.isAvailable(), "Newly created user added to the newly created group with " +
                "all features enabled doesn't have access to Group Details page.");
        CDSPreferencesPage preferencesPage = groupDetailsPage.openPreferencesPage();

        assertTrue(preferencesPage.isAvailable(), "Newly created user added to the newly created group with all " +
                "features enabled doesn't have access to Preferences page.");
        usersPage = preferencesPage.openUsersPage();

        assertTrue(usersPage.isAvailable(), "Newly created user added to the newly created group with all features " +
                "enabled doesn't have access to Users page.");
        userDetailsPage = usersPage.getFilteredUser(fullUserName, true).goToUserDetailsPage();

        assertTrue(userDetailsPage.isAvailable(), "Newly created user added to the newly created group with all " +
                "features enabled doesn't have access to User Details page.");
    }

    @Override
    @AfterClass (alwaysRun = true)
    public void tearDown() {
        super.tearDown();
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDS();
        usersPage = cdsHomePage.openUsersPage();
        usersPage.deleteUserNameOfWhichWasAttemptedToBeUpdated(fullNameOfOriginalUser, fullNameOfUpdatedUser);
        groupsPage = usersPage.openGroupsPage();
        groupsPage.deleteGroupNameOfWhichWasAttemptedToBeUpdated(originalGroupName, updatedGroupName);
        super.tearDown();
    }
}