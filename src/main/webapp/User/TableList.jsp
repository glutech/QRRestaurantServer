<%@page import="com.qrrest.model.Table"%>
<%@page import="com.qrrest.service.TableService"%>
<%@page import="com.qrrest.service.UserAuthService"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
List<Table> _tables = new TableService().getAllTablesByRestId(new UserAuthService(request.getSession()).getRestId()+"");

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
                    <table class="table">
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
                            for(Table table : _tables) {
                                boolean isInService = 1 == table.getTable_status();
                                out.println("<tr class=\"model-module model-module-" + (isInService  ? "free" : "service") + "\">");
                                out.println("<th><span class=\"model-no\"></span></th>");
                                out.println("<td><span class=\"model-name\">" + table.getTable_name() + "</span></td>");
                                out.println("<td><span class=\"model-status\"></span></td>");
                                out.println("<td><span class=\"model-sorter\">" + table.getTable_sort() + "</span></td>");
                                if(!isInService) {
                                    out.println("<td> - </td>");
                                } else {
                                    out.println("<td><ul class=\"model-action\"><li><a href=\"./TableEditor.jsp?id=" + table.getTable_id() + "\">编辑</a></li><li><a href=\"./TableEditor.do?action=del&id=" + table.getTable_id() + "\">删除</a></li></ul></td>");
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
                            <small>仅餐桌空闲时可进行编辑或删除操作</small>
                        </blockquote>
                        <a class="btn" href="./TableEditor.jsp" role="button">新增餐桌</a>
                    </div>
                </div>
            </div>
        </div>

<jsp:include page="_PageFooter_.jsp" />
