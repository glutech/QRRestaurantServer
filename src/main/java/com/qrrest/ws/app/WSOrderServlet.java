package com.qrrest.ws.app;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

@SuppressWarnings({ "serial", "deprecation" })
public class WSOrderServlet extends WebSocketServlet {

	@Override
	protected StreamInbound createWebSocketInbound(String arg0,
			HttpServletRequest arg1) {
		String tid = arg1.getParameter("tid");
		String uid = arg1.getParameter("uid");
		// TODO: tid uid 有效性检测
		if (WSLog.IS_DEBUG) {
			WSLog.d(getClass().getSimpleName(), "请求WS，tid:" + tid + ",uid:"
					+ uid);
		}
		return TableMessageHandlerPool.getInstance()
				.getOrCreateHandler(Integer.parseInt(tid))
				.createMessageInbound(Integer.parseInt(uid));
	}

}
