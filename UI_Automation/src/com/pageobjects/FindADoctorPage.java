package com.pageobjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utils.CommonUtils;

public class FindADoctorPage extends CommonUtils {

	@FindBy(xpath = "//a/img[@alt='Mount Sinai']")
	static WebElement homeLogo;

	@FindBy(linkText = "Find a Doctor")
	static WebElement fad_Link;

	@FindBy(xpath = "//div[@class='container-fluid ']//form[1]/div[1]//ul/li[2]/label/span")
	static WebElement searchByName;

	@FindBy(xpath = "//div[@class='container-fluid ']//form[1]//input[@name='lastName']")
	static WebElement txtLastName;

	@FindBy(xpath = "//div[@class='container-fluid ']//form[1]//input[@name='firstName']")
	static WebElement txtFirstName;

	@FindBy(xpath = "//div[@class='container-fluid ']//form[1]//div[3]//button[text()='Go']")
	static WebElement btnGo;

	@FindBy(xpath = "//*[@id='resultSummary']/strong")
	static WebElement foundResultNum;

	@FindBy(xpath = "//*[@id='peopleResults']//div[1]//h1/a")
	static WebElement doctorProfileCardLink;

	@FindBy(xpath = "//div[@class='profile-header__container--outer']//h1")
	static WebElement doctorProfilePageHeading;

	@FindBy(xpath = "//div[@class='accordion-title']/a")
	static List<WebElement> accordionTitles;

	@FindBy(xpath = "//div[@class='accordion-title']/a[text()='Publications']")
	static WebElement publicationAccordion;

	@FindBy(xpath = "//img[@alt='plumX logo']")
	static WebElement plumX;

	@FindBy(xpath = "//div[@class='doctor-results']")
	static WebElement docResult;

	static String pageTitle = "Mount Sinai Health System - New York City | Mount Sinai - New York";

	public FindADoctorPage(WebDriver driver) {

		PageFactory.initElements(driver, this);
	}

	public static void navigateToUrl(String sheetName) throws InterruptedException {

		PageFactory.initElements(driver, FindADoctorPage.class);
		navigateToURL(datatable.getCellData(sheetName, 3, 2));
		logInfo("HomePage Loaded");

	}

	public static void goToFADPage() throws InterruptedException {
		PageFactory.initElements(driver, FindADoctorPage.class);
		waitAndClick(fad_Link);
		waitForVisible(searchByName);
	}

	public static void verifyVIPProfiles() throws InterruptedException, IOException {
		PageFactory.initElements(driver, FindADoctorPage.class);

		int rowCount = datatable.getRowCount("VIP_Profiles");
		int totalProfiles = rowCount - 1;

		logInfo("Total VIP profiles are " + totalProfiles);

		click(searchByName);

		for (int j = 2; j <= rowCount; j++) {

			String lastName = datatable.getCellData("VIP_Profiles", 0, j);
			String firstName = datatable.getCellData("VIP_Profiles", 1, j);

			txtLastName.clear();
			txtLastName.sendKeys(lastName);

			txtFirstName.clear();
			txtFirstName.sendKeys(firstName);
			Thread.sleep(1000);
			btnGo.submit();
			waitForVisible(foundResultNum);
			
			waitForVisible(docResult);
	
			if(getText(foundResultNum).equals("0")){
				logFail("0 results are found for doctor : "+lastName+" "+firstName);
			}

			if (getText(foundResultNum).equals("1")) {
				waitForElementToBeClickable(doctorProfileCardLink);
				boolean flag =checkIfBrokenLink(doctorProfileCardLink.getAttribute("href").toString());
				if(flag==true){
				click(doctorProfileCardLink);
				try {
					// hold all window handles in array list
					ArrayList<String> newTb = new ArrayList<String>(driver.getWindowHandles());
					// switch to new tab
					driver.switchTo().window(newTb.get(1));

					logInfo("Page title of new tab: " + getTitle());

					if (getTitle().toString().toLowerCase().contains(lastName.toLowerCase())) {
						waitForVisible(doctorProfilePageHeading);
						

						if (getText(doctorProfilePageHeading).toLowerCase().contains(firstName.toLowerCase())) {
							logPass("User is redirected to the correct profile : "+firstName+" "+lastName);

							if (getText(doctorProfilePageHeading).toLowerCase().contains("fuster")) {
								publicationAccordion.click();
								Thread.sleep(1000);
								if (isDisplayed(plumX)) {
									logPass("PlumX widget is loading for valentine fuster profile");
								} else
									logFail("PlumX widget is NOT loading for valentine fuster profile");
							}
						} else
							logFail("User is NOT redirected to the correct profile page");

						logInfo("There are " + accordionTitles.size() + " accordion's present");

//						for (WebElement we : accordionTitles) {
//							logInfo("accordion " + getText(we) + " is present");
//						}

						driver.close();
						// switch to parent window
						driver.switchTo().window(newTb.get(0));
						logInfo("Page title of parent window: " + getTitle());
					}
				} catch (Exception e) {
					logFail("Issue switching the tabs");
				}
			}
			}

		}

	}

}
