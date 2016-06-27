package selenium.common.elements;

import org.openqa.selenium.WebElement;

public class CheckBox extends BaseElement {

	public CheckBox(WebElement element) {
		super(element);
	}

	public void check() {
		if (!isSelected()) {
			click();

			if (!isSelected()) {
				scrollIntoView(true);
				click();
			} else {
				return;
			}

			if (!isSelected()) {
				scrollIntoView();
				click();
			} else {
				return;
			}

			if (!isSelected()) {
				throw new RuntimeException("Failed to check the checkbox");
			}
		}
	}

	public void uncheck() {
		if (isSelected()) {
			click();

			if (isSelected()) {
				scrollIntoView(true);
				click();
			} else {
				return;
			}

			if (isSelected()) {
				scrollIntoView();
				click();
			} else {
				return;
			}

			if (isSelected()) {
				throw new RuntimeException("Failed to uncheck the checkbox");
			}
		}
	}

	public boolean isChecked() {
		return isSelected();
	}
}
