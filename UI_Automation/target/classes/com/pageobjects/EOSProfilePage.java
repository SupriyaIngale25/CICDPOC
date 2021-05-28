package com.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utils.CommonUtils;

public class EOSProfilePage extends CommonUtils {

	@FindBy(xpath = "//div[@class='title']/h1")
	static WebElement profileHeading;

	@FindBy(xpath = "//a[@class='btn btn-large btn-pink btn-action person-profile__zdclickscroll']")
	static WebElement BookAnApppointmentBtn;

	@FindBy(xpath = "//*[@class='person-profile__eos--root']")
	static WebElement EOSWidget;

	@FindBy(xpath = "//*[@id='schedule']/h3")
	static WebElement DocNameOnWidget;

	@FindBy(xpath = "//*[@id='pp-eos-page--select-ui']/div[1]/div[1]/label")
	static WebElement InPersonVisit;

	@FindBy(xpath = "//*[@id='pp-eos-page--select-ui']/div[1]/div[2]/label")
	static WebElement VideoVisit;

	@FindBy(xpath = "//*[@id='pp-eos-select--inperson']/select")
	static WebElement InPersonVisit_Type_Dropdown;

	@FindBy(xpath = "//*[@id='pp-eos-select--video']/select")
	static WebElement VideoVisit_Type_Dropdown;

	@FindBy(xpath = "//*[@id='pp-eos-button--submit']")
	static WebElement submitBtn;

	@FindBy(xpath = "//*[@id='pp-eos-select--inperson']/select/option[text()='FOLLOW UP']")
	static WebElement followUp;

	@FindBy(xpath = "//*[@id='pp-eos-select--inperson']/select/option[2]")
	static WebElement VisitType;
	@FindBy(xpath = "//div[@class='subslotslist am']//a")
	static List<WebElement> timeSlots_AM;

	@FindBy(xpath = "//div[@class='subslotslist pm']//a")
	static List<WebElement> timeSlots_PM;

	@FindBy(xpath = "//*[@id='D6F73C26-7627-4948-95EA-2C630C25C5E9_scheduleOpenings_OpeningsData']/div[1]//div[2]//a")
	static WebElement timeSlot;

	@FindBy(xpath = "//*[@id='pp-eos-button--restart']")
	static WebElement startOverBtn;

	@FindBy(xpath = "//div[@class='providerName']/span")
	static WebElement docNameOnApptDeatilsPage;

	@FindBy(xpath = "//*[@id='D6F73C26-7627-4948-95EA-2C630C25C5E9_scheduleOpenings_apptDetailsControlsContainer']/a[1]")
	static WebElement backBtnOnApptDetailsPage;

	@FindBy(xpath = "//a[@class='button scheduleaction completeworkflow']")
	static WebElement scheduleItBtnOnApptDetailsPage;

	@FindBy(xpath = "//div[@class='address']")
	static WebElement addressOnApptDetailsPage;

	@FindBy(xpath = "//div[@class='dateTime']")
	static WebElement dateTimeOnApptDetailsPage;

	@FindBy(xpath = "//*[@id='Comments']")
	static WebElement commentsTxtBoxOnApptDetailsPg;

	@FindBy(xpath = "//div[@class='comments']//label")
	static WebElement placeholderTxtOnApptDetailsPg;

	@FindBy(xpath = "//div[@class='comments']//span[@class=' helptext']")
	static WebElement helpTextOnApptDetailsPage;

	@FindBy(xpath = "//*[@id='0946B3AC-7639-4F83-94A9-25C30DD9C902_EmbeddedSchedule_detailsContainer']/h2")
	static WebElement isThatCorrectQueOnApptDetailsPage;

	@FindBy(xpath = "//*[@id='DFA7477E-2068-4DCA-99B0-794909E5892F_login_login']")
	static WebElement loginBtnOnMychartLoginPage;

	@FindBy(xpath = "//*[@id='DFA7477E-2068-4DCA-99B0-794909E5892F_login_Login']//h2")
	static WebElement queOnMychartLoginPage;

	@FindBy(xpath = "//*[@id='D6F73C26-7627-4948-95EA-2C630C25C5E9_scheduleOpenings_OpeningsData']/div[@class='errormessage']")
	static WebElement noOpenApptErrMsg;

