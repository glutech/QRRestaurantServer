package com.qrrest.vo;

import com.qrrest.model.Book;


public class BookVo {
	Book book;
	MenuVo menu;
	
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public MenuVo getMenu() {
		return menu;
	}
	public void setMenu(MenuVo menu) {
		this.menu = menu;
	}
}
