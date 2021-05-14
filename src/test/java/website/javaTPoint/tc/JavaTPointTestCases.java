package website.javaTPoint.tc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.javaTPoint.config.ExReports;
import com.javaTPoint.config.GenericFunctions;
import com.mastercard.POMPages.LandingPage;
import com.mastercard.POMPages.SeleniumPage;
import com.relevantcodes.extentreports.ExtentTest;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class JavaTPointTestCases 
{
	public WebDriver driver;
	public GenericFunctions objGenericFunctions;
	LandingPage objLandingPage;
	SeleniumPage objSeleniumPage;
	public ExReports objExtentReport=null;
	public static ExtentTest logger;
	String strOSName=null;
	String strBrowser=null;
	String strTitle=null;
	String strBaseURL=null;
	static com.sun.speech.freetts.Voice systemVoice = null;
	@Parameters({ "browserType", "baseURL" })
	@BeforeTest(alwaysRun=true)
	public void initobjExtentReportReport(String browserType, String baseURL) throws MalformedURLException, InterruptedException, URISyntaxException
	{
		System.out.println("In before class");
		objGenericFunctions=new GenericFunctions();
		objExtentReport=new ExReports();
		objExtentReport.ERSetUp();
		strBrowser=browserType;
		driver=objGenericFunctions.setDriver(strBrowser);
		objLandingPage=new LandingPage(driver);
		objSeleniumPage=new SeleniumPage(driver);
		strBaseURL=baseURL;
		strOSName=System.getProperty("os.name", "generic");
		System.out.println(strOSName);
		try 
		{
			if((!strOSName.contains("Windows")) && (strBrowser.equalsIgnoreCase("edge")))
			{
				objExtentReport.generateReport("Launch JavaTPoint", "JavaTPoint NOT launched as linux does not support edge browser", strOSName, strBrowser, "FAIL");
				Assert.assertTrue(false);
			}
			else
			{
				driver.navigate().to(strBaseURL);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			objExtentReport.generateReport("Launch JavaTPoint", "JavaTPoint NOT launched, exception occurred"+e.getMessage(), strOSName, strBrowser, "FAIL");
		}
	}
	@Test(description="Launch JavaTPoint")
    public void tcLaunchJavaTPoint() throws InterruptedException, IOException
    {
		try
		{
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			strTitle=driver.getTitle();
	    	if(strTitle.contains("Javatpoint"))
	    	{
	    		objExtentReport.generateReport("Launch JavaTPoint", "JavaTPoint launched successfully", strOSName, strBrowser, "PASS");
	    	}
	    	else
	    	{
	    		objExtentReport.generateReport("Launch JavaTPoint", "JavaTPoint NOT launched successfully", strOSName, strBrowser, "FAIL");
	    		Assert.assertTrue(false);
	    	}
		}
		catch(Exception e)
		{
    		objExtentReport.generateReport("Launch JavaTPoint", "JavaTPoint NOT launched exception occured"+e.getMessage(), strOSName, strBrowser, "FAIL");
    		Assert.assertTrue(false);
		}
    }
	@Test(description="Tab Navigation")
    public void tcSeleniumTabNavigation() throws InterruptedException, IOException
    {
		String strTabText="Selenium";
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		try
		{
			objGenericFunctions.selectListEleByText(LandingPage.eleTabList, strTabText);
			
	    	if(objGenericFunctions.getEleText(SeleniumPage.elePageHead).equalsIgnoreCase("Selenium Tutorial"))
	    	{
	    		objExtentReport.generateReport("Selenium Tab Navigation", strTabText+"Tab navigated sucessfully", strOSName, strBrowser, "PASS");
	    	}
	    	else
	    	{
	    		objExtentReport.generateReport("Selenium Tab Navigation", strTabText+"Tab navigation NOT happened", strOSName, strBrowser, "FAIL");
	    	}
		}
		catch(Exception e)
		{
			objExtentReport.generateReport("Selenium Tab Navigation", strTabText+"Tab navigation NOT happened"+e.getMessage(), strOSName, strBrowser, "FAIL");
		}
    }
	@Test(description="Validate endpoint status code")
    public void tcTestGetRequestStatusCode() 
    {
		try
		{
			RestAssured.baseURI = "https://reqres.in";
			RestAssured.basePath="/api/users?page=2";
			RequestSpecification reqSpc=RestAssured.given();
			Response res=reqSpc.get();
			int statusCode=res.getStatusCode();
			System.out.println(statusCode);
	    	if(statusCode==201)
	    	{
	    		objExtentReport.generateReport("Validate customer users API", "Resreq customer API is reachable and working fine", strOSName, strBrowser, "PASS");
	    	}
	    	else
	    	{
	    		objExtentReport.generateReport("Validate customer users API", "Resreq customer API is NOT reachable", strOSName, strBrowser, "FAIL");
	    		Assert.assertTrue(false);
	    	}
		}
		catch(Exception e)
		{
    		objExtentReport.generateReport("Validate customer users API", "Resreq customer API is NOT reachable"+e.getMessage(), strOSName, strBrowser, "FAIL");
    		Assert.assertTrue(false);
		}
    }
	@Test(description="Validate endpoint status code")
    public void tcTestValidateUserMailIDExistence() 
    {
		try
		{
			RestAssured.baseURI = "https://reqres.in";
			RestAssured.basePath="/api/users/2";
			RequestSpecification reqSpc=RestAssured.given();
			Response res=reqSpc.get();
			Map<String, String> email = res.jsonPath().getMap("data");
			if(email.containsValue("janet.weaver@reqres.in"))
	    	{
	    		objExtentReport.generateReport("Check user is exist with provided email ID", "Provided mail ID is exist", strOSName, strBrowser, "PASS");
	    	}
	    	else
	    	{
	    		objExtentReport.generateReport("Check user is exist with provided email ID", "Provided mail ID is NOT exist", strOSName, strBrowser, "FAIL");
	    		Assert.assertTrue(false);
	    	}
		}
		catch(Exception e)
		{
    		objExtentReport.generateReport("Check user is exist with provided email ID", "Provided mail ID is exist"+e.getMessage(), strOSName, strBrowser, "FAIL");
		}
    }
	@AfterClass(alwaysRun=true)
	public void closeBrowser() 
	{
		objExtentReport.flush();
		driver.quit();
	}
	@AfterTest
	public void CloseAllInstances() 
	{
		driver.quit();
		objExtentReport.flush();
	}
}