	@FindBy(xpath = "//*[@id='pp-eos-select--inperson']/select/option[@value!='']")
	static List<WebElement> InPerson_visitTypeOptions;

	@FindBy(xpath = "//*[@id='pp-eos-select--video']/select/option[@value!='']")
	static List<WebElement> VideoVisit_visitTypeOptions;

	public EOSProfilePage(WebDriver driver) {

		PageFactory.initElements(driver, this);
	}


	public static boolean selectInpersonRadioBtn() throws InterruptedException{

		boolean flag =false;

		try{
			InPersonVisit.click();
			logInfo("*****Clicked on In Person Visit radio button*******");
			flag= true;
		}catch(Exception e){ flag=false; }
		return flag;
	}

	public static boolean selectVideoVisitRadioBtn() throws InterruptedException{
		boolean flag =false;

		try{
			VideoVisit.click();
			logInfo("*****Clicked on Video visit radio button*******");
			flag= true;
		}catch(Exception e){ flag=false; }

		return flag;

	}

	public static boolean CheckVisitTypeAvailableFor_Inperson() throws InterruptedException{

		try{
			waitAndClick(InPersonVisit_Type_Dropdown);

			logInfo("Total visit types available for In Person are : "+ InPerson_visitTypeOptions.size());
			if(InPerson_visitTypeOptions.size()>0) {
				click(InPersonVisit_Type_Dropdown);
				return true;
			}
			else{
				logFail("FAILURE  :: No Visit types are available for In Person");
				click(InPersonVisit_Type_Dropdown);
				takeFailSnapShot();
				return false;
			}

		}catch(Exception e){
			logFail("FAILURE  ::  There is No visit type Dropdown displayed for In Person Visit , Kindly check the source data");
			return false;
		}
	}

	public static boolean CheckVisitTypeAvailableFor_VideoVisit() throws Exception{


		try {
			waitForVisible(VideoVisit_Type_Dropdown);
			click(VideoVisit_Type_Dropdown);

			logInfo("Total visit types available for Video Visit are : "+ VideoVisit_visitTypeOptions.size());
			if(VideoVisit_visitTypeOptions.size()>0) {
				click(VideoVisit_Type_Dropdown);
				return true;
			}
			else{
				logFail("FAILURE  :: No Visit types are available for Video Visit");
				click(VideoVisit_Type_Dropdown);
				return false;
			}

		}catch(Exception e){
			logFail("FAILURE  :: There is No visit type Dropdown displayed for Video Visit , Kindly check the source data");
			takeFailSnapShot();
			return false;
		}
	}

	public static boolean CheckAndSelectTimeslot_Inperson() throws Exception{


		boolean flag1=false;
		boolean flag2=false;


		selectInpersonRadioBtn();
		waitAndClick(InPersonVisit_Type_Dropdown);
		int Options =InPerson_visitTypeOptions.size(), count=0;

		for (WebElement we : InPerson_visitTypeOptions) {

			String visitType= getText(we);

			click(we);
			submitBtn.isEnabled();
			submitBtn.click();

			count++;

			try {
				switchIframeByID("openSchedulingFrame");
				waitForVisible(timeSlot);
				flag1=true;
				logPass("Timeslots are available for In Person : "+visitType);
				break;
			} catch (Exception e) {
				logInfo("No Timeslots are available for In Person : "+visitType);

				if (isDisplayed(noOpenApptErrMsg))
					if (getText(noOpenApptErrMsg).toString()
							.contains("we couldn't find any open appointments")) {
						logInfo("No appointments are available, Message shown is  : "
								+ getText(noOpenApptErrMsg));
					}

				driver.switchTo().defaultContent();
				click(startOverBtn);

				if(count<Options){
					selectInpersonRadioBtn();
					click(InPersonVisit_Type_Dropdown);
				}
			}
		}

		if(flag1==true){
			flag2=selectTimeSlot();
		}

		return flag2;

	}


