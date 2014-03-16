package com.qrrest.model2;

public class Table {
	private long table_id;
	private String table_name;
	/**
	 * 预留字段，表示餐桌类型，暂为空
	 */
	private String table_type;
	private int table_sort;
	/**
	 * 参见常量定义
	 */
	private int table_status;
	private long rest_id;

	/**
	 * table_status常量，表示餐桌因餐厅内部原因暂不公开使用
	 */
	public static final int STATUS_BLOCKED = 0;
	/**
	 * table_status常量，表示餐桌正常公开使用
	 */
	public static final int STATUS_OK = 1;
	/**
	 * table_status常量，表示餐桌已被删除
	 */
	public static final int STATUS_DELETED = 2;

	public long getTable_id() {
		return table_id;
	}

	public void setTable_id(long table_id) {
		this.table_id = table_id;
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public String getTable_type() {
		return table_type;
	}

	public void setTable_type(String table_type) {
		this.table_type = table_type;
	}

	public int getTable_sort() {
		return table_sort;
	}

	public void setTable_sort(int table_sort) {
		this.table_sort = table_sort;
	}

	public int getTable_status() {
		return table_status;
	}

	public void setTable_status(int table_status) {
		this.table_status = table_status;
	}

	public long getRest_id() {
		return rest_id;
	}

	public void setRest_id(long rest_id) {
		this.rest_id = rest_id;
	}
}
