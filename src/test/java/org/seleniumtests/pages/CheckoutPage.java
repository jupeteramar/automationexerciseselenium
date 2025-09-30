package org.seleniumtests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage{

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    private final By lbl = By.xpath("");
    private final By lblProductName = By.cssSelector("div.product-information h2");


}
