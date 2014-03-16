package com.qrrest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.qrrest.model.Restaurant;

public class RestaurantsDao extends BaseDao<Restaurant> {

	@Override
	protected Restaurant parseRS(ResultSet rs) throws SQLException {
		Restaurant model = new Restaurant();
		model.setRestId(rs.getInt("rest_id"));
		model.setRestName(rs.getString("rest_name"));
		model.setRestTypeId(rs.getInt("rest_type_id"));
		model.setRestDesc(rs.getString("rest_desc"));
		model.setRestAddr(rs.getString("rest_addr"));
		model.setRestTel(rs.getString("rest_tel"));
		model.setRestUpidNullabled((Integer) rs
				.getObject("rest_upid_nullabled"));
		return model;
	}

	public Restaurant getRestById(int restId) {
		String sql = "select * from restaurants where rest_id = ?";
		Object[] params = { restId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToModel(rs);
	}

	public String getRestNameById(int restId) {
		String sql = "select rest_name from restaurants where rest_id = ?";
		Object[] params = { restId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseString(rs);
	}

	public List<Restaurant> getRestsByName(String keyword) {
		String sql = "select * from restaurants where rest_name like ?";
		Object[] params = { "%" + keyword + "%" };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToList(rs);
	}

	public int getRestsCount() {
		String sql = "select count(rest_id) from restaurants";
		Object[] params = {};
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseInt(rs);
	}

	public List<Restaurant> getSubRestsById(int parentRestId) {
		String sql = "select * from restaurants where rest_upid_nullabled is not null and rest_upid_nullabled = ?";
		Object[] params = { parentRestId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToList(rs);
	}

	public List<Restaurant> getAllRests() {
		String sql = "select * from restaurants";
		Object[] params = {};
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToList(rs);
	}

	public boolean insertRest(Restaurant rest) {
		String sql = "insert into restaurants(rest_name, rest_type_id, rest_desc, rest_addr, rest_tel, rest_upid_nullabled) values(?, ?, ?, ?, ?, ?)";
		Object[] params = { rest.getRestName(), rest.getRestTypeId(),
				rest.getRestDesc(), rest.getRestAddr(), rest.getRestTel(),
				rest.getRestUpidNullabled() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public boolean updateRest(Restaurant rest) {
		String sql = "update restaurants set rest_name = ?, rest_type_id = ?, rest_desc = ?, rest_addr = ?, rest_tel = ?, rest_upid_nullabled = ? where rest_id = ?";
		Object[] params = { rest.getRestName(), rest.getRestTypeId(),
				rest.getRestDesc(), rest.getRestAddr(), rest.getRestTel(),
				rest.getRestUpidNullabled(), rest.getRestId() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public boolean deleteRest(Restaurant rest) {
		String sql = "delete from restaurants where rest_id = ?";
		Object[] params = { rest.getRestId() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}
}
