<%@page import="com.qrrest.service.UserAuthService"%>
<%@page import="com.qrrest.service.RestaurantService"%>
<%@page import="com.qrrest.model.Restaurant"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
Restaurant _rest = new RestaurantService().getRestById(new UserAuthService(request.getSession()).getRestId());
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
                        <form class="form-horizontal panel-body module-exValidate" action="RestaurantEditor.do" method="post" role="form">
                            <div class="form-group">
                                <label class="control-label col-xs-2">餐厅名称</label>
                                <div class="col-xs-10">
                                    <input class="form-control" name="name" type="text" placeholder="输入餐厅名称" value="<%=_rest.getRest_name()%>" />
                                    <span class="help-block">填写具有唯一性的餐桌名称</span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-xs-2">餐厅描述</label>
                                <div class="col-xs-10">
                                    <textarea class="form-control" name="desc" style="resize: vertical;" placeholder="输入餐厅描述"><%=_rest.getRest_desc()%></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-xs-2">主营菜品</label>
                                <div class="col-xs-10">
                                    <select class="form-control" name="type">
                                        <% String __type = _rest.getRest_type(); %>
                                        <option value="滇菜"<%= "滇菜".equals(__type) ? " selected=\"selected\"" : "" %>>滇菜</option>
                                        <option value="川菜"<%= "川菜".equals(__type) ? " selected=\"selected\"" : "" %>>川菜</option>
                                        <option value="西餐"<%= "西餐".equals(__type) ? " selected=\"selected\"" : "" %>>西餐</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-xs-2">餐厅地址</label>
                                <div class="col-xs-10">
                                    <textarea class="form-control" name="addr" style="resize: vertical;" placeholder="输入餐厅地址"><%=_rest.getRest_addr()%></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-xs-2">联系电话</label>
                                <div class="col-xs-10">
                                    <input class="form-control" name="tel" type="text" placeholder="输入联系电话" value="<%=_rest.getRest_tel()%>" />
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-offset-2 col-xs-6">
                                    <button class="model-btn-submit btn" type="submit">保存</button>
                                    <a class="model-btn-back btn" href="./RestaurantInfo.jsp">返回</a>
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
    <jsp:param name="_AdditionScripts_" value="./../UserPublic/Validation/jquery.validate.min.js" />
</jsp:include>