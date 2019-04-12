package dto;

public class User {
	private long userId;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String roleId;

	User() {
	}

	public User(long userId, String userName, String password, String firstName, String lastName, String email,
			String roleId) {
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.roleId = roleId;
	}

	public User(String userName, String password, String firstName, String lastName, String email, String roleId) {
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.roleId = roleId;
	}

	public long getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getRoleId() {
		return roleId;
	}
}
