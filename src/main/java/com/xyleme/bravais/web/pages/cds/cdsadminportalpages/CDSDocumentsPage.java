package com.xyleme.bravais.web.pages.cds.cdsadminportalpages;

import com.xyleme.bravais.datacontainers.FileType;
import com.xyleme.bravais.utils.FileDownloader;
import com.xyleme.bravais.web.elements.*;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tables.TableOnDocumentsPage;
import com.xyleme.bravais.web.pages.cds.cdsdatatables.tablerows.RowOfTableOnDocumentsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.CDSDocumentDetailsPage;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsfolderdetailspage.CDSFolderDetailsPage;
import com.xyleme.bravais.web.pages.cds.functionalmobules.additemform.BaseAddItemForm;
import com.xyleme.bravais.web.pages.cds.popupdialogs.confirmationpopupdialogs.ArchivingConfirmationDialog;
import com.xyleme.bravais.web.pages.cds.popupdialogs.uploadingdialog.FileUploadingDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.xyleme.bravais.BaseTest.staticSleep;
import static com.xyleme.bravais.DriverMaster.pathToFilesForUploadingFolder;

/**
 * Implementation of CDS Documents page.
 */
public class CDSDocumentsPage extends BaseCDSPageWithDataTableAndBulkChangesDropDown {

    public CDSDocumentsPage(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
        getDataTable();
    }

