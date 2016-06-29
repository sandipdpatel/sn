package selenium.pages;

import org.openqa.selenium.support.FindBy;

import selenium.common.elements.BaseElement;
import selenium.common.elements.Label;
import selenium.common.pagefactory.CustomPageFactory;

public class HomePage extends CommonHomePage {

	@FindBy(css = "h1[translate=\"login.title\"]")
	public Label title;

	@FindBy(css = "div[translate=\"main.logged.message\"]")
	public BaseElement welcomeMsg;

	@FindBy(css = "div[translate=\"login.messages.error.authentication\"]")
	public BaseElement errorMessage;

	public HomePage() {
		CustomPageFactory.initElements(driver, this);
		this.waitForReady();
	}

	public String getTitle() {
		return title.getText();
	}

	public String getWelcomeMessage() {
		return welcomeMsg.getText();
	}

}
