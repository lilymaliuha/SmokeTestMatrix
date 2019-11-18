package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels.previewpane.previewarea.visualdocumentpreview;

import com.xyleme.bravais.datacontainers.PreviewType;
import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.CheckBox;
import com.xyleme.bravais.web.elements.LabelText;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.xyleme.bravais.BaseTest.staticSleep;

/**
 * Implementation of preview of a Course-type documents.
 */
public class CoursePreview extends BaseVisualDocumentPreview {
    private String nextButtonMissingError = "'Next course page' button is not present on cds course preview page.";
    private String previousButtonMissingError = "'Previous course page' button is not present on cds course preview page.";

    public CoursePreview(WebDriver driver) {
        super(driver);
        waitForInvisibilityOfSpinnerOnPageInIFrame(PreviewType.FULL_PREVIEW);
    }

    private Button confirmationButton() {
        return new Button(driver, By.cssSelector(".ConfirmButton"));
    }

    private CheckBox submitAllCheckbox() {
        return new CheckBox(driver, By.cssSelector(".SubmitAllConfirmationDialog input"));
    }

    private Button okButtonOnConfirmationDialog() {
        return new Button(driver, By.cssSelector("button[title='OK']"));
    }

    private Button btnContent() {
        return new Button(driver, By.xpath(".//*[@id='MainMenuIcon']"));
    }

    private LabelText lblPageTitle() {
        return new LabelText(driver, By.cssSelector("#PageBody div .Title"));
    }

    private WebElement richTextElementOfParaBlock(int paraBlockIndex) {
        return getElementDynamically(driver.findElements(By.xpath("//div[@id='PageBodyWrapper']//div[@class='ParaBlock']"))
                .get(paraBlockIndex).findElement(By.xpath("./div[@class='RichText']")));
    }

    private WebElement imageOnCoursePage(int imageIndexOnPage) {
        return getElementDynamically(driver.findElements(By.xpath("//div[@id='PageBody']//div[@class='Figure Wrap']/img"))
                .get(imageIndexOnPage));
    }

    private LabelText lblPopupAlertOnCoursePage() {
        return new LabelText(driver, By.cssSelector(".NotifyMessage"));
    }

    private Button btnNavigateToNextPage() {
        return new Button(driver, By.id("NavigationButtonNext"));
    }

    private Button btnNavigateToPreviousPage() {
        return new Button(driver, By.id("NavigationButtonPrevious"));
    }

    /**
     * Checks if content menu button is available on a page.
     *
     * @return {@code boolean}
     */
    public boolean isContentMenuButtonAvailable() {
        return btnContent().isAvailable();
    }

    /**
     * Gets page title.
     *
     * @return {@code String}
     */
    public String getPageTitle() {
        return lblPageTitle().waitUntilAvailable().getText();
    }

    /**
     * Gets rich text of specific ParaBlock on course page.
     *
     * @param paraBlockIndex - Specifies index of ParaBlock on a course page
     * @return {@code String}
     */
    public String getParaBlockRichText(int paraBlockIndex) {
        return richTextElementOfParaBlock(paraBlockIndex).getText();
    }

    /**
     * Gets name of specific image located on a course page.
     *
     * @param imageIndex - Specifies index of the image on course page
     * @return {@code String}
     */
    public String getNameOfCoursePageImage(int imageIndex) {
        String src = imageOnCoursePage(imageIndex).getAttribute("src");
        return src.substring(src.lastIndexOf("/")).replaceAll("/", "");
    }

