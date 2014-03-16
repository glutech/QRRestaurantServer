package com.qrrest.dao2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qrrest.model2.Dish;
import com.qrrest.model2.Menu;
import com.qrrest.model2.Restaurant;
import com.qrrest.model2.Table;

/*
 * 字段说明：
 * rest_type：表示餐厅主营业务总类，暂为字符串常量。列表定义位于RestaurantEditor.jsp
 * rest_status: 表示餐厅状态，0-餐厅停用、不再在公开展示，1-正常
 */
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
				rest.setRest_type(rs.getString("rest_type"));
				rest.setRest_status(rs.getInt("rest_status"));
				rest.setRest_addr(rs.getString("rest_addr"));
				rest.setRest_tel(rs.getString("rest_tel"));
				rest.setRest_upid(rs.getLong("rest_upid"));
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
				rest.setRest_status(rs.getInt("rest_status"));
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
		String sql = "insert into restaurants (rest_name, rest_desc, rest_type, rest_status, rest_addr, rest_tel, rest_upid) values(?, ?, ?, ?, ?, ?, ?)";
		Object[] params = {rest.getRest_name(), rest.getRest_desc(), rest.getRest_type(), rest.getRest_status(), rest.getRest_addr(), rest.getRest_tel(),rest.getRest_upid()==0?null:rest.getRest_upid()};
		flag = sqlE.execSqlWithoutRS(sql, params);
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
		String sql = "update restaurants set rest_name=?, rest_desc=?, rest_type=?, rest_status=?, rest_addr=?, rest_tel=?, rest_upid=? where rest_id=?";
		Object[] params = {rest.getRest_name(), rest.getRest_desc(), rest.getRest_type(), rest.getRest_status(), rest.getRest_addr(), rest.getRest_tel(),rest.getRest_upid()==0?null:rest.getRest_upid(),rest.getRest_id()};
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
				rest.setRest_status(rs.getInt("rest_status"));
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
				rest.setRest_status(rs.getInt("rest_status"));
				rest.setRest_upid(rs.getLong("rest_upid"));
				list.add(rest);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public String getRestName(long rest_id) {
		ResultSet rs = sqlE.execSqlWithRS("select rest_name from restaurants where rest_id = ?", new Object[]{rest_id});
		try {
			if(rs.next()) {
				return rs.getString(1);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
}
