package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.CsvUtil;
import com.qa.opencart.utils.ExcelUtil;
import com.qa.opencart.utils.StringUtils;

public class RegisterPageTest extends BaseTest {

	//BT(chrome+loginUrl) --> BC(move to register Page) -->@Test
	@BeforeClass
	public void goToRegisterPage() {
		registerPage = loginPage.navigateToRegisterPage();
	}
	
	@DataProvider
	public Object[][] getRegData() {
		return new Object[][] {
			{"harpreet" , "automation" , "9846782829" , "harpreet@123" , "yes"},
			{"ratul" , "saha" , "9846782827" , "ratul@123" , "no"},
			{"sandhya" , "automation" , "9846782828" , "sandhya@123" , "yes"}
		};
	}
	
	@DataProvider
	public Object[][] getRegSheetData() {
		return ExcelUtil.getTestData("register");
	}
	
	@DataProvider
	public Object[][] getRegCSVData() {
		return CsvUtil.csvData("register");
	}
	
	
	@Test(dataProvider = "getRegCSVData")
	public void registerTest(String firstName, String lastName, String telephone, String password, String subscribe) {
		Assert.assertTrue(registerPage.userRegister(firstName, lastName, StringUtils.getRandomEmail() , telephone, password, subscribe));
	}
}
