package selenium.common.datamodel;

public class User {
	public String username;
	public String password;
	public String confirmPassword;
	public String email;
	public boolean success;

	public User(String username, String password, boolean success) {
		this.username = username;
		this.password = password;
		this.success = success;
	}

	public User(String username, String password, String confirmPassword, String email, boolean success) {
		this(username, password, success);
		this.confirmPassword = confirmPassword;
		this.email = email;
	}

	public User(String username, String password, String email, boolean success) {
		this(username, password, password, email, success);
	}
}
