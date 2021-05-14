package com.mastercard.POMPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SeleniumPage
{
	WebDriver driver;

	@FindBy(xpath="//div[@class='onlycontentinner']//h1")
	public static WebElement elePageHead;

	

    public SeleniumPage(WebDriver driver){

        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}