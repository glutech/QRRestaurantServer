package com.qrrest.vo;

import java.util.ArrayList;
import java.util.List;

import com.qrrest.model.Dish;
import com.qrrest.model.Menu;

public class MenuVo {
	Menu menu;
	ArrayList<Dish> dishes;
	
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
