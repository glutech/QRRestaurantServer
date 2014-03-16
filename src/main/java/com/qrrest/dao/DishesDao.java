package com.qrrest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.qrrest.model.Dish;
import com.qrrest.model.Dish.DishStatusEnum;
import com.qrrest.model.Restaurant;
import com.qrrest.vo.FullOrderItemVo;

public class DishesDao extends BaseDao<Dish> {

	@Override
	protected Dish parseRS(ResultSet rs) throws SQLException {
		Dish model = new Dish();
		model.setDishId((rs.getInt("dish_id")));
		model.setDishStatus(DishStatusEnum.valueOf(rs.getString("dish_status")));
		model.setDishName(rs.getString("dish_name"));
		model.setDishPrice(rs.getDouble("dish_price"));
		model.setDishPic(rs.getString("dish_pic"));
		model.setDishDesc(rs.getString("dish_desc"));
		model.setRestDishCatId(rs.getInt("rest_dish_cat_id"));
		return model;
	}

	public Dish getDishById(int dishId) {
		String sql = "select * from dishes where dish_id = ?";
		Object[] params = { dishId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToModel(rs);
	}

	public List<Dish> getDishesByCatId(int catId) {
		String sql = "select * from dishes where rest_dish_cat_id = ?";
		Object[] params = { catId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToList(rs);
	}

	public List<Dish> getDishesByRestId(Restaurant rest) {
		String sql = "select * from dishes where rest_dish_cat_id in (select rest_dish_cat_id from rest_dish_category_terms where rest_id = ?)";
		Object[] params = { rest.getRestId() };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToList(rs);
	}

	public int getDishesCountByRestId(Restaurant rest) {
		String sql = "select count(dish_id) from dishes where rest_dish_cat_id in (select rest_dish_cat_id from rest_dish_catgory_terms where rest_id = ?)";
		Object[] params = { rest.getRestId() };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseInt(rs);
	}

	public boolean modifyDishStatus(Dish dish) {
		String sql = "update dishes set dish_status = ? where dish_id = ?";
		Object[] params = { dish.getDishStatus().toString(), dish.getDishId() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public boolean insertDish(Dish dish) {
		String sql = "insert into dishes(dish_status, dish_name, dish_price, dish_pic, dish_desc, rest_dish_cat_id) values(?, ?, ?, ?, ?, ?)";
		Object[] params = { dish.getDishStatus().toString(),
				dish.getDishName(), dish.getDishPrice(), dish.getDishPic(),
				dish.getDishDesc(), dish.getRestDishCatId() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public Dish insertAndGetDish(Dish dish) {
		if (insertDish(dish)) {
			return getDishById(getLastInsertId());
		} else {
			return null;
		}
	}

	public boolean updateDish(Dish dish) {
		String sql = "update dishes set dish_status = ?, dish_name = ?, dish_price = ?, dish_pic = ?, dish_desc = ?, rest_dish_cat_id = ? where dish_id = ?";
		Object[] params = { dish.getDishStatus().toString(),
				dish.getDishName(), dish.getDishPrice(), dish.getDishPic(),
				dish.getDishDesc(), dish.getRestDishCatId(), dish.getDishId() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public boolean deleteDish(Dish dish) {
		String sql = "delete from dishes where dish_id = ?";
		Object[] params = { dish.getDishId() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public List<FullOrderItemVo> getFullBookDishRelationship(int bookId) {
		String sql = "select * from ( "
				+ "select dish_id, dish_count from book_dishes_temp_map where book_id = ? "
				+ ") as ta left outer join dishes as tb on ta.dish_id = tb.dish_id";
		Object[] params = { bookId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		List<FullOrderItemVo> r = new LinkedList<FullOrderItemVo>();
		try {
			while (rs.next()) {
				Dish dish = parseRS(rs);
				int count = rs.getInt("dish_count");
				FullOrderItemVo vo = new FullOrderItemVo();
				vo.setDish(dish);
				vo.setCount(count);
				r.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return r;
	}

	public List<FullOrderItemVo> getFullOrderDishRelationship(int orderId) {
		String sql = "select * from ( "
				+ "select dish_id, dish_count from order_active_dishes_map where order_id = ? "
				+ ") as ta left outer join dishes as tb on ta.dish_id = tb.dish_id";
		Object[] params = { orderId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		List<FullOrderItemVo> r = new LinkedList<FullOrderItemVo>();
		try {
			while (rs.next()) {
				Dish dish = parseRS(rs);
				int count = rs.getInt("dish_count");
				FullOrderItemVo vo = new FullOrderItemVo();
				vo.setDish(dish);
				vo.setCount(count);
				r.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return r;
	}

	/**
	 * 为后台管理菜品列表定制的搜索查询
	 * 
	 * @author wu.kui2@gmail.com
	 * @param status
	 *            为null时表示忽略
	 * @param catId
	 *            为null时表示忽略
	 * @param tagId
	 *            为null时表示忽略
	 * @param priceOrder
	 *            为null时表示忽略
	 * @param begin
	 *            配合分页的起始位置
	 * @param offset
	 *            配合分页的偏移位置
	 * @return MAP
	 */
	public Object queryOnAdminDishList(DishStatusEnum status, Integer catId,
			Integer tagId, String priceOrder, int begin, int offset) {
		String sql;
		Object[] params;
		// 组装查询条件
		{
			// 中转结构
			List<String> sqlList = new LinkedList<String>();
			List<Object> paramList = new ArrayList<Object>(3);
			// 字段子句，包含统计
			sqlList.add("select SQL_CALC_FOUND_ROWS * from dishes");
			// 查询子句
			{
				List<String> condition = new ArrayList<String>(3);
				if (status != null) {
					condition.add(" dish_status = ? ");
					paramList.add(status.toString());
				}
				if (catId != null) {
					condition.add(" rest_dish_cat_id = ? ");
					paramList.add(catId.intValue());
				}
				if (tagId != null) {
					condition
							.add(" dish_id in ( select dish_id from dish_tags_map where dish_tag_id = ? ) ");
					paramList.add(tagId.intValue());
				}
				if (condition.size() != 0) {
					sqlList.add(" where ");
					for (String c : condition) {
						sqlList.add(c);
						sqlList.add(" and ");
					}
					sqlList.remove(sqlList.size() - 1);
				}
			}
			// 排序子句
			if (priceOrder != null) {
				priceOrder = priceOrder.toLowerCase();
				if (priceOrder.equals("asc") || priceOrder.equals("desc")) {
					sqlList.add(" order by dish_price ");
					sqlList.add(priceOrder);
				}
			}
			// 区间子句
			sqlList.add(" limit " + begin + ", " + offset);
			// 拼装结果
			StringBuilder sqlResult = new StringBuilder();
			for (String tmp : sqlList) {
				sqlResult.append(tmp);
			}
			sql = sqlResult.toString();
			params = paramList.toArray();
		}
		// 查询
		ResultSet rsMain = getSqlExecution().execSqlWithRS(sql, params);
		List<Dish> dishes = parseRsToList(rsMain);
		ResultSet rsNum = getSqlExecution().execSqlWithRS(
				"SELECT FOUND_ROWS()", new Object[] {});
		int num = parseInt(rsNum);
		// 构造返回结果
		return new Object[] { dishes, num };
	}
}
