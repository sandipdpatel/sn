package selenium.testcases.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import selenium.common.FailureMonitor;
import selenium.common.TestProperties;
import selenium.common.pagefactory.CustomPageFactory;
import selenium.pages.AuthenticationPage;
import selenium.pages.HomePage;
import selenium.pages.WelcomePage;

public class BaseTestCase implements FailureMonitor {

	protected static final Logger logger = LogManager.getLogger(BaseTestCase.class);
	protected WebDriver driver;
	private boolean isLoggedIn = false;
	private boolean isAssertionFailed = false;

	private List<Throwable> exceptions = new ArrayList<>();

	@Rule
	public final TestRule upWatchman = new TestWatcher() {
		@Override
		protected void starting(Description description) {
			final TestProperties properties = TestProperties.getInstance(true);
			driver = properties.initDriver();
			startUp(properties);
		}

		@Override
		public void finished(Description desc) {
			boolean fail = isAssertionFailed;
			StringBuilder buffer = new StringBuilder();

			final Collection<Throwable> ex = getExceptions();
			if (ex != null && !ex.isEmpty()) {
				logger.error("Test Failed. List of Errors: ");
				for (final Throwable e : ex) {
					buffer.append("\n" + e.getMessage());
					logger.error(e.getMessage(), e);
				}
				fail = true;
			}

			TestProperties properties = TestProperties.getInstance();
			properties.getDriver().quit();
			isLoggedIn = false;

			String[] classParts = desc.getClassName().split("\\.");
			String testClassName = classParts[classParts.length - 1];
			if (fail) {
				logger.info("Test {} # {} failed", testClassName, desc.getMethodName());
				Assert.fail("Test Case " + testClassName + "#" + desc.getMethodName() + " failed due to: " + buffer.toString()
						+ "\nsee log for details.");
			} else {
				logger.info("Finished Running Test, {} # {} ...", testClassName, desc.getMethodName());
			}
		}

		@Override
		protected void failed(Throwable e, Description description) {
			super.failed(e, description);

			isAssertionFailed = true;

			final String screenshotPath = TestProperties.getInstance().getScreenshotPath();
			if (screenshotPath != null) {
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				String[] className = description.getClassName().split("\\.");
				File destFile = new File(screenshotPath,
						className[className.length - 1] + "_" + description.getMethodName() + "_" + getTimeStamp() + ".png");
				try {
					FileUtils.copyFile(scrFile, destFile);
				} catch (IOException ex) {
					logger.error("Failed to save screenshot to ", screenshotPath, e);
				}
			}

			logger.error("Test failed: ", e.getMessage());
		}

		private String getTimeStamp() {
			return new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(new Date());
		}
	};

	public void startUp(TestProperties properties) {
		driver.get(properties.getUrl());
		logger.info("Browsing to login page");
	}

	@Before
	public void setUp() throws Exception {
		if (!isLoggedIn) {
			login(TestProperties.getInstance().getUsername(), TestProperties.getInstance().getPassword());
			isLoggedIn = true;
		}
	}

	@After
	public void tearDown() {
		driver.close();
		isLoggedIn = false;
	}

	public HomePage login(String username, String password) {
		WelcomePage welcomePage = CustomPageFactory.initElements(WelcomePage.class);
		AuthenticationPage authPage = welcomePage.navigateToLogin();
		return authPage.authenticate(username, password);
	}

	@Override
	public Collection<Throwable> getExceptions() {
		return exceptions;
	}

	@Override
	public void addException(Throwable e) {
		exceptions.add(e);
	}

	@Override
	public void flushExceptions() {
		exceptions.clear();
	}

	public void navigateTo(String path) {
		driver.navigate().to(TestProperties.getInstance().getUrl() + "/#/" + path);
	}
}
