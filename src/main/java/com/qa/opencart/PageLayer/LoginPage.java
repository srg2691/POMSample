package com.qa.opencart.PageLayer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	//1. private By locators: to restrict access to the locators 
	
	private By emailID=By.id("input-email");
	
	private By password=By.id("input-password");
	
	private By loginBtn=By.cssSelector("input.btn");
	
	private By forgotPswd=By.linkText("Forgotten Password");
	
	private By registerLink=By.linkText("Register");
	
	
	//2. supplying driver to this class by page constructor

	public LoginPage(WebDriver driver)
	{
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	
	//3. Page actions/methods
	@Step("getting the login page title")
	public String getLoginPageTitle()
	{
		
		
		eleUtil.waitForTitleContainsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT,AppConstants.LOGIN_PAGE_TITLE_VALUE);
		String Title=driver.getTitle();
		System.out.println(" Login Page title is .."+Title);
		
		return Title;
	}
	
	
	public String getLoginURLTitle()
	{
		
		String URL=eleUtil.waitForURLContainsAndFetch(AppConstants.DEFAULT_MEDIUM_TIME_OUT, AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE);
		System.out.println(" Login Page URL is .."+URL);
		
		return URL;
	}
	
	public boolean  forgotPswdLinkExist()
	{
		return eleUtil.waitForElementVisible(forgotPswd, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
		
	
	}
	
	@Step("login with username : {0} and password: {1}")
	public AccountsPage doLogin(String un,String pwd)
	{
		System.out.println("App credentils are "+un+" and "+pwd);
		eleUtil.waitForElementVisible(emailID, AppConstants.DEFAULT_MEDIUM_TIME_OUT).sendKeys(un);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		
		//will use element utilities instead of below
//		driver.findElement(emailID).sendKeys(un);
//		driver.findElement(password).sendKeys(pwd );
//		driver.findElement(loginBtn).click();
		return new AccountsPage(driver);
	}
	
	
	
	public RegistrationPage navigateToRegisterPage()
	{
		eleUtil.doClick(registerLink);
		return new RegistrationPage(driver);
	}

}
