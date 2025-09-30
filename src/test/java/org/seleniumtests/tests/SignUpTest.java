package org.seleniumtests.tests;

import io.qameta.allure.*;
import org.openqa.selenium.WebElement;
import org.seleniumtests.base.BaseTest;
import org.seleniumtests.data.User;
import org.seleniumtests.pages.SignInPage;
import org.seleniumtests.pages.SignUpPage;
import org.seleniumtests.utils.ScreenshotUtils;
import org.seleniumtests.utils.WaitUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Authentication")
@Feature("Sign Up")
public class SignUpTest extends BaseTest {
    SignUpPage pgSignUp;
    SignInPage pgSignIn;

    @BeforeMethod
    public void setUpPages() {
        pgSignIn = new SignInPage(driver);
        pgSignUp = new SignUpPage(driver);
    }

    @Test(description = "Verify a successful user registration")
    @Story("Users can successfully register an account by fulfilling required information")
    @Severity(SeverityLevel.BLOCKER)
    public void registerNewUser() throws InterruptedException {

        preRegistration();

        inputUserData();

        pgSignUp.clickSubmit();

        Assert.assertTrue(pgSignUp.isSuccessRegistrationVisible(), "Nawp");
        Thread.sleep(5000);

        pgSignUp.clickContinueButton();
        pgSignUp.deleteAccount();
    }

    @Test(description = "Verify user can not register user with existing email")
    @Story("Users cannot use an email that has been already used to register")
    @Severity(SeverityLevel.BLOCKER)
    public void registerUsedEmail() throws InterruptedException {
        preRegistration();

        inputUserData();

        pgSignUp.clickSubmit();

        Assert.assertTrue(pgSignUp.isSuccessRegistrationVisible(), "Nawp");
        Thread.sleep(2000);

        pgSignUp.clickContinueButton();

        pgSignUp.logOut();

        preRegistration();
        Assert.assertTrue(pgSignUp.isErrorUserAlreadyExistVisible(), "Nawp");
        Thread.sleep(2000);
        pgSignIn.typeSignInCredentials(User.EMAIL, User.PASSWORD);
        pgSignIn.clickLogInButton();
        pgSignIn.deleteAccount();
    }

    @Test(description = "Verify registration fails with invalid email format (missing '@')")
    @Story("Users cannot register with an invalid email")
    @Severity(SeverityLevel.BLOCKER)
    public void registerInvalidEmail1() throws InterruptedException {
        String invalidEmail = "glennSturgis";
        pgSignUp.goToSignupLogin();

        pgSignUp.typeName(User.NAME);
        pgSignUp.typeEmail(invalidEmail);

        pgSignUp.clickSignUpButton();
        WebElement txtEmail = pgSignUp.getEmailElement();
        String tooltip = txtEmail.getAttribute("validationMessage");
        System.out.println(tooltip);

        String expectedError = "Please include an '@' in the email address. '" + invalidEmail + "' is missing an '@'.";
        Assert.assertEquals(tooltip, expectedError, "Nawp");
        Thread.sleep(1500);
    }

    @Test(description = "Verify registration fails with invalid email format (domain contains special character)")
    @Story("Users cannot register with an invalid email")
    @Severity(SeverityLevel.BLOCKER)
    public void registerInvalidEmailFormat2() throws InterruptedException {
        String invalidEmail = "glensturgis@#cloud.9.org";
        pgSignUp.goToSignupLogin();

        pgSignUp.typeName(User.NAME);
        pgSignUp.typeEmail(invalidEmail);

        pgSignUp.clickSignUpButton();
        WebElement txtEmail = pgSignUp.getEmailElement();
        String tooltip = txtEmail.getAttribute("validationMessage");
        System.out.println(tooltip);

        String expectedError = "A part following '@' should not contain the symbol '#'.";
        Assert.assertEquals(tooltip, expectedError, "Nawp");
        Thread.sleep(1500);
    }

    @Test(description = "Verify registration fails with invalid email format (domain contains more than one dot)")
    @Story("Users cannot register with an invalid email")
    @Severity(SeverityLevel.BLOCKER)
    public void registerInvalidEmailFormat3() throws InterruptedException {
        String invalidEmail = "glensturgis@cloud9..org";
        //String domain = invalidEmail.substring(invalidEmail.indexOf("@") + 1);

        pgSignUp.goToSignupLogin();

        pgSignUp.typeName(User.NAME);
        pgSignUp.typeEmail(invalidEmail);

        pgSignUp.clickSignUpButton();
        WebElement txtEmail = pgSignUp.getEmailElement();
        String tooltip = txtEmail.getAttribute("validationMessage");
        System.out.println(tooltip);

        String expectedError = "'.' is used at a wrong position";

        Assert.assertTrue(tooltip.contains(expectedError));
        Thread.sleep(1500);
    }

