<%-- 
    Document   : UserLogin
    Created on : Feb 17, 2025, 9:51:01 PM
    Author     : Nguyen Ngoc Phat - CE180321
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <title>User Login</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #B0C4DE;
                font-family: 'Garamond', serif;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                background-color: #f5f5f5;
            }
            .login-container {
                background: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                width: 300px;
                text-align: center;
            }
            input {
                width: 100%;
                padding: 10px;
                margin: 10px 0;
                border: 1px solid #ccc;
                border-radius: 4px;
            }
            button {
                width: 100%;
                padding: 10px;
                background: #007bff;
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }
            button:hover {
                background: #0056b3;
            }
            .error-message {
                color: red;
                margin-bottom: 10px;
            }

            .login-container {
                background: rgba(255, 255, 255, 0.9);
                padding: 25px;
                border-radius: 15px;
                box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
                text-align: center;
                width: 320px;
            }

            .login-container h2 {
                font-size: 24px;
                color: #4A4A4A;
                margin-bottom: 15px;
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
            }

            button {
                background: #6A5ACD;
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
                background: #483D8B;
            }

            .manager-login {
                background: #4B0082;
                margin-top: 5px;
            }

            .manager-login:hover {
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

        </style>
        <script>
            function validateForm() {
                var username = document.getElementById("username").value.trim();
                var password = document.getElementById("password").value.trim();
                if (username.length < 3) {
                    alert("Username must be at least 3 characters.");
                    return false;
                }
                if (password.length < 3) {
                    alert("Password must be at least 6 characters.");
                    return false;
                }
                return true;
            }
        </script>
    </head>
    <body>
        <div class="login-container">
            <h2>User Login</h2>
            <form action="Login" method="post" onsubmit="return validateForm()">
                <input type="hidden" name="action" value="normal">
                <input type="hidden" name="redirect" value="${param.redirect}">
                <input type="text" id="username" name="username" placeholder="Username" required>
                <input type="password" id="password" name="password" placeholder="Password" required>
                <button type="submit">Login</button>
            </form>
            <button class="manager-login" onclick="window.location.href = '${pageContext.request.contextPath}/ManagerLogin'">Manager Login</button>
            <p class="register-link">Don't have an account? <a href="register.jsp">Register</a></p>
        </div>
    </body>
</html>
