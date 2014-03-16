package com.qrrest.service;

import java.sql.Timestamp;
import java.util.List;

import com.qrrest.dao.BookDao;
import com.qrrest.model.Book;
import com.qrrest.vo.OrderItemVo;

public class BookService {

	private BookDao bookDao = new BookDao();
	private int lastestInsertId;

	public int getLastestInsertId() {
		return lastestInsertId;
	}
	
	public Book getBookByBookId(int bookdId) {
		return bookDao.getBookByBookId(bookdId);
	}

	public Book getLastestUnorderedBookByUserIdAndRestId(int userId, int restId) {
		return bookDao.getLastestUnorderedBookByUserIdAndRestId(userId, restId);
	}

	public boolean modifyBookOrderId(Book book) {
		return bookDao.modifyBookOrderId(book);
	}

	public List<OrderItemVo> getBookDishRelationship(int bookId) {
		return bookDao.getBookDishRelationship(bookId);
	}

	public boolean createBook(Book book, List<OrderItemVo> orderItems) {
		book.setBookTime(new Timestamp(System.currentTimeMillis()));
		if (bookDao.insertBook(book)) {
			lastestInsertId = bookDao.getLastestInsertId();
			if (bookDao.fillBookDishRelationship(lastestInsertId, orderItems)) {
				return true;
			}
		}
		return false;
	}
	
	public double queryTotalPrice(int bookId) {
		return bookDao.queryTotalPrice(bookId);
	}
}
