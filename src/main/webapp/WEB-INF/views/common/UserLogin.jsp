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
        </style>
    </head>
    <body>

        <div class="login-container">
            <h2>User Login</h2>

            <%-- Hiá»n thá» lá»i náº¿u cÃ³ --%>
            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>

            <form action="Login" method="post">
                <input type="hidden" name="action" value="normal">
                <input type="text" name="username" placeholder="Username" required>
                <input type="password" name="password" placeholder="Password" required>
                <button type="submit">Login</button>
            </form>

            <hr>

            <form action="LoginController" method="post">
                <input type="hidden" name="action" value="google">
                <button type="submit" style="background: #db4437;">Login with Google</button>
            </form>


            <p>Don't have an account? <a href="register.jsp">Register</a></p>
        </div>

    </body>
</html>
