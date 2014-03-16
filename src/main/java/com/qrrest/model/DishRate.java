package com.qrrest.model;

public class DishRate {

	private int dishId;
	private int orderedNum;
	private int recommendNum;
	private int rankingNum;
	private float rankingValue;

	public int getDishId() {
		return dishId;
	}

	public void setDishId(int dishId) {
		this.dishId = dishId;
	}

	public int getOrderedNum() {
		return orderedNum;
	}

	public void setOrderedNum(int orderedNum) {
		this.orderedNum = orderedNum;
	}

	public int getRecommendNum() {
		return recommendNum;
	}

	public void setRecommendNum(int recommendNum) {
		this.recommendNum = recommendNum;
	}

	public int getRankingNum() {
		return rankingNum;
	}

	public void setRankingNum(int rankingNum) {
		this.rankingNum = rankingNum;
	}

	public float getRankingValue() {
		return rankingValue;
	}

	public void setRankingValue(float rankingValue) {
		this.rankingValue = rankingValue;
	}

}
