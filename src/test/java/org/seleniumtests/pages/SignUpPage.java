package org.seleniumtests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.seleniumtests.utils.ClickUtils;
import org.seleniumtests.utils.WaitUtil;

public class SignUpPage extends BasePage {

    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    private final By txtName = By.xpath("//div[@class='signup-form']//input[@data-qa='signup-name']");
    private final By txtEmail = By.xpath("//div[@class='signup-form']//input[@data-qa='signup-email']");
    private final By btnSignUp = By.xpath("//div[@class='signup-form']//button[@data-qa='signup-button']");
    private final By lblUserAlreadyExists = By.xpath("//div[@class='signup-form']//p[@style='color: red;']");


    // Account Information
    // Title
    private final By radMr = By.id("id_gender1");
    private final By radMrs = By.id("id_gender2");

    // Name
    private final By txtName2 = By.id("name");
    private final By txtEmail2 = By.id("email");
    private final By txtPassword = By.id("password");

    // Birthdate
    private final By drpDay = By.id("days");
    private final By drpMonth = By.id("months");
    private final By drpYear = By.id("years");

    // Optional Subscription
    private final By chkNewsletter = By.id("newsletter");
    private final By chkOffers = By.id("optin");

    // Address Information
    private final By txtFirstName = By.id("first_name");
    private final By txtLastName = By.id("last_name");
    private final By txtCompany = By.id("company");
    private final By txtAddress = By.id("address1");
    private final By txtAddress2 = By.id("address2");
    private final By drpCountry = By.id("country");
    private final By txtState = By.id("state");
    private final By txtCity = By.id("city");
    private final By txtZip = By.id("zipcode");
    private final By txtMobileNumber = By.id("mobile_number");

    private final By btnSubmit = By.xpath("//div[@class='login-form']/form/button[@data-qa='create-account']");

    // Account Created Page
    private final By lblSuccessRegistration = By.xpath("//h2[@data-qa='account-created']");
    private final By btnContinue = By.xpath("//a[@data-qa='continue-button']");

    public void typeSignUpCredentials(String name, String email) {
        driver.findElement(txtName).sendKeys(name);
        driver.findElement(txtEmail).sendKeys(email);
    }

    public void typeEmail(String email) {
        driver.findElement(txtEmail).sendKeys(email);
    }

    public void typeName(String name) {
        driver.findElement(txtName).sendKeys(name);
    }

    public void clickSignUpButton() {
        ClickUtils.safeClick(driver, btnSignUp, 10);
    }

    public void selectTitle(String title) {
        if (title.equals("Mr")) {
            ClickUtils.safeClick(driver, radMr, 10);
        } else if (title.equals("Mrs")) {
            ClickUtils.safeClick(driver, radMrs, 10);
        }
    }

    public void typeName2(String name2) {
        driver.findElement(txtName2).sendKeys(name2);
    }

    public void typeEmail2(String email2) {
        driver.findElement(txtEmail2).sendKeys(email2);
    }

    public void typePassword(String password) {
        driver.findElement(txtPassword).sendKeys(password);
    }

    public void selectDay(String day) {
        new Select(driver.findElement(drpDay)).selectByVisibleText(day);
    }

    public void selectMonth(String month) {
        new Select(driver.findElement(drpMonth)).selectByVisibleText(month);
    }

    public void selectYear(String year) {
        new Select(driver.findElement(drpYear)).selectByVisibleText(year);
    }

    public void checkPromotions(boolean newsletter, boolean offers) {
        if (newsletter) {
            ClickUtils.safeClick(driver, chkNewsletter, 10);
        }

        if (offers) {
            ClickUtils.safeClick(driver, chkOffers, 10);
        }
    }

    public void typeFirstName(String firstName) {
        driver.findElement(txtFirstName).sendKeys(firstName);
    }

    public void typeLastName(String lastName) {
        driver.findElement(txtLastName).sendKeys(lastName);
    }

    public void typeCompany(String company) {
        driver.findElement(txtCompany).sendKeys(company);
    }

    public void typeAddress(String address) {
        driver.findElement(txtAddress).sendKeys(address);
    }

    public void typeAddress2(String address2) {
        driver.findElement(txtAddress2).sendKeys(address2);
    }

    public void selectCountry(String country) {
        new Select(driver.findElement(drpCountry)).selectByVisibleText(country);
    }

    public void typeState(String state) {
        driver.findElement(txtState).sendKeys(state);
    }

    public void typeCity(String city) {
        driver.findElement(txtCity).sendKeys(city);
    }

    public void typeZip(String zip) {
        driver.findElement(txtZip).sendKeys(zip);
    }

    public void typeMobileNumber(String mobileNumber) {
        driver.findElement(txtMobileNumber).sendKeys(mobileNumber);
    }

    public void clickSubmit() {
        ClickUtils.safeClick(driver, btnSubmit, 5);

    }

    public boolean isSuccessRegistrationVisible() {
        boolean success = driver.findElement(lblSuccessRegistration).isDisplayed();
        return success;
    }

    public boolean isErrorUserAlreadyExistVisible() {
        boolean error = driver.findElement(lblUserAlreadyExists).isDisplayed();
        return error;
    }

    public void clickContinueButton() {
        ClickUtils.safeClick(driver, btnContinue, 5);
    }

    public WebElement getEmailElement() {
        return driver.findElement(txtEmail);
    }

    public WebElement getNameElement() {
        return driver.findElement(txtName);
    }

    public void clearName2() {
        driver.findElement(txtName2).clear();
    }

    public String tooltipName2() {
        return driver.findElement(txtName2).getAttribute("validationMessage");
    }

    public String tooltipPassword() {
        return driver.findElement(txtPassword).getAttribute("validationMessage");
    }

    public String tooltipFirstName() {
        return driver.findElement(txtFirstName).getAttribute("validationMessage");
    }

    public String tooltipLastName() {
        return driver.findElement(txtLastName).getAttribute("validationMessage");
    }

    public String tooltipAddress() {
        return driver.findElement(txtAddress).getAttribute("validationMessage");
    }

    public String tooltipCity() {
        return driver.findElement(txtCity).getAttribute("validationMessage");
    }

    public String tooltipState() {
        return driver.findElement(txtState).getAttribute("validationMessage");
    }

    public String tooltipZip() {
        return driver.findElement(txtZip).getAttribute("validationMessage");
    }

    public String tooltipMobileNumber() {
        return driver.findElement(txtMobileNumber).getAttribute("validationMessage");
    }

    public String tooltipName(){
        return driver.findElement(txtName).getAttribute("validationMessage");
    }
}

