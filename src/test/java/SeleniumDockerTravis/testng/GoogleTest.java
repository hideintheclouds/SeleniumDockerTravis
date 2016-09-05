package SeleniumDockerTravis.testng;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.*;
import org.testng.annotations.*;

public class GoogleTest {
	public static class DriverManager {
		/**
		 * DriverManager driver
		 * shares the same web driver and use thread local to handle the multi-thread
		 */
		public static ThreadLocal<WebDriver> ThreadDriver=new ThreadLocal<WebDriver>() ;
		public static String browserType;

		/**
		 * create a driver for this thread if not exist. or return it directly
		 */
		public static WebDriver getDriver(){
			WebDriver driver= DriverManager.ThreadDriver.get();
			if (driver==null){
				if (browserType.equals("firefox")){
					driver = new FirefoxDriver();
					ThreadDriver.set(driver);
					DriverManager.getDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
				}
				else if (browserType.equals("chrome")){
					ChromeOptions options = new ChromeOptions();
					options.addArguments("no-sandbox");
					driver = new ChromeDriver();
					ThreadDriver.set(driver);
					DriverManager.getDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
				}
				//you can add ie or other driver here
			}
			return driver;
		}


		public static void setupDriver(String browser){
			browserType=browser;

		}		

		public static void quitDriver(){
			getDriver().quit();
			DriverManager.ThreadDriver.set(null);
		}

	}

	//private WebDriver driver;

	@BeforeMethod(alwaysRun=true)
	@Parameters("browser")
	protected void testMethodStart(@Optional("chrome") String browser){
		DriverManager.setupDriver(browser);
	}
	
	@Test(groups = { "Google" })
	public void googleAssertPass() {
		WebDriver driver = DriverManager.getDriver();
		driver.get("http://google.com");

		Boolean isFound = IsElementPresent(driver, By.cssSelector("input[value*='Google Search']"));
		Assert.assertTrue(isFound);
	}
	@Test(groups = { "Google" })
	public void googleAssertFail() {
		WebDriver driver = DriverManager.getDriver();
		driver.get("http://google.com");

		Boolean isFound = IsElementPresent(driver, By.cssSelector("input[value*='Googl Search']"));

		Assert.assertTrue(isFound);
	}
	@Test(groups = { "Google" })
	public void googleAssertPass2() {
		WebDriver driver = DriverManager.getDriver();
		driver.get("http://google.com");

		Boolean isFound = IsElementPresent(driver, By.cssSelector("input[value*='Google Search']"));

		Assert.assertTrue(isFound);
	}
	@Test(groups = { "Google" })
	public void googleAssertPass3() {
		WebDriver driver = DriverManager.getDriver();
		driver.get("http://google.com");

		Boolean isFound = IsElementPresent(driver, By.cssSelector("input[value*='Google Search']"));

		Assert.assertTrue(isFound);
	}
	
	private Boolean IsElementPresent(WebDriver driver, By by) {
		try
		{
			driver.findElement(by);
			return true;
		}
		catch (NoSuchElementException e) { return false; }
	}

	@AfterMethod (alwaysRun=true)
	public void afterMethod() {
		DriverManager.quitDriver();
	}
}
