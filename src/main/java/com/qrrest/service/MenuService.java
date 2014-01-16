package com.qrrest.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.qrrest.dao.CmMap;
import com.qrrest.dao.DishesDao;
import com.qrrest.dao.MenuDishMap;
import com.qrrest.dao.MenusDao;
import com.qrrest.model.Dish;
import com.qrrest.model.Menu;
import com.qrrest.model.Restaurant;
import com.qrrest.vo.HistoryVo;
import com.qrrest.vo.MenuVo;
import com.qrrest.wsorder.WSVirtualOrder;

public class MenuService {
	MenusDao mdao;
	MenuDishMap mdmap;
	CmMap cmmap;
	DishesDao ddao;
	
	public MenuService() {
		mdao = new MenusDao();
		mdmap = new MenuDishMap();
		cmmap = new CmMap();
		ddao = new DishesDao();
	}
	
	public double calTotalPrice(List<Long> didlist){
		double result = 0.0;
		for(Long did : didlist){
			result += ddao.getPriceByDishId(did);
		}
		
		return result;
	}
	
//	public Menu createBookMenu(Menu menu, List<Long> dlist){
//		boolean flag = false;
//		menu = mdao.insertMenu(menu);
//		for(int i = 0; i < dlist.size(); i++){
//			flag = mdmap.insertMDEntry(menu.getMenu_id(), dlist.get(i));
//		}
//		if(!flag){
//			menu = null;
//		}
//		
//		return menu;
//	}
	
	public MenuVo createMenu(long t_id, long r_id, WSVirtualOrder wso){
		MenuVo mv = new MenuVo();
		
		Map<Long, Integer> dishes = wso.getDishesMap();
		List<Long> customers = wso.getCustomerIdList();
		List<Long> dids = new ArrayList<Long>();
		List<Dish> dishlist = new ArrayList<Dish>();
		
		Set<Map.Entry<Long, Integer>> set = dishes.entrySet(); 
		for (Iterator<Map.Entry<Long, Integer>> it = set.iterator(); it.hasNext();) {              
		    Map.Entry<Long, Integer> entry = (Map.Entry<Long, Integer>) it.next();  
		    dids.add(entry.getKey());
		}  	
		
		Menu m = new Menu();
		m.setMenu_price(calTotalPrice(dids));
		m.setMenu_status(1);
		m.setMenu_time((new Timestamp(System.currentTimeMillis())).toString());
		m.setMenu_type(0);
		m.setRest_id(r_id);
		m.setTable_id(t_id);
		
		m = mdao.insertMenu(m); 		
 
		for (Iterator<Map.Entry<Long, Integer>> it = set.iterator(); it.hasNext();) {              
		    Map.Entry<Long, Integer> entry = (Map.Entry<Long, Integer>) it.next();  
		    mdmap.insertMDEntry(m.getMenu_id(), entry.getKey(), entry.getValue(), ddao.getPriceByDishId(entry.getKey()));
		}  	
		
		for(Long cid:customers){
			cmmap.insertCMEntry(cid, m.getMenu_id());
		}
		
		mv.setMenu(m);
		for(Long did: dids){
			dishlist.add(ddao.getDishById(did));
		}
		mv.setDishes(dishlist);
		
		return mv;
	}
	
	public List<HistoryVo> getHistoryByCustomerId(long c_id){
		List<HistoryVo> list = new ArrayList<HistoryVo>();
		List<Long> idlist = cmmap.getMenusByCustomerId(c_id);
		
		for(Long mid:idlist){
			HistoryVo hv= new HistoryVo();
			hv.setMe(mdao.getMenuById(mid));
			Restaurant rest = mdao.getRestByMenuId(mid);
			hv.setRest_id(rest.getRest_id());
			hv.setRest_name(rest.getRest_name());
			list.add(hv);
		}
		
		return list;
	}
	
	public MenuVo getMenuVoByMenuId(long m_id){
		MenuVo mv = new MenuVo();
		ArrayList<Dish> dishes = new ArrayList<Dish>();
		Menu m = mdao.getMenuById(m_id);
		mv.setMenu(m);
		for(Dish dish:mdao.getDishesByMenuId(m_id)){
			dishes.add(dish);
		}
		mv.setDishes(dishes);
		
		return mv;
	}
	
	public boolean delMenuById(long cid, long mid){
		return cmmap.delCMEntry(cid, mid);
	}
}
