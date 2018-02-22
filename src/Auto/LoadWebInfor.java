package Auto;

import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LoadWebInfor {
	public ArrayList<String> ScriptList = new ArrayList<String>();// 存放待執行的腳本名稱
	// public ArrayList<String> BrowserList = new ArrayList<String>();//
	// 存放待測試之Borwser
	XSSFWorkbook workBook;
	XSSFSheet Sheet;
	String DriverPath, URL, Browser;

	public LoadWebInfor() {
		try {
			workBook = new XSSFWorkbook(new FileInputStream("C:\\TUTK_QA_TestTool\\TestTool\\Web_TestScrpit.xlsm"));
			Sheet = workBook.getSheet("Web_Infor");// hard code
			ScriptList = new ArrayList<String>();
			// BrowserList = new ArrayList<String>();
			Browser = Sheet.getRow(1).getCell(0).toString();
			DriverPath = Sheet.getRow(1).getCell(1).toString();
			URL = Sheet.getRow(1).getCell(2).toString();


			int k = 1;
			try {
				do {
					ScriptList.add(Sheet.getRow(k).getCell(3).toString());// 準備要Run的腳本
					k++;
				} while (!Sheet.getRow(k).getCell(3).toString().equals(""));
			} catch (Exception e) {
				;
			}

			System.out.println("瀏覽器/URL/ScrpitName");
			// System.out.print(Browser + "/");

			System.out.print(Browser + "/");
			System.out.print(URL);
			for (int u = 0; u < ScriptList.size(); u++) {

				System.out.print("/" + ScriptList.get(u).toString());
			}
			System.out.println("");

			workBook.close();

		} catch (Exception e) {

		}

	}

}
