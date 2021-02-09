package com.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.utils.CommonUtils;

public class HomePage extends CommonUtils {

    @FindBy(xpath = "//a/img[@alt='Mount Sinai']")
    static WebElement homeLogo;

static String pageTitle="Mount Sinai Health System - New York City | Mount Sinai - New York";

    public HomePage(WebDriver driver) {

        PageFactory.initElements(driver, this);
    }

    public static void navigateToUrl(String sheetName) throws InterruptedException {

        PageFactory.initElements(driver, HomePage.class);
        navigateToURL(datatable.getCellData(sheetName, 3, 2));
        logInfo("HomePage Loaded");

    }

    public static void verifyPageTitle() throws InterruptedException {
        PageFactory.initElements(driver, HomePage.class);
        waitForPageTitle(pageTitle);
        String actulPageTitle = getTitle();
        Assert.assertEquals(actulPageTitle,pageTitle);
        logPass("Page Title is : " + actulPageTitle);

    }

    public static void verifylogoRedirectionlink() throws InterruptedException {
        PageFactory.initElements(driver, HomePage.class);
        click(homeLogo);
        waitForPageLoaded();
        String actulPageTitle = getTitle();
        Assert.assertEquals(actulPageTitle, pageTitle);
        logPass("Page Title is : " + actulPageTitle );
        logPass("Verified the logo redirection to home page");

    }

    

}
