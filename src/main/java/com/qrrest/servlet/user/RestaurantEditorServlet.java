package com.qrrest.servlet.user;

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
		dbRest.setRest_name(request.getParameter("name"));
		dbRest.setRest_desc(request.getParameter("desc"));
		dbRest.setRest_type(request.getParameter("type"));
		dbRest.setRest_addr(request.getParameter("addr"));
		dbRest.setRest_tel(request.getParameter("tel"));
		if (new RestaurantService().updateRestaurant(dbRest,
				authService.getRestId())) {
			redirectAndAlertMsg("./RestaurantInfo.jsp", "编辑餐厅信息成功！");
		} else {
			redirectAndAlertMsg("./RestaurantEditor.jsp", "保存失败，请检查餐厅名称是否唯一");
		}
	}

}
