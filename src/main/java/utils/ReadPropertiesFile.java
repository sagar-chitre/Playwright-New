package utils;

import java.io.*;
import java.util.*;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ReadPropertiesFile {
	Logger log = LogManager.getLogger(ReadPropertiesFile.class);
	public String readPropertiesFile(String name) throws IOException {
		FileInputStream fis =null;
		 Properties prop = null;
		 String key = null;
		try {
			fis = new FileInputStream(new File("./Resources/props.properties"));
			prop = new Properties();
			prop.load(fis);
			key=prop.get(name).toString();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			fis.close();
		}
		return key;
	}
	
}