package com.testclasses;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automationbase.TestBase;
import com.pageobjects.HomePage;
import com.pageobjects.FindADoctorPage;

public class FAD_Page_Testing extends TestBase {

	@Test(dataProvider = "data-provider")
	public static void VerifyVIPProfiles(String testUrl) throws InterruptedException, IOException {

		extentTest = extent.createTest("Verify VIP Profiles");

		logInfo("===== TestCase  VerifyVIPProfiles Started =====");
		HomePage.navigateToUrl(testUrl);
		HomePage.verifyPageTitle();
		FindADoctorPage.goToFADPage();
		FindADoctorPage.clickOnSearchByName();
		FindADoctorPage.verifyVIPProfiles();
		logInfo("===== TestCase  VeriffocusOnElementyVIPProfiles Load Ended =====");

	}


	@DataProvider(name = "data-provider")
	public Object[][] dpMethod() {

		String sheetName="TestURLs";
		//int rowCount = datatable.getRowCount(sheetName);

					return new Object[][] { { datatable.getCellData(sheetName, 0, 2) }};

	}

}
