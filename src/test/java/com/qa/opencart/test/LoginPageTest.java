package com.qa.opencart.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.Base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

//sample annotations present from allure reports @epic, @story,@severity,@description
@Epic("EPIC -100: design login for open cart app")
@Story("Login 101: design login page features for open cart application")
public class LoginPageTest extends BaseTest {

	@Severity(SeverityLevel.NORMAL)
	@Description("getting title of the page")
	@Test(priority = 1)
	public void loginPageTitleTest() {
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE_VALUE);// assertion should always be in the test
																			// class and NEVER in the Page class

	}

	@Test(priority = 2)
	public void loginPageURLTest() {
		String actURL = loginPage.getLoginURLTitle();
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE));
	}

	@Test(priority = 3)
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.forgotPswdLinkExist());

	}

	@Test(priority = 4)
	public void loginTest() {
		// assertion to check if login was successful
		accPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
		String title = accPage.getAccPageTitle();
		Assert.assertEquals(title, "My Account");
		Assert.assertTrue(accPage.isLogoutLinkExist());

	}

}
