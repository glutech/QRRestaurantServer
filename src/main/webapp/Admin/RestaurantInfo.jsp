<%@page import="com.qrrest.servlet.admin.Util"%>
<%@page import="com.qrrest.model.RestTypeTerm"%>
<%@page import="com.qrrest.model.Restaurant"%>
<%@page import="com.qrrest.vo.RestaurantVo"%>
<%@page import="com.qrrest.service.AdminAuthService"%>
<%@page import="com.qrrest.service.RestaurantService"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
    RestaurantService _service = new RestaurantService();
    RestaurantVo _restVo = _service.getRestVoByRestId(new AdminAuthService(request.getSession()).getRestId());
%>

<jsp:include page="_PageHeader_.jsp">
    <jsp:param name="_PageName_" value="page-restaurant-info" />
    <jsp:param name="_ActionDesc_" value="餐厅信息" />
</jsp:include>

<div class="container">
            <div class="row">
                <div>
                    <ol class="breadcrumb">
                        <li><a href="./Service.jsp">服务</a></li>
                        <li class="active">餐厅信息</li>
                    </ol>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row model model-restaurant-info" role="main">
                <div class="col-xs-9 model-container-left">
                    <div class="panel">
                        <form class="form-horizontal panel-body module-exValidate" action="" method="get" role="form">
                            <div class="form-group">
                                <label class="control-label col-xs-2">餐厅名称</label>
                                <div class="col-xs-10">
                                    <p class="form-control-static"><%=_restVo.getRest().getRestName()%></p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-xs-2">餐厅描述</label>
                                <div class="col-xs-10">
                                    <p class="form-control-static"><%=Util.text2Textarea(_restVo.getRest().getRestDesc())%></p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-xs-2">主营菜品</label>
                                <div class="col-xs-10">
                                    <p class="form-control-static"><%=_restVo.getType().getRestTypeName()%></p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-xs-2">餐厅地址</label>
                                <div class="col-xs-10">
                                    <p class="form-control-static"><%=_restVo.getRest().getRestAddr()%></p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-xs-2">联系电话</label>
                                <div class="col-xs-10">
                                    <p class="form-control-static"><%=_restVo.getRest().getRestTel()%></p>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="col-xs-3 model-container-right">
                    <div>
                        <blockquote>
                            <p>在此页面查看餐厅信息</p>
                            <small>点击修改按钮编辑信息</small>
                            <small>主营菜品指餐厅主要营业菜品的类型</small>
                        </blockquote>
                        <a class="btn" href="./RestaurantEditor.jsp" role="button">编辑餐厅信息</a>
                    </div>
                </div>
            </div>
        </div>

<jsp:include page="_PageFooter_.jsp" />