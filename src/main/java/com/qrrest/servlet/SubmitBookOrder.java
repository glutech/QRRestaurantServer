package com.qrrest.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.qrrest.model.Restaurant;
import com.qrrest.service.MenuService;
import com.qrrest.service.RestaurantService;
import com.qrrest.vo.MenuVo;
import com.qrrest.wsorder.DishesMapBuilderForGson;

public class SubmitBookOrder extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public SubmitBookOrder() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Long cID = Long.parseLong(req.getParameter("c_id"));
		Long rID = Long.parseLong(req.getParameter("r_id"));
		String orderStr = req.getParameter("order_list");
		
		Gson gson = new Gson();

		// 使用与客户端统一的 gson 序列化格式的类来反序列化，DishesMapBuilderForGson
		Map<Long, Integer> dishMap = gson.fromJson(orderStr,
				DishesMapBuilderForGson.class).getMap();

		System.out.println("Got a book order for rID:" + rID
				+ " /and the detail:" + orderStr);

		MenuService mService = new MenuService();
		RestaurantService restService = new RestaurantService();

		Restaurant rest = restService.getRestById(String.valueOf(rID));

		List<Long> customers = new ArrayList<Long>();
		customers.add(cID);
		MenuVo mv = mService.createMenu(0, rID, dishMap, customers);

		String result = gson.toJson(mv);

		PrintWriter out = resp.getWriter();
		result = new String(result.getBytes("utf-8"), "iso-8859-1");
		out.append(result);
		out.close();

	}
}
