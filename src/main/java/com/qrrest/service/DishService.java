package com.qrrest.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qrrest.dao.CategoriesDao;
import com.qrrest.dao.DishesDao;
import com.qrrest.model.Category;
import com.qrrest.model.Dish;
import com.qrrest.vo.DishVo;

public class DishService {
	DishesDao ddao;
	CategoriesDao cdao;

	public DishService() {
		ddao = new DishesDao();
		cdao = new CategoriesDao();
	}

	public Dish getDishById(long did) {
		Dish dish = ddao.getDishById(did);
		return dish;
	}

	public ArrayList<Dish> getDishesByRestId(String r_id) {
		return ddao.getDishesByRestId(Long.valueOf(r_id));
	}

	public DishVo getDishVoByDishId(long did) {
		DishVo dv = new DishVo();
		dv.setDish(ddao.getDishById(did));
		dv.setCat(cdao.getCatById(cdao.getCatIdByDishId(did)));

		return dv;

	}

	public boolean insertDish(Dish dish, long auth_rest_id) {
		// 新增菜品dish_status默认为1，dish_recommend、dish_oordered字段默认为0（数据库定义）
		dish.setDish_status(1);
		// 确保菜品分类存在、菜品分类属于当前餐厅
		Category category = cdao.getCatById(dish.getCat_id());
		if (category.getCat_id() == 0 || category.getRest_id() != auth_rest_id) {
			return false;
		}
		// 确保菜品归属于当前餐厅
		if (dish.getRest_id() != auth_rest_id) {
			return false;
		}
		//
		return ddao.insertDish(dish);
	}

	public boolean updateDish(Dish dish, long auth_rest_id) {
		// TODO: 菜品是否使用中检测
		// 确保菜品存在、菜品归属于是当前餐厅
		Dish dbDish = ddao.getDishById(dish.getDish_id());
		if (dbDish.getDish_id() == 0
				|| (dish.getRest_id() != auth_rest_id || dbDish.getRest_id() != auth_rest_id)) {
			return false;
		}
		// 确保菜品分类存在、菜品分类属于当前餐厅
		Category category = cdao.getCatById(dish.getCat_id());
		if (category.getCat_id() == 0
				|| category.getRest_id() != dish.getRest_id()) {
			return false;
		}
		//
		return ddao.modifyDish(dish);
	}

	public boolean deleteDish(long dish_id, long auth_rest_id) {
		// TODO: 菜品是否使用中检测
		Dish dbDish = ddao.getDishById(dish_id);
		// 确保菜品存在、菜品归属于是当前餐厅
		if (dbDish.getDish_id() == 0 || dbDish.getRest_id() != auth_rest_id) {
			return false;
		}
		return ddao.deleteDish(dish_id);
	}

	public Map<String, Object> queryByRestAndOptionsForPading(long rest_id,
			int page, int pageItemsCount, long cat_id, String priceOrder) {
		// cat_id == 0 表示忽略分类
		// priceOrder == null 表示忽略排序
		// 分页
		int totalCount = ddao.queryTotalCountByCategoryAndRest(rest_id, cat_id);
		int totalPages = (int) Math.ceil((float) totalCount / pageItemsCount);
		boolean isResetPage = false;
		if (page > totalPages) {
			page = 1;
			isResetPage = true;
		}
		// 查询
		List<Dish> dishes = ddao.queryDishesByCategoryAndRestOrderPriceOnLimit(
				rest_id, (page - 1) * pageItemsCount, pageItemsCount, cat_id,
				priceOrder);
		// 组装结果
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("totalCount", totalCount);
		result.put("totalPages", totalPages);
		result.put("isResetPage", isResetPage);
		result.put("currentPage", page);
		result.put("dishes", dishes);
		return result;
	}
}
