package com.qrrest.vo2;

import com.qrrest.model2.Category;
import com.qrrest.model2.Dish;

public class DishVo {

	private Dish dish;
	private Category cat;

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

	public Category getCat() {
		return cat;
	}

	public void setCat(Category cat) {
		this.cat = cat;
	}
}
