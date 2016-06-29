package selenium.pages;

import org.openqa.selenium.support.FindBy;

import selenium.common.elements.BaseElement;
import selenium.common.elements.Button;
import selenium.common.elements.NavMenu;
import selenium.common.elements.NavPill;

public class CommonPage extends BasePage {

	// NavBar
	@FindBy(css = "nav.navbar")
	public NavPill navBar;

	@FindBy(css = ".navbar-header button.navbar-toggle")
	public Button navBarToggle;

	@FindBy(css = ".navbar-header a.navbar-brand")
	public BaseElement navBarBrandImage;

	@FindBy(css = "a[ui-sref=\"home\"]")
	public NavPill home;

	@FindBy(css = "span[translate=\"global.menu.account.main\"]")
	public NavMenu account;
}
