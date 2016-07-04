package selenium.testcases.staff;

import static selenium.common.Verify.verifyTrue;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import selenium.common.datamodel.Branch;
import selenium.common.datamodel.Staff;
import selenium.pages.HomePage;
import selenium.pages.StaffPage;
import selenium.testcases.common.BaseTestCase;

public class TestStaffPagging extends BaseTestCase {

	private StaffPage staffPage;
	private int pageSize = 10;

	@Before
	public void setUpStaff() {
		staffPage = new HomePage().openStaff();
	}

	@Test
	public void testNoPaggingForZeroRows() {
		List<WebElement> rows = staffPage.staffTable.getAllRows();
		if (rows.size() < pageSize) {
			Branch branch = setUpBranch();
			staffPage = new HomePage().openStaff();
			for (int i = rows.size(); i <= pageSize; i++) {
				Staff staff = new Staff(null, RandomStringUtils.randomAlphabetic(5), branch.name);
				staffPage.createStaff(staff);
			}
		}

		verifyTrue(this, "Pager is missing", staffPage.isElementPresent(staffPage.pager));
		verifyTrue(this, "First button is missing", staffPage.pager.isFirstVisible());
		verifyTrue(this, "Last button is missing", staffPage.pager.isLastVisible());
		verifyTrue(this, "Prev button is missing", staffPage.pager.isPrevVisible());
		verifyTrue(this, "Next button is missing", staffPage.pager.isPrevVisible());
	}

	private Branch setUpBranch() {
		Branch branch = new Branch(null, RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphanumeric(2).toUpperCase());
		new HomePage().openBranch().createBranch(branch);
		return branch;
	}
}
