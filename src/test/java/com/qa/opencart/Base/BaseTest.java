package com.qa.opencart.Base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.PageLayer.AccountsPage;
import com.qa.opencart.PageLayer.LoginPage;
import com.qa.opencart.PageLayer.ProductInfoPage;
import com.qa.opencart.PageLayer.RegistrationPage;
import com.qa.opencart.PageLayer.SearchPage;
import com.qa.opencart.PageLayer.ViewCartPopUpPage;
import com.qa.opencart.factory.DriverFactory;

public class BaseTest {

	DriverFactory df;
	WebDriver driver;
	protected Properties prop;
	protected LoginPage loginPage;
	protected AccountsPage accPage;
	protected SearchPage searchPage;
	protected ProductInfoPage productInfoPage;
	protected ViewCartPopUpPage viewCartPopUpPage;
	protected RegistrationPage registerPage;
	
	protected SoftAssert softAssert;
	
	
	@BeforeTest
	public void setup() {
		df = new DriverFactory();
		prop = df.initProp();
		driver = df.initDriver(prop);
		loginPage = new LoginPage(driver);
		
		
		softAssert = new SoftAssert();

	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}