package com.qrrest.wsorder;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;

/**
 * 一个 WSOrderMessageInbound 对应着一个用户连接，类似聊天室中的一个用户消息管道
 * 这个用户连接属于一个餐桌，被一个用户拥有，用于处理这个用 接收/发送 的消息 这些消息会被解析成特定操作：增删菜品、提交、等 这意味着，
 * WSOrderMessageInbound 要操作它的所属 WSOrderMessageInboundPool
 * 
 * @author LuoHanLin
 * 
 */
public class WSOrderMessageInbound extends MessageInbound {

	public long tid;
	public long userId;
	public WsOutbound outbound;
	public WSOrderMessageInboundPool belongPool;

	public WSOrderMessageInbound(long tid, long uid) {
		super();
		this.tid = tid;
		this.userId = uid;
	}

	/**
	 * 当第一次打开餐桌点餐的时候，也就是连接这个MessageInbound 的时候会调用这个函数 进行一些必要的初始化操作
	 */
	@Override
	protected void onOpen(WsOutbound outbound) {
		System.out.println("On open...");
		this.outbound = outbound;

		// 有新的小伙伴加入到这个点餐队伍中，告知其他的小伙伴我来了
		StringBuilder sb = new StringBuilder("JOIN "
				+ String.valueOf(this.userId));
		;
		
		CharBuffer buffer = CharBuffer.allocate(sb.length());

		buffer = CharBuffer.wrap(sb);
		try {
			syncExistsData();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			belongPool.broadcastMessage(buffer, this);
		} catch (IOException e) {
			// TODO 告知失败，是不是做点什么？？
			e.printStackTrace();
		}
	}

	@Override
	protected void onClose(int status) {
		System.out.println("On close...");

		// TODO: 告知的操作有问题
		// 有新的小伙伴离开这个点餐队伍中，告知其他的小伙伴我走了
		StringBuilder sb = new StringBuilder("LEAVE "
				+ String.valueOf(this.userId));
		CharBuffer buffer = CharBuffer.allocate(sb.length());

		buffer = CharBuffer.wrap(sb);

		try {
			belongPool.broadcastMessage(buffer, this);
		} catch (IOException e) {
			// TODO 告知失败，是不是做点什么？？
			e.printStackTrace();
		}

		WSOrderingListHelper.removeMessageInboundFromPool(this);
	}

	@Override
	protected void onBinaryMessage(ByteBuffer arg0) throws IOException {
		System.out.println("On binaryMessage comming...");

	}

	@Override
	protected void onTextMessage(CharBuffer arg0) throws IOException {
		System.out.println("On Text message comming..., message is: " + arg0);

		// 解析字符串命令，转换成操作
		// 命令格式： String[] command = {"action", "uid", "did"}
		// uid 在用户自己的 messageBound 里没有用，但在广播到其他用户那里的时候需要
		long did = -1;
		String message = arg0.toString();
		String[] messages = message.split(" ");
		if (messages[0].equals("ADD")) {
			// 增加菜品
			did = Long.parseLong(messages[2]);
			System.out.println("Got a add action, the did is: " + did);
			if (null != this.belongPool) {
				belongPool.addDishByUser(did, userId);
			}

		} else if (messages[0].equals("DELETE")) {
			// 删除菜品
			did = Long.parseLong(messages[2]);
			System.out.println("Got a delete action, the did is: " + did);
			if (null != this.belongPool) {
				belongPool.removeDishByUser(did, userId);
			}
		} else if(messages[0].equals("SUBMIT")){
			// 用户发出提交申请，整理菜单，返回给用户显示，等待用户确认
			CharBuffer result = this.belongPool.generateSubmitResultByUser(userId);
			this.outbound.writeTextMessage(result);
		}else{
			// 其他不能识别的操作
			System.out.println("Got unkwown action message...");
		}

		// 将这个用户的操作广播给所有在这个餐桌上点餐的小伙伴们
		// TODO: 在广播之前，如果用户有昵称，首先要将消息的发送者有 uid 处理成用户昵称，没有的话就不用了
		belongPool.broadcastMessage(arg0, this);

	}
	
	/**
	 * 在接入之后，如果现在这个餐桌上已经有点餐数据，需要将这些数据同步给新用户
	 * @throws IOException 
	 */
	private void syncExistsData() throws IOException{
		Iterator it = this.belongPool.wsvo.dishByUserMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<Long, List<Long>> entry = (Entry<Long, List<Long>>) it.next();
			long did = entry.getKey();
			List<Long> uids = entry.getValue();
			for(long uid: uids){
				StringBuilder sb = new StringBuilder("SYNC_DATA "
						+ String.valueOf(uid) + " " + String.valueOf(did));
				;
				
				CharBuffer buffer = CharBuffer.allocate(sb.length());

				buffer = CharBuffer.wrap(sb);
				this.outbound.writeTextMessage(buffer);
				this.outbound.flush();
			}
		}
	}

}
