<%@page import="com.qrrest.service.RestDishCatService"%>
<%@page import="com.qrrest.model.RestDishCategoryTerm"%>
<%@page import="com.qrrest.service.AdminAuthService"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
    RestDishCatService _service = new RestDishCatService();
    List<RestDishCategoryTerm> _cats = _service.getCatsByRestId(new AdminAuthService(request.getSession()).getRestId());
%>

<jsp:include page="_PageHeader_.jsp">
    <jsp:param name="_PageName_" value="page-restdishcat-list"/>
    <jsp:param name="_ActionDesc_" value="菜品分类管理;菜品管理"/>
</jsp:include>

        <div class="container">
            <div class="row">
                <div>
                    <ol class="breadcrumb">
                        <li><a href="./Service.jsp">服务</a></li>
                        <li><a href="./DishList.jsp">菜品管理</a></li>
                        <li class="active">菜品分类管理</li>
                    </ol>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row model model-restdishcat-list" role="main">
                <div class="col-xs-9 model-container-left">
                    <table class="table" data-tableautono="true" data-tableautono-class="model-no">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>分类名称</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                            for(RestDishCategoryTerm _cat : _cats) {
                                out.println("<tr class=\"model-module\">");
                                out.println("<td><span class=\"model-no\"><span></td>");
                                out.println("<td><span class=\"model-name\">" + _cat.getRestDishCatName() + "</span></td>");
                                out.println("<td><ul class=\"model-action\"><li><a href=\"./RestDishCatEditor.jsp?id=" + _cat.getRestDishCatId() + "\">编辑</a></li><li><a href=\"./RestDishCatEditor.do?action=del&id=" + _cat.getRestDishCatId() + "\" data-ajaxtodo=\"true\" data-ajaxtodo-warning=\"确定要删除此菜品分类\">删除</a></li></ul></td>");
                                out.println("</tr>");
                            }
                            %>
                        </tbody>
                    </table>
                </div>
                <div class="col-xs-3 model-container-right">
                    <div>
                        <blockquote>
                            <p>在此页面对菜品分类进行管理操作</p>
                            <small>包括新增、修改、删除菜品分类等操作</small>
                            <small>仅菜品分类未在使用时可进行删除操作</small>
                        </blockquote>
                        <a class="btn" href="./RestDishCatEditor.jsp" role="button">新增分类</a>
                    </div>
                </div>
            </div>
        </div>

<jsp:include page="_PageFooter_.jsp" />
