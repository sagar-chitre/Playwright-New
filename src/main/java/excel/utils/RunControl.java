package excel.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.platform.launcher.listeners.*;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import testExcecute.ExecuteTestSet;
public class RunControl {
	Logger log = LogManager.getLogger(RunControl.class);

	public void fetch() throws IOException {
		FileInputStream fis = new FileInputStream(new File("./Resources/excel/Test_Data_Sheet.xlsx"));
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(2);
		int rows = sheet.getPhysicalNumberOfRows();
		int i = 1;
		while (i <= rows) {
			Row row1 =sheet.getRow(i);
			if(row1 ==null)
				break;
				else 
				{
				String ticketId = row1.getCell(1).toString();
				if (!(ticketId.equals("YES"))) {
				System.out.println("execution flag is off");
				System.out.println();
					
				}
				else
				{
					ExecuteTestSet r =new ExecuteTestSet();
					
					TestExecutionSummary summary = r.listener.getSummary();
					 					String TestCase = row1.getCell(0).toString();
					String ClassName = row1.getCell(3).toString();
					String MethodName = row1.getCell(4).toString();
					String Class_hierarchy = row1.getCell(2).toString();
					System.out.println(Class_hierarchy);
					if ((Class_hierarchy)=="")
					   Class_hierarchy =ClassName;
						else {
							Class_hierarchy=row1.getCell(2).toString()+"."+ClassName;
						}
					r.runTestSet(TestCase,Class_hierarchy,MethodName);
					System.out.println();
					System.out.println(TestCase);
					System.out.println(MethodName);
					System.out.println();
					
					  summary = r.listener.getSummary(); summary.printTo(new
					 PrintWriter(System.out));
					 
				}
			i=i+1;
			 log.info("all tests done ");
	        }
		}
	fis.close();
	wb.close();
	}
}
