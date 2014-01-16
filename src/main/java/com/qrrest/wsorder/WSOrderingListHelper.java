package com.qrrest.wsorder;

import java.util.HashMap;

/**
 * 整个点餐模块里，正在点餐的餐桌菜单在该类里面维护,负责增删点餐菜单 对正在进行的点餐的餐桌，返回给新加入用户该餐桌已有的菜单
 * 对该餐桌的点餐发起人，给该餐桌新增点菜单并返回给发起者
 * 
 * @author LuoHanLin
 * 
 */

public class WSOrderingListHelper {

	private static WSOrderingListHelper instance;
	private static HashMap<Long, WSOrderMessageInboundPool> wsOMIPoolList = new HashMap<Long, WSOrderMessageInboundPool>();

	protected WSOrderingListHelper() {

	}

	public static WSOrderingListHelper getInstance() {
		if (null == instance) {
			instance = new WSOrderingListHelper();
		}

		return instance;
	}

	/**
	 * 返回餐桌消息流的绑定实体
	 * 
	 * @param tid
	 * @param uid
	 * @return
	 */
	public WSOrderMessageInbound getWSOrderMessageInboundFromPool(long tid,
			long uid) {
		// 如果这个餐桌已经有人在点，说明pool list 中存在这个 pool，这样只要新生成一个 messageinbound 加入这个
		// pool 中就可以
		WSOrderMessageInbound wsomi = new WSOrderMessageInbound(tid, uid);
		if (wsOMIPoolList.containsKey(tid)) {
			wsOMIPoolList.get(tid).addNewUserMessageInbound(wsomi);
			wsomi.belongPool = wsOMIPoolList.get(tid);
		} else {

			WSOrderMessageInboundPool wsOMIPool = new WSOrderMessageInboundPool(
					tid, uid, wsomi);
			wsOMIPoolList.put(tid, wsOMIPool);
			wsomi.belongPool = wsOMIPool;
		}

		return wsomi;
	}

	/**
	 * 有用户退出自己所在的餐桌，从 WSOrderMessageInboundPool 中删除
	 * 如果这是该餐桌的最后一个用户的话，将 pool 从 poollist 中删除
	 * TODO：如果这个用户是这个餐桌的creator 的话，另作打算
	 * @param mi
	 */
	public static void removeMessageInboundFromPool(WSOrderMessageInbound mi) {
		if (wsOMIPoolList.containsKey(mi.tid)) {
			WSOrderMessageInboundPool wsomip = wsOMIPoolList.get(mi.tid);
			if (wsomip.mis.size() <= 1) {
				wsOMIPoolList.remove(mi.tid);
			} else {
				wsomip.removeMessageInboundFromList(mi);
			}
		}
	}
	
	/**
	 * 从点菜模块中返回虚拟菜单
	 * @param tid 餐桌 id
	 * @return WSVirtualOrder,不存在Table，则返回 null
	 */
	public static WSVirtualOrder getSubmittedOrderByTableId(String tid){
		// 从 pool 里面导出生成 order 所需信息
		if(wsOMIPoolList.containsKey(Long.parseLong(tid))){
			return wsOMIPoolList.get(Long.parseLong(tid)).wsvo;
		}
		
		return null;
	}

}
