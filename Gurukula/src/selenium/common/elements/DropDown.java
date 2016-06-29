package selenium.common.elements;

import org.openqa.selenium.WebElement;

import selenium.common.MenuOption;

public class DropDown extends BaseElement {

	public DropDown(WebElement element) {
		super(element);
	}

	public void openDropDown() {
		jsExecutor.executeScript("arguments[0].closest('li.dropdown:not(.open)>a').click()", element);
	}

	public WebElement getMenuOption(MenuOption option) {
		openDropDown();
		return (WebElement) jsExecutor.executeScript(
				"return arguments[0].closest('li.dropdown').querySelector('ul.dropdown-menu>li>a[ui-sref=" + option.getSref() + "]')", element);
	}

	public boolean isOptionDisplayed(MenuOption option) {
		return getMenuOption(option) != null ? true : false;
	}

	public void selectOption(MenuOption option) {
		getMenuOption(option).click();
	}
}
