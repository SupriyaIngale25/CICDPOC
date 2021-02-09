package com.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utils.CommonUtils;


public class PageFooter extends CommonUtils {

    @FindBy(xpath = "//footer")
    static WebElement footer;

    @FindBy(xpath = "//footer//a")
    static List<WebElement> footerLinks;

   

    public PageFooter(WebDriver driver) {

        PageFactory.initElements(driver, this);
    }

    public static boolean verifyPageFooter() throws InterruptedException {
        PageFactory.initElements(driver, PageFooter.class);
        if (isDisplayed(footer)) {
            logPass("Footer Is present on the page");
            return true;
        } else {
            logFail("Footer is not displayed on the page");
            return false;
        }
    }


}
