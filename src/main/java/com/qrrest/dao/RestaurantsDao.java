package com.qrrest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qrrest.model.Dish;
import com.qrrest.model.Menu;
import com.qrrest.model.Restaurant;
import com.qrrest.model.Table;

public class RestaurantsDao {
	private SQLExecution sqlE = new SQLExecution();

	/**
	 * 通过餐馆id获取餐馆
	 * 
	 */
	public Restaurant getRestById(long restid) {
		Restaurant rest = new Restaurant();
		String sql = "select * from restaurants where rest_id=?";
		Object[] params = { restid };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				rest.setRest_id(rs.getLong("rest_id"));
				rest.setRest_name(rs.getString("rest_name"));
				rest.setRest_desc(rs.getString("rest_desc"));
				rest.setRest_addr(rs.getString("rest_addr"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rest;
	}
	
	/**
	 * 获取某餐厅id下的全部下属餐厅
	 */
	public List<Restaurant> getRestsByRestId(long restid){
		List<Restaurant> list = new ArrayList<Restaurant>();
		String sql = "select * from restaurants where rest_upid=rest_id";
		Object[] params = { };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				Restaurant rest = new Restaurant();
				rest.setRest_id(rs.getLong("rest_id"));
				rest.setRest_name(rs.getString("rest_name"));
				rest.setRest_desc(rs.getString("rest_desc"));
				rest.setRest_addr(rs.getString("rest_addr"));
				rest.setRest_tel(rs.getString("rest_tel"));
				rest.setRest_type(rs.getString("rest_type"));
				rest.setRest_upid(rs.getLong("rest_upid"));
				list.add(rest);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 新增餐馆
	 */
	public boolean insertRest(Restaurant rest) {
		boolean flag = false;
		String sql = "insert into restaurants (rest_name, rest_desc, rest_type, rest_addr, rest_tel, rest_upid) values(?, ?, ?, ?, ?, ?)";
		Object[] params = {rest.getRest_name(), rest.getRest_desc(), rest.getRest_type(),rest.getRest_tel(),rest.getRest_upid()};
		sqlE.execSqlWithRS(sql, params);
		
		return flag;
	}
	
	/**
	 * 获取餐馆数，用于分页
	 * 
	 * @return
	 */
	public int getRestsCount() {
		int i = -1;
		String sql = "select count(*) from restaurant";
		Object[] params = {};
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
	 * 根据餐馆id删除餐馆
	 * 
	 */
	public boolean deleteRest(long restid) {
		boolean flag = false;
		String sql = "delete from restaurants where rest_id = ?";
		Object[] params = { restid };
		if (sqlE.execSqlWithoutRS(sql, params)) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 修改餐馆信息
	 * 
	 */
	public boolean modifyRest(Restaurant rest) {
		boolean flag = false;
		String sql = "update restaurants set rest_name=?, rest_desc=?, rest_type=?, rest_addr=?, rest_tel=?, rest_upid=? where rest_id=?";
		Object[] params = {rest.getRest_name(), rest.getRest_desc(), rest.getRest_type(),rest.getRest_tel(),rest.getRest_upid(),rest.getRest_id()};
		flag = sqlE.execSqlWithoutRS(sql, params);
		
		return flag;
	}
	
	public List<Restaurant> getAllRests(){
		List<Restaurant> list = new ArrayList<Restaurant>();
		String sql = "select * from restaurants";
		Object[] params = { };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				Restaurant rest = new Restaurant();
				rest.setRest_id(rs.getLong("rest_id"));
				rest.setRest_name(rs.getString("rest_name"));
				rest.setRest_desc(rs.getString("rest_desc"));
				rest.setRest_addr(rs.getString("rest_addr"));
				rest.setRest_tel(rs.getString("rest_tel"));
				rest.setRest_type(rs.getString("rest_type"));
				rest.setRest_upid(rs.getLong("rest_upid"));
				list.add(rest);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Restaurant> getRestsByName(String keyword){
		List<Restaurant> list = new ArrayList<Restaurant>();
		String sql = "select * from restaurants where rest_name like ?";
		Object[] params = {"%"+keyword+"%"};
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				Restaurant rest = new Restaurant();
				rest.setRest_id(rs.getLong("rest_id"));
				rest.setRest_name(rs.getString("rest_name"));
				rest.setRest_desc(rs.getString("rest_desc"));
				rest.setRest_addr(rs.getString("rest_addr"));
				rest.setRest_tel(rs.getString("rest_tel"));
				rest.setRest_type(rs.getString("rest_type"));
				rest.setRest_upid(rs.getLong("rest_upid"));
				list.add(rest);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}