package com.xyleme.bravais.documentcontentextractor;

import com.xyleme.bravais.datacontainers.FileType;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import static com.xyleme.bravais.BaseTest.staticSleep;
import static com.xyleme.bravais.DriverMaster.headlessMode;


/**
 * Implementation of PDF Document viewer (PDF document opened in browser).
 */
public class PDFDocumentPreview extends BaseDocumentContentExtractor {
    private PDDocument openedPDFDocument;

    public PDFDocumentPreview(WebDriver driver) {
        this.driver = driver;
        openedPDFDocument = getPDFDocument(waitForPDFDocumentOpeningByMSISPreviewAndReturnDocumentURL());
    }

    public PDFDocumentPreview(WebDriver driver, String urlOfPDFDocument) {
        this.driver = driver;
        openedPDFDocument = getPDFDocument(urlOfPDFDocument);
    }

    /**
     * Gets and parses PDF document opened in browser (or downloaded PDF file, in case of remote execution).
     *
     * @param urlOrPathToPDFFile - Specifies URL of PDF file opened in a browser or in case of remote execution - specifies
     *                             path to downloaded PDF file
     * @return {@code PDDocument}
     */
    private PDDocument getPDFDocument(String urlOrPathToPDFFile) {
        URL url;
        PDDocument pdDocument = null;
        try {
            if (urlOrPathToPDFFile.startsWith("http")) {
                url = new URL(urlOrPathToPDFFile);
            } else {
                url = new File(urlOrPathToPDFFile).toURL();
            }
            BufferedInputStream file = new BufferedInputStream(url.openStream());
            PDFParser parser = new PDFParser(new RandomAccessBufferedFileInputStream(file));
            parser.parse();
            COSDocument document = parser.getDocument();
            pdDocument = new PDDocument(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert pdDocument != null;
        return pdDocument;
    }

    /**
     * Gets text of specific page of PDF document opened in a browser.
     *
     * @param startPage - Specifies beginning of range of pages text of which is intended to be extracted
     * @param endPage   - Specifies end of range of pages text of which is intended to be extracted
     * @return {@code String}
     */
    private String getTextOfSpecificPageOfOpenedPDFDocument(int startPage, int endPage) {
        String parsedText = null;
        try {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            if (startPage <= endPage && endPage > 0) {
                pdfStripper.setStartPage(startPage);
                pdfStripper.setEndPage(endPage);
            }
            parsedText = pdfStripper.getText(openedPDFDocument);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parsedText;
    }

    /**
     * Gets number of pages of PDF document opened in a browser.
     *
     * @return {@code int}
     */
    public int getNumberOfPagesOfDownloadedPDFFile() {
        return openedPDFDocument.getNumberOfPages();
    }

    /**
     * Gets whole text of PDF document opened in a browser.
     *
     * @return {@code String}
     */
    public String getWholeTextOfPDFDocument() {
        return getTextOfSpecificPageOfOpenedPDFDocument(0, 0);
    }

    /**
     * Waits until PDF document is opened in browser (course previewed in MSIS output) and returns document's URL.
     *
     * @return {@code String}
     */
    private String waitForPDFDocumentOpeningByMSISPreviewAndReturnDocumentURL() {
        String urlToReturn;

        if (headlessMode) {
            urlToReturn = waitUntilMSISPreviewDocumentLinkIsGeneratedAndDownloadGeneratedFile(FileType.PDF);
        } else {
            while (!driver.getCurrentUrl().startsWith("https://msis")) {
                staticSleep(1);
            }

            System.out.println("MSIS Preview launched...");

            int timeOut = 0;
            while (driver.getCurrentUrl().startsWith("https://msis") && driver.getCurrentUrl().endsWith("redirect=false")
                    && timeOut < 600) {
                if (!driver.getTitle().contains("ERROR")) {
                    staticSleep(1);
                    timeOut++;
                } else {
                    throw new RuntimeException("Preview process resulted in Error!");
                }
            }
            System.out.println("Time taken by processing PDF document for MSIS preview: " + timeOut + " seconds.");
            new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//embed[@id='plugin']")));
            System.out.println("PDF Viewer is launched...");
            urlToReturn = driver.getCurrentUrl();
        }
        return urlToReturn;
    }
}