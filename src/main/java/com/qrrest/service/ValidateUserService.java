package com.qrrest.service;

import com.qrrest.dao2.UsersDao;
import com.qrrest.model2.User;

public class ValidateUserService {
	public ValidateUserService(){
		
	}
	
	public static boolean validate(String vusrname, String vpwd){
		boolean result = false;
		UsersDao ud = new UsersDao();
		//从数据库获取用户名
		User usr = ud.getUserByName(vusrname);
		
		//验证用户提交的密码是否正确
		if(usr != null && usr.getUser_pwd().equals(vpwd)){
			result = true;
		}
		
		return result;
	}

}
