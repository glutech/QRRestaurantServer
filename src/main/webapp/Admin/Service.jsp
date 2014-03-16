<%@page import="com.qrrest.model.Table.TableStatusEnum"%>
<%@page import="com.qrrest.service.TableService"%>
<%@page import="com.qrrest.service.AdminAuthService"%>
<%@page import="com.qrrest.model.Table"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
    int _authRestId = new AdminAuthService(request.getSession()).getRestId();
    List<Table> _tables = new TableService().getAllTablesByRestId(_authRestId);
%>

<jsp:include page="_PageHeader_.jsp">
    <jsp:param name="_PageName_" value="page-service"/>
    <jsp:param name="_ActionDesc_" value="服务"/>
</jsp:include>

<div class="container">
    <div class="row" role="main">
        <div class="model model-service-map">
            <ul class="model-list">
                <% for(Table table : _tables) {
                    if(table.getTableStatus() != TableStatusEnum.blocked) { 
                %>
                <li class="model-item">
                    <a class="model-module model-module-<%=table.getTableStatus().toString()%>" href="./section-board-status.html" data-toggle="modal" data-target="#section-board-status-container">
                        <span class="model-icon glyphicon"></span>
                        <span class="model-notice label"></span>
                        <span class="model-name"><%=table.getTableName()%></span>
                        <span class="model-status"></span>
                    </a>
                <% } } %><!-- 考虑display:inline-block, li不闭合 -->
            </ul>
        </div>
    </div>
</div>
        
<jsp:include page="_PageFooter_.jsp" />
