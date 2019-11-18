package ondeployment.smoketest;

import cdssmoketest.BaseCDSTest;
import com.xyleme.bravais.datacontainers.FileType;
import com.xyleme.bravais.utils.Utils;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSLogoutPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.CDSUsersPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.previewarea.visualdocumentpreview.NonCourseDocumentPreview;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdssearchpage.CDSSearchPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsuserdetailspage.CDSUserDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsconsumerportalpages.CDSConsumerPortalDocumentsPage;
import com.xyleme.bravais.web.pages.cds.cdsconsumerportalpages.homepage.CDSConsumerPortalHomePage;
import com.xyleme.bravais.web.pages.cds.popupdialogs.infodialogs.YourDocumentIsBeingPreparedDialog;
import org.testng.annotations.*;

import static com.xyleme.bravais.DriverMaster.waitUntilSecondTabIsOpenedAndSwitchToIt;
import static com.xyleme.bravais.datacontainers.StringConstants.*;
import static org.testng.Assert.*;

/**
 * A short version of the CDS Smoke test for executing together with a deployment of the CDS build.
 */
public class OnDeploymentCDSSmokeTest extends BaseCDSTest {
    private String firstPartOfImageName = "AT_Image_";
    private String imageNewName = firstPartOfImageName + Utils.getRandomNumber();
    private String baseCDSFolder = "Automated CDS Smoke test (short version)";
    private String firstNameOfNewUser = "AT";
    private String lastNameOfNewUser = "Tester";
    private boolean isImageUploaded = false;

    @Override
    @BeforeMethod (alwaysRun = true)
    public void setUp() {
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDS();
    }

    @Test
    public void a_successfulLoginTest() {
        assertTrue(cdsHomePage.isAvailable(),
                "User hasn't accessed the CDS Home page after attempting to log in using valid credentials.");
    }

    @Test
    public void userCreationTest() {
        String usernameOfNewUser = "at.tester";
        String passwordOfNewUser = "Mypass@123";
        String emailOfNewUser = "at.tester" + Utils.getRandomNumber() + "@xyleme.com";

        CDSUserDetailsPage userDetailsPage = cdsHomePage.openUsersPage().createNewUser(firstNameOfNewUser,
                lastNameOfNewUser, emailOfNewUser);
        userDetailsPage.selectIdentitiesTabAndGetPane().getLocalIdentityBlockWithNoIdentityAdded().addLocalIdentity(
                usernameOfNewUser, passwordOfNewUser);
        super.tearDown();
        super.setUp();
        cdsHomePage = cdsLoginPage.logInToCDSWithUser(usernameOfNewUser, passwordOfNewUser);

        assertTrue(cdsHomePage.isAvailable(), "The newly crated user hasn't accessed the Home page after the " +
                "login attempt.");
    }

    @Test
    public void an_imageUploadTest() {
        documentsPage = cdsHomePage.openDocumentsPage();
        documentsPage.navigateToFolder(baseCDSFolder);
        documentDetailsPage = documentsPage.uploadFileFromFilesForUploadingFolder(JPEG_FILE);
        documentDetailsPage.editDocumentName(imageNewName);
        isImageUploaded = true;

        assertTrue(documentDetailsPage.isAvailable(), "The image has not been uploaded successfully, the user " +
                "is not redirected to the Document Details page after the upload.");
        assertTrue(documentDetailsPage.selectPreviewTabAndGetPane().getVisualPreviewOfNonCourseDocument()
                .isImagePreviewOpened(), "Image preview is not available on the Document Details page (Preview pane).");
    }

    @Test
    public void imageDownloadingTest() {
        uploadImageIfNotUploaded();
        documentsPage = cdsHomePage.openDocumentsPage();
        documentsPage.navigateToFolder(baseCDSFolder);
        documentsPage.getFilteredTableItem(imageNewName, true).downloadItemUsingDownloadOptionOfMoreMenu(FileType.JPEG);

        assertTrue(Utils.getFilesInDownloadsFolder().contains("downloaded.jpeg"),"Expected image is not present in the " +
                "downloads folder.\nFiles available in the downloads folder: " + Utils.getFilesInDownloadsFolder() + "\n");
    }

    @Test
    public void searchImageByItsNameTest() {
        uploadImageIfNotUploaded();
        CDSSearchPage searchPage = cdsHomePage.searchForContent(imageNewName);

        assertEquals(searchPage.getSearchResultsContainer().getSearchResultItem(0).getDocumentName(), imageNewName,
                "The image uploaded in the 'an_imageUploadTest' didn't appear on the Search page after searching " +
                        "for the image by its name.");
    }

    @Test
    public void imagePreviewOnConsumerPortalTest() {
        uploadImageIfNotUploaded();
        CDSConsumerPortalHomePage consumerPortalHomePage = cdsHomePage.goToConsumerPortal();

        assertTrue(consumerPortalHomePage.isAvailable(), "The user has not been redirected to the Consumer Portal " +
                "Home page after selecting the 'Portal' option in the CDS modules drop-down.");
        CDSConsumerPortalDocumentsPage consumerPortalDocumentsPage = consumerPortalHomePage.openDocumentsPage();
        String originalWindow = driver.getWindowHandle();
        consumerPortalDocumentsPage.navigateTo(baseCDSFolder);
        consumerPortalDocumentsPage.getFilteredTableItem(imageNewName,false).clickItemNameLink();
        driver.switchTo().window(originalWindow);

        assertTrue(new YourDocumentIsBeingPreparedDialog(driver).isAvailable(),"The 'Your document is being " +
                "prepared' dialog hasn't appeared after clicking on the document intended to be previewed.");
        waitUntilSecondTabIsOpenedAndSwitchToIt();

        assertEquals(driver.getWindowHandles().size(), 2,"Separate tab for document preview has not been opened.");
        assertTrue(new NonCourseDocumentPreview(driver, false).isImagePreviewOpened(),
                "Image preview has not been opened.");
    }

    @Test
    public void logOutTest() {
        CDSLogoutPage logoutPage = cdsHomePage.signOut();

        assertTrue(logoutPage.isAvailable(), "User was not redirected to the Logout page after attempting to sign " +
                "out form the CDS.");
        assertEquals(logoutPage.getMessageInSignOutForm(), "You have been signed out of the Xyleme platform.",
                "Actual message displayed in the Sign Out form differs from the expected one.");
    }

    @Override
    @AfterMethod (alwaysRun = true)
    public void tearDown() {
        super.tearDown();
    }

    @AfterClass (alwaysRun = true)
    public void classTearDown() {
        this.setUp();
        CDSUsersPage cdsUsersPage = cdsHomePage.openUsersPage();
        cdsUsersPage.getFilteredUser(firstNameOfNewUser + " " + lastNameOfNewUser, true).deleteUser();
        documentsPage = cdsUsersPage.openDocumentsPage();
        documentsPage.navigateToFolder(baseCDSFolder);
        int numberOfFolderItems = documentsPage.getDataTable().getNumberOfTableRows();

        if (numberOfFolderItems > 0) {
            documentsPage.archiveAllItemsInFolder();
        }
        this.tearDown();
    }

//----------------------------------------------------------------------------------------------------------------------

    /**
     * Uploads an image if it hasn't been uploaded before.
     */
    private void uploadImageIfNotUploaded() {
        if (!isImageUploaded) {
            an_imageUploadTest();
            driver.get(ENVIRONMENT.env.get("CDS_URL"));
            cdsHomePage.waitUntilAvailable();
        }
    }
}