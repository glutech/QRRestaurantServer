package com.qrrest.model;

public class OrderStatementTableItem {

	private int itemId;
	private int orderId;
	private String tableName;
	private String tableTypeName;
	private Integer sourceTableIdNullabled;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableTypeName() {
		return tableTypeName;
	}

	public void setTableTypeName(String tableTypeName) {
		this.tableTypeName = tableTypeName;
	}

	public Integer getSourceTableIdNullabled() {
		return sourceTableIdNullabled;
	}

	public void setSourceTableIdNullabled(Integer sourceTableIdNullabled) {
		this.sourceTableIdNullabled = sourceTableIdNullabled;
	}

}
