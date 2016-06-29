package selenium.pages;

import org.openqa.selenium.support.FindBy;

import selenium.common.datamodel.Branch;
import selenium.common.elements.Button;
import selenium.common.elements.Grid;
import selenium.common.elements.TextBox;
import selenium.common.pagefactory.CustomPageFactory;

public class BranchPage extends CommonHomePage {

	@FindBy(css = "span[translate=\"gurukulaApp.branch.home.createLabel\"]")
	public Button createBranchButton;

	@FindBy(css = "#searchQuery")
	public TextBox searchBox;

	@FindBy(css = "[name=searchForm]>.btn")
	public Button searchButton;

	@FindBy(css = ".table")
	public Grid branchTable;

	public BranchPage() {
		CustomPageFactory.initElements(this);
		this.waitForReady();
	}

	public ModalPage clickCreateBranch() {
		createBranchButton.click();
		return new ModalPage();
	}

	public void searchBranch(String searchText) {
		searchBox.setText(searchText);
		searchButton.click();
	}

	public void createBranch(Branch branch) {

	}
}
