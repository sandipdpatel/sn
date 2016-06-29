package selenium.common.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import selenium.pages.ModalPage;
import selenium.pages.ViewPage;

public class Grid extends BaseElement {

	public Grid(WebElement element) {
		super(element);
	}

	public WebElement getRowById(Integer id) {
		return (WebElement) jsExecutor.executeScript("return $(arguments[0]).find('td:nth-child(1):contains(" + id + ")').closest('tr'), element)");
	}

	public ViewPage viewRowByName(String name) {
		element.findElement(By.cssSelector("td:nth-child(2):contains(" + name + ")~td>button:first-child")).click();
		return new ViewPage();
	}

	public ModalPage deleteRowByName(String name) {
		jsExecutor.executeScript(
				"console.log($(arguments[0])); console.log($(arguments[0]).find('td:nth-child(2):contains(" + name
						+ ")~td>button:last-child')); $(arguments[0]).find('td:nth-child(2):contains(" + name + ")~td>button:last-child').click()",
				element);
		return new ModalPage();
	}

	public int getRowsCount() {
		return element.findElements(By.cssSelector("tbody tr")).size();
	}
}
