package com.qrrest.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.qrrest.dao.RcommentsDao;
import com.qrrest.model.Rcomment;

public class RcommentService {
	RcommentsDao rdao;
	
	public RcommentService(){
		rdao = new RcommentsDao();
	}
	
	public boolean createRcomment(Rcomment rco){
		return rdao.insertRcomment(rco);

	}
	
	public Rcomment getRcommentByRestId(long id){
		return rdao.getCommentById(id);
	}

}
