<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String _AdditionScripts_ = request.getParameter("_AdditionScripts_");
String[] _additionScripts = _AdditionScripts_ == null ? new String[] {} : _AdditionScripts_.split(";");
%>
        <div class="footer container">
            <hr />
            <small>&copy;&nbsp;2006&nbsp;-&nbsp;2014&nbsp;&nbsp;ZDEZ.COM.CN,&nbsp;All Rights Reserved.&nbsp;&nbsp;昆明博客科技有限公司&nbsp;版权所有</small>
        </div>

        <script src="./../UserPublic/jQuery/jquery-1.10.2.min.js"></script>
        <script src="./../UserPublic/Bootstrap/dist/js/bootstrap.min.js"></script>
        <%
        for(String script : _additionScripts) {
            out.print("<script src=\"");
            out.print(script);
            out.print("\"></script>");
        }
        %>
        <script src="./../UserPublic/common.min.js"></script>

    </body>
</html>