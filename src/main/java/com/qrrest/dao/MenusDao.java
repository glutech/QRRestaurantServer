package com.qrrest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.qrrest.model.Dish;
import com.qrrest.model.Menu;
import com.qrrest.model.Restaurant;
import com.qrrest.util.StringDateConverter;

/*
 * 1、由id获取菜单
 * 2、获取某顾客id下的全部菜单
 * 3、新增菜单
 * 4、修改菜单
 * 5、删除菜单
 * 6、通过桌号与时间获取菜单
 * 7、由菜单id获取菜品
 */

public class MenusDao {
	private SQLExecution sqlE = new SQLExecution();

	/**
	 * 通过菜单id获取菜单
	 * 
	 * @param username
	 * @return student entity
	 */
	public Menu getMenuById(long menuid) {
		Menu menu = new Menu();
		String sql = "select * from menus where menu_id=?";
		Object[] params = { menuid };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				menu.setMenu_id(rs.getLong("menu_id"));
				menu.setMenu_price(rs.getDouble("menu_price"));
				menu.setMenu_status(rs.getInt("menu_status"));
				menu.setMenu_time(StringDateConverter.dateToString(
						rs.getTimestamp("menu_time"), "MEDIUM"));
				menu.setMenu_type(rs.getInt("menu_type"));
				menu.setRest_id(rs.getLong("rest_id"));
				menu.setTable_id(rs.getLong("table_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return menu;
	}

	/**
	 * 获取某顾客id下的全部菜单
	 */
	public List<Menu> getMenusByCustomerId(long customerid) {
		List<Menu> list = new ArrayList<Menu>();
		String sql = "select * from menus where customer_id=?";
		Object[] params = { customerid };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				Menu menu = new Menu();
				menu.setMenu_id(rs.getLong("menu_id"));
				menu.setMenu_price(rs.getDouble("menu_price"));
				menu.setMenu_status(rs.getInt("menu_status"));
				menu.setMenu_time(StringDateConverter.dateToString(
						rs.getTimestamp("menu_time"), "MEDIUM"));
				menu.setMenu_type(rs.getInt("menu_type"));
				menu.setRest_id(rs.getLong("rest_id"));
				menu.setTable_id(rs.getLong("table_id"));
				list.add(menu);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 新增菜单
	 */
	public Menu insertMenu(Menu menu) {
		boolean flag = false;
		long id = 0;

		String sql = "insert into menus (menu_status, menu_time, menu_price, menu_type, table_id, rest_id) values(?, ?, ?, ?, ?, ?)";
		Object[] params = { menu.getMenu_status(),
				new Date(System.currentTimeMillis()), menu.getMenu_price(),
				menu.getMenu_type(), menu.getTable_id(), menu.getRest_id() };
		flag = sqlE.execSqlWithoutRS(sql, params);

		if (flag) {
			String sql2 = "SELECT @@IDENTITY";
			Object[] params2 = {};
			ResultSet rs = sqlE.execSqlWithRS(sql2, params2);
			try {
				while (rs.next()) {
					id = rs.getLong(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (id != 0) {
			menu = getMenuById(id);
		} else {
			menu = null;
		}

		return menu;
	}

	/**
	 * 获取菜单数，用于分页
	 * 
	 * @return
	 */
	public int getMenusCount(long customerid) {
		int i = -1;
		String sql = "select count(*) from menus where customer_id = ?";
		Object[] params = { customerid };
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
	 * 根据菜单id删除菜单
	 * 
	 */
	public boolean deleteMenu(long menuid) {
		boolean flag = false;
		String sql = "delete from menus where menu_id = ?";
		Object[] params = { menuid };
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
	public boolean modifyMenuStatus(long menuid, int status) {
		boolean flag = false;
		String sql = "update menus set menu_status = ? where menu_id = ?";
		Object[] params = { status, menuid };
		flag = sqlE.execSqlWithoutRS(sql, params);

		return flag;
	}

	/**
	 * 修改菜单信息
	 * 
	 */
	public boolean modifyMenu(Menu menu) {
		boolean flag = false;
		String sql = "update menus set menu_status=?, menu_time=?, menu_price=?, menu_type=?, table_id=?, rest_id=? where menu_id=?";
		Object[] params = { menu.getMenu_status(),
				new Date(System.currentTimeMillis()), menu.getMenu_price(),
				menu.getMenu_type(), menu.getTable_id(), menu.getRest_id(),
				menu.getMenu_id() };
		flag = sqlE.execSqlWithoutRS(sql, params);

		return flag;
	}

	/**
	 * 由菜单id获取菜品, ADD: 添加 dish 的数量
	 */
	public List<Dish> getDishesByMenuId(long menuid) {
		List<Dish> list = new ArrayList<Dish>();
		// List<Long> dishIdList = new ArrayList<Long>();
		DishesDao dishesdao = new DishesDao();
		String sql = "select * from menudishmap where menu_id=?";
		Object[] params = { menuid };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				// dishIdList.add(rs.getLong("dish_id"));
				Dish dish = dishesdao.getDishById(rs.getLong("dish_id"));
				dish.setCount(rs.getInt("map_num"));
				list.add(dish);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// for(int i=0; i<dishIdList.size(); i++){
		// Dish dish = dishesdao.getDishById(dishIdList.get(i));
		// list.add(dish);
		// }

		return list;
	}

	public Restaurant getRestByMenuId(long mid) {
		Restaurant rest = new Restaurant();

		String sql = "select rest_id from menus where menu_id=?";
		Object[] params = { mid };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		long rid = 0;
		try {
			while (rs.next()) {
				rid = rs.getLong("rest_id");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String sql2 = "select rest_name from restaurants where rest_id=?";
		Object[] params2 = { rid };
		ResultSet rs2 = sqlE.execSqlWithRS(sql2, params2);
		try {
			while (rs2.next()) {
				rest.setRest_id(rid);
				rest.setRest_name(rs2.getString("rest_name"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rest;
	}
}
