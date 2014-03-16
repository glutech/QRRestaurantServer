<%@page import="com.qrrest.model.Table.TableStatusEnum"%>
<%@page import="com.qrrest.model.Table"%>
<%@page import="com.qrrest.service.TableService"%>
<%@page import="com.qrrest.service.AdminAuthService"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
    TableService _service = new TableService();
	List<Table> _tables = _service.getAllTablesByRestId(new AdminAuthService(request.getSession()).getRestId());
%>

<jsp:include page="_PageHeader_.jsp">
    <jsp:param name="_PageName_" value="page-table-list"/>
    <jsp:param name="_ActionDesc_" value="餐桌管理"/>
</jsp:include>

        <div class="container">
            <div class="row">
                <div>
                    <ol class="breadcrumb">
                        <li><a href="./Service.jsp">服务</a></li>
                        <li class="active">餐桌管理</li>
                    </ol>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row model model-table-list" role="main">
                <div class="col-xs-9 model-container-left">
                    <table class="table" data-tableautono="true" data-tableautono-class="model-no">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>餐桌名称</th>
                                <th>状态</th>
                                <th>排序因子</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                           
                            <%
                                                       	for(Table _table : _tables) {
                                                                                       TableStatusEnum _status = _table.getTableStatus();
                                                                                       out.println("<tr class=\"model-module model-module-" + _status.toString() + "\">");
                                                                                       out.println("<th><span class=\"model-no\"></span></th>");
                                                                                       out.println("<td><span class=\"model-name\">" + _table.getTableName() + "</span></td>");
                                                                                       out.println("<td><span class=\"model-status\">" + _table.getTableStatus().getNameZHCN() + "</span></td>");
                                                                                       out.println("<td><span class=\"model-sorter\">" + _table.getTableSort() + "</span></td>");
                                                                                       if(_status != TableStatusEnum.free && _status != TableStatusEnum.blocked) {
                                                                                           out.println("<td> - </td>");
                                                                                       } else {
                                                                                           out.println("<td><ul class=\"model-action\"><li><a href=\"./TableEditor.do?action=edit&id=" + _table.getTableId() + "\" data-ajaxtodo=\"true\">编辑</a></li><li><a href=\"./TableEditor.do?action=del&id=" + _table.getTableId() + "\" data-ajaxtodo=\"true\" data-ajaxtodo-warning=\"确定要删除此餐桌吗？\">删除</a></li></ul></td>");
                                                                                       }
                                                                                       out.println("</tr>");
                                                                                   }
                                                       %>
                        </tbody>
                    </table>
                </div>
                <div class="col-xs-3 model-container-right">
                    <div>
                        <blockquote>
                            <p>在此页面对餐桌进行管理操作</p>
                            <small>包括新增、修改、删除餐桌等操作</small>
                            <small>还可以通过排序因子调整餐桌的显示顺序</small>
                            <small>仅餐桌空闲或禁用时可进行编辑或删除操作</small>
                        </blockquote>
                        <a class="btn" href="./TableEditor.jsp" role="button">新增餐桌</a>
                    </div>
                </div>
            </div>
        </div>

<jsp:include page="_PageFooter_.jsp" />
