package ui.playwright;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;

import excel.utils.ExcelUtil;
import excel.utils.ExcelUtilInput;
import excel.utils.WriteToExcel;
import utils.FileCopy;
import utils.ReadPropertiesFile;
import utils.XrayExportResultJson;
import utils.ZipDir;

public class BaseTestClass extends driverFactory {
	Logger log = LogManager.getLogger(BaseTestClass.class);
	protected static Page pagedriver;
	public static boolean flag = true;
	protected static ExtentSparkReporter report;
	protected static ExtentReports extent;
	protected static ExtentTest test;
	ReadPropertiesFile rp = new ReadPropertiesFile();
	protected ExcelUtil data;
	// public FileCopy f;

	public ExcelUtil data() {
		return data;
	}

	private WriteToExcel writeToExcel;
	public ExcelUtilInput objrep;
	boolean screenprint = false;

	public BaseTestClass() {
		// super();
		// f = new FileCopy();
		// f.deleteFile(new File("./Result"));
		this.setExcelUtil(new ExcelUtil());
		this.setWriteToExcel(new WriteToExcel());
		this.setFetchOR(new ExcelUtilInput());
	}

	public ExcelUtilInput OR() {
		return objrep;
	}

	public void setFetchOR(ExcelUtilInput fetchOR) {
		this.objrep = fetchOR;
	}

	public void setExcelUtil(ExcelUtil data) {
		this.data = data;
	}

	public WriteToExcel getWriteToExcel() {
		return writeToExcel;
	}

	public void setWriteToExcel(WriteToExcel writeToExcel) {
		this.writeToExcel = writeToExcel;
	}

	public void setExcelUtilInput(ExcelUtilInput fetchOR) {
		this.objrep = fetchOR;
	}

	public boolean screenPrint(boolean take) {
		return screenprint = take;
	}

