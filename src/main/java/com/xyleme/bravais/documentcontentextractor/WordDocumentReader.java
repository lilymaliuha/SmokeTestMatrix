package com.xyleme.bravais.documentcontentextractor;

import com.xyleme.bravais.datacontainers.FileType;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of Word Document reader.
 */
public class WordDocumentReader extends BaseDocumentContentExtractor {

    public WordDocumentReader(WebDriver driver) {
        this.driver = driver;
        pathToDownloadedFile = waitUntilWordDocumentIsDownloadedAndReturnItsFullName();
    }

    public WordDocumentReader(WebDriver driver, String pathToDownloadedDocument) {
        this.driver = driver;
        this.pathToDownloadedFile = pathToDownloadedDocument;
    }

    /**
     * Gets downloaded Word document.
     *
     * @return {@code XWPFDocument}
     */
    private XWPFDocument getDownloadedWordDocument() {
        XWPFDocument docxDocument = null;
        try {
            docxDocument = new XWPFDocument(new FileInputStream(pathToDownloadedFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return docxDocument;
    }

    /**
     * Gets number of pages of downloaded Word document.
     *
     * @return {@code int}
     */
    public int getNumberOfDocumentPages() {
        return getDownloadedWordDocument().getProperties().getExtendedProperties().getPages();
    }

    /**
     * Gets text content of downloaded word document.
     *
     * @return {@code String}
     */
    public String getWordDocumentTextContent() {
        return new XWPFWordExtractor(getDownloadedWordDocument()).getText();
    }

    /**
     * Gets images of downloaded word document.
     *
     * @return {@code List<Image>}
     */
    private List<Image> getWordDocumentImages() {
        List<Image> images = new ArrayList<>();
        List<XWPFPictureData> documentImages = getDownloadedWordDocument().getAllPictures();
        for (XWPFPictureData image : documentImages) {
            byte[] data = image.getData();
            try {
                images.add(ImageIO.read(new ByteArrayInputStream(data)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return images;
    }

    /**
     * Gets number of images if downloaded word document.
     *
     * @return {@code int}
     */
    public int getNumberOfWordDocumentImages() {
        return getWordDocumentImages().size();
    }

    /**
     * Gets sizes (height and width) of each image of downloaded Word document.
     *
     * @return {@code List<Dimension>}
     */
    public List<Dimension> getSizesOfWordDocumentImages()  {
        List<Dimension> sizes = new ArrayList<>();
        for (Image image : getWordDocumentImages()) {
            sizes.add(new Dimension(image.getHeight(null), image.getWidth(null)));
        }
        return sizes;
    }

    /**
     * Waits until link to a formed docx file becomes available on a page, then waits until the document is
     * automatically downloaded and returns its full name (filename with extension).
     *
     * @return {@code String}
     */
    private String waitUntilWordDocumentIsDownloadedAndReturnItsFullName() {
        return waitUntilMSISPreviewDocumentLinkIsGeneratedAndDownloadGeneratedFile(FileType.WORD_DOCX);
    }
}