package projects.apa;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Keyboard;
import com.microsoft.playwright.Page;

import ui.playwright.BaseTestClass;
import utils.ZipDir;
import utils.XrayExportResultJson;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

//@TestMethodOrder(OrderAnnotation.class)
@RunWith(JUnitPlatform.class)
public class DashboardPage extends BaseTestClass {
	LoginTest testLogin = new LoginTest();
	boolean screenprint = screenPrint(false);

	@Test
	//@Order(1)
	public void verifyDashBoardPage() throws Exception {
		// Modify the testName as per test
		String Execute = data.excelValueExtractor("DASH_0001", "Execute");
		//String testName = "verifyDashboardPage";
		String testName = "DASH_0001";
		boolean screenprint = screenPrint(true);
		// Initialize all Test Data from Excel Sheet
		String uname=data.get("UserId");
		String pwd=data.get("Password");
		
		initializeExtent(testName);
		Page pgdriver = testLogin.verifySuccessfulLogin(testName, uname, pwd, screenprint);

		String checkInputField = objrep.fetch("landingPageTextField");			
		test = extent.createTest("DASH_0001_Verify Dashboard Page");

		// Check if Label is visible or not
		boolean verifyInputLabel = pgdriver.isVisible(checkInputField);
		Assertions.assertEquals(true, verifyInputLabel);
		if (verifyInputLabel) {
			passedResultContext("Crear nuevo análisis para label is visible", testName, pgdriver);
		} else {
			failedResultContext("Crear nuevo análisis para label is not visible", testName, pgdriver);
		}

		// Check if Input field is visible or not
		boolean verifyInputField = pgdriver.isVisible(objrep.fetch("landingPageimage"));
		Assertions.assertEquals(true, verifyInputField);
		if (verifyInputLabel) {
			passedResultContext("Crear nuevo análisis para input field is visible", testName, pgdriver);
		} else {
			failedResultContext("Crear nuevo análisis para input field is not visible", testName, pgdriver);
		}

		// Check if Mostrar analisis realizados link is visible or not
		boolean verifyLink = pgdriver.isVisible(objrep.fetch("showanalysisperformed"));
		Assertions.assertEquals(true, verifyLink);
		if (verifyInputLabel) {
			pgdriver.hover(objrep.fetch("showanalysisperformed"));
			passedResultContext("Mostrar analisis realizados link is visible", testName, pgdriver);
		} else {
			failedResultContext("Mostrar analisis realizados link is not visible", testName, pgdriver);
		}

		pgdriver.close();

		testLogin.verifySuccessfulLogout(testName, screenprint);
		extent.flush();

		// Update Xray and upload the Html results with Test Execution, the below code should be written in each test
		uploadxrayresults(testName);	
		}

	
	@Test
	//@Order(2)
	public void verifyCreationOfNewPortfolio() throws Exception {

		// Modify the testName as per test
		//String testName = "verifyCreationOfNewPortfolio";
		String Execute = data.excelValueExtractor("CRET_0001", "Execute");
		String testName = "CRET_0001";
		boolean screenprint = screenPrint(true);
		// Initialize all Test Data from Excel Sheet
		String uname = data.get("UserId");
		String pwd = data.get("Password");
		String aliasName = data.get("aliasName") + System.currentTimeMillis();
		String ISIN = data.get("ISIN");
		String ISINName = data.get("ISIN_Name");
		String Amount = data.get("Amount");
			
		String[] arrOfAmt = Amount.split(",");
		String[] arrISINName = ISINName.split(",");
		initializeExtent(testName);
		Page pgdriver = testLogin.verifySuccessfulLogin(testName, uname, pwd, screenprint);

		test = extent.createTest("CRET_0001_Verify user is able to create a new analysis");

		// Check if Input field is visible or not
		boolean verifyInputField = pgdriver.isVisible(objrep.fetch("AntonioGonzalez"));
		Assertions.assertEquals(true, verifyInputField);
		if (verifyInputField) {
			passedResultContext("Crear nuevo análisis para input field is visible", testName, pgdriver);
			pgdriver.fill(objrep.fetch("AntonioGonzalez"), aliasName);
			actResultContext(aliasName + " -  is entered", testName, pgdriver, screenprint);
			pgdriver.click(objrep.fetch("ltocreate"));
			actResultContext("The user click Create button", testName, pgdriver, screenprint);

			pgdriver.waitForLoadState();
			
			//boolean verifyDepot = pgdriver.isVisible(objrep.fetch("lfind"));
			boolean verifyDepot = pgdriver.isEditable(objrep.fetch("lfind"));
			Assertions.assertEquals(true, verifyDepot);
			if (verifyDepot) {
				passedResultContext("The user navigate to Agregar un nuevo fondo  page", testName, pgdriver);
				String[] arrOfISIN = ISIN.split(",");
				int i = 0;
				for (String strISIN : arrOfISIN) {
					pgdriver.click(objrep.fetch("lfind"));
					//pgdriver.keyboard().type(strISIN);
					pgdriver.keyboard().type(strISIN, new Keyboard.TypeOptions().setDelay(100));;
					pgdriver.keyboard().press(" ");
					pgdriver.keyboard().press("Backspace");
					pgdriver.waitForSelector(objrep.fetch("eISIN") + arrISINName[i] + ".*/");
					pgdriver.waitForLoadState();
					pgdriver.press(objrep.fetch("lfind"), "ArrowDown");
					// Press Enter
					pgdriver.press(objrep.fetch("lfind"), "Enter");
					actResultContext("The user selected ISIN: - " + strISIN, testName, pgdriver, screenprint);
					pgdriver.fill("#nx-input-1", arrOfAmt[i]);
					actResultContext("The user entered units is: - " + arrOfAmt[i], testName, pgdriver, screenprint);
					pgdriver.click(objrep.fetch("ladd"));
					boolean checkISINAdded = pgdriver.isVisible("text=" + strISIN + "");
					Assertions.assertEquals(true, checkISINAdded);
					if (checkISINAdded) {
						pgdriver.hover(objrep.fetch("lrequestanalysis"));
						passedResultContext(strISIN + ":-ISIN added successfully.", testName, pgdriver);
					} else {
						failedResultContext("ISIN not added successfully.", testName, pgdriver);
					}
					pgdriver.waitForLoadState(null);
					i++;
				}
			} else {
				failedResultContext("The user is not navigated to Agregar un nuevo fondo  page", testName,
						pgdriver);
			}
			pgdriver.click(objrep.fetch("lrequestanalysis"));
			pgdriver.waitForSelector(objrep.fetch("bnxdropbutton"));
			//boolean checkPortfolio = pgdriver.isVisible("text=" + aliasName + "");
			boolean checkPortfolio = pgdriver.isEnabled("text=" + aliasName + "");
			Assertions.assertEquals(true, checkPortfolio);
			if (checkPortfolio) {
				pgdriver.hover("text=" + aliasName + "");
				passedResultContext("The porfolio with name- " + aliasName + " - created successfully", testName,
						pgdriver);
				// write the Alias Name to be searched in next test case
				getWriteToExcel().WriteDataIntoExcelCell(4, 11, aliasName);
			} else {
				failedResultContext("The porfolio not created successfully", testName, pgdriver);
			}
		} else {
			failedResultContext("Crear nuevo análisis para input field is not visible", testName, pgdriver);
		}
		pgdriver.close();

		testLogin.verifySuccessfulLogout(testName, screenprint);
		extent.flush();

		// Update Xray and upload the Html results with Test Execution, the below code should be written in each test
		uploadxrayresults(testName);
	}

