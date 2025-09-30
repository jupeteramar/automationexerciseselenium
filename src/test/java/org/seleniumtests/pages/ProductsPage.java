package org.seleniumtests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.seleniumtests.utils.ClickUtils;
import org.seleniumtests.utils.WaitUtil;

public class ProductsPage extends BasePage{

    public ProductsPage(WebDriver driver) { super(driver); }

    private final By btnModalClose = By.cssSelector("#cartModal .modal-footer > button");
    private final By btnModalViewCart = By.cssSelector("#cartModal div.modal-body a[href='/view_cart']");
    private final By cartModal = By.id("cartModal");



    private By addToCartButton(String productId) {
        return By.cssSelector("div.productinfo.text-center a[data-product-id='" + productId + "']");
    }

    private By viewProductButton(String productId) {
        return By.cssSelector("div.choose ul.nav.nav-pills.nav-justified li a[href='/product_details/" + productId + "']");
    }

    public void clickAddProductToCart(String productId) {
        ClickUtils.safeClick(driver, addToCartButton(productId), 10);
        WaitUtil.waitForVisibility(driver, cartModal, 10);
    }

    public void clickViewProductButton(String productId) {
        ClickUtils.safeClick(driver, viewProductButton(productId), 10);
    }

    // View Cart from Modal
    public void clickViewCartFromModal(){ClickUtils.safeClick(driver, btnModalViewCart, 10);}

    // Close the Modal when a product is added to Cart
    public void closeAddedToCartSuccessModal(){
        ClickUtils.safeClick(driver, btnModalClose, 10);
    }

    // Confirm that the Modal is displayed - triggered by add to cart button
    public boolean isCartModalDisplayed() {
        return driver.findElement(cartModal).isDisplayed();
    }

    // Get the Product Name of the selected product
    public String getProductName(String productId) {
        return driver.findElement(By.xpath("//a[@data-product-id='"+productId+"']/preceding-sibling::p")).getText().trim();
    }

    public String getProductPrice(String productId) {
        return driver.findElement(By.xpath("//a[@data-product-id='"+productId+"']/preceding-sibling::h2")).getText().trim();
    }

    public String[] getProductDetails (String productId){
        return new String[]{getProductName(productId), getProductPrice(productId)};
    }


    public String getProductIdByName(String productName) {
        WebElement product = driver.findElement(
                By.xpath("//p[text()='" + productName + "']/ancestor::div[@class='productinfo text-center']")
        );
        return product.findElement(By.cssSelector("a.add-to-cart")).getAttribute("data-product-id");
    }


}
