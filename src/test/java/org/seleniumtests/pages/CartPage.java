package org.seleniumtests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.seleniumtests.utils.ClickUtils;
import org.testng.Assert;

import java.util.List;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    private final By lblEmptyCart = By.id("empty_cart");

    private final By lblCartPageTitle = new By.ByCssSelector("div.breadcrumbs ol.breadcrumb li.active");
    private final By btnCheckOut = By.xpath("//section[@id='do_action']//a[@class='btn btn-default check_out']");
    private final By btnDeleteProduct = By.xpath("//tc[@class='cart_delete']//a");
    private final By mdlCheckoutModal = By.id("checkoutModal");

    public boolean isCartEmpty() {
        return driver.findElement(lblEmptyCart).isDisplayed();
    }

    public boolean isCheckOutNotDisplayed() {
        List<WebElement> checkOutButton = driver.findElements(btnCheckOut);
        return checkOutButton.isEmpty();
    }

    public String getCartPageTitle() {
        return driver.findElement(lblCartPageTitle).getText().trim();
    }

    public void deleteProductFromCart(String productId) {
        ClickUtils.safeClick(driver, By.xpath("//td[@class='cart_delete']//a[@data-product-id='" + productId + "']"), 5);
    }

    public String getCartProductName(String productId) {
        return driver.findElement(By.cssSelector("tr[id='product-" + productId + "'] td.cart_description h4 a[href='/product_details/" + productId + "']")).getText().trim();
    }

    public int getNumberOfItemsInCart() {
        return driver.findElements(By.xpath("//table[@id='cart_info_table']/tbody/tr")).size();
    }

    public By getMdlCheckoutModal() {
        return mdlCheckoutModal;
    }

    public int getBasePriceOfProductInCart(String productId) {
        // Step 1: Extract the text (e.g. "Rs. 400")
        String priceText = driver.findElement(
                By.xpath("//tr[@id='product-" + productId + "']//td[@class='cart_price']/p")
        ).getText().trim();

        // Step 2: Remove "Rs." and whitespace, keep only numbers
        String numericPrice = priceText.replace("Rs.", "").trim();

        // Step 3: Return cleaned value
        return Integer.parseInt(numericPrice);
    }

    public boolean isProductInCart(String productId) {
        List<WebElement> productInCart = driver.findElements(By.id("product-" + productId));
        return !productInCart.isEmpty();
    }

    public String getProductText(String productId) {
        return driver.findElement(By.xpath("//tr[@id='product-" + productId + "']//td[@class='cart_description']//h4//a")).getText().trim();
    }

    public int getQuantityOfProductInCart(String productId) {
        String quantityText = driver.findElement(
                By.xpath("//tr[@id='product-" + productId + "']//td[@class='cart_quantity']/button")
        ).getText().trim();

        return Integer.parseInt(quantityText);
    }

    public int getTotalPriceOfProductInCart(String productId) {
        String totalPriceText = driver.findElement(
                By.xpath("//tr[@id='product-" + productId + "']//td[@class='cart_total']/p")
        ).getText().trim();

        String totalPrice = totalPriceText.replace("Rs.", "").trim();

        return Integer.parseInt(totalPrice);
    }

    public By getQuantityButton(String productId) {
        return By.xpath("//tr[@id='product-" + productId + "']/td[@class='cart_quantity']/button");

    }

    public void clickQuantityButton(By locator) {
        driver.findElement(locator).click();
    }

    public void clickCheckOutButton() {
        driver.findElement(btnCheckOut).click();
    }

    public void waitForCheckoutModal() {
        waitForElementDisplayed(mdlCheckoutModal); // ✅ will STOP until modal shows
    }

    /*
     // Get Product Total (useful if quantity > 1 in future)
    public String getProductTotal(String productId) {
        WebElement row = driver.findElement(productRow(productId));
        return row.findElement(By.cssSelector(".cart_total_price")).getText().trim();
    }

    // Get Product Quantity
    public String getProductQuantity(String productId) {
        WebElement row = driver.findElement(productRow(productId));
        return row.findElement(By.cssSelector(".cart_quantity button")).getText().trim();
    }

    // Remove product from cart
    public void removeProduct(String productId) {
        WebElement row = driver.findElement(productRow(productId));
        row.findElement(By.cssSelector(".cart_quantity_delete")).click();
    }

    // Check if product exists in cart
    public boolean isProductInCart(String productId) {
        return driver.findElements(productRow(productId)).size() > 0;
    }
     */
}
