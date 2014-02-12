package com.qrrest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.qrrest.model.Dish;
import com.qrrest.model.User;

/*
 * 1. 根据用户名获取用户
 */

public class UsersDao {
	private SQLExecution sqlE = new SQLExecution();
	
	public User getUserByName(String usrname) {
		User usr = new User();
		String sql = "select * from users where user_name=?";
		Object[] params = { usrname };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				usr.setUser_id(rs.getLong("user_id"));
				usr.setUser_name(rs.getString("user_name"));
				usr.setUser_pwd(rs.getString("user_pwd"));
				usr.setUser_nickname(rs.getString("user_nickname"));
				usr.setRest_id(rs.getLong("rest_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usr;
	}

}
