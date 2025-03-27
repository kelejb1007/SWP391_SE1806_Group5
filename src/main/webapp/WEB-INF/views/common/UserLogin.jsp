<%-- 
    Document   : UserLogin
    Created on : Feb 17, 2025, 9:51:01 PM
    Author     : Nguyen Ngoc Phat - CE180321
--%>
<%@ page import="com.google.api.services.oauth2.model.Userinfo" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
        <link rel="shortcut icon" type="image/x-icon" href="<%= application.getInitParameter("shortcut") %>">
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f5f5f5;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                position: relative; /* Để chứa phần tử ::before */
                overflow: hidden;
            }

            body::before {
                content: "";
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-image: url('img/login.png');
                background-size: cover;
                background-position: center;
                background-repeat: no-repeat;
              
                z-index: -1; /* Đưa lớp này ra sau nội dung */
                
            
            }
            .login-container {
                position: relative;
                background: rgba(255, 255, 255, 0.57);
                padding: 25px;
                border-radius: 15px;
                box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
                text-align: center;
                width: 320px;
            }
            .login-container h2 {
                font-size: 24px;
                font-family: sans-serif;
                color: #ff3955;
                margin-bottom: 15px;
                text-shadow:
                    1px 1px 1.5px #ff3955,  /* Bóng mờ màu đỏ hồng */
                    1px 1px 2px rgba(255, 57, 85, 1); /* Độ sâu */

                background: linear-gradient(to top, #ff3955 30%, #ff9aa5 70%);
                -webkit-background-clip: text;
                -webkit-text-fill-color: transparent;
            }

            input[type="text"],
            input[type="password"] {
                width: 100%;
                padding: 12px;
                margin: 10px 0;
                border: 1px solid #ccc;
                border-radius: 8px;
                font-size: 16px;
                box-sizing: border-box;
                border-color: #ff3955;
            }
            button {
                background: #ff3955;
                color: white;
                border: none;
                padding: 12px;
                width: 100%;
                border-radius: 8px;
                cursor: pointer;
                transition: 0.3s;
                font-size: 16px;
                margin-top: 10px;
            }
            button:hover {
                background: #cc2f44;
            }
            .gg-login {
                background: #4B0082;
                margin-top: 5px;
            }
            .gg-login:hover {
                background: #360061;
            }
            .register-link {
                margin-top: 15px;
                font-size: 14px;
            }
            .register-link a {
                color: #6c4bb6;
                text-decoration: none;
            }
            .register-link a:hover {
                text-decoration: underline;
            }
            .error-message {
                color: red;
                margin-bottom: 10px;
            }
            .manager-login{
                position: absolute;
                right: -500px;
                top: -160px;
                width: 150px;
                height: 40px
            }
        </style>
        <link rel="icon" type="image/png" href="img/logo.jpg">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css" type="text/css">


        <script>
            function validateForm() {
                var username = document.getElementById("username").value.trim();
                var password = document.getElementById("password").value.trim();
                if (username.length < 3) {
                    alert("Username must be at least 3 characters.");
                    return false;
                }
                if (password.length < 8) {
                    alert("Password must be at least 8 characters.");
                    return false;
                }
                return true;
            }
        </script>
    </head>
    <body>
        <div class="login-container">
            <h2>LOGIN</h2>


            <c:if test="${not empty requestScope.error}">
                <p class="error-message">${requestScope.error}</p>
            </c:if>

            <form action="Login" method="post" onsubmit="return validateForm()">
                <input type="hidden" name="action" value="normal">
                <!-- Lấy giá trị redirect từ request hoặc session -->
                <input type="hidden" name="redirect" value="${param.redirect ne null ? param.redirect : sessionScope.redirectURL}">
                <input type="text" id="username" name="username" placeholder="Username" required>
                <input type="password" id="password" name="password" placeholder="Password" required>
                <button type="submit">Login</button>
            </form>

            <button type="button" class="gg-login" onclick="window.location.href = '${pageContext.request.contextPath}/Login?action=google'">
                <i class="fa fa-google-plus"></i> Log in with Google
            </button>
<!--            <button class="manager-login" onclick="window.location.href = '${pageContext.request.contextPath}/ManagerLogin'">Manager Login</button>-->
            <p class="register-link">Don't have an account? <a href="${pageContext.request.contextPath}/Register">Register</a></p>
        </div>

    </body>
</html>
