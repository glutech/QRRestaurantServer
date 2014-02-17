<%@page import="com.qrrest.model.Category"%>
<%@page import="com.qrrest.service.DishService"%>
<%@page import="com.qrrest.service.CategoryService"%>
<%@page import="com.qrrest.model.Dish"%>
<%@page import="com.qrrest.service.UserAuthService"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
long _catId; String _priceOrder; int _currentPage;
String _pageUrl; String _catUrl; String _priceUrl;
long _auth_rest_id;
{
    String _cat = request.getParameter("cat");
    _catId = _cat == null ? 0 : Long.parseLong(_cat);
    String _price = request.getParameter("price");
    _priceOrder = (_price != null && !_price.equals("")) ? _price : null;
    _currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
    _auth_rest_id = new UserAuthService(request.getSession()).getRestId();
    
    final String _path = "./DishList.jsp?";
    if(_catId != 0 && _priceOrder != null) {
        _pageUrl = _path + "cat=" + _catId + "&price=" + _priceOrder + "&page=";
        _catUrl = _path + "price=" + _priceOrder + "&";
        _priceUrl = _path + "cat=" + _catId + "&";    
    } else if(_catId != 0) {
        _pageUrl = _path + "cat=" + _catId + "&page=";
        _catUrl = _path;
        _priceUrl = _path + "cat" + _catId + "&";   
    } else if(_priceOrder != null) {
        _pageUrl = _path + "price=" + _priceOrder + "&page=";
        _catUrl = _path + "price=" + _priceOrder + "&";
        _priceUrl = _path;
    } else {
        _pageUrl = _path + "page=";
        _catUrl = _path;
        _priceUrl = _path;   
    }
}
DishService _dService = new DishService();
Map<String, Object> query = _dService.queryByRestAndOptionsForPading(_auth_rest_id, _currentPage, 2, _catId, _priceOrder);
List<Dish> _dishes =  (List<Dish>)query.get("dishes");
int _dishesCount = _dishes.size();
_currentPage = (Integer)query.get("currentPage");
int _totalPages = (Integer)query.get("totalPages");
%>

<jsp:include page="_PageHeader_.jsp">
    <jsp:param name="_PageName_" value="page-dish-list"/>
    <jsp:param name="_ActionDesc_" value="菜品管理"/>
</jsp:include>

        <div class="container">
            <div class="row">
                <div>
                    <ol class="breadcrumb">
                        <li><a href="./Service.jsp">服务</a></li>
                        <li class="active">菜品管理</li>
                    </ol>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-xs-9 model-container-left">
                    <ul class="model model-dish-list">
                        <% CategoryService __catService = new CategoryService(); %>
                        <% for(Dish dish : _dishes) { %>
                        <li>
                            <div class="thumbnail">
                                <img src="<%=dish.getDish_pic()%>" alt="<%=dish.getDish_name()%>" />
                                <div class="caption">
                                    <h4><%=dish.getDish_name()%></h4>
                                    <p>类别：<%=__catService.getCatByCatId(dish.getCat_id()).getCat_name()%>&nbsp;|&nbsp;价格：<i class="money"></i><%=dish.getDish_price()%></p>
                                    <p>标签：<%=dish.getDish_tag().replace(',', ' ')%></p>
                                    <p>
                                         <a class="btn btn-success btn-sm" href="./DishEditor.jsp?id=<%=dish.getDish_id()%>" role="button">编辑菜品信息</a>
                                         <a class="btn btn-danger btn-sm" href="./DishEditor.do?action=del&id=<%=dish.getDish_id()%>" role="button">删除</a>
                                    </p>
                                </div>
                            </div>
                        <% } %> <!-- </li> --><!-- 从 display:inline-block角度考虑，或者忽略闭合li、或者</li><li> -->
                    </ul>
                    <% if(_dishesCount != 0) { %>
                    <div class="text-center">
	                    <ul class="pagination">
	                       <%
	                           int _shownPagerNum = 5;
	                           int _minPage = _currentPage - _shownPagerNum / 2;
	                           if(_minPage < 1) _minPage = 1;
	                           int _maxPage = _minPage + _shownPagerNum;
	                           if(_maxPage > _totalPages) _maxPage = _totalPages;
	                       %>
	                       <li<%= _currentPage==1 ? " class=\"disabled\"" : "" %>><a href="<%= _currentPage==1 ? "#" : _pageUrl + (_currentPage - 1) %>">«</a></li>
	                       <% for(int i=_minPage; i<=_maxPage; i++) { %>
	                       <li<%= i==_currentPage ? " class=\"active\"" : ""%>><a href="<%= _pageUrl + i %>"><%= i %></a></li>
	                       <% } %>
	                       <li<%= _currentPage==_totalPages ? " class=\"disabled\"" : "" %>><a href="<%= _currentPage==_totalPages ? "#" : _pageUrl + (_currentPage + 1) %>">»</a></li>
	                    </ul>
	                </div>
	                <% } %>
                </div>
                <div class="col-xs-3 model-container-right">
                <div>
                    <blockquote>
                        <p>在此页对菜品进行管理操作</p>
                        <small>包括查找、新增、修改、删除等操作</small>
                        <small>可以按条件对菜品进行筛选和排序操作</small>
                    </blockquote>
                    <a class="btn" href="./DishEditor.jsp" role="button">新增菜品</a>
                    <div class="panel">
                        <div class="panel-heading"><h4 class="panel-title">筛选排序</h4></div>
                        <div class="panel-body">
                            <ul class="nav">
                                <li class="dropdown">
                                    <% List<Category> _categories = new CategoryService().getCatsByRestId(_auth_rest_id); %>
                                    <a class="dropdown-toggle" href="#" data-toggle="dropdown">按菜品类别<%
                                    if(_catId == 0) out.print("(全部类别)");
                                    else out.print("(" + new CategoryService().getCatByCatId(_catId).getCat_name() + ")");
                                    %><span class="caret"></span>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li<%=_catId == 0 ? " class=\"active\"" : ""%>><a href="<%=_catUrl%>">全部类别</a></li>
                                        <% for(Category category : _categories) { %>
                                        <li<%=_catId == category.getCat_id() ? " class=\"active\"" : ""%>><a href="<%=_catUrl + "cat=" + category.getCat_id() %>"><%= category.getCat_name() %></a></li>
                                        <% } %>
                                    </ul>
                                </li>
                                <li class="dropdown">
                                    <a class="dropdown-toggle" href="#" data-toggle="dropdown">按菜品价格<%
                                    if(_priceOrder != null) {
                                        if(_priceOrder.equals("desc")) out.print("(从高到低)");
                                        else if(_priceOrder.equals("asc")) out.print("(从低到高)");
                                        else out.print("(忽略)");
                                    } else {
                                        out.print("(忽略)");
                                    }
                                    %><span class="caret"></span>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li<%=_priceOrder == null ? " class=\"active\"" : ""%>><a href="<%=_priceUrl%>">忽略</a></li>
                                        <li<%="desc".equals(_priceOrder) ?  " class=\"active\"" : ""%>><a href="<%=_priceUrl + "price=desc"%>">从高到低</a></li>
                                        <li<%="asc".equals(_priceOrder) ?  " class=\"active\"" : ""%>><a href="<%=_priceUrl + "price=asc"%>">从低到高</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

<jsp:include page="_PageFooter_.jsp" />
