package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private Map<String , String> productMap;

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	private final By header = By.tagName("h1");
	private final By productImages = By.xpath("//li//a[@class='thumbnail']");
	private final By productMetaData = By.xpath("(//div[@class='col-sm-4']//ul[@class='list-unstyled'])[1]/li"); 
	private final By productPriceData = By.xpath("(//div[@class='col-sm-4']//ul[@class='list-unstyled'])[2]/li");
	
	public String getProductHeader() {
		String headerVal = eleUtil.waitForElementVisible(header, AppConstants.DEFAULT_SHORT_WAIT).getText();
		System.out.println("product header is :" + headerVal);
		return headerVal;
	}

	public int getProductImages() {
		int imagesCount = eleUtil.waitForElementsVisisble(productImages, AppConstants.DEFAULT_MEDIUM_WAIT).size();
		System.out.println("Total number of images : " + imagesCount);
		return imagesCount;
	}
	
	public Map<String , String> getProductData() {
		//productMap = new HashMap<String, String>(); //does not maintain the order
		
		productMap = new LinkedHashMap<String, String>(); //maintain the order
		
		//productMap = new TreeMap<String, String>(); //sorted key in capital letter with alaphabatical order then same priority goes to small letter
		
		productMap.put("productname", getProductHeader());
		productMap.put("productImages", String.valueOf(getProductImages()));
		
		getProductMetaDta();
		getProductpriceDta();
		
		System.out.println("==============Product Data==============: \n"+ productMap);
		return productMap;
	}
	
	// Brand: Apple
	// Product Code: Product 16
	// Reward Points: 600
	// Availability: In Stock
	
	private void getProductMetaDta() {
		List<WebElement> metaList = eleUtil.waitForElementsVisisble(productMetaData, AppConstants.DEFAULT_SHORT_WAIT);
		System.out.println("total meata data: "+ metaList.size());
		
		for(WebElement e : metaList) {
			String metaData = e.getText();
			String meta[] = metaData.split(":");
			String metakey = meta[0].trim();
			String metaVal = meta[1].trim();
			productMap.put(metakey, metaVal);
		}
	}
	
	// $602.00
	// Ex Tax: $500.00
	
	private void getProductpriceDta() {
		List<WebElement> priceList = eleUtil.waitForElementsVisisble(productPriceData, AppConstants.DEFAULT_SHORT_WAIT);
		System.out.println("total price data: "+ priceList.size());//2
		
		String priceValue = priceList.get(0).getText();
		String exTaxValue =priceList.get(1).getText().split(":")[1].trim();
		
		productMap.put("productprice", priceValue);
		productMap.put("extraprice", exTaxValue);
	}
}
