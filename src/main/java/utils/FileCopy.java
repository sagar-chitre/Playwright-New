package utils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ui.playwright.driverFactory;

public class FileCopy {
	Logger log = LogManager.getLogger(FileCopy.class);
 IOFileFilter txtSuffixFilter = FileFilterUtils.suffixFileFilter(".zip");
	public static void copyFile() throws IOException {
		
	    //FileUtils.copyDirectory(source, dest);
		ReadPropertiesFile rp =new ReadPropertiesFile(); 
		String filetodo =rp.readPropertiesFile("archiveresults"); if(filetodo.equals("yes")) {
			  String fileName= "./ArchiveResults/Results"+
		  LocalDateTime.now().getDayOfMonth()+LocalDateTime.now().getMonthValue()+
		  LocalDateTime.now().getYear()+LocalDateTime.now().getHour()+LocalDateTime.now
		  ().getMinute()+LocalDateTime.now().getSecond(); 
			  File dir = new
					  File(fileName);
			  File source =new File("./Result/");
			  FileUtils.copyDirectory(source, dir);
		  }
		
		 
	}
	
		public static void deleteFile() throws IOException {
		    FileUtils.cleanDirectory(new File("./Result/"));
		}
}
