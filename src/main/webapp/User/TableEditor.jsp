<%@page import="com.qrrest.model.Table"%>
<%@page import="com.qrrest.service.TableService"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String _sourceId = request.getParameter("id");
boolean _isActionNew = _sourceId == null;
String _actionText = _isActionNew ? "新增餐桌" : "编辑餐桌";
Table _sourceTable = _isActionNew ? null : new TableService().getTableById(_sourceId);
%>

<% String _ActionDesc_ = _actionText + ";" + "餐桌管理"; %>
<jsp:include page="_PageHeader_.jsp">
    <jsp:param name="_PageName_" value="page-table-editor" />
    <jsp:param name="_ActionDesc_" value="<%=_ActionDesc_%>" />
</jsp:include>

<div class="container">
            <div class="row">
                <div>
                    <ol class="breadcrumb">
                        <li><a href="./Service.jsp">服务</a></li>
                        <li><a href="./TableList.jsp">餐桌管理</a></li>
                        <li class="active"><%=_actionText%></li>
                    </ol>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row model model-table-editor" role="main">
                <div class="col-xs-9 model-container-left">
                    <div class="panel">
                        <form class="form-horizontal panel-body module-exValidate" action="./TableEditor.do" method="post" role="form">
                            <%
                            if(!_isActionNew) {
                                out.print("<input name=\"sourceId\" type=\"hidden\" value=\"" + _sourceId +"\" />");
                            }
                            %>
                            <div class="form-group">
                                <label class="control-label col-xs-2">餐桌名称</label>
                                <div class="col-xs-10">
                                    <input class="form-control" name="name" type="text" placeholder="输入餐桌名称" value="<%=_isActionNew ? "" : _sourceTable.getTable_name()%>" />
                                    <span class="help-block">根据餐厅规则填写方便管理、具有唯一性的餐桌名称</span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-xs-2">排序因子</label>
                                <div class="col-xs-10">
                                    <input class="form-control" name="sort" type="number" placeholder="输入排序因子（选填，默认值为0）" value="<%=_isActionNew ? "0" : _sourceTable.getTable_sort()%>" min="0" max="1024" />
                                    <span class="help-block">默认根据餐桌名称排序，填写排序因子后按值递减排序</span>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-offset-2 col-xs-6">
                                    <button class="model-btn-submit btn" type="submit"><%=_isActionNew ? "新增" : "保存"%></button>
                                    <a class="model-btn-back btn" href="./TableList.jsp">返回</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="col-xs-3 model-container-right">
                    <div>
                        <blockquote>
                            <p>在此页面<%=_isActionNew ? "创建新餐桌" : "编辑餐桌信息"%></p>
                            <small>根据餐厅规则填写方便管理、具有唯一性的餐桌名称</small>
                            <small>默认根据餐桌名称排序，填写排序因子后按值递减排序</small>
                        </blockquote>
                    </div>
                </div>
            </div>
        </div>

<jsp:include page="_PageFooter_.jsp">
    <jsp:param name="_AdditionScripts_" value="./../UserPublic/Validation/jquery.validate.min.js" />
</jsp:include>