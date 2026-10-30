package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	private static Workbook workbook;
	private static Sheet sheet;

	public static void loadExcel(String filePath, String sheetName) throws IOException {

		FileInputStream file = new FileInputStream(filePath);
		workbook = new XSSFWorkbook(file);
		sheet = workbook.getSheet(sheetName);
	}

	public static String getCellData(int row, int col) {

		if (sheet.getRow(row) == null) {
			return "";
		}

		Cell cell = sheet.getRow(row).getCell(col);

		if (cell == null) {
			return "";
		}

		if (cell.getCellType() == CellType.STRING) {
			return cell.getStringCellValue().trim();
		}

		if (cell.getCellType() == CellType.NUMERIC) {
			return String.valueOf(cell.getNumericCellValue());
		}

		if (cell.getCellType() == CellType.BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		}

		return "";
	}

	public static int getRowCount() {
		return sheet.getPhysicalNumberOfRows();
	}

	public static int getColumnCount() {

		if (sheet.getRow(0) == null) {
			return 0;
		}

		return sheet.getRow(0).getLastCellNum();
	}

	public static int getRowIndex(String testCaseName) {

		int rowCount = getRowCount();

		for (int i = 1; i < rowCount; i++) {

			String currentTC = getCellData(i, 0);

			if (currentTC.equalsIgnoreCase(testCaseName)) {
				return i;
			}
		}

		return -1;
	}

	public static int getColumnIndex(String headerName) {

		int colCount = getColumnCount();

		for (int j = 0; j < colCount; j++) {

			String header = getCellData(0, j);

			if (header.equalsIgnoreCase(headerName)) {
				return j;
			}
		}

		return -1;
	}

	public static List<HashMap<String, String>> getTestData(String testCaseName) {

		List<HashMap<String, String>> testData = new ArrayList<>();

		int rowCount = getRowCount();
		int colCount = getColumnCount();

		for (int i = 1; i < rowCount; i++) {

			if (getCellData(i, 0).equalsIgnoreCase(testCaseName)) {

				HashMap<String, String> map = new HashMap<>();

				for (int j = 1; j < colCount; j++) {

					String header = getCellData(0, j);
					String value = getCellData(i, j);

					map.put(header, value);
				}

				testData.add(map);
			}
		}

		return testData;
	}

	public static void closeExcel() throws IOException {

		if (workbook != null) {
			workbook.close();
		}
	}
}