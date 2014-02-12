package com.qrrest.model;

public class User {
	private long user_id;
	private String user_name;
	private String user_pwd;
	private String user_nickname;
	private long rest_id;
	
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_pwd() {
		return user_pwd;
	}
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}
	public String getUser_nickname() {
		return user_nickname;
	}
	public long getRest_id() {
		return rest_id;
	}
	public void setRest_id(long rest_id) {
		this.rest_id = rest_id;
	}
}
