package selenium.common.elements;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

import selenium.common.TestProperties;

public class BaseElement implements WebElement {

	protected final WebDriver driver = TestProperties.getInstance().getDriver();
	protected final JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
	private static final Logger logger = LogManager.getLogger(BaseElement.class);

	protected WebElement element;

	public BaseElement(WebElement element) {
		this.element = element;
		initChildComponents();
	}

	public void initChildComponents() {

	}

	public WebElement waitForReady() {
		sleep(0.3);
		return waitForReady(TestProperties.getInstance().getWaitTime());
	}

	public WebElement waitForReady(int secondsToSleep) {
		FluentWait<WebElement> wait = new FluentWait<WebElement>(element);

		wait.withTimeout(secondsToSleep, TimeUnit.SECONDS).withMessage("waitForReady timed out. Page not ready")
				.until(new Predicate<WebElement>() {
					@Override
					public boolean apply(WebElement element) {
						return isPageReady();
					}
				});

		return element;
	}

	public void waitUntilHidden(int secondsToSleep) {
		FluentWait<BaseElement> wait = new FluentWait<BaseElement>(this);

		wait.withTimeout(secondsToSleep, TimeUnit.SECONDS)
				.withMessage("waitUntilHidden timed out. Element did not disappear in alloted time")
				.until(new Predicate<BaseElement>() {
					@Override
					public boolean apply(BaseElement element) {
						return !element.exists();
					}
				});
	}

	public void waitUntilHidden() {
		waitUntilHidden(TestProperties.getInstance().getWaitTime());
	}

	public BaseElement waitUntilEnabled() {
		return waitUntilEnabled(TestProperties.getInstance().getWaitTime());
	}

	public BaseElement waitUntilEnabled(int secondsToSleep) {
		FluentWait<BaseElement> wait = new FluentWait<BaseElement>(this);

		wait.withTimeout(secondsToSleep, TimeUnit.SECONDS)
				.withMessage("waitUntilEnabled timed out. Element " + this + " did not become enabled in alloted time")
				.until(new Predicate<BaseElement>() {
					@Override
					public boolean apply(BaseElement element) {
						return element.isEnabled();
					}
				});

		return this;
	}

	@Override
	public void clear() {
		element.clear();
	}

	@Override
	public void click() {
		try {
			element.click();
		} catch (WebDriverException e) {
			try {
				logger.debug("Click Failed, Attempting to click by scrolling into view with bottom align option");
				scrollIntoView();
				element.click();
			} catch (WebDriverException ef) {
				try {
					logger.debug("Click Failed, Attempting to click by scrolling into view with top align option");
					scrollIntoView(true);
					element.click();
				} catch (WebDriverException e2) {
					try {
						logger.debug("Click Failed, Attempting to click by scrolling down by 200 pixels");
						jsExecutor.executeScript("window.scrollBy(0,200)");
						element.click();
					} catch (WebDriverException e3) {
						logger.debug("Click Failed, Attempting to click by scrolling up by 200 pixels");
						jsExecutor.executeScript("window.scrollBy(0,-200)");
						element.click();
					}
				}
			}
		}
	}

	public void clickCorner() {
		new Actions(driver).moveToElement(element, 5, 5).click().build().perform();
	}

	@Override
	public WebElement findElement(By by) {
		return element.findElement(by);
	}

	@Override
	public List<WebElement> findElements(By by) {
		return element.findElements(by);
	}

	@Override
	public String getAttribute(String name) {
		return element.getAttribute(name);
	}

	public String getClassName() {
		return element.getAttribute("class");
	}

	@Override
	public String getCssValue(String propertyName) {
		return element.getCssValue(propertyName);
	}

	@Override
	public String getTagName() {
		return element.getTagName();
	}

	@Override
	public String getText() {
		return element.getText();
	}

	public String getType() {
		return element.getAttribute("type");
	}

	@Override
	public boolean isDisplayed() {
		waitForReady();
		return element.isDisplayed();
	}

	public boolean exists() {
		try {
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			logger.debug("Element does not exist");
			return false;
		}
	}

	@Override
	public boolean isEnabled() {
		return element.isEnabled();
	}

	public boolean isEnabledByClass(BaseElement elementToCheck) {
		return !elementToCheck.getClassName().contains("disabled");
	}

	@Override
	public boolean isSelected() {
		return element.isSelected();
	}

	@Override
	public void sendKeys(java.lang.CharSequence... keysToSend) {
		element.sendKeys(keysToSend);
	}

	@Override
	public void submit() {
		element.submit();
	}

	public void doubleClick() {
		new Actions(driver).moveToElement(element, (element.getSize().getWidth() / 2), 1).doubleClick().perform();
	}

	public void contextClick() {
		new Actions(driver).contextClick(element).perform();
	}

	public void scrollIntoView() {
		scrollIntoView(false);
	}

