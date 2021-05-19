package com.javaTPoint.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.karate.driver.firefox.GeckoWebDriver;
import com.sun.speech.freetts.VoiceManager;

import io.github.bonigarcia.wdm.WebDriverManager;

import com.sun.speech.freetts.VoiceManager;

public class GenericFunctions
{
	static String strFilePath=null;
	String strLocation=null;
	WebDriver driver;
	String strOSName=null;
	static com.sun.speech.freetts.Voice systemVoice = null;
	static FileInputStream fis=null;
	static XSSFWorkbook wb=null;
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
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		options.addArguments("disable-infobars");
		driver = new ChromeDriver(options);
		//options.addArguments("--unsafely-treat-insecure-origin-as-secure=http://51.145.239.109:5000/");
		/*if(strOSName.contains("Windows"))
		{
			System.setProperty("webdriver.chrome.driver", (System.getProperty("user.dir")+ "/browsers/chromedriver.exe"));
			driver = new ChromeDriver(options);
		}
		else
		{
			System.setProperty("webdriver.chrome.driver", (System.getProperty("user.dir")+ "/browsers/chromedriver"));
			driver = new ChromeDriver(options);
		}*/
		return driver;
	}
	private WebDriver initFirefoxDriver() 
	{
		WebDriverManager.firefoxdriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		options.addArguments("disable-infobars");
		driver = new FirefoxDriver();
		//options.addArguments("--unsafely-treat-insecure-origin-as-secure=http://51.145.239.109:5000/");
		/*if(strOSName.contains("Windows"))
		{
			System.setProperty("webdriver.chrome.driver", (System.getProperty("user.dir")+ "/browsers/chromedriver.exe"));
			driver = new ChromeDriver(options);
		}
		else
		{
			System.setProperty("webdriver.chrome.driver", (System.getProperty("user.dir")+ "/browsers/chromedriver"));
			driver = new ChromeDriver(options);
		}*/
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
	public String getEleText(WebElement ele)
    {
    	/*JavascriptExecutor jse = (JavascriptExecutor)driver;
    	String strText = jse.executeScript("return arguments[0].text;", ele).toString();*/
		System.out.println(ele.getText());
    	return ele.getText();
    }
	public void selectListEleByText(List<WebElement> ele, String strText)
    {
    	try
    	{
	    	for (WebElement option : ele) 
	 	   	{
	    		String strActual=option.getText().toLowerCase().trim();
	    		System.out.println(strActual);
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
	public static void allocate(String strText){
        systemVoice = VoiceManager.getInstance().getVoice(strText);
        systemVoice.allocate();
    }
     
    public static void speak(String text){
        systemVoice.speak(text);
    }
     
    public static void deallocate(){
        systemVoice.deallocate();
    }
    public static String getData(String strSheetName, String strColumnTitle, String strTCNumber) throws IOException
	{
		String strColumnValue=null;
		int intColNo=0;
		strFilePath=System.getProperty("user.dir")+"/TestData/TestData.xlsx";
		File file=new File(strFilePath);
		try					
		{
			fis=new FileInputStream(file);
			wb = new XSSFWorkbook(fis);
			Sheet sheet=wb.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator(); 
	        while (rowIterator.hasNext()) 
	        {
	            Row row = rowIterator.next(); 
	            Iterator<Cell> cellIterator = row.cellIterator();
	            while (cellIterator.hasNext()) 
	            {
	                Cell cell = cellIterator.next();
	                if(row.getRowNum()>0)
	                {
	                	if(cell.getColumnIndex()==intColNo)
						{
	                		strColumnValue=cell.getStringCellValue();
	                		break;
						}
	                }
	          }
	     }    
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			wb.close();
			fis.close();
		}
		return strColumnValue;
	}
    public boolean eleExist(WebElement ele)
    {
    	boolean flag=false;
    	try
    	{
    		 if(ele.isDisplayed())
    			 flag=true;
    		 
    	}
    	catch(NoSuchElementException e)
    	{
    		 System.out.println("Element does not exist");
    	}
    	return flag;
    }
}