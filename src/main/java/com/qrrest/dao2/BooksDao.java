package com.qrrest.dao2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qrrest.model2.Book;

/*需要考虑的一些方法：
 * 1、由id取book列表
 * 2、新建book
 * 3、删除book
 * 4、修改book
 * 5、取出所有book
 */

public class BooksDao {
	private SQLExecution sqlE = new SQLExecution();

	/**
	 * 通过预约id获取预约
	 * 
	 * @param username
	 * @return student entity
	 */
	public Book getBookById(long bookid) {
		Book book = new Book();
		String sql = "select * from books where book_id=?";
		Object[] params = { bookid };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				book.setBook_id(rs.getLong("book_id"));
				book.setBook_name(rs.getString("book_name"));
				book.setBook_tel(rs.getString("book_tel"));
				book.setBook_time(rs.getString("book_time"));
				book.setBook_memo(rs.getString("book_memo"));
				book.setCustomer_id(rs.getLong("customer_id"));
				book.setMenu_id(rs.getLong("menu_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return book;
	}
	
	/**
	 * 获取某顾客id下的全部预约
	 */
	public List<Book> getBooksByCustomerId(long customerid){
		List<Book> list = new ArrayList<Book>();
		String sql = "select * from books where customer_id=?";
		Object[] params = { customerid };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				Book book = new Book();
				
				book.setBook_id(rs.getLong("book_id"));
				book.setBook_name(rs.getString("book_name"));
				book.setBook_tel(rs.getString("book_tel"));
				book.setBook_time(rs.getString("book_time"));
				book.setBook_memo(rs.getString("book_memo"));
				book.setCustomer_id(rs.getLong("customer_id"));
				book.setMenu_id(rs.getLong("menu_id"));
				
				list.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 新增预约
	 */
	public Book insertBook(Book book) {
		boolean flag = false;
		long id = 0;
		
		String sql = "insert into books (book_name, book_tel, book_time, book_memo, customer_id, menu_id) values(?, ?, ?, ?, ?, ?)";
		Object[] params = {book.getBook_name(),book.getBook_tel(),book.getBook_time(), book.getBook_memo(), book.getCustomer_id(),book.getMenu_id()};
		flag = sqlE.execSqlWithoutRS(sql, params);
		
		if(flag){
			String sql2 = "SELECT @@IDENTITY";
			Object[] params2 = {};
			ResultSet rs = sqlE.execSqlWithRS(sql2, params2);
			try {
				while (rs.next()) {
					id = rs.getLong(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(id != 0){
			book = getBookById(id);
		}else{
			book = null;
		}
		
		return book;
	}
	
	/**
	 * 获取预约数，用于分页
	 * 
	 * @return
	 */
	public int getBooksCount(long customerid) {
		int i = -1;
		String sql = "select count(*) from books where customer_id = ?";
		Object[] params = {customerid};
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
	 * 根据预约id删除预约
	 * 
	 */
	public boolean deleteBook(long bookid) {
		boolean flag = false;
		String sql = "delete from books where book_id = ?";
		Object[] params = { bookid };
		if (sqlE.execSqlWithoutRS(sql, params)) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 修改预约信息
	 * 
	 */
	public boolean modifyBook(Book book) {
		boolean flag = false;
		String sql = "update books set book_name=?, book_tel=?, book_time=?, book_memo=?, customer_id=?, menu_id=? where book_id=?";
		Object[] params = {book.getBook_name(),book.getBook_tel(),book.getBook_time(), book.getBook_memo(),book.getCustomer_id(),book.getMenu_id(),book.getBook_id()};
		flag = sqlE.execSqlWithoutRS(sql, params);
		
		return flag;
	}
	
	/**
	 * 由预约id获取菜单id
	 */
	public long getMenuIdByBookId(long bookid){
		long menu_id = 0;
		String sql = "select menu_id from books where book_id=?";
		Object[] params = { bookid };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				menu_id = rs.getLong("menu_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return menu_id;
	}
}
