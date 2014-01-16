package com.qrrest.wsorder;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;

/**
 * 一个 WSOrderMessageInboundPool 对应着一张餐桌，类似聊天室中的一个聊天室 这张餐桌要维护点菜过程的全部信息
 * messageInbound 对应着一个用的连接，类型聊天室的一个用户消息管道
 * 
 * @author LuoHanLin
 * 
 */
public class WSOrderMessageInboundPool {
	public long tid;
	public long creatorId;
	public WSVirtualOrder wsvo; // 每张在点餐桌都有一个唯一的点菜单，记录菜单信息
	public List<WSOrderMessageInbound> mis; // 在点用户消息绑定列表
	public List<Long> usersInThisTable; // 在点用户列表

	public WSOrderMessageInboundPool(long tid, long uid,
			WSOrderMessageInbound wsomi) {
		this.creatorId = uid;
		this.tid = tid;
		this.wsvo = new WSVirtualOrder(tid);
		this.usersInThisTable = new ArrayList<Long>();
		this.usersInThisTable.add(uid);
		mis = new ArrayList<WSOrderMessageInbound>();
		this.mis.add(wsomi);
	}

	public void addNewUserMessageInbound(WSOrderMessageInbound wsomi) {
		this.mis.add(wsomi);
		this.usersInThisTable.add(wsomi.userId);
	}

	public void addDishByUser(long did, long uid) {
		wsvo.addDishByUser(did, uid);
	}

	public void removeDishByUser(long did, long uid) {
		wsvo.removeDishByUser(did, uid);
	}

	// 给在这个餐桌上的所有小伙伴广播消息
	@SuppressWarnings("deprecation")
	public void broadcastMessage(CharBuffer message,
			WSOrderMessageInbound sender) throws IOException {
		for (WSOrderMessageInbound wsomi : mis) {
			if (wsomi != sender) {
				wsomi.outbound.writeTextMessage(message);
				wsomi.outbound.flush();
			}
		}
	}

	public void removeMessageInboundFromList(WSOrderMessageInbound mi) {
		mis.remove(mi);
		mi = null;
	}
	
	public CharBuffer generateSubmitResultByUser(long uid){
		StringBuilder sb = new StringBuilder();

		sb.append("SUBMIT_RESULT ");
		
		Gson gson = new Gson();
		DishesMapBuilderForGson gb = new DishesMapBuilderForGson();
		gb.setMap(this.wsvo.getDishesMap());
		sb.append(gson.toJson(gb));
		
		CharBuffer cb = CharBuffer.allocate(sb.length());
		cb = CharBuffer.wrap(sb);
		return cb;
	}
	
}
