package selenium.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Predicate;

import selenium.common.TestProperties;
import selenium.common.elements.BaseElement;

public class BasePage {
	private static final Logger LOGGER = LogManager.getLogger(BasePage.class);
	protected int defaultWaitTimeout = TestProperties.getInstance().getWaitTime();
	protected WebDriver driver = TestProperties.getInstance().getDriver();
	protected final JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

	public BasePage waitForReady() {
		return waitForReady(defaultWaitTimeout, false);
	}

	public BasePage waitForReady(boolean withSave) {
		return waitForReady(defaultWaitTimeout, withSave);
	}

	public BasePage waitForReady(int secondsToSleep) {
		return waitForReady(secondsToSleep, false);
	}

	public BasePage waitForReady(int secondsToSleep, boolean withSave) {
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
		javascriptExecutor.executeScript("if (window.jQuery) { window.jQuery.fx.off = true; }");
		if (!isPageReady()) {
			FluentWait<BasePage> wait = new FluentWait<BasePage>(this).withTimeout(secondsToSleep, TimeUnit.SECONDS);
			wait = wait.pollingEvery(100, TimeUnit.MILLISECONDS);

			wait.withMessage("WaitForReady timed out. Page is not ready.").until(new Predicate<BasePage>() {
				@Override
				public boolean apply(BasePage element) {
					return isPageReady();
				}
			});
		}

		LOGGER.info("Page loaded successfully");
		return this;
	}

	public WebElement waitForElement(By by) {
		return waitForElement(by, defaultWaitTimeout);
	}

	public WebElement waitForElement(By by, int timeout) {
		FluentWait<By> wait = new FluentWait<By>(by).withTimeout(timeout, TimeUnit.SECONDS);
		wait = wait.pollingEvery(100, TimeUnit.MILLISECONDS);

		wait.withMessage("waitForElement timed out. Element " + by.toString() + " is not present")
				.until(new Predicate<By>() {
					@Override
					public boolean apply(By by) {
						try {
							driver.findElement(by);
							return true;
						} catch (final NoSuchElementException e) {
							LOGGER.debug("Waiting for element to load", by);
							return false;
						}
					}
				});

		return driver.findElement(by);
	}

	public List<WebElement> waitForElements(By by) {
		return waitForElements(by, defaultWaitTimeout, false);
	}

	public List<WebElement> waitForElements(By by, boolean expectsNoResult) {
		return waitForElements(by, defaultWaitTimeout, expectsNoResult);
	}

	public List<WebElement> waitForElements(By by, int secondsToWait) {
		return waitForElements(by, secondsToWait, false);
	}

	public List<WebElement> waitForElements(By by, int secondsToWait, boolean expectsNoResult) {
		List<WebElement> elements = driver.findElements(by);
		if (!elements.isEmpty()) {
			Assert.assertFalse("Found elements for none " + by, expectsNoResult);
			return elements;
		}

		FluentWait<By> wait = new FluentWait<By>(by).withTimeout(secondsToWait, TimeUnit.SECONDS);
		wait = wait.pollingEvery(200, TimeUnit.MILLISECONDS);
		wait = wait.withMessage("waitForElements timed out. Elements " + by.toString() + "are not present");

		try {
			wait.until(new Predicate<By>() {
				@Override
				public boolean apply(By by) {
					if (!driver.findElements(by).isEmpty()) {
						return true;
					} else {
						LOGGER.debug("Waiting for elements to get loaded ", by);
						return false;
					}
				}
			});
		} catch (TimeoutException e) {
			LOGGER.debug("FluentWait timed out for {}", by);
		}

		elements = driver.findElements(by);
		if (elements.isEmpty() && !expectsNoResult) {
			Assert.fail("Waited for elements " + by + ", but none ever appeared");
		}
		return elements;
	}

	public void waitForElement(BaseElement element) {
		waitForElement(element, defaultWaitTimeout);
	}

	public void waitForElement(BaseElement element, long secondsToWait) {
		FluentWait<BaseElement> wait = new FluentWait<BaseElement>(element).withTimeout(secondsToWait,
				TimeUnit.SECONDS);
		wait = wait.pollingEvery(100, TimeUnit.MILLISECONDS);

		wait.withMessage("waitForReady timed out. Element " + element.toString() + " is still not present")
				.until(new Predicate<BaseElement>() {
					@Override
					public boolean apply(BaseElement element) {
						try {
							if (element.isDisplayed()) {
								return true;
							}
						} catch (final NoSuchElementException e) {
							return false;
						}
						return true;
					}
				});
	}

	public boolean isElementPresent(By by) {
		try {
			TestProperties.getInstance().setTimeouts(10, TimeUnit.SECONDS);
			return driver.findElement(by).isDisplayed();
		} catch (final NoSuchElementException e) {
			return false;
		} finally {
			TestProperties.getInstance().setTimeouts();
		}
	}

	public boolean isElementPresent(By by, int waitTime) {
		try {
			TestProperties.getInstance().setTimeouts(waitTime, TimeUnit.SECONDS);
			return driver.findElement(by).isDisplayed();
		} catch (final NoSuchElementException e) {
			return false;
		} finally {
			TestProperties.getInstance().setTimeouts();
		}
	}

	public boolean isElementPresent(BaseElement element) {
		try {
			return element.isDisplayed();
		} catch (final NoSuchElementException e) {
			return false;
		}
	}

	private static boolean isPageReady() {
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor) TestProperties.getInstance().getDriver();
		boolean jQueryAnimationDone, jQueryReady, docReadyState;

		// Document ready State
		docReadyState = (Boolean) javascriptExecutor.executeScript("return document.readyState == 'complete'");

		// wait for jQuery animations to complete
		jQueryReady = (Boolean) javascriptExecutor
				.executeScript("return (typeof(jQuery) != 'undefined') && jQuery.isReady");

		// wait for jQuery animations to complete
		jQueryAnimationDone = (Boolean) javascriptExecutor
				.executeScript("return (typeof(jQuery) != 'undefined') && (jQuery(':animated').length === 0)");

		return (docReadyState && jQueryReady && jQueryAnimationDone);
	}
}
