package selenium.common.elements;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class Pager extends BaseElement {

	public Pager(WebElement element) {
		super(element);
	}

	public List<WebElement> getVisibleLinks() {
		return element.findElements(By.cssSelector("li:not(.ng-hide)"));
	}

	public boolean isFirstVisible() {
		return isVisible("li:first-child:not(.ng-hide)");
	}

	public boolean isLastVisible() {
		return isVisible("li:last-child:not(.ng-hide)");
	}

	public boolean isPrevVisible() {
		return isVisible("li::nth-child(2):not(.ng-hide)");
	}

	public boolean isNextVisible() {
		return isVisible("li::nth-child(3):not(.ng-hide)");
	}

	public void clickFirst() {
		WebElement link = element.findElement(By.cssSelector("li:first-child:not(.ng-hide)"));
		try {
			link.click();
		} catch (NoSuchElementException e) {
			logger.error("First link is not visible", e);
			throw new RuntimeException(e);
		}
	}

	public void clickLast() {
		WebElement link = element.findElement(By.cssSelector("li:last-child:not(.ng-hide)"));
		try {
			link.click();
		} catch (NoSuchElementException e) {
			logger.error("Last link is not visible", e);
			throw new RuntimeException(e);
		}
	}

	public void clickPrev() {
		WebElement link = element.findElement(By.cssSelector("li:nth-child(2):not(.ng-hide)"));
		try {
			link.click();
		} catch (NoSuchElementException e) {
			logger.error("Prev link is not visible", e);
			throw new RuntimeException(e);
		}
	}

	public void clickNext() {
		WebElement link = element.findElement(By.cssSelector("li:last-child(3):not(.ng-hide)"));
		try {
			link.click();
		} catch (NoSuchElementException e) {
			logger.error("Next link is not visible", e);
			throw new RuntimeException(e);
		}
	}

	private boolean isVisible(String locator) {
		try {
			element.findElement(By.cssSelector(locator));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

}
