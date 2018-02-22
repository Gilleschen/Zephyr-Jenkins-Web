package Auto;

import java.io.FileInputStream;
import java.util.ArrayList;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LoadExpectResult {
	public ArrayList<String> ResultList = new ArrayList<String>();// �s��Y���ծצC�����浲�G

	public void LoadExpectResult(String CaseName) {// �ǤJ���ծצC�W��
		XSSFWorkbook workbook;
		XSSFSheet sheet;

		try {
			workbook = new XSSFWorkbook(new FileInputStream("C:\\TUTK_QA_TestTool\\TestTool\\Web_TestScrpit.xlsm"));
			sheet = workbook.getSheet("ExpectResult");// hard code
			ResultList = new ArrayList<String>();
			int i = 1;
			try {
				do {
					if (sheet.getRow(i).getCell(0).toString().equals(CaseName)) {// �j�M�_���ծצC�W��
						for (int j = 1; j < sheet.getRow(i).getPhysicalNumberOfCells(); j++) {

							ResultList.add(sheet.getRow(i).getCell(j).toString());// �s����浲�G�ܲM�斮
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
