package com.qrrest.ws.app;

public class WSLog {

	public static final boolean IS_INFO = true;
	public static final boolean IS_DEBUG = true;
	public static final boolean IS_WARNING = true;

	public static void d(String tag, String message) {
		if (IS_DEBUG) {
			System.out.println("[[" + tag + "]]:DEBUG MESSAGE: " + message);
		}
	}

	public static void i(String tag, String message) {
		if (IS_INFO) {
			System.out.println("[[" + tag + "]]:INFO MESSAGE: " + message);
		}
	}

	public static void e(String tag, String message) {
		System.out.println("[[" + tag + "]]:ERROR MESSAGE: " + message);
	}

	public static void w(String tag, String message) {
		if (IS_WARNING) {
			System.out.println("[[" + tag + "]]:WARNING MESSAGE: " + message);
		}
	}
}
