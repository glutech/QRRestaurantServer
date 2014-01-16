package com.qrrest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.qrrest.model.Rcomment;
import com.qrrest.util.StringDateConverter;

public class RcommentsDao {
	private SQLExecution sqlE = new SQLExecution();

	/**
	 * 通过评论id获取评论
	 * 
	 * @param username
	 * @return student entity
	 */
	public Rcomment getCommentById(long commentid) {
		Rcomment comment = new Rcomment();
		String sql = "select * from rcomments where comment_id=?";
		Object[] params = { commentid };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return comment;
	}
	
	/**
	 * 获取某餐厅id下的全部评论
	 */
	public List<Rcomment> getRCommentsByRestId(long restid){
		List<Rcomment> list = new ArrayList<Rcomment>();
		String sql = "select * from rcomments where rest_id=?";
		Object[] params = { restid };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				Rcomment comment = new Rcomment();
				comment.setRcomment_id(rs.getLong("rcomment_id"));
				comment.setRcomment_content(rs.getString("rcomment_content"));
				comment.setRcomment_date(StringDateConverter.dateToString(rs.getDate("rcomment_date"),"MEDIUM"));
				comment.setRest_id(rs.getLong("rest_id"));
				comment.setCustomer_id(rs.getLong("customer_id"));
				list.add(comment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 新增评论
	 */
	public boolean insertRcomment(Rcomment comment) {
		boolean flag = false;
		String sql = "insert into rcomments (rcomment_content, rcomment_date, rest_id, customer_id) values(?, ?, ?, ?)";
		Object[] params = {comment.getRcomment_content(),new Date(System.currentTimeMillis()),comment.getRest_id(),comment.getCustomer_id()};
		flag = sqlE.execSqlWithoutRS(sql, params);
		
		return flag;
	}
	
	/**
	 * 获取评论数，用于分页
	 * 
	 * @return
	 */
	public int getRcommentsCount(long restid) {
		int i = -1;
		String sql = "select count(*) from rcomments where rest_id = ?";
		Object[] params = {restid};
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
	 * 根据评论id删除评论
	 * 
	 */
	public boolean deleteRcomment(long commentid) {
		boolean flag = false;
		String sql = "delete from rcomments where rcomment_id = ?";
		Object[] params = { commentid };
		if (sqlE.execSqlWithoutRS(sql, params)) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 修改评论
	 * 
	 */
	public boolean modifyRcomment(Rcomment comment) {
		boolean flag = false;
		String sql = "update comments set rcomment_content=?, rcomment_date=?, rest_id=?, customer_id=? where comment_id=?";
		Object[] params = {comment.getRcomment_content(),new Date(System.currentTimeMillis()),comment.getRest_id(),comment.getCustomer_id(),comment.getRcomment_id()};
		flag = sqlE.execSqlWithoutRS(sql, params);
		
		return flag;
	}
}
