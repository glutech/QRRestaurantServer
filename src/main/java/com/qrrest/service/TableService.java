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
	
	public TableService(){
		tdao = new TablesDao();
		rdao = new RestaurantsDao();
	}
	
	public Table getTableById(String tid){
		Table table = tdao.getTableById(Long.valueOf(tid));
		
		return table;
	}
	
	//检查餐桌状态，餐桌为0不可用，为1可用
	public int checkTableStatus(String t_id){
		int result = 1;
		result = tdao.checkTableStatus(Long.valueOf(t_id));
		
		return result;
	}
	
	public Restaurant getRestByTableId(String t_id){
		Restaurant rest = new Restaurant();
		long rid = tdao.getRestIdByTableId(Long.valueOf(t_id));
		rest = rdao.getRestById(rid);
		
		return rest;
	}
	
	public List<Table> getAllTablesByRestId(String r_id){
		List<Table> list = new ArrayList<Table>();
		list = tdao.getTablesByRestId(Long.valueOf(r_id));
		return list;
	}
}
