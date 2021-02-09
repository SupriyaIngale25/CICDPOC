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
//import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
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
        extent = new ExtentReports(PROJECTDIR + "/test-output/TestReportFinal.html", true);

        extent.addSystemInfo("Host Name", "TEST MACHINE" +" "+ System.getProperty("os.name"))
                .addSystemInfo("Environment", System.getProperty("os.name") + "_CHROME")
                .addSystemInfo("User Name", "TEST USER");

        System.out.println(PROJECTDIR);

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
        if (ITestResult.FAILURE == result.getStatus()) {
            String screenshotpath = getScreenshot(driver, result.getName());

            extentTest.log(LogStatus.FAIL, "Screenshot ",
                    result.getThrowable() + extentTest.addScreenCapture(screenshotpath)); // add screenshot to extent
                                                                                          // report
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentTest.log(LogStatus.SKIP, "Test case skipped is ", result.getName());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.log(LogStatus.PASS, "Test case - ", result.getName());
        }

        extent.endTest(extentTest);
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
        extent.close();

    }

    public static String getScreenshot(WebDriver driver, String screenshotName) {
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destination = System.getProperty("user.dir") + "/failedTestsScreenshots/" + screenshotName + "_"
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
        extentTest.log(LogStatus.FAIL, details);
    }

    public static void logPass(String details) {
        extentTest.log(LogStatus.PASS, details);
    }

    public static void logInfo(String details) {
        extentTest.log(LogStatus.INFO, details);
    }

}
