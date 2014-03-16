package com.qrrest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.qrrest.model.RestDishCategoryTerm;
import com.qrrest.vo.RestDishCategoryTermVo;

public class RestDishCategoriyTermsDao extends BaseDao<RestDishCategoryTerm> {

	@Override
	protected RestDishCategoryTerm parseRS(ResultSet rs) throws SQLException {
		RestDishCategoryTerm model = new RestDishCategoryTerm();
		model.setRestDishCatId(rs.getInt("rest_dish_cat_id"));
		model.setRestDishCatName(rs.getString("rest_dish_cat_name"));
		model.setRestId(rs.getInt("rest_id"));
		return model;
	}

	public RestDishCategoryTerm getCatById(int catId) {
		String sql = "select * from rest_dish_category_terms where rest_dish_cat_id = ?";
		Object[] params = { catId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToModel(rs);
	}

	public List<RestDishCategoryTerm> getCatsByRestId(int restId) {
		String sql = "select * from rest_dish_category_terms where rest_id = ?";
		Object[] params = { restId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToList(rs);
	}

	public boolean insertCat(RestDishCategoryTerm cat) {
		String sql = "insert into rest_dish_category_terms(rest_dish_cat_name, rest_id) values(?, ?)";
		Object[] params = { cat.getRestDishCatName(), cat.getRestId() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public boolean updateCat(RestDishCategoryTerm cat) {
		String sql = "update rest_dish_category_terms set rest_dish_cat_name = ?, rest_id = ? where rest_dish_cat_id = ?";
		Object[] params = { cat.getRestDishCatName(), cat.getRestId(),
				cat.getRestDishCatId() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public boolean deleteCat(RestDishCategoryTerm cat) {
		String sql = "delete from rest_dish_category_terms where rest_dish_cat_id = ?";
		Object[] params = { cat.getRestDishCatId() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public List<RestDishCategoryTermVo> getCatVosByRestId(int restId) {
		String sql = "select tmp.rest_dish_cat_id, terms.rest_dish_cat_name, tmp.rest_dish_cat_num, terms.rest_id from ( "
				+ "select rest_dish_cat_id, count(*) as rest_dish_cat_num from dishes where rest_dish_cat_id in ( "
				+ "select rest_dish_cat_id from rest_dish_category_terms where rest_id = ? "
				+ ") "
				+ ") as tmp left outer join rest_dish_category_terms as terms on tmp.rest_dish_cat_id = terms.rest_dish_cat_id "
				+ " order by rest_dish_cat_num desc";
		Object[] params = { restId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		List<RestDishCategoryTermVo> result = new LinkedList<RestDishCategoryTermVo>();
		try {
			while (rs.next()) {
				RestDishCategoryTermVo vo = new RestDishCategoryTermVo();
				vo.setCat(parseRS(rs));
				vo.setUsedNum(rs.getInt("rest_dish_cat_num"));
				result.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
