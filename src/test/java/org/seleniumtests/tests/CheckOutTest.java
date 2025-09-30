package org.seleniumtests.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.seleniumtests.base.BaseTest;
import org.seleniumtests.pages.CartPage;
import org.seleniumtests.pages.ProductsPage;
import org.seleniumtests.utils.WaitUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Checkout")
public class CheckOutTest extends BaseTest {

    private ProductsPage pgProducts;
    private CartPage pgCart;

    @BeforeMethod
    public void setUpPages() {
        pgProducts = new ProductsPage(driver);
        pgCart = new CartPage(driver);
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
}
