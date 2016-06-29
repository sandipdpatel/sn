package selenium.common.elements;

import org.openqa.selenium.WebElement;

public class NavPill extends BaseElement {

	public NavPill(WebElement element) {
		super(element);
	}

	public boolean isPillActive() {
		WebElement li = (WebElement) jsExecutor.executeScript("$(arguments[0].closest('li.active')", element);
		return li == null ? false : true;
	}
}
