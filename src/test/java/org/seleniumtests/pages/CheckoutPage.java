package org.seleniumtests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.seleniumtests.data.User;
import org.seleniumtests.utils.ClickUtils;
import org.seleniumtests.utils.WaitUtil;

import java.util.List;
import java.util.Map;

public class CheckoutPage extends BasePage {

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    // Breadcrumbs
    private final By lblBreadcrumb = By.xpath("//div[@class='breadcrumbs']//li[@class='active']");

    // Delivery and Billing Details
    private final By lblDeliveryName = By.xpath("//ul[@id='address_delivery']//li[@class='address_firstname address_lastname']");
    private final By lblDeliveryCompany = By.xpath("//ul[@id='address_delivery']//li[3]");
    private final By lblDeliveryAddress = By.xpath("//ul[@id='address_delivery']//li[4]");
    private final By lblDeliveryAddress2 = By.xpath("//ul[@id='address_delivery']//li[5]");
    private final By lblDeliveryCityStateZip = By.xpath("//ul[@id='address_delivery']//li[@class='address_city address_state_name address_postcode']");
    private final By lblDeliveryCountry = By.xpath("//ul[@id='address_delivery']//li[@class='address_country_name']");
    private final By lblDeliveryPhone = By.xpath("//ul[@id='address_delivery']//li[@class='address_phone']");

    private final By lblBillingName = By.xpath("//ul[@id='address_invoice']//li[@class='address_firstname address_lastname']");
    private final By lblBillingFirstName = By.xpath("//ul[@id='address_invoice']//li[3]");
    private final By lblBillingCompany = By.xpath("//ul[@id='address_invoice']//li[3]");
    private final By lblBillingAddress = By.xpath("//ul[@id='address_invoice']//li[4]");
    private final By lblBillingAddress2 = By.xpath("//ul[@id='address_invoice']//li[5]");
    private final By lblBillingCityStateZip = By.xpath("//ul[@id='address_invoice']//li[@class='address_city address_state_name address_postcode']");
    private final By lblBillingCountry = By.xpath("//ul[@id='address_invoice']//li[@class='address_country_name']");
    private final By lblBillingPhone = By.xpath("//ul[@id='address_invoice']//li[@class='address_phone']");

    private final By txtOrderComment = By.xpath("//div[@id='ordermsg']//textarea[@class='form-control']");
    private final By btnPlaceOrder = By.xpath("//a[@href='/payment']");

    // Payment Page
    private final By txtName = By.xpath("//input[@data-qa='name-on-card']");
    private final By txtCardNumber = By.xpath("//input[@data-qa='card-number']");
    private final By txtCVC = By.xpath("//input[@data-qa='cvc']");
    private final By txtExpiryMonth = By.xpath("//input[@data-qa='expiry-month']");
    private final By txtExpiryYear = By.xpath("//input[@data-qa='expiry-year']");
    private final By btnPay = By.xpath("//button[@data-qa='pay-button']");

    // Successful Order Placed
    private final By lblSuccessOrderPlaced = By.xpath("//h2[@class='title text-center']");
    private final By btnDownloadInvoice = By.xpath("//a[@class='btn btn-default check_out']");

    public boolean matchCheckoutDetails(String type) {
        WaitUtil.waitForVisibility(driver, By.id("address_delivery"), 10);
        WaitUtil.waitForVisibility(driver, By.id("address_invoice"), 10);

        // Expected values from User class
        Map<String, String> expected = Map.of(
                "Name", User.TITLE + " " + User.FIRST_NAME + " " + User.LAST_NAME,
                "Company", User.COMPANY,
                "Address", User.ADDRESS,
                "Address2", User.ADDRESS2,
                "CityStateZip", User.CITY + " " + User.STATE + " " + User.ZIP,
                "Country", User.COUNTRY,
                "Phone", User.MOBILE_NUMBER
        );

        // Choose locator set based on type
        Map<String, By> locators = type.equalsIgnoreCase("delivery") ? Map.of(
                "Name", lblDeliveryName,
                "Company", lblDeliveryCompany,
                "Address", lblDeliveryAddress,
                "Address2", lblDeliveryAddress2,
                "CityStateZip", lblDeliveryCityStateZip,
                "Country", lblDeliveryCountry,
                "Phone", lblDeliveryPhone
        ) : Map.of(
                "Name", lblBillingName,
                "Company", lblBillingCompany,
                "Address", lblBillingAddress,
                "Address2", lblBillingAddress2,
                "CityStateZip", lblBillingCityStateZip,
                "Country", lblBillingCountry,
                "Phone", lblBillingPhone
        );

        // validation loop
        for (String field : expected.keySet()) {
            String actual = driver.findElement(locators.get(field)).getText().trim();
            String expectedValue = expected.get(field);

            if (!actual.equals(expectedValue)) {
                System.out.printf("❌ %s %s mismatch: expected '%s' but got '%s'%n",
                        type, field, expectedValue, actual);
                return false;
            }
        }

        System.out.println("✅ All " + type + " details match");
        return true;
    }

