package ui.playwright;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import excel.utils.ExcelUtil;
import excel.utils.WriteToExcel;

public class BaseExcelUtil {
	Logger log = LogManager.getLogger(BaseExcelUtil.class);
	private ExcelUtil excelUtil;

	public BaseExcelUtil() {
		super();
		this.setExcelUtil(new ExcelUtil());
	}

	public ExcelUtil getExcelUtil() {
		return excelUtil;
	}

	public void setExcelUtil(ExcelUtil excelUtil) {
		this.excelUtil = excelUtil;
	}
	
	
	/*
	 * public static ExcelUtil initializeExcel() { ExcelUtil excelUtil =new
	 * ExcelUtil(); return excelUtil; }
	 */

}
