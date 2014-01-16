package com.qrrest.model;

import java.util.Date;

public class Rcomment {
	private long rcomment_id;
	private String rcomment_content;
	private String rcomment_date;
	private long rest_id;
	private long customer_id;
	private int rest_rate;
	
	public long getRcomment_id() {
		return rcomment_id;
	}
	public void setRcomment_id(long rcomment_id) {
		this.rcomment_id = rcomment_id;
	}
	public String getRcomment_content() {
		return rcomment_content;
	}
	public void setRcomment_content(String rcomment_content) {
		this.rcomment_content = rcomment_content;
	}
	public String getRcomment_date() {
		return rcomment_date;
	}
	public void setRcomment_date(String rcomment_date) {
		this.rcomment_date = rcomment_date;
	}
	public long getRest_id() {
		return rest_id;
	}
	public void setRest_id(long rest_id) {
		this.rest_id = rest_id;
	}
	public long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}
	public int getRest_rate() {
		return rest_rate;
	}
	public void setRest_rate(int rest_rate) {
		this.rest_rate = rest_rate;
	}
}
