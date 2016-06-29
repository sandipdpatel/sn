package selenium.common;

public enum MenuOption {
	AUTHENTICATE("login"), REGISTER("register"), BRANCH("branch"), STAFF("staff");

	private String sref;

	MenuOption(String sref) {
		this.sref = sref;
	}

	public String getSref() {
		return sref;
	}
}
