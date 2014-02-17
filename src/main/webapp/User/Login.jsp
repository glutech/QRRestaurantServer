<%@page import="com.qrrest.service.UserAuthService"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
if(new UserAuthService(request.getSession()).isAuth()) {
    response.sendRedirect("./Main.jsp");
}
 %>
<!DOCTYPE html>
<html lang="zh-cn" id="page-login">
    
    <head>
        <meta charset="UTF-8" />
        <title>系统登录</title>
        <link rel="stylesheet" href="./../UserPublic/common.min.css">
    </head>
    
    <body>
       
        <div class="container jumbotron model model-login" role="main">
            <h1>欢迎登录</h1>
            <form class="form-horizontal module-exValidate" action="./Login.do" method="post" role="form">
                <div class="form-group">
                    <label class="control-label col-xs-2">用户名</label>
                    <div class="col-xs-10"><input class="form-control" name="username" type="text" placeholder="输入用户名" /></div>
                </div>
                <div class="form-group">
                    <label class="control-label col-xs-2">密&nbsp;&nbsp;码</label>
                    <div class="col-xs-10"><input class="form-control" name="password" type="password" placeholder="输入密码" /></div>
                </div>
                <div class="form-group">
                    <label class="control-label col-xs-2">验证码</label>
                    <div class="col-xs-10">
                        <div class="input-group">
                            <input class="form-control" name="verifycode" type="text" placeholder="输入验证码" autocomplete="off" />
                            <span class="input-group-btn">
                                <a class="model-verifycode btn" href="#" title="点击更换验证码">
                                    <img src="./Verifycode.do" alt="验证码" />
                                </a>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-xs-offset-3 col-xs-6">
                        <button class="model-submit btn" type="submit">登录</button>
                    </div>
                </div>
            </form>
        </div>

        <script src="./../UserPublic/jQuery/jquery-1.10.2.min.js"></script>
        <script src="./../UserPublic/Bootstrap/dist/js/bootstrap.min.js"></script>
        <script src="./../UserPublic/Validation/jquery.validate.min.js"></script>
        <script src="./../UserPublic/common.min.js"></script>
        
    </body>
</html>
