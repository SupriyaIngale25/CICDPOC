package StepsDefinition;

import org.openqa.selenium.By;		
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import cucumber.api.java.en.Given;		
import cucumber.api.java.en.Then;		
import cucumber.api.java.en.When;	

public class Steps {				

     
WebDriver driver;		
static String pageTitle="Mount Sinai Health System - New York City | Mount Sinai - New York";
	
    @Given("^Open the Chrome and launch the application$")					
    public void open_the_Chrome_and_launch_the_application() throws Throwable							
    {		
    	
    	  System.out.println("Open the Chrome and launch the application.");	
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/driver/chromedriver.exe");
        driver= new ChromeDriver();					
       driver.manage().window().maximize();			
       driver.get("https://www.mountsinai.org/");					
    }		

    @When("^Click On Page Logo$")					
    public void click_On_Page_Logo() throws Throwable 							
    {
    	 System.out.println("Click on home page logo.");
       driver.findElement(By.xpath("//a/img[@alt='Mount Sinai']")).click();
            							
    }		

    @Then("^Verify Page title$")					
    public void verify_Page_title() throws Throwable 							
    {		

        System.out.println("Verify Page title.");					
        String actulPageTitle = driver.getTitle();
        Assert.assertEquals(actulPageTitle,pageTitle);
        System.out.println("Page Title is : " + actulPageTitle );
        System.out.println("Verified the logo redirection to home page");
    }		
}		