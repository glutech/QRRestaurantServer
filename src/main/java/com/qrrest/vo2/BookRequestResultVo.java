package com.qrrest.vo2;

import java.util.ArrayList;

public class BookRequestResultVo {
	String book_id;
	String book_name;
	String book_tel;
	String customer_id;
	String rest_id;
	ArrayList<String> dishes;
	
	public String getBook_id() {
		return book_id;
	}
	public void setBook_id(String book_id) {
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
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public ArrayList<String> getDishes() {
		return dishes;
	}
	public void setDishes(ArrayList<String> dishes) {
		this.dishes = dishes;
	}
	public String getRest_id() {
		return rest_id;
	}
	public void setRest_id(String rest_id) {
		this.rest_id = rest_id;
	}
	
}
