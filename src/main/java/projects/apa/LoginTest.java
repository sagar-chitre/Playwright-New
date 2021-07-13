package projects.apa;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.rendering.PageDrawer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.aventstack.extentreports.util.Assert;
import com.microsoft.playwright.Page;

import excel.utils.ExcelUtil;
import ui.playwright.BaseTestClass;
import utils.ZipDir;
import utils.CreateTestExecution;
import utils.XrayExportResultJson;
@RunWith(JUnitPlatform.class)
public class LoginTest extends BaseTestClass {
	boolean screenprint =screenPrint(true);
	static String teststatus = null;
	
	@Test
	public void testExecution() throws Exception {
	CreateTestExecution.creatExecution();
	}
	
	@Test
	public void verifyLoginPage() throws Exception {

		// Below 2 lines should be called in each test.
		// Modify the testName as per test
		//String testName = "verifyLoginPage";
		String testName = "LOGN_0001";
		initializeExtent(testName);
		String teststatus = null;
		String testresult;
		int rowid = getrowid.getExcelRowID(testName);
		writeToExcel.WriteDataIntoExcelCell(rowid, 4, "");

		boolean screenprint =screenPrint(true);
		test = extent.createTest("LOGN_0001_Verify Login Page");

		// Fetch the Test Data for the test to be used
		String Execute = data.excelValueExtractor("LOGN_0001", "Execute");
		String uname=data.get("UserId");
		String pwd = data.get( "Password");
		String expectedURL = data.get("expectedHomePageName");
		String expectedLandingPageName = data.get( "expectedLandingPageName");
		String expPageTitle = data.get("PageTitle");
		//String testName = "LOGN_0001";
		
		//Below If / else should be copied for each test to ensure execution can be controlled via XL flag
		/*
		 * if(!(Execute.equals("ON"))) { pagedriver.close(); System.out.println("/n");
		 * System.out.println("Test Case not executed as the Execute flag is - " +
		 * Execute); } else {
		 */		// Verify UserName field is visible
		boolean verifyUsername = pagedriver.isVisible(objrep.fetch("username"));
		Assertions.assertEquals(true, verifyUsername);
		if (verifyUsername) {
			passedResult("User Name field is visible", /* false, */ testName);
		
		} else {
			failedResult("User Name field is not visible", testName);
		}

		// Verify Password field is visible
		boolean verifyPassword = pagedriver.isVisible(objrep.fetch("password"));
		Assertions.assertEquals(true, verifyPassword);
		if (verifyPassword) {
			passedResult("Password field is visible", /* false, */ testName);
		} else {
			failedResult("Password field is not visible",  testName);
		}

		// Verify Login button is visible
		boolean verifyLoginbutton = pagedriver.isVisible(objrep.fetch("loginbutton"));
		Assertions.assertEquals(true, verifyLoginbutton);
		if (verifyLoginbutton) {
			passedResult("Login button is visible", /* false, */ testName);
		} else {
			failedResult("Login button is not visible",testName);
		}

		// Enter User Name
		pagedriver.fill(objrep.fetch("username"), uname);
		actResult(uname + "- is entered as User Id", /* true, */ testName, screenprint);

		// Enter password
		pagedriver.fill(objrep.fetch("password"), pwd);
		actResult("A valid password is entered for the User Id", testName, screenprint);

		// Click the login button
		pagedriver.click(objrep.fetch("loginbutton"));
		pagedriver.waitForLoadState();
		String txtActualURL = pagedriver.url();

		Assertions.assertEquals(txtActualURL, expectedURL);
		if (expectedURL.equals(txtActualURL)) {
			passedResult(
					"The user navigate to an application selection page and user selects 1- Analiza", /* false, */
					testName);
		} else {
			failedResult("The user does not navigate to an application selection page.Exiting the test!",
					testName);
		}
		// Handling Pop-up or new page
		Page pgdriver1 = pagedriver.waitForPopup(new Runnable() {
			public void run() {
				try {
					pagedriver.click(objrep.fetch("Analiza"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		pgdriver1.waitForLoadState();
		String txtLandingPage = pgdriver1.url();	
		if (expectedLandingPageName.equals(txtLandingPage)) {
			passedResultContext("The user navigate to the portfolio landing page", /* false, */ testName, pgdriver1);
		} else {
			failedResultContext(
					"The user not navigated to the portfolio landing page. Exiting the test!", /* true, */
					testName, pgdriver1);
		}
		String txtActPageTitle = pgdriver1.title();
		if (expPageTitle.equals(txtActPageTitle)) {
			passedResultContext("The page title is correct:- " + txtActPageTitle, /* false, */ testName, pgdriver1);
		} else {
			failedResultContext("The page title is not correct:- " + txtActPageTitle, /* true, */ testName, pgdriver1);
		}
		pgdriver1.click(objrep.fetch("closewindow"));
		pgdriver1.close();

		extent.flush();
		verifySuccessfulLogout(testName, screenprint);
		
		teststatus = data.excelValueExtractor(testName, "TestStatus");
		if (!(teststatus.equals("fail"))) {
			testresult = "pass";
			} else 	{
			testresult = "fail";
			}

		// Update Xray and upload the Html results with Test Execution, the below code should be written in each test
		uploadxrayresults(testName, testresult);
	}

	// Verify Successful Login
	public Page verifySuccessfulLogin(String callingTest, String uname, String pwd, boolean screenprint) throws IOException {
		// Below lines should be called in each test.
		// Modify the testName as per test
		String testName = callingTest;
		test = extent.createTest("REUSABLE_Login");

		// Get the OR for page
		//String Execute = data.excelValueExtractor(testName, "Execute");
		
		String username = objrep.fetch("username");
		String password = objrep.fetch("password");
		String loginbutton = objrep.fetch("loginbutton");
		String Analiza = objrep.fetch("Analiza");
		String closewindow = objrep.fetch("closewindow");
		
		pagedriver.fill(username, uname);
		actResult(uname + "- is entered as User Id", /* true, */ testName, screenprint);

		// Enter password
		pagedriver.fill(password, pwd);
		actResult("A valid password is entered for the User Id", /* true, */ testName, screenprint);

		// Click the login button
		pagedriver.click(loginbutton);
		pagedriver.waitForLoadState();

		// Handling Pop-up or new page
		Page pgdriver1 = pagedriver.waitForPopup(new Runnable() {
			public void run() {
				pagedriver.click(Analiza);
			}
		});
		pgdriver1.waitForLoadState();
		pgdriver1.click(closewindow);
		return pgdriver1;
		//}
		//return null;
	}

	// Verify Successful Logout
	public void verifySuccessfulLogout(String callingTest, boolean screenprint) throws IOException {
		String testName = callingTest;
		test = extent.createTest("REUSABLE_Verify Successful_Logout");
		pagedriver.click(objrep.fetch("luisgarcia"));
		pagedriver.click(objrep.fetch("mainlogout"));
		pagedriver.waitForLoadState();
		// Verify Logout is successful is visible
		boolean verifyLogoutIsSuccessful = pagedriver.isVisible(objrep.fetch("username"));
		Assertions.assertEquals(true, verifyLogoutIsSuccessful);
		pagedriver.waitForSelector(objrep.fetch("username"));
		if (verifyLogoutIsSuccessful) {
			actResult("Logout is successful", /* true, */ testName, screenprint);
		} else {
			failedResult("Logout is not successful", testName);
		}
	}
}