package com.qrrest.service;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.Base64;

import org.json.JSONException;

import com.google.gson.Gson;
import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.rs.PutPolicy;

public class QiniuUploaderService {

	// 七牛账号密钥，部署时更换为正式账号
	private static final String ACCESS_KEY = "qYxg1BVylDBQbwZh6FoiQzIBpi4SVkixcOWlfz2C";
	private static final String SECRET_KEY = "CUPZZZXD4Eq8cSp8qdsrYRe34_WbrGvFbhBBr2Gl";
	// 七牛账号的空间名称，部署时根据正式账号设置
	private static final String BUCKET_NAME = "wu-kui2";

	static {
		Config.ACCESS_KEY = ACCESS_KEY;
		Config.SECRET_KEY = SECRET_KEY;
	}

	public String getUploadToken(String callbackUrl) {
		PutPolicy putPolicy = new PutPolicy(BUCKET_NAME);
		putPolicy.returnUrl = callbackUrl;
		putPolicy.returnBody = "{\"key\": $(key)}";
		try {
			return putPolicy
					.token(new Mac(Config.ACCESS_KEY, Config.SECRET_KEY));
		} catch (AuthException e) {
			throw new RuntimeException(e);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	public CallbackResult parseCallbackResult(HttpServletRequest request) {
		CallbackResult result = null;
		String upload_ret = request.getParameter("upload_ret");
		if (upload_ret != null) {
			Base64 b64 = new Base64();
			byte[] bt = b64.decode(upload_ret.getBytes());
			upload_ret = new String(bt);
			result = new Gson().fromJson(upload_ret, CallbackResult.class);
			result.setKey(result.getKey());
		}
		if (result == null) {
			result = new CallbackResult();
		}
		result.setError(request.getParameter("error"));
		return result;
	}

	public static String getUrlByKey(String key) {
		return "http://" + BUCKET_NAME + ".qiniudn.com/" + key;
	}

	public static class CallbackResult {
		private String key;
		private String error;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}

		public CallbackResult() {
		}

		public CallbackResult(String key, String error) {
			this.key = key;
			this.error = error;
		}

	}

}
