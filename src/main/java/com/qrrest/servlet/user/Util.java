package com.qrrest.servlet.user;

/**
 * 后台管理的工具类库
 * 
 * @author wu.kui2@gmail.com
 * 
 */
class Util {

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

}