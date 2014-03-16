<%@page import="com.qrrest.vo.RestaurantVo"%>
<%@page import="com.qrrest.model.RestTypeTerm"%>
<%@page import="com.qrrest.service.AdminAuthService"%>
<%@page import="com.qrrest.service.RestaurantService"%>
<%@page import="com.qrrest.model.Restaurant"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
    RestaurantService _service = new RestaurantService();
    Restaurant _rest = _service.getRestById(new AdminAuthService(request.getSession()).getRestId());
    List<RestTypeTerm> _typeList = _service.getAllTypes();
%>

<jsp:include page="_PageHeader_.jsp">
    <jsp:param name="_PageName_" value="page-restaurant-editor" />
    <jsp:param name="_ActionDesc_" value="编辑;餐厅信息" />
</jsp:include>

<div class="container">
            <div class="row">
                <div>
                    <ol class="breadcrumb">
                        <li><a href="./Service.jsp">服务</a></li>
                        <li><a href="./RestaurantInfo.jsp">餐厅信息</a>
                        <li class="active">编辑</li>
                    </ol>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row model model-restaurant-editor" role="main">
                <div class="col-xs-9 model-container-left">
                    <div class="panel">
                        <form class="form-horizontal panel-body module-exValidate" action="RestaurantEditor.do" method="post" role="form" data-ajaxform="true">
                            <div class="form-group">
                                <label class="control-label col-xs-2">餐厅名称</label>
                                <div class="col-xs-10">
                                    <input class="form-control" name="name" type="text" placeholder="输入餐厅名称" value="<%=_rest.getRestName()%>" />
                                    <span class="help-block">填写具有唯一性的餐桌名称</span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-xs-2">餐厅描述</label>
                                <div class="col-xs-10">
                                    <textarea class="form-control" name="desc" style="resize: vertical;" placeholder="输入餐厅描述"><%=_rest.getRestDesc()%></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-xs-2">主营菜品</label>
                                <div class="col-xs-10">
                                    <select class="form-control" name="type">
                                        <%
                                        int _sourceTypeId = _rest.getRestTypeId();
                                        %>
                                        <% for(RestTypeTerm _type : _typeList) { %>
                                        <option value="<%=_type.getRestTypeId()%>"<%= _sourceTypeId == _type.getRestTypeId() ? " selected=\"selected\"" : "" %>><%=_type.getRestTypeName()%></option>
                                        <% } %>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-xs-2">餐厅地址</label>
                                <div class="col-xs-10">
                                    <textarea class="form-control" name="addr" style="resize: vertical;" placeholder="输入餐厅地址"><%=_rest.getRestAddr()%></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-xs-2">联系电话</label>
                                <div class="col-xs-10">
                                    <input class="form-control" name="tel" type="text" placeholder="输入联系电话" value="<%=_rest.getRestTel()%>" />
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-offset-2 col-xs-6">
                                    <button class="model-btn-submit btn" type="submit">保存</button>
                                    <a class="model-btn-back btn" href="#" data-exaele="goback">返回</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="col-xs-3 model-container-right">
                    <div>
                        <blockquote>
                            <p>在此页面编辑餐厅信息</p>
                            <small>主营菜品指餐厅主要营业菜品的类型</small>
                        </blockquote>
                    </div>
                </div>
            </div>
        </div>

<jsp:include page="_PageFooter_.jsp">
    <jsp:param name="_AdditionScripts_" value="./../AdminPublic/Validation/jquery.validate.min.js" />
</jsp:include>