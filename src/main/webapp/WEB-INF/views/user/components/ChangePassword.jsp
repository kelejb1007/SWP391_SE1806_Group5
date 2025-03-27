<%-- 
    Document   : ChangePassword
    Created on : Mar 9, 2025, 9:09:18 PM
    Author     : default
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Change Password</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: Arial, sans-serif;
            background-color: #e3e9f7;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }
        .container {
            width: 40%;
            background: white;
            border-radius: 10px;
            box-shadow: 0px 0px 10px gray;
            padding: 30px;
            text-align: center;
        }
        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }
        .error {
            color: red;
            font-weight: bold;
        }
        .message {
            color: green;
            font-weight: bold;
        }
        label {
            font-weight: bold;
            display: block;
            text-align: left;
            margin: 10px 0 5px;
        }
        input[type="password"] {
            width: 100%;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .btn {
            width: 100%;
            padding: 12px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            margin-top: 10px;
        }
        .btn-change {
            background-color: #dc3545;
            color: white;
        }
        .btn-change:hover {
            background-color: #c82333;
        }
        .btn-back {
            background-color: gray;
            color: white;
        }
        .btn-back:hover {
            background-color: #5a5a5a;
        }
        .footer {
            width: 100%;
            text-align: center;
            padding: 10px;
            background-color: #4a90e2;
            color: white;
            position: absolute;
            bottom: 0;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Change Password</h2>
        
        <% if (request.getAttribute("error") != null) { %>
            <p class="error"><%= request.getAttribute("error") %></p>
        <% } %>
        <% if (request.getAttribute("message") != null) { %>
            <p class="message"><%= request.getAttribute("message") %></p>
        <% } %>

        <form action="<%= request.getContextPath() %>/changePassword" method="post">
            <label>Old Password:</label>
            <input type="password" name="oldPassword" required>
            
            <label>New Password:</label>
            <input type="password" name="newPassword" required>

            <label>Confirm New Password:</label>
            <input type="password" name="confirmPassword" required>

            <button type="submit" class="btn btn-change">Change Password</button>
        </form>

        <a href="<%= request.getContextPath() %>/viewprofile">
            <button class="btn btn-back">Back to Profile</button>
        </a>
    </div>
</body>
</html>
