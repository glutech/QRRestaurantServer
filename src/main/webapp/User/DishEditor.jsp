<%@page import="com.qrrest.service.DishService"%>
<%@page import="com.qrrest.model.Dish"%>
<%@page import="com.qrrest.service.UserAuthService"%>
<%@page import="com.qrrest.service.CategoryService"%>
<%@page import="com.qrrest.model.Category"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String _sourceId = request.getParameter("id");
boolean _isActionNew = _sourceId == null;
String _actionText = _isActionNew ? "新增菜品" : "编辑菜品";
Dish _sourceDish = _isActionNew ? null : new DishService().getDishById(Long.parseLong(_sourceId));
long _auth_rest_id = new UserAuthService(request.getSession()).getRestId();
if(_sourceDish != null && (_sourceDish.getDish_id() == 0 || _sourceDish.getRest_id() != _auth_rest_id)) {
    response.setStatus(500);
    return;
}
%>

<% String _ActionDesc_ = _actionText + ";" + "菜品管理"; %>
<jsp:include page="_PageHeader_.jsp">
    <jsp:param name="_PageName_" value="page-dish-editor" />
    <jsp:param name="_ActionDesc_" value="<%=_ActionDesc_%>" />
</jsp:include>

<div class="container">
            <div class="row">
                <div>
                    <ol class="breadcrumb">
                        <li><a href="./Service.jsp">服务</a></li>
                        <li><a href="./DishList.jsp">菜品管理</a></li>
                        <li class="active"><%=_actionText%></li>
                    </ol>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row model model-dish-editor" role="main">
                <div class="col-xs-9 model-container-left">
                    <div class="panel">
                        <form class="form-horizontal panel-body module-exValidate" action="./DishEditor.do" method="post" role="form">
                            <%
                            if(!_isActionNew) {
                                out.print("<input name=\"sourceId\" type=\"hidden\" value=\"" + _sourceId +"\" />");
                            }
                            %>
	                        <div class="form-group">
	                            <label class="control-label col-xs-2">菜品名称</label>
	                            <div class="col-xs-10">
	                                <input class="form-control" name="name" type="text" placeholder="输入菜品名称" value="<%=_isActionNew ? "" : _sourceDish.getDish_name()%>" />
	                            </div>
	                        </div>
	                        <div class="form-group">
	                            <label class="control-label col-xs-2">菜品描述</label>
	                            <div class="col-xs-10">
	                                <textarea class="form-control" name="desc" style="resize: vertical;" placeholder="输入菜品描述"><%=_isActionNew ? "" : _sourceDish.getDish_desc()%></textarea>
	                            </div>
	                        </div>
	                        <div class="form-group">
	                            <label class="control-label col-xs-2">菜品类别</label>
	                            <div class="col-xs-10">
	                                <select class="form-control" name="category">
	                                   <% List<Category> categories = new CategoryService().getCatsByRestId(_auth_rest_id); %>
                                       <%for(Category category : categories) {%>
                                       <%if(!_isActionNew && category.getCat_id() == _sourceDish.getCat_id()) {%>
                                           <option value="<%=category.getCat_id()%>" selected="selected"><%=category.getCat_name()%></option>
                                       <%}else{%>
                                       <option value="<%=category.getCat_id()%>"><%=category.getCat_name()%></option>
	                                   <%}} %>
	                                </select>
	                            </div>
	                        </div>
	                        <div class="form-group">
	                            <label class="control-label col-xs-2">菜品价格</label>
	                            <div class="col-xs-10">
	                                <div class="input-group">
	                                    <span class="input-group-addon">¥</span>
	                                    <input class="form-control" name="price" type="text" placeholder="输入菜品价格" value="<%=_isActionNew ? "" : _sourceDish.getDish_price()%>" />
	                                </div>
	                            </div>
	                        </div>
	                        <div class="form-group">
	                            <label class="control-label col-xs-2">菜品标签</label>
	                            <div class="col-xs-10">
	                                <input class="form-control" name="tag" type="text" placeholder="输入菜品标签（可输入多个，以逗号或空格分隔）" value="<%=_isActionNew ? "" : _sourceDish.getDish_tag()%>" />
	                                <span class="help-block">使用标签标明菜品的一些特点，两个标签之间用逗号或空格隔开</span>
	                            </div>
	                        </div>
	                        <div class="form-group">
	                            <label class="control-label col-xs-2">菜品图片</label>
	                            <div class="col-xs-10">
	                                <div class="form-control model-upload">
	                                    <input name="pic" type="hidden" value="<%=_isActionNew ? "" : _sourceDish.getDish_pic()%>" />
	                                    <button type="button">选择图片</button>
	                                    <input type="file" accept="image/*" style="display:none;" />
	                                    <img />
	                                </div>
	                                <span class="help-block">上传一张用于展示的图片（不大于2MB）</span>
	                            </div>
	                        </div>
	                        <div class="form-group">
	                            <div class="col-xs-offset-2 col-xs-6">
	                                <button class="model-btn-submit btn" type="submit"><%=_isActionNew ? "新增" : "保存"%></button>
                                    <a class="model-btn-back btn" href="./DishList.jsp">返回</a>
	                            </div>
	                        </div>
	                    </form>
                    </div>
                </div>
                <div class="col-xs-3 model-container-right">
	                <div>
	                    <blockquote>
	                        <p>在此页<%=_isActionNew ? "添加新菜品" : "编辑菜品信息" %></p>
	                        <small>填写基础信息</small>
	                        <small>填写描述菜品特性的标签</small>
	                        <small>上传一张展示图片（不大于2MB）</small>
	                    </blockquote>
	                </div>
	            </div>
            </div>
        </div>

<jsp:include page="_PageFooter_.jsp">
    <jsp:param name="_AdditionScripts_" value="./../UserPublic/Validation/jquery.validate.min.js" />
</jsp:include>