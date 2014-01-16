package com.qrrest.wsorder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * WSOrder 模块点菜是生成的临时菜单-用户表
 * 
 * @author LuoHanLin
 * 
 */
public class WSVirtualOrder {
	public long tid;
	// Long dishId, List<Long> 点这个菜的用户id
	public Map<Long, List<Long>> dishByUserMap = new HashMap<Long, List<Long>>();

	public WSVirtualOrder(long tid) {
		this.tid = tid;
	}

	public void addDishByUser(long did, long uid) {
		if (dishByUserMap.containsKey(did)) {
			dishByUserMap.get(did).add(uid);
		} else {
			List<Long> uids = new ArrayList<Long>();
			uids.add(uid);
			dishByUserMap.put(did, uids);
		}
	}

	/**
	 * 用户删除一个菜品，有可能是删除的不是自己点的，但优先删除自己点的 如果这个菜品只有一个用户点，则可以将这个菜品从map 中移除
	 * 
	 * @param did
	 * @param uid
	 */
	public void removeDishByUser(long did, long uid) {
		if (dishByUserMap.containsKey(did)) {
			List<Long> uids = dishByUserMap.get(did);
			// 如果该菜品数量为1，则从 map 中移除
			if (uids.size() == 1) {
				dishByUserMap.remove(did);
			} else {
				// 若是该用户点过，优先删除
				if (uids.contains(uid)) {
					uids.remove(uid);
				} else {
					// 没有的话，无所谓，删除最后一个
					uids.remove(uids.size() - 1);
				}

			}
		}
	}

	/**
	 * 
	 * @return 菜品 id 和其对应的 数量
	 */
	public Map<Long, Integer> getDishesMap() {
		Map<Long, Integer> dishCountMap = new HashMap<Long, Integer>();
		Iterator iter = dishByUserMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<Long, ArrayList<Long>> entry = (Entry<Long, ArrayList<Long>>) iter
					.next();
			dishCountMap.put(entry.getKey(), entry.getValue().size());
		}

		return dishCountMap;
	}

	/**
	 * 
	 * @return 这次点菜的所有用户列表
	 */
	public List<Long> getCustomerIdList() {
		List<Long> users = new ArrayList<Long>();
		Iterator iter = dishByUserMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<Long, ArrayList<Long>> entry = (Entry<Long, ArrayList<Long>>) iter
					.next();
			for (Long uid : entry.getValue()) {
				if (!users.contains(uid)) {
					users.add(uid);
				}
			}
		}

		return users;
	}

}
