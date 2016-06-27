package selenium.common.elements;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class TextBox extends BaseElement {

	public TextBox(WebElement element) {
		super(element);
	}

	public void setText(String text) {
		if (text == null) {
			return;
		}
		element.clear();
		element.sendKeys(text, Keys.TAB);
	}

	@Override
	public String getText() {
		return element.getAttribute("value");
	}

}
