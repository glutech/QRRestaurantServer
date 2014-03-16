package com.qrrest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.qrrest.model.TableTypeTerm;

public class TableTypeTermsDao extends BaseDao<TableTypeTerm> {

	@Override
	protected TableTypeTerm parseRS(ResultSet rs) throws SQLException {
		TableTypeTerm model = new TableTypeTerm();
		model.setTableTypeId(rs.getInt("table_type_id"));
		model.setTableTypeName(rs.getString("table_type_name"));
		return model;
	}

	public TableTypeTerm getTypeById(int tableTypeId) {
		String sql = "select * from table_type_terms where table_type_id = ?";
		Object[] params = { tableTypeId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToModel(rs);
	}

	public List<TableTypeTerm> getAllTypes() {
		String sql = "select * from table_type_terms";
		Object[] params = {};
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToList(rs);
	}

	public boolean insertType(TableTypeTerm type) {
		String sql = "insert into table_type_terms (table_type_name) values(?)";
		Object[] params = { type.getTableTypeName() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public boolean updateType(TableTypeTerm type) {
		String sql = "update table_type_terms set table_type_name = ? where table_type_id = ?";
		Object[] params = { type.getTableTypeName(), type.getTableTypeId() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public boolean deleteType(TableTypeTerm type) {
		String sql = "delete from table_type_terms where table_type_id = ?";
		Object[] params = { type.getTableTypeId() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

}
