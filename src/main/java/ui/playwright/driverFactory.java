package ui.playwright;
import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.microsoft.playwright.*;

import utils.ReadPropertiesFile;

public class driverFactory  {
	Logger log = LogManager.getLogger(driverFactory.class);
	 public static Page getChromeDriver() throws IOException{
	    Playwright playwright = Playwright.create();
	    ReadPropertiesFile rp =new ReadPropertiesFile();
	    Browser browser = null;
	    BrowserContext context =null;
	    Page pagedriver =null;
	    Boolean SetDevTools = ((rp.readPropertiesFile("setDevtools").equals("true")) ? true : false);
	    Boolean headless =((rp.readPropertiesFile("headless").equals("true")) ? true : false);
	    if(rp.readPropertiesFile("browser").equals("firefox"))
	    {
	    	
		//Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().withHeadless(false));
	     browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless).setDevtools(SetDevTools));
		 // BrowserContext context = browser.newContext(new Browser.NewContextOptions().withViewport(800, 600));
		 context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
		pagedriver = context.newPage();
		
	  }
	    if(rp.readPropertiesFile("browser").equals("chrome"))
        {
         browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless).setChannel("chrome").setDevtools(SetDevTools));
         context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
         pagedriver = context.newPage();
              }
        if(rp.readPropertiesFile("browser").equals("edge"))
        {
         browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless).setChannel("msedge").setDevtools(SetDevTools));
         context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
         pagedriver = context.newPage();
              }
        if(rp.readPropertiesFile("browser").equals("webkit"))
        {
         browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(headless).setDevtools(SetDevTools));
         context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
         pagedriver = context.newPage();
              }
	 return pagedriver;
	 }
}
