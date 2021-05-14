package com.javaTPoint.config;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExReports
{
	public static ExtentReports extent;
	public static ExtentTest logger;
	public ExtentReports ERSetUp()
	{
		try
		{
			SimpleDateFormat sdfDate = new SimpleDateFormat("ddMMyy_HHmm");
	        Date now = new Date();
	        String strDate = sdfDate.format(now);
	        extent = new ExtentReports(System.getProperty("user.dir") +"/TestReports/Xornet"+strDate+".html", false);
			extent
	        .addSystemInfo("Host Name", "Xornet")
	        .addSystemInfo("Environment", "Xornet QA");
	        extent.loadConfig(new File(System.getProperty("user.dir")+"/extent-config.xml"));
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
        return extent;
	}
	public void generateReport(String strTestCase, String strTCDescription, String strOSName, String strBrowserName, String strPassFail)
	{
		if(strPassFail.equalsIgnoreCase("Pass"))
		{
		 	logger = extent.startTest(strTestCase+" on OS "+strOSName+" and browser is "+strBrowserName);
		 	logger.log(LogStatus.PASS, strTCDescription);
		 	extent.endTest(logger);
		}
		else
		{
			logger = extent.startTest(strTestCase+" on OS "+strOSName+" and browser is "+strBrowserName);
		 	logger.log(LogStatus.FAIL, strTCDescription);
		 	extent.endTest(logger);
		}
	}
	public void flush()
	{
		extent.flush();
	}
	
}