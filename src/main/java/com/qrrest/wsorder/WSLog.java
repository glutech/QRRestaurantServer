package com.qrrest.wsorder;

public class WSLog {

	public static final boolean isInfored = true;
	public static final boolean isDebuged = true;

	public static void d(String tag, String dStr) {
		if (isDebuged) {
			System.out.println("[[" + tag + "]]:DEBUG MESSAGE: " + dStr);
		}
	}

	public static void info(String tag, String info) {
		if (isInfored) {
			System.out.println("[[" + tag + "]]:RUNTIME INFO MESSAGE: " + info);
		}
	}
}
