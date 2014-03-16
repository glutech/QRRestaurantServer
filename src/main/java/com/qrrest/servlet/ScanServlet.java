package com.qrrest.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.qrrest.model.Dish;
import com.qrrest.model.Table;
import com.qrrest.model.Dish.DishStatusEnum;
import com.qrrest.model.Table.TableStatusEnum;
import com.qrrest.service.CategoryService;
import com.qrrest.service.DishService;
import com.qrrest.service.RestaurantService;
import com.qrrest.service.TableService;
import com.qrrest.vo.DishesVo;

public class ScanServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int tableId = Integer.parseInt( request.getParameter("t_id"));
		//int userId = Integer.parseInt(request.getParameter("u_id"));
		
		TableService tableService = new TableService();
		DishService dishService = new DishService();
		
		Table table = tableService.getTableById(tableId);
		if(table == null) {
			if(AppDebug.IS_DEBUG) {
				AppDebug.log(getClass(),"invalid t_id, not found: " + tableId,AppDebug.LEVEL_ERROR);
			}
			response.setStatus(500);
			return;
		}
		if(table.getTableStatus() == Table.TableStatusEnum.blocked || table.getTableStatus() == TableStatusEnum.controlled) {
			if(AppDebug.IS_DEBUG) {
				AppDebug.log(getClass(),"table_status out of service: " + tableId,AppDebug.LEVEL_ERROR);
			}
		}
		
		DishesVo vo = dishService.getDishesVoByRestId(table.getRestId());
		Iterator<Dish> dishIterator = vo.getDishList().iterator();
		while (dishIterator.hasNext()) {
			if (dishIterator.next().getDishStatus() == DishStatusEnum.blocked) {
				dishIterator.remove();
			}
		}

		Gson gson = new Gson();
		String result = gson.toJson(vo);
		if (AppDebug.IS_DEBUG) {
			AppDebug.log(getClass(), result);
		}

		PrintWriter out = response.getWriter();
		result = new String(result.getBytes("utf-8"), "iso-8859-1");
		out.append(result);
		out.close();
		
		
		
//		String t_id = request.getParameter("t_id");
//		
//		TableService tservice = new TableService();
//		RestaurantService rservice = new RestaurantService();
//		DishService dservice = new DishService();
//		CategoryService cservice = new CategoryService();
//		
//		String result = "0";
//		Gson gson = new Gson();
//		
//		DishesVo dvo = new DishesVo();
//		if(tservice.checkTableStatus(t_id)== 1){
//			Restaurant rest = tservice.getRestByTableId(t_id);
//			dvo.setRest_id(String.valueOf(rest.getRest_id()));
//			dvo.setRest_name(rest.getRest_name());
//			ArrayList<Dish> dishes = dservice.getDishesByRestId(String.valueOf(rest.getRest_id()));
//			ArrayList<Category> cats = new ArrayList<Category>();
//			for(int i = 0; i < dishes.size(); i++ ){
//				Dish d = dishes.get(i);
//				Category cat = cservice.getCatByDishId(d.getDish_id());
//				
//				cats.add(cat);
//			}
//			dvo.setDishlist(dishes);
//			dvo.setCatlist(cats);
//			result = gson.toJson(dvo);
//		}else{
//			result = "table not available";
//		}
//		
//		PrintWriter out = response.getWriter();
//
//		result = new String(result.getBytes("utf-8"), "iso-8859-1");
//		out.append(result);
//		out.close();
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
