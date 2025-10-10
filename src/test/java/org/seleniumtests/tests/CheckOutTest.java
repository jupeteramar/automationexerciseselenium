package org.seleniumtests.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.seleniumtests.base.BaseTest;
import org.seleniumtests.data.User;
import org.seleniumtests.pages.*;
import org.seleniumtests.utils.FileUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Epic("Checkout")
public class CheckOutTest extends BaseTest {
    private SoftAssert softAssert;

    @BeforeMethod
    public void setUpPages() {
        pgProducts = new ProductsPage(driver);
        pgCart = new CartPage(driver);
        pgCheckout = new CheckoutPage(driver);
        pgSignUp = new SignUpPage(driver);
        pgSignIn = new SignInPage(driver);

        softAssert = new SoftAssert();
    }

    @Test(description = "Verify that user needs to log in before checking out")
    @Story("Users cannot checkout if they are not logged in")
    @Severity(SeverityLevel.CRITICAL)
    public void updateQuantityInCartPage() {
        String product1name = "Men Tshirt";
        String product1Id = pgProducts.getProductIdByName(product1name);
        pgProducts.goToProducts();
        pgProducts.clickAddProductToCart(product1Id);
        pgProducts.clickViewCartFromModal();
        pgCart.clickCheckOutButton();
        Assert.assertFalse(pgCart.isElementDisplayed(pgCart.getMdlCheckoutModal()));
    }

    @Test(description = "Verify if the Checkout Details match with the registration input")
    @Story("Users can see a valid match from their registration and checkout delivery details")
    @Severity(SeverityLevel.BLOCKER)
    public void checkDeliveryDetails() {
        register_addProductToCart_Checkout();
        softAssert.assertTrue(pgCheckout.matchCheckoutDetails("Delivery"), "Mismatch Encountered!");
        pgCheckout.deleteAccount();
        softAssert.assertAll();
    }

    @Test(description = "Verify if the Billing Details match with the registration input")
    @Story("Users can see a valid match from their registration and checkout billing details")
    @Severity(SeverityLevel.BLOCKER)
    public void checkBillingDetails() {
        register_addProductToCart_Checkout();
        softAssert.assertTrue(pgCheckout.matchCheckoutDetails("Billing"), "Mismatch Encountered!");
        pgCheckout.deleteAccount();
        softAssert.assertAll();
    }

    @Test(description = "Verify that the User can add a comment before checking out the product")
    @Story("User is able to add a comment before checking out the product")
    @Severity(SeverityLevel.BLOCKER)
    public void orderComment() throws InterruptedException {
        String comment = "This is the comment for this order 12345 QWERTY !@#$%";
        register_addProductToCart_Checkout();
        pgCheckout.typeOrderComment(comment);
        String commentText = pgCheckout.getOrderCommentText(); // Value after input
        pgCheckout.clickPlaceOrder();
        softAssert.assertEquals(commentText, comment, "Expected: " + comment + " | Actual: " + commentText);
        pgCheckout.deleteAccount();
        softAssert.assertAll();
    }

    @Test(description = "Verify that the User cannot checkout the product if a Name is not entered on the Payment Page")
    @Story("User cannot checkout the product if a Name is not entered on the Payment Page")
    @Severity(SeverityLevel.BLOCKER)
    public void paymentMissingName() throws InterruptedException {
        register_addProductToCart_CheckOut_Pay();
        softAssert.assertTrue(pgCheckout.tooltipName());
        pgCheckout.deleteAccount();
        softAssert.assertAll();
    }

    @Test(description = "Verify that the User cannot checkout the product if a CVC is not entered on the Payment Page")
    @Story("User cannot checkout the product if a CVC is not entered on the Payment Page")
    @Severity(SeverityLevel.BLOCKER)
    public void paymentMissingCVC() throws InterruptedException {
        register_addProductToCart_CheckOut_Pay();
        typeAllCardDetails();
        pgCheckout.clearCVC();
        softAssert.assertTrue(pgCheckout.tooltipCVC());
        pgCheckout.deleteAccount();
        softAssert.assertAll();
    }

    @Test(description = "Verify that the User cannot checkout the product if a Card Number is not entered on the Payment Page")
    @Story("User cannot checkout the product if a CardNumber is not entered on the Payment Page")
    @Severity(SeverityLevel.BLOCKER)
    public void paymentMissingCardNumber() throws InterruptedException {
        register_addProductToCart_CheckOut_Pay();
        typeAllCardDetails();
        pgCheckout.clearCardNumber();
        softAssert.assertTrue(pgCheckout.tooltipCVC());
        pgCheckout.deleteAccount();
        softAssert.assertAll();
    }

    @Test(description = "Verify that the User cannot checkout the product if Card Month Expiry is not entered on the Payment Page")
    @Story("User cannot checkout the product if a CardNumber is not entered on the Payment Page")
    @Severity(SeverityLevel.BLOCKER)
    public void paymentMissingExpiryMonth() throws InterruptedException {
        register_addProductToCart_CheckOut_Pay();
        typeAllCardDetails();
        pgCheckout.clearExpiryMonth();
        softAssert.assertTrue(pgCheckout.tooltipExpiryMonth(), "CVC");
        pgCheckout.deleteAccount();
        softAssert.assertAll();
    }

