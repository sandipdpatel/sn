package selenium.pages;

import org.openqa.selenium.support.FindBy;

import selenium.common.MenuOption;
import selenium.common.elements.BaseElement;
import selenium.common.elements.NavMenu;

public class CommonHomePage extends CommonPage {

	@FindBy(css = "span[translate=\"global.menu.entities.main\"]")
	public NavMenu entities;

	@FindBy(css = "span[translate=\"global.menu.account.main\"]")
	public NavMenu account;

	// Footer
	@FindBy(css = "div.container div.footer")
	public BaseElement footer;

	public BranchPage openBranch() {
		entities.selectOption(MenuOption.BRANCH);
		return new BranchPage();
	}

	public StaffPage openStaff() {
		entities.selectOption(MenuOption.STAFF);
		return new StaffPage();
	}
}
