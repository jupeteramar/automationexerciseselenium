package org.seleniumtests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.seleniumtests.utils.ClickUtils;

import java.time.Duration;

public abstract class BasePage {
    protected WebDriver driver;
    private final WebDriverWait wait;

    // Navbar locators
    private final By homeLink = By.cssSelector(".shop-menu .nav.navbar-nav li a[href='/']");
    private final By productsLink = By.cssSelector(".shop-menu .nav.navbar-nav li a[href='/products']");
    private final By cartLink = By.cssSelector(".shop-menu .nav.navbar-nav li a[href='/view_cart']");
    private final By signupLoginLink = By.cssSelector(".shop-menu .nav.navbar-nav li a[href='/login']");
    private final By logOutLink = By.cssSelector(".shop-menu .nav.navbar-nav li a[href='/logout']");
    private final By deleteAccountLink = By.cssSelector(".shop-menu .nav.navbar-nav li a[href='/delete_account']");
    private final By testCasesLink = By.cssSelector(".shop-menu .nav.navbar-nav li a[href='/test_cases']");
    private final By apiTestingLink = By.cssSelector(".shop-menu .nav.navbar-nav li a[href='/api_list']");
    private final By videoTutorialsLink = By.cssSelector(".shop-menu .nav.navbar-nav li a[href*='youtube']");
    private final By contactUsLink = By.cssSelector(".shop-menu .nav.navbar-nav li a[href='/contact_us']");
    private final By lblUserName = By.xpath("//ul[@class='nav navbar-nav']//a//b");

    // Product Filter

    // Categories
    // Women
    private final By lnkWomen = By.xpath("//div[@class='left-sidebar']//a[@href='#Women']");
    // Women Subcategories
    private final By lnkWomenDress = By.xpath("//div[@class='left-sidebar']//a[@href='/category_products/1']");
    private final By lnkWomenTops = By.xpath("//div[@class='left-sidebar']//a[@href='/category_products/2']");
    private final By lnkWomenSaree = By.xpath("//div[@class='left-sidebar']//a[@href='/category_products/7']");
    // Men
    private final By lnkMen = By.xpath("//div[@class='left-sidebar']//a[@href='#Men']");
    // Men Subcategories
    private final By lnkMenTshirt = By.xpath("//div[@class='left-sidebar']//a[@href='/category_products/3']");
    private final By lnkMenJeans = By.xpath("//div[@class='left-sidebar']//a[@href='/category_products/6']");
    // Kids
    private final By lnkKids = By.xpath("//div[@class='left-sidebar']//a[@href='#Kids']");
    // Kids Subcategories
    private final By lnkKidsDress = By.xpath("//div[@class='left-sidebar']//a[@href='/category_products/4']");
    private final By lnkKidsTopsShirts = By.xpath("//div[@class='left-sidebar']//a[@href='/category_products/5']");


    // Constructor
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Navbar actions
    public void goToHome() {
        ClickUtils.safeClick(driver, homeLink, 10);
    }

    public void goToProducts() {
        ClickUtils.safeClick(driver, productsLink, 10);
    }

    public void goToCart() {
        ClickUtils.safeClick(driver, cartLink, 10);
    }

    public void goToSignupLogin() {
        ClickUtils.safeClick(driver, signupLoginLink, 10);
    }

    public void deleteAccount() {
        ClickUtils.safeClick(driver, deleteAccountLink, 10);
    }

    public void logOut() {
        ClickUtils.safeClick(driver, logOutLink, 10);
    }

    public void goToTestCases() {
        ClickUtils.safeClick(driver, testCasesLink, 10);
    }

    public void goToApiTesting() {
        ClickUtils.safeClick(driver, apiTestingLink, 10);
    }

    public void goToVideoTutorials() {
        ClickUtils.safeClick(driver, videoTutorialsLink, 10);
    }

    public void goToContactUs() {
        ClickUtils.safeClick(driver, contactUsLink, 10);
    }

    public boolean isButtonDisabled(By locator) {
        WebElement element = driver.findElement(locator);

        // Check multiple ways a button can be "disabled"
        boolean nativeDisabled = !element.isEnabled();
        boolean cssDisabled = element.getAttribute("class") != null &&
                element.getAttribute("class").contains("disabled");
        boolean ariaDisabled = "true".equals(element.getAttribute("aria-disabled"));

        return nativeDisabled || cssDisabled || ariaDisabled;
    }

    public boolean isElementDisplayed(By locator) {
        WebElement element = driver.findElement(locator);

        // Check multiple ways a button can be "disabled"
        boolean nativeDisabled = !element.isEnabled();
        boolean cssDisabled = element.getAttribute("class") != null &&
                element.getAttribute("class").contains("disabled");
        boolean ariaDisabled = "true".equals(element.getAttribute("aria-disabled"));

        return nativeDisabled || cssDisabled || ariaDisabled;
    }

    public void waitForElementDisplayed(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public boolean isUserNameDisplayed() {
        WebElement lblUsername = driver.findElement(lblUserName);

        boolean loggedIn = lblUsername.isDisplayed();
        return loggedIn;
    }

    public void filterWomenDress(){
        ClickUtils.safeClick(driver, lnkWomen, 10);
        ClickUtils.safeClick(driver, lnkWomenDress, 10);
    }

    public void filterWomenTops(){
        ClickUtils.safeClick(driver, lnkWomen, 10);
        ClickUtils.safeClick(driver, lnkWomenTops, 10);
    }

    public void filterWomenSaree(){
        ClickUtils.safeClick(driver, lnkWomen, 10);
        ClickUtils.safeClick(driver, lnkWomenSaree, 10);
    }

    public void filterMenTshirt(){
        ClickUtils.safeClick(driver, lnkMen, 10);
        ClickUtils.safeClick(driver, lnkMenTshirt, 10);
    }

    public void filterMenJeans(){
        ClickUtils.safeClick(driver, lnkMen, 10);
        ClickUtils.safeClick(driver, lnkMenJeans, 10);
    }

    public void filterKidsDress(){
        ClickUtils.safeClick(driver, lnkKids, 10);
        ClickUtils.safeClick(driver, lnkKidsDress, 10);
    }

    public void filterKidsTopsShirts(){
        ClickUtils.safeClick(driver, lnkKids, 10);
        ClickUtils.safeClick(driver, lnkKidsTopsShirts, 10);
    }
}
