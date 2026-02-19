package com.mapfinder.modal;

import java.util.Date;

public class Users {
	private int USER_ID;
	private String userName;
	private String password;
	private String role;
	private Date CREATED_AT;



	public Users(int USER_ID, String userName, String password, String role, Date CREATED_AT) {
		this.USER_ID = USER_ID;
		this.userName = userName;
		this.password = password;
		this.role = role;
		this.CREATED_AT = CREATED_AT;
	}

	public int getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(int USER_ID) {
		if (USER_ID > 0)
			this.USER_ID = USER_ID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		if (userName != null || !userName.isEmpty())
			this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (password != null || !password.isEmpty())
			this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		if (role != null || !role.isEmpty())
			this.role = role;
	}

	public Date getCREATED_AT() {
		return CREATED_AT;
	}

	public void setCREATED_AT(Date CREATED_AT) {
		if (CREATED_AT != null)
			this.CREATED_AT = CREATED_AT;
	}

}