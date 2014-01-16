package com.qrrest.service;

import java.util.ArrayList;

import com.qrrest.dao.CommentsDao;
import com.qrrest.model.Comment;

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
