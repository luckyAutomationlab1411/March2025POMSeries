package com.qa.opencart.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	

	// private By locators: page objects
	private final By emailID = By.id("input-email");
	private final By pwd = By.id("input-password");
	private final By loginBtn = By.xpath("//input[@value='Login']");
	private final By forgotPwsLink = By.linkText("Forgotten Password");
	private final By header = By.tagName("h2");
	private final By registerLink = By.linkText("Register");
	private final By loginErrorMesg = By.xpath("//div[@class='alert alert-danger alert-dismissible']");
	
	private static final Logger log = LogManager.getLogger(LoginPage.class);

	// public constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// public page methods/actions
	@Step("getting the login page title...")
	public String getLoginPageTitle() {
		String title = eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, AppConstants.DEFAULT_SHORT_WAIT);
		//System.out.println("login page title; " + title);
		log.info("login page title; " + title);
		return title;
	}

	@Step("getting the login page url...")
	public String getLoginPageURL() {
		String url = eleUtil.waitForURLContains(AppConstants.LOGIN_PAGE_FRACTION_URL, AppConstants.DEFAULT_SHORT_WAIT);
		//System.out.println("login page title; " + url);
		log.info("login page title; " + url);
		return url;
	}

	@Step("forgot password link exist...")
	public boolean isForPwdLinkExist() {
		return eleUtil.isElementDisplayed(forgotPwsLink);
	}

	@Step("page header exist...")
	public boolean isHeaderExist() {
		return eleUtil.isElementDisplayed(header);
	}

	@Step("login with username: {0} and password: {1}")
	public AccountsPage doLogin(String appUsername, String appPassword) {
		//System.out.println("Application credentials: " + appUsername + " : " + appPassword);
		log.info("Application credentials: " + appUsername + " : " + "********");
		eleUtil.waitForElementVisible(emailID, AppConstants.DEFAULT_MEDIUM_WAIT).sendKeys(appUsername);
		eleUtil.doSendKeys(pwd, appPassword);
		eleUtil.doClick(loginBtn);
		return new AccountsPage(driver);
	}
	
	@Step("login with incorrct username: {0} and password: {1}")
	public boolean doLoginWithInvalidCredentials(String invalidUN, String invalidPWD) {
		log.info("Invalid application credentials: "+ invalidUN + " : "+invalidPWD);
		
		WebElement emailEle = eleUtil.waitForElementVisible(emailID, AppConstants.DEFAULT_MEDIUM_WAIT);
		emailEle.clear();
		emailEle.sendKeys(invalidUN);
		eleUtil.doSendKeys(pwd, invalidPWD);
		eleUtil.doClick(loginBtn);
		String errormesg = eleUtil.doElementGetText(loginErrorMesg);
		log.info("invalid credentials error message: "+errormesg);
		if(errormesg.contains(AppConstants.LOGIN_BLANK_CRED_MESG)){
			return true;
		}
		
		else if(errormesg.contains(AppConstants.LOGIN_INVALID_CRED_MESG)) {
			return true;
		}
		return false;
	}
	
	@Step("navigating to the register page")
	public RegisterPage navigateToRegisterPage() {
		log.info("trying to navigate to resgister page...");
		eleUtil.waitForElementVisible(registerLink, AppConstants.DEFAULT_SHORT_WAIT).click();;
		return new RegisterPage(driver);
	}
 
}
