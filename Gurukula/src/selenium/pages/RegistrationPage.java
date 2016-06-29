package selenium.pages;

import java.util.List;

import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import selenium.common.elements.Alert;
import selenium.common.elements.BaseElement;
import selenium.common.elements.Button;
import selenium.common.elements.Label;
import selenium.common.elements.TextBox;
import selenium.common.pagefactory.CustomPageFactory;

public class RegistrationPage extends WelcomePage {

	@FindBy(css = "h1[translate=\"register.title\"]")
	public Label title;

	@FindBy(css = "div[translate=\"register.messages.error.fail\")]")
	public Alert failureAlert;

	@FindBy(css = "input[name=login]")
	public TextBox login;

	@FindAll({ @FindBy(css = "input[name=login] + div>p.help-block:not(.ng-hide)") })
	public List<BaseElement> loginFeedBack;

	@FindBy(css = "input[name=email]")
	public TextBox email;

	@FindAll({ @FindBy(css = "input[name=email] + div>p.help-block:not(.ng-hide)") })
	public List<BaseElement> emailFeedBack;

	@FindBy(css = "input[name=password]")
	public TextBox newPassword;

	@FindAll({ @FindBy(css = "input[name=password] + div>p.help-block:not(.ng-hide)") })
	public List<BaseElement> newPasswordFeedBack;

	@FindBy(css = "input[name=confirmPassword]")
	public TextBox confirmPassword;

	@FindAll({ @FindBy(css = "input[name=confirmPassword] + div>p.help-block:not(.ng-hide)") })
	public List<BaseElement> confirmPasswordFeedBack;

	@FindBy(css = "button[type=submit]")
	public Button register;

	@FindBy(css = "div.alert>a[href=\"#/login\"]")
	public BaseElement loginLink;

	public RegistrationPage() {
		CustomPageFactory.initElements(this);
		this.waitForReady();
	}

	@Override
	public String getTitle() {
		return title.getLabelText();
	}

	public AuthenticationPage clickLoginLink() {
		loginLink.click();
		return new AuthenticationPage();
	}
}
