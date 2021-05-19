package com.mastercard.POMPages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage
{
	WebDriver driver;

	@FindBy(xpath="//div[@class='ddsmoothmenu']//li")
	public static List<WebElement> eleTabList;


    public LandingPage(WebDriver driver){

        this.driver = driver;
        PageFactory.initElements(driver, this);

    }
      
}