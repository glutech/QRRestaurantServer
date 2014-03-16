package com.qrrest.servlet.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.qrrest.service.AdminAuthService;

/**
 * 扩展的基Servlet，扩展Servlet级的工具方法
 * 
 * @author wu.kui2@gmail.com
 */
@SuppressWarnings("serial")
public abstract class BaseServlet extends HttpServlet {

	private HttpServletRequest request;
	private HttpServletResponse response;

	protected AdminAuthService authService;

	/**
	 * 初始化BaseServlet，提供通用支持和工具方法 <br/>
	 * 返回当前BaseServlet，方便进一步调用
	 */
	protected BaseServlet initBaseServlet(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.authService = new AdminAuthService(request.getSession());
		return this;
	}

	/**
	 * 发送forward重定向，浏览器URL不发生变化，对用户透明
	 */
	protected void forward(String url) throws ServletException, IOException {
		request.getRequestDispatcher(url).forward(request, response);
	}

	/**
	 * 发送redirect重定向，浏览器URL发生变化，显式声明重定向
	 */
	protected void redirect(String url) throws IOException {
		response.sendRedirect(url);
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
		response.getWriter().write(Util.stringJoin("", html));
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
		response.getWriter().write(Util.stringJoin("", html));
	}

	/**
	 * 标准ajax返回
	 */
	protected void ajax(boolean status, String message, Object data)
			throws IOException {
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", status);
		result.put("message", message);
		result.put("data", data);
		PrintWriter out = response.getWriter();
		out.print(new Gson().toJson(result));
		out.flush();
		out.close();
	}

	/**
	 * 约定ajax返回 <br />
	 * 特别注意，AjaxType是函数式的包装，可以链式调用
	 */
	protected void ajax(AjaxReturnType type) throws IOException {
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(new Gson().toJson(type));
		out.flush();
		out.close();
	}

	/**
	 * 发送400状态码，通常表明当前请求是一个不恰当的请求（非法路径、非法参数等）
	 */
	protected void send400() {
		response.setStatus(400);
	}

	/**
	 * 发送500状态码，通常表明当前请求导致了服务器错误（内部逻辑错误、依赖项错误等）
	 */
	protected void send500() {
		response.setStatus(500);
	}

	/**
	 * 判断当前请求是否由Ajax方式发起
	 */
	protected boolean isAjax() {
		return request.getHeader("x-requested-with") != null
				&& request.getHeader("x-requested-with").equalsIgnoreCase(
						"XMLHttpRequest");
	}

	/**
	 * 约定返回Ajax结构 <br />
	 * 特别注意：所有字段都可为NULL，意味着返回JSON不一定包含该字段
	 */
	protected static class AjaxReturnType {

		private Boolean status;
		private String message;
		private String redirectUrl;
		private Boolean redirectReplace;
		private Object data;
		private Boolean reload;

		public Boolean isStatus() {
			return status;
		}

		public AjaxReturnType setStatus(Boolean status) {
			this.status = status;
			return this;
		}

		public String getMessage() {
			return message;
		}

		public AjaxReturnType setMessage(String message) {
			this.message = message;
			return this;
		}

		public String getRedirectUrl() {
			return redirectUrl;
		}

		public AjaxReturnType setRedirectUrl(String redirectUrl) {
			this.redirectUrl = redirectUrl;
			return this;
		}

		public Boolean isRedirectReplace() {
			return redirectReplace;
		}

		public AjaxReturnType setRedirectReplace(Boolean redirectReplace) {
			this.redirectReplace = redirectReplace;
			return this;
		}

		public Object getData() {
			return data;
		}

		public AjaxReturnType setData(Object data) {
			this.data = data;
			return this;
		}

		public Boolean getReload() {
			return reload;
		}

		public AjaxReturnType setReload(Boolean reload) {
			this.reload = reload;
			return this;
		}

	}

}
