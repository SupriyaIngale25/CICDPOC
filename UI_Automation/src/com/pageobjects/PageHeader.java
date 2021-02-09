package com.pageobjects;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utils.CommonUtils;

public class PageHeader extends CommonUtils {

    @FindBy(xpath = "//header//*[@class='blue-sky-bg']")
    static WebElement header;



    public PageHeader(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }



    public static boolean verifyPageHeader() throws InterruptedException {
        PageFactory.initElements(driver, PageHeader.class);
        waitForVisible(header);
        if (isDisplayed(header) ) {
            logPass("Header displayed On Top of the page ");
            return true;
        } else {
            logFail("Header is not displayed on the page ");
            return false;
        }

    }

    public static void isLogoPresent() throws InterruptedException {
        PageFactory.initElements(driver, PageHeader.class);
        PageFactory.initElements(driver, HomePage.class);
        waitForVisible(HomePage.homeLogo);
        if (isDisplayed(HomePage.homeLogo)) {
            logPass("Mount Sinai Logo is present on header of the page");
        } else {
            logFail("Mount Sinai Logo  is not displayed on the header of the page");
        }
    }



        
    

}
