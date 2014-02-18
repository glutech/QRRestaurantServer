package com.qrrest.service;

import java.util.ArrayList;
import java.util.List;

import com.qrrest.dao.RestaurantsDao;
import com.qrrest.dao.TablesDao;
import com.qrrest.model.Restaurant;
import com.qrrest.model.Table;

public class TableService {
	TablesDao tdao;
	RestaurantsDao rdao;

	public TableService() {
		tdao = new TablesDao();
		rdao = new RestaurantsDao();
	}

	public Table getTableById(String tid) {
		Table table = tdao.getTableById(Long.valueOf(tid));

		return table;
	}

	// 检查餐桌状态，餐桌为0不可用，为1可用
	public int checkTableStatus(String t_id) {
		int result = 1;
		result = tdao.checkTableStatus(Long.valueOf(t_id));

		return result;
	}

	public Restaurant getRestByTableId(String t_id) {
		Restaurant rest = new Restaurant();
		long rid = tdao.getRestIdByTableId(Long.valueOf(t_id));
		rest = rdao.getRestById(rid);

		return rest;
	}

	public List<Table> getAllTablesByRestId(String r_id) {
		List<Table> list = new ArrayList<Table>();
		list = tdao.getTablesByRestId(Long.valueOf(r_id));
		return list;
	}

	public boolean insertTable(Table table, long auth_rest_id) {
		// 新餐桌状态设为1可用
		table.setTable_status(1);
		if (table.getRest_id() != auth_rest_id) {
			return false;
		}
		return tdao.insertTable(table);
	}

	public boolean updateTable(Table table, long auth_rest_id) {
		Table dbTable = tdao.getTableById(table.getTable_id());
		// 餐厅与餐桌不匹配、餐桌未处在空闲状态时，不允许修改
		if (dbTable.getTable_id() == 0
				|| (table.getRest_id() != auth_rest_id || dbTable.getRest_id() != auth_rest_id)
				|| dbTable.getTable_status() != 1) {
			return false;
		}
		return tdao.modifyTable(table);
	}

	public boolean deleteTable(long t_id, long auth_rest_id) {
		Table table = tdao.getTableById(t_id);
		// 不存在餐桌、餐桌与餐厅不匹配、餐桌未处在空闲状态时，不允许删除
		if (table.getTable_id() == 0 || table.getRest_id() != auth_rest_id
				|| table.getTable_status() != 1) {
			return false;
		}
		return tdao.deleteTable(t_id);
	}
}
