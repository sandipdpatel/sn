package selenium.pages;

import org.openqa.selenium.support.FindBy;

import selenium.common.elements.BaseElement;
import selenium.common.elements.Button;
import selenium.common.elements.CheckBox;
import selenium.common.elements.Label;
import selenium.common.elements.TextBox;
import selenium.common.pagefactory.CustomPageFactory;

public class AuthenticationPage extends WelcomePage {

	@FindBy(css = "h1[translate=\"login.title\"]")
	public Label title;

	@FindBy(css = ".form #username")
	public TextBox login;

	@FindBy(css = ".form #password")
	public TextBox password;

	@FindBy(css = "#rememberMe")
	public CheckBox automaticLogin;

	@FindBy(css = "button[type=submit]")
	public Button authenticate;

	@FindBy(css = "a[translate=\"login.password.forgot\"]")
	public BaseElement forgotPassword;

	public AuthenticationPage() {
		CustomPageFactory.initElements(this);
		this.waitForReady();
	}

	public HomePage authenticate(String username, String password) {
		login.setText(username);
		this.password.setText(password);
		authenticate.click();
		return new HomePage();
	}

	@Override
	public String getTitle() {
		return title.getLabelText();
	}
}
