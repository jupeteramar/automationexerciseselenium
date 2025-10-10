package org.seleniumtests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ContactPage extends BasePage{

    public ContactPage(WebDriver driver){super(driver);}

    private final By txtName = By.xpath("//input[@data-qa='name']");
}