    @Override
    public CDSDocumentsPage load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() &&
                breadCrumbElement().isAvailable() &&
                currentFolderLabelOnBreadCrumbElement().isAvailable() &&
                detailLinkOnBreadCrumbElement().isAvailable();
    }

    private Button addDocumentButton() {
        return new Button(driver, By.xpath("//span[normalize-space()='Add Document']"));
    }

    private Element addDocumentButtonInput() {
        return new Element(driver, By.xpath(addDocumentButton().getXpathLocatorValue() + "//input"));
    }

    private Button addFolderButton() {
        return new Button(driver, By.xpath("//button[normalize-space()='Add Folder']"));
    }

    private Element breadCrumbElement() {
        return new Element(driver, By.xpath("//div[contains(@class, 'breadcrumb')]"));
    }

    private Link linkToFolderOnBreadCrumbElement(String folderName) {
        return new Link(driver, By.xpath(breadCrumbElement().getXpathLocatorValue() + "//a[normalize-space()='" +
                folderName + "']"));
    }

    private LabelText currentFolderLabelOnBreadCrumbElement() {
        return new LabelText(driver, By.xpath(breadCrumbElement().getXpathLocatorValue() +
                "//li[starts-with(@class, 'active')]/span"));
    }

    private Link detailLinkOnBreadCrumbElement() {
        return new Link(driver, By.xpath(breadCrumbElement().getXpathLocatorValue() + "//a[text()='Details']"));
    }

    /**
     * Checks if 'Add Document' button is available on the page.
     *
     * @return {@code boolean}
     */
    public boolean isAddDocumentButtonAvailable() {
        return addDocumentButton().isAvailable();
    }

    /**
     * Checks if 'Add Folder' button is available on the page.
     *
     * @return {@code boolean}
     */
    public boolean isAddFolderButtonAvailable() {
        return addFolderButton().isAvailable();
    }

    /**
     * Gets name of the opened folder (the folder the user is located in).
     *
     * @return {@code String}
     */
    public String getNameOfCurrentFolder() {
        return currentFolderLabelOnBreadCrumbElement().getText();
    }

    /**
     * Clicks link in the bread crumbs block which leads to the specified outer folder.
     *
     * @param folderName - Specifies the outer folder intended to be navigated to
     * @return {@code CDSDocumentsPage}
     */
    public CDSDocumentsPage goToOuterFolderViaLinkInBreadCrumbsBlock(String folderName) {
        linkToFolderOnBreadCrumbElement(folderName).waitUntilAvailable().click();
        return this;
    }

    /**
     * Waits until all folder items get deleted.
     * NOTE: The method waits up to 20 seconds for the expected condition. The timeout can be adjusted if needed.
     *
     * @return {@code CDSDocumentsPage}
     */
    private CDSDocumentsPage waitUntilAllFolderItemsAreDeleted() {
        int timeOutInSeconds = 20;

        for (int i = 0; i <= timeOutInSeconds; i++) {

            if (getDataTable().getNumberOfTableRows() > 0) {
                staticSleep(1);
            } else {
                break;
            }
        }
        int numberOfFolderItems = getDataTable().getNumberOfTableRows();

        if (numberOfFolderItems == 0) {
            System.out.println("All items were successfully deleted from the target CDS folder.");
        } else {
            throw new RuntimeException("Not all items were deleted from the target CDS folder! Number of " +
                    "remaining folder items: " + numberOfFolderItems);
        }
        return this;
    }

    /**
     * Archives all items i a folder.
     *
     * @return {@code CDSDocumentsPage}
     */
    public CDSDocumentsPage archiveAllItemsInFolder() {
        int numberOfFolderItems = getDataTable().getNumberOfTableRows();

        if (numberOfFolderItems > 0) {
            getDataTable().checkSelectAllCheckbox();
            selectOptionOfBulkChangesDropDown("Archive");
            new ArchivingConfirmationDialog(driver).confirmAction();
            waitUntilAllFolderItemsAreDeleted();
        } else {
            System.out.println("Target CDS folder is empty.");
        }
        return this;
    }

    /**
     * Downloads pre-selected items (documents or/and folders) using 'Download' option in Bulk Changes drop-down.
     *
     * @param itemsToDownload - Specifies items intended to be downloaded (items of RowOfTableOnDocumentsPage type)
     * @return {@code String}
     */
    public String downloadSelectedItemsViaBulkChangesMenu(List<RowOfTableOnDocumentsPage> itemsToDownload) {

        if (isOptionPresentInBulkChangesDropDown("Download")) {
            String pathToDownloadedFile = null;
            List<String> comaSeparatedIds = new ArrayList<>();
            itemsToDownload.forEach(item -> comaSeparatedIds.add(item.getId()));
            String url = driver.getCurrentUrl();
            String folderId = url.substring(url.lastIndexOf("/") + 1);
            String downloadingURL = ENVIRONMENT.env.get("CDS_CORE_URL") + "/api/dynamic/folders/" + folderId + "/items?ids=" +
                    URLEncoder.encode(String.valueOf(comaSeparatedIds));

            try {
                pathToDownloadedFile = new FileDownloader(driver, downloadingURL).downloadFile(FileType.ZIP_FOR_DOCUMENT_DOWNLOADING);
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert pathToDownloadedFile != null;
            return pathToDownloadedFile;
        } else {
            throw new RuntimeException("Selected items cannot be downloaded using the 'Download' option of Bulk Changes " +
                    "drop-down since the option is not available in the menu!");
        }
    }

    /**
     * Uploads specifies file available in 'filesForUploading' folder.
     *
     * @param file - Specifies name and extension of the file intended to be uploaded
     * @return {@code CDSDocumentDetailsPage}
     */
    public CDSDocumentDetailsPage uploadFileFromFilesForUploadingFolder(String file) {

        if (addDocumentButton().isAvailable()) {
            addDocumentButtonInput().sendKeys(pathToFilesForUploadingFolder + file);
            return new FileUploadingDialog(driver).completeUploading(CDSDocumentDetailsPage.class);
        } else {
            throw new RuntimeException("'Add Document' button is not available! The document cannot be uploaded.");
        }
    }

    /**
     * Uploads specified files available in 'filesForUploading' folder.
     *
     * @param files - Specifies the files (names with extensions) intended to be uploaded
     * @return {@code CDSDocumentsPage}
     */
    public CDSDocumentsPage uploadMultipleFilesFromFilesForUploadingFolder(String[] files) {
        // NOTE: The following approach works only locally (directly on a local machine without using selenium grid and with the grid)!
//        String pathToMultipleFiles = "";
//        for (String file : files) {
//            pathToMultipleFiles = pathToMultipleFiles + pathToFilesForUploadingFolder + file + "\n";
//        }
//        pathToMultipleFiles = pathToMultipleFiles.substring(0, pathToMultipleFiles.length() - 1);
//        if (addDocumentButton().isAvailable()) {
//            addDocumentButtonInput().sendKeys(pathToMultipleFiles);
//            return new FileUploadingDialog(driver).completeUploading(CDSDocumentsPage.class);
//        } else {
//            throw new RuntimeException("'Add Document' button is not available! The documents cannot be uploaded.");
//        }

//----------------------------------------------------------------------------------------------------------------------

        // NOTE: The following approach works on both types of machines - local and remote.
        if (addDocumentButton().isAvailable()) {

            for (String file : files) {
                addDocumentButtonInput().sendKeys(pathToFilesForUploadingFolder + file);
                staticSleep(0.5);
            }
            return new FileUploadingDialog(driver).completeUploading(CDSDocumentsPage.class);
        } else {
            throw new RuntimeException("The 'Add Document' button is not available! Documents cannot be uploaded.");
        }
    }

    /**
     * Clicks '+ Add Folder' button and returns Add Folder form.
     *
     * @return {@code AddFolderForm}
     */
    private AddFolderForm clickAddFolderButton() {
        addFolderButton().waitUntilAvailable().click();
        return new AddFolderForm(driver);
    }

    /**
     * Creates a new folder with a specified name and description.
     *
     * @param folderName - Specifies name of the folder
     * @param folderDescription - Specifies description of the folder
     * @return {@code CDSDocumentsPage}
     */
    public CDSDocumentsPage createNewFolder(String folderName, String folderDescription) {
        return clickAddFolderButton().addFolder(folderName, folderDescription);
    }

    /**
     * Renames existing folder with a specified name.
     *
     * @param originalName - Specifies original name of the existing folder intended to be renamed
     * @param newName      - Specifies a new name intended to be assigned to the folder
     * @return {@code CDSFolderDetailsPage}
     */
    public CDSFolderDetailsPage renameFolder(String originalName, String newName) {
        RowOfTableOnDocumentsPage row = getFilteredTableItem(originalName, true);
        CDSFolderDetailsPage folderDetailsPage = row.goToFolderDetails();
        return folderDetailsPage.editFolderName(newName);
    }

    /**
     * Gets Documents table available on the page.
     *
     * @return {@code TableOnDocumentsPage}
     */
    @Override
    public TableOnDocumentsPage getDataTable() {
        waitForAngularJSProcessing();
        return new TableOnDocumentsPage(driver);
    }

    /**
     * Gets the first item of the Documents table.
     *
     * @return {@code RowOfTableOnDocumentsPage}
     */
    private RowOfTableOnDocumentsPage getFirstTableItem() {
        return getDataTable().getRow(0);
    }

    /**
     * Enters specified text item into the 'Filter Documents' input field and returns the first table row available
     * after the filtering.
     *
     * @param filteringItem - Specifies the item intended to be entered into the filter field
     * @param fullMatch     - Specifies type of the filtering (true - full match / false - part match)
     * @return {@code RowOfTableOnDocumentsPage}
     */
    public RowOfTableOnDocumentsPage getFilteredTableItem(String filteringItem, boolean fullMatch) {
        enterQueryIntoFilterInputField(filteringItem);
        String name = getFirstTableItem().getItemName();
        boolean condition = fullMatch ? name.equals(filteringItem) : name.startsWith(filteringItem);

        if (condition) {
            return getFirstTableItem();
        } else {
            throw new RuntimeException("Destination folder doesn't contain item with name '" + filteringItem + "'.");
        }
    }

    /**
     * Checks if specified item is available in a folder.
     *
     * @param item - Specifies the item intended to be checked
     * @return {@code boolean}
     */
    public boolean isItemPresentInFolder(String item) {

        try {
            getFilteredTableItem(item, true);
            return true;
        } catch (RuntimeException e) {

            if (e.getMessage().contains("Destination folder doesn't contain item with name") || e.getMessage().contains(
                    "The table is empty")) {
                return false;
            } else {
                throw e;
            }
        }
    }

    /**
     * Navigates to the specified folder in CDS.
     *
     * @param path - Specifies the path to the folder (path format: Folder A|Folder AA|Folder AAA)
     * @return {@code CDSDocumentDetailsPage}
     */
    public CDSDocumentsPage navigateToFolder(String path) {
        List<String> pathElements = new ArrayList<>(Arrays.asList((path).split("\\|")));

        for (String pathElement : pathElements) {
            getFilteredTableItem(pathElement, true).openFolder();
        }
        return this;
    }

    /**
     * Implementation of Add Folder form which appears after clicking '+Add Folder' button.
     */
    private class AddFolderForm extends BaseAddItemForm {

        AddFolderForm(WebDriver driver) {
            super(driver);
            this.waitUntilAvailable();
        }

        @Override
        public boolean isAvailable() {
            return super.isAvailable() &&
                    folderNameInput().isAvailable() &&
                    folderDescriptionInput().isAvailable();
        }

        @Override
        protected Element parentFormElement() {
            return new Element(driver, By.xpath("//form[@name='addFolderForm']"));
        }

        private TextInput folderNameInput() {
            return new TextInput(driver, By.id("new-folder-name"));
        }

        private TextInput folderDescriptionInput() {
            return new TextInput(driver, By.id("new-folder-description"));
        }

        /**
         * Enters specified folder name into the Folder Name input field.
         *
         * @param folderName - Specifies folder name intended to be entered into the input field
         * @return {@code AddFolderForm}
         */
        private AddFolderForm enterFolderName(String folderName) {
            fillOutInputField(folderNameInput(), folderName);
            return this;
        }

        /**
         * Enters specified folder description into the Folder Description input field.
         *
         * @param folderDescription - Specifies folder description intended to be entered into the input field
         * @return {@code AddFolderForm}
         */
        private AddFolderForm enterFolderDescription(String folderDescription) {
            fillOutInputField(folderDescriptionInput(), folderDescription);
            return this;
        }

        /**
         * Adds a new folder with specified name and description.
         *
         * @param folderName        - Specifies folder name
         * @param folderDescription - Specifies folder description
         * @return {@code CDSDocumentsPage}
         */
        CDSDocumentsPage addFolder(String folderName, String folderDescription) {
            enterFolderName(folderName);
            enterFolderDescription(folderDescription);
            return clickAddButton(CDSDocumentsPage.class);
        }
    }
}