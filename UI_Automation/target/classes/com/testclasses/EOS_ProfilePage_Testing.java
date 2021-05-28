package com.testclasses;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automationbase.TestBase;
import com.pageobjects.HomePage;
import com.pageobjects.EOSProfilePage;

public class EOS_ProfilePage_Testing extends TestBase {

	@Test(dataProvider = "data-provider")
	public static void VerifyEOSProfiles(String testUrl) throws Exception {

		extentTest = extent.createTest("Verify EOS Profiles");

		logInfo("===== TestCase  VerifyEOSProfiles Started =====");
		HomePage.navigateToUrl(testUrl);
		EOSProfilePage.verifyEOSProfiles();
		logInfo("===== TestCase  VerifyEOSProfiles Ended =====");

	}

	@DataProvider(name = "data-provider")
	public Object[][] dpMethod() {

		String sheetName="TestURLs";
		//int rowCount = datatable.getRowCount(sheetName);

					return new Object[][] { { datatable.getCellData(sheetName, 0, 2) } };

	}

}
