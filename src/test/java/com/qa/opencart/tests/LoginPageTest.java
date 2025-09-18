package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

@Epic("EP-100: Design the Open Cart App Login Page")
@Feature("F-101: design open cart login feature")
@Story("US-50: develop login core features: title, url, user is able to login")
public class LoginPageTest extends BaseTest {

	@Description("loging page title test....")
	@Owner("Lucky Automation Labs")
	@Severity(SeverityLevel.MINOR)
	@Test
	
	public void loginPageTitleTest() {
		String actTitle = loginPage.getLoginPageTitle();
		ChainTestListener.log("Login page title: "+ actTitle);
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE);
	}

	@Description("loging page url test......")
	@Owner("Lucky Automation Labs")
	@Severity(SeverityLevel.MINOR)
	@Test
	
	public void loginPageURLTest() {
		String actURL = loginPage.getLoginPageURL();
		ChainTestListener.log("Login page url: "+ actURL);
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_FRACTION_URL));
	}

	@Description("forgot password link exist test......")
	@Owner("Lucky Automation Labs")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void isForgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForPwdLinkExist());
	}

	@Description("loging page header test....")
	@Owner("Lucky Automation Labs")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void isLoginPageHeaderExistTest() {
		Assert.assertTrue(loginPage.isHeaderExist());
	}

	@Description("user is able to login to app with correct credentials....")
	@Owner("Lucky Automation Labs")
	@Severity(SeverityLevel.BLOCKER)
	@Test
	public void loginTest() {
		accPage  = loginPage.doLogin(prop.getProperty("username") , prop.getProperty("password"));
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}

}
