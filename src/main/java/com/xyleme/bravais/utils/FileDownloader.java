package com.xyleme.bravais.utils;

import com.xyleme.bravais.DriverMaster;
import com.xyleme.bravais.datacontainers.FileType;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.tools.ant.DirectoryScanner;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import static com.xyleme.bravais.BaseTest.staticSleep;
import static com.xyleme.bravais.DriverMaster.pathToDownloadsFolder;
import static com.xyleme.bravais.utils.Utils.defineFileExtension;

/**
 * Implementation of File Downloader utility.
 */
public class FileDownloader {
    private WebDriver driver;
    private String requestURL;

    public FileDownloader(WebDriver driver, String requestURL) {
        this.driver = driver;
        this.requestURL = requestURL;
    }

    /**
     * Downloads specific file using cookies of the active WebDriver session and a request URL which is called using
     * methods of the RestAssured library and returns the path of the downloaded file.
     *
     * @param fileType - Specifies type of the file intended to be downloaded
     * @param fileName - Specifies name of the file intended to be downloaded (this parameter is optional)
     * @return {@code String}
     * @throws IOException - Input/Output exception
     */
    public String downloadFile(FileType fileType, String... fileName) throws IOException {
        String zipContainerOfDownloadedFile;

        createDownloadsDirectory();
        Utils.cleanDefaultDownloadingDirectory();
        CookieStore cookieStore = convertWebDriverSessionCookiesToHttpClientCookies();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.setCookieStore(cookieStore);

        HttpResponse response = httpClient.execute(new HttpGet(requestURL));
        int responseStatusCode = response.getStatusLine().getStatusCode();

        if (responseStatusCode == 200) {
            InputStream inputStream = response.getEntity().getContent();
            File outputFile = new File(getOutputFilePath(fileType, fileName));
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            int read;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, read);
            }
            fileOutputStream.close();
            inputStream.close();
            zipContainerOfDownloadedFile = outputFile.getAbsolutePath();
        } else {
            throw new RuntimeException("Status code of the response is not 200. File cannot be downloaded." +
                    "\n > Status code of the response: " + responseStatusCode);
        }

        if (fileType.equals(FileType.ZIP) && !fileType.equals(FileType.ZIP_FOR_DOCUMENT_DOWNLOADING)) {
            unzipFile(zipContainerOfDownloadedFile, DriverMaster.pathToDownloadsFolder);
            new File(zipContainerOfDownloadedFile).delete();
        }
        String pathToDownloadedFile = DriverMaster.pathToDownloadsFolder + findFilesInDir(fileType)[findFilesInDir(fileType).length - 1];
        System.out.println("Downloaded file: " + pathToDownloadedFile);
        return pathToDownloadedFile;
    }

    /**
     * Gets full output path of the file expected to be downloaded.
     *
     * @param fileType - Specifies type of the file intended to be downloaded
     * @param fileName - Specifies name of the file intended to be downloaded
     * @return {@code String}
     */
    private String getOutputFilePath(FileType fileType, String... fileName) {
        String outputFilePath;
        String fileExtension = defineFileExtension(fileType);

        if (fileName.length == 0) {

            if (!fileType.equals(FileType.ZIP) && !fileType.equals(FileType.ZIP_FOR_EXPORT) &&
                    !fileType.equals(FileType.ZIP_FOR_DOCUMENT_DOWNLOADING)) {
                outputFilePath = DriverMaster.pathToDownloadsFolder + "downloaded" + fileExtension;
            } else {
                outputFilePath = DriverMaster.pathToDownloadsFolder + "downloaded.zip";
            }
        } else {
            assert fileName.length == 1;
            outputFilePath = DriverMaster.pathToDownloadsFolder + fileName[0] + fileExtension;
        }
        return outputFilePath;
    }

    /**
     * Gets cookies from the active WebDriver session and converts them into RestAssured cookies.
     *
     * @return {@code CookieStore}
     */
    private CookieStore convertWebDriverSessionCookiesToHttpClientCookies() {
        Set<Cookie> seleniumCookies = driver.manage().getCookies();
        CookieStore cookieStore = new BasicCookieStore();

        for (Cookie seleniumCookie : seleniumCookies) {
            BasicClientCookie basicClientCookie = new BasicClientCookie(seleniumCookie.getName(), seleniumCookie.getValue());
            basicClientCookie.setDomain(seleniumCookie.getDomain());
            basicClientCookie.setExpiryDate(seleniumCookie.getExpiry());
            basicClientCookie.setPath(seleniumCookie.getPath());
            cookieStore.addCookie(basicClientCookie);
        }
        return cookieStore;
    }

    /**
     * Creates the 'downloads' directory.
     */
    private void createDownloadsDirectory() {
        new File(pathToDownloadsFolder).mkdir();
    }

    /**
     * Finds files with specified extension in the downloading target folder.
     *
     * @param fileType - Specifies file type
     * @return {@code String[]}
     */
    private String[] findFilesInDir(FileType fileType) {
        String fileExtension = defineFileExtension(fileType);
        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setIncludes(new String[]{"**/*" + fileExtension});
        scanner.setBasedir(pathToDownloadsFolder);
        scanner.setCaseSensitive(false);
        scanner.scan();
        int i = 0;

        while (scanner.getIncludedFiles().length == 0 && i < 100) {
            scanner.scan();
            staticSleep(1);
            i++;
        }

        if (i == 100) {
            throw new RuntimeException("The 'downloads' folder does not contain any files with extension '" +
                    fileExtension + "'.");
        }
        return scanner.getIncludedFiles();
    }

    /**
     * Extracts all content (files and folders) of the specified zipped file.
     *
     * @param zipFilePath           - Specifies path to the zip file
     * @param destinationFolderPath - Specifies path to destination folder == the folder which will contain unzipped files
     */
    public static void unzipFile(String zipFilePath, String destinationFolderPath) {

        try {
            ZipFile zipFile = new ZipFile(zipFilePath);
            zipFile.extractAll(destinationFolderPath);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }
}