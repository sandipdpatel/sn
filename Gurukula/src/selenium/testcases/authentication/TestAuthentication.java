package selenium.testcases.authentication;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import selenium.common.Verify;
import selenium.common.datamodel.User;
import selenium.common.suites.RegressionTest;
import selenium.pages.HomePage;
import selenium.testcases.common.BaseTestCase;

@RunWith(Parameterized.class)
public class TestAuthentication extends BaseTestCase {
	private User user;
	private String objective;
	private final String welcomeMessage = "You are logged in as user ";

	public TestAuthentication(String objective, User user) {
		this.objective = objective;
		this.user = user;
	}

	@Override
	@Before
	public void setUp() {

	}

	@Parameters(name = "{index}: {0}")
	public static Collection<Object[]> regressionData() {
		// @formatter:off
		return Arrays.asList(new Object[][] {
			{ "valid authentication", new User("admin", "admin", true) },
			{ "invalid authentication", new User("admin", "admin1", false) }
		});
		// @formatter:on
	}

	/**
	 * Test to verify authenticate by navigating from login link available on
	 * Welcome page for new users
	 */
	@Test
	@Category(RegressionTest.class)
	public void testAuthenticationLoginLink() throws Exception {
		HomePage homePage = login(user.username, user.password);

		if (user.success) {
			Verify.verifyEquals(this, getFailureMessage(objective, user), homePage.getWelcomeMessage(), getWelcomeMessage(user));
		} else {
			Verify.verifyTrue(this, getFailureMessage(objective, user), homePage.isElementPresent(homePage.errorMessage));
		}
	}

	private String getFailureMessage(String objective, User user) {
		return "Failed for " + objective + ", [username]: " + user.username + " [password]: " + user.password;
	}

	private String getWelcomeMessage(User user) {
		return welcomeMessage + "\"" + user.username + "\".";
	}
}
