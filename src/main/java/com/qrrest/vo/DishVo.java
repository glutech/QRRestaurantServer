package com.qrrest.vo;

import java.util.List;

import com.qrrest.model.Dish;
import com.qrrest.model.DishTagTerm;
import com.qrrest.model.RestDishCategoryTerm;

public class DishVo {

	private Dish dish;
	private List<DishTagTerm> tags;
	private RestDishCategoryTerm cat;

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

	public List<DishTagTerm> getTags() {
		return tags;
	}

	public void setTags(List<DishTagTerm> tags) {
		this.tags = tags;
	}

	public RestDishCategoryTerm getCat() {
		return cat;
	}

	public void setCat(RestDishCategoryTerm cat) {
		this.cat = cat;
	}

}
