package com.qrrest.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qrrest.dao.RestTypeTermsDao;
import com.qrrest.dao.RestaurantsDao;
import com.qrrest.model.RestTypeTerm;
import com.qrrest.model.Restaurant;
import com.qrrest.vo.RestaurantVo;

public class RestaurantService {

	private RestaurantsDao restsDao = new RestaurantsDao();
	private RestTypeTermsDao typesDao = new RestTypeTermsDao();

	public List<Restaurant> getAllRests() {
		return restsDao.getAllRests();
	}

	public Restaurant getRestById(int restId) {
		return restsDao.getRestById(restId);
	}

	public String getRestNameById(int restId) {
		return restsDao.getRestNameById(restId);
	}

	public List<Restaurant> getRestsByName(String keyword) {
		return restsDao.getRestsByName(keyword);
	}

	public boolean updateRestaurant(Restaurant rest, int authRestId) {
		// 确保餐厅是当前管理员所管理的
		if (rest.getRestId() != authRestId) {
			return false;
		}
		return restsDao.updateRest(rest);
	}

	public RestTypeTerm getTypeByTypeId(int typeId) {
		return typesDao.getTypeById(typeId);
	}

	public List<RestTypeTerm> getAllTypes() {
		return typesDao.getAllTypes();
	}

	public Map<Integer, RestTypeTerm> getAllTypesToMap() {
		Map<Integer, RestTypeTerm> result = new HashMap<Integer, RestTypeTerm>();
		for (RestTypeTerm type : getAllTypes()) {
			result.put(type.getRestTypeId(), type);
		}
		return result;
	}

	/*
	 * vo
	 */

	public RestaurantVo getRestVoByRestId(int restId) {
		RestaurantVo result = new RestaurantVo();
		result.setRest(getRestById(restId));
		result.setType(getTypeByTypeId(result.getRest().getRestTypeId()));
		return result;
	}

	public List<RestaurantVo> getAllRestVos() {
		List<Restaurant> rests = getAllRests();
		Map<Integer, RestTypeTerm> types = getAllTypesToMap();
		List<RestaurantVo> result = new ArrayList<RestaurantVo>();
		for (Restaurant rest : rests) {
			RestaurantVo vo = new RestaurantVo();
			vo.setRest(rest);
			vo.setType(types.get(rest.getRestTypeId()));
			result.add(vo);
		}
		return result;
	}

}
