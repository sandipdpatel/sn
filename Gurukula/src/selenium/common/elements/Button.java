package selenium.common.elements;

import org.openqa.selenium.WebElement;

public class Button extends BaseElement {

	public Button(WebElement element) {
		super(element);
	}

	@Override
	public void click() {
		if (!element.isEnabled()) {
			throw new RuntimeException(element + " is not enabled");
		}
		element.click();
	}

	public String getLabel() {
		return element.getText();
	}
}
