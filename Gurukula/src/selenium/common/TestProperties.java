package selenium.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

public class TestProperties {

	private static final Logger LOGGER = LogManager.getLogger(TestProperties.class);

	private final static String FILENAME = "selenium.properties";
	private final static String APP_URL = "appUrl";
	private final static String USERNAME = "username";
	private final static String PASSWORD = "password";
	private final static String BROWSER = "browser";
	private final static String DRIVER_PATH = "driverPath";
	private final static String SCREENSHOT_DIR = "screenShotDir";
	private final static String IMPLICIT_WAIT_TIMEOUT = "implicitTimeOutInMiliSec";

	private final static String DEFAULT_APP_URL = "http://localhost:8080";
	private final static String DEFAULT_USERNAME = "admin";
	private final static String DEFAULT_COMP_LOAD_TIMEOUT = "20000";

	private static TestProperties propertyInstance;
	private static Map<Long, TestProperties> instanceMap = Collections
			.synchronizedMap(new HashMap<Long, TestProperties>());

	private String appUrl;
	private String username;
	private String password;
	private String driverPath;
	private String screenshotDir;
	private Browser browser;
	private Integer timeOut;

	private WebDriver driver;

	private Properties properties = new Properties();

	public TestProperties() {

		try {
			properties.load(getPropertiesFile());
			username = getSetting(USERNAME, DEFAULT_USERNAME);
			password = getSetting(PASSWORD, null);
			appUrl = getSetting(APP_URL, DEFAULT_APP_URL);
			driverPath = getSetting(DRIVER_PATH, null);
			timeOut = Integer.parseInt(getSetting(IMPLICIT_WAIT_TIMEOUT, DEFAULT_COMP_LOAD_TIMEOUT));
			browser = Browser.getBrowser(getSetting(BROWSER, "chrome"));
			screenshotDir = createScreenshotPath();
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			throw new RuntimeException("Failed to load " + FILENAME + " from classpath", e);
		}

	}

	private InputStream getPropertiesFile() {
		InputStream propFile;
		propFile = getClass().getClassLoader().getResourceAsStream(FILENAME);
		return propFile;
	}

	public String getSetting(String settingName, String defaultValue) {
		String value = System.getProperty(settingName);
		if (value == null) {
			value = properties.getProperty(settingName, defaultValue);
		}
		return value;
	}

	public WebDriver initDriver() {
		DesiredCapabilities capabilities;
		String driverPath = TestProperties.getInstance().getDriverPath();

		switch (browser) {
		case FIREFOX:
			capabilities = DesiredCapabilities.firefox();
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("browser.startup.homepage_override.mstone", "ignore");
			profile.setPreference("startup.homepage_welcome_url.additional", "about:blank");
			profile.setEnableNativeEvents(true);
			capabilities.setCapability(FirefoxDriver.PROFILE, profile);
			driver = new FirefoxDriver(capabilities);
			driver.manage().window().maximize();
			break;
		case INTERNET_EXPLORER:
			if (driverPath != null) {
				System.setProperty("webdriver.ie.driver", driverPath);
			}
			capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			driver = new InternetExplorerDriver(capabilities);
			driver.manage().window().maximize();
			break;
		case SAFARI:
			driver = new SafariDriver();
			driver.manage().window().maximize();
			break;
		default:
			if (driverPath != null) {
				System.setProperty("webdriver.chrome.driver", driverPath);
			}
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized");
			capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);

			LOGGER.info("Chrome capabilities initialized; ready to call `new ChromeDriver`");
			driver = new ChromeDriver(capabilities);
			LOGGER.info("Chrome driver instantiated - case ready to run");
		}

		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.MILLISECONDS);
		driver.manage().timeouts().setScriptTimeout(timeOut, TimeUnit.MILLISECONDS);
		return driver;
	}

	private String createScreenshotPath() {
		screenshotDir = getSetting(SCREENSHOT_DIR, null);

		if (screenshotDir != null) {
			File path = new File(screenshotDir);
			if (path.exists() && !path.isDirectory()) {
				LOGGER.error("Either path does not exist or it's not a directory");
				throw new RuntimeException("Path " + screenshotDir + "must be a folder");
			}
			path.mkdirs();
		}

		return screenshotDir;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public String getScreenshotPath() {
		return screenshotDir;
	}

	public void setScreenshotPath(String screenshotPath) {
		screenshotDir = screenshotPath;
	}

	public void setUrl(String url) {
		appUrl = url;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setTimeouts() {
		setTimeouts(timeOut, TimeUnit.MILLISECONDS);
	}

	public void setTimeouts(int waitTime, TimeUnit unit) {
		driver.manage().timeouts().implicitlyWait(waitTime, unit);
		driver.manage().timeouts().setScriptTimeout(waitTime, unit);
	}

	public synchronized static TestProperties getInstance() {
		return getInstance(false);
	}

	public synchronized static TestProperties getInstance(boolean createNew) {
		long threadId = Thread.currentThread().getId();
		if (createNew || instanceMap.get(threadId) == null) {
			propertyInstance = new TestProperties();
			instanceMap.put(threadId, propertyInstance);
		}
		return instanceMap.get(threadId);
	}

	public String getUrl() {
		return appUrl;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public int getWaitTime() {
		return timeOut;
	}

	public void setWaitTime(String waitTime) {
		timeOut = Integer.parseInt(waitTime);
	}

	public String getDriverPath() {
		return driverPath;
	}

	public void setDriverPath(String driverPath) {
		this.driverPath = driverPath;
	}

	public Browser getBrowser() {
		return browser;
	}

	public void setBrowser(Browser browser) {
		this.browser = browser;
	}
}
