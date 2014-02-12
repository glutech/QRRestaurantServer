package com.qrrest.service;

import com.qrrest.dao.UsersDao;
import com.qrrest.model.User;

public class UserService {

	UsersDao userDao;

	public UserService() {
		userDao = new UsersDao();
	}

	public User getByUsernameAndPassword(String username, String password) {
		User user = userDao.getUserByName(username);
		return user.getUser_id() != 0 && user.getUser_pwd().equals(password)
				? user : null;
	}
}