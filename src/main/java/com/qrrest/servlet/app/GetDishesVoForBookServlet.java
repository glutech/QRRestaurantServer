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
import com.qrrest.model.Dish.DishStatusEnum;
import com.qrrest.service.DishService;
import com.qrrest.vo.DishesVo;

@SuppressWarnings("serial")
public class GetDishesVoForBookServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int restId = Integer.parseInt(request.getParameter("r_id"));

		DishService dishService = new DishService();
		DishesVo vo = dishService.getDishesVoByRestId(restId);

		if (vo == null) {
			if (AppDebug.IS_DEBUG) {
				AppDebug.log(getClass(), "invalid r_id, not found: " + restId,
						AppDebug.LEVEL_ERROR);
			}
			response.setStatus(500);
			return;
		}

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
