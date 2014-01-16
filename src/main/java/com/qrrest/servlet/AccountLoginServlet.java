package com.qrrest.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.qrrest.service.CustomerService;
import com.qrrest.model.Customer;

public class AccountLoginServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AccountLoginServlet() {
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
		String user_name = request.getParameter("user_name");
		String password = request.getParameter("password");
		
		CustomerService cservice = new CustomerService();
		Customer cust = new Customer();
		String err = "no response";
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		String result = null;
		
		switch(cservice.validateAccount(user_name, password)){
		case 0:
			err = "no_such_user";
			result = gson.toJson(err);
			break;
		case 1:
			cust = cservice.getCustomerByName(user_name);
			result = gson.toJson(cust);
			break;
		case 2:
			err = "wrong_password";
			result = gson.toJson(err);
			break;
		default:
			err = "no_response";
			result = gson.toJson(err);
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