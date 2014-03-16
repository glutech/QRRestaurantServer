package com.qrrest.servlet.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qrrest.model.Administrator;
import com.qrrest.service.AdminAuthService;
import com.qrrest.service.AdminService;
import com.qrrest.service.VerifycodeService;

@SuppressWarnings("serial")
public class LoginServlet extends BaseServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initBaseServlet(request, response).send400();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initBaseServlet(request, response);
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String verifycode = request.getParameter("verifycode");
		if (Util.isStringNullOrEmpty(username)
				|| Util.isStringNullOrEmpty(password)
				|| Util.isStringNullOrEmpty(verifycode)) {
			ajax(false, "用户名、密码、验证码都不能为空", null);
			return;
		}
		if (!new VerifycodeService().verify(verifycode, request.getSession())) {
			ajax(false, "验证码错误", null);
			return;
		}
		Administrator admin = new AdminService().getAdminByNameAndPassword(
				username, password);
		if (admin == null) {
			ajax(false, "用户名或密码错误", null);
			return;
		}
		if (admin.getRestIdNullabled() == null) {
			ajax(false, "此账号还未关联餐厅，请联系管理员", null);
			return;
		}
		new AdminAuthService(request.getSession()).setAuth(admin);
		ajax(true, null, "./Service.jsp");
	}

}
