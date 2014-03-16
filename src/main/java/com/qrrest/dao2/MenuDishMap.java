package com.qrrest.dao2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuDishMap {
	private SQLExecution sqlE = new SQLExecution();

	public MenuDishMap() {
	}

	public boolean insertMDEntry(Long mid, Long did, Integer num, double price) {
		boolean flag = false;
		String sql = "insert into menudishmap (menu_id, dish_id, map_num, map_price) values(?, ?, ?, ?)";
		Object[] params = { mid, did, num, price * num };
		flag = sqlE.execSqlWithoutRS(sql, params);

		return flag;
	}

	public boolean delMdEntry(long mid, long did) {
		boolean flag = false;
		String sql = "delete from menudishmap where menu_id = ? and dish_id = ?";
		Object[] params = { mid, did };
		if (sqlE.execSqlWithoutRS(sql, params)) {
			flag = true;
		}
		return flag;
	}

	public List<Long> getDishesByMenuId(long mid) {
		List<Long> list = new ArrayList<Long>();
		String sql = "select dish_id from menudishmap where menu_id=?";
		Object[] params = { mid };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				list.add(rs.getLong("dish_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
