package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class SearchTest extends BaseTest{

	//BT(chrome+url) -> BC(loginPage) ->Test
	@BeforeClass
	public void searchSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username") , prop.getProperty("password"));
	}
	
	@Test
	public void searchTest() {
		searchResultsPage = accPage.doSearch("macbook");
		productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
		String actualHeader = productInfoPage.getProductHeader();
		Assert.assertEquals(actualHeader, "MacBook Pro");
	}
	
}
