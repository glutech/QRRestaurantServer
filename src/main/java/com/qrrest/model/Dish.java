package com.qrrest.model;

public class Dish {

	public static enum DishStatusEnum {
		/**
		 * 表示菜品可正常使用
		 */
		normal("正常"),
		/**
		 * 表示菜品已在使用中，含义上包含normal
		 */
		active("使用中"),
		/**
		 * 表示菜品因时令等原因暂停使用
		 */
		blocked("禁用");

		private String nameZHCN;

		private DishStatusEnum(String nameZHCN) {
			this.nameZHCN = nameZHCN;
		}

		public String getNameZHCN() {
			return this.nameZHCN;
		}
	}

	private int dishId;
	private DishStatusEnum dishStatus;
	private String dishName;
	private double dishPrice;
	private String dishPic;
	private String dishDesc;
	private int restDishCatId;

	public int getDishId() {
		return dishId;
	}

	public void setDishId(int dishId) {
		this.dishId = dishId;
	}

	public DishStatusEnum getDishStatus() {
		return dishStatus;
	}

	public void setDishStatus(DishStatusEnum dishStatus) {
		this.dishStatus = dishStatus;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public double getDishPrice() {
		return dishPrice;
	}

	public void setDishPrice(double dishPrice) {
		this.dishPrice = dishPrice;
	}

	public String getDishPic() {
		return dishPic;
	}

	public void setDishPic(String dishPic) {
		this.dishPic = dishPic;
	}

	public String getDishDesc() {
		return dishDesc;
	}

	public void setDishDesc(String dishDesc) {
		this.dishDesc = dishDesc;
	}

	public int getRestDishCatId() {
		return restDishCatId;
	}

	public void setRestDishCatId(int restDishCatId) {
		this.restDishCatId = restDishCatId;
	}

}
