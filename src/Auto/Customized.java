package Auto;// hard code

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//hard code部分勿動
public class Customized {
	WebDriver driver = null;// hard code
	WebDriverWait wait;
	boolean CommandError;// hard code

	public Customized(WebDriver driver) {
		this.driver = driver;// hard code
	}

	public boolean Customized_Method() {

		try {
			System.out.println("[info] Executing:|Customized_Method|");// hard
																		// code
			/* 由method.java傳入WebDriver, 變數命名為driver */
			/* 以下請撰寫程式 */
			// 範例
			wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("element_id"))).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("element2_id"))).click();

			/* 以上請撰寫程式 */
			CommandError = true;// hard code

		} catch (Exception e) {
			CommandError = false;// hard code
		}
		return CommandError;// hard code
	}
}
