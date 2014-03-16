package com.qrrest.servlet.app;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.qrrest.model.Book;
import com.qrrest.model.Order;
import com.qrrest.model.Order.OrderStatusEnum;
import com.qrrest.model.Restaurant;
import com.qrrest.service.BookService;
import com.qrrest.service.DishService;
import com.qrrest.service.OrderService;
import com.qrrest.service.RestaurantService;
import com.qrrest.vo.MixedOrderVo;

@SuppressWarnings("serial")
public class GetMixedOrderServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String o_id = request.getParameter("o_id");
		String b_id = request.getParameter("b_id");

		MixedOrderVo vo = new MixedOrderVo();
		int restId;

		if (o_id != null) {
			int orderId = Integer.parseInt(o_id);
			OrderService service = new OrderService();
			Order order = service.getOrderByOrderId(orderId);
			vo.setBookOrder(false);
			vo.setOrderActive(order.getOrderStatus() == OrderStatusEnum.serving);
			if(vo.isOrderActive()) {
				vo.setTableId(order.getActiveTableIdNullabled().intValue());
			}
			vo.setOrderDate(order.getOrderTime());
			vo.setOrderPrice(service.queryActiveTotalPrice(orderId));
			restId = order.getRestId();
			vo.setOrderItems(new DishService()
					.getFullOrderDishRelationship(orderId));
		} else {
			int bookId = Integer.parseInt(b_id);
			BookService service = new BookService();
			Book book = service.getBookByBookId(bookId);
			vo.setBookOrder(true);
			vo.setOrderActive(false);
			vo.setOrderDate(book.getBookTime());
			vo.setOrderPrice(service.queryTotalPrice(bookId));
			restId = book.getRestId();
			vo.setOrderItems(new DishService()
					.getFullBookDishRelationship(bookId));
		}
		Restaurant rest = new RestaurantService().getRestById(restId);
		vo.setRestAddr(rest.getRestAddr());
		vo.setRestName(rest.getRestName());
		vo.setRestTel(rest.getRestTel());

		String result = new Gson().toJson(vo);
		if (AppDebug.IS_DEBUG) {
			AppDebug.log(getClass(), result);
		}

		PrintWriter out = response.getWriter();
		out.append(result);
		out.close();
	}

}