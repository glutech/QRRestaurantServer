package com.qrrest.model;

import java.sql.Date;

public class CommentDish {

	private int cmtId;
	private String cmtContent;
	private Date cmtDate;
	private short cmtRate;
	private int userId;
	private int dishId;

	public int getCmtId() {
		return cmtId;
	}

	public void setCmtId(int cmtId) {
		this.cmtId = cmtId;
	}

	public String getCmtContent() {
		return cmtContent;
	}

	public void setCmtContent(String cmtContent) {
		this.cmtContent = cmtContent;
	}

	public Date getCmtDate() {
		return cmtDate;
	}

	public void setCmtDate(Date cmtDate) {
		this.cmtDate = cmtDate;
	}

	public short getCmtRate() {
		return cmtRate;
	}

	public void setCmtRate(short cmtRate) {
		this.cmtRate = cmtRate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getDishId() {
		return dishId;
	}

	public void setDishId(int dishId) {
		this.dishId = dishId;
	}

}
