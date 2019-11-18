package cdssmoketest.groupsanduserspagestests;

import cdssmoketest.BaseCDSTest;
import com.xyleme.bravais.datacontainers.CDSFeatures;
import com.xyleme.bravais.datacontainers.PermissionLevel;
import com.xyleme.bravais.utils.RandomizationUtil;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnChannelsPage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnContentPermissionsPanelOfGroupDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSChannelsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSGroupsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSUsersPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdschanneldetailspage.CDSChannelDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsgroupdetailspage.CDSGroupDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsgroupdetailspage.panels.contentpermissionspane.ContentPermissionsPane;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsuserdetailspage.CDSUserDetailsPage;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.*;

import java.util.List;

import static com.xyleme.bravais.datacontainers.StringConstants.BASE_CDS_FOLDER;
import static org.testng.Assert.*;

/**
 * Test which verify functionality related to granting/limiting access permissions to different types of content
 * (Checks ##67 - 72).
 */
public class TestContentPermissionsSetting extends BaseCDSTest {
    private CDSUsersPage usersPage;
    private CDSGroupsPage groupsPage;
    private CDSGroupDetailsPage groupDetailsPage;
    private ContentPermissionsPane contentPermissionsPane;
    private RowOfTableOnContentPermissionsPanelOfGroupDetailsPage folderPermission;
    private CDSChannelsPage channelsPage;
    private String folderName = "AT Folder " + RandomizationUtil.getRandomNumber(100, 999);
    private String firstNameOfNewUser = "TestFIRST";
    private String lastNameOfNewUser = "TestLAST";
    private String fullNameOfNewUser = firstNameOfNewUser + " " + lastNameOfNewUser;
    private String usernameOfNewUser = "testFN.testLN";
    private String passwordOfNewUser = "Qwerty@123";
    private String groupName = "AT Group " + RandomizationUtil.getRandomNumber(100, 999);
    private String channelName = "AT Channel " + RandomizationUtil.getRandomNumber(100, 999);

    @Override
    @BeforeClass (alwaysRun = true)
    public void setUp() {
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDS();
        documentsPage = cdsHomePage.openDocumentsPage();
        documentsPage.navigateToFolder(BASE_CDS_FOLDER);
        documentsPage.createNewFolder(folderName, "");
        usersPage = documentsPage.openUsersPage();
        CDSUserDetailsPage userDetailsPage = usersPage.createNewUser(firstNameOfNewUser, lastNameOfNewUser,
                usernameOfNewUser + "@xyleme.com");
        userDetailsPage.selectIdentitiesTabAndGetPane().getLocalIdentityBlockWithNoIdentityAdded().addLocalIdentity(
                usernameOfNewUser, passwordOfNewUser);
        groupsPage = userDetailsPage.openGroupsPage();
        groupDetailsPage = groupsPage.createNewGroup(groupName, "");
        groupDetailsPage.selectFeatureAccessTabAndGetPane().selectMultipleFeatures(CDSFeatures.ADMIN_PORTAL);
        groupDetailsPage.selectMembersTabAndGetPane().addMember(fullNameOfNewUser);
        channelsPage = groupDetailsPage.openChannelsPage();
        CDSChannelDetailsPage channelDetailsPage = channelsPage.createNewChannel(channelName, "",
                "");
        groupsPage = channelDetailsPage.openGroupsPage();
    }

    @Test (description = "Check #69 of TLOR-613")
    public void permissionsLevelOfAllUsersGroupConformityTest() {
        groupDetailsPage = groupsPage.getFilteredGroup("All users", true).goToGroupDetailsPage();
        contentPermissionsPane = groupDetailsPage.selectContentPermissionsTabAndGetPane();
        folderPermission = contentPermissionsPane.getFolderPermissionsBlock().getFilteredTableItem(
                "All documents", true);
        if (!folderPermission.getAccessLevel().equals(PermissionLevel.READ.getValue())) {
            folderPermission.setLevelOfPermissions(PermissionLevel.READ);
        }

        assertEquals(folderPermission.getAccessLevel(), PermissionLevel.READ.getValue(),
                "Actual level of permissions of 'All users' group differs from the expected one.");
    }

