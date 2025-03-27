<%-- 
    Document   : EditProfile
    Created on : Mar 8, 2025, 6:00:12 PM
    Author     : default
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edit Profile</title>
        <link rel="shortcut icon" type="image/x-icon" href="<%= application.getInitParameter("shortcut")%>">
        <link rel="icon" type="image/png" href="img/logo.jpg">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css" type="text/css">
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
            .edit-container {
                background: rgba(255, 255, 255, 0.8);
                padding: 25px;
                border-radius: 15px;
                box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
                text-align: center;
                width: 350px;
            }
            h2 {
                font-size: 24px;
                color: #ff3955;
                margin-bottom: 15px;
                text-shadow: 1px 1px 2px rgba(255, 57, 85, 0.8);
            }
            .avatar-container {
                display: flex;
                flex-direction: column;
                align-items: center;
                margin-bottom: 10px;
            }
            .avatar {
                width: 100px;
                height: 100px;
                border-radius: 50%;
                border: 3px solid #ff3955;
                object-fit: cover;
                margin-bottom: 5px;
            }
            .custom-file-upload {
                display: inline-block;
                padding: 6px 12px;
                cursor: pointer;
                background-color: #ff3955;
                color: white;
                border-radius: 5px;
                font-size: 14px;
            }
            .custom-file-upload:hover {
                background-color: #cc2f44;
            }
            #avatarInput {
                display: none; /* Ẩn input file mặc định */
            }
            .input-group {
                margin-bottom: 12px;
                text-align: left;
            }
            .input-group label {
                font-weight: bold;
                font-size: 14px;
                display: block;
                margin-bottom: 5px;
            }
            .input-group input,
            .input-group select {
                width: 100%;
                padding: 10px;
                border-radius: 6px;
                border: 1px solid #ff3955;
                font-size: 14px;
                height: 40px; /* Đảm bảo cả input và select có chiều cao giống nhau */
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
            .btn-back {
                background: #ccc;
                color: black;
            }
            .btn-back:hover {
                background: #b3b3b3;
            }
        </style>
    </head>
    <body>
        <div class="edit-container">
            <h2>Edit Profile</h2>
            <c:if test="${not empty error}">
                <div style="color: red; font-weight: bold; margin-bottom: 10px;">${error}</div>
            </c:if>
            <c:if test="${not empty message}">
                <div style="color: green; font-weight: bold; margin-bottom: 10px;">${message}</div>
            </c:if>
            <form method="POST" enctype="multipart/form-data">
                <!-- Avatar & Choose File -->
                <div class="avatar-container">
                    <img src="${user.imageUML}" alt="User Avatar" class="avatar" id="avatarPreview">
                    <label for="avatarInput" class="custom-file-upload">Choose File</label>
                    <input type="file" name="avatar" id="avatarInput" accept="image/*" onchange="previewAvatar(event)">
                </div>
                <div class="input-group">
                    <label>Username</label>
                    <input type="text" name="userName" value="${user.userName}" required>
                </div>
                <div class="input-group">
                    <label>Full Name</label>
                    <input type="text" name="fullName" value="${user.fullName}" required>
                </div>
                <div class="input-group">
                    <label>Email</label>
                    <input type="email" name="email" value="${user.email}" required>
                </div>
                <div class="input-group">
                    <label>Number Phone</label>
                    <input type="text" name="numberPhone" value="${user.numberPhone}" required>
                </div>
                <div class="input-group">
                    <label>Gender</label>
                    <select name="gender">
                        <option value="Male" ${user.gender == 'Male' ? 'selected' : ''}>Male</option>
                        <option value="Female" ${user.gender == 'Female' ? 'selected' : ''}>Female</option>
                    </select>
                </div>
                <button type="submit">Save</button>
                <button type="button" class="btn-back" onclick="window.location.href = 'viewprofile'">Back to Profile</button>
            </form>
        </div>

        <script>
            function previewAvatar(event) {
                const reader = new FileReader();
                reader.onload = function () {
                    document.getElementById('avatarPreview').src = reader.result;
                }
                reader.readAsDataURL(event.target.files[0]);
            }
        </script>
    </body>
</html>







