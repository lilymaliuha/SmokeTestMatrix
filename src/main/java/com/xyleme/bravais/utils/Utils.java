package com.xyleme.bravais.utils;


import com.xyleme.bravais.datacontainers.FileType;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.util.FileUtils;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.text.*;
import java.util.*;

import static com.xyleme.bravais.BaseTest.staticSleep;
import static com.xyleme.bravais.DriverMaster.pathToDownloadsFolder;
import static com.xyleme.bravais.DriverMaster.pathToScreenshotsFolder;

public class Utils {

    private static void cleanDirectory(String folderPath) {

        if (folderPath.equals("")) {
            folderPath = pathToDownloadsFolder;
        }

        File destinationDirectory = new File(folderPath);
        Arrays.stream(Objects.requireNonNull(destinationDirectory.listFiles())).forEach(File::delete); // ToDo: Needs to be tested.
        staticSleep(3);
    }

    /**
     * Cleans the downloads directory.
     */
    static void cleanDefaultDownloadingDirectory() {
        cleanDirectory(pathToDownloadsFolder);
    }

    /**
     * Cleans screenshots of failures taken on previous test run.
     */
    public static void cleanScreenshotsOfFailuresFolder() {
        cleanDirectory(pathToScreenshotsFolder);
    }

    /**
     * Calculates time elapsed from specified date and time. Returns map with the following keys: days, hours, minutes, seconds.
     *
     * @param startDateAndTime - Specifies date with time from which the time difference is calculated (date/time format: 17/07/17 12:31:36)
     * @return {@code Map<String, Integer>}
     */
    public static Map<String, Integer> getElapsedTime(String startDateAndTime) {
        Map<String, Integer> timeElapsed = new HashMap<>();
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date currentDateAndTime = new Date();

        try {
            Date dateStart = df.parse(startDateAndTime);
            //in milliseconds
            long diff = currentDateAndTime.getTime() - dateStart.getTime();
            final long diffSeconds = diff / 1000 % 60;
            final long diffMinutes = diff / (60 * 1000) % 60;
            final long diffHours = diff / (60 * 60 * 1000) % 24;
            final long diffDays = diff / (24 * 60 * 60 * 1000);
            timeElapsed = new HashMap<String, Integer>(){{
                put("days", (int) diffDays);
                put("hours", (int) diffHours);
                put("minutes", (int) diffMinutes);
                put("seconds", (int) diffSeconds);
            }};
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeElapsed;
    }

    /**
     * Writes specified value to the specified property in 'additionaldata' property file.
     *
     * @param property      - specifies the property
     * @param propertyValue - specifies the value which will be recorded as the value of the respective property
     */
    public static void setPropertyToAdditionalDataFile(String property, String propertyValue) {
        Properties prop = new Properties();
        InputStream in = null;
        String path = null;
        URL resource = ClassLoader.class.getResource("/additionaldata.properties");

        try {
            path = URLDecoder.decode(resource.getPath(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        assert path != null;
        File file = new File(path);

        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                assert in != null;
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        prop.setProperty(property, propertyValue);

        try {
            assert out != null;
            prop.store(out, null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert out != null;
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Reads value of the specified property in 'additionaldata' property file.
     *
     * @param property - specifies the property
     * @return {@code String}
     */
    public static String readPropertyValueFromAdditionalDataFile(String property) {
        Properties prop = new Properties();
        String value = null;
        InputStream inputStream = ClassLoader.class.getResourceAsStream("/additionaldata.properties");

        try {
            prop.load(inputStream);
            value = prop.getProperty(property);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    /**
     * Returns name of a file of specified type located in 'downloads' folder.
     *
     * @param fileType - Specifies type of a file intended to be returned.
     * @return {@code String}
     */
    public static String getFileInDownloadsFolder(FileType fileType) {
        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setIncludes(new String[]{"**/*" + defineFileExtension(fileType)});
        scanner.setBasedir(pathToDownloadsFolder);
        scanner.setCaseSensitive(false);
        scanner.scan();
        scanner.getIncludedFiles();
        return scanner.getIncludedFiles()[0];
    }

    /**
     * Gets all files located in 'downloads' folder.
     *
     * @return {@code List<String>}
     */
    public static List<String> getFilesInDownloadsFolder() {
        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setBasedir(pathToDownloadsFolder);
        scanner.setCaseSensitive(false);
        scanner.scan();
        scanner.getIncludedFiles();
        return Arrays.asList(scanner.getIncludedFiles());
    }

    /**
     * Defines and returns extension of the specified file type.
     *
     * @param fileType - Specifies file type
     * @return {@code String}
     */
    static String defineFileExtension(FileType fileType) {
        String fileExtension = "";

        switch (fileType) {
            case WORD_DOC:
                fileExtension = ".doc";
                break;
            case WORD_DOCX:
                fileExtension = ".docx";
                break;
            case PDF:
                fileExtension = ".pdf";
                break;
            case PPT:
                fileExtension =".ppt";
                break;
            case PPTX:
                fileExtension = ".pptx";
                break;
            case XML:
                fileExtension = ".xml";
                break;
            case ZIP:
                fileExtension = ".zip";
                break;
            case EXEL:
                fileExtension = ".xltx";
                break;
            case JPEG:
                fileExtension = ".jpeg";
                break;
        }
        return fileExtension;
    }

    /**
     * Gets a random number within the specified range.
     *
     * @return {@code int}
     */
    public static int getRandomNumber() {
        return (1000 + (int) (Math.random() * ((9999) + 1)));
    }
}