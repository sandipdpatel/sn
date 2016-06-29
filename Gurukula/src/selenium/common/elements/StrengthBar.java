package selenium.common.elements;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class StrengthBar extends BaseElement {

	private final String veryWeak = "rgb(255, 0, 0)";
	private final String weak = "rgb(255, 153, 0)";
	private final String good = "rgb(255, 153, 0)";
	private final String strong = "rgb(153, 255, 0)";

	public StrengthBar(WebElement element) {
		super(element);
	}

	public List<String> getBackgroundColor() {
		List<String> backgroundColors = new ArrayList<String>();
		List<WebElement> strengthBars = getStrengthBars();
		for (WebElement bar : strengthBars) {
			backgroundColors.add(bar.getCssValue("background-color"));
		}
		return backgroundColors;
	}

	public List<WebElement> getStrengthBars() {
		return element.findElements(By.className("point"));
	}

	public boolean isStrengthBarClear() {
		List<String> backgroundColors = getBackgroundColor();
		for (String color : backgroundColors) {
			if (!color.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public boolean isPasswordVeryWeak() {
		List<String> backgroundColors = getBackgroundColor();
		int i = 0;
		if (!backgroundColors.get(i++).equals(veryWeak)) {
			return false;
		}
		for (; i < backgroundColors.size(); i++) {
			if (!backgroundColors.get(i).isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public boolean isPasswordWeak() {
		List<String> backgroundColors = getBackgroundColor();
		int i = 0;
		if (!backgroundColors.get(i++).equals(weak) || !backgroundColors.get(i++).equals(weak)) {
			return false;
		}
		for (; i < backgroundColors.size(); i++) {
			if (!backgroundColors.get(i).isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public boolean isPasswordGood() {
		List<String> backgroundColors = getBackgroundColor();
		int i = 0;
		for (; i < backgroundColors.size() - 1; i++) {
			if (!backgroundColors.get(i).equals(good)) {
				return false;
			}
		}
		if (!backgroundColors.get(i).isEmpty()) {
			return false;
		}
		return true;
	}

	public boolean isPasswordStrong() {
		List<String> backgroundColors = getBackgroundColor();
		for (int i = 0; i < backgroundColors.size(); i++) {
			if (!backgroundColors.get(i).equals(strong)) {
				return false;
			}
		}
		return true;
	}
}
