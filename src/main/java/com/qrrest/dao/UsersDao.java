package com.qrrest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.qrrest.model.User;
import com.qrrest.model.User.UserCategoryEnum;

public class UsersDao extends BaseDao<User> {

	@Override
	protected User parseRS(ResultSet rs) throws SQLException {
		User model = new User();
		model.setUserId(rs.getInt("user_id"));
		model.setUserCategory(UserCategoryEnum.valueOf(rs
				.getString("user_category")));
		model.setUserName(rs.getString("user_name"));
		model.setUserPwd(rs.getString("user_pwd"));
		model.setUserDeviceId(rs.getString("user_device_id"));
		model.setUserNickName(rs.getString("user_nickname"));
		model.setUserContact(rs.getString("user_contact"));
		return model;
	}

	public User getUserById(int userId) {
		String sql = "select * from users where user_id = ?";
		Object[] params = { userId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToModel(rs);
	}

	public User getUserByDeviceId(String deviceId) {
		String sql = "select * from users where user_device_id is not null and user_device_id = ?";
		Object[] params = { deviceId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToModel(rs);
	}

	public User getUserByName(String name) {
		String sql = "select * from users where user_name is not null and user_name = ?";
		Object[] params = { name };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToModel(rs);
	}

	public boolean insertUser(User user) {
		String sql = "insert into users(user_category, user_name, user_pwd, user_device_id, user_nickname, user_contact) values(?, ?, ?, ?, ?, ?)";
		Object[] params = { user.getUserCategory().toString(),
				user.getUserName(), user.getUserPwd(), user.getUserDeviceId(),
				user.getUserNickName(), user.getUserContact() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public boolean updateUser(User user) {
		String sql = "update users set user_category = ?, user_name = ?, user_pwd = ?, user_device_id = ?, user_nickname = ?, user_contact = ? where user_id = ?";
		Object[] params = { user.getUserCategory().toString(),
				user.getUserName(), user.getUserPwd(), user.getUserDeviceId(),
				user.getUserNickName(), user.getUserContact(), user.getUserId() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public boolean deleteUser(User user) {
		String sql = "delete from users where user_id = ?";
		Object[] params = { user.getUserId() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public List<User> getOrderUsersByOrderId(int orderId) {
		String sql = "select * from users where user_id in (select user_id in order_users_map where order_id = ?)";
		Object[] params = { orderId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToList(rs);
	}
}