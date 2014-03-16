package com.qrrest.vo;

import java.sql.Timestamp;
import java.util.List;

public class MixedOrderVo {

	private String restName;
	private String restAddr;
	private String restTel;
	private boolean isBookOrder;
	private boolean isOrderActive;
	private int tableId;
	private Timestamp orderDate;
	private double orderPrice;
	private List<FullOrderItemVo> orderItems;

	public String getRestName() {
		return restName;
	}

	public void setRestName(String restName) {
		this.restName = restName;
	}

	public String getRestAddr() {
		return restAddr;
	}

	public void setRestAddr(String restAddr) {
		this.restAddr = restAddr;
	}

	public String getRestTel() {
		return restTel;
	}

	public void setRestTel(String restTel) {
		this.restTel = restTel;
	}

	public boolean isBookOrder() {
		return isBookOrder;
	}

	public void setBookOrder(boolean isBookOrder) {
		this.isBookOrder = isBookOrder;
	}

	public boolean isOrderActive() {
		return isOrderActive;
	}

	public void setOrderActive(boolean isOrderActive) {
		this.isOrderActive = isOrderActive;
	}

	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}

	public Timestamp getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}

	public double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public List<FullOrderItemVo> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<FullOrderItemVo> orderItems) {
		this.orderItems = orderItems;
	}

}
