package com.qrrest.service;

import java.util.ArrayList;

import com.qrrest.dao.CategoriesDao;
import com.qrrest.dao.DishesDao;
import com.qrrest.model.Dish;
import com.qrrest.vo.DishVo;

public class DishService {
	DishesDao ddao;
	CategoriesDao cdao;
	
	public DishService(){
		ddao = new DishesDao();
		cdao = new CategoriesDao();
	}
	
	public Dish getDishById(long did){
		Dish dish = ddao.getDishById(did);
		return dish;
	}
	
	public ArrayList<Dish> getDishesByRestId(String r_id){
		return ddao.getDishesByRestId(Long.valueOf(r_id));
	}
	
	public DishVo getDishVoByDishId(long did){
		DishVo dv = new DishVo();
		dv.setDish(ddao.getDishById(did));
		dv.setCat(cdao.getCatById(cdao.getCatIdByDishId(did)));
		
		return dv;
		
	}
}
