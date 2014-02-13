package com.qrrest.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qrrest.model.Table;
import com.qrrest.service.TableService;

@SuppressWarnings("serial")
public class TableEditorServlet extends BaseServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initBaseServlet(request, response).send400();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initBaseServlet(request, response);
		String paramAction = request.getParameter("action");
		String paramSourceId = request.getParameter("sourceId");
		String paramName = request.getParameter("name");
		String paramSort = request.getParameter("sort");
		// add
		if (paramSourceId == null) {
			Table table = new Table();
			table.setTable_name(paramName);
			table.setTable_sort(Integer.parseInt(paramSort));
			table.setRest_id(authService.getRestId());
			if (new TableService().insertTable(table, authService.getRestId())) {
				redirectAndAlertMsg("./TableEditor.jsp", "新增餐桌成功");
			} else {
				redirectAndAlertMsg("./TableEditor.jsp",
						"新增餐桌时发生错误，请检查餐桌名是否已被使用");
			}
		}
		// del
		else if (Util.isStringEquals(paramAction, "del")) {
			if (new TableService().deleteTable(Long.parseLong(paramSourceId),
					authService.getRestId())) {
				redirectAndAlertMsg("./TableList.jsp", "删除餐桌成功！");
			} else {
				redirectAndAlertMsg("./TableList.jsp", "删除过程发生了错误，请确保餐桌未在使用中");
			}
		}
		// edit
		else {
			Table table = new Table();
			table.setTable_id(Long.parseLong(paramSourceId));
			table.setTable_name(paramName);
			table.setTable_sort(Integer.parseInt(paramSort));
			table.setRest_id(authService.getRestId());
			if (new TableService().updateTable(table, authService.getRestId())) {
				redirectAndAlertMsg("./TableList.jsp", "编辑餐桌成功！");
			} else {
				redirectAndAlertMsg("./TableEditor.jsp?id=" + paramSourceId,
						"保存编辑时发生了错误，请检查餐桌名是否已被使用");
			}
		}
	}

}