	@Test
	//@Order(3)
	public void verifySearchPortfolioUsingName() throws Exception {
		// Below 2 lines should be called in each test.
		// Modify the testName as per test
		//String testName = "verifySearchPortfolioUsingName";
		String Execute = data.excelValueExtractor("SEAR_0001", "Execute");
		String testName = "SEAR_0001";

		// Initialize all Test Data from Excel Sheet
		String uname= data.get("UserId");
		String pwd = data.get("Password");
		String aliasName = data.get("aliasName");

		initializeExtent(testName);
		Page pgdriver = testLogin.verifySuccessfulLogin(testName, uname, pwd, screenprint);
		test = extent.createTest("SEAR_0001_Verify Search of an existing Portfolio Analysis using an Alias");

		// Check if Input field is visible or not
		boolean verifyLink = pgdriver.isVisible(objrep.fetch("showanalysisperformed"));
		Assertions.assertEquals(true, verifyLink);
		if (verifyLink) {
			passedResultContext("Mostrar analisis realizados link is visible", testName, pgdriver);
			pgdriver.click(objrep.fetch("showanalysisperformed"));
			pgdriver.waitForSelector(objrep.fetch("bnxdropbutton"));
			pgdriver.fill(objrep.fetch("lbuscaraliasdecliente"), aliasName);
			pgdriver.click(objrep.fetch("ibuscarcliente"));
			pgdriver.waitForSelector("text=" + aliasName);
			boolean verifySearch = pgdriver.isVisible("text=" + aliasName);
			Assertions.assertEquals(true, verifySearch);
			if (verifySearch) {
				passedResultContext(aliasName + "- dislayed in the search result", testName, pgdriver);
			} else {
				failedResultContext(aliasName + "- is not dislayed in the search result", testName, pgdriver);
			}

		} else {
			failedResultContext("Mostrar analisis realizados link is not visible", testName, pgdriver);
		}
		pgdriver.close();

		testLogin.verifySuccessfulLogout(testName, screenprint);
		extent.flush();

		// Update Xray and upload the Html results with Test Execution, the below code should be written in each test
		uploadxrayresults(testName);
	}
	
