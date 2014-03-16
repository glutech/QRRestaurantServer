package com.qrrest.vo;

import com.qrrest.model.RestDishCategoryTerm;

public class RestDishCategoryTermVo {

	private RestDishCategoryTerm cat;
	private int usedNum;

	public RestDishCategoryTerm getCat() {
		return cat;
	}

	public void setCat(RestDishCategoryTerm cat) {
		this.cat = cat;
	}

	public int getUsedNum() {
		return usedNum;
	}

	public void setUsedNum(int usedNum) {
		this.usedNum = usedNum;
	}

}
