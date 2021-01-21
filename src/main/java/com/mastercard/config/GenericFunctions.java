package com.mastercard.config;

import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GenericFunctions
{
	static String strFilePath=null;
	String strLocation=null;
	static FileInputStream fis=null;
	WebDriver driver;
	String strOSName=null;
	public GenericFunctions()
	{
		strOSName=System.getProperty("os.name", "generic");
	}
	public WebDriver setDriver(String browser) throws InterruptedException, URISyntaxException, MalformedURLException 
	{
		switch (browser) 
		{
			case "chrome":
			case "Chrome":
				driver = initChromeDriver();
				break;
			case "firefox":
			case "Firefox":
				driver = initFirefoxDriver();
				break;
			case "edge":
			case "Edge":
				driver = initEdgeDriver();
				break;
			default:
				System.out.println("browser : " + browser
						+ " is invalid, Launching chrome as browser of choice..");
				driver = initChromeDriver();
		}
		return driver;
	}
	private WebDriver initChromeDriver() throws InterruptedException 
	{
		ChromeOptions objOptions=new ChromeOptions();
		HashMap<String, Object>prefs=new HashMap<String, Object>();
		prefs.put("profile.default_content_setting.popups", 0);
		prefs.put("profile.default_content_setting_values.notifications", 2);
		prefs.put("safebrowsing.enabled", true);
		LoggingPreferences logPrefs=new LoggingPreferences();
		logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
		objOptions.addArguments("disable-infobars");
		objOptions.setExperimentalOption("prefs", prefs);
		objOptions.addArguments("--start-maximized");
		objOptions.addArguments("--dns-prefetch-disable");
		objOptions.addArguments("safebrowsing-disable-extension-blacklist");
		objOptions.setExperimentalOption("useAutomationExtension", false);
		objOptions.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation")); 
		objOptions.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
		if(strOSName.contains("Windows"))
		{
			System.setProperty("webdriver.chrome.driver", (System.getProperty("user.dir")+ "/browsers/chromedriver.exe"));
			driver = new ChromeDriver(objOptions);
		}
		else
		{
			System.setProperty("webdriver.chrome.driver", (System.getProperty("user.dir")+ "/browsers/chromedriver"));
			driver = new ChromeDriver(objOptions);
		}
		return driver;
	}
	private WebDriver initFirefoxDriver() 
	{
		try
		{
			if(strOSName.equalsIgnoreCase("Windows"))
				System.setProperty("webdriver.gecko.driver", (System.getProperty("user.dir")+"/browsers/geckodriver.exe"));
			else
				System.setProperty("webdriver.gecko.driver", (System.getProperty("user.dir")+"/browsers/geckodriverLinux"));
			driver = new FirefoxDriver();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return driver;
	}
	private WebDriver initEdgeDriver() 
	{
		try
		{
			if(strOSName.equalsIgnoreCase("Windows"))
			{
				System.setProperty("webdriver.edge.driver", (System.getProperty("user.dir")+"/browsers/msedgedriver.exe"));
				driver = new EdgeDriver();
			}
			else
				System.out.println("Edge does not support linux");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return driver;
	}
	public String getTimeStamp() 
	{
        SimpleDateFormat sdfDate = new SimpleDateFormat("ddmmyyyHHmmss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
	public int getRandomNo(int intMaxNo)
	{
		Random rand = new Random(); 
		int intRanNo = rand.nextInt(intMaxNo);
		return intRanNo;
	}
	public void moveToElement(WebElement ele, WebDriver driver)
	{
		Actions objAct = new Actions(driver);
		objAct.moveToElement(ele);
	}
	public void highlightElement(WebElement ele, WebDriver driver)
    {
    	try
    	{
    		JavascriptExecutor js = (JavascriptExecutor) driver;
    		js.executeScript("arguments[0].setAttribute('style', 'background: red; border: 4px solid blue;');", ele);
    	}
    	catch(Exception e)
    	{
    		
    	}
    }
	public void enterValueInputBox(WebElement ele, String strValue)
    {
    	try
    	{
    		if(ele.isDisplayed())
    		{
    			ele.click();
    			Thread.sleep(500);
    			ele.clear();
    			ele.sendKeys(strValue);
    		}
    	}
    	catch(Exception e)
    	{
    		System.out.println("Ele not found");
    	}
    }
	public void eleClick(WebElement ele)
    {
    	try
    	{
    		if(ele.isDisplayed() && ele.isEnabled())
    		ele.click();
    	}
    	catch(Exception e)
    	{
    	}
    }
	public void ExplicitWait(WebElement ele, String strCondition)
	{
		WebDriverWait wait = new WebDriverWait(driver,30);
		if(strCondition.equalsIgnoreCase("Visibility"))
			wait.until(ExpectedConditions.visibilityOf(ele));
		else
			wait.until(ExpectedConditions.invisibilityOf((ele)));
	}
	public String getTextEle(WebElement ele)
    {
    	JavascriptExecutor jse = (JavascriptExecutor)driver;
    	String strText = jse.executeScript("return arguments[0].text;", ele).toString();
    	return strText;
    }
	public void selectListEleByText(List<WebElement> ele, String strText)
    {
    	try
    	{
	    	for (WebElement option : ele) 
	 	   	{
	    		String strActual=option.getText().toLowerCase().trim();
	    		String strExpected=strText.toLowerCase().trim();
				if(strActual.contains(strExpected))
				{
					highlightElement(option, driver);
					eleClick(option);
					break;
				}
	 	   } 
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    }
}