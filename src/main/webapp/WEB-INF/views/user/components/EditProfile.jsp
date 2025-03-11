<%-- 
    Document   : EditProfile
    Created on : Mar 8, 2025, 6:00:12 PM
    Author     : default
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edit Profile</title>
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
                border-radius: 10px;
                padding: 20px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                width: 100%;
                max-width: 350px;
                text-align: center;
            }

            .avatar-container {
                display: flex;
                justify-content: center;
                align-items: center;
                margin: 15px 0;
            }

            .avatar {
                width: 120px;
                height: 120px;
                border-radius: 50%;
                border: 3px solid #4d94ff;
            }

            h2 {
                font-size: 2em;
                color: #333;
                margin-bottom: 15px;
            }

            label {
                font-size: 14px;
                color: #4d94ff;
                display: block;
                margin-bottom: 6px;
                text-align: left;
            }

            input {
                width: 100%;
                padding: 8px;
                margin: 6px 0;
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

            button {
                width: 100%;
                padding: 10px;
                background-color: #66b3ff;
                color: white;
                font-size: 16px;
                border: none;
                border-radius: 8px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            button:hover {
                background-color: #3385ff;
            }

            footer {
                background-color: #4d94ff;
                color: white;
                padding: 10px;
                width: 100%;
                text-align: center;
                margin-top: auto;
            }

            footer p {
                margin: 0;
            }
        </style>
    </head>
    <body>

        <div class="container">
            <div class="profile-form">
                <h2>Edit Profile</h2>

                <div class="avatar-container">
                    <img src="${user.imageUML}" alt="User Avatar" class="avatar">
                </div>

                <form method="POST" action="<%= request.getContextPath()%>/editProfile">
                    <label for="fullName">Full Name</label>
                    <input type="text" id="fullName" name="fullName" value="${user.fullName}" required>

                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" value="${user.email}" required>

                    <label for="numberPhone">Number Phone</label>
                    <input type="text" id="numberPhone" name="numberPhone" value="${user.numberPhone}">

                    <label for="dateOfBirth">Date of Birth</label>
                    <input type="date" id="dateOfBirth" name="dateOfBirth" value="${user.dateOfBirth}">

                    <label for="gender">Gender</label>
                    <select id="gender" name="gender">
                        <option value="Male" ${user.gender == 'Male' ? 'selected' : ''}>Male</option>
                        <option value="Female" ${user.gender == 'Female' ? 'selected' : ''}>Female</option>
                        
                    </select>

                    <label for="imageUML">Avatar URL</label>
                    <input type="text" id="imageUML" name="imageUML" value="${user.imageUML}">

                    <button type="submit">Save Changes</button>
                    <button type="button" onclick="window.location.href = 'http://localhost:8080/NovelWeb/homepage'" 
                            style="margin-top: 10px; background-color: #ccc; color: black; padding: 10px; border-radius: 8px; border: none; cursor: pointer;">
                        Back to Home
                    </button>
                </form>
            </div>
        </div>

        <footer>
            <p>© 2025 NovelWeb. All rights reserved.</p>
        </footer>

    </body>
</html>



