<%@page import="com.qrrest.service.UserAuthService"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String _PageName_ = request.getParameter("_PageName_");
String[] _ActionDesc_ = request.getParameter("_ActionDesc_").split(";");
String _restName = "测试餐厅";
String _userNickname = new UserAuthService(request.getSession()).getAuthNickname();
String _activeAction = _ActionDesc_[_ActionDesc_.length-1];
%>

<!DOCTYPE html>
<html lang="zh-cn" id="<%=_PageName_%>">
    
    <head>
        <meta charset="UTF-8" />
        <title><%
            for(String action : _ActionDesc_) {
                out.print(action);
                out.print(" - ");
            }
            out.print(_restName);
        %></title>
        <link rel="stylesheet" href="./../UserPublic/common.min.css">
    </head>
    
    <body>
        <div class="navbar navbar-default navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button class="navbar-toggle" type="button" data-toggle="collapse"  data-target=".navbar-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand font-heiti"><%=_restName%></a>
                </div>
                <div class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">
                        <li<%=_activeAction.equals("服务") ? " class=\"active\"" : ""%>><a href="./Service.jsp"><span class="glyphicon glyphicon-cutlery"></span>服务</a></li>
                        <li<%=_activeAction.equals("预定") ? " class=\"active\"" : ""%>><a href="./page-reservation.html"><span class="glyphicon glyphicon-bookmark"></span>预定</a></li>
                        <li<%=_activeAction.equals("历史") ? " class=\"active\"" : ""%>><a href="#"><span class="glyphicon glyphicon-stats"></span>历史</a></li>
                        <li class="dropdown<%=(_activeAction.equals("餐厅管理") || _activeAction.equals("餐桌管理") || _activeAction.equals("菜品管理")) ? " active" : ""%>">
                            <a class="dropdown-toggle" href="#" data-toggle="dropdown"><span class="glyphicon glyphicon-cog"></span>管理<b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li<%=_activeAction.equals("餐厅管理") ? " class=\"active\"" : ""%>><a href="./RestaurantInfo.jsp">餐厅管理</a>
                                <li<%=_activeAction.equals("餐桌管理") ? " class=\"active\"" : ""%>><a href="./TableList.jsp">餐桌管理</a></li>
                                <li<%=_activeAction.equals("菜品管理") ? " class=\"active\"" : ""%>><a href="./DishList.jsp">菜品管理</a></li>
                            </ul>
                        </li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href=""><span class="glyphicon glyphicon-bell"></span>通知<span class="badge">42</span></a></li>
                        <li class="dropdown">
                            <a class="dorpdown-toggle" href="#" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span><%=_userNickname%><b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="#">修改密码</a></li>
                                <li><a href="#">退出登录</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>