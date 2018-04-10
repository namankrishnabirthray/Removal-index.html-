package libraries;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentXReporter;
//import com.relevantcodes.extentreports.ExtentReports;
//import com.relevantcodes.extentreports.ExtentTest;

public class ExtentManager 
{
	  public static String Path="";
	  public static  ExtentHtmlReporter htmlReporter;
	  public static ExtentReports extent;
	  public static ExtentTest logger;
	  public static ExtentXReporter extentx;
	  public static ExtentReports Instance()
    {
		 htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") +"./Report/WebTech_Automation_Report"+Utility.GetCurrentTimeStamp().replace(":","_").replace(".","_")+".html");
		 htmlReporter.loadXMLConfig(new File(System.getProperty("user.dir")+"/extent-config.xml"));
		 Path = "./Report/WebTech_Automation_Report"+Utility.GetCurrentTimeStamp().replace(":","_").replace(".","_")+".html";
		 extent=new ExtentReports();
		 htmlReporter.config().setDocumentTitle("Oracle|WebTech");	 
		 extent.setSystemInfo("Host","Aarti Ekbote");
	     extent.setSystemInfo("Author","Naman Krishna Birthray");
	     htmlReporter.config().setReportName("Removal-index.html ");
	     extent.attachReporter(htmlReporter);
		 return extent;
   }
	/* public static ExtentReports Instance()
     {
	
		 ExtentReports extent;
		 Path = "./Report/WebTech_Automation_Report"+Utility.GetCurrentTimeStamp().replace(":","_").replace(".","_")+".html";
		 System.out.println(Path);
		 extent = new ExtentReports(Path, true);
		 extent.loadConfig(new File("./Resources/config.xml"));
		 extent.addSystemInfo("User Name", "Abdul/Naman Krishna Birthray");
		 extent.assignProject("Removal_index.html");
		 return extent;
    }   */
	public static String CaptureScreen(WebDriver driver, String ImagesPath)
	{
	    TakesScreenshot oScn = (TakesScreenshot) driver;
	    File oScnShot = oScn.getScreenshotAs(OutputType.FILE);
		File oDest = new File(ImagesPath+".jpg");
		 try {
		      FileUtils.copyFile(oScnShot, oDest);
		 } catch (IOException e) {System.out.println(e.getMessage());}
		 return ImagesPath+".jpg";
	       
	}


}
