package com.qrrest.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.qrrest.model.Category;
import com.qrrest.model.Dish;
import com.qrrest.model.Restaurant;
import com.qrrest.service.CategoryService;
import com.qrrest.service.DishService;
import com.qrrest.service.RestaurantService;
import com.qrrest.service.TableService;
import com.qrrest.vo.DishesVo;

public class GetDishesServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GetDishesServlet() {
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
		String r_id = request.getParameter("r_id");
		                                 
		RestaurantService rservice = new RestaurantService();
		DishService dservice = new DishService();
		CategoryService cservice = new CategoryService();
		
		String result = "0";
		Gson gson = new Gson();
		
		DishesVo dvo = new DishesVo();

			dvo.setRest_id(r_id);
			dvo.setRest_name(rservice.getRestById(r_id).getRest_name());
			ArrayList<Dish> dishes = dservice.getDishesByRestId(r_id);
			ArrayList<Category> cats = new ArrayList<Category>();
			for(int i = 0; i < dishes.size(); i++ ){
				Dish d = dishes.get(i);
				Category cat = cservice.getCatByDishId(d.getDish_id());
				
				cats.add(cat);
			}
			dvo.setDishlist(dishes);
			dvo.setCatlist(cats);
			result = gson.toJson(dvo);
		
		PrintWriter out = response.getWriter();

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
