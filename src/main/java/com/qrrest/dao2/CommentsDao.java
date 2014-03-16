package com.qrrest.dao2;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qrrest.model2.Comment;
import com.qrrest.util.StringDateConverter;

public class CommentsDao {
	private SQLExecution sqlE = new SQLExecution();

	/**
	 * 通过评论id获取评论
	 * 
	 * @param username
	 * @return student entity
	 */
	public Comment getCommentById(long commentid) {
		Comment comment = new Comment();
		String sql = "select * from comments where comment_id=?";
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
	
	public ArrayList<Comment> getCommentByDishId(long did){
		ArrayList<Comment> list = new ArrayList<Comment>();
		String sql = "select * from comments where dish_id=?";
		Object[] params = { did };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				Comment comment = new Comment();
				comment.setComment_id(rs.getLong("comment_id"));
				comment.setComment_date(StringDateConverter.dateToString(rs.getDate("comment_date"),"MEDIUM"));
				comment.setComment_content(rs.getString("comment_content"));
				comment.setCustomer_id(rs.getLong("customer_id"));
				comment.setDish_id(rs.getLong("dish_id"));
				comment.setDish_rate(rs.getInt("dish_rate"));
				list.add(comment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 获取某菜品id下的全部评论
	 */
	public List<Comment> getCommentsByDishId(long dishid){
		List<Comment> list = new ArrayList<Comment>();
		String sql = "select * from comments where dish_id=?";
		Object[] params = { dishid };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				Comment comment = new Comment();
				comment.setComment_id(rs.getLong("comment_id"));
				comment.setComment_content(rs.getString("comment_content"));
				comment.setComment_date(StringDateConverter.dateToString(rs.getDate("comment_date"),"MEDIUM"));
				comment.setDish_id(rs.getLong("dish_id"));
				comment.setCustomer_id(rs.getLong("customer_id"));
				comment.setDish_rate(rs.getInt("dish_rate"));
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
	public boolean insertComment(Comment comment) {
		boolean flag = false;
		String sql = "insert into comments (comment_content, comment_date, dish_id, customer_id, dish_rate) values(?, ?, ?, ?, ?)";
		Object[] params = {comment.getComment_content(), new Date(System.currentTimeMillis()),comment.getDish_id(),comment.getCustomer_id(), comment.getDish_rate()};
		flag = sqlE.execSqlWithoutRS(sql, params);
		
		return flag;
	}
	
	/**
	 * 获取评论数，用于分页
	 * 
	 * @return
	 */
	public int getCommentsCount(long dishid) {
		int i = -1;
		String sql = "select count(*) from comments where dish_id = ?";
		Object[] params = {dishid};
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
	public boolean deleteComment(long commentid) {
		boolean flag = false;
		String sql = "delete from comments where comment_id = ?";
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
	public boolean modifyComment(Comment comment) {
		boolean flag = false;
		String sql = "update comments set comment_content=?, comment_date=?, dish_id=?, customer_id=? dish_rate=? where comment_id=?";
		Object[] params = {comment.getComment_content(),new Date(System.currentTimeMillis()),comment.getDish_id(),comment.getCustomer_id(),comment.getDish_rate(), comment.getComment_id()};
		flag = sqlE.execSqlWithoutRS(sql, params);
		
		return flag;
	}
}
