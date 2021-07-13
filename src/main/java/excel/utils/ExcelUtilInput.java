package excel.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import utils.ReadPropertiesFile;

public class ExcelUtilInput extends Exception {
	Logger log = LogManager.getLogger(ExcelUtilInput.class);
	int k = 0;
	int j = 0;
	static String s1;
	String cellValue = null;
	ArrayList data = new ArrayList();
	HashMap<Integer, String> map = new HashMap<Integer, String>();
	HashMap<Integer, String> mapHeader = new HashMap<Integer, String>();
	HashMap<Object, Object> mapReader = new HashMap<Object, Object>();
	static ReadPropertiesFile read = new ReadPropertiesFile();

	public String fetch(String input) throws IOException {
		FileInputStream fis = new FileInputStream(new File("./Resources/excel/Test_Data_Sheet.xlsx"));
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(1);
		for (Row row : sheet) {
			for (Cell cell : row) {
				String ticketId = cell.getStringCellValue();
				if (ticketId.equals(input)) {
					k = row.getRowNum();
					break;
				}
			}
		}
		Row rowes = sheet.getRow(j);
		for (Cell cell2 : rowes) {
			data.add(cell2.getStringCellValue());
			mapHeader.put(cell2.getColumnIndex(), cell2.getStringCellValue());
		}
		Row rowe = sheet.getRow(k);
		for (Cell cell1 : rowe) {
			data.add(cell1.getStringCellValue());
			map.put(cell1.getColumnIndex(), cell1.getStringCellValue());
		}
		fis.close();
		wb.close();
		for (Map.Entry m : map.entrySet()) {
			for (Map.Entry m1 : mapHeader.entrySet()) {
				if (m.getKey() == m1.getKey()) {

					mapReader.put(m1.getValue(), m.getValue());
					break;
				}
			}
		}
		cellValue = (String) mapReader.get("ObjectIdentifier");
		
		return cellValue;
	}
}