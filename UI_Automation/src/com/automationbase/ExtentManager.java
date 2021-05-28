package com.automationbase;

import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;

public class ExtentManager {
    
    private static ExtentReports extent;
    protected static final String PROJECTDIR = System.getProperty("user.dir");
    
    
    public static ExtentReports getInstance() {
    	String dateName = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    	if (extent == null)
    		createInstance(PROJECTDIR + "/test-output/TestReportFinal_"+dateName+".html");
    		
    	
        return extent;
    }
    
    public static ExtentReports createInstance(String fileName) {
    	
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(fileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(fileName);
        
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        
        return extent;
    }
}