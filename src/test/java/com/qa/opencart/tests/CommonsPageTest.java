package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class CommonsPageTest extends BaseTest {

	@Test
	public void checkCommonElementsOnLoginPageTest() {
		
		SoftAssert softAssert = new SoftAssert();
		
		softAssert.assertTrue(commonsPage.isLogoExist());
		softAssert.assertTrue(commonsPage.isSearchFieldExist());
		
		List<String> footeList = commonsPage.footerLinksExist();
		softAssert.assertEquals(footeList.size(), AppConstants.DEFAULT_FOOTER_COUNT);
		
		softAssert.assertAll();
	}
	
	@Test
	public void checkCommonElementsOnAccountsPageTest() {
		
		accPage  = loginPage.doLogin(prop.getProperty("username") , prop.getProperty("password"));
		
		SoftAssert softAssert = new SoftAssert();
		
		softAssert.assertTrue(commonsPage.isLogoExist());
		softAssert.assertTrue(commonsPage.isSearchFieldExist());
		
		List<String> footeList = commonsPage.footerLinksExist();
		softAssert.assertEquals(footeList.size(), AppConstants.DEFAULT_FOOTER_COUNT);
		
		softAssert.assertAll();
	}
}
