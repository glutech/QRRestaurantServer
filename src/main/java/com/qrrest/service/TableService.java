package com.qrrest.service;

import java.util.List;

import com.qrrest.dao.RestaurantsDao;
import com.qrrest.dao.TableTypeTermsDao;
import com.qrrest.dao.TablesDao;
import com.qrrest.model.Restaurant;
import com.qrrest.model.Table;
import com.qrrest.model.Table.TableStatusEnum;
import com.qrrest.model.TableTypeTerm;
import com.qrrest.vo.TableVo;

public class TableService {

	private TablesDao tablesDao = new TablesDao();
	private TableTypeTermsDao typesDao = new TableTypeTermsDao();
	private RestaurantsDao restsDao = new RestaurantsDao();

	public Table getTableById(int tableId) {
		return tablesDao.getTableById(tableId);
	}

	public Restaurant getRestByTable(Table table) {
		return restsDao.getRestById(table.getRestId());
	}

	public List<Table> getAllTablesByRestId(int restId) {
		return tablesDao.getTablesByRestId(restId);
	}

	public TableTypeTerm getTypeByTypeId(int typeId) {
		return typesDao.getTypeById(typeId);
	}

	public List<TableTypeTerm> getAllTypes() {
		return typesDao.getAllTypes();
	}

	public boolean modifyTableStatus(Table table) {
		return tablesDao.modifyTableStatus(table);
	}

	public boolean checkUpdateStatus(TableStatusEnum status) {
		return status == TableStatusEnum.free
				|| status == TableStatusEnum.blocked;
	}

	public boolean checkDeleteStatus(TableStatusEnum status) {
		return status == TableStatusEnum.free
				|| status == TableStatusEnum.blocked;
	}

	public boolean insertTable(Table table) {
		// 新餐桌状态设为可用
		table.setTableStatus(TableStatusEnum.free);
		// TODO: table_type_id字段为预留字段
		table.setTableTypeId(1);
		return tablesDao.insertTable(table);
	}

	public boolean updateTable(Table table) {
		// 检查餐桌状态是否可以被编辑
		if (!checkUpdateStatus(table.getTableStatus())) {
			return false;
		}
		return tablesDao.updateTable(table);
	}

	public boolean deleteTable(Table table) {
		// 检查餐桌状态是否可以被删除
		if (!checkDeleteStatus(table.getTableStatus())) {
			return false;
		}
		return tablesDao.deleteTable(table);
	}

	public Table getTableOnRequest(String tableIdString, int restId) {
		int tableId;
		try {
			tableId = Integer.parseInt(tableIdString);
		} catch (NumberFormatException e) {
			return null;
		}
		Table result = getTableById(tableId);
		if (result != null && result.getRestId() == restId) {
			return result;
		} else {
			return null;
		}
	}

	public TableVo getTableVoByTable(Table table) {
		TableVo result = new TableVo();
		result.setTable(table);
		result.setType(getTypeByTypeId(table.getTableTypeId()));
		return result;
	}

	public TableVo getTableVoByTableId(int tableId) {
		Table table = getTableById(tableId);
		return table == null ? null : getTableVoByTable(table);
	}
}
