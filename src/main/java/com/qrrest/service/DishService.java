package com.qrrest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qrrest.dao.DishesDao;
import com.qrrest.model.Dish;
import com.qrrest.model.DishTagTerm;
import com.qrrest.model.RestDishCategoryTerm;
import com.qrrest.model.Restaurant;
import com.qrrest.model.Dish.DishStatusEnum;
import com.qrrest.vo.DishVo;
import com.qrrest.vo.DishesVo;
import com.qrrest.vo.FullOrderItemVo;

public class DishService {

	private DishesDao dishesDao = new DishesDao();

	public Dish getDishById(int dishId) {
		return dishesDao.getDishById(dishId);
	}

	public List<Dish> getDishesByRest(Restaurant rest) {
		return dishesDao.getDishesByRestId(rest);
	}

	public boolean modifyDishStatus(Dish dish) {
		return dishesDao.modifyDishStatus(dish);
	}

	public boolean checkUpdateStatus(DishStatusEnum status) {
		return status == DishStatusEnum.normal
				|| status == DishStatusEnum.blocked;
	}

	public boolean checkDeleteStatus(DishStatusEnum status) {
		return status == DishStatusEnum.normal
				|| status == DishStatusEnum.blocked;
	}

	public boolean insertDish(Dish dish) {
		// 新增菜品dish_status设置为normal
		dish.setDishStatus(DishStatusEnum.normal);
		return dishesDao.insertDish(dish);
	}

	public Dish insertAndGetDish(Dish dish) {
		// 新增菜品dish_status设置为normal
		dish.setDishStatus(DishStatusEnum.normal);
		return dishesDao.insertAndGetDish(dish);
	}

	public boolean updateDish(Dish dish) {
		// 检查菜品状态是否可以被编辑
		if (!checkUpdateStatus(dish.getDishStatus())) {
			return false;
		}
		return dishesDao.updateDish(dish);
	}

	public boolean deleteDish(Dish dish) {
		// 检查菜品状态是否可以被删除
		if (!checkDeleteStatus(dish.getDishStatus())) {
			return false;
		}
		return dishesDao.deleteDish(dish);
	}

	/*
	 * query
	 */

	public Dish getDishOnRequest(String dishIdString, int authRestId) {
		int dishId;
		try {
			dishId = Integer.parseInt(dishIdString);
		} catch (NumberFormatException e) {
			return null;
		}
		Dish dish = getDishById(dishId);
		if (dish != null
				&& new RestDishCatService().getCatByCatId(
						dish.getRestDishCatId()).getRestId() == authRestId) {
			return dish;
		} else {
			return null;
		}
	}
	
	public List<FullOrderItemVo> getFullBookDishRelationship(int bookId) {
		return dishesDao.getFullBookDishRelationship(bookId);
	}
	
	public List<FullOrderItemVo> getFullOrderDishRelationship(int orderId) {
		return dishesDao.getFullOrderDishRelationship(orderId);
	}

	/**
	 * 为后台管理菜品列表定制的搜索查询，参数有效性已在前台验证
	 * 
	 * @author wu.kui2@gmail.com
	 */
	public Map<String, Object> queryOnAdminDishList(DishStatusEnum status,
			Integer catId, Integer tagId, String priceOrder, int page,
			int pageItemsCount) {
		Object[] query = (Object[]) dishesDao.queryOnAdminDishList(status,
				catId, tagId, priceOrder, (page - 1) * pageItemsCount,
				pageItemsCount);
		@SuppressWarnings("unchecked")
		List<Dish> dishes = (List<Dish>) query[0];
		int queryNum = (Integer) query[1];
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("totalNum", queryNum);
		result.put("totalPage",
				(int) Math.ceil((float) queryNum / pageItemsCount));
		result.put("dishes", dishes);
		return result;
	}

	/*
	 * vo
	 */

	public DishVo getDishVoByDishId(int dishId) {
		Dish dish = getDishById(dishId);
		List<DishTagTerm> tags = new DishTagService().getTagsByDishId(dishId);
		RestDishCategoryTerm cat = new RestDishCatService().getCatByCatId(dish
				.getRestDishCatId());
		DishVo result = new DishVo();
		result.setDish(dish);
		result.setTags(tags);
		result.setCat(cat);
		return result;
	}

	public DishesVo getDishesVoByRestId(int restId) {
		Restaurant rest = new RestaurantService().getRestById(restId);
		if (rest == null)
			return null;
		List<Dish> dishes = getDishesByRest(rest);
		List<RestDishCategoryTerm> cats = new RestDishCatService()
				.getCatsByRestId(restId);
		DishesVo result = new DishesVo();
		result.setRest(rest);
		result.setDishList(dishes);
		result.setCatList(cats);
		return result;
	}
}
