package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.exceptions.ElementException;
import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

public class ElementUtil {

	private WebDriver driver;
	private Actions act;
	private JavaScriptUtil jsUtil;
	
	private static final Logger log = LogManager.getLogger(ElementUtil.class);

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		act = new Actions(driver);
		jsUtil = new JavaScriptUtil(driver);
	}

	@Step("checking the element : {0} is displayed on the page")
	public boolean isElementDisplayed(By locator) {
		try {
			return getElement(locator).isDisplayed();
		} catch (NoSuchElementException e) {
			System.out.println("Element is not displayed on the page " + locator);
			return false;
		}
	}
	
	public boolean isElementDisplayed(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			System.out.println("Element is not displayed on the page " + element);
			return false;
		}
	}

	public boolean isElementEnabled(By locator) {
		try {
			return getElement(locator).isEnabled();
		} catch (NoSuchElementException e) {
			System.out.println("Element is not enabled on the page " + locator);
			return false;
		}
	}

	public String doElementGetText(By locator) {
		return getElement(locator).getText();
	}

	public String getElementDOMAtrributeValue(By locator, String attrName) {
		return getElement(locator).getDomAttribute(attrName);
	}

	public String getElementDOMPropertyValue(By locator, String propName) {
		return getElement(locator).getDomProperty(propName);
	}

	public void doSendKeys(By locator, String value) {
		log.info("entering the value : "+ value + " into locator "+ locator);
		if (value == null) {
			log.error("value : "+ value +" is null...");
			throw new ElementException("===value can not be null==");
		}
		WebElement ele = getElement(locator);
		ele.clear();
		ele.sendKeys(value);
	}

	public void doMultipleSendKeys(By locator, CharSequence... value) {

		getElement(locator).sendKeys(value);
	}

	public void doClick(By locator) {
		log.info("clicking on element using :"+locator);
		getElement(locator).click();
	}

	public int getElementCount(By locator) {
		return getElements(locator).size();
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public List<String> getElementTextList(By locator) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleTextList = new ArrayList<String>();// pc=0, vc=10
		for (WebElement e : eleList) {
			String text = e.getText();
			if (text.length() != 0) {
				eleTextList.add(text);
			}
		}
		return eleTextList;
	}

	public boolean isElementExist(By locator) {// for single webelement
		if (getElementCount(locator) == 1) {
			//System.out.println("the element : " + locator + " is present on the page one time");
			log.info("the element : " + locator + " is present on the page one time");
			return true;
		} else {
			System.out.println("the element : " + locator + " is not present on the page one time");
			log.info("the element : " + locator + " is not present on the page one time");
			return false;
		}
	}

	public boolean isElementExist(By locator, int expectedCount) {// for multiple webelement
		if (getElementCount(locator) == expectedCount) {
			System.out.println(
					"the element : " + locator + " is present on the page one time" + expectedCount + " times");
			return true;
		} else {
			System.out.println(
					"the element : " + locator + " is present not on the page one time" + expectedCount + " times");
			return false;
		}
	}

	public void clickElement(By locator, String eleText) {
		List<WebElement> eleList = getElements(locator);
		System.out.println("total Number of Links are " + eleList.size());

		for (WebElement e : eleList) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(eleText)) {
				e.click();
				break;
			}
		}
	}

	public void doSearch(By seachLocator, String searchKey, By suggeionsLocator, String suggestionsValue)
			throws InterruptedException {
		doSendKeys(seachLocator, searchKey);
		Thread.sleep(4000);

		List<WebElement> suggList = getElements(suggeionsLocator);
		System.out.println(suggList.size());

		for (WebElement e : suggList) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(suggestionsValue)) {
				e.click();
				break;
			}
		}
	}

	public WebElement getElement(By locator) {
		WebElement element = driver.findElement(locator);
		log.info("element is found using : "+locator);
		if(Boolean.parseBoolean(DriverFactory.highlightEle)) {
			jsUtil.flash(element);
		}
		return element;
			
	}

	// ***************Select drop down utils*************

	public void doSelectByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSelectByVisisbleText(By locator, String eleText) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(eleText);
	}

	public void doSelectByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	public List<String> getDropDownValuesList(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		System.out.println("total number of option: " + optionsList.size());
		List<String> optionsValueList = new ArrayList<String>();
		for (WebElement e : optionsList) {
			String text = e.getText();
			optionsValueList.add(text);
		}

		return optionsValueList;
	}

	public int getDropDownOptionsCount(By locator) {
		Select select = new Select(getElement(locator));
		return select.getOptions().size();
	}

	public void SelectdropDownValueWithoutSelectClass(By locator, String value) {

		List<WebElement> optionList = getElements(locator);
		System.out.println(optionList.size());
		for (WebElement e : optionList) {
			String text = e.getText();
			if (text.contains(value)) {
				e.click();
				break;
			}

		}
	}

	public String getDropDownFirstSelectValue(By locator) {
		Select select = new Select(getElement(locator));
		return select.getFirstSelectedOption().getText();

	}

	// ************************Actions Utils*************************************
	private void moveToElement(By locator) {
		act.moveToElement(getElement(locator)).perform();
	}

	public void menuSubmenuHandling(By parentMenu, By childMenu) throws InterruptedException {
		moveToElement(parentMenu);
		Thread.sleep(2000);
		getElement(childMenu).click();
	}

	public void menuSubmenuHandlingLevel3(By menuLevel1, By menuLevel2, By menuLevel3, String actionType)
			throws InterruptedException {

		if (actionType.equalsIgnoreCase("click")) {
			doClick(menuLevel1);
		} else if (actionType.equalsIgnoreCase("mouseHover")) {
			moveToElement(menuLevel1);
		}

		Thread.sleep(2000);
		moveToElement(menuLevel2);
		Thread.sleep(2000);
		doClick(menuLevel3);
	}

	public void menuSubmenuHandlingLevel4(By menuLevel1, By menuLevel2, By menuLevel3, By menuLevel4, String actionType)
			throws InterruptedException {

		if (actionType.equalsIgnoreCase("click")) {
			doClick(menuLevel1);
		} else if (actionType.equalsIgnoreCase("mouseHover")) {
			moveToElement(menuLevel1);
		}

		Thread.sleep(2000);
		moveToElement(menuLevel2);
		Thread.sleep(2000);
		moveToElement(menuLevel3);
		Thread.sleep(2000);
		doClick(menuLevel4);
	}

	public void doActionsClick(By locator) {
		act.click(getElement(locator)).perform();
	}

	public void doActionsSendKeys(By locator, String Value) {
		act.sendKeys(getElement(locator), Value);
	}

	public void doSendKeysWithPause(By locator, String value, long pauseTime) {

		char ch[] = value.toCharArray();

		for (char c : ch) {
			act.sendKeys(getElement(locator), String.valueOf(c)).pause(pauseTime).perform();
		}
	}

	public void doSendKeysWithPause(By locator, String value) {

		char ch[] = value.toCharArray();

		for (char c : ch) {
			act.sendKeys(getElement(locator), String.valueOf(c)).pause(200).perform();
		}
	}

