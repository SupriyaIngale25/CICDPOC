package com.testclasses;


import org.testng.annotations.Test;

import com.automationbase.TestBase;
import com.pageobjects.HomePage;
import com.pageobjects.PageFooter;
import com.pageobjects.PageHeader;

public class HomePageTesting extends TestBase {

    @Test(priority = 1)
    public static void HomePageLoadTest() throws InterruptedException {

        extentTest = extent.createTest("Verify HomePageLoad");

        logInfo("===== TestCase  TestHomePageLoad Started =====");
        HomePage.navigateToUrl("LoginTest");
        HomePage.verifyPageTitle();
        logInfo("===== TestCase  Test HomePage Load Ended =====");

    }

    @Test(priority = 2)
    public static void generalVerificationHomePage() throws InterruptedException {

        extentTest = extent.createTest("Verify Header,Footer, searchbox and logo is Present On Home Page");
        logInfo("===== TestCase  General Verification Home Page Started =====");
        PageFooter.verifyPageFooter();
        PageHeader.verifyPageHeader();
        PageHeader.isLogoPresent();
        HomePage.verifylogoRedirectionlink();
        logInfo("===== TestCase  General Verification Home Page Ended =====");

    }

}
