package com.qrrest.servlet.app;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.qrrest.service.DishService;
import com.qrrest.service.TableService;
import com.qrrest.vo.DishesVo;

@SuppressWarnings("serial")
public class GetDishesVoForOrderServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int tableId = Integer.parseInt( request.getParameter("t_id"));
		
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

		String result = new Gson().toJson(vo);
		if (AppDebug.IS_DEBUG) {
			AppDebug.log(getClass(), result);
		}

		PrintWriter out = response.getWriter();
		out.append(result);
		out.close();
	}

}
