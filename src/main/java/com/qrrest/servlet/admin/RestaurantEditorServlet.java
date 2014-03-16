package com.qrrest.servlet.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qrrest.model.Restaurant;
import com.qrrest.service.RestaurantService;

@SuppressWarnings("serial")
public class RestaurantEditorServlet extends BaseServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initBaseServlet(request, response).send400();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initBaseServlet(request, response);
		Restaurant dbRest = new RestaurantService().getRestById(authService
				.getRestId());
		dbRest.setRestName(request.getParameter("name"));
		dbRest.setRestDesc(request.getParameter("desc"));
		dbRest.setRestTypeId(Integer.parseInt(request.getParameter("type")));
		dbRest.setRestAddr(request.getParameter("addr"));
		dbRest.setRestTel(request.getParameter("tel"));
		if (new RestaurantService().updateRestaurant(dbRest,
				authService.getRestId())) {
			ajax(new AjaxReturnType().setStatus(true).setMessage("编辑餐厅信息成功！")
					.setRedirectUrl("./RestaurantInfo.jsp")
					.setRedirectReplace(true));
		} else {
			ajax(new AjaxReturnType().setStatus(false).setMessage(
					"保存失败，请检查餐厅名称是否唯一"));
		}
	}

}
