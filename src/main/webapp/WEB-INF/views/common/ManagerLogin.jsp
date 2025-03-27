<%-- 
    Document   : ManagerLogin
    Created on : Feb 17, 2025, 9:51:01 PM
    Author     : Nguyen Ngoc Phat - CE180321
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Manager Login</title>
        <style>
            body {
                background-color: #B0C4DE;
                font-family: 'Garamond', serif;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
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

            .error-message {
                color: red;
                font-size: 14px;
                margin-bottom: 10px;
            }
        </style>
        <link rel="icon" type="image/png" href="img/logo.jpg">

        <script>
            function validateForm() {
                let username = document.getElementById("username").value.trim();
                let usernameRegex = /^[a-zA-Z0-9_]+$/;
                if (!usernameRegex.test(username)) {
                    alert("Username can only contain letters, numbers, and underscores.");
                    return false;
                }
                return true;
            }
        </script>
    </head>
    <body>

        <c:if test="${not empty sessionScope.manager}">
            <script>
                window.location.href = "<c:url value='ManagerLogin'/>";
            </script>
        </c:if>

        <div class="login-container">
            <h2>Manager Login</h2>

            <%-- Hiển thị lỗi nếu có --%>
            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>

            <form action="ManagerLogin" method="post" onsubmit="return validateForm()">
                <input type="hidden" name="action" value="normal">
                <input type="text" id="username" name="username" placeholder="Username" 
                       value="${param.username}" required>
                <input type="password" name="password" placeholder="Password" required>
                <button type="submit">Login</button>
            </form>
        </div>

    </body>
</html>