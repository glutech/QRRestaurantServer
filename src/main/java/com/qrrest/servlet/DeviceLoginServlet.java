package com.qrrest.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.qrrest.model.Customer;
import com.qrrest.service.CustomerService;

public class DeviceLoginServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public DeviceLoginServlet() {
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
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String device_id = request.getParameter("device_id");
		CustomerService cservice = new CustomerService();
		
		//根据设备号取出顾客数据
		Customer cust = cservice.getCustomerByDeviceId(device_id);
		System.out.println("the existing cust did:"+ cust.getCustomer_deviceid());
		
		//判断顾客设备号是否存在，若不存在则新建顾客而后再取出顾客数据
		if(cust.getCustomer_deviceid().equals("0")){
			cservice.createCustomerWithDeviceId(device_id);
			cust = cservice.getCustomerByDeviceId(device_id);
		}
		
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		String result = gson.toJson(cust);
		result = new String(result.getBytes("utf-8"), "iso-8859-1");
		out.append(result);
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
