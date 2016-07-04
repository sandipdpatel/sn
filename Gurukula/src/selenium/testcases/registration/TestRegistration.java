package selenium.testcases.registration;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import selenium.common.MenuOption;
import selenium.common.Verify;
import selenium.common.datamodel.User;
import selenium.pages.RegistrationPage;
import selenium.pages.WelcomePage;
import selenium.testcases.common.BaseTestCase;

public class TestRegistration extends BaseTestCase {
	private RegistrationPage registrationPage;

	@Override
	public void setUp() {

	}

	@Before
	public void setUpRegistration() {
		new WelcomePage().account.selectOption(MenuOption.REGISTER);
		registrationPage = new RegistrationPage();
	}

	@Test
	public void testValidRegistration() {
		String email = buildEmail(RandomStringUtils.randomAlphabetic(5),
				RandomStringUtils.randomAlphabetic(8) + "." + RandomStringUtils.randomAlphabetic(3));
		User user = new User(RandomStringUtils.randomAlphabetic(5).toLowerCase(), RandomStringUtils.randomAlphabetic(6), email, true);
		registrationPage.registerUser(user);

		Verify.verifyFalse(this, "Error registering valid user", registrationPage.hasError());
	}

	public String buildEmail(String user, String domain) {
		String email = "";
		if (user != null) {
			email += user;
		}
		if (domain != null) {
			email += "@" + domain;
		}
		return email;
	}
}
