package com.qrrest.servlet;

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
public class GetRestListServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RestaurantService restService = new RestaurantService();
		List<RestaurantVo> restVos = restService.getAllRestVos();

		Gson gson = new Gson();
		String result = gson.toJson(restVos);
		if (AppDebug.IS_DEBUG) {
			AppDebug.log(getClass(), result);
		}

		PrintWriter out = response.getWriter();
		result = new String(result.getBytes("utf-8"), "iso-8859-1");
		out.append(result);
		out.close();
	}

}
