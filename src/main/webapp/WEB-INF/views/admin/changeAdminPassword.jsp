<%-- 
    Document   : changeAdminPassword
    Created on : 9 thg 3, 2025, 18:53:54
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change Admin Password</title>
        <link rel="shortcut icon" type="image/x-icon" href="<%= application.getInitParameter("shortcut")%>">
        <link rel="stylesheet" href="css/startmin/bootstrap.css">
        <link rel="stylesheet" href="css/startmin/startmin.css">
        <link rel="stylesheet" href="css/startmin/font-awesome.min.css" type="text/css">
    </head>
    <body>
        <!-- Navigation -->
        <%@ include file="header.jsp" %>
        <%@ include file="sidebar.jsp" %>

        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Change Admin Password</h1>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                Update Your Password
                            </div>
                            <div class="panel-body">
                                <% String errorMessage = (String) session.getAttribute("errorMessage"); %>
                                <% if (errorMessage != null) {%>
                                <div class="alert alert-danger"><%= errorMessage%></div>
                                <% session.removeAttribute("errorMessage"); %>
                                <% } %>

                                <% String successMessage = (String) session.getAttribute("successMessage"); %>
                                <% if (successMessage != null) {%>
                                <div class="alert alert-success"><%= successMessage%></div>
                                <% session.removeAttribute("successMessage"); %>
                                <% }%>

                                <form action="${pageContext.request.contextPath}/change-password" method="post" onsubmit="return validateForm()">
                                    <div class="form-group">
                                        <label>Old Password</label>
                                        <input type="password" class="form-control" id="oldPassword" name="oldPassword" required>
                                    </div>

                                    <div class="form-group">
                                        <label>New Password</label>
                                        <input type="password" class="form-control" id="newPassword" name="newPassword" required>
                                    </div>

                                    <div class="form-group">
                                        <label>Confirm Password</label>
                                        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                                    </div>

                                    <div id="error-message" class="text-danger"></div>

                                    <button type="submit" class="btn btn-primary">Change Password</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            function validateForm() {
                var oldPassword = document.getElementById("oldPassword").value;
                var newPassword = document.getElementById("newPassword").value;
                var confirmPassword = document.getElementById("confirmPassword").value;
                var errorMessage = document.getElementById("error-message");

                if (oldPassword.trim() === "") {
                    errorMessage.innerHTML = "Old password is required.";
                    return false;
                }
                if (newPassword.length < 8) {
                    errorMessage.innerHTML = "Password must be at least 8 characters long.";
                    return false;
                }
                if (!/[A-Z]/.test(newPassword) || !/[0-9]/.test(newPassword)) {
                    errorMessage.innerHTML = "Password must contain at least one uppercase letter and one number.";
                    return false;
                }
                if (/\s/.test(newPassword)) {
                    errorMessage.innerHTML = "Password cannot contain spaces.";
                    return false;
                }
                if (newPassword === oldPassword) {
                    errorMessage.innerHTML = "New password cannot be the same as the old password.";
                    return false;
                }
                if (newPassword !== confirmPassword) {
                    errorMessage.innerHTML = "Passwords do not match.";
                    return false;
                }
                return true;
            }

        </script>

        <script src="js/startmin/jquery.min.js"></script>
        <script src="js/startmin/bootstrap.min.js"></script>
        <script src="js/startmin/metisMenu.min.js"></script>
        <script src="js/startmin/startmin.js"></script>
    </body>
</html>