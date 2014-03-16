package com.qrrest.servlet.app;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.qrrest.service.RestaurantService;
import com.qrrest.vo.RestaurantVo;

@SuppressWarnings("serial")
public class GetRestVoDetailServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int restId = Integer.parseInt(request.getParameter("r_id"));

		RestaurantService restService = new RestaurantService();
		RestaurantVo restVo = restService.getRestVoByRestId(restId);

		String result = new Gson().toJson(restVo);
		if (AppDebug.IS_DEBUG) {
			AppDebug.log(getClass(), result);
		}

		PrintWriter out = response.getWriter();
		out.append(result);
		out.close();
	}
}
