package selenium.pages;

import org.openqa.selenium.support.FindBy;

import selenium.common.datamodel.Branch;
import selenium.common.datamodel.Staff;
import selenium.common.elements.BaseElement;
import selenium.common.elements.Button;
import selenium.common.elements.TextBox;
import selenium.common.pagefactory.CustomPageFactory;

public class ModalPage extends BasePage {
	@FindBy(css = "#saveBranchModal #myBranchLabel, #saveStaffModal #myStaffLabel")
	public BaseElement title;

	@FindBy(css = "#saveBranchModal input[name=id], #saveStaffModal input[name=id]")
	public TextBox id;

	@FindBy(css = "#saveBranchModal input[name=name], #saveStaffModal input[name=name]")
	public TextBox name;

	@FindBy(css = "#saveBranchModal input[name=name], #saveStaffModal input[name=name]")
	public TextBox Branch;

	@FindBy(css = "#saveBranchModal button[type=submit], #saveStaffModal button[type=submit]")
	public Button save;

	@FindBy(css = "#saveBranchModal .btn[data-dismiss=\"modal\"], #saveStaffModal .btn[data-dismiss=\"modal\"]")
	public Button cancel;

	@FindBy(css = "[name=deleteForm] button.btn-danger")
	public Button delete;

	public ModalPage() {
		CustomPageFactory.initElements(this);
		this.waitForReady();
	}

	public String getTitle() {
		return title.getText();
	}

	public ModalPage setFields(Branch branch) {
		name.setText(branch.name);
		branch.setText(branch.code);
		return this;
	}

	public ModalPage setFields(Staff staff) {
		name.setText(staff.name);
		code.setText(staff.branch);
		return this;
	}

	public BranchPage clickSave() {
		save.click();
		waitForElementToDisappear(cancel);
		return new BranchPage();
	}

	public BranchPage clickCancel() {
		cancel.click();
		waitForElementToDisappear(cancel);
		return new BranchPage();
	}

	public BranchPage clickDelete() {
		delete.click();
		waitForElementToDisappear(cancel);
		return new BranchPage();
	}
}
