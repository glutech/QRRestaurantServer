<%@page import="com.qrrest.service.RestDishCatService"%>
<%@page import="com.qrrest.model.RestDishCategoryTerm"%>
<%@page import="com.qrrest.service.AdminAuthService"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String _sourceId = request.getParameter("id");
boolean _isActionNew = _sourceId == null;
String _actionText = _isActionNew ? "新增分类" : "编辑分类";
RestDishCategoryTerm _sourceCategory = _isActionNew ? null : new RestDishCatService().getCatByCatId(Integer.parseInt(_sourceId));
int _authRestId = new AdminAuthService(request.getSession()).getRestId();
if(_sourceCategory != null && _sourceCategory.getRestId() != _authRestId) {
    response.setStatus(500);
    return;
}
%>

<% String _ActionDesc_ = _actionText + ";" + "菜品分类管理" + ";" + "菜品管理"; %>
<jsp:include page="_PageHeader_.jsp">
    <jsp:param name="_PageName_" value="page-restdishcat-editor" />
    <jsp:param name="_ActionDesc_" value="<%=_ActionDesc_%>" />
</jsp:include>

<div class="container">
            <div class="row">
                <div>
                    <ol class="breadcrumb">
                        <li><a href="./Service.jsp">服务</a>
                        <li><a href="./DishList.jsp">菜品管理</a></li>
                        <li><a href="./RestDishCatList.jsp">菜品分类管理</a></li>
                        <li class="active"><%=_actionText%></li>
                    </ol>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row model model-restdishcat-editor" role="main">
                <div class="col-xs-9 model-container-left">
                    <div class="panel">
                        <form class="form-horizontal panel-body module-exValidate" action="./RestDishCatEditor.do" method="post" role="form" data-ajaxform="true">
                            <%
                            if(!_isActionNew) {
                                out.print("<input name=\"sourceId\" type=\"hidden\" value=\"" + _sourceId +"\" />");
                            }
                            %>
                            <div class="form-group">
                                <label class="control-label col-xs-2">分类名称</label>
                                <div class="col-xs-10">
                                    <input class="form-control" name="name" type="text" placeholder="输入菜品分类名称" value="<%=_isActionNew ? "" : _sourceCategory.getRestDishCatName()%>" />
                                    <span class="help-block">根据餐厅规则填写方便管理、具有唯一性的菜品分类名称</span>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-offset-2 col-xs-6">
                                    <button class="model-btn-submit btn" type="submit"><%=_isActionNew ? "新增" : "保存"%></button>
                                    <a class="model-btn-back btn" href="#" data-exaele="goback">返回</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="col-xs-3 model-container-right">
                    <div>
                        <blockquote>
                            <p>在此页面<%=_isActionNew ? "创建新菜品分类" : "编辑菜品分类信息"%></p>
                            <small>根据餐厅规则填写方便管理、具有唯一性的菜品分类名称</small>
                        </blockquote>
                    </div>
                </div>
            </div>
        </div>

<jsp:include page="_PageFooter_.jsp">
    <jsp:param name="_AdditionScripts_" value="./../AdminPublic/Validation/jquery.validate.min.js" />
</jsp:include>