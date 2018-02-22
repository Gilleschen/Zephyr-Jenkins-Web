package Auto;

import java.io.FileInputStream;
import java.util.ArrayList;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LoadExpectResult {
	public ArrayList<String> ResultList = new ArrayList<String>();// ¦s©ñ¬Y´ú¸Õ®×¦Cªº´Á±æµ²ªG

	public void LoadExpectResult(String CaseName) {// ¶Ç¤J´ú¸Õ®×¦C¦WºÙ
		XSSFWorkbook workbook;
		XSSFSheet sheet;

		try {
			workbook = new XSSFWorkbook(new FileInputStream("C:\\TUTK_QA_TestTool\\TestTool\\Web_TestScrpit.xlsm"));
			sheet = workbook.getSheet("ExpectResult");// hard code
			ResultList = new ArrayList<String>();
			int i = 1;
			try {
				do {
					if (sheet.getRow(i).getCell(0).toString().equals(CaseName)) {// ·j´M§_´ú¸Õ®×¦C¦WºÙ
						for (int j = 1; j < sheet.getRow(i).getPhysicalNumberOfCells(); j++) {

							ResultList.add(sheet.getRow(i).getCell(j).toString());// ¦s©ñ´Á±æµ²ªG¦Ü²M³æ–®
						}
						break;
					}

					i++;
				} while (!sheet.getRow(i).getCell(0).toString().equals(""));

			} catch (Exception e) {
				;
			}

			// System.out.println(ResultList);

			workbook.close();
		} catch (Exception e) {
			;
		}
	}
}
