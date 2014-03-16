package com.qrrest.service;

import java.sql.Timestamp;
import java.util.List;

import com.qrrest.dao.OrderDao;
import com.qrrest.model.Order;
import com.qrrest.model.Order.OrderStatusEnum;
import com.qrrest.model.Table;
import com.qrrest.vo.OrderItemVo;

public class OrderService {

    private OrderDao orderDao = new OrderDao();

    private int lastInsertId;

    public int getLastInsertId() {
        return lastInsertId;
    }

    public Order getOrderByOrderId(int orderId) {
        return orderDao.getOrderByOrderId(orderId);
    }

    public double queryActiveTotalPrice(int orderId) {
        return orderDao.queryActiveTotalPrice(orderId);
    }

    /**
     * 创建现场服务订单
     */
    public boolean createOrderForServing(Table table, int createUserId) {
        Order order = new Order();
        order.setCreatorUserIdNullabled(createUserId);
        order.setActiveTableIdNullabled(table.getTableId());
        order.setRestId(table.getRestId());
        order.setOrderStatus(OrderStatusEnum.serving);
        order.setOrderTime(new Timestamp(System.currentTimeMillis()));
        order.setOrderDuePrice(0);
        order.setOrderActualPrice(0);
        order.setOrderMemo("");
        if (orderDao.insertOrder(order)) {
            lastInsertId = orderDao.getLastInsertId();
            return true;
        } else {
            return false;
        }
    }

    public boolean fillOrderDishAndUserRelationship(int orderId,
                                                    List<Integer> userIds, List<OrderItemVo> orderItems) {
        return orderDao.fillOrderUserRelationship(orderId, userIds)
                && orderDao.fillOrderDishRelationship(orderId, orderItems);
    }

    public int getServingOrderIdByTableId(int tableId) {
        return orderDao.getServingOrderIdByTableId(tableId);
    }

    public List<OrderItemVo> getOrderDishRelationship(int orderId) {
        return orderDao.getOrderDishRelationship(orderId);
    }

}
