package org.seleniumtests.utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    // Attach screenshot to Allure
    public static void attachScreenshot(WebDriver driver, String testName, String status) {
        try {
            String timestamp = new SimpleDateFormat("MMddyyyy_HHmm").format(new Date());
            String screenshotName = testName + "_" + status + "_" + timestamp;

            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(screenshotName, new ByteArrayInputStream(screenshot));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Attach screenshot steps to Allure
    public static void attachScreenshotSteps(WebDriver driver, String testName) {
        try {
            String timestamp = new SimpleDateFormat("MMddyyyy_HHmm").format(new Date());
            String screenshotName = testName + "_" +  timestamp;

            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(screenshotName, new ByteArrayInputStream(screenshot));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Save screenshot locally
    public static void saveScreenshot(WebDriver driver, String testName, String status) {
        try {
            String timestamp = new SimpleDateFormat("MMddyyyy_HHmm").format(new Date());
            String screenshotName = testName + "_" + status + "_" + timestamp + ".png";

            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Define storage path (screenshots folder inside project)
            File destination = new File("screenshots/" + screenshotName);

            // Ensure directory exists
            destination.getParentFile().mkdirs();

            // Copy file
            try (InputStream in = new FileInputStream(screenshotFile);
                 OutputStream out = new FileOutputStream(destination)) {
                in.transferTo(out);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
