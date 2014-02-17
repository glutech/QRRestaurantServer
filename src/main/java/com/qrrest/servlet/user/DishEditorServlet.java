package com.qrrest.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qrrest.model.Dish;
import com.qrrest.service.DishService;
import com.qrrest.service.QiniuUploaderService;

@SuppressWarnings("serial")
public class DishEditorServlet extends BaseServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initBaseServlet(request, response);
		String paramId = request.getParameter("id");
		if (Util.isStringEquals("del", request.getParameter("action"))
				&& !Util.isStringNullOrEmpty(paramId)) {
			if (new DishService().deleteDish(Long.parseLong(paramId),
					authService.getRestId())) {
				redirectAndAlertMsg("./DishList.jsp", "删除菜品成功！");
			} else {
				redirectAndAlertMsg("./DishList.jsp", "删除过程发生了错误，请确保菜品未在使用中");
			}
		} else {
			send400();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initBaseServlet(request, response);
		String paramSourceId = request.getParameter("sourceId");
		String paramName = request.getParameter("name");
		String paramDesc = request.getParameter("desc");
		String paramCategory = request.getParameter("category");
		String paramPrice = request.getParameter("price");
		String paramTag = request.getParameter("tag");
		String paramPic = request.getParameter("pic");
		// add
		if (paramSourceId == null) {
			Dish dish = new Dish();
			dish.setDish_name(paramName);
			dish.setDish_desc(paramDesc);
			dish.setCat_id(Long.parseLong(paramCategory));
			dish.setDish_price(Double.parseDouble(paramPrice));
			dish.setDish_tag(paramTag);
			dish.setDish_pic(QiniuUploaderService.getUrlByKey(paramPic));
			dish.setRest_id(authService.getRestId());
			if (new DishService().insertDish(dish, authService.getRestId())) {
				ajaxReturn(true, "新增菜品成功", "./DishEditor.jsp");
			} else {
				ajaxReturn(false, "新增菜品时发生错误，请检查菜品名称是否已被使用",
						"./TableEditor.jsp");
			}
		}
		// edit
		else {
			Dish dish = new DishService().getDishById(Long
					.parseLong(paramSourceId));
			dish.setDish_name(paramName);
			dish.setDish_desc(paramDesc);
			dish.setCat_id(Long.parseLong(paramCategory));
			dish.setDish_price(Double.parseDouble(paramPrice));
			dish.setDish_tag(paramTag);
			if (!paramPic.startsWith("http://")) {
				dish.setDish_pic(QiniuUploaderService.getUrlByKey(paramPic));
			} else {
				// TODO: 应实现完整的图片删除逻辑，包括上传但未使用的临时文件
			}
			dish.setRest_id(authService.getRestId());
			if (new DishService().updateDish(dish, authService.getRestId())) {
				ajaxReturn(true, "菜品编辑成功！", "./DishList.jsp");
			} else {
				ajaxReturn(false, "保存编辑时发生了错误，请检查菜品名是否已被使用、菜品是否在使用中",
						"./DishEditor.jsp?id=" + paramSourceId);
			}
		}
	}
}