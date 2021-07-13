
package excel.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteToExcel {
	Logger log = LogManager.getLogger(WriteToExcel.class);
	public void WriteDataIntoExcelCell(int Row, int col, String value) throws IOException {
		FileInputStream fis = new FileInputStream(new File("./Resources/excel/Test_Data_Sheet.xlsx"));
		XSSFWorkbook wb = new XSSFWorkbook(fis);

		XSSFSheet sheet1 = wb.getSheetAt(0);
		XSSFCell cell2Update = sheet1.getRow(Row).getCell(col);
		cell2Update.setCellValue(value);
		try {
			// Write the workbook in file system FileOutputStream out = new
			FileOutputStream out = new FileOutputStream(new File("./Resources/excel/Test_Data_Sheet.xlsx"));
            wb.write(out);
            out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
