package com.qrrest.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.qrrest.service.QiniuUploaderService;

@SuppressWarnings("serial")
public class PicUploaderCallbackServlet extends BaseServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initBaseServlet(request, response);
		// 仅在调试时需要在服务器和本地间跨域
		// response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		QiniuUploaderService.CallbackResult result = new QiniuUploaderService()
				.parseCallbackResult(request);
		out.print(new Gson().toJson(result));
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initBaseServlet(request, response).send400();
	}

}
