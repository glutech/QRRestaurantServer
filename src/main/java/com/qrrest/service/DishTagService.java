package com.qrrest.service;

import java.util.ArrayList;
import java.util.List;

import com.qrrest.dao.DishTagTermsDao;
import com.qrrest.model.DishTagTerm;
import com.qrrest.vo.DishTagTermVo;

public class DishTagService {

	private DishTagTermsDao tagsDao = new DishTagTermsDao();

	public DishTagTerm getTagByTagId(int tagId) {
		return tagsDao.getTagById(tagId);
	}

	public List<DishTagTerm> getTagsByDishId(int dishId) {
		return tagsDao.getTagsByDishId(dishId);
	}

	public String[] getTagNamesByDish(int dishId) {
		List<DishTagTerm> tags = getTagsByDishId(dishId);
		List<String> result = new ArrayList<String>(tags.size());
		for (DishTagTerm tag : tags) {
			result.add(tag.getDishTagName());
		}
		return result.toArray(new String[0]);
	}

	public List<DishTagTerm> getTagsByRestId(int restId) {
		return tagsDao.getTagsByRestId(restId);
	}

	public void updateDishTags(int dishId, String[] tags) {
		tagsDao.fillRelationship(dishId, tagsDao.insertOrGetTagsByNames(tags));
	}

	public List<DishTagTermVo> getTagVosByRestId(int restId) {
		return tagsDao.getTagVosByRestId(restId);
	}

}
