package testCases;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.JsonParseException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.relevantcodes.extentreports.ExtentReports;
//import com.relevantcodes.extentreports.ExtentTest;
//import com.relevantcodes.extentreports.LogStatus;


import libraries.ExtentManager;
import libraries.Utility;


public class Base 
{
	
	  public static  ExtentHtmlReporter htmlReporter;
	  public static com.aventstack.extentreports.ExtentReports extent;
	  public static ExtentTest logger;
	  private static String[] links = null;
      private static int linksCount = 0;
      List<WebElement> header=null;
      List<WebElement>  footer=null;
      public static String SBc=null;
      public static String SB=null;
      public static List<WebElement>  SBcc=null;
	  WebDriver driver = null;
	  public static int breadCrumbSize=0;
	  
	  
	  
	  
	
	@BeforeSuite
	public void preRequisite()
	{
		 extent= ExtentManager.Instance();
		System.setProperty("webdriver.chrome.driver","./Resources/chromedriver.exe");
		
	}
	@BeforeMethod
	public void setup()
	{
		
			
	}
		
	protected void validateTest(String URL) 
	{
		
		setupDriver(URL);
		
		String s=driver.getCurrentUrl();
		try {
			Thread.sleep(5000);
		    } 
		catch (InterruptedException e1) {
			
		}
		String mainTitle=driver.getTitle();
		logger = extent.createTest(s);
		String[][] data1 = new String[1][3];
		data1[0][1] =mainTitle;
		data1[0][2] =s;
		String s2=s;
		String [] s3= s2.split("/");
        String s4 = s3[s3.length-1];    
        String sb2=new String("index.html");
        header=driver.findElements(By.xpath("//nav[contains(@class,'u02nav')]//a"));  
        footer=driver.findElements(By.xpath("//div[contains(@class,'u10')]//a"));
        int nullLinks=0; 
        List<WebElement> linksize =  driver.findElements(By.tagName("a")); 
        int headerSize=header.size();
        int footerSize= footer.size();          
        try {
       breadCrumbSize=driver.findElements(By.xpath("//div[contains(@class,'u03 ') ]//a ")).size();
            }
        catch(NoSuchElementException B) {
        	
        }if(breadCrumbSize==0) {
        	breadCrumbSize=driver.findElements(By.xpath(SB)).size();	
        }  
        linksCount = linksize.size();
        int page=linksCount-(headerSize+footerSize+ breadCrumbSize);
       
        logger.log(Status.INFO,MarkupHelper.createLabel("No of URL available in main URL="+linksCount+",BreadCrumb URL="+breadCrumbSize+",Header URL="+headerSize+",Page URL="+page+",Footer URL="+footerSize,ExtentColor.GREY));
     
        logger.log(Status.INFO,MarkupHelper.createLabel("Main URL navigation result listed below", ExtentColor.GREY));
        String[][] data = new String[1][3];
        data[0][0]="Status"; 
        data[0][1]="Page Title";
        data[0][2]="URL";
        
        logger.log(Status.INFO, MarkupHelper.createTable(data));
        
	    if(s4.compareTo(sb2)==0) {
	    	data1[0][0]="301Fail";
	    logger.log(Status.FAIL, MarkupHelper.createTable(data1));
	                             }
	    else if(s4.contains("index.html")) {
	    	data1[0][0]="301Fail";
	     logger.log(Status.FAIL, MarkupHelper.createTable(data1));
	         }
	    else if(s4.contains("index.html")==false) {
	    	data1[0][0]="301Pass";
	     logger.log(Status.PASS, MarkupHelper.createTable(data1));
	    }
	    
	    
        logger.log(Status.INFO, MarkupHelper.createLabel("Page URL navigation result listed below", ExtentColor.GREY));
        
        
        
        links= new String[linksCount];
        int start=(headerSize+ breadCrumbSize);  
        int end= ( linksCount-1-footerSize);
        System.out.println(start);
       // linksCount-1-footerSize
        int count=0;
        
        String[][] data4 = new String[1][3];
        data4[0][0]="Status"; 
        data4[0][1]="Page Title";
        data4[0][2]="URL";
        logger.log(Status.INFO, MarkupHelper.createTable(data4));
        
        
        for(int i=headerSize;i<linksCount-1-footerSize;i++)
         {
         links[i] = linksize.get(i).getAttribute("href");
         } 
        
  
         
        
         for(int i=headerSize;i<linksCount-1-footerSize;i++) { 
          
        	 
        	 if(links[i]==null){
        		 nullLinks++;
        	                   }
    
       
        	 
        	 
        else {   
                 
        		 try{
                     driver.navigate().to(links[i]); 
                     WebDriverWait wait=new WebDriverWait(driver,10);
                     
                     wait.until(ExpectedConditions.alertIsPresent());
                    try {
                     Alert alert=driver.switchTo().alert();
                                 alert.accept();}
                    catch(NoAlertPresentException a) {
                	   
                                                     }
          
                     }
               catch(Exception e){
         		 
         	                     }
                  String s5= driver.getCurrentUrl();
                  String titles=driver.getTitle();
                 titles=titles.replace("Oracle |","").replace("| Oracle", "");
                  
                  String sA[] = s5.split("/"); 
                  String s6 =sA[sA.length-1];
                  
              if(s6.contains(".pdf")|s5.contains("video/player")) {
            	  
                                        }
              else {   
            	  
            	  
            	  
            	  
            	  
            	  String s7=new String("index.html");
                  String[][] data5 = new String[1][3];
                  data5[0][1]=titles;
                  data5[0][2]=s5;
                 
               
               
                    if(s6.compareTo(s7)==0){
            	   
            	   
            	    data5[0][0]="301Fail";
            	    logger.log(Status.FAIL, MarkupHelper.createTable(data5));
            	   
                                      }
                else if(s6.contains("index.html")){
     	             String s8[]=s6.split("");
     	             String s9[]=s7.split("");
     	           if(s8[0].compareTo(s9[0])==0){
     	           if(s8[0].compareTo(s9[0])==0){
     	           if(s8[1].compareTo(s9[1])==0){
                   if(s8[2].compareTo(s9[2])==0){
     	           if(s8[3].compareTo(s9[3])==0){
     	           if(s8[4].compareTo(s9[4])==0){	 
     	           if(s8[5].compareTo(s9[5])==0){			 
     	           if(s8[6].compareTo(s9[6])==0){
     	           if(s8[7].compareTo(s9[7])==0){				 
     	           if(s8[8].compareTo(s9[8])==0){
     	           if(s8[9].compareTo(s9[9])==0){
    
     	           data5[0][0]="301Fail";
               	   logger.log(Status.FAIL, MarkupHelper.createTable(data5)); }}}}}}}}}}}
     	           else {
     	           data5[0][0]="301Fail";
               	   logger.log(Status.FAIL, MarkupHelper.createTable(data5));
     	                }
                           } 
                  else if(s6.contains("index.html")==false){ 	
            	   if(titles.contains("Page not found")|titles.contains("500 Internal Server Error")) {
            		   data5[0][0]="301Pass";
                	   logger.log(Status.FAIL, MarkupHelper.createTable(data5));}
            	  
            	   
            	    else   {
                		   data5[0][0]="301Pass";
                    	   logger.log(Status.PASS, MarkupHelper.createTable(data5));
            	            }
     	                      }  
                                count++;
                                  
                                      
                               }
                         
                          
                          
                                    }
                          
                          
	                                        }
         
         logger.log(Status.INFO, MarkupHelper.createLabel("No of Null URL="+nullLinks,ExtentColor.GREY));
	                                                 }
	       protected void validateBreadCrumb( )
	   
