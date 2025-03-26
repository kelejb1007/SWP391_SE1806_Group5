<%-- 
    Document   : ViewProfile
    Created on : Feb 28, 2025, 5:52:24 AM
    Author     : Khoa
--%>
<%-- 
    Document   : ViewProfile
    Created on : Feb 28, 2025, 5:52:24 AM
    Author     : Khoa
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Profile</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f8ff;
            display: flex;
            flex-direction: column;
            align-items: center;
            min-height: 100vh;
            margin: 0;
        }

        .container {
            flex-grow: 1;
            display: flex;
            justify-content: center;
            align-items: center;
            width: 100%;
        }

        .profile-form {
            background-color: #fff;
            border-radius: 12px;
            padding: 20px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
            width: 100%;
            max-width: 360px;
            text-align: center;
        }

        .avatar-container {
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 10px 0;
        }

        .avatar {
            width: 100px;
            height: 100px;
            border-radius: 50%;
            border: 3px solid #4d94ff;
        }

        h2 {
            font-size: 1.8em;
            color: #333;
            margin-bottom: 10px;
        }

        label {
            font-size: 14px;
            color: #4d94ff;
            display: block;
            margin-bottom: 4px;
            text-align: left;
        }

        input {
            width: 100%;
            padding: 8px;
            margin-bottom: 8px;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 14px;
            box-sizing: border-box;
            background-color: white;
        }

        input:focus {
            border-color: #66b3ff;
            outline: none;
        }

        .button-container {
            display: flex;
            flex-direction: column;
            gap: 10px;
            margin-top: 10px;
        }

        .button-container a {
            text-decoration: none;
        }

        .button {
            width: 100%;
            padding: 10px;
            font-size: 14px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            transition: background-color 0.3s ease;
            display: block;
            text-align: center;
        }

        .edit-btn {
            background-color: #4d94ff;
            color: white;
        }

        .edit-btn:hover {
            background-color: #3385ff;
        }

        .password-btn {
            background-color: #dc3545;
            color: white;
        }

        .password-btn:hover {
            background-color: #c82333;
        }

        .home-btn {
            background-color: #ccc;
            color: black;
        }

        .home-btn:hover {
            background-color: #b0b0b0;
        }

        footer {
            background-color: #4d94ff;
            color: white;
            padding: 8px;
            width: 100%;
            text-align: center;
            font-size: 14px;
            margin-top: auto;
        }

        footer p {
            margin: 0;
        }

        @media (max-width: 768px) {
            .profile-form {
                max-width: 90%;
                padding: 15px;
            }

            h2 {
                font-size: 1.6em;
            }
        }
    </style>
</head>
<body>

    <div class="container">
        <div class="profile-form">
            <h2>User Profile</h2>

            <div class="avatar-container">
                <img src="<c:if test="${not empty user.imageUML}">${user.imageUML}</c:if> 
                     <c:if test="${empty user.imageUML}">//yuxseocdn.yuewen.com/pro/readnovel_pc/_prelease/images/ico/account.1e031.png</c:if>" 
                     alt="User Avatar" class="avatar">
            </div>
            
            <form method="POST">
                <label for="username">User name</label>
                <input type="text" id="username" value="${user.userName}" readonly>

                <label for="fullname">Full name</label>
                <input type="text" id="fullname" value="${user.fullName}" readonly>

                <label for="creationDate">Creation Date</label>
                <input type="text" id="creationDate" value="${user.creationDate}" readonly>

                <label for="email">Email</label>
                <input type="text" id="email" value="${user.email}" readonly>

                <label for="numberPhone">Number phone</label>
                <input type="text" id="numberPhone" value="${user.numberPhone}" readonly>

                <label for="dateOfBirth">Date of Birth</label>
                <input type="text" id="dateOfBirth" value="${user.dateOfBirth}" readonly>

                <label for="gender">Gender</label>
                <input type="text" id="gender" value="${user.gender}" readonly>
            </form>

            <div class="button-container">
                <a href="<%= request.getContextPath()%>/editProfile">
                    <button class="button edit-btn" type="button">Edit Profile</button>
                </a>

                <a href="<%= request.getContextPath()%>/changePassword">
                    <button class="button password-btn" type="button">Change Password</button>
                </a>

                <button class="button home-btn" type="button" 
                        onclick="window.location.href = 'homepage'">
                    Back to Home
                </button>
            </div>
        </div>
    </div>

    <footer>
        <p>© 2025 NovelWeb. All rights reserved.</p>
    </footer>

</body>
</html>


