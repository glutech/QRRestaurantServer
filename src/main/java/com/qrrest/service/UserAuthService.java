package com.qrrest.service;

import javax.servlet.http.HttpSession;

import com.qrrest.model.User;

public class UserAuthService {

	private static final String KEY_IS_AUTH = "com.qrrest.service.isAuth";
	private static final String KEY_AUTH_ID = "com.qrrest.service.authId";
	private static final String KEY_REST_ID = "com.qrrest.service.restId";

	private HttpSession _session;

	public UserAuthService(HttpSession session) {
		this._session = session;
	}

	public boolean isAuth() {
		Object r = _session.getAttribute(KEY_IS_AUTH);
		return r != null && r.equals(true);
	}

	public void setAuth(User user) {
		_session.setAttribute(KEY_IS_AUTH, true);
		_session.setAttribute(KEY_AUTH_ID, user.getUser_id());
		_session.setAttribute(KEY_REST_ID, user.getRest_id());
	}

	public void clearAuth() {
		_session.removeAttribute(KEY_IS_AUTH);
		_session.removeAttribute(KEY_AUTH_ID);
		_session.removeAttribute(KEY_REST_ID);
	}

	public int getAuthId() {
		return (Integer) _session.getAttribute(KEY_AUTH_ID);
	}

	public int getRestId() {
		return (Integer) _session.getAttribute(KEY_REST_ID);
	}

}
