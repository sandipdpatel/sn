package selenium.common.pagefactory;

import java.lang.reflect.Field;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.DefaultElementLocator;
import org.openqa.selenium.support.ui.Clock;
import org.openqa.selenium.support.ui.SlowLoadableComponent;
import org.openqa.selenium.support.ui.SystemClock;

import selenium.common.TestProperties;

public class AjaxElementLocator extends DefaultElementLocator {
	protected int timeOutInSeconds;
	private final Clock clock;

	public AjaxElementLocator(WebDriver driver, Field field) {
		this(new SystemClock(), driver, field);
	}

	public AjaxElementLocator(Clock clock, WebDriver driver, Field field) {
		super(driver, field);
		this.clock = clock;
	}

	@Override
	public WebElement findElement() {
		timeOutInSeconds = TestProperties.getInstance().getWaitTime();
		SlowLoadingElement loadingElement = new SlowLoadingElement(clock, timeOutInSeconds);
		try {
			return loadingElement.get().getElement();
		} catch (NoSuchElementError e) {
			throw new NoSuchElementException(
					String.format("Timed out after %d seconds. %s", timeOutInSeconds, e.getMessage()), e.getCause());
		}
	}

	protected long sleepFor() {
		return 250;
	}

	protected boolean isElementUsable(WebElement element) {
		return true;
	}

	private class SlowLoadingElement extends SlowLoadableComponent<SlowLoadingElement> {
		private NoSuchElementException lastException;
		private WebElement element;

		public SlowLoadingElement(Clock clock, int timeOutInSeconds) {
			super(clock, timeOutInSeconds);
		}

		@Override
		protected void load() {
			// Does nothing
		}

		@Override
		protected long sleepFor() {
			return AjaxElementLocator.this.sleepFor();
		}

		@Override
		protected void isLoaded() throws Error {
			try {
				element = AjaxElementLocator.super.findElement();
				if (!isElementUsable(element)) {
					throw new NoSuchElementException("Element is not usable");
				}
			} catch (NoSuchElementException e) {
				lastException = e;
				throw new NoSuchElementError("Unable to locate the element", e);
			}
		}

		public NoSuchElementException getLastException() {
			return lastException;
		}

		public WebElement getElement() {
			return element;
		}
	}

	private static class NoSuchElementError extends Error {
		private NoSuchElementError(String message, Throwable throwable) {
			super(message, throwable);
		}
	}
}
