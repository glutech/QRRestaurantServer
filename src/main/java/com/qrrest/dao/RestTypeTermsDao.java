package com.qrrest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.qrrest.model.RestTypeTerm;

public class RestTypeTermsDao extends BaseDao<RestTypeTerm> {

	@Override
	protected RestTypeTerm parseRS(ResultSet rs) throws SQLException {
		RestTypeTerm model = new RestTypeTerm();
		model.setRestTypeId(rs.getInt("rest_type_id"));
		model.setRestTypeName(rs.getString("rest_type_name"));
		return model;
	}

	public RestTypeTerm getTypeById(int restTypeId) {
		String sql = "select * from rest_type_terms where rest_type_id = ?";
		Object[] params = { restTypeId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToModel(rs);
	}

	public List<RestTypeTerm> getAllTypes() {
		String sql = "select * from rest_type_terms";
		Object[] params = {};
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToList(rs);
	}

	public boolean insertType(RestTypeTerm type) {
		String sql = "insert into rest_type_terms (rest_type_name) values(?)";
		Object[] params = { type.getRestTypeName() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public boolean updateType(RestTypeTerm type) {
		String sql = "update rest_type_terms set rest_type_name = ? where rest_type_id = ?";
		Object[] params = { type.getRestTypeName(), type.getRestTypeId() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public boolean deleteType(RestTypeTerm type) {
		String sql = "delete from rest_type_terms where rest_type_id = ?";
		Object[] params = { type.getRestTypeId() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

}
