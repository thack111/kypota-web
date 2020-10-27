package com.hacksnet.kypota.model;

public class User {

	private String user;
	private String email;
	private String password;
	private String enabled;
	private String adminFlag;
	
	public User(String user, String email, String adminFlag) {
		this.user = user;
		this.email = email;
		this.adminFlag = adminFlag;
	}
	
	public User() {
		//  Auto-generated constructor stub
	}

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getAdminFlag() {
		return adminFlag;
	}
	public void setAdminFlag(String adminFlag) {
		this.adminFlag = adminFlag;
	}
	
	
}
