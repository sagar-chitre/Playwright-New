package testExcecute;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherFactory;

import excel.utils.RunControl;
import utils.FileCopy;

public class JavaMain {
	Logger log = LogManager.getLogger(JavaMain.class);
	public static boolean flag = true;
	//	public static Launcher launcher = LauncherFactory.create();
	//public static LauncherDiscoveryRequest request ;
    public static void main(String[] args) throws IOException {	 	
    	FileCopy f= new
		  FileCopy(); 
		  f.copyFile(); 
		  if(flag == true)
		  {
		  f.deleteFile();
		  flag=false; 
		  }
    	RunControl runner = new RunControl();
        runner.fetch();
      System.exit(0);
      }   
	}