package com.qrrest.service;

import java.util.ArrayList;
import java.util.List;

import com.qrrest.dao.RestaurantsDao;
import com.qrrest.model.Restaurant;

public class RestaurantService {
	RestaurantsDao rdao;
	public RestaurantService(){
		rdao = new RestaurantsDao();
	}
	
	public List<Restaurant> listAllRests(){
		List<Restaurant> list = new ArrayList<Restaurant>();
		list = rdao.getAllRests();
		
		return list;
	}
	
	public Restaurant getRestById(String r_id){
		Restaurant rest;
		rest = rdao.getRestById(Long.valueOf(r_id));
		
		return rest;
	}
	
	public Restaurant getRestById(long rest_id) {
		return rdao.getRestById(rest_id);
	}
	
	public List<Restaurant> getRestsByName(String keyword){
		List<Restaurant> list;
		list = rdao.getRestsByName(keyword);
		
		return list;
	}
	
	public boolean updateRestaurant(Restaurant rest, long auth_rest_id) {
		// 确保餐厅是当前管理员所管理的
		if(rest.getRest_id() != auth_rest_id) {
			return false;
		}
		return rdao.modifyRest(rest);
	}
	
	public String getRestName(long rest_id) {
		return rdao.getRestName(rest_id);
	}
}
