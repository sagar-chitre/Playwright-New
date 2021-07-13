package excel.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import utils.ReadPropertiesFile;

public class Excelrowid extends Exception {
	int k = 0;
	int cellValue = 0;
	static ReadPropertiesFile read = new ReadPropertiesFile();
	public int getExcelRowID(String value) throws IOException {
		FileInputStream fis = new FileInputStream(new File("./Resources/excel/Test_Data_Sheet.xlsx"));
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(0);
		for (Row row : sheet) {
			for (Cell cell : row) {
				String ticketId = cell.getStringCellValue();
				if (ticketId.equals(value)) {
					k = row.getRowNum();
					break;
				}
			}
		}
		cellValue = (k);
		fis.close();
		wb.close();
		return cellValue;
	}
}