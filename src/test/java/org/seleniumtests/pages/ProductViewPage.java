package org.seleniumtests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class ProductViewPage extends BasePage {

    public ProductViewPage(WebDriver driver) {
        super(driver);
    }

    // Product Details
    private final By lblProductName = By.cssSelector("div.product-information h2");
    private final By lblProductPrice = By.cssSelector("div.product-information span span");
    public static final By inputQuantity = By.id("quantity");
    public static final By lblCategory = By.xpath("//div[@class='product-information']//p[1]");
    public static final By lblBrand = By.xpath("//div[@class='product-information']//p[4]");
    private final By btnAddToCart = By.xpath("//div[@class='product-information']//span//button[@class='btn btn-default cart']");

    // Write Review Form
    public static final By txtName = By.id("name");
    public static final By txtEmail = By.id("email");
    public static final By txtReview = By.id("review");
    public static final By btnSubmitReview = By.id("button-review");
    public static final By lblSuccessReview = By.cssSelector("div.alert-success.alert span");


    public String getProductName() {
        return driver.findElement(lblProductName).getText().trim();
    }

    public String getProductPrice() {
        return driver.findElement(lblProductPrice).getText().trim();
    }

    // Actions for Review Form
    public void typeName(String name) {
        driver.findElement(txtName).sendKeys(name);
    }

    public void typeEmail(String email) {
        driver.findElement(txtEmail).sendKeys(email);
    }

    public void typeReview(String review) {
        driver.findElement(txtReview).sendKeys(review);
    }

    public void clickSubmitReview() {
        driver.findElement(btnSubmitReview).click();
    }


    // REVIEW FORM TOOLTIP TEXT
    public String tooltipName(){
        return driver.findElement(txtName).getAttribute("validationMessage");
    }

    public String tooltipEmail(){
        return driver.findElement(txtEmail).getAttribute("validationMessage");
    }

    public String tooltipReview(){
        return driver.findElement(txtReview).getAttribute("validationMessage");
    }

    public boolean isSuccessAlertDisplayed(){
        return driver.findElement(lblSuccessReview).isDisplayed();
    }

    public void addQuantityByArrowUpKey(){
        driver.findElement(inputQuantity).sendKeys(Keys.ARROW_UP);
    }

    public void subtractQuantityByArrowDownKey(){
        driver.findElement(inputQuantity).sendKeys(Keys.ARROW_DOWN);
    }

    public String getCategory(){
        return driver.findElement(lblCategory).getText().trim();
    }

    public String getBrandName(){
        return driver.findElement(lblBrand).getText().trim();
    }

    public boolean isCategoryMatched(String expected){
        return driver.findElement(lblCategory).getText().contains(expected);
    }

    public boolean isBrandMatched(String expected){
        return driver.findElement(lblBrand).getText().contains(expected);
    }


}

