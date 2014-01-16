package com.qrrest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CmMap {
	private SQLExecution sqlE = new SQLExecution();
	
	public CmMap() {
	}
	
	public boolean insertCMEntry(long cid, long mid){
		boolean flag = false;
		String sql = "insert into cmmap (customer_id, menu_id) values(?, ?)";
		Object[] params = {cid, mid};
		flag = sqlE.execSqlWithoutRS(sql, params);
		
		return flag;
	}
	
	public boolean delCMEntry(long cid, long mid){
		boolean flag = false;
		String sql = "delete from cmmap where customer_id = ? and menu_id = ?";
		Object[] params = {cid, mid};
		if (sqlE.execSqlWithoutRS(sql, params)) {
			flag = true;
		}
		return flag;
	}
	
	public List<Long> getMenusByCustomerId(long cid){
		List<Long> list = new ArrayList<Long>();
		String sql = "select menu_id from cmmap where customer_id=?";
		Object[] params = { cid };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				list.add(rs.getLong("menu_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
