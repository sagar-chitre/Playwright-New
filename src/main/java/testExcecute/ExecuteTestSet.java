package testExcecute;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectMethod;
import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.*;

import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import utils.ReadPropertiesFile;
import static org.junit.platform.engine.discovery.DiscoverySelectors.*;
import static org.junit.platform.engine.discovery.ClassNameFilter.*;
import static org.junit.platform.launcher.EngineFilter.*;
import static org.junit.platform.launcher.TagFilter.*;



public class ExecuteTestSet {
	Logger log = LogManager.getLogger(ExecuteTestSet.class);
	
	public SummaryGeneratingListener listener = new SummaryGeneratingListener();
	  
	    public void runTestSet(String testCase, String className, String methodName) throws IOException {
	    	ReadPropertiesFile rp =new ReadPropertiesFile();
	    	
	    	String projectName =rp.readPropertiesFile("mainprojectfoldername");
			
			  LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
			  .request().selectors(selectMethod("projects."+projectName+"."+className,
			  methodName)).build();
			 
	    	//JavaMain.request =LauncherDiscoveryRequestBuilder.request().selectors(selectMethod("projects."+projectName+"."+className,methodName)).build();
	        //  .selectors(selectClass(DashboardPageTest.class))
	         // .build();
	        Launcher launcher = LauncherFactory.create();
	        TestPlan testPlan = launcher.discover(request);
	        launcher.registerTestExecutionListeners(listener);
	         launcher.execute(request);
	    }
		/*
		 * public void runOne() { LauncherDiscoveryRequest request =
		 * LauncherDiscoveryRequestBuilder .request()
		 * .selectors(selectClass(projects.apa.DashboardPage.class)) .build(); Launcher
		 * launcher = LauncherFactory.create(); TestPlan testPlan =
		 * launcher.discover(request);
		 * 
		 * launcher.registerTestExecutionListeners(listener); launcher.execute(request);
		 * }
		 * 
		 * public void runAll() { LauncherDiscoveryRequest request =
		 * LauncherDiscoveryRequestBuilder .request()
		 * .selectors(selectPackage("agi.ui.test"))
		 * .filters(includeClassNamePatterns(".*Test")) .build(); Launcher launcher =
		 * LauncherFactory.create();
		 * 
		 * TestPlan testPlan = launcher.discover(request);
		 * 
		 * launcher.registerTestExecutionListeners(listener);
		 * 
		 * launcher.execute(request); }
		 */


}
