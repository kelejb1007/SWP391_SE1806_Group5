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
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f8ff;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .signup-form {
            background-color: #fff;
            border-radius: 10px;
            padding: 40px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
            text-align: center;
        }

        h2 {
            font-size: 2.5em;
            color: #333;
            margin-bottom: 30px;
        }

        label {
            font-size: 16px;
            color: #4d94ff;
            display: block;
            margin-bottom: 8px;
            text-align: left;
        }

        input, select {
            width: 100%;
            padding: 12px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 16px;
            box-sizing: border-box;
            background-color: white;
        }

        input:focus, select:focus {
            border-color: #66b3ff;
            outline: none;
        }

        button {
            width: 100%;
            padding: 14px;
            background-color: #66b3ff;
            color: white;
            font-size: 18px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #3385ff;
        }

        .error {
            color: red;
            margin-bottom: 15px;
        }

        footer {
            background-color: #4d94ff;
            color: white;
            padding: 15px;
            position: fixed;
            width: 100%;
            bottom: 0;
            text-align: center;
        }

        footer p {
            margin: 0;
        }
    </style>
    <script>
        function validateForm() {
            var password = document.getElementById("password").value;
            var confirmPassword = document.getElementById("confirmPassword").value;

            if (password !== confirmPassword) {
                alert("Password must be the same");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>

    <div class="signup-form">
        <h2>Sign Up</h2>
        <form action="Register" method="post" onsubmit="return validateForm()">
            <div class="error">
                <%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>
            </div>

            <label for="username">User Name</label>
            <input type="text" id="username" name="username" required>

            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>

            <label for="confirmPassword">Confirm Password</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required>

            <label for="fullName">Full Name</label>
            <input type="text" id="fullName" name="fullName" required>

            <label for="email">Email</label>
            <input type="email" id="email" name="email" required>

            <label for="numberPhone">Phone Number</label>
            <input type="text" id="numberPhone" name="numberPhone" required>

            <label for="gender">Gender</label>
            <select name="gender" id="gender" required>
                <option value="" disabled selected>Select your gender</option>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
            </select>

            <button type="submit">Register</button>
        </form>
    </div>

    <footer>
        <p>© 2025 NovelWeb. All rights reserved.</p>
    </footer>

</body>
</html>



