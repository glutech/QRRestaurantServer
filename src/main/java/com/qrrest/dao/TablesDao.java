package com.qrrest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.qrrest.model.Table;
import com.qrrest.model.Table.TableStatusEnum;

public class TablesDao extends BaseDao<Table> {

	@Override
	protected Table parseRS(ResultSet rs) throws SQLException {
		Table model = new Table();
		model.setTableId(rs.getInt("table_id"));
		model.setTableStatus(TableStatusEnum.valueOf(rs
				.getString("table_status")));
		model.setTableName(rs.getString("table_name"));
		model.setTableTypeId(rs.getInt("table_type_id"));
		model.setTableSort(rs.getInt("table_sort"));
		model.setRestId(rs.getInt("rest_id"));
		return model;
	}

	public Table getTableById(int tableId) {
		String sql = "select * from tables where table_id = ?";
		Object[] params = { tableId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToModel(rs);
	}

	public List<Table> getTablesByRestId(int restId) {
		String sql = "select * from tables where rest_id = ? order by table_sort desc, table_id asc";
		Object[] params = { restId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToList(rs);
	}

	public int getTablesCount() {
		String sql = "select count(table_id) from tables";
		Object[] params = {};
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseInt(rs);
	}

	public boolean modifyTableStatus(Table table) {
		String sql = "update tables set table_status = ? where table_id = ?";
		Object[] params = { table.getTableStatus().toString(),
				table.getTableId() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public boolean insertTable(Table table) {
		String sql = "insert into tables(table_status, table_name, table_type_id, table_sort, rest_id) values(?, ?, ?, ?, ?)";
		Object[] params = { table.getTableStatus().toString(),
				table.getTableName(), table.getTableTypeId(),
				table.getTableSort(), table.getRestId() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public boolean updateTable(Table table) {
		String sql = "update tables set table_status = ?, table_name = ?, table_type_id = ?, table_sort = ?, rest_id = ? where table_id = ?";
		Object[] params = { table.getTableStatus().toString(),
				table.getTableName(), table.getTableTypeId(),
				table.getTableSort(), table.getRestId(), table.getTableId() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public boolean deleteTable(Table table) {
		String sql = "delete from tables where table_id = ?";
		Object[] params = { table.getTableId() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

}
