package Auto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Stopwatch;

public class method {
	static LoadTestCase TestCase = new LoadTestCase();
	LoadExpectResult ExpectResult = new LoadExpectResult();

	static int port = 5555;
	static int command_timeout = 30;// 30sec
	static String appElemnt;// APP元件名稱
	static String appInput;// 輸入值
	static String appInputXpath;// 輸入值的Xpath格式
	static String element;
	static WebDriver driver = null;
	static ArrayList ResultList = new ArrayList();// 各測試案例的執行結果(一維)
	static ArrayList<ArrayList> AllResultList = new ArrayList<ArrayList>();// 所有測試案例的執行結果(二維)
	static Boolean VerifiedResult;// Verified判斷結果；ture為正確；false為錯誤
	static int CurrentCaseNumber = -1;// 目前執行到第幾個測試案列
	static Boolean CommandError = true;// 判定執行的指令是否出現錯誤；ture為正確；false為錯誤
	static long totaltime;// 統計所有案例測試時間
	XSSFSheet Sheet;
	XSSFWorkbook workBook;
	static int CurrentCase;

	public ArrayList<ArrayList> method() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {

		Object obj = new method();
		Class c = obj.getClass();
		String methodName = null;

		for (CurrentCase = 0; CurrentCase < TestCase.StepList.size(); CurrentCase++) {
			CommandError = true;// 預設CommandError為True
			Stopwatch timer = Stopwatch.createStarted();// 開始計時
			System.out.println("[info] CaseName:|" + TestCase.CaseList.get(CurrentCase).toString() + "|");
			ResultList = new ArrayList();
			ResultList.add(TestCase.CaseList.get(CurrentCase).toString());
			for (int CurrentCaseStep = 0; CurrentCaseStep < TestCase.StepList.get(CurrentCase)
					.size(); CurrentCaseStep++) {
				if (!CommandError) {
					break;// 若目前測試案例出現CommandError=false，則跳出目前案例並執行下一個案例
				}
				switch (TestCase.StepList.get(CurrentCase).get(CurrentCaseStep).toString()) {

				case "Launch":
					methodName = "Launch";
					break;

				case "Byid_SendKey":
					methodName = "Byid_SendKey";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					appInput = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 2);
					CurrentCaseStep = CurrentCaseStep + 2;
					break;

				case "Byid_Click":
					methodName = "Byid_Click";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "ByXpath_SendKey":
					methodName = "ByXpath_SendKey";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					appInput = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 2);
					CurrentCaseStep = CurrentCaseStep + 2;
					break;

				case "ByXpath_Click":
					methodName = "ByXpath_Click";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "Byid_Wait":
					methodName = "Byid_Wait";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "ByXpath_Wait":
					methodName = "ByXpath_Wait";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "Byid_VerifyText":
					methodName = "Byid_VerifyText";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "ByXpath_VerifyText":
					methodName = "ByXpath_VerifyText";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "ByXpath_Scroll":
					methodName = "ByXpath_Scroll";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "Byid_Scroll":
					methodName = "Byid_Scroll";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "Byid_invisibility":
					methodName = "Byid_invisibility";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "ByXpath_invisibility":
					methodName = "ByXpath_invisibility";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "Byid_Clear":
					methodName = "Byid_Clear";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "ByXpath_Clear":
					methodName = "ByXpath_Clear";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "ScreenShot":
					methodName = "ScreenShot";
					break;

				case "Back":
					methodName = "Back";
					break;

				case "Next":
					methodName = "Next";
					break;

				case "Refresh":
					methodName = "Refresh";
					break;

				case "Goto":
					methodName = "Goto";
					appInput = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "Sleep":
					methodName = "Sleep";
					appInput = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "Quit":
					methodName = "Quit";
					break;

				}

				Method method;
				method = c.getMethod(methodName, new Class[] {});
				method.invoke(c.newInstance());
			}
			System.out.println("(" + timer.stop() + ")");
			totaltime += timer.elapsed(TimeUnit.SECONDS);
			System.out.println("");
		}
		System.out.println("測試結束!!!" + "(" + totaltime + " s)");
		return AllResultList;

	}

	public void ErrorCheck(Object... elements) throws IOException {
		DateFormat df = new SimpleDateFormat("MMM dd, yyyy h:mm:ss a");
		Date today = Calendar.getInstance().getTime();
		String reportDate = df.format(today);

		if (elements.length > 1) {

			String APPElement = "";
			int i = 0;
			for (Object element : elements) {
				APPElement = APPElement + element;
				if (i != (elements.length - 1)) {// 判斷是否處理到最後一個element
					APPElement = APPElement + " or ";// 組成" A元件 or B元件 or
														// C元件"字串
				}
				i++;
			}
			System.err.print("[Error] Can not found " + APPElement + " on screen.");
		} else {
			for (Object element : elements) {
				if (element.equals("Sleep")) {
					System.err.print("[Error] Fail to sleep.");
				} else if (element.equals("ScreenShot")) {
					System.err.print("[Error] Fail to ScreenShot.");
				} else if (element.equals("Quit")) {
					System.err.print("[Error] Can't close browser.");
				} else if (element.equals("Launch")) {
					System.err.print("[Error] Can't launch browser.");
				} else if (element.equals("BACK") || element.equals("Refresh") || element.equals("Next")) {
					System.err.print("[Error] Can't execute " + element + " button.");
				} else if (element.equals("Goto")) {
					System.err.println("[Error] Can't execute Goto " + appInput);
				} else if (element.equals("Customized")) {
					System.err.print("[Error] Can't execute Customized_Method.");
				} else {
					System.err.print("[Error] Can not found " + element + " on screen.");
				}
			}
		}
		System.err.println(" " + reportDate);
		String FilePath = MakeErrorFolder();// 建立各案例資料夾存放log資訊及Screenshot資訊
		ErrorScreenShot(FilePath);// Screenshot Error畫面
		logcat(FilePath);// 收集閃退logcat
		CommandError = false;// 設定CommandError=false
		ResultList.add("error");
		AllResultList.add(ResultList);
	}

	public void logcat(String FilePath) throws IOException {
		// 收集log
		// System.out.println("[info] Saving device log...");
		DateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH-mm-ss");
		Date today = Calendar.getInstance().getTime();
		String reportDate = df.format(today);

		LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
		try {
			FileWriter fw = new FileWriter(FilePath + TestCase.CaseList.get(CurrentCaseNumber).toString() + "_"
					+ reportDate + "_log" + ".txt");
			for (LogEntry entry : logEntries) {
				fw.write("Timestamp:" + entry.getTimestamp() + "\r\n");
				fw.write("Level:" + entry.getLevel() + "\r\n");
				fw.write("Message:" + entry.getMessage() + "\r\n");
			}
			fw.flush();
			fw.close();
			System.out.println("[info] Saving browser log - Done.");
		} catch (Exception e) {
			System.err.println("[Error] Fail to save browser log.");
		}
	}

	public void ErrorScreenShot(String FilePath) {
		try {
			System.out.println("[info] Taking a screenshot of error.");
			DateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH-mm-ss");
			Date today = Calendar.getInstance().getTime();
			String reportDate = df.format(today);
			File screenShotFile = (File) ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenShotFile,
					new File(FilePath + TestCase.CaseList.get(CurrentCaseNumber) + "_" + reportDate + ".jpg"));
		} catch (IOException e) {
			System.err.println("[Error] Fail to ErrorScreenShot.");
		}
	}

	public String MakeErrorFolder() {
		// 資料夾結構 C:\TUTK_QA_TestTool\TestReport\TestURL\CaseName\Browser
		String filePath = "C:\\TUTK_QA_TestTool\\TestReport\\"
				+ TestCase.DeviceInformation.URL.replaceAll("//", "").replaceAll("https:", "").replaceAll("/", "")
						.replaceAll("http:", "").toString()
				+ "\\" + TestCase.CaseList.get(CurrentCase).toString() + "\\" + TestCase.DeviceInformation.Browser
				+ "\\";
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		return filePath;
	}

	public void Customized() throws IOException {
		Customized custom = new Customized(driver);
		if (!custom.Customized_Method()) {
			ErrorCheck("Customized");
		}
	}

	public void Byid_VerifyText() throws IOException {

		try {
			System.out.println("[info] Executing:|Byid_VerifyText|" + appElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait = new WebDriverWait(driver, command_timeout);
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(appElemnt))).getText();

			// 回傳測試案例清單的名稱給ExpectResult.LoadExpectResult，並存放期望結果至ResultList清單
			ExpectResult.LoadExpectResult(TestCase.CaseList.get(CurrentCaseNumber).toString());

			for (int j = 0; j < ExpectResult.ResultList.size(); j++) {
				if (element.equals(ExpectResult.ResultList.get(j))) {
					VerifiedResult = true;
					break;
				} else {
					VerifiedResult = false;
				}
			}
			if (VerifiedResult) {
				System.out.println("[info] Result_Byid_VerifyText:|PASS|");
				ResultList.add(true);
			} else {
				System.out.println("[info] Result_Byid_VerifyText:|FAIL|");
				ResultList.add(false);
			}
			AllResultList.add(ResultList);
		} catch (Exception e) {
			ErrorCheck(appElemnt);
		}
	}

	public void ByXpath_VerifyText() throws IOException {

		try {
			System.out.println("[info] Executing:|ByXpath_VerifyText|" + appElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait = new WebDriverWait(driver, command_timeout);
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(appElemnt))).getText();
			// 回傳測試案例清單的名稱給ExpectResult.LoadExpectResult，並存放期望結果至ResultList清單
			ExpectResult.LoadExpectResult(TestCase.CaseList.get(CurrentCaseNumber).toString());

			for (int j = 0; j < ExpectResult.ResultList.size(); j++) {
				if (element.equals(ExpectResult.ResultList.get(j))) {
					VerifiedResult = true;
					break;
				} else {
					VerifiedResult = false;
				}
			}

			if (VerifiedResult) {
				System.out.println("[info] Result_ByXpath_VerifyText:|PASS|");
				ResultList.add(true);
			} else {
				System.out.println("[info] Result_ByXpath_VerifyText:|FAIL|");
				ResultList.add(false);
			}
			AllResultList.add(ResultList);

		} catch (Exception e) {
			ErrorCheck(appElemnt);
		}
	}

	public void Byid_Click() throws IOException {

		try {
			System.out.println("[info] Executing:|Byid_Click|" + appElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(appElemnt))).click();

		} catch (Exception e) {
			ErrorCheck(appElemnt);
		}
	}

	public void ByXpath_Click() throws IOException {

		try {
			System.out.println("[info] Executing:|ByXpath_Click|" + appElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(appElemnt))).click();

		} catch (Exception e) {
			ErrorCheck(appElemnt);
		}

	}

	public void Byid_SendKey() throws IOException {

		try {
			System.out.println("[info] Executing:|Byid_SendKey|" + appElemnt + "|" + appInput + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(appElemnt))).sendKeys(appInput);

		} catch (Exception e) {
			ErrorCheck(appElemnt);
		}
	}

	public void ByXpath_SendKey() throws IOException {

		try {
			System.out.println("[info] Executing:|ByXpath_SendKey|" + appElemnt + "|" + appInput + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(appElemnt))).sendKeys(appInput);

		} catch (Exception e) {
			ErrorCheck(appElemnt);
		}

	}

	public void ByXpath_Scroll() throws IOException {

		try {
			System.out.println("[info] Executing:|ByXpath_Scroll|" + appElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait = new WebDriverWait(driver, command_timeout);
			WebElement target = wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath(appElemnt))));
			Actions actions = new Actions(driver);
			actions.moveToElement(target);
			// actions.click(target);
			actions.perform();

		} catch (Exception e) {
			ErrorCheck(appElemnt);
		}
	}

	public void Byid_Scroll() throws IOException {

		try {
			System.out.println("[info] Executing:|Byid_Scroll|" + appElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait = new WebDriverWait(driver, command_timeout);
			WebElement target = wait.until(ExpectedConditions.presenceOfElementLocated((By.id(appElemnt))));
			Actions actions = new Actions(driver);
			actions.moveToElement(target);
			// actions.click(target);
			actions.perform();

		} catch (Exception e) {
			ErrorCheck(appElemnt);
		}

	}

	public void ByXpath_invisibility() throws IOException {

		try {
			System.out.println("[info] Executing:|ByXpath_invisibility|" + appElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(appElemnt)));

		} catch (Exception e) {
			ErrorCheck(appElemnt);
		}
	}

	public void Byid_invisibility() throws IOException {

		try {
			System.out.println("[info] Executing:|Byid_invisibility|" + appElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(appElemnt)));
		} catch (Exception e) {
			ErrorCheck(appElemnt);
		}
	}

	public void Byid_Wait() throws IOException {

		try {
			System.out.println("[info] Executing:|Byid_Wait|" + appElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id(appElemnt)));
		} catch (Exception e) {
			ErrorCheck(appElemnt);
		}

	}

	public void ByXpath_Wait() throws IOException {

		try {
			System.out.println("[info] Executing:|ByXpath_Wait|" + appElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(appElemnt)));
		} catch (Exception e) {
			ErrorCheck(appElemnt);
		}
	}

	public void Byid_Clear() throws IOException {

		try {
			System.out.println("[info] Executing:|Byid_Clear|" + appElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(appElemnt))).clear();
		} catch (Exception e) {
			ErrorCheck(appElemnt);
		}
	}

	public void ByXpath_Clear() throws IOException {

		try {
			System.out.println("[info] Executing:|ByXpath_Clear|" + appElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(appElemnt))).clear();
		} catch (Exception e) {
			ErrorCheck(appElemnt);
		}
	}

	public void Launch() throws IOException {
		CurrentCaseNumber = CurrentCaseNumber + 1;
		DesiredCapabilities cap = null;
		LoggingPreferences logs = new LoggingPreferences();
		logs.enable(LogType.BROWSER, Level.ALL);
		cap = new DesiredCapabilities();
		cap.setBrowserName(TestCase.DeviceInformation.Browser);
		cap.setCapability(CapabilityType.LOGGING_PREFS, logs);

		try {
			System.out.println("[info] Executing:|Launch|" + TestCase.DeviceInformation.Browser + "|"
					+ TestCase.DeviceInformation.URL + "|");
			driver = new RemoteWebDriver(new URL("http://localhost:" + port + "/wd/hub"), cap);
			driver.manage().timeouts().pageLoadTimeout(command_timeout, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.get(TestCase.DeviceInformation.URL);

		} catch (Exception e1) {
			ErrorCheck("Launch");
		}
	}

	public void Quit() throws IOException {
		boolean state = false;
		try {
			System.out.println("[info] Executing:|Quit|");
			driver.quit();

			for (int i = 0; i < TestCase.StepList.get(CurrentCaseNumber).size(); i++) {
				if (TestCase.StepList.get(CurrentCaseNumber).get(i).equals("Byid_VerifyText")
						|| TestCase.StepList.get(CurrentCaseNumber).get(i).equals("ByXpath_VerifyText")) {
					state = true;// true代表找到ByXpath_VerifyText或Byid_VerifyText
					break;
				}
			}
			if (!state) {
				if (CommandError) {
					System.out.print("[Result] " + TestCase.CaseList.get(CurrentCaseNumber).toString() + ":PASS");
					ResultList.add(true);
					AllResultList.add(ResultList);
				}
			} else {
				if (CommandError && VerifiedResult) {
					System.out.print("[Result] " + TestCase.CaseList.get(CurrentCaseNumber).toString() + ":PASS");
				} else {
					System.out.print("[Result] " + TestCase.CaseList.get(CurrentCaseNumber).toString() + ":FAIL");
				}
			}
		} catch (Exception e) {
			ErrorCheck("Quit");
		}
	}

	public void Sleep() throws IOException {
		try {
			System.out.println("[info] Executing:|Sleep|" + appInput + " second..." + "|");
			Thread.sleep((long) (Float.valueOf(appInput) * 1000));
		} catch (Exception e) {
			ErrorCheck("Sleep");
		}
	}

	public void ScreenShot() throws IOException {

		try {

			DateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH-mm-ss");
			Date today = Calendar.getInstance().getTime();
			String reportDate = df.format(today);

			File screenShotFile = (File) ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			System.out.println("[info] Executing:|ScreenShot|");
			FileUtils.copyFile(screenShotFile, new File("C:\\TUTK_QA_TestTool\\TestReport\\"
					+ TestCase.CaseList.get(CurrentCaseNumber) + "_" + reportDate + ".jpg"));
			System.out.println("[Log] " + "ScreenShot Successfully!! (Name:CaseName+Month+Day+Hour+Minus+Second)");

		} catch (IOException e) {
			ErrorCheck("ScreenShot");
		}

	}

	public void Back() throws IOException {

		try {
			System.out.println("[info] Executing:|Back|");
			driver.navigate().back();
		} catch (Exception e) {
			ErrorCheck("Back");
		}

	}

	public void Next() throws IOException {

		try {
			System.out.println("[info] Executing:|Next|");
			driver.navigate().forward();
		} catch (Exception e) {
			ErrorCheck("Next");
		}

	}

	public void Refresh() throws IOException {

		try {
			System.out.println("[info] Executing:|Refresh|");
			driver.navigate().refresh();
		} catch (Exception e) {
			ErrorCheck("Refresh");
		}
	}

	public void Goto() throws IOException {

		try {
			System.out.println("[info] Executing:|Goto|" + appInput + "|");
			driver.navigate().to(appInput);
		} catch (Exception e) {
			ErrorCheck("Goto");
		}
	}
}