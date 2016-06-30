package selenium.common.elements;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SelectBox extends BaseElement {

	public SelectBox(WebElement element) {
		super(element);
	}

	public List<WebElement> getAllOptions() {
		return ((Select) element).getOptions();
	}

	public SelectBox selectOption(String option) {
		((Select) element).selectByVisibleText(option);
		return this;
	}
}
