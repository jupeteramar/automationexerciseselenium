package org.seleniumtests.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import  java.time.Duration;

public class ClickUtils {

    public static void safeClick(WebDriver driver, By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));

        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

            // Scroll into view first
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);

            // Wait until it is clickable
            wait.until(ExpectedConditions.elementToBeClickable(locator));

            // Highlight the element in red border
            redBorder(driver, element);

            // Try normal Selenium click
            element.click();

        } catch (Exception e) {
            // If Selenium click fails (overlap/interception), try JS click
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

            System.out.println("⚠️ Selenium click failed, used JS click as fallback for: " + locator);
        }
    }

    public static void safeClickElement(WebDriver driver, WebElement element, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));

        try {
            // Scroll into view first
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);

            // Wait until it is clickable
            wait.until(ExpectedConditions.elementToBeClickable(element));

            // Highlight the element in red border
            redBorder(driver, element);

            // Try normal Selenium click
            element.click();

        } catch (Exception e) {
            // If Selenium click fails (overlap/interception), try JS click
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            System.out.println("⚠️ Selenium click failed, used JS click as fallback.");
        }
    }

    public static void redBorder(WebDriver driver, WebElement element){
        // Highlight the element in red border
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='5px solid red'", element);

    }

}