    public boolean matchBillingDetails() {
        WaitUtil.waitForVisibility(driver, By.id("address_delivery"), 10);
        Map<String, String> expected = Map.of(
                "Name", User.TITLE + " " + User.FIRST_NAME + " " + User.LAST_NAME,
                "Company", User.COMPANY,
                "Address", User.ADDRESS,
                "Address2", User.ADDRESS2,
                "CityStateZip", User.CITY + " " + User.STATE + " " + User.ZIP,
                "Country", User.COUNTRY,
                "Phone", User.MOBILE_NUMBER
        );

        Map<String, By> locators = Map.of(
                "Name", lblBillingName,
                "Company", lblBillingCompany,
                "Address", lblBillingAddress,
                "Address2", lblBillingAddress2,
                "CityStateZip", lblBillingCityStateZip,
                "Country", lblBillingCountry,
                "Phone", lblBillingPhone
        );

        for (String field : expected.keySet()) {
            String actual = driver.findElement(locators.get(field)).getText().trim();
            String expectedValue = expected.get(field);

            if (!actual.equals(expectedValue)) {
                System.out.println("❌ " + field + " mismatch: expected '" + expectedValue + "' but got '" + actual + "'");
                return false;
            }
        }

        System.out.println("✅ All billing details match");
        return true;
    }

    public void clickPlaceOrder(){
        ClickUtils.safeClick(driver, btnPlaceOrder, 10);
    }

    public void typeOrderComment(String comment){
        driver.findElement(txtOrderComment).clear();
        driver.findElement(txtOrderComment).sendKeys(comment);
    }

    public String getOrderCommentText(){
        return driver.findElement(txtOrderComment).getAttribute("value");
    }

    public String getName(){
        return driver.findElement(txtName).getAttribute("value");
    }

    public boolean getBreadcrumb(String breadcrumb){
        return driver.findElement(lblBreadcrumb).getText().equalsIgnoreCase(breadcrumb);
    }

    public void typeName(String name){
        driver.findElement(txtName).clear();
        driver.findElement(txtName).sendKeys(name);
    }

    public void typeCardNumber(String cardNumber){
        driver.findElement(txtCardNumber).clear();
        driver.findElement(txtCardNumber).sendKeys(cardNumber);
    }

    public void typeCVC(String cvc){
        driver.findElement(txtCVC).clear();
        driver.findElement(txtCVC).sendKeys(cvc);
    }

    public void typeExpiryMonth(String expiryMonth){
        driver.findElement(txtExpiryMonth).clear();
        driver.findElement(txtExpiryMonth).sendKeys(expiryMonth);
    }

    public void typeExpiryYear(String expiryYear){
        driver.findElement(txtExpiryYear).clear();
        driver.findElement(txtExpiryYear).sendKeys(expiryYear);
    }

    // Clear Card Details
    public void clearName(){driver.findElement(txtName).clear();}

    public void clearCardNumber(){driver.findElement(txtCardNumber).clear();}

    public void clearCVC(){driver.findElement(txtCVC).clear();}

    public void clearExpiryMonth(){driver.findElement(txtExpiryMonth).clear();}

    public void clearExpiryYear(){driver.findElement(txtExpiryYear).clear();}

    public void clickPayButton(){ClickUtils.safeClick(driver, btnPay, 10);}


    // Tooltip Present?
    public boolean tooltipName(){
        return driver.findElement(txtName).getAttribute("validationMessage").equalsIgnoreCase("Please fill out this field.");
    }

    public boolean tooltipCardNumber(){
        return driver.findElement(txtCardNumber).getAttribute("validationMessage").equalsIgnoreCase("Please fill out this field.");
    }

    public boolean tooltipCVC(){
        return driver.findElement(txtCVC).getAttribute("validationMessage").equalsIgnoreCase("Please fill out this field.");
    }

    public boolean tooltipExpiryMonth(){
        return driver.findElement(txtExpiryMonth).getAttribute("validationMessage").equalsIgnoreCase("Please fill out this field.");
    }

    public boolean tooltipExpiryYear(){
        return driver.findElement(txtExpiryYear).getAttribute("validationMessage").equalsIgnoreCase("Please fill out this field.");
    }

    public boolean SuccessfulOrder() throws InterruptedException {
        Thread.sleep(3000);
        List<WebElement> successOrder = driver.findElements(lblSuccessOrderPlaced);
        return !successOrder.isEmpty();
    }

    public void clickDownloadInvoice(){
        ClickUtils.safeClick(driver, btnDownloadInvoice, 10);
    }
}
