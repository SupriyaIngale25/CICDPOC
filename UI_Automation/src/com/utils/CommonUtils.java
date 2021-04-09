package com.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import com.automationbase.TestBase;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

public class CommonUtils extends TestBase {

    protected static final String EXPLICIT = config.getProperty("ExplicitTimeout");
    protected static final String IMPLICIT = config.getProperty("implicitTimeout");
    protected static final String LOADING = config.getProperty("LoadingTimeout");
    static String OS = System.getProperty("os.name").toLowerCase();
    /*
     * Method : openBrowser Description : launch the browser and maximize the
     */

    public static void openBrowser(String browser) throws IOException {

        System.setProperty("http.maxRedirects", "999");

        if (browser.equalsIgnoreCase("chrome")) {

            // ChromeOptions options = new ChromeOptions();
            // if (isWindows()) {
            // System.out.println("This is Windows OS");
            //
            // System.setProperty("webdriver.chrome.driver",
            // System.getProperty("user.dir") + "/drivers/chromedriver_win.exe");
            //
            // } else if (isMac()) {
            // System.out.println("This is Mac OS");
            //
            // System.setProperty("webdriver.chrome.driver",
            // System.getProperty("user.dir") + "/drivers/chromedriver_mac");
            //
            // } else if (isUnix()) {
            // System.out.println("This is Unix or Linux OS");
            //
            // System.setProperty("webdriver.chrome.driver",
            // System.getProperty("user.dir") + "/drivers/chromedriver_linux");
            //
            // } else {
            // System.out.println("Your OS is not support!!");
            // }

            // // Code to run in headless browser mode
//        	ChromeOptions options= new ChromeOptions();
//             options.addArguments("window-size=1920x1080");// to open browser window in
//            //// maximise screen
//             options.addArguments("--headless"); // running chrome in headless mode
//             options.addArguments("--disable-dev-shm-usage"); // overcome limited resource
//             //problems
//             options.addArguments("--no-sandbox"); // Bypass OS security model
//             options.addArguments("disable-infobars");
//             System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver.exe");
//             driver = new ChromeDriver(options);

            // Code to run in normal browser UI mode
        	ChromeOptions chromeOptions= new ChromeOptions();//--
        	///chromeOptions.setBinary("C:/Users/supriya_ingale/AppData/Local/Google/Chrome/Application/chrome.exe");//--
           System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver.exe");
            System.setProperty("http.maxRedirects", "999");
            driver = new ChromeDriver(chromeOptions);//--
            driver.manage().window().maximize();


        }

        driver.manage().timeouts().implicitlyWait(Integer.parseInt(IMPLICIT), TimeUnit.SECONDS);

    }

    public static boolean isWindows() {

        return (OS.indexOf("win") >= 0);

    }

    public static boolean isMac() {

        return (OS.indexOf("mac") >= 0);

    }

