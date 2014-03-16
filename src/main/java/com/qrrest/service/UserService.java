package com.qrrest.service;

import com.qrrest.dao.UsersDao;
import com.qrrest.model.User;
import com.qrrest.model.User.UserCategoryEnum;

public class UserService {

	private UsersDao usersDao = new UsersDao();

	public User getUserByUserId(int userId) {
		return usersDao.getUserById(userId);
	}

	public User getUserByDeviceId(String deviceId) {
		return usersDao.getUserByDeviceId(deviceId);
	}

	public User getUserByName(String name) {
		return usersDao.getUserByName(name);
	}

	public boolean createUserWithDeviceId(String deviceId) {
		User user = new User();
		user.setUserCategory(UserCategoryEnum.device);
		user.setUserDeviceId(deviceId);
		return usersDao.insertUser(user);
	}

	public boolean createUserWithNameAndPwd(String name, String pwd,
			String nickname, String contact) {
		User user = new User();
		user.setUserCategory(UserCategoryEnum.register);
		user.setUserName(name);
		user.setUserPwd(pwd);
		user.setUserNickName(nickname);
		user.setUserContact(contact);
		return usersDao.insertUser(user);
	}

	public boolean modifyUser(User user) {
		throw new RuntimeException("未实现");
	}
}
