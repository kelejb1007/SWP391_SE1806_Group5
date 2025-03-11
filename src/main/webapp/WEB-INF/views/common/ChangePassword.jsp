<%-- 
    Document   : ChangePassword
    Created on : Mar 9, 2025, 9:09:18 PM
    Author     : default
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Change Password</title>
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
                <h1 class="page-header">Change Password</h1>

                <form action="${pageContext.request.contextPath}/ChangePassword" method="post">
                    <div class="form-group">
                        <label>Current Password:</label>
                        <input type="password" class="form-control" name="currentPassword" required>
                    </div>

                    <div class="form-group">
                        <label>New Password:</label>
                        <input type="password" class="form-control" name="newPassword" required>
                    </div>

                    <div class="form-group">
                        <label>Confirm New Password:</label>
                        <input type="password" class="form-control" name="confirmPassword" required>
                    </div>

                    <button type="submit" class="btn btn-success">Change Password</button>
                    <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-default">Cancel</a>
                </form>
            </div>
        </div>
    </div>
</body>
</html>