//***********************************************Wait Util*******************************************************************

	/*
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This/ does not necessarily mean that the element is visible.
	 * 
	 * Parameters:
	 */

	public WebElement waitForElementPresent(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		if(Boolean.parseBoolean(DriverFactory.highlightEle)) {
			jsUtil.flash(element);
		}
		return element;
	}

	/*
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that is greater than 0.
	 */

	@Step("waiting for element :{0} visible within the timeout: {1}")
	public WebElement waitForElementVisible(By locator, int timeOut) {
		log.info("waiting for element using By locator: "+ locator + " within time out " + timeOut);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		if(Boolean.parseBoolean(DriverFactory.highlightEle)) {
			jsUtil.flash(element);
		}
		return element;
	}

	public Alert waitForAlert(int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public String getAlertText(int timeout) {
		return waitForAlert(timeout).getText();
	}

	public void acceptAlert(int timeout) {
		waitForAlert(timeout).accept();
	}

	public void dismissAlert(int timeout) {
		waitForAlert(timeout).dismiss();
	}

	public void sendKeysInAlert(int timeout, String value) {
		waitForAlert(timeout).sendKeys(value);
	}

	@Step("waiting for page title with expected value: {0}")
	public String waitForTitleContains(String fractionTitle, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

		try {
			boolean isTitlePresent = wait.until(ExpectedConditions.titleContains(fractionTitle));
		} catch (TimeoutException e) {
			System.out.println("expected title value : " + fractionTitle + " is not present");
		}

		return driver.getTitle();
	}

	@Step("waiting for page title with expected value: {0}") //{0}= expectedTitleValue
	public String waitForTitleIs(String expectedTitleValue, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

		try {
			boolean isTitlePresent = wait.until(ExpectedConditions.titleIs(expectedTitleValue));
		} catch (TimeoutException e) {
			System.out.println("expected title value : " + expectedTitleValue + " is not present");
		}

		return driver.getTitle();
	}

	@Step("waiting for url with expected fraction value: {0}")
	public String waitForURLContains(String fractionURLValue, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

		try {
			boolean isTitlePresent = wait.until(ExpectedConditions.urlContains(fractionURLValue));
		} catch (TimeoutException e) {
			System.out.println("expected URL value : " + fractionURLValue + " is not present");
		}

		return driver.getCurrentUrl();
	}

	public String waitForURLIs(String expectedURL, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

		try {
			boolean isTitlePresent = wait.until(ExpectedConditions.urlToBe(expectedURL));
		} catch (TimeoutException e) {
			System.out.println("expected URL value : " + expectedURL + " is not present");
		}

		return driver.getCurrentUrl();
	}

	public boolean waitForWindow(int expectedNoOfWindows, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

		try {
			return wait.until(ExpectedConditions.numberOfWindowsToBe(expectedNoOfWindows));
		} catch (TimeoutException e) {
			System.out.println("expected number of windows is not correct");
			return false;
		}

	}

	public boolean waitForFrame(By frameLocator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
			return true;
		} catch (TimeoutException e) {
			System.out.println("frame is not present on the page");
			return false;
		}
	}

	public boolean waitForFrame(int frameIndex, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
			return true;
		} catch (TimeoutException e) {
			System.out.println("frame is not present on the page");
			return false;
		}
	}

	public boolean waitForFrame(String frameName, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
			return true;
		} catch (TimeoutException e) {
			System.out.println("frame is not present on the page");
			return false;
		}

	}

	/*
	 * An expectation for checking that there is at least one element present on a
	 * web page.
	 * 
	 * Parameters: locator used to find the element Returns:
	 */

	public List<WebElement> waitForElementsPresence(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	/*
	 * An expectation for checking that all elements present on the web page that
	 * match the locator are visible. Visibility means that the elements are not
	 * only displayed but also have a height and width that is greater than 0.
	 * 
	 * Parameters: locator used to find the element Returns:
	 */

	public List<WebElement> waitForElementsVisisble(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

	}

	/*
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 * 
	 * Parameters: locator used to find the element Returns:
	 */

	public void clickElementsVisible(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
		;
	}

	// *********************************FluentWaitUtils********************************
	public WebElement waitForElementVisibleWithFluentWait(By locator, int timeout, int pollingtime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(pollingtime))
				.ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class)
				.withMessage("=====ELEMENT IS NOT VISIBLE ON THE PAGE=====");

		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

	}

	public WebElement waitForElementPresenceWithFluentWait(By locator, int timeout, int pollingtime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(pollingtime))
				.ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class)
				.withMessage("=====ELEMENT IS NOT PRESENT ON THE PAGE=====");

		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));

	}

	public void waitForFrameWithFluentWait(By FrameLocatoe, int timeout, int pollingtime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(pollingtime))
				.ignoring(NoSuchFrameException.class)
				.withMessage("=====FRAME IS NOT VISIBLE ON THE PAGE=====");

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(FrameLocatoe));

	}

	public Alert waitForAlertWithFluentWait(int timeout, int pollingtime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(pollingtime))
				.ignoring(NoAlertPresentException.class)
				.withMessage("=====ALERT IS NOT VISIBLE ON THE PAGE=====");

		return wait.until(ExpectedConditions.alertIsPresent());

	}

}
