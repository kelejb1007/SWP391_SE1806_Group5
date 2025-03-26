<%-- 
    Document   : EditProfile
    Created on : Mar 8, 2025, 6:00:12 PM
    Author     : default
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edit Profile</title>
        <style>
            * {
                box-sizing: border-box;
                margin: 0;
                padding: 0;
            }

            body {
                font-family: 'Poppins', sans-serif;
                background-color: #eef2f7;
                display: flex;
                flex-direction: column;
                min-height: 100vh;
            }

            .main-content {
                flex: 1;
                display: flex;
                justify-content: center;
                align-items: center;
                padding-bottom: 60px;
            }

            .container {
                background: white;
                border-radius: 12px;
                padding: 30px;
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.15);
                width: 100%;
                max-width: 500px;
                text-align: center;
            }

            .error-message {
                color: red;
                font-size: 14px;
                margin-bottom: 10px;
            }

            .message {
                color: green;
                font-size: 14px;
                margin-bottom: 10px;
            }

            .avatar-container {
                position: relative;
                display: flex;
                flex-direction: column;
                align-items: center;
                margin-bottom: 15px;
            }

            .avatar {
                width: 140px;
                height: 140px;
                border-radius: 50%;
                border: 4px solid #4d94ff;
                object-fit: cover;
                margin-bottom: 10px;
            }

            .file-upload {
                display: flex;
                flex-direction: column;
                align-items: center;
            }

            .file-upload label {
                background-color: #4d94ff;
                color: white;
                padding: 8px 12px;
                border-radius: 6px;
                cursor: pointer;
                transition: 0.3s;
            }

            .file-upload label:hover {
                background-color: #1a75ff;
            }

            .file-upload input {
                display: none;
            }

            .input-group {
                text-align: left;
                margin-bottom: 12px;
            }

            .input-group label {
                font-weight: bold;
                font-size: 14px;
                display: block;
                margin-bottom: 5px;
            }

            .input-group input, .input-group select {
                width: 100%;
                padding: 10px;
                border-radius: 6px;
                border: 1px solid #ccc;
                font-size: 14px;
                transition: 0.3s;
            }

            .input-group input:focus, .input-group select:focus {
                border-color: #4d94ff;
                box-shadow: 0 0 8px rgba(77, 148, 255, 0.2);
                outline: none;
            }

            .buttons {
                margin-top: 15px;
                display: flex;
                justify-content: space-between;
            }

            .buttons button {
                width: 48%;
                padding: 12px;
                border-radius: 8px;
                font-size: 16px;
                border: none;
                cursor: pointer;
                transition: 0.3s;
            }

            .btn-save {
                background-color: #4d94ff;
                color: white;
            }

            .btn-save:hover {
                background-color: #1a75ff;
            }

            .btn-back {
                background-color: #ccc;
                color: black;
            }

            .btn-back:hover {
                background-color: #b3b3b3;
            }

            footer {
                background-color: #4d94ff;
                color: white;
                text-align: center;
                padding: 10px;
                position: fixed;
                bottom: 0;
                width: 100%;
                height: 50px;
                line-height: 30px;
            }
        </style>
    </head>
    <body>

        <div class="main-content">
            <div class="container">
                <h2>Edit Profile</h2>

                <!-- Hiển thị lỗi nếu có -->
                <c:if test="${not empty error}">
                    <p class="error-message">${error}</p>
                </c:if>

                <!-- Hiển thị thông báo thành công -->
                <c:if test="${not empty message}">
                    <p class="message">${message}</p>
                </c:if>

                <form method="POST" enctype="multipart/form-data">
                    <div class="avatar-container">
                        <img src="${user.imageUML}" alt="User Avatar" class="avatar" id="avatarPreview">
                        <div class="file-upload">
                            <label for="avatarInput">Choose File</label>
                            <input type="file" name="avatar" id="avatarInput" accept="image/*" onchange="previewAvatar(event)">
                        </div>
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

                    <div class="buttons">
                        <button type="submit" class="btn-save">Save</button>
                        <button type="button" class="btn-back" onclick="window.location.href = 'viewprofile'">
                            Back to Profile
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <footer>
            © 2025 NovelWeb. All rights reserved.
        </footer>

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






