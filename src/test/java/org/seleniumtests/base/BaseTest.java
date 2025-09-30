package org.seleniumtests.base;

import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.seleniumtests.utils.TestListener;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

@Listeners({AllureTestNg.class})
public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void SetUp(){
        System.setProperty("webdriver.chrome.driver", "C:/Selenium_data/chromedriver.exe");
        driver = new ChromeDriver();

        // Browser Configuration
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();

        // Navigate to Home Page
        driver.get("https://automationexercise.com/");
    }

    @AfterMethod
    public void tearDown(ITestResult result){

        if (driver != null){
            driver.quit();
        }
    }
}
