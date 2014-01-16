package com.qrrest.wsorder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

/**
 * 接收用户加入餐桌请求的Websocket servlet 作为 ws://http:hostname:port/... Websocket api
 * 暴露给客户端调用 接收两个基本参数，餐桌 tid, 用户 uid，用于创建/返回WSOrderMessageInbound
 * 
 * @author LuoHanLin
 * 
 */
public class WSOrderWSServlet extends WebSocketServlet {

	/**
	 * 得到 tid, uid,通过点餐餐桌的维护列表得到已有的 MessageInbound, 如果已存在，则加入，
	 * 如果现有的点餐餐桌列表没有的话，新建 MessageInbound 返回， 如果没有，则创建
	 * 
	 */
	@Override
	protected StreamInbound createWebSocketInbound(String arg0,
			HttpServletRequest arg1) {
		String tid = arg1.getParameter("tid");
		String uid = arg1.getParameter("uid");
		System.out.println("Got params in servlet, tid: " + tid + " and uid: "
				+ uid);

		return WSOrderingListHelper.getInstance()
				.getWSOrderMessageInboundFromPool(Long.parseLong(tid),
						Long.parseLong(uid));
	}

	/**
	 * Constructor of the object.
	 */
	public WSOrderWSServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