	        {
	    	 //  logger = extent.startTest(URL, "");
		    // setupDriver(URL);
            
           //  String page0= driver.getCurrentUrl();
            
          //   String  page1[]=page0.split("/");
          //   String lastIndex= page1[  page1.length-1];
	  logger.log(Status.INFO, MarkupHelper.createLabel("Breadcrumb URL navigation result listed below", ExtentColor.GREY));

               
                   SB="//div[contains(@class,'u03 ') ]//a ";
                   SBc="//div[contains(@id,'breadCrumb') ]//a ";
                   String[][] data2 = new String[1][3];
                   data2[0][0]="Status"; 
                   data2[0][1]="Page Title";
                   data2[0][2]="URL";
                   logger.log(Status.INFO, MarkupHelper.createTable(data2));
                   
               	try{
                     SBcc=driver.findElements(By.xpath(SB));
                     int  size=SBcc.size();
                    String BreadcrumbLinks[] =new String[size];
                    for(int i=0;i<=size-1;i++){
                     BreadcrumbLinks[i]= SBcc.get(i).getAttribute("href");}
                   
                    for(int j=0;j<=size-1;j++){
                   	driver.navigate().to(BreadcrumbLinks[j]);  
                    String cuURL= driver.getCurrentUrl();
                    String titleBC= driver.getTitle();
                    
  
                    String[][] data3 = new String[1][3];
                    data3[0][1]=titleBC;
                    data3[0][2]=cuURL;
                   
                   	          if(cuURL.contains("index.html")) 
                   	        
                   	          {
                   	        	  
                   	        	data3[0][0]="301Fail";
                   	        	  logger.log(Status.FAIL, MarkupHelper.createTable(data3));
                   	        	 
                   	                        }
                   	          else{
                   	        	data3[0][0]="301Pass";
                   	        	logger.log(Status.PASS, MarkupHelper.createTable(data3));
                   	                }
                                          } 
                   
                  
                                            	}
                   	
                                           catch(NoSuchElementException e){
                   	
                                            }
                                             if(SBcc.size()==0)  {
               	  
               	  
               	 	try{
                           SBcc=driver.findElements(By.xpath(SBc));
                          System.out.println(SBcc);
                          int size= SBcc.size();
                          String BreadcrumbLinks[] =new String[size];
                         
                          for(int i=0;i<=size-1;i++){
                      	  BreadcrumbLinks[i]= SBcc.get(i).getAttribute("href");}
                          for(int j=0;j<=size-1;j++){
	                            driver.navigate().to(BreadcrumbLinks[j]);  
	                        	String cuURL= driver.getCurrentUrl();
	                            String titleBC= driver.getTitle();
	                            String[][] data3 = new String[1][3];
	                            
	                            data3[0][1]=titleBC;
	                            data3[0][2]=cuURL;
	                           
	                           	          if(cuURL.contains("index.html")){
	                           	        	  
	                           	        	data3[0][3]="301Fail";
	                           	        	  logger.log(Status.FAIL, MarkupHelper.createTable(data3));
	                           	        	 
	                           	          }
	                           	          else{
	                           	        	data3[0][3]="301Pass";
	                           	        	logger.log(Status.PASS, MarkupHelper.createTable(data3));
	                           	          }
                                       
                                        }}
                              catch(NoSuchElementException e){
                          	
                                               }
               	 	
                                                } 
                                                    }	
	      
	
	private void setupDriver(String URL) 
	{
		try 
		{
			if(driver==null)
			{
				driver = new ChromeDriver();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.manage().window().maximize();
				driver.get(URL);
			}
		Thread.sleep(2000);
		} catch(Exception ie) {
		ie.printStackTrace();
		}
		
		
	}
	
	@DataProvider(name="URLs")
	public Object[][] loginData() 
	{
		Object[][] arrayObject = Utility.getExcelData("./TestData/TestURLs.xlsx","URLs");
		return arrayObject;
	}
	@AfterMethod
	public void updateResults()
	{
		
		driver.close();
		driver=null;
	}
	
	@AfterSuite
	 public void tear()
	 {
		 // extent.endTest(logger);
		  extent.flush();  
		  driver=new ChromeDriver();
		  driver.get("file:///"+System.getProperty("user.dir")+"/"+ExtentManager.Path.replace("./", ""));
		  driver.manage().window().maximize();
		 // driver.quit();
	 }
	
}
