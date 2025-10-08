package org.seleniumtests.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FocusUtil {

    public static void focusAndHighlight(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Scroll into view
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);

        // Focus on element
        js.executeScript("arguments[0].focus();", element);

        // Highlight with red border
        js.executeScript("arguments[0].style.border='3px solid red'", element);
    }

    public static void removeHighlight(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border=''", element); // resets border
    }
}
