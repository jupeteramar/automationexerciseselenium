package org.seleniumtests.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.seleniumtests.base.BaseTest;
import org.seleniumtests.pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

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
        String product1name = "Men Tshirt";
        String product1Id = pgProducts.getProductIdByName(product1name);
        userAuthentication();
        pgSignUp.clickSubmit();

        pgProducts.goToProducts();
        pgProducts.clickAddProductToCart(product1Id);
        pgProducts.clickViewCartFromModal();
        Assert.assertTrue(pgCart.isProductInCart(product1Id), "Engk");
        pgCart.clickCheckOutButton();
        Assert.assertTrue(pgCheckout.matchCheckoutDetails("Delivery"), "Mismatch Encountered!");
        pgCheckout.deleteAccount();
    }

    @Test(description = "Verify if the Billing Details match with the registration input")
    @Story("Users can see a valid match from their registration and checkout billing details")
    @Severity(SeverityLevel.BLOCKER)
    public void checkBillingDetails() {
        String product1name = "Men Tshirt";
        String product1Id = pgProducts.getProductIdByName(product1name);
        userAuthentication();
        pgSignUp.clickSubmit();
        pgProducts.goToProducts();
        pgProducts.clickAddProductToCart(product1Id);
        pgProducts.clickViewCartFromModal();
        Assert.assertTrue(pgCart.isProductInCart(product1Id), "Engk");
        pgCart.clickCheckOutButton();
        Assert.assertTrue(pgCheckout.matchCheckoutDetails("Billing"), "Mismatch Encountered!");
        pgCheckout.deleteAccount();
    }

    @Test(description = "Verify that the User can add a comment before checking out the product")
    @Story("User is able to add a comment before checking out the product")
    @Severity(SeverityLevel.BLOCKER)
    public void orderComment() throws InterruptedException {
        String product1name = "Men Tshirt";
        String comment = "This is the comment for this order 12345 QWERTY !@#$%";
        String product1Id = pgProducts.getProductIdByName(product1name);
        userAuthentication();
        pgSignUp.clickSubmit();
        pgProducts.goToProducts();
        pgProducts.clickAddProductToCart(product1Id);
        pgProducts.clickViewCartFromModal();
        softAssert.assertTrue(pgCart.isProductInCart(product1Id), "Product is not inside the Cart");
        pgCart.clickCheckOutButton();
        pgCheckout.typeOrderComment(comment);
        String commentText = pgCheckout.getOrderCommentText(); // Value after input
        pgCheckout.clickPlaceOrder();
        softAssert.assertEquals(commentText, comment, "Expected: "+ comment+" | Actual: " + commentText);
        pgCheckout.deleteAccount();
    }

    @Test(description = "Verify that the User cannot checkout the product if a Name is not entered on the Payment Page")
    @Story("User cannot checkout the product if a Name is not entered on the Payment Page")
    @Severity(SeverityLevel.BLOCKER)
    public void paymentMissingName() throws InterruptedException {
        String product1name = "Men Tshirt";
        String comment = "This is the comment for this order 12345 QWERTY !@#$%";
        String product1Id = pgProducts.getProductIdByName(product1name);
        userAuthentication();
        pgSignUp.clickSubmit();
        pgProducts.goToProducts();
        pgProducts.clickAddProductToCart(product1Id);
        pgProducts.clickViewCartFromModal();
        softAssert.assertTrue(pgCart.isProductInCart(product1Id), "Product ["+product1name+"] is not inside the Cart");
        pgCart.clickCheckOutButton();
        pgCheckout.typeOrderComment(comment);
        String commentText = pgCheckout.getOrderCommentText(); // Value after input
        pgCheckout.clickPlaceOrder();
        softAssert.assertEquals(commentText, comment, "Expected: "+ comment+" | Actual: " + commentText);

        pgCheckout.deleteAccount();
    }
}
