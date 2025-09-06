package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class SearchResultsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	private final By searchResults = By.xpath("//div[@class='product-thumb']");
	private final By resultsHeader = By.xpath("//div[@id='content']//h1");
	
	
	public int getSearchResultsCount() {
		int resultCount = eleUtil.waitForElementsPresence(searchResults, AppConstants.DEFAULT_MEDIUM_WAIT).size();
		System.out.println("results count  :" +resultCount);
		return resultCount;
	}
	
	public String getResultsHeaderValue() {
		String header = eleUtil.doElementGetText(resultsHeader);
		System.out.println("results header :" +header);
		return header;
	}
	
	public ProductInfoPage selectProduct(String productName){
		System.out.println("product name :" +productName);
		By product = By.linkText(productName);
		eleUtil.waitForElementVisible(product, AppConstants.DEFAULT_MEDIUM_WAIT).click();
		return new ProductInfoPage(driver);
	}
}
