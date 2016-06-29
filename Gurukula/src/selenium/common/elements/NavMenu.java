package selenium.common.elements;

import org.openqa.selenium.WebElement;

public class NavMenu extends DropDown {

	public NavMenu(WebElement element) {
		super(element);
	}

	public boolean isPillActive() {
		WebElement li = (WebElement) jsExecutor.executeScript("$(arguments[0].closest('li.active')", element);
		return li == null ? false : true;
	}
}
