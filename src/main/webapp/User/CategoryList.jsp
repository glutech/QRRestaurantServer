<%@page import="com.qrrest.service.CategoryService"%>
<%@page import="com.qrrest.model.Category"%>
<%@page import="com.qrrest.service.UserAuthService"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
List<Category> _categories = new CategoryService().getCatsByRestId(new UserAuthService(request.getSession()).getRestId());
%>

<jsp:include page="_PageHeader_.jsp">
    <jsp:param name="_PageName_" value="page-category-list"/>
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
            <div class="row model model-category-list" role="main">
                <div class="col-xs-9 model-container-left">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>分类名称</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                            for(Category cat : _categories) {
                                out.println("<tr class=\"model-module\">");
                                out.println("<td><span class=\"model-no\"><span></td>");
                                out.println("<td><span class=\"model-name\">" + cat.getCat_name() + "</span></td>");
                                out.println("<td><ul class=\"model-action\"><li><a href=\"./CategoryEditor.jsp?id=" + cat.getCat_id() + "\">编辑</a></li><li><a href=\"./CategoryEditor.do?action=del&id=" + cat.getCat_id() + "\">删除</a></li></ul></td>");
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
                        <a class="btn" href="./CategoryEditor.jsp" role="button">新增分类</a>
                    </div>
                </div>
            </div>
        </div>

<jsp:include page="_PageFooter_.jsp" />
