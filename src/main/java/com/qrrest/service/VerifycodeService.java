package com.qrrest.service;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.servlet.http.HttpSession;

import com.qrrest.util.MD5;

public class VerifycodeService {

	private static final String SESSION_KEY = "com.qrrest.service.VerifycodeService.VERIFY_CODE";

	public BufferedImage makeCode(HttpSession session) {
		VerifycodeMaker maker = new VerifycodeMaker();
		maker.compute();
		// 保存验证码至session
		session.setAttribute(SESSION_KEY,
				MD5.get(maker.getCode().toLowerCase()));
		return maker.getImage();
	}

	public boolean verify(String code, HttpSession session) {
		// 提取之前生成、保存在session内的验证码
		Object sessionCode = session.getAttribute(SESSION_KEY);
		// 取出后立即清除上一次使用的验证码
		session.removeAttribute(SESSION_KEY);
		return MD5.get(code.toLowerCase()).equals(sessionCode);
	}

	static class VerifycodeMaker {

		private static final int WIDTH = 168;
		private static final int HEIGHT = 42;

		private static final int CODE_COUNT = 4;
		private static final String CODE_FONTNAME = "Cordia New";
		private static final String CODE_SEED = "ABCDEFGHIJKMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789";

		private static final Color BACK_COLOR = Color.WHITE;
		private static final Color BORDER_COLOR = new Color(0xdddddd);

		private static final int FILTER_LIENS = 5;
		private static final float FILTER_WIDTH = 2f;

		private static final double PADDING_SCALE = 0.85;

		private Random random = new Random();
		private String randomCode = null;
		private BufferedImage image = null;

		public void compute() {
			// 生成验证字符串
			randomCode = "";
			for (int i = 0; i < CODE_COUNT; i++) {
				randomCode += CODE_SEED.charAt(random.nextInt(CODE_SEED
						.length()));
			}
			/*
			 * 绘制验证码图形
			 */
			// 生成画布
			BufferedImage buffImg = new BufferedImage(WIDTH, HEIGHT,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D gd = buffImg.createGraphics();
			gd.setStroke(new BasicStroke(FILTER_WIDTH));
			// 绘制画布
			gd.setColor(BACK_COLOR);
			gd.fillRect(0, 0, WIDTH, HEIGHT);
			// 绘制干扰线
			for (int i = 0; i < FILTER_LIENS; i++) {
				gd.setColor(randomDark());
				gd.drawLine(random.nextInt(WIDTH), random.nextInt(HEIGHT),
						random.nextInt(WIDTH), random.nextInt(HEIGHT));
			}
			// 绘制验证码
			double fontSize = (double) HEIGHT;
			gd.setFont(new Font(CODE_FONTNAME, Font.BOLD, (int) (fontSize)));
			double drawFontX = ((double) WIDTH - fontSize * (double) CODE_COUNT)
					/ 2d + fontSize * PADDING_SCALE / 2d;
			double drawFontY = (double) HEIGHT * PADDING_SCALE
					- (double) HEIGHT * (1 - PADDING_SCALE) / 2d;
			for (int i = 0; i < CODE_COUNT; i++) {
				gd.setColor(randomWord());
				gd.drawString(String.valueOf(randomCode.charAt(i)),
						(int) drawFontX, (int) drawFontY);
				drawFontX += fontSize;
			}
			// 绘制干扰线
			for (int i = 0; i < FILTER_LIENS / 2; i++) {
				gd.setColor(randomLight());
				gd.drawLine(random.nextInt(WIDTH), random.nextInt(HEIGHT),
						random.nextInt(WIDTH), random.nextInt(HEIGHT));
			}
			// 绘制边框，定为黑色
			gd.setColor(BORDER_COLOR);
			gd.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);
			image = buffImg;
		}

		private Color randomLight() {
			int r, g, b;
			do {
				r = random.nextInt(256);
				g = random.nextInt(256);
				b = random.nextInt(256);
			} while (r < 128 && g < 128 && b < 128);
			return new Color(r, g, b);
		}

		private Color randomDark() {
			int r, g, b;
			do {
				r = random.nextInt(256);
				g = random.nextInt(256);
				b = random.nextInt(256);
			} while (r > 127 || g > 127 || b > 127);
			return new Color(r, g, b);
		}

		private Color randomWord() {
			int r, g, b;
			do {
				r = random.nextInt(256);
				g = random.nextInt(256);
				b = random.nextInt(256);
			} while ((r < 56 && g < 56 && b < 56)
					|| (r > 200 || g > 200 || b > 200));
			return new Color(r, g, b);
		}

		public String getCode() {
			return randomCode;
		}

		public BufferedImage getImage() {
			return image;
		}

	}

}
