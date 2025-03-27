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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="YOUR_INTEGRITY_HASH" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <style>
            body {
                background-image: url("https://i.pinimg.com/originals/56/34/9f/56349f764173af321a640f6e1bac22fd.gif");
                background-size: cover; /* Đảm bảo hình ảnh phủ kín toàn bộ nền */
                background-repeat: no-repeat; /* Ngăn hình ảnh lặp lại */
                 font-family: Arial, sans-serif;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
                overflow: hidden; /* Ẩn thanh cuộn nếu cần */
            }

            body::before {
    content: "";
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.3); /* Màu đen với độ trong suốt 40% */
    z-index: -1; /* Đặt lớp phủ phía sau nội dung */
}


            .login-container {
                background: rgba(255, 255, 255, 0.1);
                backdrop-filter: blur(10px);
                border: 1px solid rgba(255, 255, 255, 0.2);
                padding: 30px;
                border-radius: 15px;
                box-shadow: 0 0 25px rgba(0, 0, 0, 0.4);
                text-align: center;
                width: 350px;
                opacity: 0;
                transform: translateY(80px);
                transition: opacity 1s ease-out, transform 1s ease-out;
                color: white;
            }

            .login-container.show {
                opacity: 1;
                transform: translateY(0);
            }

            .login-container h2 {
                  font-family: sans-serif;
                color: #f2e9e9;
                font-size: 28px;
                margin-bottom: 20px;
                
            }

            .input-group {
                position: relative;
            }

            .input-group i {
                position: absolute;
                left: 12px;
                top: 50%;
                transform: translateY(-50%);
                color: rgba(255, 255, 255, 0.7);
            }

            input[type="text"],
            input[type="password"] {
                width: 100%;
                padding: 12px;
                margin: 12px 0;
                border: 1px solid rgba(255, 255, 255, 0.3);
                border-radius: 8px;
                font-size: 16px;
                box-sizing: border-box;
                background: rgba(255, 255, 255, 0.1);
                color: white;
                outline: none;
                transition: border-color 0.3s ease;
                padding-left: 40px;
            }

            input[type="text"]:focus,
            input[type="password"]:focus {
                border-color: #d82c47;
            }

            input::placeholder {
                color: rgba(255, 255, 255, 0.7);
            }

            button {
                background: linear-gradient(to right, #ff3955, #b22038);
                color: white;
                border: none;
                padding: 12px;
                width: 100%;
                border-radius: 8px;
                cursor: pointer;
                font-size: 16px;
                margin-top: 10px;
                transition: 0.3s;
                box-shadow: 0 3px 6px rgba(0, 0, 0, 0.3);
            }

            button:hover {
                background: linear-gradient(to right, #d82c47, #8b1d30);
                transform: translateY(-2px);
                box-shadow: 0 5px 8px rgba(0, 0, 0, 0.4);
            }

            button i {
                margin-right: 8px;
            }

            .error-message {
    color: #8b1d30;
    font-size: 18px;
    margin-bottom: 10px;
    text-shadow: 0 0 10px #ff3955; /* Phát sáng màu đỏ */
}
        </style>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                let loginContainer = document.querySelector(".login-container");
                setTimeout(() => {
                    loginContainer.classList.add("show");
                }, 300);
            });

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

            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>

            <form action="ManagerLogin" method="post" onsubmit="return validateForm()">
                <div class="input-group">
                    <i class="fas fa-user"></i>
                    <input type="text" id="username" name="username" placeholder="Username" 
                           value="${param.username}" required>
                </div>
                <div class="input-group">
                    <i class="fas fa-lock"></i>
                    <input type="password" name="password" placeholder="Password" required>
                </div>
                <button type="submit"><i class="fas fa-sign-in-alt"></i> Login</button>
            </form>
        </div>
    </body>
</html>