package com.qrrest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.qrrest.model.Order;
import com.qrrest.model.Order.OrderStatusEnum;
import com.qrrest.vo.OrderItemVo;

public class OrderDao extends BaseDao<Order> {

	@Override
	protected Order parseRS(ResultSet rs) throws SQLException {
		Order model = new Order();
		model.setOrderId(rs.getInt("order_id"));
		model.setOrderStatus(OrderStatusEnum.valueOf(rs
				.getString("order_status")));
		model.setOrderTime(rs.getTimestamp("order_time"));
		model.setOrderDuePrice(rs.getDouble("order_due_price"));
		model.setOrderActualPrice(rs.getDouble("order_actual_price"));
		model.setOrderMemo(rs.getString("order_memo"));
		model.setCreatorUserIdNullabled(rs.getInt("creator_user_id_nullabled"));
		if (model.getCreatorUserIdNullabled() == 0)
			model.setCreatorUserIdNullabled(null);
		model.setActiveTableIdNullabled(rs.getInt("active_table_id_nullabled"));
		if (model.getActiveTableIdNullabled() == 0)
			model.setActiveTableIdNullabled(null);
		model.setRestId(rs.getInt("rest_id"));
		return model;
	}

	public int getLastInsertId() {
		return super.getLastInsertId();
	}

	public Order getOrderByOrderId(int orderId) {
		String sql = "select * from orders where order_id = ?";
		Object[] params = { orderId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToModel(rs);
	}

	public List<Order> getOrdersByRestId(int restId) {
		String sql = "select * from orders where rest_id = ?";
		Object[] params = { restId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToList(rs);
	}

	public List<Order> getOrdersByCreatorUserId(int creatorUserId) {
		String sql = "select * from orders where creator_user_id is not null and creator_user_id = ?";
		Object[] params = { creatorUserId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToList(rs);
	}

	public int getServingOrderIdByTableId(int tableId) {
		String sql = "select order_id from orders where order_status = ? and active_table_id_nullabled is not null and active_table_id_nullabled = ?";
		Object[] params = { OrderStatusEnum.serving.toString(), tableId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseInt(rs);
	}

	public boolean modifyOrderStatus(Order order) {
		String sql = "update orders set order_status = ? where order_id = ?";
		Object[] params = { order.getOrderStatus().toString(),
				order.getOrderId() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public boolean insertOrder(Order order) {
		String sql = "insert into orders(order_status, order_time, order_due_price, order_actual_price, order_memo, creator_user_id_nullabled, active_table_id_nullabled, rest_id) values(?, ?, ?, ?, ?, ?, ?, ?)";
		Object[] params = { order.getOrderStatus().toString(),
				order.getOrderTime(), order.getOrderDuePrice(),
				order.getOrderActualPrice(), order.getOrderMemo(),
				order.getCreatorUserIdNullabled(),
				order.getActiveTableIdNullabled(), order.getRestId() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public boolean updateOrder(Order order) {
		String sql = "update orders set order_status = ?, order_time = ?, order_due_price = ?, order_actual_price = ?, order_memo = ?, creator_user_id_nullabled = ?, active_table_id_nullabled = ?, rest_id = ? where order_id = ?";
		Object[] params = { order.getOrderStatus().toString(),
				order.getOrderTime(), order.getOrderDuePrice(),
				order.getOrderActualPrice(), order.getOrderMemo(),
				order.getCreatorUserIdNullabled(),
				order.getActiveTableIdNullabled(), order.getRestId(),
				order.getOrderId() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public boolean deleteOrder(Order order) {
		String sql = "delete from orders where order_id = ?";
		Object[] params = { order.getOrderId() };
		return getSqlExecution().execSqlWithoutRS(sql, params);
	}

	public List<Order> getOrdersByUserId(int userId) {
		String sql = "select * from orders where order_id in (select order_id form order_users_map where user_id = ?";
		Object[] params = { userId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseRsToList(rs);
	}

	public List<OrderItemVo> getOrderDishRelationship(int orderId) {
		String sql = "select dish_id, dish_count from order_active_dishes_map where order_id = ?";
		Object[] params = { orderId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		List<OrderItemVo> r = new LinkedList<OrderItemVo>();
		try {
			while (rs.next()) {
				OrderItemVo vo = new OrderItemVo();
				vo.setDishId(rs.getInt("dish_id"));
				vo.setDishCount(rs.getInt("dish_count"));
				r.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return r;
	}

	public double queryActiveTotalPrice(int orderId) {
		String sql = "select sum(ta.dish_count * tb.dish_price) as price from ( "
				+ "select dish_id, dish_count from order_active_dishes_map where order_id = ? "
				+ ") as ta left join dishes as tb on ta.dish_id = tb.dish_id";
		Object[] params = { orderId };
		ResultSet rs = getSqlExecution().execSqlWithRS(sql, params);
		return parseDouble(rs);
	}

	public boolean fillOrderUserRelationship(int orderId, List<Integer> userIds) {
		// 检查关系
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>(userIds.size() * 4);
		for (Integer userId : userIds) {
			sql.append("insert into order_users_map(order_id, user_id) select * from (select ? as order_id, ? as user_id) as tmp where not exists (select order_id, user_id from order_users_map where order_id = ? and user_id = ?);");
			params.add(orderId);
			params.add(userId);
			params.add(orderId);
			params.add(userId);
		}
		return getSqlExecution().execSqlWithoutRS(sql.toString(),
				params.toArray());
	}

	public boolean fillOrderDishRelationship(int orderId,
			List<OrderItemVo> orderItems) {
		// 填充新关系
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>(orderItems.size() * 3);
		for (OrderItemVo item : orderItems) {
			sql.append("insert into order_active_dishes_map(order_id, dish_id, dish_count) values(?, ?, ?);");
			params.add(orderId);
			params.add(item.getDishId());
			params.add(item.getDishCount());
		}
		return getSqlExecution().execSqlWithoutRS(sql.toString(),
				params.toArray());
	}
}
