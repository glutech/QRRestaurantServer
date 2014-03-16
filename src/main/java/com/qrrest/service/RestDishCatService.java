package com.qrrest.service;

import java.util.List;

import com.qrrest.dao.RestDishCategoriyTermsDao;
import com.qrrest.model.RestDishCategoryTerm;
import com.qrrest.vo.RestDishCategoryTermVo;

public class RestDishCatService {

	private RestDishCategoriyTermsDao catsDao = new RestDishCategoriyTermsDao();

	public RestDishCategoryTerm getCatByCatId(int catId) {
		return catsDao.getCatById(catId);
	}

	public List<RestDishCategoryTerm> getCatsByRestId(int restId) {
		return catsDao.getCatsByRestId(restId);
	}

	public boolean insertCat(RestDishCategoryTerm cat) {
		return catsDao.insertCat(cat);
	}

	public boolean updateCat(RestDishCategoryTerm cat) {
		return catsDao.updateCat(cat);
	}

	public boolean deleteCat(RestDishCategoryTerm cat) {
		return catsDao.deleteCat(cat);
	}

	public List<RestDishCategoryTermVo> getCatVosByRestId(int restId) {
		return catsDao.getCatVosByRestId(restId);
	}
}
