package com.qrrest.servlet.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qrrest.model.Dish;
import com.qrrest.model.Dish.DishStatusEnum;
import com.qrrest.model.RestDishCategoryTerm;
import com.qrrest.service.DishService;
import com.qrrest.service.DishTagService;
import com.qrrest.service.QiniuUploaderService;
import com.qrrest.service.RestDishCatService;

@SuppressWarnings("serial")
public class DishEditorServlet extends BaseServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initBaseServlet(request, response).send400();
	}

	private DishService service = new DishService();

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initBaseServlet(request, response);
		if (Util.isStringEquals(request.getParameter("action"), "checkedit")) {
			Dish dish = service.getDishOnRequest(request.getParameter("id"),
					authService.getRestId());
			if (dish == null) {
				send500();
				return;
			}
			if (!service.checkUpdateStatus(dish.getDishStatus())) {
				ajax(new AjaxReturnType()
						.setStatus(false)
						.setMessage(
								"菜品处于" + dish.getDishStatus().getNameZHCN()
										+ "状态，不允许编辑").setReload(true));
				return;
			} else {
				ajax(new AjaxReturnType().setRedirectUrl("./DishEditor.jsp?id="
						+ dish.getDishId()));
			}
		} else if (Util.isStringEquals(request.getParameter("action"), "del")) {
			Dish dish = service.getDishOnRequest(request.getParameter("id"),
					authService.getRestId());
			if (dish == null) {
				send500();
				return;
			}
			if (!service.checkDeleteStatus(dish.getDishStatus())) {
				ajax(new AjaxReturnType()
						.setStatus(false)
						.setMessage(
								"菜品处于" + dish.getDishStatus().getNameZHCN()
										+ "状态，不允许删除")
						.setRedirectUrl("./DishList.jsp")
						.setRedirectReplace(true));
				return;
			}
			if (service.deleteDish(dish)) {
				ajax(new AjaxReturnType().setStatus(true).setMessage("删除菜品成功！")
						.setRedirectUrl("./DishList.jsp")
						.setRedirectReplace(true));
			} else {
				System.err.println("删除菜品逻辑异常");
				ajax(new AjaxReturnType().setStatus(false)
						.setMessage("删除菜品时发生错误，请确保菜品未在使用中")
						.setRedirectUrl("./DishList.jsp")
						.setRedirectReplace(true));
			}
		} else {
			String id = request.getParameter("sourceId");
			if (Util.isStringNullOrEmpty(id)) {
				// add
				// 检查额外参数
				RestDishCategoryTerm cat = new RestDishCatService()
						.getCatByCatId(Integer.parseInt(request
								.getParameter("category")));
				if (cat == null || cat.getRestId() != authService.getRestId()) {
					send500();
					return;
				}
				String[] tags;
				try {
					tags = request.getParameter("tag")
							.replaceAll("[\\s\\,\\;，；]+", " ").split(" ");
				} catch (Exception e) {
					send500();
					return;
				}
				// 常规添加
				Dish dish = new Dish();
				dish.setDishName(request.getParameter("name"));
				dish.setDishPrice(Double.parseDouble(request
						.getParameter("price")));
				dish.setDishPic(QiniuUploaderService.getUrlByKey(request
						.getParameter("pic")));
				dish.setDishDesc(request.getParameter("desc"));
				dish.setRestDishCatId(cat.getRestDishCatId());
				Dish newDish = service.insertAndGetDish(dish);
				if (newDish != null) {
					new DishTagService().updateDishTags(newDish.getDishId(),
							tags);
					ajax(new AjaxReturnType().setStatus(true)
							.setMessage("新增菜品成功！")
							.setRedirectUrl("./DishEditor.jsp")
							.setRedirectReplace(true));
				} else {
					ajax(new AjaxReturnType().setStatus(false).setMessage(
							"新增菜品时发生错误，请检查菜品名称是否已被使用"));
				}
			} else {
				// edit
				Dish dish = service.getDishOnRequest(id,
						authService.getRestId());
				// 检查额外参数
				if (dish == null) {
					send500();
					return;
				}
				RestDishCategoryTerm cat = new RestDishCatService()
						.getCatByCatId(Integer.parseInt(request
								.getParameter("category")));
				if (cat == null || cat.getRestId() != authService.getRestId()) {
					send500();
					return;
				}
				String[] tags;
				try {
					tags = request.getParameter("tag")
							.replaceAll("[\\s\\,\\;，；]+", " ").split(" ");
				} catch (Exception e) {
					send500();
					return;
				}
				if (service.checkUpdateStatus(dish.getDishStatus())) {
					if (Util.isStringNullOrEmpty(request
							.getParameter("disable"))) {
						dish.setDishStatus(DishStatusEnum.normal);
					} else {
						dish.setDishStatus(DishStatusEnum.blocked);
					}
				} else {
					ajax(new AjaxReturnType()
							.setStatus(false)
							.setMessage(
									"菜品处于" + dish.getDishStatus().getNameZHCN()
											+ "状态，不允许进行编辑")
							.setRedirectUrl("./DishList.jsp")
							.setRedirectReplace(true));
					return;
				}
				// 常规修改
				dish.setDishName(request.getParameter("name"));
				dish.setDishPrice(Double.parseDouble(request
						.getParameter("price")));
				if (!request.getParameter("pic").startsWith("http://")) {
					dish.setDishPic(QiniuUploaderService.getUrlByKey(request
							.getParameter("pic")));
				} else {
					// TODO: 应实现完整的图片删除逻辑，包括上传但未使用的临时文件
				}
				dish.setDishDesc(request.getParameter("desc"));
				dish.setRestDishCatId(cat.getRestDishCatId());
				if (service.updateDish(dish)) {
					new DishTagService().updateDishTags(dish.getDishId(), tags);
					ajax(new AjaxReturnType().setStatus(true)
							.setMessage("编辑菜品成功！")
							.setRedirectUrl("./DishList.jsp")
							.setRedirectReplace(true));
				} else {
					ajax(new AjaxReturnType().setStatus(false).setMessage(
							"保存编辑时发生了错误，请检查菜品名是否已存在"));
				}
			}
		}
	}
}