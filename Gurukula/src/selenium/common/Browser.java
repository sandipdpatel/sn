package selenium.common;

public enum Browser {

	CHROME, FIREFOX, INTERNET_EXPLORER("InternetExplorer"), SAFARI, HTMLUNIT;

	private String browser;

	Browser() {
		this.browser = toString();
	}

	Browser(String browser) {
		this.browser = browser;
	}

	public String getBrowser() {
		return this.browser;
	}

	public static Browser getBrowser(String browserName) {
		if (browserName == null) {
			return null;
		}
		for (Browser browser : Browser.values()) {
			if (browser.getBrowser().equalsIgnoreCase(browserName)) {
				return browser;
			}
		}
		return null;
	}
}
