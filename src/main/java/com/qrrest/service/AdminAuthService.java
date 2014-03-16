package com.qrrest.service;

import javax.servlet.http.HttpSession;

import com.qrrest.model.Administrator;

public class AdminAuthService {

	private static final String KEY_IS_AUTH = "com.qrrest.service.AdminAuthService.IS_AUTH";
	private static final String KEY_AUTH_ID = "com.qrrest.service.AdminAuthService.AUTH_ID";
	private static final String KEY_AUTH_NICKNAME = "com.qrrest.service.AdminAuthService.AUTH_NICKNAME";
	private static final String KEY_REST_ID = "com.qrrest.service.AdminAuthService.AUTH_REST_ID";

	private HttpSession session;

	public AdminAuthService(HttpSession session) {
		this.session = session;
	}

	public boolean isAuth() {
		Object r = session.getAttribute(KEY_IS_AUTH);
		return r != null && r.equals(true);
	}

	public void setAuth(Administrator admin) {
		session.setAttribute(KEY_IS_AUTH, true);
		session.setAttribute(KEY_AUTH_ID, admin.getAdminId());
		session.setAttribute(KEY_AUTH_NICKNAME, admin.getAdminNickname());
		session.setAttribute(KEY_REST_ID, admin.getRestIdNullabled());
	}

	public void clearAuth() {
		session.removeAttribute(KEY_IS_AUTH);
		session.removeAttribute(KEY_AUTH_ID);
		session.removeAttribute(KEY_AUTH_NICKNAME);
		session.removeAttribute(KEY_REST_ID);
	}

	public int getAuthId() {
		return (Integer) session.getAttribute(KEY_AUTH_ID);
	}

	public String getAuthNickname() {
		return (String) session.getAttribute(KEY_AUTH_NICKNAME);
	}

	public Integer getRestId() {
		return (Integer) session.getAttribute(KEY_REST_ID);
	}

}
