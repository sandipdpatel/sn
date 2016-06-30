package selenium.testcases.staff;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import selenium.common.Verify;
import selenium.common.datamodel.Branch;
import selenium.common.datamodel.Staff;
import selenium.common.suites.RegressionTest;
import selenium.common.suites.SmokeTest;
import selenium.pages.HomePage;
import selenium.pages.StaffPage;
import selenium.testcases.common.BaseTestCase;

@Category({ SmokeTest.class, RegressionTest.class })
public class TestStaff extends BaseTestCase {

	private Staff staff;
	private Branch branch;

	private StaffPage staffPage;

	@Before
	public void setUpStaff() {
		branch = new Branch(null, RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphanumeric(2).toUpperCase());
		new HomePage().openBranch().createBranch(branch);

		staff = new Staff(null, RandomStringUtils.randomAlphabetic(5), branch.name);
		staffPage = new HomePage().openStaff();
		staffPage.createStaff(staff).searchStaff(staff.name);
	}

	@Test
	public void testCreateStaff() {
		Verify.verifyEquals(this, "staff is not found", 1, staffPage.staffTable.getRowsCount());
	}

	@Test
	public void testDeleteStaff() {
		Verify.verifyEquals(this, "staff is not found", 1, staffPage.staffTable.getRowsCount());

		staffPage.deleteStaff(staff.name).searchStaff(staff.name);
		Verify.verifyEquals(this, "staff is not found", 0, staffPage.staffTable.getRowsCount());
	}
}
