package selenium.pages;

import org.openqa.selenium.support.FindBy;

import selenium.common.elements.Button;
import selenium.common.elements.Grid;
import selenium.common.elements.TextBox;
import selenium.common.pagefactory.CustomPageFactory;

public class StaffPage extends CommonHomePage {

	@FindBy(css = "span[translate=\"gurukulaApp.staff.home.createLabel\"]")
	public Button createStaffButton;

	@FindBy(css = "#searchQuery")
	public TextBox searchBox;

	@FindBy(css = "[name=searchForm]>.btn")
	public Button searchButton;

	@FindBy(css = ".table")
	public Grid staffTable;

	public StaffPage() {
		CustomPageFactory.initElements(this);
		this.waitForReady();
	}

	public ModalPage clickCreateBranch() {
		createStaffButton.click();
		return new ModalPage();
	}

	public void searchStaff(String searchText) {
		searchBox.setText(searchText);
		searchButton.click();
	}
}