    @Test(description = "Verify registration with empty required fields")
    @Story("Users cannot register with an empty field")
    @Severity(SeverityLevel.BLOCKER)
    public void registerBlankFields() throws InterruptedException {
        pgSignUp.goToSignupLogin();

        pgSignUp.clickSignUpButton();

        String tooltip = pgSignUp.tooltipName();
        System.out.println(tooltip);

        String expectedError = "Please fill out this field.";

        Assert.assertTrue(tooltip.contains(expectedError));
        Thread.sleep(1500);
    }

    @Test(description = "Verify required fields show 'Please fill out this field' validation on extended signup page")
    @Story("Users cannot register with an empty field when filling out the user information")
    @Severity(SeverityLevel.BLOCKER)
    public void registerWithRequiredFields() throws InterruptedException {
        preRegistration();

        pgSignUp.clearName2();
        pgSignUp.selectTitle(User.TITLE);
        pgSignUp.selectDay(User.DAY);
        pgSignUp.selectMonth(User.MONTH);
        pgSignUp.selectYear(User.YEAR);
        pgSignUp.checkPromotions(User.NEWSLETTER, User.OFFERS);
        pgSignUp.typeCompany(User.COMPANY);

        // Assert Name
        pgSignUp.clickSubmit();
        Assert.assertEquals(pgSignUp.tooltipName2(), "Please fill out this field.");
        Thread.sleep(1000);

        pgSignUp.typeName2(User.NAME);

        // Assert Password
        pgSignUp.clickSubmit();
        Assert.assertEquals(pgSignUp.tooltipPassword(), "Please fill out this field.");
        Thread.sleep(1000);

        pgSignUp.typePassword(User.PASSWORD);

        // Assert First Name
        pgSignUp.clickSubmit();
        Assert.assertEquals(pgSignUp.tooltipFirstName(), "Please fill out this field.");
        Thread.sleep(1000);

        pgSignUp.typeFirstName(User.FIRST_NAME);

        // Assert Last Name
        pgSignUp.clickSubmit();
        Assert.assertEquals(pgSignUp.tooltipLastName(), "Please fill out this field.");
        Thread.sleep(1000);

        pgSignUp.typeLastName(User.LAST_NAME);

        // Assert Address
        pgSignUp.clickSubmit();
        Assert.assertEquals(pgSignUp.tooltipAddress(), "Please fill out this field.");
        Thread.sleep(1000);

        pgSignUp.typeAddress(User.ADDRESS);

        // Assert State
        pgSignUp.clickSubmit();
        Assert.assertEquals(pgSignUp.tooltipState(), "Please fill out this field.");
        Thread.sleep(1000);

        pgSignUp.typeState(User.STATE);

        // Assert City();
        pgSignUp.clickSubmit();
        Assert.assertEquals(pgSignUp.tooltipCity(), "Please fill out this field.");
        Thread.sleep(1000);

        pgSignUp.typeCity(User.CITY);

        // Assert Zip
        pgSignUp.clickSubmit();
        Assert.assertEquals(pgSignUp.tooltipZip(), "Please fill out this field.");
        Thread.sleep(1000);

        pgSignUp.typeZip(User.ZIP);

        // Assert Mobile Number
        pgSignUp.clickSubmit();
        Assert.assertEquals(pgSignUp.tooltipMobileNumber(), "Please fill out this field.");
        Thread.sleep(1000);

        pgSignUp.typeMobileNumber(User.MOBILE_NUMBER);
    }

    @Test(description = "Verify that the password field does not accept values shorter than 6 characters", enabled = false)
    @Story("Users cannot register with a password having less than 6 characters")
    @Severity(SeverityLevel.BLOCKER)
    public void registerShortPassword() throws InterruptedException {
        preRegistration();

        pgSignUp.clickSignUpButton();

        String tooltip = pgSignUp.tooltipName();
        System.out.println(tooltip);

        String expectedError = "Please fill out this field.";

        Assert.assertTrue(tooltip.contains(expectedError));
        Thread.sleep(1500);
    }

    public void preRegistration() {
        pgSignUp.goToSignupLogin();

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
}

