package com.qrrest.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qrrest.model.User;
import com.qrrest.service.UserAuthService;
import com.qrrest.service.UserService;
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
			redirectAndAlertMsg("./Login.jsp", "用户名、密码、验证码都不能为空");
			return;
		}
		if (!new VerifycodeService().verify(verifycode, request.getSession())) {
			redirectAndAlertMsg("./Login.jsp", "验证码错误");
			return;
		}
		User user = new UserService().getByUsernameAndPassword(username, password);
		if (user == null) {
			redirectAndAlertMsg("./Login.jsp", "用户名或密码错误");
			return;
		}
		new UserAuthService(request.getSession()).setAuth(user);
		redirect("./Main.jsp");
	}

}
