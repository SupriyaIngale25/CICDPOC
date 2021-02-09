package NotUsed_Classes;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class test{

public static void main(String[] args) throws InterruptedException {
    

            
    System.setProperty("webdriver.chrome.driver",
            System.getProperty("user.dir") + "/drivers/chromedriver.exe");
    
    WebDriver driver = new ChromeDriver();
    driver.get("https://www.mountsinai.org/");
    System.out.println(driver.getTitle());
    
    driver.manage().window().maximize();

    
    }

}



