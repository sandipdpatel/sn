package selenium.pages;

import org.openqa.selenium.support.FindBy;

import selenium.common.datamodel.Staff;
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

	public ModalPage clickCreateStaff() {
		createStaffButton.click();
		return new ModalPage();
	}

	public StaffPage searchStaff(String searchText) {
		searchBox.setText(searchText);
		searchButton.click();
		waitForReady(1);
		return this;
	}

	public StaffPage clearSearch(String searchText) {
		searchBox.clear();
		searchButton.click();
		return this;
	}

	public StaffPage createStaff(Staff staff) {
		StaffPage staffPage = new HomePage().openStaff();
		ModalPage modal = staffPage.clickCreateStaff();
		modal.setFields(staff).clickSave();
		return this;
	}

	public StaffPage deleteStaff(String name) {
		StaffPage staffPage = new HomePage().openStaff();
		ModalPage modalPage = staffTable.deleteRowByName(name);
		modalPage.clickDelete();
		return this;
	}
}
