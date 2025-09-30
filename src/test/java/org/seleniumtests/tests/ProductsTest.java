package org.seleniumtests.tests;

import io.qameta.allure.*;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.seleniumtests.pages.ProductViewPage;
import org.seleniumtests.utils.ClickUtils;
import org.testng.Assert;
import org.seleniumtests.base.BaseTest;
import org.seleniumtests.pages.ProductsPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

@Epic("Product Selection")
public class ProductsTest extends BaseTest {

    private ProductsPage pgProducts;
    private ProductViewPage pgProductView;

    @BeforeMethod
    public void setUpPages() {
        pgProducts = new ProductsPage(driver);
        pgProductView = new ProductViewPage(driver);
    }

    @Test(description = "Verify add one product to cart successfully", enabled = true)
    @Story("User can add a product to cart from products page successfully")
    @Severity(SeverityLevel.BLOCKER)
    public void addProductToCartFromProductsPage() {


        String product1 = "24";

        pgProducts.goToProducts();
        pgProducts.clickAddProductToCart(product1);

        Assert.assertTrue(pgProducts.isCartModalDisplayed(), "Cart modal should be displayed.");
    }

    @Test(description = "Verify add multiple products to cart successfully", enabled = true)
    @Story("User can add multiple products to cart from products page successfully")
    @Severity(SeverityLevel.BLOCKER)
    public void addMultipleProductsToCartFromProductsPage() {
        String[] products = {"1", "24", "43"};

        pgProducts.goToProducts();
        pgProducts.clickAddProductToCart(products[0]);
        Assert.assertTrue(pgProducts.isCartModalDisplayed(), "Cart modal should be displayed.");
        pgProducts.closeAddedToCartSuccessModal();
        pgProducts.clickAddProductToCart(products[1]);
        Assert.assertTrue(pgProducts.isCartModalDisplayed(), "Cart modal should be displayed.");
        pgProducts.closeAddedToCartSuccessModal();
        pgProducts.clickAddProductToCart(products[2]);


        //Assert.assertTrue(pgProducts.isCartModalDisplayed(), "Cart modal should be displayed.");
    }

    @Test(description = "Verify if users can view the details of the chosen product", enabled = true)
    @Story("Users can view the information about the product")
    @Severity(SeverityLevel.CRITICAL)
    public void viewProductDetails() throws InterruptedException {
        String productID = "1";

        String productCardName = pgProducts.getProductName(productID);
        String productCardPrice = pgProducts.getProductPrice(productID);

        pgProducts.clickViewProductButton(productID);

        String productViewName = pgProductView.getProductName();
        String productViewPrice = pgProductView.getProductPrice();

        Assert.assertEquals(productCardName, productViewName, "Product Name from Card does not match with View: Card (" + productCardName + ") View(" + productViewName + ")");
        Assert.assertEquals(productCardPrice, productViewPrice, "Product Price from Card does not match with View: Card (" + productCardPrice + ") View(" + productViewPrice + ")");
    }

    @Test(description = "Verify that products displayed under Women > Dress match the selected category")
    @Story("User is able to browse and add products to cart")
    @Severity(SeverityLevel.CRITICAL)
    public void filterWomenDress() throws InterruptedException {
        pgProducts.filterWomenDress();
        String category = "Women > Dress";
        List<WebElement> products = driver.findElements(By.cssSelector("div.col-sm-4"));
        viewMultipleProductsSimultaneously(products, category);
    }

    @Test(description = "Verify that products displayed under Women > Tops match the selected category")
    @Story("User is able to browse and add products to cart")
    @Severity(SeverityLevel.CRITICAL)
    public void filterWomenTops() throws InterruptedException {
        pgProducts.filterWomenTops();
        String category = "Women > Tops";
        List<WebElement> products = driver.findElements(By.cssSelector("div.col-sm-4"));
        viewMultipleProductsSimultaneously(products, category);
    }

    @Test(description = "Verify that products displayed under Women > Saree match the selected category")
    @Story("User is able to browse and add products to cart")
    @Severity(SeverityLevel.CRITICAL)
    public void filterWomenSaree() throws InterruptedException {
        pgProducts.filterWomenSaree();
        String category = "Women > Saree";
        List<WebElement> products = driver.findElements(By.cssSelector("div.col-sm-4"));
        viewMultipleProductsSimultaneously(products, category);
    }

    @Test(description = "Verify that products displayed under Men > Tshirt match the selected category")
    @Story("User is able to browse and add products to cart")
    @Severity(SeverityLevel.CRITICAL)
    public void filterMenTshirt() throws InterruptedException {
        pgProducts.filterMenTshirt();
        String category = "Men > Tshirt";
        List<WebElement> products = driver.findElements(By.cssSelector("div.col-sm-4"));
        viewMultipleProductsSimultaneously(products, category);
    }

    @Test(description = "Verify that products displayed under Men > Jeans match the selected category")
    @Story("User is able to browse and add products to cart")
    @Severity(SeverityLevel.CRITICAL)
    public void filterMenJeans() throws InterruptedException {
        pgProducts.filterMenJeans();
        String category = "Men > Jeans";
        List<WebElement> products = driver.findElements(By.cssSelector("div.col-sm-4"));
        viewMultipleProductsSimultaneously(products, category);
    }

    @Test(description = "Verify that products displayed under Kids > Jeans match the selected category", enabled = false)
    @Story("User is able to browse and add products to cart")
    @Severity(SeverityLevel.CRITICAL)
    public void filterKidsJeans() throws InterruptedException {
        //pgProducts.filterKidsJeans();
        String category = "Kids > Jeans";
        List<WebElement> products = driver.findElements(By.cssSelector("div.col-sm-4"));
        viewMultipleProductsSimultaneously(products, category);
    }

    @Test(description = "Verify that products displayed under Kids > Dress match the selected category", enabled = false)
    @Story("User is able to browse and add products to cart")
    @Severity(SeverityLevel.CRITICAL)
    public void filterKidsDress() throws InterruptedException {
        //pgProducts.filterKidsDress();
        String category = "Kids > Dress";
        List<WebElement> products = driver.findElements(By.cssSelector("div.col-sm-4"));
        viewMultipleProductsSimultaneously(products, category);
    }

    @Test(description = "Verify that products displayed under Kids > Tops & Shirts match the selected category", enabled = false)
    @Story("User is able to browse and add products to cart")
    @Severity(SeverityLevel.CRITICAL)
    public void filterKidsTopsShirts() throws InterruptedException {
        //pgProducts.filterKidsTopsShirts();
        String category = "Kids > Tops & Shirts";
        List<WebElement> products = driver.findElements(By.cssSelector("div.col-sm-4"));
        viewMultipleProductsSimultaneously(products, category);
    }


    // -------------------------------------------------

    public void viewMultipleProductsSimultaneously(List<WebElement> products, String category){
        for (int i = 0; i < products.size(); i++) {
            // Re-locate products every loop because the page reloads
            products = driver.findElements(By.cssSelector("div.product-image-wrapper"));
            WebElement product = products.get(i);

            WebElement viewProductButton = product.findElement(By.cssSelector("div.choose a"));

            // Click view product button
            ClickUtils.safeClickElement(driver, viewProductButton, 10);

            String productName = pgProductView.getProductName();
            System.out.println("Opened: " + productName);

            Assert.assertTrue(pgProductView.isCategoryMatched(category));
            driver.navigate().back();
        }
    }
}
