package selenium.pages;

import org.openqa.selenium.support.FindBy;

import selenium.common.elements.BaseElement;
import selenium.common.elements.Button;
import selenium.common.elements.TextBox;
import selenium.common.pagefactory.CustomPageFactory;

public class ViewPage extends CommonHomePage {

	@FindBy(css = "span[translate=\"gurukulaApp.branch.detail.title\"]")
	public BaseElement title;

	@FindBy(css = "tr:first-child input")
	public TextBox name;

	@FindBy(css = "tr:last-child input")
	public TextBox code;

	@FindBy(css = "href=\"#/branch\"")
	public Button back;

	public ViewPage() {
		CustomPageFactory.initElements(this);
		this.waitForReady();
	}

	public String getName() {
		return name.getAttribute("value");
	}

	public String getCode() {
		return code.getAttribute("value");
	}

	public BasePage clickBack() {
		back.click();
		if (title.equals("Branch")) {
			return new BranchPage();
		} else {
			return new StaffPage();
		}
	}
}
