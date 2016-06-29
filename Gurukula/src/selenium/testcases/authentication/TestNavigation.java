package selenium.testcases.authentication;

import static selenium.common.Verify.verifyEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import selenium.common.MenuOption;
import selenium.common.suites.RegressionTest;
import selenium.common.suites.SmokeTest;
import selenium.pages.AuthenticationPage;
import selenium.pages.RegistrationPage;
import selenium.pages.WelcomePage;
import selenium.testcases.common.BaseTestCase;

public class TestNavigation extends BaseTestCase {

	private final String authPageTitle = "Authentication";

	@Override
	@Before
	public void setUp() {

	}

	/**
	 * Test to verify authenticate by directly navigating to login page
	 */
	@Test
	@Category({ SmokeTest.class, RegressionTest.class })
	public void testNavigationViaDirectLink() {
		navigateTo("login");
		AuthenticationPage authPage = new AuthenticationPage();
		validateAuthPage(authPage);
	}

	/**
	 * Test to verify navigation to Authentication via Menu
	 */
	@Test
	@Category({ SmokeTest.class, RegressionTest.class })
	public void testNavigationViaNavMenu() {
		WelcomePage welcomePage = new WelcomePage();
		welcomePage.account.selectOption(MenuOption.AUTHENTICATE);
		AuthenticationPage authPage = new AuthenticationPage();
		validateAuthPage(authPage);
	}

	/**
	 * Test to verify navigation to Authentication via Registration page
	 */
	@Test
	@Category({ SmokeTest.class, RegressionTest.class })
	public void testNavigationViaRegistration() {
		WelcomePage welcomePage = new WelcomePage();
		welcomePage.account.selectOption(MenuOption.REGISTER);
		AuthenticationPage authPage = new RegistrationPage().clickLoginLink();
		validateAuthPage(authPage);
	}

	private void validateAuthPage(AuthenticationPage authPage) {
		verifyEquals(this, "Mismatch in title", authPage.getTitle(), authPageTitle);
	}
}
