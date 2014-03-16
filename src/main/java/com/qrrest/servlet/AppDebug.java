package com.qrrest.servlet;

/**
 * 用于输出调试信息
 * 
 * @author wu.kui2@gmail.com
 * 
 */
public class AppDebug {

	public static final boolean IS_DEBUG = true;

	public static final int LEVEL_INFO = 1;
	public static final int LEVEL_ERROR = 2;

	public static void log(@SuppressWarnings("rawtypes") Class tagClass,
			String message, int level) {
		if (IS_DEBUG) {
			switch (level) {
			case LEVEL_INFO:
				System.out.print(tagClass.getName());
				System.out.print("\t");
				System.out.println(message);
				break;
			case LEVEL_ERROR:
				System.err.print(tagClass.getName());
				System.err.print("\t");
				System.err.println(message);
			}
		}
	}

	public static void log(@SuppressWarnings("rawtypes") Class tagClass,
			String message) {
		if (IS_DEBUG) {
			System.out.print(tagClass.getName());
			System.out.print("\t");
			System.out.println(message);
		}
	}

	public static void log(String message) {
		if (IS_DEBUG) {
			System.out.println(message);
		}
	}
}