    @Test(description = "Verify that the User cannot checkout the product if Card Year Expiry is not entered on the Payment Page")
    @Story("User cannot checkout the product if a CardNumber is not entered on the Payment Page")
    @Severity(SeverityLevel.BLOCKER)
    public void paymentMissingExpiryYear() throws InterruptedException {
        register_addProductToCart_CheckOut_Pay();
        typeAllCardDetails();
        pgCheckout.clearExpiryYear();
        softAssert.assertTrue(pgCheckout.tooltipExpiryYear());
        pgCheckout.deleteAccount();
        softAssert.assertAll();
    }

    @Test(description = "Verify that the User cannot checkout the product if special characters are inserted in text fields")
    @Story("User cannot checkout the product if special characters are inserted in text fields")
    @Severity(SeverityLevel.BLOCKER)
    public void checkoutInvalidCardName() throws InterruptedException {
        register_addProductToCart_CheckOut_Pay();
        typeAllCardDetails();
        pgCheckout.clearName();
        pgCheckout.typeName("Glen S+urg15");
        String nameValue = pgCheckout.getName();
        softAssert.assertTrue(isLettersOnly(nameValue), "Name on Card [" + nameValue + "] contains numerical/special characters");
        pgCheckout.deleteAccount();
        softAssert.assertAll();
    }

    @Test(description = "Verify that the User cannot checkout if the credit card is expired")
    @Story("User cannot checkout if the credit card is expired")
    @Severity(SeverityLevel.BLOCKER)
    public void checkoutExpiredCard() throws InterruptedException {
        int currentYear = LocalDate.now().getYear();
        System.out.println(currentYear);
        int cardYear = 2000;
        boolean isCardExpired = currentYear >= cardYear;
        register_addProductToCart_CheckOut_Pay();
        typeAllCardDetails();
        pgCheckout.clearExpiryYear();
        pgCheckout.typeExpiryYear(Integer.toString(cardYear));
        pgCheckout.clickPayButton();
        softAssert.assertFalse(pgCheckout.SuccessfulOrder(), "Expired card is accepted [" + cardYear + "]");
        pgCheckout.deleteAccount();
        softAssert.assertAll();
    }

    @Test(description = "Verify that the User can download the invoice")
    @Story("User can download the invoice")
    @Severity(SeverityLevel.BLOCKER)
    public void checkOutDownloadInvoice() throws InterruptedException, IOException {
        // --- Setup download directory ---
        String downloadPath = System.getProperty("user.home") + "/Downloads"; // OS default folder
        File downloadDir = new File(downloadPath);
        if (!downloadDir.exists()) downloadDir.mkdirs();

        // --- Count existing files to detect new one ---
        int initialFileCount = Objects.requireNonNull(downloadDir.listFiles()).length;

        // --- Perform checkout flow ---
        register_addProductToCart_CheckOut_Pay();
        typeAllCardDetails();
        pgCheckout.clickPayButton();
        pgCheckout.clickDownloadInvoice(); // triggers invoice download

        // --- Wait for the invoice file to appear ---
        File latestFile = FileUtils.waitForNewFile(downloadDir, initialFileCount, 15);
        Assert.assertNotNull(latestFile, "Invoice file should be downloaded");

        // --- Rename downloaded file to avoid overwrite in future runs ---
        File renamedFile = FileUtils.renameDownloadedFile(latestFile, "invoice_" + System.currentTimeMillis() + ".txt");
        System.out.println("Renamed invoice: " + renamedFile.getAbsolutePath());

        // --- Read and verify invoice content ---
        String content = new String(Files.readAllBytes(renamedFile.toPath())).trim();
        System.out.println("Invoice Content: " + content);

        Assert.assertTrue(content.contains("Hi " + User.NAME),
                "Invoice should contain the logged-in user name: " + User.NAME);
        Assert.assertTrue(content.toLowerCase().contains("thank you"),
                "Invoice should end with a 'Thank you' message");

        pgCheckout.deleteAccount();
        softAssert.assertAll();
    }



    // -------------------------------------------
    public void typeAllCardDetails() {
        pgCheckout.typeName(User.NAME);
        pgCheckout.typeCardNumber(User.CARD_NUMBER);
        pgCheckout.typeCVC(User.CARD_CVC);
        pgCheckout.typeExpiryMonth(User.CARD_EXPIRY_MONTH);
        pgCheckout.typeExpiryYear(User.CARD_EXPIRY_YEAR);
    }

    public void register_addProductToCart_Checkout() {
        String product1name = "Men Tshirt";
        String product1Id = pgProducts.getProductIdByName(product1name);
        userAuthentication();
        pgSignUp.clickSubmit();
        pgProducts.goToProducts();
        pgProducts.clickAddProductToCart(product1Id);
        pgProducts.clickViewCartFromModal();
        softAssert.assertTrue(pgCart.isProductInCart(product1Id), "Product [" + product1name + "] is not inside the Cart");
        pgCart.clickCheckOutButton();
    }

    public void register_addProductToCart_CheckOut_Pay() {
        register_addProductToCart_Checkout();
        pgCheckout.clickPlaceOrder();
        pgCheckout.clickPayButton();
    }

    public boolean isLettersOnly(String input) {
        return input != null && input.matches("[A-Za-z ]+");
    }
}
