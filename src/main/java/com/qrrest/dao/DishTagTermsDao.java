package com.qrrest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.qrrest.model.DishTagTerm;
import com.qrrest.vo.DishTagTermVo;

public class DishTagTermsDao extends BaseDao<DishTagTerm> {

	@Override
	protected DishTagTerm parseRS(ResultSet rs) throws SQLException {
		DishTagTerm model = new DishTagTerm();
		model.setDishTagId(rs.getInt("dish_tag_id"));
		model.setDishTagName(rs.getString("dish_tag_name"));
		return model;
	}

	public DishTagTerm getTagById(int tagId) {
		String sql = "select * from dish_tags_terms where dish_tag_id = ?";
		Object[] params = { tagId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToModel(rs);
	}

	public DishTagTerm getTagByName(String tagName) {
		String sql = "select * from dish_tags_terms where dish_tag_name";
		Object[] params = { tagName };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToModel(rs);
	}

	public boolean insertTag(DishTagTerm tag) {
		String sql = "insert int dish_tag_terms(dish_tag_name) values(?)";
		Object[] params = { tag.getDishTagName() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public boolean updateTag(DishTagTerm tag) {
		String sql = "update dish_tag_terms set dish_tag_name = ? where dish_tag_id = ?";
		Object[] params = { tag.getDishTagName(), tag.getDishTagId() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public boolean deleteTag(DishTagTerm tag) {
		String sql = "delete from dish_tag_terms where dish_tag_id = ?";
		Object[] params = { tag.getDishTagId() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public List<DishTagTerm> insertOrGetTagsByNames(String[] tagNames) {
		{
			// 检查是否已存在项
			StringBuilder sql = new StringBuilder();
			List<Object> params = new ArrayList<Object>(tagNames.length * 2);
			for (String tagName : tagNames) {
				sql.append("insert into dish_tag_terms(dish_tag_name) select * from (select ?) as tmp where not exists (select dish_tag_id from dish_tag_terms where dish_tag_name = ?);");
				params.add(tagName);
				params.add(tagName);
			}
			getSqlExecution()
					.execSqlWithoutRS(sql.toString(), params.toArray());
		}
		{
			// 获取项
			StringBuilder sql = new StringBuilder();
			sql.append("select * from dish_tag_terms where dish_tag_name in (");
			if (tagNames.length > 0) {
				for (int i = 0; i < tagNames.length; i++) {
					sql.append("?, ");
				}
				sql.setCharAt(sql.length() - 2, ' '); // 去除最后一个逗号
			}
			sql.append(")");
			ResultSet rs = getSqlExecution().execSqlWithRS(sql.toString(),
					tagNames);
			return parseRsToList(rs);
		}
	}

	public void fillRelationship(int dishId, List<DishTagTerm> tags) {
		// 清除原有关系
		getSqlExecution().execSqlWithoutRS(
				"delete from dish_tags_map where dish_id = ?",
				new Object[] { dishId });
		// 填充新关系
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>(tags.size() * 2);
		for (DishTagTerm tag : tags) {
			sql.append("insert into dish_tags_map(dish_id, dish_tag_id) values(?, ?);");
			params.add(dishId);
			params.add(tag.getDishTagId());
		}
		getSqlExecution().execSqlWithoutRS(sql.toString(), params.toArray());
	}

	public List<DishTagTerm> getTagsByDishId(int dishId) {
		String sql = "select * from dish_tag_terms where dish_tag_id in ("
				+ "select dish_tag_id from dish_tags_map where dish_id = ?"
				+ ")";
		Object[] params = { dishId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToList(rs);
	}

	public List<DishTagTerm> getTagsByRestId(int restId) {
		String sql = "select * from dish_tag_terms where dish_tag_id in ("
				+ "select dish_tag_id from dish_tags_map where dish_id in("
				+ "select dish_id from dishes where rest_dish_cat_id in ("
				+ "select rest_dish_cat_id from rest_dish_category_terms where rest_id = ?"
				+ ")" + ")" + ")";
		Object[] params = { restId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToList(rs);
	}

	public List<DishTagTermVo> getTagVosByRestId(int restId) {
		String sql = "select tmp.dish_tag_id, terms.dish_tag_name, tmp.dish_tag_num from ( "
				+ "select dish_tag_id, count(*) as dish_tag_num from dish_tags_map where dish_id in ( "
				+ "select dish_id from dishes where rest_dish_cat_id in ( "
				+ "select rest_dish_cat_id from rest_dish_category_terms where rest_id = ? "
				+ ") "
				+ ") group by dish_tag_id "
				+ ") as tmp left outer join dish_tag_terms as terms on tmp.dish_tag_id = terms.dish_tag_id "
				+ "order by dish_tag_num desc ";
		Object[] params = { restId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		List<DishTagTermVo> result = new LinkedList<DishTagTermVo>();
		try {
			while (rs.next()) {
				DishTagTermVo vo = new DishTagTermVo();
				vo.setTag(parseRsToModel(rs));
				vo.setRestUsedNum(rs.getInt("dish_tag_num"));
				result.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