	// use passeResults when pagedrive context is current window. If a new window is
	// opened used passedResultContext
	public void passedResult(String actualResult, String foldername) throws IOException {
		if ((rp.readPropertiesFile("screenprintsneededforall").equals("yes")) || screenprint == true) {
			String filename = System.currentTimeMillis() + ".png";
			// TakescreenShot=true;
			String extentReportImage = System.getProperty("user.dir") + "/Result/" + foldername + "/Screenshots/"
					+ filename;
			String imgAbsolutePath = "Screenshots/" + filename;
			pagedriver.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(extentReportImage)).setFullPage(true));
			test.log(Status.PASS, actualResult,
					MediaEntityBuilder.createScreenCaptureFromPath(imgAbsolutePath).build());
		} else {
			test.log(Status.PASS, actualResult);
		}
	}

	public void passedResultContext(String actualResult, String foldername, Page pgdriver) throws IOException {
		String filename = System.currentTimeMillis() + ".png";
		String extentReportImage = System.getProperty("user.dir") + "/Result/" + foldername + "/Screenshots/"
				+ filename;
		String imgAbsolutePath = "Screenshots/" + filename;
		if ((rp.readPropertiesFile("screenprintsneededforall").equals("yes")) || (screenprint) == true) {
			pgdriver.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(extentReportImage)).setFullPage(true));
			test.log(Status.PASS, actualResult,
					MediaEntityBuilder.createScreenCaptureFromPath(imgAbsolutePath).build());
		} else {
			test.log(Status.PASS, actualResult);
		}
	}

	// use failedResult when pagedrive context is current window. If a new window is
	// opened used failedResultContext
	public void failedResult(String actualResult, String foldername) {
		String filename = System.currentTimeMillis() + ".png";
		String extentReportImage = System.getProperty("user.dir") + "/Result/" + foldername + "/Screenshots/"
				+ filename;
		String imgAbsolutePath = "Screenshots/" + filename;
		pagedriver.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(extentReportImage)).setFullPage(true));
		test.log(Status.FAIL, actualResult, MediaEntityBuilder.createScreenCaptureFromPath(imgAbsolutePath).build());
	}

	public void failedResultContext(String actualResult, String foldername, Page pgdriver) {
		String filename = System.currentTimeMillis() + ".png";
		String extentReportImage = System.getProperty("user.dir") + "/Result/" + foldername + "/Screenshots/"
				+ filename;
		String imgAbsolutePath = "Screenshots/" + filename;
		pgdriver.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(extentReportImage)).setFullPage(true));
		test.log(Status.FAIL, actualResult, MediaEntityBuilder.createScreenCaptureFromPath(imgAbsolutePath).build());
		// test.log(Status.FAIL, actualResult);
	}

	// use actResult when pagedrive context is current window. If a new window is
	// opened used actResultContext
	public void actResult(String actualResult, String foldername, boolean screenprint) throws IOException {
		String filename = System.currentTimeMillis() + ".png";
		String extentReportImageInfo = System.getProperty("user.dir") + "/Result/" + foldername + "/Screenshots/"
				+ filename;
		String imgAbsolutePath = "Screenshots/" + filename;
		if ((rp.readPropertiesFile("screenprintsneededforall").equals("yes")) || (screenprint) == true) {
			pagedriver.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(extentReportImageInfo)).setFullPage(true));
			test.log(Status.INFO, actualResult,
					MediaEntityBuilder.createScreenCaptureFromPath(imgAbsolutePath).build());
		} else {
			test.log(Status.INFO, actualResult);
		}
	}

	public void actResultContext(String actualResult, String foldername, Page pgdriver, boolean screenprint)
			throws IOException {
		String filename = System.currentTimeMillis() + ".png";
		String extentReportImageInfo = System.getProperty("user.dir") + "/Result/" + foldername + "/Screenshots/"
				+ filename;
		String imgAbsolutePath = "Screenshots/" + filename;
		if ((rp.readPropertiesFile("screenprintsneededforall").equals("yes")) || (screenprint) == true) {
			pgdriver.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(extentReportImageInfo)).setFullPage(true));
			test.log(Status.INFO, actualResult,
					MediaEntityBuilder.createScreenCaptureFromPath(imgAbsolutePath).build());
		} else {
			test.log(Status.INFO, actualResult);
		}
	}

	public static void initializeExtent(String testFileName) throws IOException {
		ReadPropertiesFile rp = new ReadPropertiesFile();
		report = new ExtentSparkReporter(System.getProperty("user.dir") + "/Result/" + testFileName + "/" + testFileName
				+ "_" + System.currentTimeMillis() + ".html");
		report.config().setDocumentTitle("AUTOMATED TEST REPORT");
		report.config().setReportName(rp.readPropertiesFile("extentreportname") + ", which was run using " + rp.readPropertiesFile("browser")  + " browser");

		extent = new ExtentReports();
		extent.attachReporter(report);
		// extent.setSystemInfo("Robo User","Automation tester");
		extent.setSystemInfo("Browser", rp.readPropertiesFile("browser"));
	}

	public ElementHandle checkElement(Page pgdriver, String objectName) {
		ElementHandle elecheck = pgdriver.querySelector(objectName);
		return elecheck;
	}

	public void uploadxrayresults(String testName) {
		String testPlan = data.get("Jira_Test_Plan");
		String testCase = data.get("Jira_Id");
		String remarks = data.get("Test_Name");
		String testresult = test.getStatus().toString();

		ZipDir.zipFiles(System.getProperty("user.dir") + "/Result/" + testName);
		String base64 = ZipDir
				.encodeFileToBase64Binary(new File(System.getProperty("user.dir") + "/Result/" + testName + ".zip"));
		String filename = testName + ".zip";
		try {
			XrayExportResultJson.updateXray(testPlan, testCase, remarks, testresult, base64, filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@BeforeAll
	static void startUpBrowser() throws IOException {
		/*
		 * FileCopy f= new FileCopy(); f.copyFile(); if(flag == true) { f.deleteFile();
		 * flag=false; }
		 */
		pagedriver = getChromeDriver();
		pagedriver.navigate("https://pre-solucionesdeinversion.allianz.es/login/");
	}

	@AfterAll
	static void closeBrowser() {
		pagedriver.close();
		extent.flush();
	}
}