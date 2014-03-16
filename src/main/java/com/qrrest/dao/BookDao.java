package com.qrrest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.qrrest.model.Book;
import com.qrrest.vo.OrderItemVo;

public class BookDao extends BaseDao<Book> {

	@Override
	protected Book parseRS(ResultSet rs) throws SQLException {
		Book model = new Book();
		model.setBookId(rs.getInt("book_id"));
		model.setBookName(rs.getString("book_name"));
		model.setBookTel(rs.getString("book_tel"));
		model.setBookTime(rs.getTimestamp("book_time"));
		model.setBookMemo(rs.getString("book_memo"));
		model.setUserId(rs.getInt("user_id"));
		model.setRestId(rs.getInt("rest_id"));
		model.setOrderIdNullabled(rs.getInt("order_id_nullabled"));
		if (model.getOrderIdNullabled() == 0)
			model.setOrderIdNullabled(null);
		return model;
	}

	public int getLastestInsertId() {
		return super.getLastInsertId();
	}

	public Book getBookByBookId(int bookId) {
		String sql = "select * from books where book_id = ?";
		Object[] params = { bookId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToModel(rs);
	}

	public Book getBooksOrderId(int orderId) {
		String sql = "select * from books where order_id_nullabled is not null and order_id_nullabled = ?";
		Object[] params = { orderId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToModel(rs);
	}

	public Book getLastestUnorderedBookByUserIdAndRestId(int userId, int restId) {
		String sql = "select * from books where rest_id = ? and user_id = ? and order_id_nullabled is null order by book_id desc limit 0, 1";
		Object[] params = { restId, userId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToModel(rs);
	}

	public List<Book> getBooksByRestId(int restId) {
		String sql = "select * from books where rest_id = ?";
		Object[] params = { restId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToList(rs);
	}

	public boolean modifyBookOrderId(Book book) {
		String sql = "update books set order_id_nullabled = ? where book_id = ?";
		Object[] params = { book.getOrderIdNullabled(), book.getBookId() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public boolean insertBook(Book book) {
		String sql = "insert into books(book_name, book_tel, book_time, book_memo, user_id, rest_id, order_id_nullabled) values(?, ?, ?, ?, ?, ?, ?)";
		Object[] parmas = { book.getBookName(), book.getBookTel(),
				book.getBookTime(), book.getBookMemo(), book.getUserId(),
				book.getRestId(), book.getOrderIdNullabled() };
		return getSqlExecution().execSqlWithoutRS(sql, parmas);
	}

	public boolean updateBook(Book book) {
		String sql = "update books set book_name = ?, book_tel = ?, book_time = ?, book_memo = ?, user_id = ?, rest_id=?, order_id_nullabled = ? where book_id = ?";
		Object[] params = { book.getBookName(), book.getBookTel(),
				book.getBookTime(), book.getBookMemo(), book.getUserId(),
				book.getRestId(), book.getOrderIdNullabled(), book.getBookId() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public boolean deleteBook(Book book) {
		String sql = "delete books where book_id = ?";
		Object[] params = { book.getBookId() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public double queryTotalPrice(int bookId) {
		String sql = "select sum(ta.dish_count * tb.dish_price) as price from ( "
				+ "select dish_id, dish_count from book_dishes_temp_map where book_id = ? "
				+ ") as ta left join dishes as tb on ta.dish_id = tb.dish_id";
		Object[] params = { bookId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseDouble(rs);
	}

	public List<OrderItemVo> getBookDishRelationship(int bookId) {
		String sql = "select * from book_dishes_temp_map where book_id = ?";
		Object[] params = { bookId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		List<OrderItemVo> result = new LinkedList<OrderItemVo>();
		try {
			while (rs.next()) {
				OrderItemVo m = new OrderItemVo();
				m.setDishId(rs.getInt("dish_id"));
				m.setDishCount(rs.getInt("dish_count"));
				result.add(m);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean fillBookDishRelationship(int bookId,
			List<OrderItemVo> orderItems) {
		// 清除原关系
		getSqlExecution().execSqlWithoutRS(
				"delete from book_dishes_temp_map where book_id = ?",
				new Object[] { bookId });
		// 填充关系
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>(orderItems.size() * 3);
		for (OrderItemVo orderItem : orderItems) {
			sql.append("insert into book_dishes_temp_map(book_id, dish_id, dish_count) values(?, ?, ?);");
			params.add(bookId);
			params.add(orderItem.getDishId());
			params.add(orderItem.getDishCount());
		}
		return getSqlExecution().execSqlWithoutRS(sql.toString(),
				params.toArray());
	}
}
