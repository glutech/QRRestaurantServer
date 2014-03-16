package com.qrrest.vo2;

import java.util.ArrayList;

import com.qrrest.model2.Restaurant;

public class ScanResultVo {
	Restaurant rest;
	ArrayList<DishVo> dishes;
	
	public Restaurant getRest() {
		return rest;
	}
	public void setRest(Restaurant rest) {
		this.rest = rest;
	}
	public ArrayList<DishVo> getDishes() {
		return dishes;
	}
	public void setDishes(ArrayList<DishVo> dishes) {
		this.dishes = dishes;
	}
	
}
