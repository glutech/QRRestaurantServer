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

public class UpdateCustServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UpdateCustServlet() {
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
		String oldu = request.getParameter("old_user_info");
		String newu = request.getParameter("new_user_info");

		Gson gson = new Gson();
		Customer olduser = gson.fromJson(oldu, Customer.class);
		Customer newuser = gson.fromJson(newu, Customer.class);
		
		CustomerService cservice = new CustomerService();
		
		PrintWriter out = response.getWriter();
		String result = null;
		Customer cust = cservice.updateCustomer(olduser, newuser);
		if(cust.getCustomer_pwd().equals(newuser.getCustomer_pwd())){
			result = gson.toJson(cust);
		}else{
			result = gson.toJson("old password wrong");	
		}
		
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
