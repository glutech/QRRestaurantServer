package com.qrrest.vo;

import com.qrrest.model.RestTypeTerm;
import com.qrrest.model.Restaurant;

public class RestaurantVo {

	private Restaurant rest;
	private RestTypeTerm type;

	public Restaurant getRest() {
		return rest;
	}

	public void setRest(Restaurant rest) {
		this.rest = rest;
	}

	public RestTypeTerm getType() {
		return type;
	}

	public void setType(RestTypeTerm type) {
		this.type = type;
	}

}
