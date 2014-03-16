package com.qrrest.filter.admin;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qrrest.service.AdminAuthService;

public class AuthFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		/*
		 * 特别注意 对于http://.../Admin/*的URL，所有的/Admin/*请求都会在这里进行处理
		 * 下列代码的逻辑是将白名单以外的所有URL都强制要求登录后使用（包括该URL结构下的JS、CSS、IMG）
		 * 那么，务必确保Login.jsp、Login.do的所有依赖项都在白名单内。 否则，将全部重定向至登录页
		 * wu.kui2@gmail.com
		 */

		HttpServletRequest req = (HttpServletRequest) request;
		if (!isUrlInWhiteList(req.getRequestURI())
				&& !new AdminAuthService(req.getSession()).isAuth()) {
			((HttpServletResponse) response).sendRedirect("./Login.jsp");
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * 白名单以外的URL都将接受是否登录的检测
	 */
	private static final String[] whiteList = new String[] {
			"/Admin/Login.jsp".toLowerCase(), "/Admin/Login.do".toLowerCase(),
			"/Admin/Verifycode.do".toLowerCase() };

	private static boolean isUrlInWhiteList(String url) {
		String lowerUrl = url.toLowerCase();
		for (String pattern : whiteList) {
			if (lowerUrl.contains(pattern)) {
				return true;
			}
		}
		return false;
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

	public void destroy() {

	}

}
