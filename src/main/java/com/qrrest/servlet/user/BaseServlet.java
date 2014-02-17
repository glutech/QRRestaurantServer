package com.qrrest.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.qrrest.service.UserAuthService;

/**
 * 扩展的基Servlet，扩展Servlet级的工具方法
 * 
 * @author wu.kui2@gmail.com
 */
@SuppressWarnings("serial")
public abstract class BaseServlet extends HttpServlet {

	private HttpServletRequest _request;
	private HttpServletResponse _response;

	protected UserAuthService authService;

	/**
	 * 初始化BaseServlet，提供通用支持和工具方法 <br/>
	 * 返回当前BaseServlet，方便进一步调用
	 */
	protected BaseServlet initBaseServlet(HttpServletRequest request,
			HttpServletResponse response) {
		_request = request;
		_response = response;
		authService = new UserAuthService(request.getSession());
		return this;
	}

	/**
	 * 发送forward重定向，浏览器URL不发生变化，对用户透明
	 */
	protected void forward(String url) throws ServletException, IOException {
		_request.getRequestDispatcher(url).forward(_request, _response);
	}

	/**
	 * 发送redirect重定向，浏览器URL发生变化，显式声明重定向
	 */
	protected void redirect(String url) throws IOException {
		_response.sendRedirect(url);
	}

	/**
	 * 发送redirect重定向，并使用js弹出提示框
	 */
	protected void redirectAndAlertMsg(String url, String msg)
			throws IOException {
		String[] html = new String[] { "<!DOCTYPE html>", "<html>", "<head>",
				"<meta charset=\"UTF-8\" />", "<script>",
				"alert(\"" + msg.replace("\"", "\\\"") + "\");",
				"window.location=\"" + url + "\";", "</script>", "</head>",
				"<body>", "</body>", "</html>" };
		_response.getWriter().write(Util.stringJoin("", html));
	}

	/**
	 * 发送redirect重定向，并在界面上显示提示文本
	 */
	protected void redirectAndShowMsg(String url, String msg, int seconds)
			throws IOException {
		String[] html = new String[] {
				"<!DOCTYPE html>",
				"<html>",
				"<head>",
				"<meta charset=\"UTF-8\" />",
				"<meta http-equiv=\"refresh\" content=\"" + seconds + "5; url="
						+ url + " />", "</head>", "<body>", "<h4>", msg,
				"<br />", seconds + "秒后重定向", "</h4>", "</body>", "</html>" };
		_response.getWriter().write(Util.stringJoin("", html));
	}

	/**
	 * 标准ajax返回
	 */
	protected void ajaxReturn(boolean status, String message, Object data)
			throws IOException {
		_response.setHeader("Content-Type", "application/json;charset=UTF-8");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", status);
		result.put("message", message);
		result.put("data", data);
		PrintWriter out = _response.getWriter();
		out.print(new Gson().toJson(result));
		out.flush();
		out.close();
	}

	/**
	 * 发送400状态码，通常表明当前请求是一个不恰当的请求（非法路径、非法参数等）
	 */
	protected void send400() {
		_response.setStatus(400);
	}

	/**
	 * 发送500状态码，通常表明当前请求导致了服务器错误（内部逻辑错误、依赖项错误等）
	 */
	protected void send500() {
		_response.setStatus(500);
	}

	/**
	 * 判断当前请求是否由Ajax方式发起
	 */
	protected boolean isAjax() {
		return _request.getHeader("x-requested-with") != null
				&& _request.getHeader("x-requested-with").equalsIgnoreCase(
						"XMLHttpRequest");
	}

}
