package selenium.testcases.branch;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import selenium.common.Verify;
import selenium.common.datamodel.Branch;
import selenium.common.suites.RegressionTest;
import selenium.pages.BranchPage;
import selenium.pages.HomePage;
import selenium.testcases.common.BaseTestCase;

public class TestBranch extends BaseTestCase {

	private Branch branch;

	@Before
	public void setUpBranch() {
		branch = new Branch(null, RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphanumeric(2).toUpperCase());
	}

	@Test
	@Category(RegressionTest.class)
	public void testCreateBranch() {
		BranchPage branchPage = new HomePage().openBranch();
		branchPage.createBranch(branch).searchBranch(branch.name);

		Verify.verifyEquals(this, "branch is not found", 1, branchPage.branchTable.getRowsCount());
	}

	@Test
	@Category(RegressionTest.class)
	public void testDeleteBranch() {
		BranchPage branchPage = new HomePage().openBranch();
		branchPage.createBranch(branch).searchBranch(branch.name);
		Verify.verifyEquals(this, "branch is not found", 1, branchPage.branchTable.getRowsCount());

		branchPage.deleteBranch(branch.name).searchBranch(branch.name);
		Verify.verifyEquals(this, "branch is not found", 0, branchPage.branchTable.getRowsCount());
	}
}
