package org.seleniumtests.tests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.seleniumtests.base.BaseTest;
import org.seleniumtests.pages.SignInPage;
import org.seleniumtests.pages.SignUpPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DeleteAccountTest extends BaseTest {

    @BeforeMethod
    public void setUp(){
        pgSignIn = new SignInPage(driver);
        pgSignUp = new SignUpPage(driver);
    }

    @Test(description = "Verify that the Logout Button is working as intended")
    @Story("Users may log out their registered account from the system using the log out button")
    @Severity(SeverityLevel.BLOCKER)
    public void signOut() throws InterruptedException {
        userAuthentication();
        pgSignUp.clickSubmit();
        pgSignUp.clickContinueButton();

        pgSignUp.logOut();

        cleanTestUser();
    }
}
