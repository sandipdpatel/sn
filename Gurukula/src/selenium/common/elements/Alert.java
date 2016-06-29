package selenium.common.elements;

import org.openqa.selenium.WebElement;

public class Alert extends BaseElement {

	public Alert(WebElement element) {
		super(element);
	}

	public String getAlertMessage() {
		return element.getText();
	}

}
