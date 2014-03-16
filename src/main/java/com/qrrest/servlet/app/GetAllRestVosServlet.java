package com.qrrest.servlet.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.qrrest.service.RestaurantService;
import com.qrrest.vo.RestaurantVo;

@SuppressWarnings("serial")
public class GetAllRestVosServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RestaurantService restService = new RestaurantService();
		List<RestaurantVo> restVos = restService.getAllRestVos();

		String result = new Gson().toJson(restVos);
		if (AppDebug.IS_DEBUG) {
			AppDebug.log(getClass(), result);
		}

		PrintWriter out = response.getWriter();
		out.append(result);
		out.close();
	}

}
