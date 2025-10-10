package org.seleniumtests.base;

import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.seleniumtests.data.User;
import org.seleniumtests.pages.*;
import org.seleniumtests.utils.FileUtils;
import org.seleniumtests.utils.TestListener;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Listeners({AllureTestNg.class, TestListener.class})
public class BaseTest {
    protected WebDriver driver;

    // POM Declaration
    public CartPage pgCart;
    protected CheckoutPage pgCheckout;
    protected ProductsPage pgProducts;
    protected ProductViewPage pgProductView;
    protected SignInPage pgSignIn;
    protected SignUpPage pgSignUp;

    // Screenshot
    protected String ssName;
    protected String ssPath;
    protected Integer ssIndex;


    @BeforeMethod
    public void SetUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();

        // Open target web application
        driver.get("https://automationexercise.com/");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        // Close the browser after each test

        if (driver != null) {
            driver.quit();
        }
    }

    public void userAuthentication() {
        pgSignUp.goToSignupLogin();

        pgSignUp.typeName(User.NAME);
        pgSignUp.typeEmail(User.EMAIL);

        pgSignUp.clickSignUpButton();

        if (pgSignUp.isErrorUserAlreadyExistVisible()) {
            pgSignIn.typeSignInCredentials(User.EMAIL, User.PASSWORD);
            pgSignIn.clickLogInButton();
        } else {
            inputUserData();
        }
    }

    public void userRegistration() {
        preRegistration();
        // fill out registration form (no submission)
        inputUserData();
    }

    public void preRegistration() {
        pgSignUp.goToSignupLogin();

        // pre-Registration
        pgSignUp.typeName(User.NAME);
        pgSignUp.typeEmail(User.EMAIL);

        pgSignUp.clickSignUpButton();
    }

    public void inputUserData() {
        pgSignUp.selectTitle(User.TITLE);
        pgSignUp.typePassword(User.PASSWORD);
        pgSignUp.selectDay(User.DAY);
        pgSignUp.selectMonth(User.MONTH);
        pgSignUp.selectYear(User.YEAR);
        pgSignUp.checkPromotions(User.NEWSLETTER, User.OFFERS);
        pgSignUp.typeFirstName(User.FIRST_NAME);
        pgSignUp.typeLastName(User.LAST_NAME);
        pgSignUp.typeCompany(User.COMPANY);
        pgSignUp.typeAddress(User.ADDRESS);
        pgSignUp.typeAddress2(User.ADDRESS2);
        pgSignUp.selectCountry(User.COUNTRY);
        pgSignUp.typeState(User.STATE);
        pgSignUp.typeCity(User.CITY);
        pgSignUp.typeZip(User.ZIP);
        pgSignUp.typeMobileNumber(User.MOBILE_NUMBER);
    }

    public void cleanTestUser() {
        pgSignIn.goToSignupLogin();

        pgSignIn.typeSignInCredentials(User.EMAIL, User.PASSWORD);
        pgSignIn.clickLogInButton();
        pgSignIn.deleteAccount();
    }
}
