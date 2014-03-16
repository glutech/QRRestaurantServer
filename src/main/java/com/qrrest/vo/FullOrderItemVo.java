package com.qrrest.vo;

import com.qrrest.model.Dish;

public class FullOrderItemVo {

	private Dish dish;
	private int count;

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
