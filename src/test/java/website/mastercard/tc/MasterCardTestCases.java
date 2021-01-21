package website.mastercard.tc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.mastercard.config.ExReports;
import com.mastercard.config.GenericFunctions;
import com.relevantcodes.extentreports.ExtentTest;

public class MasterCardTestCases extends GenericFunctions
{
	public static WebDriver driver;
	public GenericFunctions objGenericFunctions;
	public ExReports objExtentReport=null;
	public static ExtentTest logger;
	String strOSName=null;
	String strBrowser=null;
	String strTitle=null;
	String strBaseURL=null;
	@Parameters({ "browserType"})
	@BeforeClass
	public void initobjExtentReportReport(String browserType) throws MalformedURLException, InterruptedException, URISyntaxException
	{
		strBrowser=browserType;
		objGenericFunctions=new GenericFunctions();
		driver=objGenericFunctions.setDriver(strBrowser);
		objExtentReport=new ExReports();
		objExtentReport.ERSetUp();
	}
	@Parameters({"baseURL" })
	@BeforeMethod
	public void initializeBaseTest(String baseURL) 
	{
		strBaseURL=baseURL;
		strOSName=System.getProperty("os.name", "generic");
		System.out.println(strOSName);
		try 
		{
			if((!strOSName.contains("Windows")) && (strBrowser.equalsIgnoreCase("edge")))
			{
				objExtentReport.generateReport("Launch MasterCard", "MasterCard NOT launched as linux does not support edge browser", strOSName, strBrowser, "FAIL");
			}
			else
			{
				driver.navigate().to(strBaseURL);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			objExtentReport.generateReport("Launch MasterCard", "MasterCard NOT launched, exception occurred", strOSName, strBrowser, "FAIL");
		}
	}
	@Test(description="Launch MasterCard")
    public void tcLaunchMasterCard() throws InterruptedException, IOException
    {
		try
		{
			strTitle=driver.getTitle();
	    	if(strTitle.contains("Speech Recognizer"))
	    	{
	    		objExtentReport.generateReport("Launch MasterCard", "MasterCard launched successfully", strOSName, strBrowser, "PASS");
	    	}
	    	else
	    	{
	    		objExtentReport.generateReport("Launch MasterCard", "MasterCard NOT launched successfully", strOSName, strBrowser, "FAIL");
	    	}
		}
		catch(Exception e)
		{
    		objExtentReport.generateReport("Launch MasterCard", "MasterCard NOT launched exception occured"+e.getMessage(), strOSName, strBrowser, "FAIL");
    		Assert.assertTrue(false);
		}
    }
	@AfterClass(alwaysRun=true)
	public void closeBrowser() 
	{
		System.out.println("AfterClass");
		objExtentReport.flush();
		driver.quit();
	}
	@AfterMethod
	public void CloseAllInstances() 
	{
		System.out.println("AfterMethod");
		driver.close();
		//objExtentReport.flush();
	}
}