	public void scrollIntoView(Boolean topAlign) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(" + topAlign + ");", element);
	}

	public void blockDisplay() {
		((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block';", element);
	}

	protected void sleep(final Double seconds) {
		try {
			Thread.sleep((long) (seconds * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public WebElement waitFor(final By locator) {
		return waitFor(element, locator);
	}

	public WebElement waitFor(final SearchContext context, final By locator) {
		Wait<By> wait = new FluentWait<By>(locator)
				.withTimeout(TestProperties.getInstance().getWaitTime(), TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		return wait.until(new Function<By, WebElement>() {
			@Override
			public WebElement apply(By locator) {
				return context.findElement(locator);
			}
		});
	}

	public WebElement waitForElement(org.openqa.selenium.By by) {
		FluentWait<org.openqa.selenium.By> wait = new FluentWait<org.openqa.selenium.By>(by);

		long secondsToSleep = TestProperties.getInstance().getWaitTime();
		wait.withTimeout(secondsToSleep, TimeUnit.SECONDS)
				.withMessage("waitForReady timed out. Element " + by.toString() + " not present")
				.until(new Predicate<org.openqa.selenium.By>() {
					@Override
					public boolean apply(org.openqa.selenium.By by) {
						try {
							driver.findElement(by);
							return true;
						} catch (NoSuchElementException e) {
							return false;
						}
					}
				});

		return driver.findElement(by);
	}

	public void waitForElement(BaseElement element) {
		waitForElement(element, TestProperties.getInstance().getWaitTime());
	}

	public void waitForElement(BaseElement element, long secondsToWait) {
		FluentWait<BaseElement> wait = new FluentWait<BaseElement>(element);

		wait.withTimeout(secondsToWait, TimeUnit.SECONDS)
				.withMessage("waitForReady timed out. Element " + element.toString() + " is still not present")
				.until(new Predicate<BaseElement>() {
					@Override
					public boolean apply(BaseElement element) {
						try {
							if (element.isDisplayed()) {
								return true;
							}
						} catch (NoSuchElementException e) {
							return false;
						}
						return true;
					}
				});
	}

	public List<WebElement> waitForElements(final By locator) {
		waitFor(locator);

		return element.findElements(locator);
	}

	public void waitForElementToDisappear(BaseElement element) {
		waitForElementToDisappear(element, TestProperties.getInstance().getWaitTime());
	}

	public void waitForElementToDisappear(BaseElement element, long secondsToWait) {
		FluentWait<BaseElement> wait = new FluentWait<BaseElement>(element);

		wait.withTimeout(secondsToWait, TimeUnit.SECONDS)
				.withMessage("waitForReady timed out. Element " + element.toString() + " is still present")
				.until(new Predicate<BaseElement>() {
					@Override
					public boolean apply(BaseElement element) {
						try {
							if (!element.isDisplayed()) {
								return true;
							}
						} catch (NoSuchElementException e) {
							return true;
						} catch (StaleElementReferenceException ex) {
							return true;
						}
						return false;
					}
				});
	}

	public List<WebElement> findElements(final By locator, final int expectedTotal) {
		Wait<By> wait = new FluentWait<By>(locator)
				.withTimeout(TestProperties.getInstance().getWaitTime(), TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		List<WebElement> resultList = wait.until(new Function<By, List<WebElement>>() {
			@Override
			public List<WebElement> apply(By locator) {
				List<WebElement> elements = findElements(locator);
				if (elements.size() < expectedTotal) {
					return null;
				}
				return elements;
			}
		});

		int actualTotal = resultList.size();
		if (actualTotal < expectedTotal) {
			logger.info("Expected amount of elements not found: {} instead of {}", Integer.toString(actualTotal),
					Integer.toString(expectedTotal));
		} else if (actualTotal > expectedTotal) {
			logger.info("Note more elements were found than expected: {} instead of {}", Integer.toString(actualTotal),
					Integer.toString(expectedTotal));
		}
		return resultList;
	}

	protected WebElement findElement(SearchContext context, By by) {
		try {
			return context.findElement(by);
		} catch (NoSuchElementException e) {
			return null;
		} catch (StaleElementReferenceException e) {
			return null;
		}
	}

	public WebElement getElement() {
		return element;
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
		throw new UnsupportedOperationException("getScreenshotAs");
	}

	/*
	 * protected String getJqueryTextFilterFunction(String text) { return
	 * "function () { return (this.textContent || this.innerText).trim().search('^"
	 * + escapeSingleSlash(text) + "$') != -1;}"; }
	 */

	protected String escapeSingleSlash(String selector) {
		return selector.replace("'", "\\'");
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

	@Override
	public Point getLocation() {
		return null;
	}

	@Override
	public Rectangle getRect() {
		return null;
	}

	@Override
	public Dimension getSize() {
		return null;
	}
}
