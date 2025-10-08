package org.seleniumtests.tests;

import io.qameta.allure.*;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.seleniumtests.pages.ProductViewPage;
import org.seleniumtests.pages.SignInPage;
import org.seleniumtests.pages.SignUpPage;
import org.seleniumtests.utils.ClickUtils;

import org.testng.Assert;
import org.seleniumtests.base.BaseTest;
import org.seleniumtests.pages.ProductsPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

@Epic("Product Selection")
public class ProductsTest extends BaseTest {

    @BeforeMethod
    public void setUpPages() {
        pgProducts = new ProductsPage(driver);
        pgProductView = new ProductViewPage(driver);
        pgSignIn = new SignInPage(driver);
        pgSignUp = new SignUpPage(driver);
    }

    @Test(description = "Verify add one product to cart successfully")
    @Story("User can add a product to cart from products page successfully")
    @Severity(SeverityLevel.BLOCKER)
    public void addProductToCartFromProductsPage() {
        String product1 = "24";

        pgProducts.goToProducts();
        pgProducts.clickAddProductToCart(product1);

        Assert.assertTrue(pgProducts.isCartModalDisplayed(), "Cart modal should be displayed.");
    }

    @Test(description = "Verify add multiple products to cart successfully")
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

    @Test(description = "Verify if users can view the details of the chosen product")
    @Story("Users can view the information about the product")
    @Severity(SeverityLevel.CRITICAL)
    public void viewProductDetails() {
        String productID = "1";

        String productCardName = pgProducts.getProductName(productID);
        String productCardPrice = pgProducts.getProductPrice(productID);

        pgProducts.clickViewProductButton(productID);

        String productViewName = pgProductView.getProductName();
        String productViewPrice = pgProductView.getProductPrice();

        Assert.assertEquals(productCardName, productViewName, "Product Name from Card does not match with View: Card (" + productCardName + ") View(" + productViewName + ")");
        Assert.assertEquals(productCardPrice, productViewPrice, "Product Price from Card does not match with View: Card (" + productCardPrice + ") View(" + productViewPrice + ")");
    }

    @Test(description = "Verify if registered users can view the details of the chosen product")
    @Story("Users can view the information about the product")
    @Severity(SeverityLevel.CRITICAL)
    public void viewProductDetailsRegisteredUser() {
        String productID = "1";

        userAuthentication();
        pgSignUp.clickSubmit();

        pgProducts.goToProducts();
        String productCardName = pgProducts.getProductName(productID);
        String productCardPrice = pgProducts.getProductPrice(productID);

        pgProducts.clickViewProductButton(productID);

        String productViewName = pgProductView.getProductName();
        String productViewPrice = pgProductView.getProductPrice();

        Assert.assertEquals(productCardName, productViewName, "Product Name from Card does not match with View: Card (" + productCardName + ") View(" + productViewName + ")");
        Assert.assertEquals(productCardPrice, productViewPrice, "Product Price from Card does not match with View: Card (" + productCardPrice + ") View(" + productViewPrice + ")");
        pgSignIn.deleteAccount();
    }

    @Test(description = "Verify that products displayed under Women > Dress match the selected category")
    @Story("User is able to browse and add products to cart")
    @Severity(SeverityLevel.CRITICAL)
    public void filterWomenDress() {
        pgProducts.filterWomenDress();
        String category = "Women > Dress";
        List<WebElement> products = driver.findElements(By.cssSelector("div.col-sm-4"));
        viewMultipleProductsSimultaneously(products, category);
    }

    @Test(description = "Verify that products displayed under Women > Tops match the selected category")
    @Story("User is able to browse and add products to cart")
    @Severity(SeverityLevel.CRITICAL)
    public void filterWomenTops() {
        pgProducts.filterWomenTops();
        String category = "Women > Tops";
        List<WebElement> products = driver.findElements(By.cssSelector("div.col-sm-4"));
        viewMultipleProductsSimultaneously(products, category);
    }

    @Test(description = "Verify that products displayed under Women > Saree match the selected category")
    @Story("User is able to browse and add products to cart")
    @Severity(SeverityLevel.CRITICAL)
    public void filterWomenSaree() {
        pgProducts.filterWomenSaree();
        String category = "Women > Saree";
        List<WebElement> products = driver.findElements(By.cssSelector("div.col-sm-4"));
        viewMultipleProductsSimultaneously(products, category);
    }

    @Test(description = "Verify that products displayed under Men > Tshirt match the selected category")
    @Story("User is able to browse and add products to cart")
    @Severity(SeverityLevel.CRITICAL)
    public void filterMenTshirt() {
        pgProducts.filterMenTshirt();
        String category = "Men > Tshirt";
        List<WebElement> products = driver.findElements(By.cssSelector("div.col-sm-4"));
        viewMultipleProductsSimultaneously(products, category);
    }

    @Test(description = "Verify that products displayed under Men > Jeans match the selected category")
    @Story("User is able to browse and add products to cart")
    @Severity(SeverityLevel.CRITICAL)
    public void filterMenJeans() {
        pgProducts.filterMenJeans();
        String category = "Men > Jeans";
        List<WebElement> products = driver.findElements(By.cssSelector("div.col-sm-4"));
        viewMultipleProductsSimultaneously(products, category);
    }

    @Test(description = "Verify that products displayed under Kids > Dress match the selected category")
    @Story("User is able to browse and add products to cart")
    @Severity(SeverityLevel.CRITICAL)
    public void filterKidsDress() {
        pgProducts.filterKidsDress();
        String category = "Kids > Dress";
        List<WebElement> products = driver.findElements(By.cssSelector("div.col-sm-4"));
        viewMultipleProductsSimultaneously(products, category);
    }

