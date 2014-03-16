package com.qrrest.vo;

import com.qrrest.model.DishTagTerm;

public class DishTagTermVo {

	private DishTagTerm tag;
	private int restUsedNum;
	private int allUsedNum;

	public DishTagTerm getTag() {
		return tag;
	}

	public void setTag(DishTagTerm tag) {
		this.tag = tag;
	}

	public int getRestUsedNum() {
		return restUsedNum;
	}

	public void setRestUsedNum(int restUsedNum) {
		this.restUsedNum = restUsedNum;
	}

	public int getAllUsedNum() {
		return allUsedNum;
	}

	public void setAllUsedNum(int allUsedNum) {
		this.allUsedNum = allUsedNum;
	}

}
