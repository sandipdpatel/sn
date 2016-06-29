package selenium.common;

public enum NavItem {
	HOME("Home"), ACCOUNT("Account");

	private String title;

	NavItem(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
