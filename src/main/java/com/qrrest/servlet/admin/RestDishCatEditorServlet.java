package com.qrrest.servlet.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qrrest.model.RestDishCategoryTerm;
import com.qrrest.service.RestDishCatService;

@SuppressWarnings("serial")
public class RestDishCatEditorServlet extends BaseServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initBaseServlet(request, response).send400();
	}

	private RestDishCatService service = new RestDishCatService();

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initBaseServlet(request, response);
		if (Util.isStringEquals(request.getParameter("action"), "del")) {
			// del
			String id = request.getParameter("id");
			RestDishCategoryTerm cat = Util.isStringNullOrEmpty(id) ? null
					: service.getCatByCatId(Integer.parseInt(id));
			if (cat == null || cat.getRestId() != authService.getRestId()) {
				send500();
				return;
			}
			if (service.deleteCat(cat)) {
				ajax(new AjaxReturnType().setStatus(true)
						.setMessage("删除菜品分类成功！")
						.setRedirectUrl("./RestDishCatList.jsp")
						.setRedirectReplace(true));
			} else {
				ajax(new AjaxReturnType().setStatus(false).setMessage(
						"删除过程发生了错误，请确保菜品分类未在使用中"));
			}
		} else {
			String id = request.getParameter("sourceId");
			if (Util.isStringNullOrEmpty(id)) {
				// add
				RestDishCategoryTerm cat = new RestDishCategoryTerm();
				cat.setRestDishCatName(request.getParameter("name"));
				cat.setRestId(authService.getRestId());
				if (service.insertCat(cat)) {
					ajax(new AjaxReturnType().setStatus(true)
							.setMessage("新增菜品分类成功！")
							.setRedirectUrl("./RestDishCatEditor.jsp")
							.setRedirectReplace(true));
				} else {
					ajax(new AjaxReturnType().setStatus(false).setMessage(
							"新增菜品分类时发生了错误，请检查此分类名称是否已存在"));
				}
			} else {
				// edit
				RestDishCategoryTerm cat = service.getCatByCatId(Integer
						.parseInt(id));
				if (cat == null || cat.getRestId() != authService.getRestId()) {
					send500();
					return;
				}
				cat.setRestDishCatName(request.getParameter("name"));
				if (service.updateCat(cat)) {
					ajax(new AjaxReturnType().setStatus(true)
							.setMessage("编辑菜品分类成功！")
							.setRedirectUrl("./RestDishCatList.jsp")
							.setRedirectReplace(true));
				} else {
					ajax(new AjaxReturnType().setStatus(false).setMessage(
							"编辑菜品分类时发生了错误，请检查此分类名称是否已存在"));
				}
			}
		}
	}

}
