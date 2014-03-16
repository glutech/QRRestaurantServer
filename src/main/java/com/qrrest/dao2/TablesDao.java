package com.qrrest.dao2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qrrest.model2.Dish;
import com.qrrest.model2.Table;

public class TablesDao {
	private SQLExecution sqlE = new SQLExecution();

	/**
	 * 通过桌id获取餐桌
	 * 
	 * @param username
	 * @return student entity
	 */
	public Table getTableById(long tableid) {
		Table table = new Table();
		String sql = "select * from tables where table_id=?";
		Object[] params = { tableid };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				table.setTable_id(rs.getLong("table_id"));
				table.setTable_name(rs.getString("table_name"));
				table.setTable_sort(rs.getInt("table_sort"));
				table.setTable_type(rs.getString("table_type"));
				table.setTable_status(rs.getInt("table_status"));
				table.setRest_id(rs.getLong("rest_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return table;
	}

	public long getRestIdByTableId(long tid) {
		long rid = 0;
		String sql = "select rest_id from tables where table_id=?";
		Object[] params = { tid };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				rid = rs.getLong("rest_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rid;
	}

	/**
	 * 获取某餐厅id下的全部餐桌
	 */
	public List<Table> getTablesByRestId(long restid) {
		List<Table> list = new ArrayList<Table>();
		String sql = "select * from tables where rest_id=? order by table_sort desc, table_id asc";
		Object[] params = { restid };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				Table table = new Table();
				table.setTable_id(rs.getLong("table_id"));
				table.setTable_name(rs.getString("table_name"));
				table.setTable_type(rs.getString("table_type"));
				table.setTable_sort(rs.getInt("table_sort"));
				table.setTable_status(rs.getInt("table_status"));
				table.setRest_id(rs.getLong("rest_id"));

				list.add(table);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 新增餐桌
	 */
	public boolean insertTable(Table table) {
		boolean flag = false;
		String sql = "insert into tables (table_name, table_type, table_sort, table_status, rest_id) values(?, ?, ?, ?, ?)";
		Object[] params = { table.getTable_name(), table.getTable_type(),
				table.getTable_sort(), table.getTable_status(),
				table.getRest_id() };
		flag = sqlE.execSqlWithoutRS(sql, params);
		return flag;
	}

	/**
	 * 获取餐桌数，用于分页
	 * 
	 * @return
	 */
	public int getTablesCount(long restid) {
		int i = -1;
		String sql = "select count(*) from tables where rest_id = ?";
		Object[] params = { restid };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				i = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * 根据餐桌id删除餐桌
	 * 
	 */
	public boolean deleteTable(long tableid) {
		boolean flag = false;
		String sql = "delete from tables where table_id = ?";
		Object[] params = { tableid };
		if (sqlE.execSqlWithoutRS(sql, params)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 根据餐桌id修改餐桌status字段
	 * 
	 * @param username
	 * @param staus
	 * @return
	 */
	public boolean modifyTableStatus(long tableid, int status) {
		boolean flag = false;
		String sql = "update tables set table_status = ? where table_id = ?";
		Object[] params = { status, tableid };
		flag = sqlE.execSqlWithoutRS(sql, params);

		return flag;
	}

	public int checkTableStatus(long tid) {
		int result = 1;
		String sql = "select table_status from tables where table_id = ?";
		Object[] params = { tid };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 修改餐桌信息
	 */
	public boolean modifyTable(Table table) {
		boolean flag = false;
		String sql = "update tables set table_name=?, table_type=?, table_sort=?, table_status=?, rest_id=? where table_id=?";
		Object[] params = { table.getTable_name(), table.getTable_type(),
				table.getTable_sort(), table.getTable_status(),
				table.getRest_id(), table.getTable_id() };
		flag = sqlE.execSqlWithoutRS(sql, params);

		return flag;
	}

	/**
	 * 由餐桌id获取菜品 参数tableid:桌号 参数menustatus：0为已预订，1为已确认订单，2为已结账
	 */
	public List<Dish> getDishesByTableId(long tableid, int menustatus) {
		List<Dish> list = new ArrayList<Dish>();
		MenusDao menusdao = new MenusDao();
		long menuid = 0;
		String sql = "select * from menus where table_id=? and menu_status=?";
		Object[] params = { tableid, menustatus };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				menuid = rs.getLong("menu_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		list = menusdao.getDishesByMenuId(menuid);

		return list;
	}
}
