<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Manager Login</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Poppins', sans-serif;
            
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background: #f5f5f5;
            margin: 0;
            overflow: hidden;
        }

        .login-wrapper {
            display: flex;
            width: 100%;
            max-width: 800px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            overflow: hidden;
            opacity: 0;
            transform: scale(0.95);
            animation: fadeIn 0.8s ease-out forwards;
        }

        @keyframes fadeIn {
            to {
                opacity: 1;
                transform: scale(1);
            }
        }

        .login-image {
            flex: 1;
            background: url('https://i.pinimg.com/736x/0c/dc/9e/0cdc9ed0ebce9fc0b5860d18534d73d9.jpg') no-repeat center center;
            background-size: cover;
            min-height: 400px;
            position: relative;
        }

        .login-image::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.3);
            z-index: 1;
        }

        .login-form {
            flex: 1;
            background: #fff;
            padding: 40px;
            display: flex;
            flex-direction: column;
            justify-content: center;
        }

        .login-form h2 {
            font-size: 28px;
            font-weight: 600;
            color: #333;
            margin-bottom: 30px;
            text-align: center;
            position: relative;
        }

        .login-form h2::after {
            content: '';
            position: absolute;
            bottom: -10px;
            left: 50%;
            transform: translateX(-50%);
            width: 40px;
            height: 3px;
            background: #ff3955;
            border-radius: 5px;
            animation: underline 2s infinite alternate;
        }

        @keyframes underline {
            0% { width: 40px; }
            100% { width: 60px; }
        }

        .input-group {
            position: relative;
            margin-bottom: 20px;
            opacity: 0;
            transform: translateY(20px);
            animation: slideUp 0.5s ease-out forwards;
        }

        .input-group:nth-child(1) { animation-delay: 0.2s; }
        .input-group:nth-child(2) { animation-delay: 0.4s; }

        @keyframes slideUp {
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .input-group input {
            width: 100%;
            padding: 12px 40px 12px 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            color: #333;
            outline: none;
            transition: all 0.3s ease;
            /* Tắt biểu tượng mắt mặc định của trình duyệt */
            -webkit-appearance: none;
            -moz-appearance: none;
            appearance: none;
        }

        /* Tắt biểu tượng mắt mặc định trên Chrome, Safari, Edge */
        .input-group input[type="password"]::-webkit-textfield-decoration-container {
            visibility: hidden;
        }

        .input-group input[type="password"]::-ms-reveal,
        .input-group input[type="password"]::-ms-clear {
            display: none; /* Tắt trên Edge */
        }

        .input-group input::placeholder {
            color: #bbb;
            font-style: italic;
        }

        .input-group input:focus {
            border-color: #ff3955;
            box-shadow: 0 0 8px rgba(255, 57, 85, 0.3);
            transform: translateY(-2px);
        }

        .input-group::before {
            content: '';
            position: absolute;
            top: 50%;
            left: 50%;
            width: 0;
            height: 0;
            background: rgba(255, 57, 85, 0.2);
            border-radius: 50%;
            transform: translate(-50%, -50%);
            pointer-events: none;
            z-index: -1;
        }

        .input-group:hover::before {
            animation: wave 0.8s ease-out;
        }

        @keyframes wave {
            0% { width: 0; height: 0; opacity: 1; }
            100% { width: 300px; height: 300px; opacity: 0; }
        }

        .input-group .toggle-password {
            position: absolute;
            right: 15px;
            top: 50%;
            transform: translateY(-50%);
            cursor: pointer;
            color: #999;
            font-size: 14px;
            transition: color 0.3s ease;
        }

        .input-group .toggle-password:hover {
            color: #ff3955;
        }

        button {
            background: #ff3955;
            color: #fff;
            border: none;
            padding: 12px;
            width: 100%;
            border-radius: 5px;
            font-size: 16px;
            font-weight: 500;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            transition: all 0.3s ease;
            opacity: 0;
            transform: translateY(20px);
            animation: slideUp 0.5s ease-out forwards;
            animation-delay: 0.6s;
        }

        button:hover {
            background: #e6304b;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(255, 57, 85, 0.4);
        }

        button::before {
            content: '';
            position: absolute;
            top: 0;
            left: -100%;
            width: 100%;
            height: 100%;
            background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.3), transparent);
            transition: 0.5s;
        }

        button:hover::before {
            left: 100%;
        }

        button::after {
            content: '';
            position: absolute;
            width: 0;
            height: 0;
            background: rgba(255, 255, 255, 0.3);
            border-radius: 50%;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            pointer-events: none;
        }

        button:active::after {
            animation: ripple 0.6s ease-out;
        }

        @keyframes ripple {
            0% { width: 0; height: 0; opacity: 1; }
            100% { width: 200px; height: 200px; opacity: 0; }
        }

        .error-message {
            color: #e74c3c;
            font-size: 14px;
            margin-bottom: 20px;
            text-align: center;
            animation: shake 0.5s ease;
        }

        @keyframes shake {
            0%, 100% { transform: translateX(0); }
            25% { transform: translateX(-5px); }
            75% { transform: translateX(5px); }
        }

        /* Responsive */
        @media (max-width: 768px) {
            .login-wrapper {
                flex-direction: column;
                max-width: 90%;
            }
            .login-image {
                min-height: 200px;
            }
            .login-form {
                padding: 30px;
            }
        }
    </style>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
    <script>
        // Toggle Show/Hide Password
        document.addEventListener("DOMContentLoaded", function () {
            const togglePassword = document.querySelector(".toggle-password");
            const passwordInput = document.querySelector("input[type='password']");
            togglePassword.addEventListener("click", function () {
                const type = passwordInput.getAttribute("type") === "password" ? "text" : "password";
                passwordInput.setAttribute("type", type);
                this.classList.toggle("fa-eye");
                this.classList.toggle("fa-eye-slash");
            });
        });

        // Xử lý submit form (bỏ hiệu ứng loading)
        function handleSubmit(event) {
            event.preventDefault();
            const form = event.target;
            setTimeout(() => {
                form.submit();
            }, 2000); // Giả lập 2 giây delay trước khi submit
        }

        // Validate Form
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

    <div class="login-wrapper">
        <div class="login-image"></div>
        <div class="login-form">
            <h2>Manager Login</h2>

            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>

            <form action="ManagerLogin" method="post" onsubmit="handleSubmit(event); return validateForm()">
                <div class="input-group">
                    <input type="text" id="username" name="username" placeholder="Username" 
                           value="${param.username}" required>
                </div>
                <div class="input-group">
                    <input type="password" name="password" placeholder="Password" required>
                    <i class="fas fa-eye toggle-password"></i>
                </div>
                <button type="submit">Login</button>
            </form>
        </div>
    </div>
</body>
</html>