    public static boolean isUnix() {

        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);

    }

    public static void waitForPageLoaded() {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
                        .equals("complete");
            }
        };
        try {
            Thread.sleep(2000);
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(expectation);
        } catch (Throwable error) {
            logFail("Timeout waiting for Page Load Request to complete. " + error.getMessage());
        }
    }

    public static void clickOnLink(String link) {

        int time = Integer.parseInt(EXPLICIT);
        expWait = new WebDriverWait(driver, time);
        expWait.until(ExpectedConditions.elementToBeClickable((By.linkText(link))));
        driver.findElement(By.linkText(link)).click();
    }

    public static WebElement getElementByLink(String s) {
        return driver.findElement(By.linkText(s));
    }

    public static void gotoUrl(String url) {
        driver.navigate().to(url);
    }

    public static boolean checkIfBrokenLink(String url) throws InterruptedException, IOException {

        HttpURLConnection huc = null;
        int respCode = 200;
        
        
        try {
        	huc = (HttpURLConnection) (new URL(url).openConnection());
            huc.setRequestMethod("HEAD");
            huc.connect();
            respCode = huc.getResponseCode();
            
        } catch (Exception e) {
            return false;
        }
        if (respCode == 404) {
            logFail("Its a broken url :  " + url);
            return false;
        } else {
            logPass("Its a Valid url");
            return true;
        }

    }

    /*
     * Method : navigateToURL Description : load the URL in the browser
     */
    public static void navigateToURL(String urlKey) throws InterruptedException {

        driver.get(urlKey);
    }

    public static void navigateBack() throws InterruptedException {

        driver.navigate().back();
    }

    public static void navigateForward() throws InterruptedException {

        driver.navigate().forward();
    }

    public static void windowRefresh() throws InterruptedException {

        driver.navigate().refresh();
        ;
    }

    public static void waitAndClick(final WebElement elem) throws InterruptedException {

        int time = Integer.parseInt(EXPLICIT);
        expWait = new WebDriverWait(driver, time);
        expWait.until(ExpectedConditions.elementToBeClickable(elem));

        click(elem);
    }

    public static void waitForVisibilityOfAllElements(final List<WebElement> elem) throws InterruptedException {

        int time = Integer.parseInt(EXPLICIT);
        expWait = new WebDriverWait(driver, time);
        expWait.until(ExpectedConditions.visibilityOfAllElements(elem));

    }

    /*
     * Method : click Description : click on an element(button) on the homepage
     */
    public static void click(final WebElement elem) throws InterruptedException {

        try {
            int time = Integer.parseInt(EXPLICIT);
            new WebDriverWait(driver, time).ignoring(WebDriverException.class, StaleElementReferenceException.class)
                    .until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver driver) {
                            elem.click();

                            return true;
                        }
                    });

        } catch (NullPointerException e) {
            logFail("NullPointerException" + e.getMessage());
        }
    }

    /*
     * Method : currentwindow Description : get the current window
     */
    public static void currentWindow() {

        currentWindow = driver.getWindowHandle();
    }

    /*
     * Method : gettitle Description : get the page title
     */
    public static String getTitle() {

        return driver.getTitle();
    }

    public static String getUrl() {

        String url = driver.getCurrentUrl();
        return url;
    }

    public static String getFirstWord(String s) {

        String mystring = s;

        String[] words = mystring.split(" ");

        String first = words[0];

        return first;
    }

    public static String[] getWords(String s) {

        String mystring = s;

        String[] words = mystring.split(" ");

        return words;
    }

    public static String getDigitsOnly(String input) {
        String x = input.replaceAll("[,]", ".");
        String numberOnly = x.replaceAll("[^0-9,^.]", "");
        return numberOnly;
    }

    /*
     * Method : getText Description : get text from a webelement
     */
    public static String getText(final WebElement elem) {

        int time = Integer.parseInt(EXPLICIT);

        return new WebDriverWait(driver, time).ignoring(StaleElementReferenceException.class)
                .until(new ExpectedCondition<String>() {

                    public String apply(WebDriver driver) {

                        return elem.getText();
                    }
                });

    }

    public static Boolean isDisplayed(final WebElement we) {
        try {
            //waitForVisible(we);
            if (we.isDisplayed()) {
                return true;
            } else
                return false;
        } catch (Exception e) {
            return false;
        }

    }

    public static boolean checkEqual(List<String> current, List<String> changed) {
        // TODO Auto-generated method stub
        // Check length
        if (current.size() != changed.size()) {
            return false;
        } else {
            for (int i = 0; i < current.size(); i++) {
                if (!current.get(i).equals(changed.get(i)))
                    return false;
            }
        }
        return true;
    }

    /*
     * Method : getValue Description : get attribute value from a webelement
     */
    public static String getValue(final WebElement elem) {

        int time = Integer.parseInt(EXPLICIT);
        return new WebDriverWait(driver, time).ignoring(StaleElementReferenceException.class)
                .until(new ExpectedCondition<String>() {

                    public String apply(WebDriver driver) {
                        return elem.getAttribute("value");

                    }
                });

    }

    /*
     * Method : verifyText Description : Verify the actual text from a webelement
     * with the expected text
     */
    public static boolean verifyText(WebElement elem, String expectedText) {

        String actualText = getText(elem);
        if (actualText.equalsIgnoreCase(expectedText)) {
            return true;
        }
        return false;
    }

    /*
     * Method : isElementPresent Description : Verify that a webelement is displayed
     */
    public static boolean isElementPresent(WebElement elem) {

        if (elem.isDisplayed()) {
            return true;
        }
        return false;
    }

    /*
     * Method : checkCheckBox Description : Select a check Box located by the
     * webelement
     */
    public static void checkCheckBox(WebElement elem) throws InterruptedException {

        if (!elem.isSelected()) {
            click(elem);
        }
    }

    /*
     * Method : uncheckCheckBox Description : unselect a check Box located by the
     * webelement
     */
    public static void uncheckCheckBox(WebElement elem) throws InterruptedException {

        if (elem.isSelected()) {
            click(elem);
        }
    }

    /*
     * Method : currentWindowHandler Description : Switch to the CurrentWindow from
     * the set of window handles
     */
    public static void currentWindowHandler() {

        Set<String> allWindowHandles = driver.getWindowHandles();
        for (String currentWindowHandle : allWindowHandles) {
            driver.switchTo().window(currentWindowHandle);
        }
    }

    public static void switchBackToMainContent() {
        driver.switchTo().defaultContent();
    }

    public static void closeTab() {
        driver.close();
    }

    public static String switchTabsGetNewTitle(String link) throws InterruptedException {

        // Get the current window handle
        String mainwindowHandle = driver.getWindowHandle();

        String title = null;

        // Get the list of window handles
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

        // Use the list of window handles to switch between windows

        if (tabs.size() > 1) {
            try {
                driver.switchTo().window(tabs.get(1));
                logPass("Switched to new tab successfully");
            } catch (Exception e) {
                logFail("Issue while switching to new tab");
            }
            title = getTitle();
            if (title.contains("Redirecting to")) {
                int time = Integer.parseInt(EXPLICIT);
                expWait = new WebDriverWait(driver, time);
                expWait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("redirectPage")));
                // waitForPageLoaded();
                String currentUrl = getUrl();
                if (checkExternalUrl(currentUrl))
                    logPass("Its an external Marchants URL");
                else
                    logFail("Its an internal URL");
                title = getTitle();
            } else 
                logPass(link + "  is redirecting to : " + getTitle());
            

        }

        else
            logFail("Could not switch to new tab");

        // close new tab
        driver.close();

        // Switch back to original window
        driver.switchTo().window(mainwindowHandle);

        return title;

    }

     public static void switchTabs() throws InterruptedException {
    
			// hold all window handles in array list
			ArrayList<String> newTb = new ArrayList<String>(driver.getWindowHandles());
			//switch to new tab
			driver.switchTo().window(newTb.get(1));
			System.out.println("Page title of new tab: " + getTitle());
    
     }
     public static void CloseTabsSwitchBack() throws InterruptedException {
    	    
		

    
     }
    public static void SwitchBacktoMainWindow() throws InterruptedException {

        // Get the current window handle
        String mainwindowHandle = driver.getWindowHandle();

        // Switch back to original window
        driver.switchTo().window(mainwindowHandle);

    }

    public static boolean checkExternalLink(WebElement we) throws InterruptedException {
        String externallink = we.getAttribute("href");
        if ((externallink.toLowerCase().contains("shopping.com")) || (externallink.toLowerCase().contains("sdc."))) {
            // logInfo("Redirecting to external link : "+externallink);
            return false;
        } else {
            // logInfo("NOT redirecting to external link");
            return true;
        }

    }

    public static boolean checkExternalUrl(String url) throws InterruptedException {

        if ((url.toLowerCase().contains("shopping.com")) || (url.toLowerCase().contains("sdc."))) {
            // logInfo("Redirecting to external link : "+externallink);
            return false;
        } else {
            // logInfo("NOT redirecting to external link");
            return true;
        }

    }

    public static void findbyUrlandClick(String url) throws InterruptedException {

        WebElement we = driver.findElement(
                By.xpath("//a[@href='" + url.replace(datatable.getCellData("LoginTest", 3, 2), "") + "']"));
        click(we);
    }

    public static boolean checkNewTab(WebElement we) {

        String newtab = we.getAttribute("target");
        if (newtab.contains("_blank")) {
            // log.info("link will open in new tab!!");
            return true;
        } else {
            // log.info("link will NOT open in new tab");
            return false;
        }

    }

    /*
     * Method : hover Description : Hover over the webelement an click on the other
     * webelement
     */
    public static void hoverAndClick(WebElement elem, WebElement clickpath) throws InterruptedException {

        Actions act = new Actions(driver);
        act.moveToElement(elem).perform();
        click(clickpath);

    }

    public static void hoverOver(WebElement elem) throws InterruptedException {

        Actions act = new Actions(driver);
        act.moveToElement(elem).perform();

    }

    /*
     * Method : implicitewait Description : Set implicit wait between webelements
     */
    public static void implicitewait() {

        int time = Integer.parseInt(IMPLICIT);
        driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
    }

    /*
     * Method : waitforPageTitle Description : wait till a page is loaded
     */
    public static void waitForPageTitle(String title) {

        int time = Integer.parseInt(EXPLICIT);
        expWait = new WebDriverWait(driver, time);
        expWait.until(ExpectedConditions.titleIs(title));
    }

    /*
     * * Method : waitforelementclick Description : wait for the element to be
     * clicked
     */
    public static void waitForElementToBeClickable(WebElement we) {

        int time = Integer.parseInt(EXPLICIT);
        expWait = new WebDriverWait(driver, time);

        try {
            expWait.ignoring(WebDriverException.class, StaleElementReferenceException.class)
                    .until(ExpectedConditions.elementToBeClickable(we));
        } catch (NullPointerException e) {
            logFail("NullPointerException" + e.getMessage());
        }

    }

    public static boolean isClickable(WebElement we) {

        try {
            if (isDisplayed(we) && we.isEnabled()) {
                return true;
            } else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * Method : waitforvisible Description : wait for the element to be visible
     */
    public static void waitForVisible(WebElement we) {

    	 int time = Integer.parseInt(EXPLICIT);
         expWait = new WebDriverWait(driver, time);

         try {
             expWait.ignoring(WebDriverException.class, StaleElementReferenceException.class)
                     .until(ExpectedConditions.visibilityOf(we));
         } catch (NullPointerException e) {
             logFail("NullPointerException" + e.getMessage());
         }
    }

    /*
     * Method : selectbytext
     */
    public static void selectByText(final WebElement elem, final String text1) {

        try {
            int time = Integer.parseInt(EXPLICIT);

            if (!text1.isEmpty()) {
                new WebDriverWait(driver, time).ignoring(StaleElementReferenceException.class, WebDriverException.class)
                        .until(new ExpectedCondition<Boolean>() {

                            public Boolean apply(WebDriver driver) {
                                Select dp = new Select(elem);

                                for (final WebElement text : dp.getOptions()) {
                                    String actual = text.getText().trim().toLowerCase();
                                    logInfo(actual);
                                    String expected = text1.trim().toLowerCase();
                                    if (actual.equalsIgnoreCase(expected)) {
                                        text.click();

                                        logInfo("Dropdown value" + text.getText());
                                        break;
                                    }
                                }

                                return true;
                            }
                        });

            }

        } catch (NullPointerException e) {

            logFail("Exception" + e.getMessage());
        }
    }

    /*
     * Method : selectbyvisibletext
     */
    public static void selectbyVisibleText(final List<WebElement> elem, final String text1) {

        try {
            int time = Integer.parseInt(EXPLICIT);

            if (!text1.isEmpty()) {
                new WebDriverWait(driver, time).ignoring(StaleElementReferenceException.class, WebDriverException.class)
                        .until(new ExpectedCondition<Boolean>() {

                            public Boolean apply(WebDriver driver) {

                                for (WebElement we : elem) {

                                    if (we.getText().toLowerCase().contains(text1)) {
                                        logPass("Clicked on filter: "+getText(we));
                                        we.click();
                                        break;
                                    }
                                }

                                return true;
                            }
                        });

            }

        } catch (NullPointerException e) {
            logFail("Exception" + e.getMessage());
        }
    }

    /*
     * Method : roundUpValue
     */
    public static String roundUpValue(String value) {

        String result;
        if (!value.isEmpty() && !value.equals("0")) {
            double v = Double.parseDouble(value);
            double rounded = (double) Math.round(v * 100) / 100;
            result = String.format("%.2f", rounded);
            return result;
        } else {
            return value;
        }
    }

    /*
     * Method : checkANDUncheckBox
     */
    public static void checkANDUncheckBox(WebElement elem, String status) throws InterruptedException {

        if (status.equals("1")) {
            checkCheckBox(elem);
        } else {
            uncheckCheckBox(elem);
        }
    }

}
