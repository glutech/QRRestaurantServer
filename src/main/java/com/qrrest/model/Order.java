package com.qrrest.model;

import java.sql.Timestamp;

public class Order {

	public static enum OrderStatusEnum {
		/**
		 * 表示订单处于预定状态
		 */
		booked,
		/**
		 * 表示订单已处于服务状态
		 */
		serving,
		/**
		 * 表示订单已结单
		 */
		completed;
	}

	private int orderId;
	private OrderStatusEnum orderStatus;
	private Timestamp orderTime;
	private double orderDuePrice;
	private double orderActualPrice;
	private String orderMemo;
	private Integer creatorUserIdNullabled;
	private Integer activeTableIdNullabled;
	private int restId;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public OrderStatusEnum getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatusEnum orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Timestamp getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	public double getOrderDuePrice() {
		return orderDuePrice;
	}

	public void setOrderDuePrice(double orderDuePrice) {
		this.orderDuePrice = orderDuePrice;
	}

	public double getOrderActualPrice() {
		return orderActualPrice;
	}

	public void setOrderActualPrice(double orderActualPrice) {
		this.orderActualPrice = orderActualPrice;
	}

	public String getOrderMemo() {
		return orderMemo;
	}

	public void setOrderMemo(String orderMemo) {
		this.orderMemo = orderMemo;
	}

	public Integer getCreatorUserIdNullabled() {
		return creatorUserIdNullabled;
	}

	public void setCreatorUserIdNullabled(Integer creatorUserIdNullabled) {
		this.creatorUserIdNullabled = creatorUserIdNullabled;
	}

	public Integer getActiveTableIdNullabled() {
		return activeTableIdNullabled;
	}

	public void setActiveTableIdNullabled(Integer activeTableIdNullabled) {
		this.activeTableIdNullabled = activeTableIdNullabled;
	}

	public int getRestId() {
		return restId;
	}

	public void setRestId(int restId) {
		this.restId = restId;
	}
}
