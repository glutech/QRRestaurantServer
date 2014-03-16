package com.qrrest.model;

public class Administrator {

	private int adminId;
	private String adminName;
	private String adminPwd;
	private String adminNickname;
	private Integer restIdNullabled;

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminPwd() {
		return adminPwd;
	}

	public void setAdminPwd(String adminPwd) {
		this.adminPwd = adminPwd;
	}

	public String getAdminNickname() {
		return adminNickname;
	}

	public void setAdminNickname(String adminNickname) {
		this.adminNickname = adminNickname;
	}

	public Integer getRestIdNullabled() {
		return restIdNullabled;
	}

	public void setRestIdNullabled(Integer restIdNullabled) {
		this.restIdNullabled = restIdNullabled;
	}

}
