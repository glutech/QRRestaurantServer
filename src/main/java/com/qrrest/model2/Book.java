package com.qrrest.model2;

public class Book {
	private long book_id;
	private String book_name;
	private String book_tel;
	private String book_time;
	private String book_memo;
	private long customer_id;
	private long menu_id;
	
	public long getBook_id() {
		return book_id;
	}
	public void setBook_id(long book_id) {
		this.book_id = book_id;
	}
	public String getBook_name() {
		return book_name;
	}
	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}
	public String getBook_tel() {
		return book_tel;
	}
	public void setBook_tel(String book_tel) {
		this.book_tel = book_tel;
	}
	public long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}
	public long getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(long menu_id) {
		this.menu_id = menu_id;
	}
	public String getBook_time() {
		return book_time;
	}
	public void setBook_time(String book_time) {
		this.book_time = book_time;
	}
	public String getBook_memo() {
		return book_memo;
	}
	public void setBook_memo(String book_memo) {
		this.book_memo = book_memo;
	}
}
