package com.qrrest.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qrrest.model.Category;
import com.qrrest.service.CategoryService;

@SuppressWarnings("serial")
public class CategoryEditorServlet extends BaseServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initBaseServlet(request, response);
		String paramId = request.getParameter("id");
		if (Util.isStringEquals("del", request.getParameter("action"))
				&& !Util.isStringNullOrEmpty(paramId)) {
			if (new CategoryService().deleteCategory(Long.parseLong(paramId),
					authService.getRestId())) {
				redirectAndAlertMsg("./CategoryList.jsp", "删除菜品分类成功！");
			} else {
				redirectAndAlertMsg("./CategoryList.jsp",
						"删除过程发生了错误，请确保菜品分类未在使用中");
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
		// add
		if (paramSourceId == null) {
			Category category = new Category();
			category.setCat_name(paramName);
			category.setRest_id(authService.getRestId());
			if (new CategoryService().insertCategory(category,
					authService.getRestId())) {
				redirectAndAlertMsg("./CategoryEditor.jsp", "新增菜品分类成功！");
			} else {
				redirectAndAlertMsg("./CategoryEditor.jsp",
						"新增菜品分类时发生错误，请检查菜品分类名是否已被使用");
			}
		}
		// edit
		else {
			Category category = new Category();
			category.setCat_id(Long.parseLong(paramSourceId));
			category.setCat_name(paramName);
			category.setRest_id(authService.getRestId());
			if (new CategoryService().updateCategory(category)) {
				redirectAndAlertMsg("./CategoryList.jsp", "编辑菜品分类成功！");
			} else {
				redirectAndAlertMsg("./CategoryEditor.jsp?id=" + paramSourceId,
						"保存编辑时发生了错误，请检查菜品分类名是否已被使用");
			}
		}
	}

}
