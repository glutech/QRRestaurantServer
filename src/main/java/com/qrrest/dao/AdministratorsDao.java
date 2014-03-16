package com.qrrest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.qrrest.model.Administrator;

public class AdministratorsDao extends BaseDao<Administrator> {

	@Override
	protected Administrator parseRS(ResultSet rs) throws SQLException {
		Administrator model = new Administrator();
		model.setAdminId(rs.getInt("admin_id"));
		model.setAdminName(rs.getString("admin_name"));
		model.setAdminPwd(rs.getString("admin_pwd"));
		model.setAdminNickname(rs.getString("admin_nickname"));
		model.setRestIdNullabled((Integer) rs.getObject("rest_id_nullabled"));
		return model;
	}

	public Administrator getAdminByName(String name) {
		String sql = "select * from administrators where admin_name is not null and admin_name = ?";
		Object[] params = { name };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToModel(rs);
	}

}