	@Test
//	@Order(4)
	public void verortfolioUsingStatus() throws Exception {

		// Modify the testName as per test
		// Initialize all Test Data from Excel Sheet
		String Execute = data.excelValueExtractor("SEAR_0002", "Execute");
		String testName = "SEAR_0002";
		
		String uname= data.get("UserId");
		String pwd = data.get("Password");
		String status = data.get("Status");

		initializeExtent(testName);
		Page pgdriver = testLogin.verifySuccessfulLogin(testName, uname, pwd, screenprint);
		test = extent.createTest("SEAR_0002_Verify Search of an existing Portfolio Analysis using  Estad");
		// Check if Input field is visible or not
		boolean verifyLink = pgdriver.isVisible(objrep.fetch("showanalysisperformed"));
		Assertions.assertEquals(true, verifyLink);
		if (verifyLink) {
			passedResultContext("Mostrar analisis realizados link is visible", testName, pgdriver);
			pgdriver.click(objrep.fetch("showanalysisperformed"));
			pgdriver.waitForSelector(objrep.fetch("bnxdropbutton"));
			pgdriver.click(objrep.fetch("bnxdropbutton"));
			pgdriver.click(objrep.fetch("ialiasName") + status + "");
			pgdriver.click(objrep.fetch("ibuscarcliente"));
			pgdriver.waitForSelector(objrep.fetch("itextwithallias") + status + "");
			ElementHandle eleFilter = pagedriver.querySelector(objrep.fetch("itextwithallias") + status + "");
			boolean verifyStatus = true;
			if (eleFilter != null) {
				verifyStatus = false;
			}
			Assertions.assertEquals(true, verifyStatus);
			if (verifyStatus) {
				passedResultContext("Alias with status - " + status + " - dislayed in the search result",
						testName, pgdriver);
			} else {
				failedResultContext("Alias with status - " + status + " - is not dislayed in the search result",
						testName, pgdriver);
			}
		} else {
			failedResultContext("Mostrar analisis realizados link is not visible", testName, pgdriver);
		}
		pgdriver.close();

		testLogin.verifySuccessfulLogout(testName, screenprint);
		extent.flush();

		// Update Xray and upload the Html results with Test Execution, the below code should be written in each test
		uploadxrayresults(testName);		
		}

	public List<String> getVisibleAliasinfo(Page pgdriver, String selector) {
		return pgdriver.querySelectorAll(selector).stream().map(e -> e.innerText()).collect(Collectors.toList());
	}
}