package com.qrrest.vo;

import java.util.List;

import com.qrrest.model.Dish;
import com.qrrest.model.RestDishCategoryTerm;
import com.qrrest.model.Restaurant;

public class DishesVo {

	private Restaurant rest;
	private List<Dish> dishList;
	private List<RestDishCategoryTerm> catList;

	public Restaurant getRest() {
		return rest;
	}

	public void setRest(Restaurant rest) {
		this.rest = rest;
	}

	public List<Dish> getDishList() {
		return dishList;
	}

	public void setDishList(List<Dish> dishList) {
		this.dishList = dishList;
	}

	public List<RestDishCategoryTerm> getCatList() {
		return catList;
	}

	public void setCatList(List<RestDishCategoryTerm> catList) {
		this.catList = catList;
	}

}
