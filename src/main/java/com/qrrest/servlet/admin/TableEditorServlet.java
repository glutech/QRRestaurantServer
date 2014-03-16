package com.qrrest.servlet.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qrrest.model.Table;
import com.qrrest.model.Table.TableStatusEnum;
import com.qrrest.service.TableService;

@SuppressWarnings("serial")
public class TableEditorServlet extends BaseServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initBaseServlet(request, response).send400();
	}

	private TableService service = new TableService();

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initBaseServlet(request, response);
		if (Util.isStringEquals(request.getParameter("action"), "edit")) {
			// check edit
			Table table = service.getTableOnRequest(request.getParameter("id"),
					authService.getRestId());
			if (table == null) {
				send500();
				return;
			}
			if (!service.checkUpdateStatus(table.getTableStatus())) {
				ajax(new AjaxReturnType()
						.setStatus(false)
						.setMessage(
								"餐桌处于" + table.getTableStatus().getNameZHCN()
										+ "状态，不允许编辑").setReload(true));
				return;
			} else {
				ajax(new AjaxReturnType()
						.setRedirectUrl("./TableEditor.jsp?id="
								+ table.getTableId()));
			}
		} else if (Util.isStringEquals(request.getParameter("action"), "del")) {
			// del
			Table table = service.getTableOnRequest(request.getParameter("id"),
					authService.getRestId());
			if (table == null) {
				send500();
				return;
			}
			if (!service.checkDeleteStatus(table.getTableStatus())) {
				ajax(new AjaxReturnType()
						.setStatus(false)
						.setMessage(
								"餐桌处于" + table.getTableStatus().getNameZHCN()
										+ "状态，不允许删除")
						.setRedirectUrl("./TableList.jsp")
						.setRedirectReplace(true));
				return;
			}
			if (service.deleteTable(table)) {
				ajax(new AjaxReturnType().setStatus(true).setMessage("删除餐桌成功！")
						.setRedirectUrl("./TableList.jsp")
						.setRedirectReplace(true));
			} else {
				System.err.println("删除餐桌逻辑异常");
				ajax(new AjaxReturnType().setStatus(false).setMessage(
						"删除过程发生了错误，请确保餐桌未在使用中"));
			}
		} else {
			String id = request.getParameter("sourceId");
			if (Util.isStringNullOrEmpty(id)) {
				// add
				Table table = new Table();
				table.setTableName(request.getParameter("name"));
				table.setTableSort(Integer.parseInt(request
						.getParameter("sort")));
				table.setRestId(authService.getRestId());
				if (service.insertTable(table)) {
					ajax(new AjaxReturnType().setStatus(true)
							.setMessage("新增餐桌成功！")
							.setRedirectUrl("./TableEditor.jsp")
							.setRedirectReplace(true));
				} else {
					ajax(new AjaxReturnType().setStatus(false).setMessage(
							"新增餐桌时发生错误，请检查餐桌名是否已被使用"));
				}
			} else {
				// edit
				Table table = service.getTableOnRequest(
						request.getParameter("id"), authService.getRestId());
				if (table == null) {
					send500();
					return;
				}
				table.setTableName(request.getParameter("name"));
				table.setTableSort(Integer.parseInt(request
						.getParameter("sort")));
				if (service.checkUpdateStatus(table.getTableStatus())) {
					if (Util.isStringNullOrEmpty(request
							.getParameter("disable"))) {
						table.setTableStatus(TableStatusEnum.free);
					} else {
						table.setTableStatus(TableStatusEnum.blocked);
					}
				} else {
					ajax(new AjaxReturnType().setStatus(false).setMessage(
							"餐桌处于" + table.getTableStatus().getNameZHCN()
									+ "状态，不允许编辑"));
					return;
				}
				if (service.updateTable(table)) {
					ajax(new AjaxReturnType().setStatus(true)
							.setMessage("编辑餐桌成功！")
							.setRedirectUrl("./TableList.jsp")
							.setRedirectReplace(true));
				} else {
					ajax(new AjaxReturnType().setStatus(false).setMessage(
							"保存编辑时发生了错误，请检查餐桌名是否已被使用"));
				}
			}
		}
	}
}
