package com.qrrest.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.qrrest.service.TableService;

public class CheckTableServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public CheckTableServlet() {
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
		String t_id = request.getParameter("t_id");
		
		TableService tservice = new TableService();
		
		String result = String.valueOf(tservice.checkTableStatus(t_id));
		
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		result = gson.toJson(result);
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
