package com.qa.opencart.pages;

import org.openqa.selenium.By;

public class DemoPage {

	
	private final By emailID = By.id("input-email");
	private final By pwd = By.id("input-password");
	private final By loginBtn = By.xpath("//input[@value='Login']");
	private final By forgotPwsLink = By.linkText("Forgotten Password");
	private final By header = By.tagName("h2");
	private final By registerLink = By.linkText("Register");
	private final By loginErrorMesg = By.xpath("//div[@class='alert alert-danger alert-dismissible']");
}
