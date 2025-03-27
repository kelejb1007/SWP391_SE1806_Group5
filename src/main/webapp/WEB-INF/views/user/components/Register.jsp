<%-- 
    Document   : Register
    Created on : Feb 26, 2025, 7:42:08 AM
    Author     : KHOA
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up</title>
    <link rel="shortcut icon" type="image/x-icon" href="<%= application.getInitParameter("shortcut")%>">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            position: relative;
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
            z-index: -1;
        }
        .register-container {
            position: relative;
            background: rgba(255, 255, 255, 0.57);
            padding: 25px;
            border-radius: 15px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
            text-align: center;
            width: 350px;
        }
        .register-container h2 {
            font-size: 24px;
            color: #ff3955;
            margin-bottom: 15px;
            text-shadow: 1px 1px 1.5px #ff3955, 1px 1px 2px rgba(255, 57, 85, 1);
            background: linear-gradient(to top, #ff3955 30%, #ff9aa5 70%);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
        }
        input, select {
            width: 100%;
            padding: 12px;
            margin: 10px 0;
            border: 1px solid #ff3955;
            border-radius: 8px;
            font-size: 16px;
            box-sizing: border-box;
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
        .error-message {
            color: red;
            margin-bottom: 10px;
        }
    </style>
    <script>
        function validateForm() {
            var password = document.getElementById("password").value;
            var confirmPassword = document.getElementById("confirmPassword").value;
            if (password !== confirmPassword) {
                alert("Passwords do not match!");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
    <div class="register-container">
        <h2>Sign Up</h2>
        <c:if test="${not empty requestScope.error}">
            <p class="error-message">${requestScope.error}</p>
        </c:if>
        <form action="Register" method="post" onsubmit="return validateForm()">
            <input type="text" id="username" name="username" placeholder="Username" required>
            <input type="password" id="password" name="password" placeholder="Password" required>
            <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm Password" required>
            <input type="text" id="fullName" name="fullName" placeholder="Full Name" required>
            <input type="email" id="email" name="email" placeholder="Email" required>
            <input type="text" id="numberPhone" name="numberPhone" placeholder="Phone Number" required>
            <select name="gender" id="gender" required>
                <option value="" disabled selected>Select your gender</option>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
            </select>
            <button type="submit">Register</button>
        </form>
    </div>
</body>
</html>





