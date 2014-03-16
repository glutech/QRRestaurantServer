package com.qrrest.vo2;

import java.util.ArrayList;
import java.util.List;

import com.qrrest.model2.Dish;
import com.qrrest.model2.Menu;

public class MenuVo {
	Menu menu;
	ArrayList<Dish> dishes;
	String restName;
	String restAddr;
	
	public String getRestName() {
		return restName;
	}
	public void setRestName(String restName) {
		this.restName = restName;
	}
	public String getRestAddr() {
		return restAddr;
	}
	public void setRestAddr(String restAddr) {
		this.restAddr = restAddr;
	}
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	public ArrayList<Dish> getDishes() {
		return dishes;
	}
	public void setDishes(List<Dish> dishlist) {
		this.dishes = (ArrayList<Dish>) dishlist;
	}
}
