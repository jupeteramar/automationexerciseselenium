package org.seleniumtests.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.seleniumtests.base.BaseTest;
import org.seleniumtests.data.User;
import org.seleniumtests.pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Cart Management")
public class CartTest extends BaseTest {

    @BeforeMethod
    public void setUpPages() {
        pgProducts = new ProductsPage(driver);
        pgSignIn = new SignInPage(driver);
        pgSignUp = new SignUpPage(driver);
        pgCart = new CartPage(driver);
        pgCheckout = new CheckoutPage(driver);
    }

    @Test(description = "Verify that the Modal's View Cart Button directs to the Cart Page")
    @Story("User can view the Cart Page by pressing the button from the modal")
    @Severity(SeverityLevel.BLOCKER)
    public void addProductOpenCartPage() {
        String product1 = "24";

        pgProducts.goToProducts();
        pgProducts.clickAddProductToCart(product1);
        pgProducts.clickViewCartFromModal();
        Assert.assertEquals(pgCart.getCartPageTitle(), "Shopping Cart", "Nope.");
    }


    @Test(description = "Verify the calculations of the cart after adding one product to cart")
    @Story("Users can add a product then view the accurate total and computations")
    @Severity(SeverityLevel.BLOCKER)
    public void checkCartTotalComputationSingleProduct() {
        String product1name = "Men Tshirt";

        String product1Id = pgProducts.getProductIdByName(product1name);

        pgProducts.goToProducts();

        pgProducts.clickAddProductToCart(product1Id);

        pgProducts.clickViewCartFromModal();
        int price = pgCart.getBasePriceOfProductInCart(product1Id);
        int quantity = pgCart.getQuantityOfProductInCart(product1Id);
        int computedTotalPrice = price * quantity;

        Assert.assertEquals(computedTotalPrice, pgCart.getTotalPriceOfProductInCart(product1Id), "Engk");
    }

    @Test(description = "Verify the calculations of the cart after adding multiple product to cart")
    @Story("Users can add a product then view the accurate total and computations")
    @Severity(SeverityLevel.BLOCKER)
    public void checkCartTotalComputationMultipleProduct() {

        String[] products = {
                "Men Tshirt",
                "Madame Top For Women",
                "Green Side Placket Detail T-Shirt"
        };

        String[] productIds = new String[products.length];

        pgProducts.goToProducts();

        for (int i = 0; i < products.length; i++) {
            productIds[i] = pgProducts.getProductIdByName(products[i]);

            pgProducts.clickAddProductToCart(productIds[i]);
            pgProducts.closeAddedToCartSuccessModal();
        }

        pgProducts.goToCart();

        // Assert (validate totals for each product)
        int totalComputed = 0;
        int totalDisplayed = 0;

        for (String productId : productIds) {
            int price = pgCart.getBasePriceOfProductInCart(productId);
            int quantity = pgCart.getQuantityOfProductInCart(productId);
            int expected = price * quantity;

            int actual = pgCart.getTotalPriceOfProductInCart(productId);

            Assert.assertEquals(actual, expected,
                    String.format("❌ Mismatch for %s | Expected: %d | Actual: %d", productId, expected, actual));

            totalComputed += expected;
            totalDisplayed += actual;
        }

        Assert.assertEquals(totalDisplayed, totalComputed, "❌ Cart total mismatch!");
    }

    @Test(description = "Verify updating the quantity of a product in cart")
    @Story("Users can modify the quantity of the added product from Products Page")
    @Severity(SeverityLevel.BLOCKER)
    public void updateQuantityInCartPage() {
        String product1name = "Men Tshirt";

        String product1Id = pgProducts.getProductIdByName(product1name);

        pgProducts.goToProducts();

        pgProducts.clickAddProductToCart(product1Id);

        pgProducts.clickViewCartFromModal();

        Assert.assertFalse(pgCart.isButtonDisabled(pgCart.getQuantityButton(product1Id)), "❌ Quantity Button is Disabled!");

        pgCart.clickQuantityButton(pgCart.getQuantityButton(product1Id));
    }

    @Test(description = "Verify if the checkout button is not displayed when cart is empty")
    @Story("User cannot checkout the cart if it is empty")
    @Severity(SeverityLevel.BLOCKER)
    public void emptyCart() {
        pgCart.goToCart();

        Assert.assertTrue(pgCart.isCartEmpty());
        Assert.assertTrue(pgCart.isCheckOutNotDisplayed());
    }

    @Test(description = "Verify if the checkout button is not displayed when cart is empty for newly registered user")
    @Story("Registered User cannot checkout the cart if it is empty")
    @Severity(SeverityLevel.BLOCKER)
    public void emptyCartRegisteredUser() {
        userAuthentication();
        pgSignUp.clickSubmit();
        pgCart.goToCart();

        Assert.assertTrue(pgCart.isCartEmpty());
        Assert.assertTrue(pgCart.isCheckOutNotDisplayed());
        pgCart.deleteAccount();
    }

    @Test(description = "Verify if the checkout button is not displayed when cart is empty for a registered user")
    @Story("Registered User can delete products to cart")
    @Severity(SeverityLevel.BLOCKER)
    public void deleteProductFromCart() {
        String product1 = "24";
        String email = "qa@tester.com";
        String password = "12345";

        userAuthentication();
        pgSignUp.clickSubmit();

        pgProducts.goToProducts();

        pgProducts.clickAddProductToCart(product1);
        pgProducts.clickViewCartFromModal();
        pgCart.deleteProductFromCart(product1);
        pgSignUp.deleteAccount();
    }

    @Test(description = "Verify that the products remain in the cart even after logging out")
    @Story("Users can see the added product in the cart even after logging out")
    @Severity(SeverityLevel.BLOCKER)
    public void checkProductInCartAfterSignOut() {
        String product1name = "Men Tshirt";

        String product1Id = pgProducts.getProductIdByName(product1name);
        userAuthentication();
        pgSignUp.clickSubmit();
        pgProducts.goToProducts();

        pgProducts.clickAddProductToCart(product1Id);

        pgProducts.clickViewCartFromModal();

        Assert.assertTrue(pgCart.isProductInCart(product1Id), "Engk");

        pgCart.logOut();

        pgSignIn.typeSignInCredentials(User.EMAIL, User.PASSWORD);
        pgSignIn.clickLogInButton();

        pgCart.goToCart();

        boolean isProductInCart = pgCart.isProductInCart(product1Id);
        pgCart.deleteAccount();
        Assert.assertTrue(isProductInCart, "Product is not in cart");
    }




}
