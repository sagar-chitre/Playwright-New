package utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.commons.*;
import org.apache.commons.io.FileUtils;

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
	
	 List<String> lines = new ArrayList<String>();
	 String line = null;

	    public void  write(String key) throws IOException {
	        try {
	            File f1 = new File("./Resources/props.properties");
	            FileReader fr = new FileReader(f1);
	            BufferedReader br = new BufferedReader(fr);
	            while ((line = br.readLine()) != null) {
	                if (line.contains("testexecutionkey="))
	                    line = line.replace("testexecutionkey=", "testexecutionkey="+key);
	                lines.add(line);
	            }
	            fr.close();
	            br.close();

	            FileWriter fw = new FileWriter(f1);
	            BufferedWriter out = new BufferedWriter(fw);
	            for(String s : lines)
	                 out.write(s);
	            out.flush();
	            out.close();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	    
	    public static void setVariable(int lineNumber, String data) throws IOException {
	        Path path = Paths.get("./Resources/props.properties");
	        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
	        lines.set(lineNumber - 1, data);
	        Files.write(path, lines, StandardCharsets.UTF_8);
	    }
	    
}