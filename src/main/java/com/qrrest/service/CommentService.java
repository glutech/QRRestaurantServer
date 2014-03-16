package com.qrrest.service;

import java.util.ArrayList;

import com.qrrest.dao2.CommentsDao;
import com.qrrest.model2.Comment;

public class CommentService {
	CommentsDao cdao;
	
	public CommentService(){
		cdao = new CommentsDao();
	}
	
	public ArrayList<Comment> getCommentByDishId(String did){
		return cdao.getCommentByDishId(Long.valueOf(did));
	}
	
	public boolean createComment(Comment com){
		return cdao.insertComment(com);	
	}
}
