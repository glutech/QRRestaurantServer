package com.qrrest.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qrrest.service.QiniuUploaderService;

@SuppressWarnings("serial")
public class PicUploaderGatewayServlet extends BaseServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initBaseServlet(request, response).send400();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initBaseServlet(request, response);
		String callbackUrl = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + "/"
				+ "./User/PicUploaderCallback.do";
		String token = new QiniuUploaderService().getUploadToken(callbackUrl);
		// 仅在调试时需要在服务器和本地间跨域
		// response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		out.print(token);
		out.flush();
		out.close();
	}

}
