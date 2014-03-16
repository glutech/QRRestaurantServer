package com.qrrest.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.qrrest.model.User;
import com.qrrest.service.UserService;

@SuppressWarnings("serial")
public class DeviceLoginServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String device_id = request.getParameter("device_id");
		UserService userService = new UserService();

		User user = userService.getUserByDeviceId(device_id);
		// 判断顾客设备号是否存在，若不存在则新建顾客而后再取出顾客数据
		if (user != null) {
			if (AppDebug.IS_DEBUG) {
				AppDebug.log(getClass(), user.getUserDeviceId());
			}
		} else {
			userService.createUserWithDeviceId(device_id);
			user = userService.getUserByDeviceId(device_id);
		}
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		String result = gson.toJson(user);
		result = new String(result.getBytes("utf-8"), "iso-8859-1");
		out.append(result);
		out.close();
	}

}
