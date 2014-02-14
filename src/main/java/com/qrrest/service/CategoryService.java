package com.qrrest.service;

import java.util.List;

import com.qrrest.dao.CategoriesDao;
import com.qrrest.model.Category;

public class CategoryService {
	CategoriesDao cdao;

	public CategoryService() {
		cdao = new CategoriesDao();
	}

	public List<Category> getCatsByRestId(long rest_id) {
		return cdao.getCatsByRestId(rest_id);
	}

	public Category getCatByDishId(long did) {
		long cat_id = cdao.getCatIdByDishId(did);
		return (cdao.getCatById(cat_id));
	}

	public Category getCatByCatId(long cat_id) {
		return cdao.getCatById(cat_id);
	}

	public boolean insertCategory(Category category, long rest_id) {
		if (category.getRest_id() != rest_id) {
			return false;
		}
		return cdao.insertCat(category.getCat_name(), category.getRest_id());
	}

	public boolean updateCategory(Category category) {
		Category dbCat = cdao.getCatById(category.getCat_id());
		if (dbCat.getCat_id() != category.getCat_id()
				|| dbCat.getRest_id() != category.getRest_id()) {
			return false;
		}
		return cdao.modifyCat(category.getCat_name(), category.getCat_id());
	}

	public boolean deleteCategory(long cat_id, long rest_id) {
		Category dbCat = cdao.getCatById(cat_id);
		if (dbCat.getCat_id() != cat_id || dbCat.getRest_id() != rest_id) {
			return false;
		}
		return cdao.deleteCat(cat_id);
	}
}
