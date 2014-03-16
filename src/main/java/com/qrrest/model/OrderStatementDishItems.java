package com.qrrest.model;

public class OrderStatementDishItems {

	private int itemId;
	private short itemCount;
	private int orderId;
	private String dishName;
	private double dishPrice;
	private String dishPic;
	private String dishDesc;
	private String dishCatName;
	private Integer sourceDishIdNullabled;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public short getItemCount() {
		return itemCount;
	}

	public void setItemCount(short itemCount) {
		this.itemCount = itemCount;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
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

	public String getDishCatName() {
		return dishCatName;
	}

	public void setDishCatName(String dishCatName) {
		this.dishCatName = dishCatName;
	}

	public Integer getSourceDishIdNullabled() {
		return sourceDishIdNullabled;
	}

	public void setSourceDishIdNullabled(Integer sourceDishIdNullabled) {
		this.sourceDishIdNullabled = sourceDishIdNullabled;
	}

}
