<%-- 
    Document   : EditStaff
    Created on : Mar 9, 2025, 6:59:56 PM
    Author     : default
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Staff Profile</title>
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
                <h1 class="page-header">Edit Staff Profile</h1>

                <form action="${pageContext.request.contextPath}/EditStaff" method="post">
                    <input type="hidden" name="managerID" value="${staff.managerID}" />

                    <div class="form-group">
                        <label>Username:</label>
                        <input type="text" class="form-control" name="username" value="${staff.username}" required>
                    </div>

                    <div class="form-group">
                        <label>Full Name:</label>
                        <input type="text" class="form-control" name="fullName" value="${staff.fullName}" required>
                    </div>

                    <div class="form-group">
                        <label>Email:</label>
                        <input type="email" class="form-control" name="email" value="${staff.email}" required>
                    </div>

                    <div class="form-group">
                        <label>Phone Number:</label>
                        <input type="text" class="form-control" name="numberPhone" value="${staff.numberPhone}" required>
                    </div>

                    <div class="form-group">
                        <label>Gender:</label>
                        <select class="form-control" name="gender">
                            <option value="Male" ${staff.gender == 'Male' ? 'selected' : ''}>Male</option>
                            <option value="Female" ${staff.gender == 'Female' ? 'selected' : ''}>Female</option>
                        </select>
                    </div>

                    <button type="submit" class="btn btn-success">Save Changes</button>
                    <a href="${pageContext.request.contextPath}/managestaff" class="btn btn-default">Cancel</a>
                </form>
            </div>
        </div>
    </div>
</body>
</html>

