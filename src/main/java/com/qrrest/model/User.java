package com.qrrest.model;

public class User {

	public static enum UserCategoryEnum {
		/**
		 * 表示账户是由设备自动注册的账户
		 */
		device,
		/**
		 * 表示账户是用户注册的账户
		 */
		register;
	}

	private int userId;
	private UserCategoryEnum userCategory;
	private String userName;
	private String userPwd;
	private String userDeviceId;
	private String userNickName;
	private String userContact;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public UserCategoryEnum getUserCategory() {
		return userCategory;
	}

	public void setUserCategory(UserCategoryEnum userCategory) {
		this.userCategory = userCategory;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserDeviceId() {
		return userDeviceId;
	}

	public void setUserDeviceId(String userDeviceId) {
		this.userDeviceId = userDeviceId;
	}

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	public String getUserContact() {
		return userContact;
	}

	public void setUserContact(String userContact) {
		this.userContact = userContact;
	}

}
