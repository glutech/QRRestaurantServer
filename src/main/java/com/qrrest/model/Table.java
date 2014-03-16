package com.qrrest.model;

public class Table {

	public static enum TableStatusEnum implements IStatusEnum {

		/**
		 * 表示餐桌处于空闲状态
		 */
		free("空闲"),
		/**
		 * 表示餐桌处于已预定状态
		 */
		booked("已预定"),
		/**
		 * 表示餐桌正在进行服务
		 */
		serving("服务中"),
		/**
		 * 表示餐桌因餐厅的原因暂时保留不进行使用
		 */
		controlled("预留"),
		/**
		 * 表示餐桌因客观原因被禁止使用
		 */
		blocked("禁用");

		private String nameZHCN;

		private TableStatusEnum(String nameZHCN) {
			this.nameZHCN = nameZHCN;
		}

		public String getNameZHCN() {
			return nameZHCN;
		}
	}

	private int tableId;
	private TableStatusEnum tableStatus;
	private String tableName;
	private int tableTypeId;
	private int tableSort;
	private int restId;

	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}

	public TableStatusEnum getTableStatus() {
		return tableStatus;
	}

	public void setTableStatus(TableStatusEnum tableStatus) {
		this.tableStatus = tableStatus;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public int getTableTypeId() {
		return tableTypeId;
	}

	public void setTableTypeId(int tableTypeId) {
		this.tableTypeId = tableTypeId;
	}

	public int getTableSort() {
		return tableSort;
	}

	public void setTableSort(int tableSort) {
		this.tableSort = tableSort;
	}

	public int getRestId() {
		return restId;
	}

	public void setRestId(int restId) {
		this.restId = restId;
	}

}
