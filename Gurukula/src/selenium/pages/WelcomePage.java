package selenium.pages;

import org.openqa.selenium.support.FindBy;

import selenium.common.elements.BaseElement;
import selenium.common.elements.Label;
import selenium.common.pagefactory.CustomPageFactory;

public class WelcomePage extends CommonPage {

	@FindBy(css = "h1[translate=\"main.title\"]")
	public Label title;

	@FindBy(css = "div[translate=\"global.messages.info.authenticated\"]>a")
	public BaseElement loginLink;

	@FindBy(css = "div[translate=\"global.messages.info.register\"]>a")
	public BaseElement registerAccountLink;

	public WelcomePage() {
		CustomPageFactory.initElements(this);
		this.waitForReady();
	}

	public String getTitle() {
		return title.getLabelText();
	}

	public AuthenticationPage navigateToLogin() {
		loginLink.click();
		AuthenticationPage authPage = CustomPageFactory.initElements(AuthenticationPage.class);
		authPage.waitForElement(authPage.authenticate);
		return authPage;
	}
}
