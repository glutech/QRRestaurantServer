package com.qrrest.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
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
	 * @throws ServletException if an error occurs
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
		// TODO Auto-generated method stub
		super.doPost(req, resp);
		Long rID = Long.parseLong(req.getParameter("r_id"));
		String orderStr = req.getParameter("order_list");
		
		// 使用与客户端统一的 gson 序列化格式的类来反序列化，DishesMapBuilderForGson
		DishesMapBuilderForGson mb = new Gson().fromJson(orderStr, DishesMapBuilderForGson.class);
		mb.getMap();
		
		System.out.println("Got a book order for rID:" + rID + " /and the detail:" + orderStr);
	}
	
	

}
