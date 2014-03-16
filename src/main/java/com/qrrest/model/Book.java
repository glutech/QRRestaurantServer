package com.qrrest.model;

import java.sql.Timestamp;

public class Book {

	private int bookId;
	private String bookName;
	private String bookTel;
	private Timestamp bookTime;
	private String bookMemo;
	public int userId;
	public int restId;
	public Integer orderIdNullabled;

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookTel() {
		return bookTel;
	}

	public void setBookTel(String bookTel) {
		this.bookTel = bookTel;
	}

	public Timestamp getBookTime() {
		return bookTime;
	}

	public void setBookTime(Timestamp bookTime) {
		this.bookTime = bookTime;
	}

	public String getBookMemo() {
		return bookMemo;
	}

	public void setBookMemo(String bookMemo) {
		this.bookMemo = bookMemo;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRestId() {
		return restId;
	}

	public void setRestId(int restId) {
		this.restId = restId;
	}

	public Integer getOrderIdNullabled() {
		return orderIdNullabled;
	}

	public void setOrderIdNullabled(Integer orderIdNullabled) {
		this.orderIdNullabled = orderIdNullabled;
	}

}
