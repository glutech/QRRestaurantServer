package com.qrrest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qrrest.model.Dish;

/**
 * @author glu 1.根据类别取菜品（15条） 2.根据id取菜品 3.新增菜品 4.修改菜品 5.删除菜品 6.获取推荐菜品列表
 *         7.获取排行榜菜品列表（前10）
 */

public class DishesDao {
	private SQLExecution sqlE = new SQLExecution();

	/**
	 * 通过菜品id获取菜品详细信息
	 * 
	 * @param username
	 * @return student entity
	 */
	public Dish getDishById(long Id) {
		Dish dish = new Dish();
		String sql = "select * from dishes where dish_id=?";
		Object[] params = { Id };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				dish.setDish_id(rs.getLong("dish_id"));
				dish.setDish_name(rs.getString("dish_name"));
				dish.setDish_desc(rs.getString("dish_desc"));
				dish.setDish_pic(rs.getString("dish_pic"));
				dish.setDish_price(rs.getDouble("dish_price"));
				dish.setDish_tag(rs.getString("dish_tag"));
				dish.setDish_status(rs.getInt("dish_status"));
				dish.setDish_recommend(rs.getInt("dish_recommend"));
				dish.setDish_ordered(rs.getInt("dish_ordered"));
				dish.setCat_id(rs.getLong("cat_id"));
				dish.setRest_id(rs.getLong("rest_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dish;
	}

	/**
	 * 获取对应餐馆和类别之下的所有菜品
	 * 
	 * @param gradeId
	 * @param majorId
	 * @return
	 */
	public List<Dish> getDishesByCategory(long catid, long restid) {
		List<Dish> list = new ArrayList<Dish>();
		String sql = "select * from dishes where cat_id = ? and rest_id = ?";
		Object[] params = { catid, restid };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				Dish dish = new Dish();

				dish.setDish_id(rs.getLong("dish_id"));
				dish.setDish_name(rs.getString("dish_name"));
				dish.setDish_desc(rs.getString("dish_desc"));
				dish.setDish_pic(rs.getString("dish_pic"));
				dish.setDish_price(rs.getDouble("dish_price"));
				dish.setDish_tag(rs.getString("dish_tag"));
				dish.setDish_status(rs.getInt("dish_status"));
				dish.setDish_recommend(rs.getInt("dish_recommend"));
				dish.setDish_ordered(rs.getInt("dish_ordered"));
				dish.setCat_id(rs.getLong("cat_id"));
				dish.setRest_id(rs.getLong("rest_id"));

				list.add(dish);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<Dish> getDishesByRestId(long restid) {
		ArrayList<Dish> list = new ArrayList<Dish>();
		String sql = "select * from dishes where rest_id = ?";
		Object[] params = { restid };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				Dish dish = new Dish();

				dish.setDish_id(rs.getLong("dish_id"));
				dish.setDish_name(rs.getString("dish_name"));
				dish.setDish_desc(rs.getString("dish_desc"));
				dish.setDish_pic(rs.getString("dish_pic"));
				dish.setDish_price(rs.getDouble("dish_price"));
				dish.setDish_tag(rs.getString("dish_tag"));
				dish.setDish_status(rs.getInt("dish_status"));
				dish.setDish_recommend(rs.getInt("dish_recommend"));
				dish.setDish_ordered(rs.getInt("dish_ordered"));
				dish.setCat_id(rs.getLong("cat_id"));
				dish.setRest_id(rs.getLong("rest_id"));

				list.add(dish);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 获取菜品数，用于分页
	 * 
	 * @return
	 */
	public int getDishesCount() {
		int i = -1;
		String sql = "select count(*) from dishes";
		Object[] params = {};
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				i = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * 新增菜品
	 */
	public boolean insertDish(Dish dish) {
		boolean flag = false;
		String sql = "insert into dishes (dish_name, dish_desc, dish_pic, dish_price, dish_tag, dish_status, dish_recommend, dish_ordered, cat_id, rest_id) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Object[] params = { dish.getDish_name(), dish.getDish_desc(),
				dish.getDish_pic(), dish.getDish_price(), dish.getDish_tag(),
				dish.getDish_status(), dish.getDish_recommend(),
				dish.getDish_ordered(), dish.getCat_id(), dish.getRest_id() };
		flag = sqlE.execSqlWithoutRS(sql, params);
		return flag;
	}

	/**
	 * 根据菜品id删除菜品
	 * 
	 * @param stuId
	 * @return
	 */
	public boolean deleteDish(long dishid) {
		boolean flag = false;
		String sql = "delete from dishes where dish_id = ?";
		Object[] params = { dishid };
		if (sqlE.execSqlWithoutRS(sql, params)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 根据菜品id修改菜品status字段
	 * 
	 * @param username
	 * @param staus
	 * @return
	 */
	public boolean modifyDishStatus(long dishid, int status) {
		boolean flag = false;
		String sql = "update dishes set dish_status = ? where dish_id = ?";
		Object[] params = { status, dishid };
		flag = sqlE.execSqlWithoutRS(sql, params);

		return flag;
	}

	/**
	 * 修改菜品信息
	 * 
	 * @param stu
	 * @return
	 */
	public boolean modifyDish(Dish dish) {
		boolean flag = false;
		String sql = "update dishes set dish_name = ?, dish_desc = ?, dish_pic = ?, "
				+ "dish_price = ?, dish_tag = ?, dish_status = ?, "
				+ "dish_recommend = ?, dish_ordered = ?, "
				+ "cat_id = ?, rest_id = ? " + "where dish_id = ?";
		Object[] params = { dish.getDish_name(), dish.getDish_desc(),
				dish.getDish_pic(), dish.getDish_price(), dish.getDish_tag(),
				dish.getDish_status(), dish.getDish_recommend(),
				dish.getDish_ordered(), dish.getCat_id(), dish.getRest_id(),
				dish.getDish_id() };
		flag = sqlE.execSqlWithoutRS(sql, params);

		return flag;
	}

	/**
	 * 获取给定餐馆前20推荐值的菜品
	 */
	public List<Dish> getRecommendDishes(long restid) {
		List<Dish> list = new ArrayList<Dish>();
		String sql = "SELECT TOP 20 * FROM dishes ORDER BY dish_recommend DESC WHERE rest_id = ?";
		Object[] params = { restid };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				Dish dish = new Dish();

				dish.setDish_id(rs.getLong("dish_id"));
				dish.setDish_name(rs.getString("dish_name"));
				dish.setDish_desc(rs.getString("dish_desc"));
				dish.setDish_pic(rs.getString("dish_pic"));
				dish.setDish_price(rs.getDouble("dish_price"));
				dish.setDish_tag(rs.getString("dish_tag"));
				dish.setDish_status(rs.getInt("dish_status"));
				dish.setDish_recommend(rs.getInt("dish_recommend"));
				dish.setDish_ordered(rs.getInt("dish_ordered"));
				dish.setCat_id(rs.getLong("cat_id"));
				dish.setRest_id(rs.getLong("rest_id"));

				list.add(dish);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 获取给定餐馆前20排行的菜品
	 */
	public List<Dish> getOrderedDishes(long restid) {
		List<Dish> list = new ArrayList<Dish>();
		String sql = "SELECT TOP 20 * FROM dishes ORDER BY dish_ordered DESC WHERE rest_id = ?";
		Object[] params = { restid };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				Dish dish = new Dish();

				dish.setDish_id(rs.getLong("dish_id"));
				dish.setDish_name(rs.getString("dish_name"));
				dish.setDish_desc(rs.getString("dish_desc"));
				dish.setDish_pic(rs.getString("dish_pic"));
				dish.setDish_price(rs.getDouble("dish_price"));
				dish.setDish_tag(rs.getString("dish_tag"));
				dish.setDish_status(rs.getInt("dish_status"));
				dish.setDish_recommend(rs.getInt("dish_recommend"));
				dish.setDish_ordered(rs.getInt("dish_ordered"));
				dish.setCat_id(rs.getLong("cat_id"));
				dish.setRest_id(rs.getLong("rest_id"));

				list.add(dish);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public double getPriceByDishId(long did) {
		double price = 0.0;
		String sql = "select dish_price from dishes where dish_id=?";
		Object[] params = { did };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				price = rs.getDouble("dish_price");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return price;
	}

	/**
	 * 查找某餐厅下属指定分类菜品的数目 <br />
	 * 由于查找项都是索引项，故此查询仅查询索引即可。无性能问题。 <br />
	 * 特别地： <br />
	 * cat_id = 0 时表示忽略分组 <br />
	 * 
	 * @author wu.kui2@gmail.com
	 */
	public int queryTotalCountByCategoryAndRest(long rest_id, long cat_id) {
		int i = 0;
		String sql;
		Object[] params;
		if (cat_id == 0) {
			sql = "select count(dish_id) from dishes where rest_id = ? ";
			params = new Object[] { rest_id };
		} else {
			sql = "select count(dish_id) from dishes where rest_id = ? and cat_id = ?";
			params = new Object[] { rest_id, cat_id };
		}
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			if (rs.next()) {
				i = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * 根据菜单分类、价格排序查询某餐厅下的指定区间菜品 <br />
	 * 特别地：<br />
	 * cat_id = 0 时表示忽略分组 <br />
	 * priceOrder = null 时忽略排序 <br />
	 * 
	 * @author wu.kui2@gmail.com
	 */
	public List<Dish> queryDishesByCategoryAndRestOrderPriceOnLimit(
			long rest_id, int begin, int offset, long cat_id, String priceOrder) {
		// 由于priceOrder需拼接SQL，提前过滤
		if (priceOrder != null && !priceOrder.toLowerCase().equals("asc")
				&& !priceOrder.toLowerCase().equals("desc")) {
			priceOrder = null;
		}
		// 根据条件动态生成查询条件
		String sql;
		Object[] params;
		if (cat_id != 0 && priceOrder != null) {
			sql = "select * from dishes where rest_id = ? and cat_id = ? order by dish_price "
					+ priceOrder + " limit ?, ?";
			params = new Object[] { rest_id, cat_id, begin, offset };
		} else if (cat_id != 0) {
			sql = "select * from dishes where rest_id = ? and cat_id = ? limit ?, ?";
			params = new Object[] { rest_id, cat_id, begin, offset };
		} else if (priceOrder != null) {
			sql = "select * from dishes where rest_id = ? order by dish_price "
					+ priceOrder + " limit ?, ?";
			params = new Object[] { rest_id, begin, offset };
		} else {
			sql = "select * from dishes where rest_id = ? limit ?, ?";
			params = new Object[] { rest_id, begin, offset };
		}
		List<Dish> result = new ArrayList<Dish>();
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				Dish dish = new Dish();
				dish.setDish_id(rs.getLong("dish_id"));
				dish.setDish_name(rs.getString("dish_name"));
				dish.setDish_desc(rs.getString("dish_desc"));
				dish.setDish_pic(rs.getString("dish_pic"));
				dish.setDish_price(rs.getDouble("dish_price"));
				dish.setDish_tag(rs.getString("dish_tag"));
				dish.setDish_status(rs.getInt("dish_status"));
				dish.setDish_recommend(rs.getInt("dish_recommend"));
				dish.setDish_ordered(rs.getInt("dish_ordered"));
				dish.setCat_id(rs.getLong("cat_id"));
				dish.setRest_id(rs.getLong("rest_id"));
				result.add(dish);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}