    @Test (description = "Checks #70 and #71 of TLOR-613", dependsOnMethods = "permissionsLevelOfAllUsersGroupConformityTest",
            retryAnalyzer = IRetryAnalyzer.class)
    public void contentPermissionsSetUpTest() {
        groupsPage = groupDetailsPage.navigateFromPageUsingLinkInBreadcrumbsBlock("Groups", CDSGroupsPage.class);
        groupDetailsPage = groupsPage.getFilteredGroup(groupName, true).goToGroupDetailsPage();
        contentPermissionsPane = groupDetailsPage.selectContentPermissionsTabAndGetPane();
        contentPermissionsPane.getFolderPermissionsBlock().addPermission(folderName, PermissionLevel.WRITE);
        contentPermissionsPane.getChannelPermissionsBlock().addPermission(channelName, PermissionLevel.WRITE);
        folderPermission = contentPermissionsPane.getFolderPermissionsBlock().getFilteredTableItem(folderName, true);

        assertEquals(folderPermission.getAccessLevel(), PermissionLevel.WRITE.getValue(),
                "The expected level of permissions has not been set for '" + folderName +"' folder.");
        RowOfTableOnContentPermissionsPanelOfGroupDetailsPage channelPermission = contentPermissionsPane
                .getChannelPermissionsBlock().getFilteredTableItem(channelName, true);

        assertEquals(channelPermission.getAccessLevel(), PermissionLevel.WRITE.getValue(),
                "The expected level of permissions has not been set for '" + channelName + "' channel .");
    }

    @Test (description = "Check #72 (folder permissions) of TLOR-613", dependsOnMethods = "contentPermissionsSetUpTest",
            retryAnalyzer = IRetryAnalyzer.class)
    public void folderPermissionsApplianceTest() {
        groupDetailsPage.signOut();
        super.tearDown();
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDSWithUser(usernameOfNewUser, passwordOfNewUser);
        documentsPage = cdsHomePage.openDocumentsPage();

        assertFalse(documentsPage.isAddDocumentButtonAvailable(),"'Add Document' button is available on the " +
                "Documents page for the user who doesn't have respective permissions.");
        assertFalse(documentsPage.isAddFolderButtonAvailable(), "'Add Folder' button is available on the " +
                "Documents page for the user who doesn't have respective permissions.");
        documentsPage.navigateToFolder(BASE_CDS_FOLDER + "|" + folderName);

        assertTrue(documentsPage.isAddDocumentButtonAvailable(),"'Add Document' button is not available in the " +
                "folder the user was granted 'Write' level of permissions for.");
        assertTrue(documentsPage.isAddFolderButtonAvailable(), "'Add Folder' button is not available in the " +
                "folder the user was granted 'Write' level of permissions for.");
    }

    @Test (description = "Check #72 (channel permissions) of TLOR-613", dependsOnMethods = "folderPermissionsApplianceTest",
            retryAnalyzer = IRetryAnalyzer.class)
    public void channelAccessPermissionsApplianceTest() {
        channelsPage = documentsPage.openChannelsPage();
        List<RowOfTableOnChannelsPage> availableChannels = channelsPage.getDataTable().getAllTableRows();

        assertEquals(availableChannels.size(), 1, "Actual number of channels available to the user with " +
                "limited channels access permissions differs from the expected one.");
        RowOfTableOnChannelsPage availableChannel = availableChannels.get(0);

        assertEquals(availableChannel.getChannelName(), channelName, "Actual channel available to the user " +
                "with limited access permissions differs from the one the access permission was granted for.");
    }

    @Override
    @AfterClass (alwaysRun = true)
    public void tearDown() {
        super.tearDown();
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDS();
        documentsPage = cdsHomePage.openDocumentsPage();
        documentsPage.navigateToFolder(BASE_CDS_FOLDER);
        documentsPage.getFilteredTableItem(folderName, true).archiveTableItem();
        usersPage = documentsPage.openUsersPage();
        usersPage.getFilteredUser(fullNameOfNewUser, true).deleteUser();
        groupsPage = usersPage.openGroupsPage();
        groupsPage.getFilteredGroup(groupName, true).deleteGroup();
        channelsPage = groupsPage.openChannelsPage();
        channelsPage.getFilteredChannel(channelName, true).deleteChannel();
        super.tearDown();
    }
}