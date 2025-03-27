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
            background-image: url('img/profile-bg.jpg');
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
            z-index: -1;
        }

        .profile-container {
            position: relative;
            background: rgba(255, 255, 255, 0.9);
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
            text-align: center;
            width: 350px;
        }
        
        .profile-container h2 {
            font-size: 24px;
            color: #ff3955;
            margin-bottom: 15px;
            text-shadow: 1px 1px 2px rgba(255, 57, 85, 0.8);
            background: linear-gradient(to top, #ff3955 30%, #ff9aa5 70%);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
        }

        .avatar-container {
            margin-bottom: 10px;
        }

        .avatar {
            width: 110px;
            height: 110px;
            border-radius: 50%;
            border: 3px solid #ff3955;
        }

        .profile-info {
            text-align: left;
            margin-top: 10px;
        }

        .profile-info label {
            font-weight: bold;
            color: #ff3955;
            display: block;
            margin-top: 8px;
        }

        .profile-info p {
            background: #fff;
            padding: 10px;
            border-radius: 6px;
            box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.1);
            margin: 5px 0;
            font-size: 14px;
        }

        .button-group {
            margin-top: 20px;
            display: flex;
            flex-direction: column;
            gap: 10px;
        }

        .button {
            background: #ff3955;
            color: white;
            border: none;
            padding: 12px;
            border-radius: 8px;
            cursor: pointer;
            transition: 0.3s;
            font-size: 16px;
            width: 100%;
            font-weight: bold;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            text-decoration: none;
            display: inline-block;
            text-align: center;
        }

        .button:hover {
            background: #cc2f44;
        }
    </style>
</head>
<body>
    <div class="profile-container">
        <h2>User Profile</h2>

        <!-- Hiển thị Avatar -->
        <div class="avatar-container">
            <img src="${not empty user.imageUML ? user.imageUML : 'https://yuxseocdn.yuewen.com/pro/readnovel_pc/_prelease/images/ico/account.1e031.png'}" 
                 alt="User Avatar" class="avatar">
        </div>

        <!-- Hiển thị thông tin người dùng -->
        <div class="profile-info">
            <label>Username</label>
            <p>${user.userName}</p>

            <label>Full Name</label>
            <p>${user.fullName}</p>

            <label>Creation Date</label>
            <p>${user.creationDate}</p>

            <label>Email</label>
            <p>${user.email}</p>

            <label>Phone Number</label>
            <p>${user.numberPhone}</p>

            <label>Gender</label>
            <p>${user.gender}</p>
        </div>


        <!-- Các nút điều hướng -->
        <div class="button-group">
            <a href="editProfile" class="button">Edit Profile</a>
            <a href="changePassword" class="button">Change Password</a>
            <a href="homepage" class="button">Back to Home</a>
        </div>
    </div>
</body>
</html>
