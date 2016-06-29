package selenium.testcases.staff;

import org.junit.Test;

import selenium.pages.HomePage;
import selenium.pages.ModalPage;
import selenium.pages.StaffPage;
import selenium.testcases.common.BaseTestCase;

public class TestStaff extends BaseTestCase {

	@Test
	public void testCreateStaff() {
		StaffPage staffPage = new HomePage().openStaff();
		ModalPage modal = staffPage.clickCreateBranch();
		System.out.println(modal.getTitle());
	}
}
