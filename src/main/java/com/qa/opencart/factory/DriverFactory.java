package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.aspectj.util.FileUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.Exception.FrameWorkException;

public class DriverFactory {

	public WebDriver driver;
	public Properties prop;
	public OptionsManager optionsManager;

	public static String highlight;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	/**
	 * this method is initializing the driver on basis of given browser name
	 * 
	 * @param browserName
	 * @return this returns the driver instance
	 */
	public WebDriver initDriver(Properties prop) {

		optionsManager = new OptionsManager(prop);

		highlight = prop.getProperty("highlight").trim();
		String browserName = prop.getProperty("browser").toLowerCase().trim();

		// String browserName=System.getProperty("browser"); //use this if you want to
		// supply browser from maven command line argument

		System.out.println("browser name is:  " + browserName);

		if (browserName.equalsIgnoreCase("chrome")) {

			if (Boolean.parseBoolean(prop.getProperty("remote"))) {

				// run the code on selenium grid

				init_remoteDriver("chrome");

			}

			else {
				// run tests on local system
				// driver= new ChromeDriver(optionsManager.getChromeOptions());
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}

		}
		// firefox
		else if (browserName.equalsIgnoreCase("firefox")) {

			if (Boolean.parseBoolean(prop.getProperty("remote"))) {

				// run the code on selenium grid

				init_remoteDriver("firefox");

			}

			else {
				// driver= new FirefoxDriver(optionsManager.getFirefoxOptions());
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			}

		}

		else if (browserName.equalsIgnoreCase("safari")) {
			// driver= new SafariDriver();
			tlDriver.set(new SafariDriver());
		}

		else if (browserName.equalsIgnoreCase("edge")) {

			if (Boolean.parseBoolean(prop.getProperty("remote"))) {

				// run the code on selenium grid

				init_remoteDriver("edge");

			}

			else {

				tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			}

		}

		else {
			System.out.println("please pass right browser......" + browserName);
		}

		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url"));
		getDriver().manage().window().maximize();

		return getDriver();
	}

	/**
	 * this method is called internally to initialize the driver with
	 * RemoteWebDriver
	 */

	private void init_remoteDriver(String browser) {
		
		System.out.println(" Running tests on selenium grid server..."+browser);

		try {

			switch (browser.toLowerCase()) {
			case "chrome":

				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));

				break;

			case "firefox":

				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFirefoxOptions()));

				break;

			case "edge":

				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getEdgeOptions()));

				break;

			default:

				System.out.println(" please pass the right browser for remote execution...." + browser);
				throw new FrameWorkException("NOREMOTEBROWSEREXCEPTION");
			}
		}

		catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * get the local thread copy of the driver from this method
	 */
	public synchronized static WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * this method is reading the properties from config.properties
	 * 
	 * @return
	 */
	public Properties initProp() {
		prop = new Properties();
		FileInputStream ip = null;

		// mvn clean install -Denv="Staging/qa/"
		// below is to tell framework to read the properties

		String envName = System.getProperty("env");
		System.out.println("Running test cases on " + envName);

		try {

			if (envName == null) {
				System.out.println("no environment selected...running tests on QA env...");
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			}

			else {
				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;

				case "staging":
					ip = new FileInputStream("./src/test/resources/config/staging.config.properties");
					break;

				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;

				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.properties");
					break;

				default:

					System.out.println("wrong env passed, not running cases...");
					throw new FrameWorkException("Wrong environment passed");

				// break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return prop;
	}

	/**
	 * takes the screenshot
	 */
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot" + System.currentTimeMillis() + ".png";// can give
																											// any file
																											// extension
																											// here
		File destination = new File(path);

		try {
			FileUtil.copyFile(srcFile, destination);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return path;
	}
}
