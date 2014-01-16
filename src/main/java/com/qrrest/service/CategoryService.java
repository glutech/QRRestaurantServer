package com.qrrest.service;

import java.util.List;

import com.qrrest.dao.CategoriesDao;
import com.qrrest.model.Category;

public class CategoryService {
	CategoriesDao cdao;
	
	public CategoryService(){
		cdao = new CategoriesDao();
	}
	
	public List<Category> getCatsByRestId(long rest_id){
		return cdao.getCatsByRestId(rest_id);
	}
	
	public Category getCatByDishId(long did){
		long cat_id = cdao.getCatIdByDishId(did);
		return (cdao.getCatById(cat_id));
	}
}
