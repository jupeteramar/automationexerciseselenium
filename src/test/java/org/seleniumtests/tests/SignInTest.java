package org.seleniumtests.tests;

import io.qameta.allure.*;
import org.seleniumtests.base.BaseTest;
import org.seleniumtests.pages.ProductsPage;
import org.seleniumtests.pages.SignInPage;
import org.testng.annotations.*;

import org.testng.annotations.Test;
import org.testng.Assert;


@Epic("Authentication")
@Feature("Sign In")
public class SignInTest extends BaseTest {

    private SignInPage pgSignIn;
    private ProductsPage pgProducts;

    @BeforeMethod
    public void setUpPages() {
        pgProducts = new ProductsPage(driver);
        pgSignIn = new SignInPage(driver);
    }

    @Test(description = "Verify a successful sign in with valid credentials")
    @Story("Users can sign in if the user credentials are correct")
    @Severity(SeverityLevel.BLOCKER)
    public void signInValid() {
        String email = "qa@tester.com";
        String password = "12345";

        pgSignIn.goToSignupLogin();

        pgSignIn.typeSignInCredentials(email, password);
        pgSignIn.clickLogInButton();

        Assert.assertTrue(pgSignIn.isUserNameDisplayed());
    }

    @Test(description = "Verify an unsuccessful sign in with invalid credentials - wrong password")
    @Story("Users cannot sign in if the user inputs an incorrect password")
    @Severity(SeverityLevel.BLOCKER)
    public void signInInvalidPassword() throws InterruptedException {
        String email = "qa@tester.com";
        String password = "00000";

        pgSignIn.goToSignupLogin();

        pgSignIn.typeSignInCredentials(email, password);
        pgSignIn.clickLogInButton();
        Thread.sleep(3000);
        Assert.assertTrue(pgSignIn.isErrorMessageVisible());
    }

    @Test(description = "Verify an unsuccessful sign in with invalid credentials - wrong email")
    @Story("Users cannot sign in if the user inputs an incorrect email")
    @Severity(SeverityLevel.BLOCKER)
    public void signInInvalidEmail() throws InterruptedException {
        String email = "qa@tester.comwrongemail";
        String password = "12345";

        pgSignIn.goToSignupLogin();

        pgSignIn.typeSignInCredentials(email, password);
        pgSignIn.clickLogInButton();

        Assert.assertTrue(pgSignIn.isErrorMessageVisible());
        Thread.sleep(3000);
    }
}
