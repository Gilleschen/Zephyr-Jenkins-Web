package Auto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LoadTestCase {
	public ArrayList<ArrayList<String>> StepList = new ArrayList<ArrayList<String>>();// �Ҧ����ծרҪ��ʧ@�M��(2���}�C)
	ArrayList<String> StepListData = new ArrayList<String>();// ��@���ծרҪ��ʧ@�M��
	ArrayList<String> CheckStepListData = new ArrayList<String>();// �h��StepListData���ťո�ơA��s��@���ծרҪ��ʧ@�M��
	public ArrayList<String> CaseList = new ArrayList<String>();// �Ҧ����ծרҪ��W�ٲM��
	LoadWebInfor DeviceInformation = new LoadWebInfor();

	public void SaveAllCase(XSSFSheet sheet) {
		int i = 0;
		try {

			do {// column Number

				for (int j = 0; j < sheet.getRow(i).getPhysicalNumberOfCells(); j++) {

					if (sheet.getRow(i).getCell(j) != null) {// Apache
																// POI
																// Ū��Excel�x�s��ɡA�����v�N�ť��x�s��Ū�J�A�]���ݧP�_�x�s��O�_���ťաA��null

						if (sheet.getRow(i).getCell(j).toString().equals("CaseName")) {
							CaseList.add(sheet.getRow(i).getCell(1).toString());// �q���w�ݴ��ո}����sheet���x�s���ծרҪ��W��
							break;
						} else {

							StepListData.add(sheet.getRow(i).getCell(j).getStringCellValue());// �q���w�ݴ��ո}����sheet���x�s���ծרҪ��B�J
																								// Excel�Ʀr�n�ন�r�ꫬ�A
						}
					}
				}
				// �P�_��@���ծרҬO�_�����A�Y�O�A�hStepListData�[�JStepList
				if (sheet.getRow(i).getCell(0).toString().equals("QuitAPP")
						|| sheet.getRow(i).getCell(0).toString().equals("Quit")) {

					for (int j = 0; j < StepListData.size(); j++) {
						if (StepListData.get(j).toString() != "") {
							CheckStepListData.add(StepListData.get(j).toString());
						}
					}
					StepList.add(CheckStepListData);
					StepListData = new ArrayList<String>();
					CheckStepListData = new ArrayList<String>();
				}
				i++;
			} while (!sheet.getRow(i).getCell(0).toString().equals(""));
		} catch (Exception e) {
			;
		}
	}

	public void SaveCase(XSSFSheet APPDeviceSheet, XSSFSheet sheet, int k) {
		String casename = APPDeviceSheet.getRow(k + 1).getCell(4).toString();
		casename = casename.replaceAll("\\s+", "");// �����r�ꤺ�Ҧ��Ů�
		String[] TargetCaseList = casename.split(",");// �N���ծרҥ[�JTargetCaseList�x�}

		for (int i = 0; i < TargetCaseList.length; i++) {
			CaseList.add(TargetCaseList[i]);
		}

		for (int c = 0; c < TargetCaseList.length; c++) {
			int i = 0;
			try {
				do {

					if (sheet.getRow(i).getCell(0).toString().equals("CaseName")) {

						if (sheet.getRow(i).getCell(1).toString().equals(TargetCaseList[c].toString())) {

							int w = i + 1;
							try {
								do {
									for (int j = 0; j < sheet.getRow(w).getPhysicalNumberOfCells(); j++) {
										StepListData.add(sheet.getRow(w).getCell(j).getStringCellValue());
									}
									w++;
								} while (!sheet.getRow(w).getCell(0).toString().equals("CaseName"));
								i = w - 1;
							} catch (Exception e) {
								i = w - 1;
							}
							// �P�_��@���ծרҬO�_�����A�Y�O�A�hStepListData�[�JStepList
							if (sheet.getRow(i).getCell(0).toString().equals("QuitAPP")
									|| sheet.getRow(i).getCell(0).toString().equals("Quit")) {

								for (int j = 0; j < StepListData.size(); j++) {
									if (StepListData.get(j).toString() != "") {
										CheckStepListData.add(StepListData.get(j).toString());
									}
								}

								StepList.add(CheckStepListData);
								StepListData = new ArrayList<String>();
								CheckStepListData = new ArrayList<String>();
								break;
							}
						}
					}
					i++;
				} while (!sheet.getRow(i).getCell(0).toString().equals(""));

			} catch (Exception e) {
				;
			}
		}
	}

	public LoadTestCase() {
		XSSFWorkbook workbook = null;
		XSSFSheet sheet, APPDeviceSheet;

		try {
			workbook = new XSSFWorkbook(new FileInputStream("C:\\TUTK_QA_TestTool\\TestTool\\Web_TestScrpit.xlsm"));
			APPDeviceSheet = workbook.getSheet("Web_Infor");// hard code
			CaseList = new ArrayList<String>();
			StepList = new ArrayList<ArrayList<String>>();
			StepListData = new ArrayList<String>();
			for (int k = 0; k < DeviceInformation.ScriptList.size(); k++) {

				sheet = workbook.getSheet(DeviceInformation.ScriptList.get(k).toString());// ���w�ݴ��ո}����sheet
				if (APPDeviceSheet.getRow(k + 1).getCell(4) == null) {
					SaveAllCase(sheet);
				} else {
					SaveCase(APPDeviceSheet, sheet, k);
				}
			}

		} catch (Exception e) {
			;
		}

		System.out.println("���ո}���G" + StepList);
		System.out.println("");
		// �إߦU�˸m��Test Report

		if (DeviceInformation.Browser.toString().length() > 20) {// Excel�u�@��W�ٳ̱`31�r���]�A�G�ݧP�_BrowserName���׬O�_�j��31
			char[] NewBrowserName = new char[20];// �]�ݥ]�t_TestReport�r��(�@11�r��)�A�G�]�w20��r���}�C(31-11)
			DeviceInformation.Browser.toString().getChars(0, 20, NewBrowserName, 0);// ���XBrowserName�e20�r����NewBrowserName
			sheet = workbook.createSheet(String.valueOf(NewBrowserName) + "_TestReport");// �ϥ�NewBrowserName�R�W�s�u�@��
		} else {
			sheet = workbook.createSheet(DeviceInformation.Browser.toString() + "_TestReport");
		}

		sheet.createRow(0).createCell(0).setCellValue("CaseName");
		sheet.getRow(0).createCell(1).setCellValue("Result");
		for (int k = 0; k < CaseList.size(); k++) {
			sheet.createRow(k + 1).createCell(0).setCellValue(CaseList.get(k).toString());// ��J�U�רҦW��
			sheet.getRow(k + 1).createCell(1).setCellValue("Error");// �w���U�צC���G��Error

		}

		// ����g�JExcel�᪺�s�ɰʧ@
		FileOutputStream out;
		try {
			out = new FileOutputStream(new File("C:\\TUTK_QA_TestTool\\TestReport\\Web_TestReport.xlsm"));// �t�s�s��
			workbook.write(out);
			out.close();
			workbook.close();
		} catch (Exception e) {
			;
		}

	}

}
