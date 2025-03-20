<%-- 
    Document   : RegisterStaff
    Created on : Mar 3, 2025, 3:03:43 PM
    Author     : Khoa
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register Staff</title>
    <link rel="stylesheet" href="css/startmin/bootstrap.css">
    <link rel="stylesheet" href="css/startmin/startmin.css">
    <link rel="stylesheet" href="css/startmin/font-awesome.min.css">
</head>
<body>
    <div id="wrapper">
        <jsp:include page="../admin/header.jsp" />
        <jsp:include page="../admin/sidebar.jsp" />

        <div id="page-wrapper">
            <div class="container-fluid">
                <h1 class="page-header">Register Staff</h1>

                <div class="panel panel-default">
                    <div class="panel-heading">Create New Staff Account</div>
                    <div class="panel-body">
                        <form action="RegisterStaff" method="post" onsubmit="return validateForm()">
                            <div class="form-group">
                                <label for="username">User Name</label>
                                <input type="text" class="form-control" id="username" name="username" required>
                            </div>
                            
                            <div class="form-group">
                                <label for="password">Password</label>
                                <input type="password" class="form-control" id="password" name="password" required>
                            </div>

                            <div class="form-group">
                                <label for="confirmPassword">Confirm Password</label>
                                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                            </div>

                            <div class="form-group">
                                <label for="fullName">Full Name</label>
                                <input type="text" class="form-control" id="fullName" name="fullName" required>
                            </div>

                            <div class="form-group">
                                <label for="email">Email</label>
                                <input type="email" class="form-control" id="email" name="email" required>
                            </div>

                            <div class="form-group">
                                <label for="numberPhone">Phone Number</label>
                                <input type="text" class="form-control" id="numberPhone" name="numberPhone" required>
                            </div>

                            <div class="form-group">
                                <label for="gender">Gender</label>
                                <select class="form-control" name="gender" id="gender" required>
                                    <option value="" disabled selected>Select your gender</option>
                                    <option value="Male">Male</option>
                                    <option value="Female">Female</option>
                                </select>
                            </div>

                            <button type="submit" class="btn btn-primary">Register</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        function validateForm() {
            var password = document.getElementById("password").value;
            var confirmPassword = document.getElementById("confirmPassword").value;
            if (password !== confirmPassword) {
                alert("Passwords do not match");
                return false;
            }
            return true;
        }
    </script>
</body>
</html>