	public static boolean CheckAndSelectTimeslot_VideoVisit() throws Exception{
		boolean flag1=false;
		boolean flag2=false;
		int Options =VideoVisit_visitTypeOptions.size(), count=0;

		selectVideoVisitRadioBtn();
		click(VideoVisit_Type_Dropdown);

		for (WebElement we : VideoVisit_visitTypeOptions) {

			String visitType= getText(we);

			click(we);
			submitBtn.isEnabled();
			submitBtn.click();
			count++;

			try {
				switchIframeByID("openSchedulingFrame");
				waitForVisible(timeSlot);
				flag1=true;
				logPass("Timeslots are available for Video Visit : "+visitType);
				break;
			} catch (Exception e) {
				logInfo("No Timeslots are available for Video Visit : "+visitType);

				if (isDisplayed(noOpenApptErrMsg))
					if (getText(noOpenApptErrMsg).toString()
							.contains("we couldn't find any open appointments")) {
						logInfo("No appointments are available, Message shown is  : "
								+ getText(noOpenApptErrMsg));
					}

				driver.switchTo().defaultContent();
				click(startOverBtn);
				if(count<Options){
					selectVideoVisitRadioBtn();
					click(VideoVisit_Type_Dropdown);
				}
			}
		}

		if(flag1==true){

			flag2=selectTimeSlot();
		}

		return flag2;

	}

	public static boolean selectTimeSlot() throws Exception{
		boolean flag=false ;


		try {
			waitForVisible(timeSlot);
			takePassSnapShot();
			hoverOverAndClick(timeSlot);
			
			logPass("Verified : selected the appointment Time slot successfully");
			
			flag=true;
		} catch (Exception e) {
			logFail("FAILURE  ::  Could not select the appointment time slot");
			takeFailSnapShot();
		}  																				

		return flag;
	}

