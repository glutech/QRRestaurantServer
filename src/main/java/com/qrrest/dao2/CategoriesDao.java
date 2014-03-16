package com.qrrest.dao2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qrrest.model2.Category;

/*
 * 1、根据餐厅id获取所有类别
 * 2、根据id获取类别
 * 3、新增类别
 * 4、修改类别
 * 5、删除类别
 */

public class CategoriesDao {
	
	private SQLExecution sqlE = new SQLExecution();
	
	/**
	 * 通过餐厅id获取类别列表
	 * @param Id
	 * @return
	 */
	public List<Category> getCatsByRestId(long restid) {
		List<Category> list = new ArrayList<Category>();
		ConnectionFactory factory = ConnectionFactory.getInstance();
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = factory.getConnection();
			String sql = "select * from categories where rest_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, restid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Category cat = new Category();
				cat.setCat_id(rs.getLong("cat_id"));
				cat.setCat_name(rs.getString("cat_name"));
				cat.setRest_id(rs.getLong("rest_id"));
				list.add(cat);
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			factory.freeConnection();
		}
		return list;
	}
	
	/**
	 * 新增类别
	 * @param Id
	 * @return
	 */
	public boolean insertCat(String catname, long restid) {
		boolean flag = false;
		String sql = "insert into categories (cat_name, rest_id) values(?, ?)";
		Object[] params = {catname, restid};
		flag = sqlE.execSqlWithoutRS(sql, params);
		return flag;
	}
	
	/**
	 * 根据id修改类别
	 * 
	 * @param username
	 * @param staus
	 * @return
	 */
	public boolean modifyCat(String catname, long catid) {
		boolean flag = false;
		String sql = "update categories set cat_name = ? where cat_id = ?";
		Object[] params = {catname, catid};
		flag = sqlE.execSqlWithoutRS(sql, params);
		return flag;
	}
	
	/**
	 * 根据id删除类别
	 * 
	 * @param stuId
	 * @return
	 */
	public boolean deleteCat(long catid) {
		boolean flag = false;
		String sql = "delete from categories where cat_id = ?";
		Object[] params = { catid };
		if (sqlE.execSqlWithoutRS(sql, params)) {
			flag = true;
		}
		return flag;
	}
	
	public long getCatIdByDishId(long did){
		long cid = 0;
		String sql = "select cat_id from dishes where dish_id=?";
		Object[] params = { did };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				cid = rs.getLong("cat_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cid;
	}
	
	public Category getCatById(long cid){
		Category cat = new Category();
		String sql = "select * from categories where cat_id=?";
		Object[] params = { cid };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				cat.setCat_id(rs.getLong("cat_id"));
				cat.setCat_name(rs.getString("cat_name"));
				cat.setRest_id(rs.getLong("rest_id"));				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cat;
	}
}
