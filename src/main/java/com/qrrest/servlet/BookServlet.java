package com.qrrest.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.qrrest.model2.Book;
import com.qrrest.model2.Customer;
import com.qrrest.model2.Menu;
import com.qrrest.service.BookService2;
import com.qrrest.vo2.BookRequestResultVo;
import com.qrrest.vo2.BookVo;

public class BookServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public BookServlet() {
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
		String book = request.getParameter("book");
		
		Gson gson = new Gson();
		BookRequestResultVo rbv = gson.fromJson(book, BookRequestResultVo.class);
		
		Book bk = new Book();
		bk.setBook_id(Long.valueOf(rbv.getBook_id()));
		bk.setBook_name(rbv.getBook_name());
		bk.setBook_tel(rbv.getBook_tel());
		bk.setCustomer_id(Long.valueOf(rbv.getCustomer_id()));
		
		Menu me = new Menu();
		me.setMenu_id(0);
		me.setMenu_price(0);
		me.setMenu_status(3);
		me.setMenu_time((new Timestamp(System.currentTimeMillis())).toString());
		me.setMenu_type(0);
		me.setRest_id(Long.valueOf(rbv.getRest_id()));
		me.setTable_id(1);
		
		ArrayList<String> olist = rbv.getDishes();
		ArrayList<Long> dlist = new ArrayList<Long>();
		for(int i = 0; i < olist.size(); i ++){
			dlist.add(Long.valueOf(olist.get(i)));
		}
		
		BookService2 bservice = new BookService2();
		BookVo bv = bservice.createBook(bk, me, dlist);
		
		PrintWriter out = response.getWriter();
		String result = gson.toJson(bv);
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