	public static void verifyEOSProfiles() throws Exception {

		PageFactory.initElements(driver, EOSProfilePage.class);
		PageFactory.initElements(driver, FindADoctorPage.class);

		int rowCount = datatable.getRowCount("EOS_Profiles");
		int totalProfiles = rowCount - 1;
		boolean slotsAvailable = false;

		logInfo("Total EOS Testing profiles are :  " + totalProfiles);

		for (int j = 2; j <= rowCount; j++) {

			boolean fadSearch = false, urlSearch = false;

			String lastName = datatable.getCellData("EOS_Profiles", 0, j);
			String firstName = datatable.getCellData("EOS_Profiles", 1, j);
			String url = datatable.getCellData("EOS_Profiles", 2, j);

			logInfo("*********Testing Profile Number " + (j - 1) + "*** LastName : " + lastName + " FirstName : "+ firstName + "*********");

			if (!lastName.isEmpty() && !firstName.isEmpty()) {
				if (j == 2) {
					FindADoctorPage.goToFADPage();
					FindADoctorPage.clickOnSearchByName();
				}

				fadSearch = FindADoctorPage.searchByFADFlow(lastName, firstName);

			} else if (!url.isEmpty()) {
				urlSearch = checkIfBrokenLink(url);
				if (urlSearch == true) {
					logPass("****Profile URL is correct****");
					navigateToURL(url);
				} else
					logFail("FAILURE  :: issue with Profile URL");

			} else
				logFail("FAILURE :: Check the Search data in input file if its proper");

			// hold all window handles in array list
			ArrayList<String> newTb = new ArrayList<String>(driver.getWindowHandles());

			if (fadSearch == true) {

				try {
					// switch to new tab
					driver.switchTo().window(newTb.get(1));
					// logInfo("Page title of new tab: " + getTitle());
				} catch (Exception e) {
					logFail("***FAILURE  :: Issue switching to New tab***");
				}

			}

			if (fadSearch == true || urlSearch == true) {

				String nameOnProfile;
				String nameOnWidget;

				if (getTitle().toString().toLowerCase().contains(lastName.toLowerCase())) {

					waitForVisible(FindADoctorPage.doctorProfilePageHeading);

					if (getText(FindADoctorPage.doctorProfilePageHeading).toLowerCase()
							.contains(firstName.toLowerCase())) {
						logPass("Verified : Valid Doctor Name is displayed in Profile Details");

						try {
							waitAndClick(BookAnApppointmentBtn);
							logPass("Verified : Book An Apppointment button is present on profile page");
						} catch (Exception e) {
							logFail("FAILURE :: Book An Apppointment button is not present on profile page");
						}

						if (isDisplayed(EOSWidget)) {
							logPass("******Verified : EOS Widget is present on the profile Page********");

							logInfo("Doctors Name displayed on Widget is : " + getText(DocNameOnWidget));
							takePassSnapShot();

							String[] s1 = getText(FindADoctorPage.doctorProfilePageHeading).toString().trim().split(",");
							String[] s2 = getText(DocNameOnWidget).toString().trim().split(" - ");
							String[] s3 = s2[1].split(",");

							nameOnProfile = s1[0].trim();
							nameOnWidget = s3[0].trim();

							if (nameOnProfile.equals(nameOnWidget))
								logPass("Verified : Name on profile Page matches to Doctors name displayed on EOS widget ");
							else
								logFail("FAILURE  :: Name on profile Page :  " + nameOnProfile+ "  Does not match to name displayed on EOS widget : " + nameOnWidget);

							boolean flag_Inperson1=selectInpersonRadioBtn();
							boolean flag_Inperson2=CheckVisitTypeAvailableFor_Inperson();
							boolean flag_VideoVisit1=selectVideoVisitRadioBtn() ;
							boolean flag_VideoVisit2=CheckVisitTypeAvailableFor_VideoVisit();

							if(flag_Inperson1 && flag_Inperson2 ){
								slotsAvailable =CheckAndSelectTimeslot_Inperson();
							}

							if(flag_VideoVisit1 && flag_VideoVisit2 && slotsAvailable ==false){
								slotsAvailable =CheckAndSelectTimeslot_VideoVisit();
							}

							if(slotsAvailable==true) {
								try {
									waitForVisible(docNameOnApptDeatilsPage);
									logPass("Verified : User is navigated to appointmets details page after selecting a time slot");
									takePassSnapShot();

									if (getText(docNameOnApptDeatilsPage).toString().toLowerCase()
											.contains(lastName.toLowerCase())
											&& getText(docNameOnApptDeatilsPage).toString().toLowerCase()
											.contains(firstName.toLowerCase())) {

										logPass("Verified : Doctor's name on appointment confirmation details page : "
												+ getText(docNameOnApptDeatilsPage));

										if (getText(backBtnOnApptDetailsPage).toString().equals("Back"))
											logPass("Verified :  Back button is present on the appointment details page");
										else
											logFail("FAILURE  ::  Back button is NOT present on the appointment details page");

										if (getText(scheduleItBtnOnApptDetailsPage).toString()
												.contains("Schedule it")) {

											logPass("Verified :  Schedule it button is present on the appointment details page");
											click(scheduleItBtnOnApptDetailsPage);

											waitForVisible(queOnMychartLoginPage);
											if (getText(queOnMychartLoginPage).toString()
													.contains("Have a MyChart account")) {
												logPass("Verified : User is navigated to my chart login page after clicking on Schedue it! Button");
												takePassSnapShot();
											} else
												logFail("FAILURE  ::  User is not navigated to my chart login page after clicking on Schedue it! Button");
										} else
											logFail("FAILURE  ::  Schedule it button is NOT present on the appointment details page");
									} else
										logFail("FAILURE  ::  Doctors Name on appointment confirmation is not correct!! "
												+ getText(docNameOnApptDeatilsPage).toString());
								} catch (Exception e) {
									logFail("FAILURE  ::  User is NOT navigated to appointmets confirmation page after selecting a time slot");
									takeFailSnapShot();
								}

							}//if time slot available
							else logFail("FAILURE  :: There are No Appointment Time slots available at all for Any of the visit types under In Person or Video Visit");


						} else {
							logFail("FAILURE  :: EOS Widget is not displayed on the profile page");
							takeFailSnapShot();
						}

					} else
						logFail("FAILURE  :: User is NOT redirected to the correct profile page");

					if (fadSearch == true) {
						try {
							driver.close();
							// switch to parent window
							driver.switchTo().window(newTb.get(0));
							// logInfo("Page title of parent window: " + getTitle());
						} catch (Exception e) {
							logFail("FAILURE :: Issue switching to main tab");
						}
					}
				}
			} // if search is successful

			Thread.sleep(2000);
		}
	}
}
