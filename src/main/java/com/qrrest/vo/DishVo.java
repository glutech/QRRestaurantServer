package com.qrrest.vo;

import java.util.ArrayList;

import com.qrrest.model.Category;
import com.qrrest.model.Comment;
import com.qrrest.model.Dish;

public class DishVo {
	Dish dish;
	Category cat;
	//ArrayList<Comment> comments;
	
	public Dish getDish() {
		return dish;
	}
	public void setDish(Dish dish) {
		this.dish = dish;
	}
	public Category getCat() {
		return cat;
	}
	public void setCat(Category cat) {
		this.cat = cat;
	}
//	public ArrayList<Comment> getComments() {
//		return comments;
//	}
//	public void setComments(ArrayList<Comment> comments) {
//		this.comments = comments;
//	}
}
