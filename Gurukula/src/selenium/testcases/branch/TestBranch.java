package selenium.testcases.branch;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import selenium.common.datamodel.Branch;
import selenium.pages.BranchPage;
import selenium.pages.HomePage;
import selenium.pages.ModalPage;
import selenium.testcases.common.BaseTestCase;

public class TestBranch extends BaseTestCase {

	private Branch branch;

	@Before
	public void setUpBranch() {
		branch = new Branch(null, RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphanumeric(2));
	}

	@Test
	public void testCreateBranch() {
		BranchPage branchPage = new HomePage().openBranch();
		ModalPage modal = branchPage.clickCreateBranch();
	}
}
