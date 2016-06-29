package selenium.common.elements;

import org.openqa.selenium.WebElement;

public class Label extends BaseElement {

	public Label(WebElement element) {
		super(element);
	}

	public String getLabelText() {
		return element.getText();
	}
}
