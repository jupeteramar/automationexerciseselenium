package org.seleniumtests.utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ru.yandex.qatools.ashot.shooting.ViewportPastingDecorator;

import javax.imageio.ImageIO;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    public static void takeScreenshot(WebDriver driver, String screenshotName, int index) {
        try {
            String timestamp = new SimpleDateFormat("MMM_dd_yyyy_HHmm").format(new Date());
            // Detect display scaling factor (e.g., 1.0, 1.25, 1.5)
            JavascriptExecutor js = (JavascriptExecutor) driver;
            Number devicePixelRatio = (Number) js.executeScript("return window.devicePixelRatio");
            float scalingFactor = devicePixelRatio.floatValue();

            // Full-page screenshot with scaling compensation
            Screenshot screenshot = new AShot()
                    .shootingStrategy(
                            new ViewportPastingDecorator(
                                    ShootingStrategies.scaling(scalingFactor)   // compensate DPI
                            ).withScrollTimeout(200) // stitching delay
                    )
                    .takeScreenshot(driver);

            // Save locally with a timestamp
            String fileName = "screenshots/" + screenshotName + " - " + timestamp + " - " + index + ".png";
            File file = new File(fileName);
            file.getParentFile().mkdirs(); // Ensure folder exists
            ImageIO.write(screenshot.getImage(), "PNG", file);
            System.out.println("✅ Screenshot saved locally at: " + file.getAbsolutePath());

            // Attach to Allure
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(screenshot.getImage(), "PNG", baos);
            baos.flush();
            Allure.addAttachment(fileName, new ByteArrayInputStream(baos.toByteArray()));

        } catch (IOException e) {
            System.out.println("❌ Exception while taking screenshot: " + e.getMessage());
        }
    }

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
            String screenshotName = testName + "_" + timestamp;

            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(screenshotName, new ByteArrayInputStream(screenshot));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Attach scrollshot steps to Allure
    public static void attachScrollShotSteps(WebDriver driver, String testName) {
        try {
            String timestamp = new SimpleDateFormat("MMddyyyy_HHmm").format(new Date());
            String screenshotName = testName + "_" + timestamp;

            // Take full-page scrollshot with AShot
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);

            // Convert BufferedImage to byte[]
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(screenshot.getImage(), "PNG", baos);

            byte[] screenshotBytes = baos.toByteArray();

            // Attach to Allure
            Allure.addAttachment(screenshotName, new ByteArrayInputStream(screenshotBytes));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Jai's Code
//    public static void takeScreenshot(WebDriver driver, String screenshotName) {
//        try {
//            Screenshot screenshot = new AShot()
//                    .shootingStrategy(ShootingStrategies.viewportPasting(200))
//                    .takeScreenshot(driver);
//
//            // Save locally with a timestamp to avoid overwriting
//            String fileName = "screenshots/fullpage_" + System.currentTimeMillis() + ".png";
//            File file = new File(fileName);
//
//            // Make sure folder exists
//            file.getParentFile().mkdirs();
//
//            ImageIO.write(screenshot.getImage(), "PNG", file);
//            System.out.println("Screenshot saved locally at: " + file.getAbsolutePath());
//
//            // Attach to Allure
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ImageIO.write(screenshot.getImage(), "PNG", baos);
//            baos.flush();
//
//            Allure.addAttachment(screenshotName, new ByteArrayInputStream(baos.toByteArray()));
//
//        } catch (IOException e) {
//            System.out.println("Exception while taking screenshot: " + e.getMessage());
//        }
//    }

    // Save screenshot locally
    public static void saveScreenshot(WebDriver driver, String testName, String status) {
        try {
            String timestamp = new SimpleDateFormat("mmddyyyy_hhmm").format(new Date());
            String screenshotName = testName + "_" + timestamp + ".png";

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

    public static void saveScrollShot(WebDriver driver, String testName) {
        try {
            String timestamp = new SimpleDateFormat("MMddyyyy_HHmm").format(new Date());
            String screenshotName = testName + "_" + timestamp + ".png";

            // Define storage path (screenshots folder inside project)
            File destination = new File("screenshots/" + screenshotName);

            // Ensure directory exists
            destination.getParentFile().mkdirs();

            // Take full-page scrollshot
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);

            // Save image as PNG
            ImageIO.write(screenshot.getImage(), "PNG", destination);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
