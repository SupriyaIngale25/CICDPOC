package com.automationbase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.utils.CommonUtils;
import com.utils.XlsReader;

public class TestBase {

    protected static Properties config;
    protected static Properties logInfo;
    protected static WebDriverWait expWait;
    protected static String currentWindow;
    protected static WebDriver driver;
    protected static String browser;
    protected static HashMap<String, String> finalResult = new HashMap<String, String>();
    protected static XlsReader datatable;
    protected static XlsReader result;
    protected static String buildVersion;
    protected static int build = 1;
    protected static final String PROJECTDIR = System.getProperty("user.dir");
    public static ExtentReports extent;
    public static ExtentTest logger;
    public static ExtentTest extentTest;
 
    private static Logger log = Logger.getLogger(TestBase.class);
   

    @BeforeSuite
    public void setReport() {
    	String dateName = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    	
    	extent = ExtentManager.createInstance(PROJECTDIR + "/test-output/TestReportFinal_"+dateName+".html");
    	ExtentHtmlReporter htmlReporter  = new ExtentHtmlReporter(PROJECTDIR + "/test-output/TestReportFinal_"+dateName+".html");
    	extent.attachReporter(htmlReporter);
    	
    	//extent.setReportUsesManualConfiguration(true);
    	
    	
    	extent.setSystemInfo("Host Name", "TEST MACHINE" +" "+ System.getProperty("os.name"));
   	 extent.setSystemInfo("Environment", System.getProperty("os.name"));
   	 extent.setSystemInfo("User Name", "TEST USER");

     //   System.out.println(PROJECTDIR);

    }

    @BeforeTest
    public static void init() throws IOException, InterruptedException {
        
        config = new Properties();
        FileInputStream ip = new FileInputStream(new File(PROJECTDIR + "/config/config.properties"));
        config.load(ip);

        logInfo = new Properties();
        PropertyConfigurator.configure(PROJECTDIR + "/config/log4j.properties");

        datatable = new XlsReader(PROJECTDIR + "/testdata/TestInput.xlsx");
        
        CommonUtils.openBrowser(datatable.getCellData("Browser", 0, 2));
        log.info("Launched the " + datatable.getCellData("Browser", 0, 2) + " Browser");

    }

    @AfterMethod
    public void tearDown(ITestResult result) throws Exception {
        if (result.getStatus()==ITestResult.FAILURE) {
            String screenshotpath = getScreenshot(driver, result.getName());
            
            extentTest.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" Test case FAILED due to below issues:", ExtentColor.RED));     
    		extentTest.fail(result.getThrowable() );
            extentTest.fail("Snapshot for failed step : ", MediaEntityBuilder.createScreenCaptureFromPath(screenshotpath).build());
            
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentTest.log(Status.SKIP, "Test case skipped is "+result.getName());
            extentTest.skip(result.getThrowable() );
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.log(Status.PASS, MarkupHelper.createLabel( "Test case - "+ result.getName(), ExtentColor.GREEN));
            
        }

      extent.flush();
    }

    @AfterTest
    public void endReport() {
        extent.flush();
    }

    @AfterClass
    public static void FinishTesting() {
        driver.close();
        driver.quit();

    }

    @AfterSuite
    public void closeExtent() {
        extent.flush();

    }

    public static String getScreenshot(WebDriver driver, String screenshotName) {
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destination = System.getProperty("user.dir") + "/TestsScreenshots/" + screenshotName + "_"
                + dateName + ".png";

        File finalDestination = new File(destination);
        try {
            FileUtils.copyFile(source, finalDestination);
        } catch (Exception e) {
            System.out.println("******   Exception occured trying to capture screenshot - " + screenshotName);
        }

        return destination;
    }

    public static void logFail(String details) {
        extentTest.log(Status.FAIL,  MarkupHelper.createLabel(details, ExtentColor.RED));
    }

    public static void logPass(String details) {
        extentTest.log(Status.PASS, MarkupHelper.createLabel(details, ExtentColor.GREEN));

    }

    public static void logInfo(String details) {
        extentTest.log(Status.INFO, details);
    }

}

