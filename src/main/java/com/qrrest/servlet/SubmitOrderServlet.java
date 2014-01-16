package com.qrrest.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.qrrest.model.Comment;
import com.qrrest.model.Dish;
import com.qrrest.model.Restaurant;
import com.qrrest.service.CommentService;
import com.qrrest.service.MenuService;
import com.qrrest.service.TableService;
import com.qrrest.vo.MenuVo;
import com.qrrest.wsorder.WSOrderingListHelper;
import com.qrrest.wsorder.WSVirtualOrder;

public class SubmitOrderServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public SubmitOrderServlet() {
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
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long t_id = Long.valueOf(request.getParameter("t_id"));

		MenuService mservice = new MenuService();
		TableService tservice = new TableService();

		Restaurant rest = tservice.getRestByTableId(String.valueOf(t_id));
		WSVirtualOrder wso = WSOrderingListHelper.getInstance()
				.getSubmittedOrderByTableId(String.valueOf(t_id));

		MenuVo mv = mservice.createMenu(t_id, rest.getRest_id(),
				wso.getDishesMap(), wso.getCustomerIdList());

		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		String result = gson.toJson(mv);
		result = new String(result.getBytes("utf-8"), "iso-8859-1");
		out.append(result);
		out.close();
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

}