    /**
     * Navigates to next course page by clicking 'Next Page' button.
     */
    private void navigateToNextPage() {
        new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfElementLocated(By.id("toast-container")));
        btnNavigateToNextPage().waitUntilAvailable().click();
        staticSleep(1);
        confirmNavigationFromPage();
    }

    /**
     * Checks if 'Next course page' button is displayed in course preview.
     *
     * @return {@code boolean}
     */
    public boolean isNextCoursePageButtonDisplayed() {
        try {
            return btnNavigateToNextPage().isDisplayed();
        } catch (NoSuchElementException e) {
            System.out.println(nextButtonMissingError);
            return false;
        }
    }

    /**
     * Checks if 'Previous course page' button is displayed in course preview.
     *
     * @return {@code boolean}
     */
    public boolean isPreviousCoursePageButtonDisplayed() {
        try {
            return btnNavigateToPreviousPage().isDisplayed();
        } catch (NoSuchElementException e) {
            System.out.println(previousButtonMissingError);
            return false;
        }
    }

    /**
     * Checks if Next course page button is enabled in course preview.
     *
     * @return {@code boolean}
     */
    public boolean isNextCoursePageButtonEnabled() {
        staticSleep(0.5);
        if (isNextCoursePageButtonDisplayed()) {
            return btnNavigateToNextPage().getAttribute("class").contains("Enabled");
        } else {
            System.out.println(nextButtonMissingError);
            return false;
        }
    }

    /**
     * Checks if Previous course page button is enabled in course preview.
     *
     * @return {@code boolean}
     */
    public boolean isPreviousCoursePageButtonEnabled() {
        if (isPreviousCoursePageButtonDisplayed()) {
            return btnNavigateToPreviousPage().getAttribute("class").contains("Enabled");
        } else {
            System.out.println(previousButtonMissingError);
            return false;
        }
    }

    /**
     * Confirms navigation from course page - closes all confirmation popups.
     */
    private void confirmNavigationFromPage() {
        if (confirmationButton().isAvailable()) {
            confirmationButton().click();
            if (submitAllCheckbox().isAvailable()) {
                submitAllCheckbox().click();
                confirmationButton().click();
            } else if (okButtonOnConfirmationDialog().isAvailable()) {
                okButtonOnConfirmationDialog().click();
            }
        }
    }

    /**
     * Gets popup alert message which appears on course page after clicking on disabled "next course page" button in course preview.
     *
     * @return {@code String}
     */
    private String getPopupAlertMessage() {
        String alert = "";
        try {
            alert = lblPopupAlertOnCoursePage().getText();
        } catch (NoSuchElementException ignored) {}
        return alert;
    }

    /**
     * Navigates to a next course page (clicks all the required tabs, flash cards and submits confirmation dialogs if required on the way).
     */
    public void goToNextCoursePage() {
        String alertMessage;
        if (isNextCoursePageButtonEnabled()) {
            navigateToNextPage();
        } else if (!isNextCoursePageButtonEnabled()) {
            navigateToNextPage();
            alertMessage = getPopupAlertMessage();
            if (alertMessage.equalsIgnoreCase("Please view all tabs before proceeding.")) {
                for (WebElement tab : driver.findElements(By.cssSelector("#PageBodyWrapper .TabsNavigation li .TabsPanelTitle"))) {
                    tab.click();
                    staticSleep(1);
                }
            } else if (alertMessage.equalsIgnoreCase("Please visit all flash cards before proceeding.")) {
                new Button(driver, By.cssSelector("#PageBodyWrapper .ChooseModePracticeButton")).waitUntilAvailable().click(); // Clicks 'Practice' mode button
                int numberOfCards = driver.findElements(By.cssSelector("#PageBodyWrapper .CardFront")).size();
                for (int i = 0; i < numberOfCards - 1; i++) {
                    driver.findElements(By.cssSelector("#PageBodyWrapper .CardFront")).get(i).click();
                    staticSleep(0.5);
                    driver.findElement(By.cssSelector("#PageBodyWrapper button[title='Next']")).click();
                    staticSleep(0.5);
                }
                driver.findElements(By.cssSelector("#PageBodyWrapper .CardFront")).get(numberOfCards - 1).click();
            } else if (alertMessage.equalsIgnoreCase("The next page is not available at this time. " +
                    "Use the table of contents to navigate to a different page.")) {
                System.out.println("The test has reached the last course page.");
            }
        }
    }
}