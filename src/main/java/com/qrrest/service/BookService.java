package com.qrrest.service;

import java.util.ArrayList;
import java.util.List;

import com.qrrest.dao.BooksDao;
import com.qrrest.model.Book;
import com.qrrest.model.Menu;
import com.qrrest.vo.BookVo;
import com.qrrest.vo.DishVo;
import com.qrrest.vo.MenuVo;

public class BookService {
	BooksDao bdao;
	MenuService mservice;
	DishService dservice;
	CategoryService cservice;
	CommentService comservice;
	
	public BookService() {
		bdao = new BooksDao();
		mservice = new MenuService();
		dservice = new DishService();
		cservice = new CategoryService();
		comservice = new CommentService();
	}
	
	public BookVo createBook(Book b, Menu m, List<Long> dlist){
//		BookVo bookv = new BookVo();
//		MenuVo menuv = new MenuVo();
//		DishVo dishv = new DishVo();
//		ArrayList<DishVo> dishes= new ArrayList<DishVo>();
//		//组装DishVo
//		
//		
//		Menu newm = mservice.createBookMenu(m, dlist);
//		menuv.setMenu(newm);
//		for(int i = 0; i < dlist.size(); i ++){
//			dishv.setDish(dservice.getDishById(dlist.get(i)));
//			dishv.setCat(cservice.getCatByDishId(dlist.get(i)));
//			dishv.setComments(comservice.getCommentByDishId(String.valueOf(dlist.get(i))));
//			dishes.add(dishv);
//		}
//		menuv.setDishes(dishes);
//		
//		//根据数据库插入的menu新id给book赋值
//		b.setMenu_id(newm.getMenu_id());
//		bookv.setBook(bdao.insertBook(b));
//		bookv.setMenu(menuv);
		
		return null; //bookv;
	}
	
}
