package com.qrrest.service;

import com.qrrest.dao.AdministratorsDao;
import com.qrrest.model.Administrator;

public class AdminService {

	private AdministratorsDao adminDao = new AdministratorsDao();

	public Administrator getAdminByNameAndPassword(String username,
			String password) {
		Administrator admin = adminDao.getAdminByName(username);
		if (admin != null && admin.getAdminPwd().equals(password)) {
			return admin;
		} else {
			return null;
		}
	}
}