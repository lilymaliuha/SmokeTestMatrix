package com.xyleme.bravais.documentcontentextractor;

import com.xyleme.bravais.datacontainers.FileType;
import org.apache.poi.xslf.usermodel.*;
import org.openqa.selenium.WebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of PPTX Document reader.
 */
public class PPTXDocumentReader extends BaseDocumentContentExtractor {

    public PPTXDocumentReader(WebDriver driver) {
        this.driver = driver;
        pathToDownloadedFile = waitUntilPPTDocumentIsDownloadedAndReturnItsFullName();
    }

    public PPTXDocumentReader(WebDriver driver, String pathToDownloadedFile) {
        this.driver = driver;
        this.pathToDownloadedFile = pathToDownloadedFile;
    }

    /**
     * Reads text content of downloaded PowerPoint file.
     *
     * @return {@code String}
     */
    public List<String> getTitlesOfSlidesOfDownloadedPPTXFile() {
        List<String> slideTitles = new ArrayList<>();
        for (XSLFSlide slide : getSlidesOfPPTXDocument()) {
            slideTitles.add(slide.getTitle());
        }
        return slideTitles;
    }

    /**
     * gets number of slides of downloaded PPTX document.
     *
     * @return {@code int}
     */
    public int getNumberOfSlidesOfDownloadedPPTXDocument() {
        return getSlidesOfPPTXDocument().size();
    }

    /**
     * Gets slides of downloaded PPTX document.
     *
     * @return {@code List<XSLFSlide>}
     */
    private List<XSLFSlide> getSlidesOfPPTXDocument() {
        List<XSLFSlide> slides = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(pathToDownloadedFile);
            XMLSlideShow pptxShow = new XMLSlideShow(file);
            slides = pptxShow.getSlides();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return slides;
    }

    /**
     * Waits until link to a formed PPPX file becomes available on a page, then waits until the document is
     * automatically downloaded and returns its full name (filename with extension).
     *
     * @return {@code String}
     */
    private String waitUntilPPTDocumentIsDownloadedAndReturnItsFullName() {
        return waitUntilMSISPreviewDocumentLinkIsGeneratedAndDownloadGeneratedFile(FileType.PPTX);
    }
}