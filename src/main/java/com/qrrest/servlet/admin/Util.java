package com.qrrest.servlet.admin;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台管理的工具类库
 * 
 * @author wu.kui2@gmail.com
 * 
 */
public class Util {

	public static boolean isStringNullOrEmpty(String string) {
		return string == null || string.equals("");
	}

	public static boolean isStringEquals(String sa, String sb) {
		return sa == null ? false : sa.equals(sb);
	}

	public static String stringJoin(String separator, String[] value) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < value.length; i++) {
			sb.append(value[i]);
			if (i != value.length - 1) {
				sb.append(separator);
			}
		}
		return sb.toString();
	}

	public static String text2Textarea(String text) {
		return text.replaceAll(" ", "&nbsp;").replaceAll("\n", "<br />");
	}

	public static String getCurrentUrl(HttpServletRequest request) {
		String url = request.getScheme() + "://";
		url += request.getHeader("host");
		url += request.getRequestURI();
		if (request.getQueryString() != null)
			url += "?" + request.getQueryString();
		return url;
	}
}