    @Test(description = "Verify that products displayed under Kids > Tops & Shirts match the selected category")
    @Story("User is able to browse and add products to cart")
    @Severity(SeverityLevel.CRITICAL)
    public void filterKidsTopsShirts() {
        pgProducts.filterKidsTopsShirts();
        String category = "Kids > Tops & Shirts";
        List<WebElement> products = driver.findElements(By.cssSelector("div.col-sm-4"));
        viewMultipleProductsSimultaneously(products, category);
    }

    @Test(description = "Verify Brand Filtering – Polo Product Listing Accuracy")
    @Story("User is able to browse and add products to cart")
    @Severity(SeverityLevel.CRITICAL)
    public void filterBrandPolo() {
        pgProducts.filterPolo();
        String brand = "Polo";
        List<WebElement> products = driver.findElements(By.cssSelector("div.col-sm-4"));
        viewMultipleProductsSimultaneouslyBrand(products, brand);
    }

    @Test(description = "Verify Brand Filtering – H&M Product Listing Accuracy")
    @Story("User is able to browse and add products to cart")
    @Severity(SeverityLevel.CRITICAL)
    public void filterBrandHnM() {
        pgProducts.filterHnM();
        String brand = "H&M";
        List<WebElement> products = driver.findElements(By.cssSelector("div.col-sm-4"));
        viewMultipleProductsSimultaneouslyBrand(products, brand);
    }

    @Test(description = "Verify Brand Filtering – Madame Product Listing Accuracy")
    @Story("User is able to browse and add products to cart")
    @Severity(SeverityLevel.CRITICAL)
    public void filterBrandMadame() {
        pgProducts.filterMadame();
        String brand = "Madame";
        List<WebElement> products = driver.findElements(By.cssSelector("div.col-sm-4"));
        viewMultipleProductsSimultaneouslyBrand(products, brand);
    }

    @Test(description = "Verify Brand Filtering – Mast & Harbour Product Listing Accuracy")
    @Story("User is able to browse and add products to cart")
    @Severity(SeverityLevel.CRITICAL)
    public void filterBrandMastHarbour() {
        pgProducts.filterMastHarbour();
        String brand = "Mast & Harbour";
        List<WebElement> products = driver.findElements(By.cssSelector("div.col-sm-4"));
        viewMultipleProductsSimultaneouslyBrand(products, brand);
    }

    @Test(description = "Verify Brand Filtering – Babyhug Product Listing Accuracy")
    @Story("User is able to browse and add products to cart")
    @Severity(SeverityLevel.CRITICAL)
    public void filterBrandBabyhug() {
        pgProducts.filterBabyhug();
        String brand = "Babyhug";
        List<WebElement> products = driver.findElements(By.cssSelector("div.col-sm-4"));
        viewMultipleProductsSimultaneouslyBrand(products, brand);
    }

    @Test(description = "Verify Brand Filtering – Allen Solly Junior Product Listing Accuracy")
    @Story("User is able to browse and add products to cart")
    @Severity(SeverityLevel.CRITICAL)
    public void filterBrandAllenSollyJunior() {
        pgProducts.filterAllenSollyJunior();
        String brand = "Allen Solly Junior";
        List<WebElement> products = driver.findElements(By.cssSelector("div.col-sm-4"));
        viewMultipleProductsSimultaneouslyBrand(products, brand);
    }

    @Test(description = "Verify Brand Filtering – Kookie Kids Product Listing Accuracy")
    @Story("User is able to browse and add products to cart")
    @Severity(SeverityLevel.CRITICAL)
    public void filterBrandKookieKids() {
        pgProducts.filterKookieKids();
        String brand = "Kookie Kids";
        List<WebElement> products = driver.findElements(By.cssSelector("div.col-sm-4"));
        viewMultipleProductsSimultaneouslyBrand(products, brand);
    }

    @Test(description = "Verify Brand Filtering – Biba Product Listing Accuracy")
    @Story("User is able to browse and add products to cart")
    @Severity(SeverityLevel.CRITICAL)
    public void filterBrandBiba() {
        pgProducts.filterBiba();
        String brand = "Biba";
        List<WebElement> products = driver.findElements(By.cssSelector("div.col-sm-4"));
        viewMultipleProductsSimultaneouslyBrand(products, brand);
    }


    // -------------------------------------------------

    public void viewMultipleProductsSimultaneously(List<WebElement> products, String category) {
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
            System.out.println("Expected: " + category + " | Actual: " + pgProductView.getCategory() + "\n");
            driver.navigate().back();
        }
    }

    public void viewMultipleProductsSimultaneouslyBrand(List<WebElement> products, String brand) {
        for (int i = 0; i < products.size(); i++) {
            // Re-locate products every loop because the page reloads
            products = driver.findElements(By.cssSelector("div.product-image-wrapper"));
            WebElement product = products.get(i);

            WebElement viewProductButton = product.findElement(By.cssSelector("div.choose a"));

            // Click view product button
            ClickUtils.safeClickElement(driver, viewProductButton, 10);

            String productName = pgProductView.getProductName();
            System.out.println("Opened: " + productName);

            Assert.assertTrue(pgProductView.isBrandMatched(brand));
            System.out.println("Expected: " + brand + " | Actual: " + pgProductView.getBrandName() + "\n");
            driver.navigate().back();
        }
    }
}
