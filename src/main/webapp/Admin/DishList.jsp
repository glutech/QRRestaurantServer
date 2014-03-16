<%@page import="com.qrrest.model.Dish.DishStatusEnum"%>
<%@page import="com.qrrest.servlet.admin.Util"%>
<%@page import="com.qrrest.vo.RestDishCategoryTermVo"%>
<%@page import="com.qrrest.vo.DishTagTermVo"%>
<%@page import="com.qrrest.service.RestDishCatService"%>
<%@page import="com.qrrest.model.RestDishCategoryTerm"%>
<%@page import="com.qrrest.service.DishTagService"%>
<%@page import="com.qrrest.model.DishTagTerm"%>
<%@page import="com.qrrest.vo.DishVo"%>
<%@page import="com.qrrest.service.RestaurantService"%>
<%@page import="com.qrrest.service.DishService"%>
<%@page import="com.qrrest.model.Dish"%>
<%@page import="com.qrrest.service.AdminAuthService"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%

    int _authRestId = new AdminAuthService(request.getSession()).getRestId();
    DishService _dishService = new DishService();
    
    List<RestDishCategoryTermVo> _catVos = new RestDishCatService().getCatVosByRestId(_authRestId);
    List<DishTagTermVo> _tagVos = new DishTagService().getTagVosByRestId(_authRestId);
    
    String _pStatus = Util.isStringNullOrEmpty(request.getParameter("status")) ? null : request.getParameter("status");
    String _pCat = Util.isStringNullOrEmpty(request.getParameter("cat")) ? null : request.getParameter("cat");
    String _pTag = Util.isStringNullOrEmpty(request.getParameter("tag")) ? null : request.getParameter("tag");
    String _pPrice = Util.isStringNullOrEmpty(request.getParameter("price")) ? null : request.getParameter("price");
    
    DishStatusEnum _status = null;
    Integer _cat = null;
    Integer _tag = null;
    String _price = null;
    try {
	    if(_pStatus != null) {
	        _status = DishStatusEnum.valueOf(_pStatus);
	    }  
	    if(_pCat != null) {
	        _cat = Integer.parseInt(_pCat);
	        boolean find = false;
	        for(RestDishCategoryTermVo vo : _catVos) {
	            if(vo.getCat().getRestDishCatId() == _cat) {
	                find = true;
	                break;
	            }
	        }
	        if(!find) throw new RuntimeException();
	    }
	    if(_pTag != null) {
	        _tag = Integer.parseInt(_pTag);
	        boolean find = false;
	        for(DishTagTermVo vo : _tagVos) {
	            if(vo.getTag().getDishTagId() == _tag) {
	                find = true;
	                break;
	            }
	        }
	        if(!find) throw new RuntimeException();
	    }
	    if(_pPrice != null) {
	        if(!_pPrice.equals("asc") && !_pPrice.equals("desc")) throw new RuntimeException();
	        _price = _pPrice.toLowerCase();
	    }
    } catch(Exception e) {
        response.setStatus(500);
        return;
    }
    
    int _currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
    
    Map<String, Object> query = new DishService().queryOnAdminDishList(_status, _cat, _tag, _price, _currentPage, 9);
    
    int _totalPages = (Integer)query.get("totalPage");
    int _dishesCount = (Integer)query.get("totalNum");
    List<Dish> _dishes = (List<Dish>)query.get("dishes");
    
    String __currentUrl = Util.getCurrentUrl(request);
    String _pageUrl;
    if(__currentUrl.indexOf("page=") == -1) {
        _pageUrl = __currentUrl.indexOf("?") == -1 ? (__currentUrl + "?page=") : (__currentUrl + "&page=");
    }  else {
        _pageUrl = __currentUrl.replaceAll("page=[\\d]+", "page=");
    }
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
                        <%  %>
                        <% for(Dish dish : _dishes) { %>
                        <% DishVo dishVo = _dishService.getDishVoByDishId(dish.getDishId()); %>
                        <li>
                            <div class="thumbnail">
                                <img src="<%=dish.getDishPic()%>?imageView2/2/w/216" alt="<%=dish.getDishName()%>" />
                                <div class="caption">
                                    <h4><%=dish.getDishName()%></h4>
                                    <p>类别：<a href="./DishList.jsp?cat=<%=dishVo.getCat().getRestDishCatId()%>" title="查看所有 <%=dishVo.getCat().getRestDishCatName()%> 分类的菜品"><%=dishVo.getCat().getRestDishCatName()%></a></p>
                                    <p>价格：<i class="money"></i><%=dish.getDishPrice()%>&nbsp;&nbsp;|&nbsp;&nbsp;状态：<a href="./DishList.jsp?status=<%=dishVo.getDish().getDishStatus().toString()%>" title="查看所有 <%=dishVo.getDish().getDishStatus().getNameZHCN()%> 状态的菜品"><%=dishVo.getDish().getDishStatus().getNameZHCN()%></a></p>
                                    <p>标签：<%
                                    for(DishTagTerm tag : dishVo.getTags()) {
                                        out.print("<a href=\"./DishList.jsp?tag=" +  tag.getDishTagId() + "\" title=\"查看所有 " + tag.getDishTagName() +" 标签的菜品\">" + tag.getDishTagName()+"</a> ");
                                    }%></p>
                                    <p>
                                         <a class="btn btn-success btn-sm" href="./DishEditor.do?action=checkedit&id=<%=dish.getDishId()%>" role="button" data-ajaxtodo="true">编辑菜品信息</a>
                                         <a class="btn btn-danger btn-sm" href="./DishEditor.do?action=del&id=<%=dish.getDishId()%>" role="button">删除</a>
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
                        <small>仅当前没有被使用(被预定、正在就餐等)的菜品可以进行编辑和删除操作</small>
                    </blockquote>
                    <p><a class="btn" href="./DishEditor.jsp" role="button">新增菜品</a></p>
                    <p><a class="btn" href="./RestDishCatList.jsp" role="button">菜品分类管理</a></p>
                    <div class="panel">
                        <div class="panel-heading"><h4 class="panel-title">筛选排序</h4></div>
                        <div class="panel-body">
                            <form action="./DishList.jsp" method="get">
	                            <ul class="nav">
	                                <li class="dropdown">
	                                    <a class="dropdown-toggle" href="#" data-toggle="dropdown">按菜品状态<span class="caret"></span></a>
	                                    <ul class="dropdown-menu" data-key="status" data-value="<%=_status==null?"":_status%>">
	                                        <li><a href="#" data-value="">全部</a></li>
	                                        <li><a href="#" data-value="normal">正常<small> (对外显示但还没有被使用)</small></a></li>
	                                        <li><a href="#" data-value="active">使用中<small> (被预定或在订单内)</small></a></li>
	                                        <li><a href="#" data-value="blocked">禁用</a></li>
	                                    </ul>
	                                </li>
	                                <li class="dropdown">
	                                    <a class="dropdown-toggle" href="#" data-toggle="dropdown">按菜品分类<span class="caret"></span></a>
	                                    <ul class="dropdown-menu" data-key="cat" data-value="<%=_cat==null?"":_cat%>">
	                                        <li><a href="#" data-value="">全部</a></li>
	                                        <% for(RestDishCategoryTermVo catVo : _catVos) { %>
	                                        <li><a href="#" data-value="<%=catVo.getCat().getRestDishCatId()%>"><%=catVo.getCat().getRestDishCatName()%><small><%=" (" + catVo.getUsedNum() + ")"%></small></a></li>
	                                        <% } %>
	                                    </ul>
	                                </li>
	                                <li class="dropdown">
	                                    <a class="dropdown-toggle" href="#" data-toggle="dropdown">按菜品标签<span class="caret"></span></a>
	                                    <ul class="dropdown-menu" data-key="tag" data-value="<%=_tag==null?"":_tag%>">
	                                        <li><a href="#" data-value="">全部</a></li>
	                                        <% for(DishTagTermVo vo : _tagVos) { %>
	                                        <li><a href="#" data-value="<%=vo.getTag().getDishTagId()%>"><%=vo.getTag().getDishTagName()%><small><%=" ("+vo.getRestUsedNum()+")"%></small></a></li>
	                                        <% } %>
	                                    </ul>
	                                </li>
	                                <li class="dropdown">
	                                    <a class="dropdown-toggle" href="#" data-toggle="dropdown">按价格排序<span class="caret"></span></a>
	                                    <ul class="dropdown-menu" data-key="price" data-value="<%=_price==null?"":_price%>">
	                                        <li><a href="#" data-value="">忽略</a></li>
	                                        <li><a href="#" data-value="desc">从高到低</a></li>
	                                        <li><a href="#" data-value="asc">从低到高</a></li>
	                                    </ul>
	                                </li>
	                            </ul>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

<jsp:include page="_PageFooter_.jsp" />
